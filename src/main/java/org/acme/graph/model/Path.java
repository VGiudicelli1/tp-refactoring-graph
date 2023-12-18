package org.acme.graph.model;

import java.util.List;

public class Path {
	private List<Edge> edges;

	public Path(List<Edge> edges) {
		this.edges = edges;
	}

	public double getLength() {
		double length = 0.0;
		for (Edge e : this.edges) {
			length += e.getCost();
		}
		return length;
	}

	public List<Edge> getEdges() {
		return this.edges;
	}
}
