package org.jgrapht.event;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;

public class TFUL_GraphListener<V, E extends DefaultEdge> implements GraphListener<V, E> {

	private final List<String> events = new LinkedList<String>();

	public void vertexAdded(GraphVertexChangeEvent<V> e) {
		events.add("ADDED V " + e.vertex.toString());
	}

	public void vertexRemoved(GraphVertexChangeEvent<V> e) {
		events.add("REMOVED V " + e.vertex.toString());
	}

	public void edgeAdded(GraphEdgeChangeEvent<V, E> e) {
		events.add("ADDED E " + e.edge.toString());
	}

	public void edgeRemoved(GraphEdgeChangeEvent<V, E> e) {
		events.add("REMOVED E " + e.edge.toString());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String e : events) sb.append(e).append("\n");
		events.clear();

		return sb.toString();
	}
}
