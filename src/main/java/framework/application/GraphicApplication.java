package framework.application;

import framework.graphics.MeshHandler;
import framework.graphics.TextureHandler;
import framework.injection.annotations.Singleton;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;


/**
 * Extension of {@link Application} class for a fully graphical application.
 * <p>
 * This class defines in addition to the core application lifecycle of the {@link Application},
 * graphical lifecycle methods, including:
 * <ul>
 *     <li>{@link #onRender()} - called before refreshing the {@link Window}.</li>
 * </ul>
 */
@Singleton
public abstract non-sealed class GraphicApplication extends Application {

    /**
     * Main Window of the {@link GraphicApplication}.
     */
    public Window window;


    /**
     * Executed before refreshing the {@link Window}.
     */
    protected abstract void onRender();


    /**
     * Package private start which handles start logic, initializes <a href="https://www.lwjgl.org">lwjgl</a> and calls {@link #onStart()}.
     */
    @Override
    void start() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        INJECTOR.bind(GraphicApplication.class, this);
        this.window = INJECTOR.create(Window.class);
        INJECTOR.create(MeshHandler.class);
        INJECTOR.create(TextureHandler.class);

        GL.createCapabilities();
        glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

        onStart();
        isRunning = true;
    }

    /**
     * Package private update which handles update logic, {@link Window} logic and calls {@link #onUpdate()}.
     */
    @Override
    void update() {
        if (window.shouldClose())
            quit();
        super.update();
        render();
    }

    /**
     * Package private exit which handles the exit logic, terminates <a href="https://www.lwjgl.org">lwjgl</a> and calls {@link #onExit()}.
     */
    @Override
    void exit() {
        window.destroy();
        glfwTerminate();
        glfwSetErrorCallback(null).free();
        super.exit();
    }

    /**
     * Package private render which handles render logic, refreshes the {@link Window} and calls {@link #onRender()}.
     */
    void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        onRender();
        window.render();
        glfwPollEvents();
    }
}
