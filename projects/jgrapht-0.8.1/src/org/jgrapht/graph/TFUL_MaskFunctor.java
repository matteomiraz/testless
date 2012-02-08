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
 * MaskFunctor.java
 * -------------------------
 * (C) Copyright 2007-2008, by France Telecom
 *
 * Original Author:  Guillaume Boulmier and Contributors.
 *
 * $Id: MaskFunctor.java 645 2008-09-30 19:44:48Z perfecthash $
 *
 * Changes
 * -------
 * 05-Jun-2007 : Initial revision (GB);
 *
 */
package org.jgrapht.graph;

import java.util.HashSet;
import java.util.Set;

public class TFUL_MaskFunctor<V, E> implements MaskFunctor<V, E>
{

	private Set<E> eMasked = new HashSet<E>();
	private Set<V> vMasked = new HashSet<V>();

	public void addMaskEdge(E e) {
		eMasked.add(e);
	}

	public void clearMaskEdge(E e) {
		eMasked.remove(e);
	}

	public void addMaskVertex(V v) {
		vMasked.add(v);
	}

	public void clearMaskVertex(V v) {
		vMasked.remove(v);
	}

    public boolean isEdgeMasked(E edge) {
    	return (eMasked.contains(edge));
    }

    public boolean isVertexMasked(V vertex) {
    	return (vMasked.contains(vertex));
    }
}

// End MaskFunctor.java
