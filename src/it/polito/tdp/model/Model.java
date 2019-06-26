package it.polito.tdp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;

public class Model {
	private EventsDao dao;
	private Graph<Integer,DefaultWeightedEdge> grafo;
	private List<Integer> distretti;
	
	
	
	public Model() {
		dao = new EventsDao();
		//distretti = new ArrayList<Integer>();
		
	}
	public List<Integer> getAnni(){
		return dao.getAnni();
	}
	public void creaGrafo(Integer anno) {
		grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		distretti = dao.getDistricts(anno);
		Graphs.addAllVertices(grafo, distretti);
		
		for(Integer d1 : this.grafo.vertexSet()) {
			for(Integer d2:this.grafo.vertexSet()) {
				if(!d1.equals(d2)) {
					if(this.grafo.getEdge(d1, d2)==null) {
						Double lat1 = dao.getLatMedia(anno, d1);
						Double lat2 = dao.getLatMedia(anno, d2);
						
						Double lon1 = dao.getLonMedia(anno, d1);
						Double lon2 = dao.getLonMedia(anno, d2);
						
						Double distanzaMedia= LatLngTool.distance(new LatLng(lat1, lon1), new LatLng(lat2, lon2), LengthUnit.KILOMETER);
						Graphs.addEdgeWithVertices(grafo, d1, d2, distanzaMedia);
						
						
					}
				}
			}
		}
		System.out.println("VERTICI: "+grafo.vertexSet().size());
		System.out.println("ARCHI: "+grafo.edgeSet().size());
		
	}
	public List<Vicino> getVicini(Integer distretto){
		List<Vicino> vicini = new LinkedList<Vicino>();
		List<Integer> neighbors = Graphs.neighborListOf(grafo, distretto);
		for(Integer n:neighbors) {
			vicini.add(new Vicino(n, this.grafo.getEdgeWeight(this.grafo.getEdge(distretto, n))));
		}
		Collections.sort(vicini);
		return vicini;
	}
	public List<Integer> getDistretti() {
		// TODO Auto-generated method stub
		return distretti;
	}

	
	public List<Integer> getMesi() {
		List<Integer> mesi = new LinkedList<Integer>();
		for(int i = 1; i<=12; i++)
			mesi.add(i);
		return mesi;
	}
	
	public List<Integer> getGiorni() {
		List<Integer> giorni = new LinkedList<Integer>();
		for(int i = 1; i<=31; i++)
			giorni.add(i);
		return giorni;
	}
	

	
	public int simula(Integer anno, Integer mese, Integer giorno, Integer N) {
		Simulatore sim = new Simulatore();
		sim.init(N, anno, mese, giorno, grafo);
		return sim.run();
	}

}
