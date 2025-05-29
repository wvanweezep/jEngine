package application;

import injection.Injector;
import injection.annotations.Singleton;


/**
 * Base class for a fully non-graphical application.
 * <p>
 * This class defines the core application lifecycle, including:
 * <ul>
 *     <li>{@link #onStart()} - called at the start of execution</li>
 *     <li>{@link #onUpdate()} - called during execution</li>
 *     <li>{@link #onExit()} - called at the end of execution</li>
 * </ul>
 * @see GraphicApplication
 */
@Singleton
public sealed abstract class Application permits GraphicApplication {

    /**
     * Injector of the {@link Application} managing injection and Singletons.
     */
    public final Injector INJECTOR = new Injector();

    /**
     * Package private boolean storing {@link Application} execution status.
     */
    boolean isRunning;


    /**
     * Executed at the start of the execution of the {@link Application}.
     */
    protected abstract void onStart();

    /**
     * Executed during runtime of the {@link Application}.
     */
    protected abstract void onUpdate();

    /**
     * Executed at the end of the execution of the {@link Application} before full-exit.
     */
    protected abstract void onExit();


    /**
     * Package private start which handles start logic and calls {@link #onStart()}.
     */
    void start() {
        INJECTOR.bind(Application.class, this);
        onStart();
        isRunning = true;
    }

    /**
     * Package private update which handles update logic and calls {@link #onUpdate()}.
     */
    void update() {
        onUpdate();
    }

    /**
     * Package private exit which handles the exit logic and calls {@link #onExit()}.
     */
    void exit() {
        onExit();
    }

    /**
     * Starts the execution of the {@link Application}.
     */
    public final void run() {
        start();
        while (isRunning) update();
        exit();
    }

    /**
     * Quits the {@link Application} by exiting the update loop.
     */
    public final void quit() {
        isRunning = false;
    }
}
