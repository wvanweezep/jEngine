package engine;

import application.GraphicApplication;

public class Engine extends GraphicApplication {

    @Override
    protected void onStart() {
        System.out.println("Starting Application...");
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void onExit() {
        System.out.println("Exiting Application...");
    }

    @Override
    protected void onRender() {

    }

    public static void main(String[] args) {
        new Engine().run();
    }
}
