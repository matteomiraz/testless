/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/* ----------------------
 * AsUndirectedGraph.java
 * ----------------------
 * (C) Copyright 2003-2008, by John V. Sichi and Contributors.
 *
 * Original Author:  John V. Sichi
 * Contributor(s):   Christian Hammer
 *
 * $Id: AsUndirectedGraph.java 645 2008-09-30 19:44:48Z perfecthash $
 *
 * Changes
 * -------
 * 14-Aug-2003 : Initial revision (JVS);
 * 11-Mar-2004 : Made generic (CH);
 * 07-May-2006 : Changed from List<Edge> to Set<Edge> (JVS);
 * 28-May-2006 : Moved connectivity info from edge to graph (JVS);
 *
 */
package org.jgrapht.graph;

import java.io.*;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.util.*;


/**
 * An undirected view of the backing directed graph specified in the
 * constructor. This graph allows modules to apply algorithms designed for
 * undirected graphs to a directed graph by simply ignoring edge direction. If
 * the backing directed graph is an <a
 * href="http://mathworld.wolfram.com/OrientedGraph.html">oriented graph</a>,
 * then the view will be a simple graph; otherwise, it will be a multigraph.
 * Query operations on this graph "read through" to the backing graph. Attempts
 * to add edges will result in an <code>UnsupportedOperationException</code>,
 * but vertex addition/removal and edge removal are all supported (and
 * immediately reflected in the backing graph).
 *
 * <p>Note that edges returned by this graph's accessors are really just the
 * edges of the underlying directed graph. Since there is no interface
 * distinction between directed and undirected edges, this detail should be
 * irrelevant to algorithms.</p>
 *
 * <p>This graph does <i>not</i> pass the hashCode and equals operations through
 * to the backing graph, but relies on <tt>Object</tt>'s <tt>equals</tt> and
 * <tt>hashCode</tt> methods. This graph will be serializable if the backing
 * graph is serializable.</p>
 *
 * @author John V. Sichi
 * @since Aug 14, 2003
 */
public class AsUndirectedGraph<V, E extends org.jgrapht.graph.DefaultEdge>
    extends GraphDelegator<V, E>
    implements Serializable,
        UndirectedGraph<V, E>
{
    //~ Static fields/initializers ---------------------------------------------

    private static final long serialVersionUID = 3257845485078065462L; // @todo renew
    private static final String NO_EDGE_ADD =
        "this graph does not support edge addition";
    private static final String UNDIRECTED =
        "this graph only supports undirected operations";

    //~ Constructors -----------------------------------------------------------

    /**
     * Constructor for AsUndirectedGraph.
     *
     * @param g the backing directed graph over which an undirected view is to
     * be created.
     */
    public AsUndirectedGraph(DirectedGraph<V, E> g)
    {
        super(g);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * @see Graph#getAllEdges(Object, Object)
     */
    public Set<E> getAllEdges(V sourceVertex, V targetVertex)
    {
    	if(sourceVertex == null){
    		throw new NullPointerException("sourceVertex cannot be null");
    	}
    	if(targetVertex == null){
    		throw new NullPointerException("targetVertex cannot be null");
    	}
    	
        Set<E> forwardList = super.getAllEdges(sourceVertex, targetVertex);

        if (sourceVertex.equals(targetVertex)) {
            // avoid duplicating loops
            return forwardList;
        }

        Set<E> reverseList = super.getAllEdges(targetVertex, sourceVertex);
        Set<E> list =
            new ArrayUnenforcedSet<E>(
                forwardList.size() + reverseList.size());
        list.addAll(forwardList);
        list.addAll(reverseList);

        return list;
    }

    /**
     * @see Graph#getEdge(Object, Object)
     */
    public E getEdge(V sourceVertex, V targetVertex)
    {
        E edge = super.getEdge(sourceVertex, targetVertex);

        if (edge != null) {
            return edge;
        }

        // try the other direction
        return super.getEdge(targetVertex, sourceVertex);
    }

    /**
     * @see Graph#addEdge(Object, Object)
     */
    public E addEdge(V sourceVertex, V targetVertex) throws java.lang.IllegalArgumentException
    {
        throw new UnsupportedOperationException(NO_EDGE_ADD);
    }

    /**
     * @see Graph#addEdge(Object, Object, Object)
     */
    public boolean addEdge(V sourceVertex, V targetVertex, E e)
    {
        throw new UnsupportedOperationException(NO_EDGE_ADD);
    }

    /**
     * @see UndirectedGraph#degreeOf(Object)
     */
    public int degreeOf(V vertex)
    {
        // this counts loops twice, which is consistent with AbstractBaseGraph
        return super.inDegreeOf(vertex) + super.outDegreeOf(vertex);
    }

    /**
     * @see DirectedGraph#inDegreeOf(Object)
     */
    public int inDegreeOf(V vertex)
    {
        throw new UnsupportedOperationException(UNDIRECTED);
    }

    /**
     * @see DirectedGraph#incomingEdgesOf(Object)
     */
    public Set<E> incomingEdgesOf(V vertex)
    {
        throw new UnsupportedOperationException(UNDIRECTED);
    }

    /**
     * @see DirectedGraph#outDegreeOf(Object)
     */
    public int outDegreeOf(V vertex)
    {
        throw new UnsupportedOperationException(UNDIRECTED);
    }

    /**
     * @see DirectedGraph#outgoingEdgesOf(Object)
     */
    public Set<E> outgoingEdgesOf(V vertex)
    {
        throw new UnsupportedOperationException(UNDIRECTED);
    }

    /**
     * @see AbstractBaseGraph#toString()
     */
    public String toString()
    {
        return super.toStringFromSets(vertexSet(), edgeSet(), false);
    }
}

// End AsUndirectedGraph.java
