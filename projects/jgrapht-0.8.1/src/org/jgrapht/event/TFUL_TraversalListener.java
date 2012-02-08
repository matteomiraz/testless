package org.jgrapht.event;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;

public class TFUL_TraversalListener<V, E extends DefaultEdge> implements TraversalListener<V, E> {

	private final List<String> events = new LinkedList<String>();

	public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
		events.add("connectedComponentFinished");
	}

	public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
		events.add("connectedComponentStarted");
	}

	public void edgeTraversed(EdgeTraversalEvent<V, E> e) {
		events.add("edgeTraversed " + e.edge.toString());
	}

	public void vertexTraversed(VertexTraversalEvent<V> e) {
		events.add("vertexTraversed " + e.vertex.toString());
	}

	public void vertexFinished(VertexTraversalEvent<V> e) {
		events.add("vertexFinished " + e.vertex.toString());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String e : events) sb.append(e).append("\n");
		events.clear();

		return sb.toString();
	}
}
