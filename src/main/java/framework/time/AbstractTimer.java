package framework.time;

public abstract class AbstractTimer {

    protected long anchorTime;
    protected long prevTime;

    protected boolean isPaused;
    protected boolean isRunning;

    protected double delta;

    public final double getDeltaTime() {
        return delta;
    }

    public abstract double getElapsed();

    public void start() {
        isPaused = false;
        isRunning = true;
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public final void reset() {
        start();
    }

    public void tick() {
        if (isPaused) return;
        long current = System.nanoTime();
        delta = (current - prevTime) / 1_000_000_000.0;
        prevTime = current;
    }
}
