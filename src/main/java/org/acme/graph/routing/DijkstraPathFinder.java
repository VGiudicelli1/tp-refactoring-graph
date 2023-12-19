package org.acme.graph.routing;

import java.util.List;

import org.acme.graph.errors.NotFoundException;
import org.acme.graph.model.Edge;
import org.acme.graph.model.Graph;
import org.acme.graph.model.Vertex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * Utilitaire pour le calcul du plus court chemin dans un graphe
 * 
 * @author MBorne
 *
 */
public class DijkstraPathFinder {

	private static final Logger log = LogManager.getLogger(DijkstraPathFinder.class);

	private Graph graph;
	private PathTree pathTree;

	public DijkstraPathFinder(Graph graph) {
		this.pathTree = new PathTree();
		this.graph = graph;
	}

	/**
	 * Calcul du plus court chemin entre une origine et une destination
	 * 
	 * @param origin
	 * @param destination
	 * @return
	 */
	public List<Edge> findPath(Vertex origin, Vertex destination) {
		log.info("findPath({},{})...", origin, destination);
		this.pathTree.initGraph(this.graph, origin);
		Vertex current;
		PathNode destNode = this.pathTree.getNode(destination);
		while ((current = findNextVertex()) != null) {
			visit(current);
			if (destNode.getReachingEdge() != null) {
				log.info("findPath({},{}) : path found", origin, destination);
				return this.pathTree.buildPath(destination);
			}
		}
		log.info("findPath({},{}) : path not found", origin, destination);
		throw new NotFoundException(String.format("Path not found from '%s' to '%s'", origin, destination));
	}

	/**
	 * Parcourt les arcs sortants pour atteindre les sommets avec le meilleur coût
	 * 
	 * @param vertex
	 */
	private void visit(Vertex vertex) {
		log.trace("visit({})", vertex);
		List<Edge> outEdges = graph.getOutEdges(vertex);
		PathNode node = this.pathTree.getNode(vertex);
		/*
		 * On étudie chacun des arcs sortant pour atteindre de nouveaux sommets ou
		 * mettre à jour des sommets déjà atteint si on trouve un meilleur coût
		 */
		for (Edge outEdge : outEdges) {
			PathNode reachedNode = this.pathTree.getNode(outEdge.getTarget());
			/*
			 * Convervation de arc permettant d'atteindre le sommet avec un meilleur coût
			 * sachant que les sommets non atteint ont pour coût "POSITIVE_INFINITY"
			 */
			double newCost = node.getCost() + outEdge.getCost();
			if (newCost < reachedNode.getCost()) {
				reachedNode.setCost(newCost);
				reachedNode.setReachingEdge(outEdge);
			}
		}
		/*
		 * On marque le sommet comme visité
		 */
		node.setVisited(true);
	}

	/**
	 * Recherche le prochain sommet à visiter. Dans l'algorithme de Dijkstra, ce
	 * sommet est le sommet non visité le plus proche de l'origine du calcul de plus
	 * court chemin.
	 * 
	 * @return
	 */
	private Vertex findNextVertex() {
		double minCost = Double.POSITIVE_INFINITY;
		Vertex result = null;
		for (Vertex vertex : graph.getVertices()) {
			PathNode node = this.pathTree.getNode(vertex);
			// sommet déjà visité?
			if (node.isVisited()) {
				continue;
			}
			// sommet non atteint?
			if (node.getCost() == Double.POSITIVE_INFINITY) {
				continue;
			}
			// sommet le plus proche de la source?
			if (node.getCost() < minCost) {
				result = vertex;
			}
		}
		return result;
	}

}
