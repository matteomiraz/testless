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
package org.apache.commons.math.stat.descriptive.summary;

import java.io.Serializable;

import org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.apache.commons.math.stat.descriptive.WeightedEvaluation;

/**
 * Returns the product of the available values.
 * <p>
 * If there are no values in the dataset, or any of the values are
 * <code>NaN</code>, then <code>NaN</code> is returned.</p>
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If
 * multiple threads access an instance of this class concurrently, and at least
 * one of the threads invokes the <code>increment()</code> or
 * <code>clear()</code> method, it must be synchronized externally.</p>
 *
 * @version $Revision: 917270 $ $Date: 2010-02-28 14:37:53 -0500 (Sun, 28 Feb 2010) $
 */
public class Product extends AbstractStorelessUnivariateStatistic implements Serializable, WeightedEvaluation {

    /** Serializable version identifier */
    private static final long serialVersionUID = 2824226005990582538L;

    /**The number of values that have been added */
    private long n;

    /**
     * The current Running Product.
     */
    private double value;

    /**
     * Create a Product instance
     */
    public Product() {
        n = 0;
        value = Double.NaN;
    }

    /**
     * Copy constructor, creates a new {@code Product} identical
     * to the {@code original}
     *
     * @param original the {@code Product} instance to copy
     */
    public Product(Product original) {
        copy(original, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increment(final double d) {
        if (n == 0) {
            value = d;
        } else {
            value *= d;
        }
        n++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getResult() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    public long getN() {
        return n;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        value = Double.NaN;
        n = 0;
    }

    /**
     * Returns the product of the entries in the specified portion of
     * the input array, or <code>Double.NaN</code> if the designated subarray
     * is empty.
     * <p>
     * Throws <code>IllegalArgumentException</code> if the array is null.</p>
     *
     * @param values the input array
     * @param begin index of the first array element to include
     * @param length the number of elements to include
     * @return the product of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException if the array is null or the array index
     *  parameters are not valid
     */
    @Override
    public double evaluate(final double[] values, final int begin, final int length) {
        double product = Double.NaN;
        if (test(values, begin, length)) {
            product = 1.0;
            for (int i = begin; i < begin + length; i++) {
                product *= values[i];
            }
        }
        return product;
    }

    /**
     * <p>Returns the weighted product of the entries in the specified portion of
     * the input array, or <code>Double.NaN</code> if the designated subarray
     * is empty.</p>
     *
     * <p>Throws <code>IllegalArgumentException</code> if any of the following are true:
     * <ul><li>the values array is null</li>
     *     <li>the weights array is null</li>
     *     <li>the weights array does not have the same length as the values array</li>
     *     <li>the weights array contains one or more infinite values</li>
     *     <li>the weights array contains one or more NaN values</li>
     *     <li>the weights array contains negative values</li>
     *     <li>the start and length arguments do not determine a valid array</li>
     * </ul></p>
     *
     * <p>Uses the formula, <pre>
     *    weighted product = &prod;values[i]<sup>weights[i]</sup>
     * </pre>
     * that is, the weights are applied as exponents when computing the weighted product.</p>
     *
     * @param values the input array
     * @param weights the weights array
     * @param begin index of the first array element to include
     * @param length the number of elements to include
     * @return the product of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException if the parameters are not valid
     * @since 2.1
     */
    public double evaluate(final double[] values, final double[] weights,
                           final int begin, final int length) {
        double product = Double.NaN;
        if (test(values, weights, begin, length)) {
            product = 1.0;
            for (int i = begin; i < begin + length; i++) {
                product *= Math.pow(values[i], weights[i]);
            }
        }
        return product;
    }

    /**
     * <p>Returns the weighted product of the entries in the input array.</p>
     *
     * <p>Throws <code>IllegalArgumentException</code> if any of the following are true:
     * <ul><li>the values array is null</li>
     *     <li>the weights array is null</li>
     *     <li>the weights array does not have the same length as the values array</li>
     *     <li>the weights array contains one or more infinite values</li>
     *     <li>the weights array contains one or more NaN values</li>
     *     <li>the weights array contains negative values</li>
     * </ul></p>
     *
     * <p>Uses the formula, <pre>
     *    weighted product = &prod;values[i]<sup>weights[i]</sup>
     * </pre>
     * that is, the weights are applied as exponents when computing the weighted product.</p>
     *
     * @param values the input array
     * @param weights the weights array
     * @return the product of the values or Double.NaN if length = 0
     * @throws IllegalArgumentException if the parameters are not valid
     * @since 2.1
     */
    public double evaluate(final double[] values, final double[] weights) {
        return evaluate(values, weights, 0, values.length);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Product copy() {
        Product result = new Product();
        copy(this, result);
        return result;
    }

    /**
     * Copies source to dest.
     * <p>Neither source nor dest can be null.</p>
     *
     * @param source Product to copy
     * @param dest Product to copy to
     * @throws NullPointerException if either source or dest is null
     */
    public static void copy(Product source, Product dest) {
        dest.n = source.n;
        dest.value = source.value;
    }

    public double testful_evaluate(tful.arrays.doubleArray p0, int p1, int p2)  { return evaluate( p0.toArray() , p1, p2);}
    public double testful_evaluate(tful.arrays.doubleArray p0, tful.arrays.doubleArray p1, int p2, int p3)  { return evaluate( p0.toArray() , p1.toArray() , p2, p3);}
    public double testful_evaluate(tful.arrays.doubleArray p0, tful.arrays.doubleArray p1)  { return evaluate( p0.toArray() , p1.toArray() );}

}
