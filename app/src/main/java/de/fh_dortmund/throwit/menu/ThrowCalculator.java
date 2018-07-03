package de.fh_dortmund.throwit.menu;

import android.util.Log;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.RombergIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.apache.commons.math3.util.Pair;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Bijan Riesenberg
 */
public class ThrowCalculator {
    List<Pair<Double, Long>> accel;
    private static final int MINSAMPLEAMOUNT= 10;
    private static final int DFTSIZE= 16; //DFSIZE ∊ 2^n

    public ThrowCalculator() {
        accel = new LinkedList<>();
    }

    public boolean add(Double acceleration, Long timestamp) {
        return accel.add(new Pair<>(acceleration, timestamp));
    }

    /**
     * Integrale sind zu schwer, darum FFT :)
     * Lang lebe Fourier!
     * @return Bestimmtes Integral der Approximierten Geschwindigkeitsfunktion über den gemessenen Zeitraum
     */
    public double calculateHeight() {
            if(accel.size() < MINSAMPLEAMOUNT)
                return 0.0;
            double result = 0.0;


            for(int k = 0; k<accel.size()/DFTSIZE;k++){
                // Daten auslesen
                double[] time = new double[DFTSIZE];
                double[] acceleration = new double[DFTSIZE];

//                System.out.println();
//                System.out.println(DFTSIZE*k);
//                System.out.println(DFTSIZE*k+16);
//                System.out.println();

                int count = 0;
                for(Pair<Double, Long> p: accel.subList(DFTSIZE*k,DFTSIZE*k+16)) {
                    time[count] = p.getSecond();
                    acceleration[count] = p.getFirst();
                    count++;
                }


                FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
                Complex[] accelFrequency = fft.transform(acceleration, TransformType.FORWARD);
                double frequencyRate = calculateSamplingRate(time);

                for(int i = 0; i < accelFrequency.length; i++)
                    accelFrequency[i] = accelFrequency[i].divide(Math.pow(frequencyRate,2));
                //System.out.println("SamplingRate: "+frequencyRate);
                Complex[] displacement = fft.transform(accelFrequency, TransformType.INVERSE);
                for(Complex d: displacement)
                    result += d.abs();

            }
        return result;

    }

     /**   // Keine Daten führt zum werfen von NoDataException durch Interpolator
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
        for(int i = 0; i < time.length-1; i++) {
            velocity[i] = integrator.integrate(Integer.MAX_VALUE, accelerationFunction, i, i + 1);
            System.out.println(velocity[i]);
        }


        System.out.println(integrator.integrate(Integer.MAX_VALUE, accelerationFunction, 0, accel.size()-1));



        // Aus den bestimmten Integralen Geschwindigkeitsfunktion approximieren, dann das 2. Integral
        UnivariateFunction velocityFunction = approximator.interpolate(time, velocity);
        PolynomialFunction[] pf = ((PolynomialSplineFunction) velocityFunction).getPolynomials();
        for(PolynomialFunction p: pf)
            System.out.println(p);
        return integrator.integrate(Short.MAX_VALUE, velocityFunction, 0, accel.size()-1);

        */


    public double calculateSamplingRate(double[] time) {
        assert time != null && time.length >= MINSAMPLEAMOUNT;
//        System.out.println(time.length);
//        System.out.println("Endtime " + time[time.length-1]);
//        System.out.println("Starttime: "+time[0]);
//        System.out.println("Zeitdauer: " + ((time[time.length-1]-time[0])/1000000000));
        return time.length/((time[time.length-1]-time[0])/1000000000);
    }


    public List<Pair<Double, Long>> getList() {
        return accel;
    }


}
