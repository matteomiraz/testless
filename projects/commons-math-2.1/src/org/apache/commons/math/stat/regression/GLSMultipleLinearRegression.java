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
package org.apache.commons.math.stat.regression;

import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealVector;


/**
 * The GLS implementation of the multiple linear regression.
 *
 * GLS assumes a general covariance matrix Omega of the error
 * <pre>
 * u ~ N(0, Omega)
 * </pre>
 *
 * Estimated by GLS,
 * <pre>
 * b=(X' Omega^-1 X)^-1X'Omega^-1 y
 * </pre>
 * whose variance is
 * <pre>
 * Var(b)=(X' Omega^-1 X)^-1
 * </pre>
 * @version $Revision: 811685 $ $Date: 2009-09-05 13:36:48 -0400 (Sat, 05 Sep 2009) $
 * @since 2.0
 */
public class GLSMultipleLinearRegression extends AbstractMultipleLinearRegression {

    /** Covariance matrix. */
    private RealMatrix Omega;

    /** Inverse of covariance matrix. */
    private RealMatrix OmegaInverse;

    /** Replace sample data, overriding any previous sample.
     * @param y y values of the sample
     * @param x x values of the sample
     * @param covariance array representing the covariance matrix
     */
    public void newSampleData(double[] y, double[][] x, double[][] covariance) {
        validateSampleData(x, y);
        newYSampleData(y);
        newXSampleData(x);
        validateCovarianceData(x, covariance);
        newCovarianceData(covariance);
    }

    /**
     * Add the covariance data.
     *
     * @param omega the [n,n] array representing the covariance
     */
    protected void newCovarianceData(double[][] omega){
        this.Omega = new Array2DRowRealMatrix(omega);
        this.OmegaInverse = null;
    }

    /**
     * Get the inverse of the covariance.
     * <p>The inverse of the covariance matrix is lazily evaluated and cached.</p>
     * @return inverse of the covariance
     */
    protected RealMatrix getOmegaInverse() {
        if (OmegaInverse == null) {
            OmegaInverse = new LUDecompositionImpl(Omega).getSolver().getInverse();
        }
        return OmegaInverse;
    }

    /**
     * Calculates beta by GLS.
     * <pre>
     *  b=(X' Omega^-1 X)^-1X'Omega^-1 y
     * </pre>
     * @return beta
     */
    @Override
    protected RealVector calculateBeta() {
        RealMatrix OI = getOmegaInverse();
        RealMatrix XT = X.transpose();
        RealMatrix XTOIX = XT.multiply(OI).multiply(X);
        RealMatrix inverse = new LUDecompositionImpl(XTOIX).getSolver().getInverse();
        return inverse.multiply(XT).multiply(OI).operate(Y);
    }

    /**
     * Calculates the variance on the beta by GLS.
     * <pre>
     *  Var(b)=(X' Omega^-1 X)^-1
     * </pre>
     * @return The beta variance matrix
     */
    @Override
    protected RealMatrix calculateBetaVariance() {
        RealMatrix OI = getOmegaInverse();
        RealMatrix XTOIX = X.transpose().multiply(OI).multiply(X);
        return new LUDecompositionImpl(XTOIX).getSolver().getInverse();
    }

    /**
     * Calculates the variance on the y by GLS.
     * <pre>
     *  Var(y)=Tr(u' Omega^-1 u)/(n-k)
     * </pre>
     * @return The Y variance
     */
    @Override
    protected double calculateYVariance() {
        RealVector residuals = calculateResiduals();
        double t = residuals.dotProduct(getOmegaInverse().operate(residuals));
        return t / (X.getRowDimension() - X.getColumnDimension());
    }


    public void testful_newSampleData(tful.arrays.doubleArray p0, tful.arrays.doubleArrayArray p1, tful.arrays.doubleArrayArray p2)  { newSampleData(p0.toArray() , p1.toArray() , p2.toArray() ); }
}
