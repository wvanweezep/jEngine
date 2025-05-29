package engine;

import injection.Injector;
import injection.annotations.Inject;
import injection.annotations.PostConstruct;
import injection.annotations.Singleton;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

@Singleton
public class Engine {

    private final Injector INJECTOR;

    private Window window;


    public Engine(Injector injector) {
        INJECTOR = injector;
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
    }

    public void run() {
        this.window = INJECTOR.create(Window.class);

        GL.createCapabilities();
        glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

        while (!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            window.render();
            glfwPollEvents();
        }

        destroy();
    }

    private void destroy() {
        window.destroy();
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static void main(String[] args) {
        Injector injector = new Injector();
        injector.bind(Engine.class, new Engine(injector));
        injector.get(Engine.class).run();
    }
}
