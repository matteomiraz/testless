/*
 * @(#)Iterator.java	1.27 06/07/24
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javux;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * An iterator over a collection.  Iterator takes the place of Enumeration in
 * the Java collections framework.  Iterators differ from enumerations in two
 * ways: <ul>
 *	<li> Iterators allow the caller to remove elements from the
 *	     underlying collection during the iteration with well-defined
 * 	     semantics.
 *	<li> Method names have been improved.
 * </ul><p>
 *
 * This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch
 * @version 1.27, 07/24/06
 * @see Collection
 * @see ListIterator
 * @see Enumeration
 * @since 1.2
 */
public interface Iterator<E> extends java.util.Iterator<E> {
    /**
     * Returns <tt>true</tt> if the iteration has more elements. (In other
     * words, returns <tt>true</tt> if <tt>next</tt> would return an element
     * rather than throwing an exception.)
     *
     * @return <tt>true</tt> if the iterator has more elements.
     */
    boolean hasNext() throws ConcurrentModificationException;

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration.
     * @exceptiona NoSuchElementException iteration has no more elements.
     */
    E next() throws NoSuchElementException, ConcurrentModificationException;

    /**
     *
     * Removes from the underlying collection the last element returned by the
     * iterator (optional operation).  This method can be called only once per
     * call to <tt>next</tt>.  The behavior of an iterator is unspecified if
     * the underlying collection is modified while the iteration is in
     * progress in any way other than by calling this method.
     *
     * @exceptiona UnsupportedOperationException if the <tt>remove</tt>
     *		  operation is not supported by this Iterator.

     * @exceptiona IllegalStateException if the <tt>next</tt> method has not
     *		  yet been called, or the <tt>remove</tt> method has already
     *		  been called after the last call to the <tt>next</tt>
     *		  method.
     */
    void remove() throws java.lang.IllegalStateException, java.lang.UnsupportedOperationException;
}
