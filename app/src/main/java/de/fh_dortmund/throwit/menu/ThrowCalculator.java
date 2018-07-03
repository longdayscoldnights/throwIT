package de.fh_dortmund.throwit.menu;

import android.util.Log;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.RombergIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.util.Pair;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Bijan Riesenberg
 */
public class ThrowCalculator {
    List<Pair<Double, Long>> accel;

    public ThrowCalculator() {
        accel = new LinkedList<>();
    }

    public boolean add(Double acceleration, Long timestamp) {
        return accel.add(new Pair<>(acceleration, timestamp));
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
        for(Pair<Double, Long> p: accel) {
            time[count] = p.getSecond();
            acceleration[count] = p.getFirst();
            count++;
        }
        // Beschleunigung(Zeit) mit Local Regression approximieren

        UnivariateInterpolator approximator = new SplineInterpolator();
        UnivariateFunction accelerationFunction = approximator.interpolate(time, acceleration);

        // Approximierte Beschleunigungsfunktion für je einen Zeitschritt bestimmt integrieren TODO nope über ganzen Raum integrieren und v(0) bestimmen
        UnivariateIntegrator integrator = new RombergIntegrator();
        double[] velocity = new double[time.length];
      /*  for(int i = 0; i < time.length-1; i++) {
            velocity[i] = integrator.integrate(Integer.MAX_VALUE, accelerationFunction, i, i + 1);
            System.out.println(velocity[i]);
        }*/


        System.out.println(integrator.integrate(Integer.MAX_VALUE, accelerationFunction, 0, accel.size()-1));



        // Aus den bestimmten Integralen Geschwindigkeitsfunktion approximieren, dann das 2. Integral
        UnivariateFunction velocityFunction = approximator.interpolate(time, velocity);
        PolynomialFunction[] pf = ((PolynomialSplineFunction) velocityFunction).getPolynomials();
        for(PolynomialFunction p: pf)
            System.out.println(p);
        return integrator.integrate(Short.MAX_VALUE, velocityFunction, 0, accel.size()-1);
    }

    public List<Pair<Double, Long>> getList() {
        return accel;
    }


}
