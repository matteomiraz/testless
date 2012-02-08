package org.jgrapht.graph;

import org.jgrapht.EdgeFactory;

public class TFUL_DefaultWeightedEdgeFactory<V> implements EdgeFactory<V, DefaultWeightedEdge> {

	public DefaultWeightedEdge createEdge(V sourceVertex, V targetVertex) {
		DefaultWeightedEdge edge = new DefaultWeightedEdge();

		edge.source = sourceVertex;
		edge.target = targetVertex;

		return edge;
	}

}
