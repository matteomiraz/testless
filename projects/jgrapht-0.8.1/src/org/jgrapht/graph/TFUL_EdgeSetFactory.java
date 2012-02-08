package org.jgrapht.graph;

import java.util.LinkedHashSet;

public class TFUL_EdgeSetFactory<V, E> implements EdgeSetFactory<V, E> {

	public java.util.Set<E> createEdgeSet(V vertex) {
		return new LinkedHashSet<E>();
	};
}
