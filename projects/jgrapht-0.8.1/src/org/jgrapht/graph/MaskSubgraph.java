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
/* -------------------------
 * MaskSubgraph.java
 * -------------------------
 * (C) Copyright 2007-2008, by France Telecom
 *
 * Original Author:  Guillaume Boulmier and Contributors.
 *
 * $Id: MaskSubgraph.java 645 2008-09-30 19:44:48Z perfecthash $
 *
 * Changes
 * -------
 * 05-Jun-2007 : Initial revision (GB);
 *
 */
package org.jgrapht.graph;

import java.util.*;

import org.jgrapht.*;


/**
 * An unmodifiable subgraph induced by a vertex/edge masking function. The
 * subgraph will keep track of edges being added to its vertex subset as well as
 * deletion of edges and vertices. When iterating over the vertices/edges, it
 * will iterate over the vertices/edges of the base graph and discard
 * vertices/edges that are masked (an edge with a masked extremity vertex is
 * discarded as well).
 *
 * @author Guillaume Boulmier
 * @since July 5, 2007
 */
public class MaskSubgraph<V, E extends org.jgrapht.graph.DefaultEdge>
    extends AbstractGraph<V, E>
{
    //~ Static fields/initializers ---------------------------------------------

    private static final String UNMODIFIABLE = "this graph is unmodifiable";

    //~ Instance fields --------------------------------------------------------

    private Graph<V, E> base;

    private Set<E> edges;

    private MaskFunctor<V, E> mask;

    private Set<V> vertices;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new induced subgraph. Running-time = O(1).
     *
     * @param base the base (backing) graph on which the subgraph will be based.
     * @param mask vertices and edges to exclude in the subgraph. If a
     * vertex/edge is masked, it is as if it is not in the subgraph.
     */
    public MaskSubgraph(Graph<V, E> base, MaskFunctor<V, E> mask)
    {
        super();
        this.base = base;
        this.mask = mask;

        this.vertices = new MaskVertexSet<V, E>(base.vertexSet(), mask);
        this.edges = new MaskEdgeSet<V, E>(base, base.edgeSet(), mask);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * @see Graph#addEdge(Object, Object)
     */
    public E addEdge(V sourceVertex, V targetVertex) throws java.lang.IllegalArgumentException
    {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    public boolean addEdge(V sourceVertex, V targetVertex, E edge) throws java.lang.ClassCastException, java.lang.IllegalArgumentException
    {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    /**
     * @see Graph#addVertex(Object)
     */
    public boolean addVertex(V v)
    {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    public boolean containsEdge(E e)
    {
        return edgeSet().contains(e);
    }

    public boolean containsVertex(V v)
    {
        return !this.mask.isVertexMasked(v) && this.base.containsVertex(v);
    }

    /**
     * @see UndirectedGraph#degreeOf(Object)
     */
    public int degreeOf(V vertex)
    {
        return edgesOf(vertex).size();
    }

    public Set<E> edgeSet()
    {
        return this.edges;
    }

    public Set<E> edgesOf(V vertex) throws java.lang.IllegalArgumentException
    {
        assertVertexExist(vertex);

        return new MaskEdgeSet<V, E>(
            this.base,
            this.base.edgesOf(vertex),
            this.mask);
    }

    public Set<E> getAllEdges(V sourceVertex, V targetVertex)
    {
        Set<E> edges = null;

        if (containsVertex(sourceVertex) && containsVertex(targetVertex)) {
            return new MaskEdgeSet<V, E>(
                this.base,
                this.base.getAllEdges(
                    sourceVertex,
                    targetVertex),
                this.mask);
        }

        return edges;
    }

    public E getEdge(V sourceVertex, V targetVertex)
    {
        Set<E> edges = getAllEdges(sourceVertex, targetVertex);

        if ((edges == null) || edges.isEmpty()) {
            return null;
        } else {
            return edges.iterator().next();
        }
    }

    public EdgeFactory<V, E> getEdgeFactory()
    {
        return this.base.getEdgeFactory();
    }

    public V getEdgeSource(E edge)
    {
        assert (edgeSet().contains(edge));

        return this.base.getEdgeSource(edge);
    }

    public V getEdgeTarget(E edge)
    {
        assert (edgeSet().contains(edge));

        return this.base.getEdgeTarget(edge);
    }

    public double getEdgeWeight(E edge)
    {
        assert (edgeSet().contains(edge));

        return this.base.getEdgeWeight(edge);
    }

    /**
     * @see DirectedGraph#incomingEdgesOf(Object)
     */
    public Set<E> incomingEdgesOf(V vertex)
    {
        assertVertexExist(vertex);

        return new MaskEdgeSet<V, E>(
            this.base,
            ((DirectedGraph<V, E>) this.base).incomingEdgesOf(vertex),
            this.mask);
    }

    /**
     * @see DirectedGraph#inDegreeOf(Object)
     */
    public int inDegreeOf(V vertex)
    {
        return incomingEdgesOf(vertex).size();
    }

    /**
     * @see DirectedGraph#outDegreeOf(Object)
     */
    public int outDegreeOf(V vertex)
    {
        return outgoingEdgesOf(vertex).size();
    }

    /**
     * @see DirectedGraph#outgoingEdgesOf(Object)
     */
    public Set<E> outgoingEdgesOf(V vertex)
    {
        assertVertexExist(vertex);

        return new MaskEdgeSet<V, E>(
            this.base,
            ((DirectedGraph<V, E>) this.base).outgoingEdgesOf(vertex),
            this.mask);
    }

    /**
     * @see Graph#removeAllEdges(Collection)
     */
    public boolean removeAllEdges(Collection<? extends E> edges)
    {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    /**
     * @see Graph#removeAllEdges(Object, Object)
     */
    public Set<E> removeAllEdges(V sourceVertex, V targetVertex)
    {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    /**
     * @see Graph#removeAllVertices(Collection)
     */
    public boolean removeAllVertices(Collection<? extends V> vertices)
    {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    /**
     * @see Graph#removeEdge(Object)
     */
    public boolean removeEdge(E e)
    {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    /**
     * @see Graph#removeEdge(Object, Object)
     */
    public E removeEdge(V sourceVertex, V targetVertex)
    {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    /**
     * @see Graph#removeVertex(Object)
     */
    public boolean removeVertex(V v)
    {
        throw new UnsupportedOperationException(UNMODIFIABLE);
    }

    public Set<V> vertexSet()
    {
        return this.vertices;
    }
}

// End MaskSubgraph.java
