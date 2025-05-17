package injection;

import injection.annotations.Inject;
import injection.annotations.PostConstruct;
import injection.annotations.Singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for managing dependency injection, keeping track of Singletons and handling PostConstruct methods.
 * Note that Singleton classes are automatically bound and do not have to be registered.
 */
public final class Injector {

    private final Map<Class<?>, Object> singletons = new HashMap<>();

    /**
     * Getter for a Singleton class within the Injector's Singleton map.
     *
     * @param cls The class to retrieve from the Singleton map.
     * @return The requested Singleton, or {@code null} if the class type is not a Singleton.
     * @param <T> The type of the class to retrieve from the Singleton map.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> cls) {
        if (cls.isAnnotationPresent(Singleton.class) && !singletons.containsKey(cls))
            return create(cls);
        return (T) singletons.get(cls);
    }

    /**
     * Manually bind an instance of a class to a class in the Singleton map (for testing purposes).
     *
     * @param cls The class to bind the instance too.
     * @param instance The instance for the given class.
     * @param <T> The type of the class to bind.
     */
    public <T> void bind(Class<T> cls, T instance) {
        singletons.put(cls, instance);
    }

    /**
     * Creates a new instance of the class if the class is either not a Singleton or is not yet bound, and returns it.
     *
     * @param cls The class to instantiate or retrieve.
     * @return The requested class, either newly created or from the Singleton map.
     * @param <T> The type of the class to instantiate or retrieve.
     * @throws CircularDependencyException if the given class contains circular dependencies.
     */
    public <T> T create(Class<T> cls) {
        return create(cls, new ArrayDeque<>());
    }

    /**
     * Creates a new instance of the class if the class is either not a Singleton or is not yet bound, and returns it,
     * while keeping track of a construction stack to detect circular dependencies.
     *
     * @param cls The class to instantiate or retrieve.
     * @param stack The stack to keep track of the class type we are currently constructing.
     * @return The requested class, either newly created or from the Singleton map.
     * @param <T> The type of the class to instantiate or retrieve.
     * @throws CircularDependencyException if the given class contains circular dependencies detected by
     * the construction stack.
     */
    private <T> T create(Class<T> cls, Deque<Class<?>> stack) {
        if (stack.contains(cls))
            throw new CircularDependencyException(cls.getName());
        if (singletons.containsKey(cls))
            return cls.cast(singletons.get(cls));
        stack.push(cls);

        try {
            T instance = createInstance(cls, stack);
            if (cls.isAnnotationPresent(Singleton.class))
                singletons.put(cls, instance);

            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Object dependency = create(field.getType(), stack);
                    field.setAccessible(true);
                    field.set(instance, dependency);
                }
            }
            invokePostConstructMethods(cls, instance);
            return instance;
        } catch (CircularDependencyException e) {
            throw new CircularDependencyException(stack.stream().map(Class::getSimpleName).collect(Collectors.joining(" → ")));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            stack.pop();
        }
    }

    /**
     * Instantiates an instance of the given class with the correct constructor.
     *
     * @param cls The class to instantiate.
     * @param stack The current construction stack to detect circular dependencies.
     * @return An instance of the class, created using the correct constructor.
     * @param <T> The type of the class to instantiate.
     * @throws Exception when something went wrong instantiating an instance of the class.
     */
    private <T> T createInstance(Class<T> cls, Deque<Class<?>> stack) throws Exception {
        Constructor<?> injectConstructor = null;
        for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                if (injectConstructor != null)
                    throw new RuntimeException("Multiple @Inject constructors found.");
                injectConstructor = constructor;
            }
        }

        T instance;
        if (injectConstructor != null) {
            Class<?>[] paramTypes = injectConstructor.getParameterTypes();
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                args[i] = create(paramTypes[i], stack);
            }

            injectConstructor.setAccessible(true);
            instance = cls.cast(injectConstructor.newInstance(args));
        } else instance = cls.getDeclaredConstructor().newInstance();

        return instance;
    }

    /**
     * Invokes all methods marked by the {@code PostConstruct} annotation in a given class.
     *
     * @param cls The class to scan for {@code PostConstruct} methods.
     * @param instance The instance of the class to invoke the methods for.
     * @param <T> The type of the class to invoke the methods for.
     * @throws Exception when the method is inaccessible.
     */
    private <T> void invokePostConstructMethods(Class<T> cls, T instance) throws Exception {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.setAccessible(true);
                method.invoke(instance);
            }
        }
    }
}
