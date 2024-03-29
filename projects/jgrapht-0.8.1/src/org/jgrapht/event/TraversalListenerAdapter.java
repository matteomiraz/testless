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
/* -----------------------------
 * TraversalListenerAdapter.java
 * -----------------------------
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh
 * Contributor(s):   Christian Hammer
 *
 * $Id: TraversalListenerAdapter.java 645 2008-09-30 19:44:48Z perfecthash $
 *
 * Changes
 * -------
 * 06-Aug-2003 : Initial revision (BN);
 * 11-Aug-2003 : Adaptation to new event model (BN);
 * 11-Mar-2004 : Made generic (CH);
 *
 */
package org.jgrapht.event;

import org.jgrapht.graph.DefaultEdge;

/**
 * An empty do-nothing implementation of the {@link TraversalListener} interface
 * used for subclasses.
 *
 * @author Barak Naveh
 * @since Aug 6, 2003
 */
public class TraversalListenerAdapter<V, E extends DefaultEdge>
    implements TraversalListener<V, E>
{
    //~ Methods ----------------------------------------------------------------

    /**
     * @see TraversalListener#connectedComponentFinished(ConnectedComponentTraversalEvent)
     */
    public void connectedComponentFinished(
        ConnectedComponentTraversalEvent e)
    {
    	System.out.println("TraversalListenerAdapter.connectedComponentFinished");
    }

    /**
     * @see TraversalListener#connectedComponentStarted(ConnectedComponentTraversalEvent)
     */
    public void connectedComponentStarted(ConnectedComponentTraversalEvent e)
    {
    	System.out.println("TraversalListenerAdapter.connectedComponentStarted");
    }

    /**
     * @see TraversalListener#edgeTraversed(EdgeTraversalEvent)
     */
    public void edgeTraversed(EdgeTraversalEvent<V, E> e)
    {
    	System.out.println("TraversalListenerAdapter.edgeTraversed");
    }

    /**
     * @see TraversalListener#vertexTraversed(VertexTraversalEvent)
     */
    public void vertexTraversed(VertexTraversalEvent<V> e)
    {
    	System.out.println("TraversalListenerAdapter.vertexTraversed");
    }

    /**
     * @see TraversalListener#vertexFinished(VertexTraversalEvent)
     */
    public void vertexFinished(VertexTraversalEvent<V> e)
    {
    	System.out.println("TraversalListenerAdapter.vertexFinished");
    }
}

// End TraversalListenerAdapter.java
