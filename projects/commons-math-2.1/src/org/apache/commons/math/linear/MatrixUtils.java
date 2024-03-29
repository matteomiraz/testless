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

package org.apache.commons.math.linear;

import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.commons.math.Field;
import org.apache.commons.math.FieldElement;
import org.apache.commons.math.MathRuntimeException;
import org.apache.commons.math.fraction.BigFraction;
import org.apache.commons.math.fraction.Fraction;

import tful.Contracts;

/**
 * A collection of static methods that operate on or return matrices.
 *
 * @version $Revision: 903046 $ $Date: 2010-01-25 21:07:26 -0500 (Mon, 25 Jan 2010) $
 */
public class MatrixUtils {

    /**
     * Private constructor.
     */
    private MatrixUtils() {
        super();
    }

    /**
     * Returns a {@link RealMatrix} with specified dimensions.
     * <p>The type of matrix returned depends on the dimension. Below
     * 2<sup>12</sup> elements (i.e. 4096 elements or 64&times;64 for a
     * square matrix) which can be stored in a 32kB array, a {@link
     * Array2DRowRealMatrix} instance is built. Above this threshold a {@link
     * BlockRealMatrix} instance is built.</p>
     * <p>The matrix elements are all set to 0.0.</p>
     * @param rows number of rows of the matrix
     * @param columns number of columns of the matrix
     * @return  RealMatrix with specified dimensions
     * @see #createRealMatrix(double[][])
     */
    public static RealMatrix createRealMatrix(final int rows, final int columns) {
        return (rows * columns <= 4096) ?
                new Array2DRowRealMatrix(rows, columns) : new BlockRealMatrix(rows, columns);
    }

    /**
     * Returns a {@link FieldMatrix} with specified dimensions.
     * <p>The type of matrix returned depends on the dimension. Below
     * 2<sup>12</sup> elements (i.e. 4096 elements or 64&times;64 for a
     * square matrix), a {@link FieldMatrix} instance is built. Above
     * this threshold a {@link BlockFieldMatrix} instance is built.</p>
     * <p>The matrix elements are all set to field.getZero().</p>
     * @param <T> the type of the field elements
     * @param field field to which the matrix elements belong
     * @param rows number of rows of the matrix
     * @param columns number of columns of the matrix
     * @return  FieldMatrix with specified dimensions
     * @see #createFieldMatrix(FieldElement[][])
     * @since 2.0
     */
    public static <T extends FieldElement<T>> FieldMatrix<T> createFieldMatrix(final Field<T> field,
                                                                               final int rows,
                                                                               final int columns) {
        return (rows * columns <= 4096) ?
                new Array2DRowFieldMatrix<T>(field, rows, columns) : new BlockFieldMatrix<T>(field, rows, columns);
    }

    /**
     * Returns a {@link RealMatrix} whose entries are the the values in the
     * the input array.
     * <p>The type of matrix returned depends on the dimension. Below
     * 2<sup>12</sup> elements (i.e. 4096 elements or 64&times;64 for a
     * square matrix) which can be stored in a 32kB array, a {@link
     * Array2DRowRealMatrix} instance is built. Above this threshold a {@link
     * BlockRealMatrix} instance is built.</p>
     * <p>The input array is copied, not referenced.</p>
     *
     * @param data input array
     * @return  RealMatrix containing the values of the array
     * @throws IllegalArgumentException if <code>data</code> is not rectangular
     *  (not all rows have the same length) or empty
     * @throws NullPointerException if either <code>data</code> or
     * <code>data[0]</code> is null
     * @see #createRealMatrix(int, int)
     */
    public static RealMatrix createRealMatrix(double[][] data) {
        return (data.length * data[0].length <= 4096) ?
                new Array2DRowRealMatrix(data) : new BlockRealMatrix(data);
    }

    /**
     * Returns a {@link FieldMatrix} whose entries are the the values in the
     * the input array.
     * <p>The type of matrix returned depends on the dimension. Below
     * 2<sup>12</sup> elements (i.e. 4096 elements or 64&times;64 for a
     * square matrix), a {@link FieldMatrix} instance is built. Above
     * this threshold a {@link BlockFieldMatrix} instance is built.</p>
     * <p>The input array is copied, not referenced.</p>
     * @param <T> the type of the field elements
     * @param data input array
     * @return  RealMatrix containing the values of the array
     * @throws IllegalArgumentException if <code>data</code> is not rectangular
     *  (not all rows have the same length) or empty
     * @throws NullPointerException if either <code>data</code> or
     * <code>data[0]</code> is null
     * @see #createFieldMatrix(Field, int, int)
     * @since 2.0
     */
    public static <T extends FieldElement<T>> FieldMatrix<T> createFieldMatrix(T[][] data) {
        return (data.length * data[0].length <= 4096) ?
                new Array2DRowFieldMatrix<T>(data) : new BlockFieldMatrix<T>(data);
    }

    /**
     * Returns <code>dimension x dimension</code> identity matrix.
     *
     * @param dimension dimension of identity matrix to generate
     * @return identity matrix
     * @throws IllegalArgumentException if dimension is not positive
     * @since 1.1
     */
    public static RealMatrix createRealIdentityMatrix(int dimension) {
    	Contracts.assertSmall(dimension);
        final RealMatrix m = createRealMatrix(dimension, dimension);
        for (int i = 0; i < dimension; ++i) {
            m.setEntry(i, i, 1.0);
        }
        return m;
    }

    /**
     * Returns <code>dimension x dimension</code> identity matrix.
     *
     * @param <T> the type of the field elements
     * @param field field to which the elements belong
     * @param dimension dimension of identity matrix to generate
     * @return identity matrix
     * @throws IllegalArgumentException if dimension is not positive
     * @since 2.0
     */
    public static <T extends FieldElement<T>> FieldMatrix<T>
        createFieldIdentityMatrix(final Field<T> field, final int dimension) {
        final T zero = field.getZero();
        final T one  = field.getOne();
        @SuppressWarnings({ "unchecked", "restriction" }) // zero is type T
        final T[][] d = (T[][]) java.lang.reflect.Array.newInstance(zero.getClass(), new int[] { dimension, dimension });
        for (int row = 0; row < dimension; row++) {
            final T[] dRow = d[row];
            Arrays.fill(dRow, zero);
            dRow[row] = one;
        }
        return new Array2DRowFieldMatrix<T>(d, false);
    }

    /**
     * Returns <code>dimension x dimension</code> identity matrix.
     *
     * @param dimension dimension of identity matrix to generate
     * @return identity matrix
     * @throws IllegalArgumentException if dimension is not positive
     * @since 1.1
     * @deprecated since 2.0, replaced by {@link #createFieldIdentityMatrix(Field, int)}
     */
    @Deprecated
    public static BigMatrix createBigIdentityMatrix(int dimension) {
    	Contracts.assertSmall(dimension);
        final BigDecimal[][] d = new BigDecimal[dimension][dimension];
        for (int row = 0; row < dimension; row++) {
            final BigDecimal[] dRow = d[row];
            Arrays.fill(dRow, BigMatrixImpl.ZERO);
            dRow[row] = BigMatrixImpl.ONE;
        }
        return new BigMatrixImpl(d, false);
    }

    /**
     * Returns a diagonal matrix with specified elements.
     *
     * @param diagonal diagonal elements of the matrix (the array elements
     * will be copied)
     * @return diagonal matrix
     * @since 2.0
     */
    public static RealMatrix createRealDiagonalMatrix(final double[] diagonal) {
        final RealMatrix m = createRealMatrix(diagonal.length, diagonal.length);
        for (int i = 0; i < diagonal.length; ++i) {
            m.setEntry(i, i, diagonal[i]);
        }
        return m;
    }

    /**
     * Returns a diagonal matrix with specified elements.
     *
     * @param <T> the type of the field elements
     * @param diagonal diagonal elements of the matrix (the array elements
     * will be copied)
     * @return diagonal matrix
     * @since 2.0
     */
    public static <T extends FieldElement<T>> FieldMatrix<T>
        createFieldDiagonalMatrix(final T[] diagonal) {
        final FieldMatrix<T> m =
            createFieldMatrix(diagonal[0].getField(), diagonal.length, diagonal.length);
        for (int i = 0; i < diagonal.length; ++i) {
            m.setEntry(i, i, diagonal[i]);
        }
        return m;
    }

    /**
     * Returns a {@link BigMatrix} whose entries are the the values in the
     * the input array.  The input array is copied, not referenced.
     *
     * @param data input array
     * @return  RealMatrix containing the values of the array
     * @throws IllegalArgumentException if <code>data</code> is not rectangular
     *  (not all rows have the same length) or empty
     * @throws NullPointerException if data is null
     * @deprecated since 2.0 replaced by {@link #createFieldMatrix(FieldElement[][])}
     */
    @Deprecated
    public static BigMatrix createBigMatrix(double[][] data) {
        return new BigMatrixImpl(data);
    }

    /**
     * Returns a {@link BigMatrix} whose entries are the the values in the
     * the input array.  The input array is copied, not referenced.
     *
     * @param data input array
     * @return  RealMatrix containing the values of the array
     * @throws IllegalArgumentException if <code>data</code> is not rectangular
     *  (not all rows have the same length) or empty
     * @throws NullPointerException if data is null
     * @deprecated since 2.0 replaced by {@link #createFieldMatrix(FieldElement[][])}
     */
    @Deprecated
    public static BigMatrix createBigMatrix(BigDecimal[][] data) {
        return new BigMatrixImpl(data);
    }

    /**
     * Returns a {@link BigMatrix} whose entries are the the values in the
     * the input array.
     * <p>If an array is built specially in order to be embedded in a
     * BigMatrix and not used directly, the <code>copyArray</code> may be
     * set to <code>false</code. This will prevent the copying and improve
     * performance as no new array will be built and no data will be copied.</p>
     * @param data data for new matrix
     * @param copyArray if true, the input array will be copied, otherwise
     * it will be referenced
     * @return  BigMatrix containing the values of the array
     * @throws IllegalArgumentException if <code>data</code> is not rectangular
     *  (not all rows have the same length) or empty
     * @throws NullPointerException if <code>data</code> is null
     * @see #createRealMatrix(double[][])
     * @deprecated since 2.0 replaced by {@link #createFieldMatrix(FieldElement[][])}
     */
    @Deprecated
    public static BigMatrix createBigMatrix(BigDecimal[][] data, boolean copyArray) {
        return new BigMatrixImpl(data, copyArray);
    }

    /**
     * Returns a {@link BigMatrix} whose entries are the the values in the
     * the input array.  The input array is copied, not referenced.
     *
     * @param data input array
     * @return  RealMatrix containing the values of the array
     * @throws IllegalArgumentException if <code>data</code> is not rectangular
     *  (not all rows have the same length) or empty
     * @throws NullPointerException if data is null
     * @deprecated since 2.0 replaced by {@link #createFieldMatrix(FieldElement[][])}
     */
    @Deprecated
    public static BigMatrix createBigMatrix(String[][] data) {
        return new BigMatrixImpl(data);
    }

    /**
     * Creates a {@link RealVector} using the data from the input array.
     *
     * @param data the input data
     * @return a data.length RealVector
     * @throws IllegalArgumentException if <code>data</code> is empty
     * @throws NullPointerException if <code>data</code>is null
     */
    public static RealVector createRealVector(double[] data) {
        return new ArrayRealVector(data, true);
    }

    /**
     * Creates a {@link FieldVector} using the data from the input array.
     *
     * @param <T> the type of the field elements
     * @param data the input data
     * @return a data.length FieldVector
     * @throws IllegalArgumentException if <code>data</code> is empty
     * @throws NullPointerException if <code>data</code>is null
     */
    public static <T extends FieldElement<T>> FieldVector<T> createFieldVector(final T[] data) {
        return new ArrayFieldVector<T>(data, true);
    }

    /**
     * Creates a row {@link RealMatrix} using the data from the input
     * array.
     *
     * @param rowData the input row data
     * @return a 1 x rowData.length RealMatrix
     * @throws IllegalArgumentException if <code>rowData</code> is empty
     * @throws NullPointerException if <code>rowData</code>is null
     */
    public static RealMatrix createRowRealMatrix(double[] rowData) {
        final int nCols = rowData.length;
        final RealMatrix m = createRealMatrix(1, nCols);
        for (int i = 0; i < nCols; ++i) {
            m.setEntry(0, i, rowData[i]);
        }
        return m;
    }

    /**
     * Creates a row {@link FieldMatrix} using the data from the input
     * array.
     *
     * @param <T> the type of the field elements
     * @param rowData the input row data
     * @return a 1 x rowData.length FieldMatrix
     * @throws IllegalArgumentException if <code>rowData</code> is empty
     * @throws NullPointerException if <code>rowData</code>is null
     */
    public static <T extends FieldElement<T>> FieldMatrix<T>
        createRowFieldMatrix(final T[] rowData) {
        final int nCols = rowData.length;
        if (nCols == 0) {
            throw MathRuntimeException.createIllegalArgumentException("matrix must have at least one column");
        }
        final FieldMatrix<T> m = createFieldMatrix(rowData[0].getField(), 1, nCols);
        for (int i = 0; i < nCols; ++i) {
            m.setEntry(0, i, rowData[i]);
        }
        return m;
    }

    /**
     * Creates a row {@link BigMatrix} using the data from the input
     * array.
     *
     * @param rowData the input row data
     * @return a 1 x rowData.length BigMatrix
     * @throws IllegalArgumentException if <code>rowData</code> is empty
     * @throws NullPointerException if <code>rowData</code>is null
     * @deprecated since 2.0 replaced by {@link #createRowFieldMatrix(FieldElement[])}
     */
    @Deprecated
    public static BigMatrix createRowBigMatrix(double[] rowData) {
        final int nCols = rowData.length;
        final BigDecimal[][] data = new BigDecimal[1][nCols];
        for (int i = 0; i < nCols; ++i) {
            data[0][i] = new BigDecimal(rowData[i]);
        }
        return new BigMatrixImpl(data, false);
    }

    /**
     * Creates a row {@link BigMatrix} using the data from the input
     * array.
     *
     * @param rowData the input row data
     * @return a 1 x rowData.length BigMatrix
     * @throws IllegalArgumentException if <code>rowData</code> is empty
     * @throws NullPointerException if <code>rowData</code>is null
     * @deprecated since 2.0 replaced by {@link #createRowFieldMatrix(FieldElement[])}
     */
    @Deprecated
    public static BigMatrix createRowBigMatrix(BigDecimal[] rowData) {
        final int nCols = rowData.length;
        final BigDecimal[][] data = new BigDecimal[1][nCols];
        System.arraycopy(rowData, 0, data[0], 0, nCols);
        return new BigMatrixImpl(data, false);
    }

    /**
     * Creates a row {@link BigMatrix} using the data from the input
     * array.
     *
     * @param rowData the input row data
     * @return a 1 x rowData.length BigMatrix
     * @throws IllegalArgumentException if <code>rowData</code> is empty
     * @throws NullPointerException if <code>rowData</code>is null
     * @deprecated since 2.0 replaced by {@link #createRowFieldMatrix(FieldElement[])}
     */
    @Deprecated
    public static BigMatrix createRowBigMatrix(String[] rowData) {
        final int nCols = rowData.length;
        final BigDecimal[][] data = new BigDecimal[1][nCols];
        for (int i = 0; i < nCols; ++i) {
            data[0][i] = new BigDecimal(rowData[i]);
        }
        return new BigMatrixImpl(data, false);
    }

    /**
     * Creates a column {@link RealMatrix} using the data from the input
     * array.
     *
     * @param columnData  the input column data
     * @return a columnData x 1 RealMatrix
     * @throws IllegalArgumentException if <code>columnData</code> is empty
     * @throws NullPointerException if <code>columnData</code>is null
     */
    public static RealMatrix createColumnRealMatrix(double[] columnData) {
        final int nRows = columnData.length;
        final RealMatrix m = createRealMatrix(nRows, 1);
        for (int i = 0; i < nRows; ++i) {
            m.setEntry(i, 0, columnData[i]);
        }
        return m;
    }

    /**
     * Creates a column {@link FieldMatrix} using the data from the input
     * array.
     *
     * @param <T> the type of the field elements
     * @param columnData  the input column data
     * @return a columnData x 1 FieldMatrix
     * @throws IllegalArgumentException if <code>columnData</code> is empty
     * @throws NullPointerException if <code>columnData</code>is null
     */
    public static <T extends FieldElement<T>> FieldMatrix<T>
        createColumnFieldMatrix(final T[] columnData) {
        final int nRows = columnData.length;
        if (nRows == 0) {
            throw MathRuntimeException.createIllegalArgumentException("matrix must have at least one row");
        }
        final FieldMatrix<T> m = createFieldMatrix(columnData[0].getField(), nRows, 1);
        for (int i = 0; i < nRows; ++i) {
            m.setEntry(i, 0, columnData[i]);
        }
        return m;
    }

    /**
     * Creates a column {@link BigMatrix} using the data from the input
     * array.
     *
     * @param columnData  the input column data
     * @return a columnData x 1 BigMatrix
     * @throws IllegalArgumentException if <code>columnData</code> is empty
     * @throws NullPointerException if <code>columnData</code>is null
     * @deprecated since 2.0 replaced by {@link #createColumnFieldMatrix(FieldElement[])}
     */
    @Deprecated
    public static BigMatrix createColumnBigMatrix(double[] columnData) {
        final int nRows = columnData.length;
        final BigDecimal[][] data = new BigDecimal[nRows][1];
        for (int row = 0; row < nRows; row++) {
            data[row][0] = new BigDecimal(columnData[row]);
        }
        return new BigMatrixImpl(data, false);
    }

    /**
     * Creates a column {@link BigMatrix} using the data from the input
     * array.
     *
     * @param columnData  the input column data
     * @return a columnData x 1 BigMatrix
     * @throws IllegalArgumentException if <code>columnData</code> is empty
     * @throws NullPointerException if <code>columnData</code>is null
     * @deprecated since 2.0 replaced by {@link #createColumnFieldMatrix(FieldElement[])}
     */
    @Deprecated
    public static BigMatrix createColumnBigMatrix(BigDecimal[] columnData) {
        final int nRows = columnData.length;
        final BigDecimal[][] data = new BigDecimal[nRows][1];
        for (int row = 0; row < nRows; row++) {
            data[row][0] = columnData[row];
        }
        return new BigMatrixImpl(data, false);
    }

    /**
     * Creates a column {@link BigMatrix} using the data from the input
     * array.
     *
     * @param columnData  the input column data
     * @return a columnData x 1 BigMatrix
     * @throws IllegalArgumentException if <code>columnData</code> is empty
     * @throws NullPointerException if <code>columnData</code>is null
     * @deprecated since 2.0 replaced by {@link #createColumnFieldMatrix(FieldElement[])}
     */
    @Deprecated
    public static BigMatrix createColumnBigMatrix(String[] columnData) {
        int nRows = columnData.length;
        final BigDecimal[][] data = new BigDecimal[nRows][1];
        for (int row = 0; row < nRows; row++) {
            data[row][0] = new BigDecimal(columnData[row]);
        }
        return new BigMatrixImpl(data, false);
    }

    /**
     * Check if a row index is valid.
     * @param m matrix containing the submatrix
     * @param row row index to check
     * @exception MatrixIndexException if index is not valid
     */
    public static void checkRowIndex(final AnyMatrix m, final int row) {
        if (row < 0 || row >= m.getRowDimension()) {
            throw new MatrixIndexException("row index {0} out of allowed range [{1}, {2}]",
                                           row, 0, m.getRowDimension() - 1);
        }
    }

    /**
     * Check if a column index is valid.
     * @param m matrix containing the submatrix
     * @param column column index to check
     * @exception MatrixIndexException if index is not valid
     */
    public static void checkColumnIndex(final AnyMatrix m, final int column)
        throws MatrixIndexException {
        if (column < 0 || column >= m.getColumnDimension()) {
            throw new MatrixIndexException("column index {0} out of allowed range [{1}, {2}]",
                                           column, 0, m.getColumnDimension() - 1);
        }
    }

    /**
     * Check if submatrix ranges indices are valid.
     * Rows and columns are indicated counting from 0 to n-1.
     *
     * @param m matrix containing the submatrix
     * @param startRow Initial row index
     * @param endRow Final row index
     * @param startColumn Initial column index
     * @param endColumn Final column index
     * @exception MatrixIndexException  if the indices are not valid
     */
    public static void checkSubMatrixIndex(final AnyMatrix m,
                                           final int startRow, final int endRow,
                                           final int startColumn, final int endColumn) {
        checkRowIndex(m, startRow);
        checkRowIndex(m, endRow);
        if (startRow > endRow) {
            throw new MatrixIndexException("initial row {0} after final row {1}",
                                           startRow, endRow);
        }

        checkColumnIndex(m, startColumn);
        checkColumnIndex(m, endColumn);
        if (startColumn > endColumn) {
            throw new MatrixIndexException("initial column {0} after final column {1}",
                                           startColumn, endColumn);
        }


    }

    /**
     * Check if submatrix ranges indices are valid.
     * Rows and columns are indicated counting from 0 to n-1.
     *
     * @param m matrix containing the submatrix
     * @param selectedRows Array of row indices.
     * @param selectedColumns Array of column indices.
     * @exception MatrixIndexException if row or column selections are not valid
     */
    public static void checkSubMatrixIndex(final AnyMatrix m,
                                           final int[] selectedRows, final int[] selectedColumns)
        throws MatrixIndexException {
        if (selectedRows.length * selectedColumns.length == 0) {
            if (selectedRows.length == 0) {
                throw new MatrixIndexException("empty selected row index array");
            }
            throw new MatrixIndexException("empty selected column index array");
        }

        for (final int row : selectedRows) {
            checkRowIndex(m, row);
        }
        for (final int column : selectedColumns) {
            checkColumnIndex(m, column);
        }
    }

    /**
     * Check if matrices are addition compatible
     * @param left left hand side matrix
     * @param right right hand side matrix
     * @exception IllegalArgumentException if matrices are not addition compatible
     */
    public static void checkAdditionCompatible(final AnyMatrix left, final AnyMatrix right)
        throws IllegalArgumentException {
        if ((left.getRowDimension()    != right.getRowDimension()) ||
            (left.getColumnDimension() != right.getColumnDimension())) {
            throw MathRuntimeException.createIllegalArgumentException(
                    "{0}x{1} and {2}x{3} matrices are not addition compatible",
                    left.getRowDimension(), left.getColumnDimension(),
                    right.getRowDimension(), right.getColumnDimension());
        }
    }

    /**
     * Check if matrices are subtraction compatible
     * @param left left hand side matrix
     * @param right right hand side matrix
     * @exception IllegalArgumentException if matrices are not subtraction compatible
     */
    public static void checkSubtractionCompatible(final AnyMatrix left, final AnyMatrix right)
        throws IllegalArgumentException {
        if ((left.getRowDimension()    != right.getRowDimension()) ||
            (left.getColumnDimension() != right.getColumnDimension())) {
            throw MathRuntimeException.createIllegalArgumentException(
                    "{0}x{1} and {2}x{3} matrices are not subtraction compatible",
                    left.getRowDimension(), left.getColumnDimension(),
                    right.getRowDimension(), right.getColumnDimension());
        }
    }

    /**
     * Check if matrices are multiplication compatible
     * @param left left hand side matrix
     * @param right right hand side matrix
     * @exception IllegalArgumentException if matrices are not multiplication compatible
     */
    public static void checkMultiplicationCompatible(final AnyMatrix left, final AnyMatrix right)
        throws IllegalArgumentException {
        if (left.getColumnDimension() != right.getRowDimension()) {
            throw MathRuntimeException.createIllegalArgumentException(
                    "{0}x{1} and {2}x{3} matrices are not multiplication compatible",
                    left.getRowDimension(), left.getColumnDimension(),
                    right.getRowDimension(), right.getColumnDimension());
        }
    }

    /**
     * Convert a {@link FieldMatrix}/{@link Fraction} matrix to a {@link RealMatrix}.
     * @param m matrix to convert
     * @return converted matrix
     */
    public static Array2DRowRealMatrix fractionMatrixToRealMatrix(final FieldMatrix<Fraction> m) {
        final FractionMatrixConverter converter = new FractionMatrixConverter();
        m.walkInOptimizedOrder(converter);
        return converter.getConvertedMatrix();
    }

    /** Converter for {@link FieldMatrix}/{@link Fraction}. */
    private static class FractionMatrixConverter extends DefaultFieldMatrixPreservingVisitor<Fraction> {

        /** Converted array. */
        private double[][] data;

        /** Simple constructor. */
        public FractionMatrixConverter() {
            super(Fraction.ZERO);
        }

        /** {@inheritDoc} */
        @Override
        public void start(int rows, int columns,
                          int startRow, int endRow, int startColumn, int endColumn) {
            data = new double[rows][columns];
        }

        /** {@inheritDoc} */
        @Override
        public void visit(int row, int column, Fraction value) {
            data[row][column] = value.doubleValue();
        }

        /** Get the converted matrix.
         * @return converted matrix
         */
        Array2DRowRealMatrix getConvertedMatrix() {
            return new Array2DRowRealMatrix(data, false);
        }

    }

    /**
     * Convert a {@link FieldMatrix}/{@link BigFraction} matrix to a {@link RealMatrix}.
     * @param m matrix to convert
     * @return converted matrix
     */
    public static Array2DRowRealMatrix bigFractionMatrixToRealMatrix(final FieldMatrix<BigFraction> m) {
        final BigFractionMatrixConverter converter = new BigFractionMatrixConverter();
        m.walkInOptimizedOrder(converter);
        return converter.getConvertedMatrix();
    }

    /** Converter for {@link FieldMatrix}/{@link BigFraction}. */
    private static class BigFractionMatrixConverter extends DefaultFieldMatrixPreservingVisitor<BigFraction> {

        /** Converted array. */
        private double[][] data;

        /** Simple constructor. */
        public BigFractionMatrixConverter() {
            super(BigFraction.ZERO);
        }

        /** {@inheritDoc} */
        @Override
        public void start(int rows, int columns,
                          int startRow, int endRow, int startColumn, int endColumn) {
            data = new double[rows][columns];
        }

        /** {@inheritDoc} */
        @Override
        public void visit(int row, int column, BigFraction value) {
            data[row][column] = value.doubleValue();
        }

        /** Get the converted matrix.
         * @return converted matrix
         */
        Array2DRowRealMatrix getConvertedMatrix() {
            return new Array2DRowRealMatrix(data, false);
        }

    }


    public static org.apache.commons.math.linear.RealMatrix testful_createRealDiagonalMatrix(tful.arrays.doubleArray p0)  { return createRealDiagonalMatrix( p0.toArray() );}
    public static void testful_checkSubMatrixIndex(org.apache.commons.math.linear.AnyMatrix p0, tful.arrays.intArray p1, tful.arrays.intArray p2) throws org.apache.commons.math.linear.MatrixIndexException { checkSubMatrixIndex( p0, p1.toArray() , p2.toArray() );}
    public static org.apache.commons.math.linear.RealVector testful_createRealVector(tful.arrays.doubleArray p0)  { return createRealVector( p0.toArray() );}
    public static org.apache.commons.math.linear.RealMatrix testful_createRowRealMatrix(tful.arrays.doubleArray p0)  { return createRowRealMatrix( p0.toArray() );}
    @SuppressWarnings("deprecation")
	public static org.apache.commons.math.linear.BigMatrix testful_createRowBigMatrix(tful.arrays.doubleArray p0)  { return createRowBigMatrix( p0.toArray() );}
    public static org.apache.commons.math.linear.RealMatrix testful_createColumnRealMatrix(tful.arrays.doubleArray p0)  { return createColumnRealMatrix( p0.toArray() );}
    @SuppressWarnings("deprecation")
	public static org.apache.commons.math.linear.BigMatrix testful_createColumnBigMatrix(tful.arrays.doubleArray p0)  { return createColumnBigMatrix( p0.toArray() );}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static org.apache.commons.math.linear.FieldMatrix testful_createFieldDiagonalMatrix(tful.arrays.FieldElementArray p0)  { return createFieldDiagonalMatrix(p0.toArray() ); }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static org.apache.commons.math.linear.FieldVector testful_createFieldVector(tful.arrays.FieldElementArray p0)  { return createFieldVector(p0.toArray() ); }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static org.apache.commons.math.linear.FieldMatrix testful_createRowFieldMatrix(tful.arrays.FieldElementArray p0)  { return createRowFieldMatrix(p0.toArray() ); }
    @SuppressWarnings("deprecation")
    public static org.apache.commons.math.linear.BigMatrix testful_createRowBigMatrix(tful.arrays.BigDecimalArray p0)  { return createRowBigMatrix(p0.toArray() ); }
    @SuppressWarnings("deprecation")
    public static org.apache.commons.math.linear.BigMatrix testful_createRowBigMatrix(tful.arrays.StringArray p0)  { return createRowBigMatrix(p0.toArray() ); }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static org.apache.commons.math.linear.FieldMatrix testful_createColumnFieldMatrix(tful.arrays.FieldElementArray p0)  { return createColumnFieldMatrix(p0.toArray() ); }
    @SuppressWarnings("deprecation")
    public static org.apache.commons.math.linear.BigMatrix testful_createColumnBigMatrix(tful.arrays.BigDecimalArray p0)  { return createColumnBigMatrix(p0.toArray() ); }
    @SuppressWarnings("deprecation")
    public static org.apache.commons.math.linear.BigMatrix testful_createColumnBigMatrix(tful.arrays.StringArray p0)  { return createColumnBigMatrix(p0.toArray() ); }

    public static org.apache.commons.math.linear.RealMatrix testful_createRealMatrix(tful.arrays.doubleArrayArray p0)  { return createRealMatrix(p0.toArray() ); }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static org.apache.commons.math.linear.FieldMatrix testful_createFieldMatrix(tful.arrays.FieldElementArrayArray p0)  { return createFieldMatrix(p0.toArray() ); }
    @SuppressWarnings("deprecation")
    public static org.apache.commons.math.linear.BigMatrix testful_createBigMatrix(tful.arrays.doubleArrayArray p0)  { return createBigMatrix(p0.toArray() ); }
    @SuppressWarnings("deprecation")
    public static org.apache.commons.math.linear.BigMatrix testful_createBigMatrix(tful.arrays.BigDecimalArrayArray p0)  { return createBigMatrix(p0.toArray() ); }
    @SuppressWarnings("deprecation")
    public static org.apache.commons.math.linear.BigMatrix testful_createBigMatrix(tful.arrays.BigDecimalArrayArray p0, boolean p1)  { return createBigMatrix(p0.toArray() , p1); }
    @SuppressWarnings("deprecation")
    public static org.apache.commons.math.linear.BigMatrix testful_createBigMatrix(tful.arrays.StringArrayArray p0)  { return createBigMatrix(p0.toArray() ); }

}
