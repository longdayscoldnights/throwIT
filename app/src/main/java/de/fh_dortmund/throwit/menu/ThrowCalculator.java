package de.fh_dortmund.throwit.menu;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;

import java.util.LinkedList;
import java.util.List;

public class ThrowCalculator {
    List<Pair> accel;

    public ThrowCalculator() {
        accel = new LinkedList<>();
    }

    public boolean add(Double acceleration, Long timestamp) {
        return accel.add(new Pair(acceleration, timestamp));
    }

    public double calculateVelocity() {
        UnivariateFunction f = new UnivariateFunction() {
            @Override
            public double value(double x) {
                return x*2;
            }
        };

        UnivariateIntegrator integrator = new TrapezoidIntegrator();
       // integrator.integrate(100, f);
        return 0;
    }





    class Pair {
        private Double acceleration;
        private Long timestamp;

        Pair(Double acceleration, Long timestamp) {
            this.acceleration = acceleration;
            this.timestamp = timestamp;
        }
        public Double getAcceleration() {
            return acceleration;
        }

        public void setAcceleration(Double acceleration) {
            this.acceleration = acceleration;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }

}
