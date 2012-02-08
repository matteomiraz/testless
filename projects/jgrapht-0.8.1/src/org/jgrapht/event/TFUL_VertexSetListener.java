package org.jgrapht.event;

import java.util.LinkedList;
import java.util.List;

public class TFUL_VertexSetListener<V> implements VertexSetListener<V> {

	private final List<String> events = new LinkedList<String>();

	public void vertexAdded(GraphVertexChangeEvent<V> e) {
		events.add("ADDED " + e.vertex.toString());
	}

	public void vertexRemoved(GraphVertexChangeEvent<V> e) {
		events.add("REMOVED " + e.vertex.toString());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String e : events) sb.append(e).append("\n");
		events.clear();

		return sb.toString();
	}
}
