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
/* -----------------------
 * WeightedMultigraph.java
 * -----------------------
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh
 * Contributor(s):   Christian Hammer
 *
 * $Id: WeightedMultigraph.java 645 2008-09-30 19:44:48Z perfecthash $
 *
 * Changes
 * -------
 * 05-Aug-2003 : Initial revision (BN);
 * 06-Aug-2005 : Made generic (CH);
 * 28-May-2006 : Moved connectivity info from edge to graph (JVS);
 *
 */
package org.jgrapht.graph;

import org.jgrapht.*;


/**
 * A weighted multigraph. A weighted multigraph is a non-simple undirected graph
 * in which no loops are permitted, but multiple edges between any two vertices
 * are. The edges of a weighted multigraph have weights. If you're unsure about
 * multigraphs, see: <a href="http://mathworld.wolfram.com/Multigraph.html">
 * http://mathworld.wolfram.com/Multigraph.html</a>.
 */
public class WeightedMultigraph<V, E extends org.jgrapht.graph.DefaultWeightedEdge>
    extends Multigraph<V, E>
    implements WeightedGraph<V, E>
{
    //~ Static fields/initializers ---------------------------------------------

    private static final long serialVersionUID = 3544671793370640696L;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new weighted multigraph with the specified edge factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public WeightedMultigraph(EdgeFactory<V, E> ef)
    {
        super(ef);
    }
}

// End WeightedMultigraph.java
