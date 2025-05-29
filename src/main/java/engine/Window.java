package engine;

import injection.annotations.Inject;
import injection.annotations.PostConstruct;
import injection.annotations.Singleton;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

@Singleton
public class Window {

    @Inject
    private Engine engine;

    private long window;

    public Window() {
        this.window = glfwCreateWindow(1920, 1080, "jEngine", NULL, NULL);
        if (window == NULL) throw new RuntimeException("Failed to create the GLFW window.");
    }

    @PostConstruct
    public void init() {
        glfwSetKeyCallback(window, (window, key, _, action, _) -> {
            if (key == GLFW_KEY_Q && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    public void render() {
        glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void destroy() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }

}
