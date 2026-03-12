package javaapplication4;

public class RecIntegral {
    private static final double MIN_VALUE = 0.000001;
    private static final double MAX_VALUE = 1000000.0;

    private double lower;
    private double upper;
    private double step;
    private Double result;   // null, если результат не вычислен

    // Внутренний метод проверки диапазона
    private void validateRange(double value) throws InvalidRangeException {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new InvalidRangeException(
                "Значение должно быть в диапазоне от " + MIN_VALUE + " до " + MAX_VALUE
            );
        }
    }

    // Конструкторы с проверкой диапазона
    public RecIntegral(double lower, double upper, double step) throws InvalidRangeException {
        validateRange(lower);
        validateRange(upper);
        validateRange(step);
        this.lower = lower;
        this.upper = upper;
        this.step = step;
        this.result = null;
    }

    public RecIntegral(double lower, double upper, double step, double result) throws InvalidRangeException {
        validateRange(lower);
        validateRange(upper);
        validateRange(step);
        this.lower = lower;
        this.upper = upper;
        this.step = step;
        this.result = result;
    }

    // Геттеры и сеттеры (сеттеры также проверяют диапазон)
    public double getLower() { return lower; }
    public void setLower(double lower) throws InvalidRangeException {
        validateRange(lower);
        this.lower = lower;
    }

    public double getUpper() { return upper; }
    public void setUpper(double upper) throws InvalidRangeException {
        validateRange(upper);
        this.upper = upper;
    }

    public double getStep() { return step; }
    public void setStep(double step) throws InvalidRangeException {
        validateRange(step);
        this.step = step;
    }

    public Double getResult() { return result; }
    public void setResult(Double result) { this.result = result; }

    // Метод вычисления интеграла (без изменений)
    public double integrateSqrt(double step, double lp, double hp) {
        if (lp < 0 || hp < 0) {
            throw new IllegalArgumentException("Границы интегрирования не могут быть отрицательными");
        }
        if (step <= 0) {
            throw new IllegalArgumentException("Шаг должен быть положительным");
        }

        boolean reversed = lp > hp;
        if (reversed) {
            double tmp = lp;
            lp = hp;
            hp = tmp;
        }

        double sum = 0.0;
        double x = lp;
        while (x < hp) {
            double nextX = Math.min(x + step, hp);
            sum += Math.sqrt(x) * (nextX - x);
            x = nextX;
        }

        return reversed ? -sum : sum;
    }
}