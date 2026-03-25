package javaapplication4;

import java.io.Serializable;

public class RecIntegral implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final double MIN_VALUE = 0.000001;
    private static final double MAX_VALUE = 1000000.0;

    public static final int THREAD_COUNT = 6;

    private double lower;
    private double upper;
    private double step;
    private Double result;

    private void validateBound(double value) throws InvalidRangeException {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new InvalidRangeException(
                "Значение должно быть в диапазоне от " + MIN_VALUE + " до " + MAX_VALUE
            );
        }
    }

    private void validateAll(double lower, double upper, double step) throws InvalidRangeException {
        validateBound(lower);
        validateBound(upper);

        if (lower > upper) {
            throw new InvalidRangeException("Нижняя граница не может быть больше верхней.");
        }

        if (step < MIN_VALUE || step > MAX_VALUE) {
            throw new InvalidRangeException(
                "Шаг должен быть в диапазоне от " + MIN_VALUE + " до " + MAX_VALUE
            );
        }

        if (step > (upper - lower)) {
            throw new InvalidRangeException(
                "Шаг должен быть меньше или равен разнице между верхней и нижней границами."
            );
        }
    }

    public RecIntegral(double lower, double upper, double step) throws InvalidRangeException {
        validateAll(lower, upper, step);
        this.lower = lower;
        this.upper = upper;
        this.step = step;
        this.result = null;
    }

    public RecIntegral(double lower, double upper, double step, double result) throws InvalidRangeException {
        validateAll(lower, upper, step);
        this.lower = lower;
        this.upper = upper;
        this.step = step;
        this.result = result;
    }

    public double getLower() {
        return lower;
    }

    public void setLower(double lower) throws InvalidRangeException {
        validateAll(lower, this.upper, this.step);
        this.lower = lower;
    }

    public double getUpper() {
        return upper;
    }

    public void setUpper(double upper) throws InvalidRangeException {
        validateAll(this.lower, upper, this.step);
        this.upper = upper;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) throws InvalidRangeException {
        validateAll(this.lower, this.upper, step);
        this.step = step;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public double integrateSqrtMultiThreaded(double lp, double hp, double step) throws InterruptedException {
        if (lp < 0 || hp < 0) {
            throw new IllegalArgumentException("Границы интегрирования не могут быть отрицательными.");
        }
        if (step <= 0) {
            throw new IllegalArgumentException("Шаг должен быть положительным.");
        }
        if (lp > hp) {
            throw new IllegalArgumentException("Нижняя граница не может быть больше верхней.");
        }

        double intervalLength = hp - lp;
        double partLength = intervalLength / THREAD_COUNT;

        IntegralTask[] tasks = new IntegralTask[THREAD_COUNT];
        Thread[] threads = new Thread[THREAD_COUNT];

        double start = lp;

        for (int i = 0; i < THREAD_COUNT; i++) {
            double end = (i == THREAD_COUNT - 1) ? hp : start + partLength;

            tasks[i] = new IntegralTask(start, end, step);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();

            start = end;
        }

        double total = 0.0;

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
            total += tasks[i].getPartialResult();
        }

        return total;
    }

    @Override
    public String toString() {
        return "RecIntegral{" +
                "lower=" + lower +
                ", upper=" + upper +
                ", step=" + step +
                ", result=" + result +
                '}';
    }
}