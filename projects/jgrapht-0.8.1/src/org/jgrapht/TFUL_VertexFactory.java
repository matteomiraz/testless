package org.jgrapht;

import java.util.concurrent.atomic.AtomicLong;

public class TFUL_VertexFactory implements VertexFactory<Object> {

	private AtomicLong last = new AtomicLong();

	public Object createVertex() {
		return last.incrementAndGet();
	};

}
