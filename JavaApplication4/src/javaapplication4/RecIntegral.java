/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication4;

/**
 *
 * @author alksejvidaev
 */
public class RecIntegral {
    private double lower;
    private double upper;
    private double step;
    private Double result;   // Используем Double, чтобы можно было хранить null (не вычислено)

    public RecIntegral(double lower, double upper, double step) {
        this.lower = lower;
        this.upper = upper;
        this.step = step;
        this.result = null;   // результат ещё не вычислен
    }

    public RecIntegral(double lower, double upper, double step, double result) {
        this.lower = lower;
        this.upper = upper;
        this.step = step;
        this.result = result;
    }

    public double getLower() { return lower; }
    public void setLower(double lower) { this.lower = lower; }

    public double getUpper() { return upper; }
    public void setUpper(double upper) { this.upper = upper; }

    public double getStep() { return step; }
    public void setStep(double step) { this.step = step; }

    public Double getResult() { return result; }
    public void setResult(Double result) { this.result = result; }
    
    public double integrateSqrt(double step,double lp,double hp){
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