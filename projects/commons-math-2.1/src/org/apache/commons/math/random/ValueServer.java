/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.math.random;

import org.apache.commons.math.MathRuntimeException;

/**
 * Generates values for use in simulation applications.
 * <p>
 * How values are generated is determined by the <code>mode</code>
 * property.</p>
 * <p>
 * Supported <code>mode</code> values are: <ul>
 * <li> DIGEST_MODE -- uses an empirical distribution </li>
 * <li> REPLAY_MODE -- replays data from <code>valuesFileURL</code></li>
 * <li> UNIFORM_MODE -- generates uniformly distributed random values with
 *                      mean = <code>mu</code> </li>
 * <li> EXPONENTIAL_MODE -- generates exponentially distributed random values
 *                         with mean = <code>mu</code></li>
 * <li> GAUSSIAN_MODE -- generates Gaussian distributed random values with
 *                       mean = <code>mu</code> and
 *                       standard deviation = <code>sigma</code></li>
 * <li> CONSTANT_MODE -- returns <code>mu</code> every time.</li></ul></p>
 *
 * @version $Revision: 811827 $ $Date: 2009-09-06 11:32:50 -0400 (Sun, 06 Sep 2009) $
 *
 */
public class ValueServer {

    /** Use empirical distribution.  */
    public static final int DIGEST_MODE = 0;

    /** Uniform random deviates with mean = &mu;. */
    public static final int UNIFORM_MODE = 2;

    /** Exponential random deviates with mean = &mu;. */
    public static final int EXPONENTIAL_MODE = 3;

    /** Gaussian random deviates with mean = &mu;, std dev = &sigma;. */
    public static final int GAUSSIAN_MODE = 4;

    /** Always return mu */
    public static final int CONSTANT_MODE = 5;

    /** mode determines how values are generated. */
    private int mode = 5;

    /** Mean for use with non-data-driven modes. */
    private double mu = 0.0;

    /** Standard deviation for use with GAUSSIAN_MODE. */
    private double sigma = 0.0;

    /** Empirical probability distribution for use with DIGEST_MODE. */
    private EmpiricalDistribution empiricalDistribution = null;

    /** RandomDataImpl to use for random data generation. */
    private RandomData randomData = new RandomDataImpl();

    // Data generation modes ======================================

    /** Creates new ValueServer */
    public ValueServer() {
    }

    /**
     * Construct a ValueServer instance using a RandomData as its source
     * of random data.
     *
     * @param randomData the RandomData instance used to source random data
     * @since 1.1
     */
    public ValueServer(RandomData randomData) {
        this.randomData = randomData;
    }

    /**
     * Returns the next generated value, generated according
     * to the mode value (see MODE constants).
     *
     * @return generated value
     */
    public double getNext() {
        switch (mode) {
            case DIGEST_MODE: return getNextDigest();
            case UNIFORM_MODE: return getNextUniform();
            case EXPONENTIAL_MODE: return getNextExponential();
            case GAUSSIAN_MODE: return getNextGaussian();
            case CONSTANT_MODE: return mu;
            default: throw MathRuntimeException.createIllegalStateException(
                    "unknown mode {0}, known modes: " +
                    "{1} ({2}), {3} ({4}), {5} ({6}), {7} ({8}), and {9} ({10})",
                    mode,
                    "DIGEST_MODE",   DIGEST_MODE,
                    "UNIFORM_MODE",  UNIFORM_MODE,  "EXPONENTIAL_MODE", EXPONENTIAL_MODE,
                    "GAUSSIAN_MODE", GAUSSIAN_MODE, "CONSTANT_MODE",    CONSTANT_MODE);
        }
    }

    /**
     * Fills the input array with values generated using getNext() repeatedly.
     *
     * @param values array to be filled
     */
    public void fill(double[] values) {
        for (int i = 0; i < values.length; i++) {
            values[i] = getNext();
        }
    }

    /**
     * Returns an array of length <code>length</code> with values generated
     * using getNext() repeatedly.
     *
     * @param length length of output array
     * @return array of generated values
     */
    public double[] fill(int length) {
        double[] out = new double[length];
        for (int i = 0; i < length; i++) {
            out[i] = getNext();
        }
        return out;
    }

    /**
     * Computes the empirical distribution using values from the file
     * in <code>valuesFileURL</code>, using the default number of bins.
     * <p>
     * <code>valuesFileURL</code> must exist and be
     * readable by *this at runtime.</p>
     * <p>
     * This method must be called before using <code>getNext()</code>
     * with <code>mode = DIGEST_MODE</code></p>
     */
    public void computeDistribution() {
        empiricalDistribution = new EmpiricalDistributionImpl();
    }

    /**
     * Computes the empirical distribution using values from the file
     * in <code>valuesFileURL</code> and <code>binCount</code> bins.
     * <p>
     * <code>valuesFileURL</code> must exist and be readable by this process
     * at runtime.</p>
     * <p>
     * This method must be called before using <code>getNext()</code>
     * with <code>mode = DIGEST_MODE</code></p>
     *
     * @param binCount the number of bins used in computing the empirical
     * distribution
     */
    public void computeDistribution(int binCount) {
        empiricalDistribution = new EmpiricalDistributionImpl(binCount);
        mu = empiricalDistribution.getSampleStats().getMean();
        sigma = empiricalDistribution.getSampleStats().getStandardDeviation();
    }

    /** Getter for property mode.
     * @return Value of property mode.
     */
    public int getMode() {
        return mode;
    }

    /** Setter for property mode.
     * @param mode New value of property mode.
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /** Getter for property empiricalDistribution.
     * @return Value of property empiricalDistribution.
     */
    public EmpiricalDistribution getEmpiricalDistribution() {
        return empiricalDistribution;
    }

    /** Getter for property mu.
     * @return Value of property mu.
     */
    public double getMu() {
        return mu;
    }

    /** Setter for property mu.
     * @param mu New value of property mu.
     */
    public void setMu(double mu) {
        this.mu = mu;
    }

    /** Getter for property sigma.
     * @return Value of property sigma.
     */
    public double getSigma() {
        return sigma;
    }

    /** Setter for property sigma.
     * @param sigma New value of property sigma.
     */
    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    //------------- private methods ---------------------------------

    /**
     * Gets a random value in DIGEST_MODE.
     * <p>
     * <strong>Preconditions</strong>: <ul>
     * <li>Before this method is called, <code>computeDistribution()</code>
     * must have completed successfully; otherwise an
     * <code>IllegalStateException</code> will be thrown</li></ul></p>
     *
     * @return next random value from the empirical distribution digest
     */
    private double getNextDigest() {
        if ((empiricalDistribution == null) ||
            (empiricalDistribution.getBinStats().size() == 0)) {
            throw MathRuntimeException.createIllegalStateException("digest not initialized");
        }
        return empiricalDistribution.getNextValue();
    }

    /**
     * Gets a uniformly distributed random value with mean = mu.
     *
     * @return random uniform value
     */
    private double getNextUniform() {
        return randomData.nextUniform(0, 2 * mu);
    }

    /**
     * Gets an exponentially distributed random value with mean = mu.
     *
     * @return random exponential value
     */
    private double getNextExponential() {
        return randomData.nextExponential(mu);
    }

    /**
     * Gets a Gaussian distributed random value with mean = mu
     * and standard deviation = sigma.
     *
     * @return random Gaussian value
     */
    private double getNextGaussian() {
        return randomData.nextGaussian(mu, sigma);
    }

    public void testful_fill(tful.arrays.doubleArray p0) { fill( p0.toArray() );}
    public tful.arrays.doubleArray testful_fill(int p0) { return new tful.arrays.doubleArray(fill( p0));}
}
