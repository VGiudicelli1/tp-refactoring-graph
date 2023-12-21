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

	/**
	 * Prépare le graphe pour le calcul du plus court chemin
	 * 
	 * @param source
	 */
	PathTree(Graph graph, Vertex source) {
		this.nodes = new HashMap<Vertex, PathNode>();
		this.getNode(source).setCost(0.0);
	}

	/**
	 * Construit le chemin en remontant les relations incoming edge
	 * 
	 * @param target
	 * @return
	 */
	List<Edge> getPath(Vertex target) {
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
			node.setCost(Double.POSITIVE_INFINITY);
			node.setReachingEdge(null);
			node.setVisited(false);
			this.nodes.put(v, node);
		}
		return node;
	}

	boolean isReached(Vertex destination) {
		return this.nodes.get(destination) != null && this.getNode(destination).getReachingEdge() != null;
	}
}
