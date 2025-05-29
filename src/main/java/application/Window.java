package application;

import injection.annotations.Inject;
import injection.annotations.PostConstruct;
import injection.annotations.Singleton;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

@Singleton
public class Window {

    @Inject
    private GraphicApplication application;

    private final long window;

    public Window() {
        this.window = glfwCreateWindow(1920, 1080, "GraphicApplication", NULL, NULL);
        if (window == NULL) throw new RuntimeException("Failed to create the Window.");
    }

    @PostConstruct
    private void init() {
        glfwSetKeyCallback(window, (window, key, _, action, _) -> {
            if (key == GLFW_KEY_Q && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
                application.quit();
            }
        });

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    void render() {
        glfwSwapBuffers(window);
    }

    boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    void destroy() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }

}
