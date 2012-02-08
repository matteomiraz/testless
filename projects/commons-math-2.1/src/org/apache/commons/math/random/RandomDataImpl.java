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

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.math.MathRuntimeException;
import org.apache.commons.math.util.MathUtils;

/**
 * Implements the {@link RandomData} interface using a {@link RandomGenerator}
 * instance to provide data for the <code>nextSecureXxx</code> methods. If no
 * <code>RandomGenerator</code> is provided in the constructor, the default is
 * to use a generator based on {@link java.util.Random}. To plug in a different
 * implementation, either implement <code>RandomGenerator</code> directly or
 * extend {@link AbstractRandomGenerator}.
 * <p>
 * Supports reseeding the underlying pseudo-random number generator (PRNG). The
 * <code>SecurityProvider</code> and <code>Algorithm</code> used by the
 * <code>SecureRandom</code> instance can also be reset.
 * </p>
 * <p>
 * For details on the default PRNGs, see {@link java.util.Random} and
 * </p>
 * <p>
 * <strong>Usage Notes</strong>:
 * <ul>
 * <li>
 * Instance variables are used to maintain <code>RandomGenerator</code> and
 * <code>SecureRandom</code> instances used in data generation. Therefore, to
 * generate a random sequence of values or strings, you should use just
 * <strong>one</strong> <code>RandomDataImpl</code> instance repeatedly.</li>
 * <li>
 * The "secure" methods are *much* slower. These should be used only when a
 * cryptographically secure random sequence is required. A secure random
 * sequence is a sequence of pseudo-random values which, in addition to being
 * well-dispersed (so no subsequence of values is an any more likely than other
 * subsequence of the the same length), also has the additional property that
 * knowledge of values generated up to any point in the sequence does not make
 * it any easier to predict subsequent values.</li>
 * <li>
 * When a new <code>RandomDataImpl</code> is created, the underlying random
 * number generators are <strong>not</strong> intialized. If you do not
 * explicitly seed the default non-secure generator, it is seeded with the
 * current time in milliseconds on first use. The same holds for the secure
 * generator. If you provide a <code>RandomGenerator</code> to the constructor,
 * however, this generator is not reseeded by the constructor nor is it reseeded
 * on first use.</li>
 * <li>
 * The <code>reSeed</code> and <code>reSeedSecure</code> methods delegate to the
 * corresponding methods on the underlying <code>RandomGenerator</code> and
 * <code>SecureRandom</code> instances. Therefore, <code>reSeed(long)</code>
 * fully resets the initial state of the non-secure random number generator (so
 * that reseeding with a specific value always results in the same subsequent
 * random sequence); whereas reSeedSecure(long) does <strong>not</strong>
 * reinitialize the secure random number generator (so secure sequences started
 * with calls to reseedSecure(long) won't be identical).</li>
 * <li>
 * This implementation is not synchronized.
 * </ul>
 * </p>
 *
 * @version $Revision: 831510 $ $Date: 2009-10-30 22:30:18 -0400 (Fri, 30 Oct 2009) $
 */
public class RandomDataImpl implements RandomData, Serializable {

    /** Serializable version identifier */
    private static final long serialVersionUID = -626730818244969716L;

    /** underlying random number generator */
    private RandomGenerator rand = null;

    /**
     * Construct a RandomDataImpl.
     */
    public RandomDataImpl() {
    }

    /**
     * Construct a RandomDataImpl using the supplied {@link RandomGenerator} as
     * the source of (non-secure) random data.
     *
     * @param rand
     *            the source of (non-secure) random data
     * @since 1.1
     */
    public RandomDataImpl(RandomGenerator rand) {
        super();
        this.rand = rand;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <strong>Algorithm Description:</strong> hex strings are generated using a
     * 2-step process.
     * <ol>
     * <li>
     * len/2+1 binary bytes are generated using the underlying Random</li>
     * <li>
     * Each binary byte is translated into 2 hex digits</li>
     * </ol>
     * </p>
     *
     * @param len
     *            the desired string length.
     * @return the random string.
     */
    public String nextHexString(int len) {
        if (len <= 0) {
            throw MathRuntimeException.createIllegalArgumentException(
                  "length must be positive ({0})", len);
        }

        // Get a random number generator
        RandomGenerator ran = getRan();

        // Initialize output buffer
        StringBuffer outBuffer = new StringBuffer();

        // Get int(len/2)+1 random bytes
        byte[] randomBytes = new byte[(len / 2) + 1];
        ran.nextBytes(randomBytes);

        // Convert each byte to 2 hex digits
        for (int i = 0; i < randomBytes.length; i++) {
            Integer c = Integer.valueOf(randomBytes[i]);

            /*
             * Add 128 to byte value to make interval 0-255 before doing hex
             * conversion. This guarantees <= 2 hex digits from toHexString()
             * toHexString would otherwise add 2^32 to negative arguments.
             */
            String hex = Integer.toHexString(c.intValue() + 128);

            // Make sure we add 2 hex digits for each byte
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            outBuffer.append(hex);
        }
        return outBuffer.toString().substring(0, len);
    }

    /**
     * Generate a random int value uniformly distributed between
     * <code>lower</code> and <code>upper</code>, inclusive.
     *
     * @param lower
     *            the lower bound.
     * @param upper
     *            the upper bound.
     * @return the random integer.
     */
    public int nextInt(int lower, int upper) {
        if (lower >= upper) {
            throw MathRuntimeException.createIllegalArgumentException(
                    "upper bound ({0}) must be greater than lower bound ({1})",
                    upper, lower);
        }
        double r = getRan().nextDouble();
        return (int) ((r * upper) + ((1.0 - r) * lower) + r);
    }

    /**
     * Generate a random long value uniformly distributed between
     * <code>lower</code> and <code>upper</code>, inclusive.
     *
     * @param lower
     *            the lower bound.
     * @param upper
     *            the upper bound.
     * @return the random integer.
     */
    public long nextLong(long lower, long upper) {
        if (lower >= upper) {
            throw MathRuntimeException.createIllegalArgumentException(
                  "upper bound ({0}) must be greater than lower bound ({1})",
                  upper, lower);
        }
        double r = getRan().nextDouble();
        return (long) ((r * upper) + ((1.0 - r) * lower) + r);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <strong>Algorithm Description</strong>:
     * <ul><li> For small means, uses simulation of a Poisson process
     * using Uniform deviates, as described
     * <a href="http://irmi.epfl.ch/cmos/Pmmi/interactive/rng7.htm"> here.</a>
     * The Poisson process (and hence value returned) is bounded by 1000 * mean.</li>
     *
     * <li> For large means, uses the rejection algorithm described in <br/>
     * Devroye, Luc. (1981).<i>The Computer Generation of Poisson Random Variables</i>
     * <strong>Computing</strong> vol. 26 pp. 197-207.</li></ul></p>
     *
     * @param mean mean of the Poisson distribution.
     * @return the random Poisson value.
     */
    public long nextPoisson(double mean) {
        if (mean <= 0) {
            throw MathRuntimeException.createIllegalArgumentException(
                  "the Poisson mean must be positive ({0})", mean);
        }

        final RandomGenerator generator = getRan();

        final double pivot = 40.0d;
        if (mean < pivot) {
            double p = Math.exp(-mean);
            long n = 0;
            double r = 1.0d;
            double rnd = 1.0d;

            while (n < 1000 * mean) {
                rnd = generator.nextDouble();
                r = r * rnd;
                if (r >= p) {
                    n++;
                } else {
                    return n;
                }
            }
            return n;
        } else {
            final double lambda = Math.floor(mean);
            final double lambdaFractional = mean - lambda;
            final double logLambda = Math.log(lambda);
            final double logLambdaFactorial = MathUtils.factorialLog((int) lambda);
            final long y2 = lambdaFractional < Double.MIN_VALUE ? 0 : nextPoisson(lambdaFractional);
            final double delta = Math.sqrt(lambda * Math.log(32 * lambda / Math.PI + 1));
            final double halfDelta = delta / 2;
            final double twolpd = 2 * lambda + delta;
            final double a1 = Math.sqrt(Math.PI * twolpd) * Math.exp(1 / 8 * lambda);
            final double a2 = (twolpd / delta) * Math.exp(-delta * (1 + delta) / twolpd);
            final double aSum = a1 + a2 + 1;
            final double p1 = a1 / aSum;
            final double p2 = a2 / aSum;
            final double c1 = 1 / (8 * lambda);

            double x = 0;
            double y = 0;
            double v = 0;
            int a = 0;
            double t = 0;
            double qr = 0;
            double qa = 0;
            for (;;) {
                final double u = nextUniform(0.0, 1);
                if (u <= p1) {
                    final double n = nextGaussian(0d, 1d);
                    x = n * Math.sqrt(lambda + halfDelta) - 0.5d;
                    if (x > delta || x < -lambda) {
                        continue;
                    }
                    y = x < 0 ? Math.floor(x) : Math.ceil(x);
                    final double e = nextExponential(1d);
                    v = -e - (n * n / 2) + c1;
                } else {
                    if (u > p1 + p2) {
                        y = lambda;
                        break;
                    } else {
                        x = delta + (twolpd / delta) * nextExponential(1d);
                        y = Math.ceil(x);
                        v = -nextExponential(1d) - delta * (x + 1) / twolpd;
                    }
                }
                a = x < 0 ? 1 : 0;
                t = y * (y + 1) / (2 * lambda);
                if (v < -t && a == 0) {
                    y = lambda + y;
                    break;
                }
                qr = t * ((2 * y + 1) / (6 * lambda) - 1);
                qa = qr - (t * t) / (3 * (lambda + a * (y + 1)));
                if (v < qa) {
                    y = lambda + y;
                    break;
                }
                if (v > qr) {
                    continue;
                }
                if (v < y * logLambda - MathUtils.factorialLog((int) (y + lambda)) + logLambdaFactorial) {
                    y = lambda + y;
                    break;
                }
            }
            return y2 + (long) y;
        }
    }

    /**
     * Generate a random value from a Normal (a.k.a. Gaussian) distribution with
     * the given mean, <code>mu</code> and the given standard deviation,
     * <code>sigma</code>.
     *
     * @param mu
     *            the mean of the distribution
     * @param sigma
     *            the standard deviation of the distribution
     * @return the random Normal value
     */
    public double nextGaussian(double mu, double sigma) {
        if (sigma <= 0) {
            throw MathRuntimeException.createIllegalArgumentException(
                  "standard deviation must be positive ({0})", sigma);
        }
        return sigma * getRan().nextGaussian() + mu;
    }

    /**
     * Returns a random value from an Exponential distribution with the given
     * mean.
     * <p>
     * <strong>Algorithm Description</strong>: Uses the <a
     * href="http://www.jesus.ox.ac.uk/~clifford/a5/chap1/node5.html"> Inversion
     * Method</a> to generate exponentially distributed random values from
     * uniform deviates.
     * </p>
     *
     * @param mean the mean of the distribution
     * @return the random Exponential value
     */
    public double nextExponential(double mean) {
        if (mean <= 0.0) {
            throw MathRuntimeException.createIllegalArgumentException(
                  "mean must be positive ({0})", mean);
        }
        final RandomGenerator generator = getRan();
        double unif = generator.nextDouble();
        while (unif == 0.0d) {
            unif = generator.nextDouble();
        }
        return -mean * Math.log(unif);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <strong>Algorithm Description</strong>: scales the output of
     * Random.nextDouble(), but rejects 0 values (i.e., will generate another
     * random double if Random.nextDouble() returns 0). This is necessary to
     * provide a symmetric output interval (both endpoints excluded).
     * </p>
     *
     * @param lower
     *            the lower bound.
     * @param upper
     *            the upper bound.
     * @return a uniformly distributed random value from the interval (lower,
     *         upper)
     */
    public double nextUniform(double lower, double upper) {
        if (lower >= upper) {
            throw MathRuntimeException.createIllegalArgumentException(
                  "upper bound ({0}) must be greater than lower bound ({1})",
                  upper, lower);
        }
        final RandomGenerator generator = getRan();

        // ensure nextDouble() isn't 0.0
        double u = generator.nextDouble();
        while (u <= 0.0) {
            u = generator.nextDouble();
        }

        return lower + u * (upper - lower);
    }

    /**
     * Returns the RandomGenerator used to generate non-secure random data.
     * <p>
     * Creates and initializes a default generator if null.
     * </p>
     *
     * @return the Random used to generate random data
     * @since 1.1
     */
    private RandomGenerator getRan() {
        if (rand == null) {
            rand = new JDKRandomGenerator();
            rand.setSeed(System.currentTimeMillis());
        }
        return rand;
    }

    /**
     * Reseeds the random number generator with the supplied seed.
     * <p>
     * Will create and initialize if null.
     * </p>
     *
     * @param seed
     *            the seed value to use
     */
    public void reSeed(long seed) {
        if (rand == null) {
            rand = new JDKRandomGenerator();
        }
        rand.setSeed(seed);
    }

    /**
     * Reseeds the random number generator with the current time in
     * milliseconds.
     */
    public void reSeed() {
        if (rand == null) {
            rand = new JDKRandomGenerator();
        }
        rand.setSeed(System.currentTimeMillis());
    }

    /**
     * Generates an integer array of length <code>k</code> whose entries are
     * selected randomly, without repetition, from the integers
     * <code>0 through n-1</code> (inclusive).
     * <p>
     * Generated arrays represent permutations of <code>n</code> taken
     * <code>k</code> at a time.
     * </p>
     * <p>
     * <strong>Preconditions:</strong>
     * <ul>
     * <li> <code>k <= n</code></li>
     * <li> <code>n > 0</code></li>
     * </ul>
     * If the preconditions are not met, an IllegalArgumentException is thrown.
     * </p>
     * <p>
     * Uses a 2-cycle permutation shuffle. The shuffling process is described <a
     * href="http://www.maths.abdn.ac.uk/~igc/tch/mx4002/notes/node83.html">
     * here</a>.
     * </p>
     *
     * @param n
     *            domain of the permutation (must be positive)
     * @param k
     *            size of the permutation (must satisfy 0 < k <= n).
     * @return the random permutation as an int array
     */
    public int[] nextPermutation(int n, int k) {
        if (k > n) {
            throw MathRuntimeException.createIllegalArgumentException(
                  "permutation k ({0}) exceeds n ({1})", k, n);
        }
        if (k == 0) {
            throw MathRuntimeException.createIllegalArgumentException(
                  "permutation k ({0}) must be positive", k);
        }

        int[] index = getNatural(n);
        shuffle(index, n - k);
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = index[n - i - 1];
        }

        return result;
    }

    /**
     * Uses a 2-cycle permutation shuffle to generate a random permutation.
     * <strong>Algorithm Description</strong>: Uses a 2-cycle permutation
     * shuffle to generate a random permutation of <code>c.size()</code> and
     * then returns the elements whose indexes correspond to the elements of the
     * generated permutation. This technique is described, and proven to
     * generate random samples, <a
     * href="http://www.maths.abdn.ac.uk/~igc/tch/mx4002/notes/node83.html">
     * here</a>
     *
     * @param c
     *            Collection to sample from.
     * @param k
     *            sample size.
     * @return the random sample.
     */
    public Object[] nextSample(Collection<?> c, int k) {
        int len = c.size();
        if (k > len) {
            throw MathRuntimeException.createIllegalArgumentException(
                  "sample size ({0}) exceeds collection size ({1})");
        }
        if (k <= 0) {
            throw MathRuntimeException.createIllegalArgumentException(
                  "sample size must be positive ({0})", k);
        }

        Object[] objects = c.toArray();
        int[] index = nextPermutation(len, k);
        Object[] result = new Object[k];
        for (int i = 0; i < k; i++) {
            result[i] = objects[index[i]];
        }
        return result;
    }

    // ------------------------Private methods----------------------------------

    /**
     * Uses a 2-cycle permutation shuffle to randomly re-order the last elements
     * of list.
     *
     * @param list
     *            list to be shuffled
     * @param end
     *            element past which shuffling begins
     */
    private void shuffle(int[] list, int end) {
        int target = 0;
        for (int i = list.length - 1; i >= end; i--) {
            if (i == 0) {
                target = 0;
            } else {
                target = nextInt(0, i);
            }
            int temp = list[target];
            list[target] = list[i];
            list[i] = temp;
        }
    }

    /**
     * Returns an array representing n.
     *
     * @param n
     *            the natural number to represent
     * @return array with entries = elements of n
     */
    private int[] getNatural(int n) {
        int[] natural = new int[n];
        for (int i = 0; i < n; i++) {
            natural[i] = i;
        }
        return natural;
    }

    public tful.arrays.intArray testful_nextPermutation(int p0, int p1)  { return new tful.arrays.intArray(nextPermutation( p0, p1));}
    @SuppressWarnings("rawtypes")
	public tful.arrays.ObjectArray testful_nextSample(java.util.Collection p0, int p1)  { return new tful.arrays.ObjectArray(nextSample( p0, p1));}
}
