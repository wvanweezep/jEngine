package injection;

/**
 * Thrown when a Circular Dependency is present in a given class.
 */
public class CircularDependencyException extends RuntimeException {
    public CircularDependencyException(String stack) {
        super("Circular Dependency detected: " + stack);
    }
}
