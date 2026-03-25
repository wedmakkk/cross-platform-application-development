package javaapplication4;

import java.io.Serializable;

public class ComputationResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final double partialResult;
    private final String clientName;

    public ComputationResponse(double partialResult, String clientName) {
        this.partialResult = partialResult;
        this.clientName = clientName;
    }

    public double getPartialResult() {
        return partialResult;
    }

    public String getClientName() {
        return clientName;
    }
}