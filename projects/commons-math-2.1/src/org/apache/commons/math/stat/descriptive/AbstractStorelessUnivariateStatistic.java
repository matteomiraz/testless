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
package org.apache.commons.math.stat.descriptive;

import org.apache.commons.math.MathRuntimeException;
import org.apache.commons.math.util.MathUtils;

/**
 *
 * Abstract implementation of the {@link StorelessUnivariateStatistic} interface.
 * <p>
 * Provides default <code>evaluate()</code> and <code>incrementAll(double[])<code>
 * implementations.</p>
 * <p>
 * <strong>Note that these implementations are not synchronized.</strong></p>
 *
 * @version $Revision: 811833 $ $Date: 2009-09-06 12:27:50 -0400 (Sun, 06 Sep 2009) $
 */
public abstract class AbstractStorelessUnivariateStatistic
    extends AbstractUnivariateStatistic
    implements StorelessUnivariateStatistic {

    /**
     * This default implementation calls {@link #clear}, then invokes
     * {@link #increment} in a loop over the the input array, and then uses
     * {@link #getResult} to compute the return value.
     * <p>
     * Note that this implementation changes the internal state of the
     * statistic.  Its side effects are the same as invoking {@link #clear} and
     * then {@link #incrementAll(double[])}.</p>
     * <p>
     * Implementations may override this method with a more efficient and
     * possibly more accurate implementation that works directly with the
     * input array.</p>
     * <p>
     * If the array is null, an IllegalArgumentException is thrown.</p>
     * @param values input array
     * @return the value of the statistic applied to the input array
     * @see org.apache.commons.math.stat.descriptive.UnivariateStatistic#evaluate(double[])
     */
    @Override
    public double evaluate(final double[] values) {
        if (values == null) {
            throw MathRuntimeException.createIllegalArgumentException("input values array is null");
        }
        return evaluate(values, 0, values.length);
    }

    /**
     * This default implementation calls {@link #clear}, then invokes
     * {@link #increment} in a loop over the specified portion of the input
     * array, and then uses {@link #getResult} to compute the return value.
     * <p>
     * Note that this implementation changes the internal state of the
     * statistic.  Its side effects are the same as invoking {@link #clear} and
     * then {@link #incrementAll(double[], int, int)}.</p>
     * <p>
     * Implementations may override this method with a more efficient and
     * possibly more accurate implementation that works directly with the
     * input array.</p>
     * <p>
     * If the array is null or the index parameters are not valid, an
     * IllegalArgumentException is thrown.</p>
     * @param values the input array
     * @param begin the index of the first element to include
     * @param length the number of elements to include
     * @return the value of the statistic applied to the included array entries
     * @see org.apache.commons.math.stat.descriptive.UnivariateStatistic#evaluate(double[], int, int)
     */
    @Override
    public double evaluate(final double[] values, final int begin, final int length) {
        if (test(values, begin, length)) {
            clear();
            incrementAll(values, begin, length);
        }
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract StorelessUnivariateStatistic copy();

    /**
     * {@inheritDoc}
     */
    public abstract void clear();

    /**
     * {@inheritDoc}
     */
    public abstract double getResult();

    /**
     * {@inheritDoc}
     */
    public abstract void increment(final double d);

    /**
     * This default implementation just calls {@link #increment} in a loop over
     * the input array.
     * <p>
     * Throws IllegalArgumentException if the input values array is null.</p>
     *
     * @param values values to add
     * @throws IllegalArgumentException if values is null
     * @see org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic#incrementAll(double[])
     */
    public void incrementAll(double[] values) {
        if (values == null) {
            throw MathRuntimeException.createIllegalArgumentException("input values array is null");
        }
        incrementAll(values, 0, values.length);
    }

    /**
     * This default implementation just calls {@link #increment} in a loop over
     * the specified portion of the input array.
     * <p>
     * Throws IllegalArgumentException if the input values array is null.</p>
     *
     * @param values  array holding values to add
     * @param begin   index of the first array element to add
     * @param length  number of array elements to add
     * @throws IllegalArgumentException if values is null
     * @see org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic#incrementAll(double[], int, int)
     */
    public void incrementAll(double[] values, int begin, int length) {
        if (test(values, begin, length)) {
            int k = begin + length;
            for (int i = begin; i < k; i++) {
                increment(values[i]);
            }
        }
    }

    /**
     * Returns true iff <code>object</code> is an
     * <code>AbstractStorelessUnivariateStatistic</code> returning the same
     * values as this for <code>getResult()</code> and <code>getN()</code>
     * @param object object to test equality against.
     * @return true if object returns the same value as this
     */
    @Override
    public boolean equals(Object object) {
        if (object == this ) {
            return true;
        }
       if (object instanceof AbstractStorelessUnivariateStatistic == false) {
            return false;
        }
        AbstractStorelessUnivariateStatistic stat = (AbstractStorelessUnivariateStatistic) object;
        return MathUtils.equals(stat.getResult(), this.getResult()) &&
               MathUtils.equals(stat.getN(), this.getN());
    }

    /**
     * Returns hash code based on getResult() and getN()
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return 31* (31 + MathUtils.hash(getResult())) + MathUtils.hash(getN());
    }

    public double testful_evaluate(tful.arrays.doubleArray p0)  { return evaluate( p0.toArray() );}
    public double testful_evaluate(tful.arrays.doubleArray p0, int p1, int p2)  { return evaluate( p0.toArray() , p1, p2);}
    public void testful_incrementAll(tful.arrays.doubleArray p0)  { incrementAll( p0.toArray() );}
    public void testful_incrementAll(tful.arrays.doubleArray p0, int p1, int p2)  { incrementAll( p0.toArray() , p1, p2);}


}
