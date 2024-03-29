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
 * AbstractPathElement.java
 * -------------------------
 * (C) Copyright 2006-2008, by France Telecom
 *
 * Original Author:  Guillaume Boulmier and Contributors.
 * Contributor(s):   John V. Sichi
 *
 * $Id: AbstractPathElement.java 645 2008-09-30 19:44:48Z perfecthash $
 *
 * Changes
 * -------
 * 05-Jan-2006 : Initial revision (GB);
 * 14-Jan-2006 : Added support for generics (JVS);
 *
 */
package org.jgrapht.alg;

import java.util.*;

import org.jgrapht.*;


/**
 * A new path is created from a path concatenated to an edge. It's like a linked
 * list.<br>
 * The empty path is composed only of one vertex.<br>
 * In this case the path has no previous path element.<br>
 * .
 *
 * <p>NOTE jvs 1-Jan-2008: This is an internal data structure for use in
 * algorithms. For returning paths to callers, use the public {@link GraphPath}
 * interface instead.
 *
 * @author Guillaume Boulmier
 * @since July 5, 2007
 */
abstract class AbstractPathElement<V, E extends org.jgrapht.graph.DefaultEdge>
{
    //~ Instance fields --------------------------------------------------------

    /**
     * Number of hops of the path.
     */
    protected int nHops;

    /**
     * Edge reaching the target vertex of the path.
     */
    protected E prevEdge;

    /**
     * Previous path element.
     */
    protected AbstractPathElement<V, E> prevPathElement;

    /**
     * Target vertex.
     */
    private V vertex;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a path element by concatenation of an edge to a path element.
     *
     * @param pathElement
     * @param edge edge reaching the end vertex of the path element created.
     */
    protected AbstractPathElement(
        Graph<V, E> graph,
        AbstractPathElement<V, E> pathElement,
        E edge)
    {
        this.vertex =
            Graphs.getOppositeVertex(
                graph,
                edge,
                pathElement.getVertex());
        this.prevEdge = edge;
        this.prevPathElement = pathElement;

        this.nHops = pathElement.getHopCount() + 1;
    }

    /**
     * Copy constructor.
     *
     * @param original source to copy from
     */
    protected AbstractPathElement(AbstractPathElement<V, E> original)
    {
        this.nHops = original.nHops;
        this.prevEdge = original.prevEdge;
        this.prevPathElement = original.prevPathElement;
        this.vertex = original.vertex;
    }

    /**
     * Creates an empty path element.
     *
     * @param vertex end vertex of the path element.
     */
    protected AbstractPathElement(V vertex)
    {
        this.vertex = vertex;
        this.prevEdge = null;
        this.prevPathElement = null;

        this.nHops = 0;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Returns the path as a list of edges.
     *
     * @return list of <code>Edge</code>.
     */
    public List<E> createEdgeListPath()
    {
        List<E> path = new ArrayList<E>();
        AbstractPathElement<V, E> pathElement = this;

        // while start vertex is not reached.
        while (pathElement.getPrevEdge() != null) {
            path.add(pathElement.getPrevEdge());

            pathElement = pathElement.getPrevPathElement();
        }

        Collections.reverse(path);

        return path;
    }

    /**
     * Returns the number of hops (or number of edges) of the path.
     *
     * @return .
     */
    public int getHopCount()
    {
        return this.nHops;
    }

    /**
     * Returns the edge reaching the target vertex of the path.
     *
     * @return <code>null</code> if the path is empty.
     */
    public E getPrevEdge()
    {
        return this.prevEdge;
    }

    /**
     * Returns the previous path element.
     *
     * @return <code>null</code> is the path is empty.
     */
    public AbstractPathElement<V, E> getPrevPathElement()
    {
        return this.prevPathElement;
    }

    /**
     * Returns the target vertex of the path.
     *
     * @return .
     */
    public V getVertex()
    {
        return this.vertex;
    }
}

// End AbstractPathElement.java
