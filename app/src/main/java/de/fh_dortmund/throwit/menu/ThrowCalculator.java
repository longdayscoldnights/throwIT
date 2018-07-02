package de.fh_dortmund.throwit.menu;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.interpolation.LoessInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Bijan Riesenberg
 */
public class ThrowCalculator {
    List<Pair> accel;

    public ThrowCalculator() {
        accel = new LinkedList<>();
    }

    public boolean add(Double acceleration, Double timestamp) {
        return accel.add(new Pair(acceleration, timestamp));
    }

    /**
     * Unglücklicherweise sind unbestimmte Integrale schwer zu berechnen,
     * darum werden wir die bestimmten Integrale interpolieren um eine Funktion für die Geschwindikeit zu approximieren, und dies anschließend wieder integrieren
     * @return Bestimmtes Integral der Approximierten Geschwindigkeitsfunktion über den gemessenen Zeitraum
     */
    public double calculateHeight() {

        // Keine Daten führt zum werfen von NoDataException durch Interpolator
        if(accel.size() < 4)
            return 0.0;

        // Daten aus List in die durch den Interpolator benötigten double Arrays auslesen
        double[] time = new double[accel.size()];
        double[] acceleration = new double[accel.size()];
        int count = 0;
        for(Pair p: accel) {
            time[count] = p.getTimestamp().doubleValue();
            acceleration[count] = p.getAcceleration();
        }

        // Beschleunigung(Zeit) mit Local Regression approximieren
        UnivariateInterpolator approximator = new LoessInterpolator();
        UnivariateFunction accelerationFunction = approximator.interpolate(time, acceleration);

        // Approximierte Beschleunigungsfunktion für je einen Zeitschritt bestimmt integrieren TODO nope über ganzen Raum integrieren und v(0) bestimmen
        UnivariateIntegrator integrator = new TrapezoidIntegrator();
        double[] velocity = new double[time.length];
        for(int i = 0; i < time.length; i++) {
                velocity[i] = integrator.integrate(100, accelerationFunction, i,i+1);
        }

        // Aus den bestimmten Integralen Geschwindigkeitsfunktion approximieren, dann das 2. Integral
        UnivariateFunction velocityFunction = approximator.interpolate(time, velocity);
        return integrator.integrate(100, velocityFunction, 0, accel.size());
    }



    class Pair {
        private Double acceleration;
        private Double timestamp;

        Pair(Double acceleration, Double timestamp) {
            this.acceleration = acceleration;
            this.timestamp = timestamp;
        }
        public Double getAcceleration() {
            return acceleration;
        }

        public void setAcceleration(Double acceleration) {
            this.acceleration = acceleration;
        }

        public Double getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Double timestamp) {
            this.timestamp = timestamp;
        }
    }

}
