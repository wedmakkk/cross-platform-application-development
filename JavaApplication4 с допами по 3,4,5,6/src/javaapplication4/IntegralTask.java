package javaapplication4;

import java.util.concurrent.Callable;

public class IntegralTask implements Callable<Double> {
    private final double start;
    private final double end;
    private final double step;

    public IntegralTask(double start, double end, double step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public Double call() {
        double partialResult = 0.0;
        double x = start;

        while (x < end) {
            double nextX = Math.min(x + step, end);
            partialResult += Math.sqrt(x) * (nextX - x);
            x = nextX;
        }

        return partialResult;
    }
}