package org.acme.graph.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acme.graph.model.Edge;
import org.acme.graph.model.Graph;
import org.acme.graph.model.Vertex;

public class PathTree {

	private Map<Vertex, PathNode> nodes;

	public PathTree() {
		this.nodes = new HashMap<Vertex, PathNode>();
	}

	/**
	 * Prépare le graphe pour le calcul du plus court chemin
	 * 
	 * @param source
	 */
	void initGraph(Graph graph, Vertex source) {
		for (Vertex vertex : graph.getVertices()) {
			PathNode node = this.getNode(vertex);
			node.setCost(source == vertex ? 0.0 : Double.POSITIVE_INFINITY);
			node.setReachingEdge(null);
			node.setVisited(false);
		}
	}

	/**
	 * Construit le chemin en remontant les relations incoming edge
	 * 
	 * @param target
	 * @return
	 */
	List<Edge> buildPath(Vertex target) {
		List<Edge> result = new ArrayList<>();

		Edge current = this.getNode(target).getReachingEdge();
		do {
			result.add(current);
			current = this.getNode(current.getSource()).getReachingEdge();
		} while (current != null);

		Collections.reverse(result);
		return result;
	}

	PathNode getNode(Vertex v) {
		PathNode node = this.nodes.get(v);
		if (node == null) {
			node = new PathNode();
			this.nodes.put(v, node);
		}
		return node;
	}
}
