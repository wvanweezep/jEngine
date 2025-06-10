package framework.time;

public class Clock extends AbstractTimer {

    private long pausedTime;

    @Override
    public double getElapsed() {
        if (!isRunning) return 0.0;
        long current = isPaused ? pausedTime : System.nanoTime();
        return (current - anchorTime) / 1_000_000_000.0;
    }

    @Override
    public void start() {
        anchorTime = System.nanoTime();
        prevTime = anchorTime;
        super.start();
    }

    @Override
    public void pause() {
        if (isPaused) return;
        pausedTime = System.nanoTime();
        super.pause();
    }

    @Override
    public void resume() {
        if (!isPaused) return;
        long current = System.nanoTime();
        anchorTime += current - pausedTime;
        prevTime = current;
        super.resume();
    }

    @Override
    public String toString() {
        return "Clock(running=" + isRunning +
                ", paused=" + isPaused +
                ", elapsed=" + getElapsed() + ")";
    }
}
