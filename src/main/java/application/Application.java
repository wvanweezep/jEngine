package application;

public sealed abstract class Application permits GraphicApplication {

    void start() {
        onStart();
    }

    void update() {
        onUpdate();
    }

    void exit() {
        onExit();
    }

    protected void onStart() {
        System.out.println("Starting Application...");
    }

    protected abstract void onUpdate();

    protected abstract void onExit();
}
