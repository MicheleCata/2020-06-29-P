package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleWeightedGraph<Match, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer,Match> idMap;
	
	private List<Match> percorsoMigliore;
	private int max;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap= new HashMap<>();
		dao.listAllMatches(idMap);
	}
	
	public void creaGrafo(int mese, int min ) {
		grafo = new SimpleWeightedGraph<Match, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getVertici(mese, idMap));
		
		for (Adiacenze a: dao.getAdiacenze(idMap, min,mese)) {
			if (this.grafo.containsVertex(a.getM1()) && this.grafo.containsVertex(a.getM2()))
			Graphs.addEdgeWithVertices(grafo, a.getM1(), a.getM2(),a.getPeso());
		}
		
		System.out.format("Grafo creato con %d vertici e %d archi\n",
 				this.grafo.vertexSet().size(), this.grafo.edgeSet().size()); 
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Match> getVertex(){
		return new ArrayList<Match> (grafo.vertexSet());
	}
	
	public List<Adiacenze> getConnessioniMax(){
		List<Adiacenze> result = new ArrayList<>();
		int max = 0;
		
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)>max) {
				max=(int) grafo.getEdgeWeight(e); 
			}
			
		}
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)==max) {
			result.add(new Adiacenze(grafo.getEdgeSource(e),grafo.getEdgeTarget(e),max));
		}
		
		}
		return result;
	}
	
	public List<Match> getPercorso (Match partenza, Match arrivo){
		this.percorsoMigliore= new ArrayList<>();
		List<Match> parziale =  new ArrayList<>();
		max=0;
		parziale.add(partenza);
		cerca(parziale, arrivo);
		
		return percorsoMigliore;
		
	}

	private void cerca(List<Match> parziale, Match arrivo) {
		
		Match ultimo = parziale.get(parziale.size()-1);
		if (ultimo.equals(arrivo)) {
			if(calcolaPeso(parziale)>max) {
				percorsoMigliore= new ArrayList<>(parziale);
				max=calcolaPeso(parziale);
			}
		}
		
		for (Match m: Graphs.neighborListOf(grafo, ultimo)) {
			if (!parziale.contains(m) && m.teamHomeID!=ultimo.teamHomeID && m.teamAwayID!=ultimo.teamAwayID && m.teamHomeID!=ultimo.teamAwayID) {
					parziale.add(m);
					cerca(parziale,arrivo);
					parziale.remove(parziale.size()-1);
				}
			}
		}
	

	private Integer calcolaPeso(List<Match> parziale) {
		Integer pesoTot=0;
		for (int i=1; i<parziale.size(); i++) {
			Match m1= parziale.get(i-1);
			Match m2= parziale.get(i);
			
			pesoTot+= (int) grafo.getEdgeWeight(grafo.getEdge(m1, m2));
		}
		return pesoTot;
	}
	
}
