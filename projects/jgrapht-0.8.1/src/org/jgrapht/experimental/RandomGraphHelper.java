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
 * RandomGraphHelper.java
 * -------------------
 * (C) Copyright 2003-2008, by Michael Behrisch and Contributors.
 *
 * Original Author:  Michael Behrisch
 * Contributor(s):   -
 *
 * $Id: RandomGraphHelper.java 645 2008-09-30 19:44:48Z perfecthash $
 *
 * Changes
 * -------
 * 13-Sep-2004 : Initial revision (MB);
 *
 */
// package org.jgrapht.generate;
package org.jgrapht.experimental;

import java.util.*;

import org.jgrapht.*;

import tful.Contracts;
import tful.arrays.ObjectArray;


/**
 * UniformRandomGraphGenerator generates a <a
 * href="http://mathworld.wolfram.com/RandomGraph.html">uniform random graph</a>
 * of any size. A uniform random graph contains edges chosen independently
 * uniformly at random from the set of all possible edges.
 *
 * @author Michael Behrisch
 * @since Sep 13, 2004
 */
public final class RandomGraphHelper
{
    //~ Static fields/initializers ---------------------------------------------


    //~ Constructors -----------------------------------------------------------

    /**
     * .
     */
    private RandomGraphHelper()
    {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * @see GraphGenerator#generateGraph
     */
    @SuppressWarnings("unchecked")
    public static void addEdges(
        Graph target,
        List sourceVertices,
        List destVertices,
        int numEdges, long seed)
    {
    	Contracts.assertSmall(numEdges);
    	Random randSingleton = new Random(seed);

        int sourceSize = sourceVertices.size();
        int destSize = destVertices.size();

        for (int i = 0; i < numEdges; ++i) {
            while (
                target.addEdge(
                    sourceVertices.get(randSingleton.nextInt(
                            sourceSize)),
                    destVertices.get(randSingleton.nextInt(destSize)))
                == null)
            {
                ;
            }
        }
    }

    /**
     * .
     *
     * @param target
     * @param vertexFactory
     * @param numVertices
     *
     * @return
     */
    public static ObjectArray testful_addVertices(
            Graph target,
            VertexFactory vertexFactory,
            int numVertices)
    {
    	return new ObjectArray(addVertices(target, vertexFactory, numVertices));
    }

    @SuppressWarnings("unchecked")
    public static Object [] addVertices(
        Graph target,
        VertexFactory vertexFactory,
        int numVertices)
    {
    	Contracts.assertSmall(numVertices);
        Object [] vertices = new Object[numVertices];

        for (int i = 0; i < numVertices; ++i) {
            vertices[i] = vertexFactory.createVertex();
            target.addVertex(vertices[i]);
        }

        return vertices;
    }
}

// End RandomGraphHelper.java
