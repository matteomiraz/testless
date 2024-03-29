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

import java.util.Arrays;

import org.apache.commons.math.MathRuntimeException;

/**
 * A {@link RandomVectorGenerator} that generates vectors with uncorrelated
 * components. Components of generated vectors follow (independent) Gaussian
 * distributions, with parameters supplied in the constructor.
 *
 * @version $Revision: 811827 $ $Date: 2009-09-06 11:32:50 -0400 (Sun, 06 Sep 2009) $
 * @since 1.2
 */

public class UncorrelatedRandomVectorGenerator
  implements RandomVectorGenerator {

    /** Underlying scalar generator. */
    private final NormalizedRandomGenerator generator;

    /** Mean vector. */
    private final double[] mean;

    /** Standard deviation vector. */
    private final double[] standardDeviation;

  /** Simple constructor.
   * <p>Build an uncorrelated random vector generator from
   * its mean and standard deviation vectors.</p>
   * @param mean expected mean values for each component
   * @param standardDeviation standard deviation for each component
   * @param generator underlying generator for uncorrelated normalized
   * components
   */
  public UncorrelatedRandomVectorGenerator(double[] mean,
                                           double[] standardDeviation,
                                           NormalizedRandomGenerator generator) {
    if (mean.length != standardDeviation.length) {
      throw MathRuntimeException.createIllegalArgumentException(
            "dimension mismatch {0} != {1}",
            mean.length, standardDeviation.length);
    }
    this.mean              = mean.clone();
    this.standardDeviation = standardDeviation.clone();
    this.generator = generator;
  }

  /** Simple constructor.
   * <p>Build a null mean random and unit standard deviation
   * uncorrelated vector generator</p>
   * @param dimension dimension of the vectors to generate
   * @param generator underlying generator for uncorrelated normalized
   * components
   */
  public UncorrelatedRandomVectorGenerator(int dimension,
                                           NormalizedRandomGenerator generator) {
    mean              = new double[dimension];
    standardDeviation = new double[dimension];
    Arrays.fill(standardDeviation, 1.0);
    this.generator = generator;
  }

  /** Generate an uncorrelated random vector.
   * @return a random vector as a newly built array of double
   */
  public double[] nextVector() {

    double[] random = new double[mean.length];
    for (int i = 0; i < random.length; ++i) {
      random[i] = mean[i] + standardDeviation[i] * generator.nextNormalizedDouble();
    }

    return random;

  }

  public UncorrelatedRandomVectorGenerator(tful.arrays.doubleArray p0, tful.arrays.doubleArray p1, org.apache.commons.math.random.NormalizedRandomGenerator p2)  { this(p0.toArray() , p1.toArray() , p2); }
  public tful.arrays.doubleArray testful_nextVector()  { return new tful.arrays.doubleArray(nextVector( ));}

}
