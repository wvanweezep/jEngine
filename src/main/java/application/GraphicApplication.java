package application;

public abstract non-sealed class GraphicApplication extends Application {

    void render() {
        onRender();
    }

    protected abstract void onRender();
}
