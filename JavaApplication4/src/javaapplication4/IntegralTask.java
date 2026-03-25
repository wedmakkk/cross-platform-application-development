package javaapplication4;

public class IntegralTask implements Runnable {
    private final double start;
    private final double end;
    private final double step;
    private double partialResult;

    public IntegralTask(double start, double end, double step) {
        this.start = start;
        this.end = end;
        this.step = step;
        this.partialResult = 0.0;
    }

    @Override
    public void run() {
        double x = start;

        while (x < end) {
            double nextX = Math.min(x + step, end);
            partialResult += Math.sqrt(x) * (nextX - x);
            x = nextX;
        }
    }

    public double getPartialResult() {
        return partialResult;
    }
}