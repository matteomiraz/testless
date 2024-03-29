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
 * CompleteGraphGenerator.java
 * -------------------
 * (C) Copyright 2003-2008, by Tim Shearouse and Contributors.
 *
 * Original Author:  Tim Shearouse
 * Contributor(s):   -
 *
 * $Id: CompleteGraphGenerator.java 598 2008-04-13 02:50:13Z perfecthash $
 *
 * Changes
 * -------
 * 10-Feb-2008 : Initial revision (TS);
 *
 */
package org.jgrapht.generate;

import java.util.*;

import org.jgrapht.*;

import tful.Contracts;


/**
 * Generates a complete graph of any size. A complete graph is a graph where
 * every vertex shares an edge with every other vertex. If it is a directed
 * graph, then edges must always exist in both directions. On a side note, a
 * complete graph is the least efficient possible graph in terms of memory and
 * cpu usage. Note: This contructor was designed for a simple undirected or
 * directed graph. It will act strangely when used with certain graph types,
 * such as undirected multigraphs. Note, though, that a complete undirected
 * multigraph is rather senseless -- you can keep adding edges and the graph is
 * never truly complete.
 *
 * @author Tim Shearouse
 * @since Nov 02, 2008
 */
public class CompleteGraphGenerator<V, E extends org.jgrapht.graph.DefaultEdge>
    implements GraphGenerator<V, E, V>
{
    //~ Instance fields --------------------------------------------------------

    private int size;

    //~ Constructors -----------------------------------------------------------

    /**
     * Construct a new CompleteGraphGenerator.
     *
     * @param size number of vertices to be generated
     *
     * @throwsa IllegalArgumentException if the specified size is negative.
     */
    public CompleteGraphGenerator(int size) throws IllegalArgumentException
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

        //Add all the vertices to the set
        for (int i = 0; i < size; i++) {
            V newVertex = vertexFactory.createVertex();
            target.addVertex(newVertex);
        }

        /*
         * We want two iterators over the vertex set, one fast and one slow.
         * The slow one will move through the set once. For each vertex,
         * the fast iterator moves through the set, adding an edge to all
         * vertices we haven't connected to yet.
         *
         * If we have an undirected graph, the second addEdge call will return
         * nothing; it will not add a second edge.
         */
        Iterator<V> slowI = target.vertexSet().iterator();
        Iterator<V> fastI;

        while (slowI.hasNext()) { //While there are more vertices in the set

            V latestVertex = slowI.next();
            fastI = target.vertexSet().iterator();

            //Jump to the first vertex *past* latestVertex
            while (fastI.next() != latestVertex) {
                ;
            }

            //And, add edges to all remaining vertices
            V temp;
            while (fastI.hasNext()) {
                temp = fastI.next();
                target.addEdge(latestVertex, temp);
                target.addEdge(temp, latestVertex);
            }
        }
    }
}

// End CompleteGraphGenerator.java
