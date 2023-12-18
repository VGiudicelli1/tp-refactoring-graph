package org.acme.graph.model;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * Un sommet dans un graphe
 * 
 * @author MBorne
 *
 */
public class Vertex {

	/**
	 * Identifiant du sommet
	 */
	private String id;

	/**
	 * Position du sommet
	 */
	private Coordinate coordinate;

	private List<Edge> inEdges;
	private List<Edge> outEdges;

	Vertex() {
		this.inEdges = new ArrayList<Edge>();
		this.outEdges = new ArrayList<Edge>();
	}

	@JsonIgnore
	public List<Edge> getInEdges() {
		return this.inEdges;
	}

	@JsonIgnore
	public List<Edge> getOutEdges() {
		return this.outEdges;
	}

	void AddInEdge(Edge in) {
		this.inEdges.add(in);
	}

	void addOutEdge(Edge out) {
		this.outEdges.add(out);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public String toString() {
		return id;
	}

}
