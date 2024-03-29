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
/* -------------------
 * RingGraphGenerator.java
 * -------------------
 * (C) Copyright 2003-2008, by John V. Sichi and Contributors.
 *
 * Original Author:  John V. Sichi
 * Contributor(s):   -
 *
 * $Id: RingGraphGenerator.java 645 2008-09-30 19:44:48Z perfecthash $
 *
 * Changes
 * -------
 * 16-Sep-2003 : Initial revision (JVS);
 *
 */
package org.jgrapht.generate;

import java.util.*;

import org.jgrapht.*;

import tful.Contracts;


/**
 * Generates a ring graph of any size. A ring graph is a graph that contains a
 * single cycle that passes through all its vertices exactly once. For a
 * directed graph, the generated edges are oriented consistently around the
 * ring.
 *
 * @author John V. Sichi
 * @since Sep 16, 2003
 */
public class RingGraphGenerator<V, E extends org.jgrapht.graph.DefaultEdge>
    implements GraphGenerator<V, E, V>
{
    //~ Instance fields --------------------------------------------------------

    private int size;

    //~ Constructors -----------------------------------------------------------

    /**
     * Construct a new RingGraphGenerator.
     *
     * @param size number of vertices to be generated
     *
     * @throwsa IllegalArgumentException if the specified size is negative.
     */
    public RingGraphGenerator(int size) throws IllegalArgumentException
    {
    	Contracts.assertSmall(size);
        if (size < 0) {
            throw new IllegalArgumentException("must be non-negative");
        }

        this.size = size;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public void generateGraph(
        Graph<V, E> target,
        VertexFactory<V> vertexFactory,
        Map<String, V> resultMap)
    {
        if (size < 1) {
            return;
        }

        LinearGraphGenerator<V, E> linearGenerator =
            new LinearGraphGenerator<V, E>(size);
        Map<String, V> privateMap = new HashMap<String, V>();
        linearGenerator.generateGraph(target, vertexFactory, privateMap);

        V startVertex = privateMap.get(LinearGraphGenerator.START_VERTEX);
        V endVertex = privateMap.get(LinearGraphGenerator.END_VERTEX);
        target.addEdge(endVertex, startVertex);
    }
}

// End RingGraphGenerator.java
