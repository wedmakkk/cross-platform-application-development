package javaapplication4;

import java.io.Serializable;

public class ComputationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final double lower;
    private final double upper;
    private final double step;

    public ComputationRequest(double lower, double upper, double step) {
        this.lower = lower;
        this.upper = upper;
        this.step = step;
    }

    public double getLower() {
        return lower;
    }

    public double getUpper() {
        return upper;
    }

    public double getStep() {
        return step;
    }
}