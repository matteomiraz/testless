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

import org.apache.commons.math.DimensionMismatchException;
import org.apache.commons.math.linear.RealMatrix;

/**
 * Implementation of
 * {@link org.apache.commons.math.stat.descriptive.MultivariateSummaryStatistics} that
 * is safe to use in a multithreaded environment.  Multiple threads can safely
 * operate on a single instance without causing runtime exceptions due to race
 * conditions.  In effect, this implementation makes modification and access
 * methods atomic operations for a single instance.  That is to say, as one
 * thread is computing a statistic from the instance, no other thread can modify
 * the instance nor compute another statistic.
 * @since 1.2
 * @version $Revision: 811685 $ $Date: 2009-09-05 13:36:48 -0400 (Sat, 05 Sep 2009) $
 */
public class SynchronizedMultivariateSummaryStatistics
  extends MultivariateSummaryStatistics {

    /** Serialization UID */
    private static final long serialVersionUID = 7099834153347155363L;

    /**
     * Construct a SynchronizedMultivariateSummaryStatistics instance
     * @param k dimension of the data
     * @param isCovarianceBiasCorrected if true, the unbiased sample
     * covariance is computed, otherwise the biased population covariance
     * is computed
     */
    public SynchronizedMultivariateSummaryStatistics(int k, boolean isCovarianceBiasCorrected) {
        super(k, isCovarianceBiasCorrected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void addValue(double[] value)
      throws DimensionMismatchException {
      super.addValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized int getDimension() {
        return super.getDimension();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized long getN() {
        return super.getN();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized double[] getSum() {
        return super.getSum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized double[] getSumSq() {
        return super.getSumSq();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized double[] getSumLog() {
        return super.getSumLog();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized double[] getMean() {
        return super.getMean();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized double[] getStandardDeviation() {
        return super.getStandardDeviation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized RealMatrix getCovariance() {
        return super.getCovariance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized double[] getMax() {
        return super.getMax();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized double[] getMin() {
        return super.getMin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized double[] getGeometricMean() {
        return super.getGeometricMean();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized String toString() {
        return super.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void clear() {
        super.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean equals(Object object) {
        return super.equals(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized int hashCode() {
        return super.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized StorelessUnivariateStatistic[] getSumImpl() {
        return super.getSumImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setSumImpl(StorelessUnivariateStatistic[] sumImpl)
      throws DimensionMismatchException {
        super.setSumImpl(sumImpl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized StorelessUnivariateStatistic[] getSumsqImpl() {
        return super.getSumsqImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setSumsqImpl(StorelessUnivariateStatistic[] sumsqImpl)
      throws DimensionMismatchException {
        super.setSumsqImpl(sumsqImpl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized StorelessUnivariateStatistic[] getMinImpl() {
        return super.getMinImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setMinImpl(StorelessUnivariateStatistic[] minImpl)
      throws DimensionMismatchException {
        super.setMinImpl(minImpl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized StorelessUnivariateStatistic[] getMaxImpl() {
        return super.getMaxImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setMaxImpl(StorelessUnivariateStatistic[] maxImpl)
      throws DimensionMismatchException {
        super.setMaxImpl(maxImpl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized StorelessUnivariateStatistic[] getSumLogImpl() {
        return super.getSumLogImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setSumLogImpl(StorelessUnivariateStatistic[] sumLogImpl)
      throws DimensionMismatchException {
        super.setSumLogImpl(sumLogImpl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized StorelessUnivariateStatistic[] getGeoMeanImpl() {
        return super.getGeoMeanImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setGeoMeanImpl(StorelessUnivariateStatistic[] geoMeanImpl)
      throws DimensionMismatchException {
        super.setGeoMeanImpl(geoMeanImpl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized StorelessUnivariateStatistic[] getMeanImpl() {
        return super.getMeanImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setMeanImpl(StorelessUnivariateStatistic[] meanImpl)
      throws DimensionMismatchException {
        super.setMeanImpl(meanImpl);
    }

    public synchronized tful.arrays.doubleArray testful_getMin()  { return new tful.arrays.doubleArray(getMin( ));}
    public synchronized tful.arrays.doubleArray testful_getMax()  { return new tful.arrays.doubleArray(getMax( ));}
    public synchronized tful.arrays.doubleArray testful_getStandardDeviation()  { return new tful.arrays.doubleArray(getStandardDeviation( ));}
    public synchronized tful.arrays.doubleArray testful_getMean()  { return new tful.arrays.doubleArray(getMean( ));}
    public synchronized tful.arrays.doubleArray testful_getSum()  { return new tful.arrays.doubleArray(getSum( ));}
    public synchronized void testful_addValue(tful.arrays.doubleArray p0) throws org.apache.commons.math.DimensionMismatchException { addValue( p0.toArray() );}
    public synchronized tful.arrays.doubleArray testful_getGeometricMean()  { return new tful.arrays.doubleArray(getGeometricMean( ));}
    public synchronized tful.arrays.doubleArray testful_getSumSq()  { return new tful.arrays.doubleArray(getSumSq( ));}
    public synchronized tful.arrays.doubleArray testful_getSumLog()  { return new tful.arrays.doubleArray(getSumLog( ));}

    public synchronized tful.arrays.StorelessUnivariateStatisticArray testful_getSumImpl()  { return new tful.arrays.StorelessUnivariateStatisticArray (getSumImpl()); }
    public synchronized void testful_setSumImpl(tful.arrays.StorelessUnivariateStatisticArray p0) throws org.apache.commons.math.DimensionMismatchException { setSumImpl(p0.toArray() ); }
    public synchronized tful.arrays.StorelessUnivariateStatisticArray testful_getSumsqImpl()  { return new tful.arrays.StorelessUnivariateStatisticArray (getSumsqImpl()); }
    public synchronized void testful_setSumsqImpl(tful.arrays.StorelessUnivariateStatisticArray p0) throws org.apache.commons.math.DimensionMismatchException { setSumsqImpl(p0.toArray() ); }
    public synchronized tful.arrays.StorelessUnivariateStatisticArray testful_getMinImpl()  { return new tful.arrays.StorelessUnivariateStatisticArray (getMinImpl()); }
    public synchronized void testful_setMinImpl(tful.arrays.StorelessUnivariateStatisticArray p0) throws org.apache.commons.math.DimensionMismatchException { setMinImpl(p0.toArray() ); }
    public synchronized tful.arrays.StorelessUnivariateStatisticArray testful_getMaxImpl()  { return new tful.arrays.StorelessUnivariateStatisticArray (getMaxImpl()); }
    public synchronized void testful_setMaxImpl(tful.arrays.StorelessUnivariateStatisticArray p0) throws org.apache.commons.math.DimensionMismatchException { setMaxImpl(p0.toArray() ); }
    public synchronized tful.arrays.StorelessUnivariateStatisticArray testful_getSumLogImpl()  { return new tful.arrays.StorelessUnivariateStatisticArray (getSumLogImpl()); }
    public synchronized void testful_setSumLogImpl(tful.arrays.StorelessUnivariateStatisticArray p0) throws org.apache.commons.math.DimensionMismatchException { setSumLogImpl(p0.toArray() ); }
    public synchronized tful.arrays.StorelessUnivariateStatisticArray testful_getGeoMeanImpl()  { return new tful.arrays.StorelessUnivariateStatisticArray (getGeoMeanImpl()); }
    public synchronized void testful_setGeoMeanImpl(tful.arrays.StorelessUnivariateStatisticArray p0) throws org.apache.commons.math.DimensionMismatchException { setGeoMeanImpl(p0.toArray() ); }
    public synchronized tful.arrays.StorelessUnivariateStatisticArray testful_getMeanImpl()  { return new tful.arrays.StorelessUnivariateStatisticArray (getMeanImpl()); }
    public synchronized void testful_setMeanImpl(tful.arrays.StorelessUnivariateStatisticArray p0) throws org.apache.commons.math.DimensionMismatchException { setMeanImpl(p0.toArray() ); }

}
