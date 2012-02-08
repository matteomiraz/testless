package org.jgrapht.graph;

import org.jgrapht.EdgeFactory;

public class TFUL_DefaultEdgeFactory<V> implements EdgeFactory<V, DefaultEdge> {

	public DefaultEdge createEdge(V sourceVertex, V targetVertex) {
		DefaultEdge edge = new DefaultEdge();
		// DefaultEdge edge = ( weighted ? new DefaultWeightedEdge() : new DefaultEdge());

		edge.source = sourceVertex;
		edge.target = targetVertex;

		return edge;
	}

}
