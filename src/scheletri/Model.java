package scheletri;

import java.awt.Event;
import java.awt.font.GraphicAttribute;
import java.lang.annotation.Target;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
	
	// EDITOR
	String string = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ._";
	
	//inserire tipo di dao
	private EventsDao dao;
	
	//scelta valore mappa
	private Map<Integer,E> idMap;
	
	//scelta tipo valori lista
	private List<E> vertex;
	
	//scelta tra uno dei due edges
	private List<Adiacenza> edges;
	
	//scelta tipo vertici e tipo archi
	private Graph<E,/*DefaultEdge*/> graph;
	
	// Recursion
	//inserire tipo valori lista best
	private List<E> best;
	
	
	public Model() {
		
		//inserire tipo dao
		dao  = new EventsDao();
		//inserire tipo values
		idMap = new HashMap<Integer,E>();
	}
	
	public void creaGrafo() {
		
		//scelta tipo vertici e archi
		graph = new SimpleWeightedGraph<E,DefaultEdge>(/*DefaultEdge.class*/);
		
		//scelta tipo valori lista
		vertex = new ArrayList<E>(dao.getVertex(/*parametri*/));
		Graphs.addAllVertices(graph,vertex);
		
		edges = new ArrayList<Adiacenza>(dao.getEdges(/*parametri*/));
		
		for(Adiacenza a : edges) {
			
			//CASO BASE POTRESTI DOVER AGGIUNGERE CONTROLLI
			/*District*/ source = idMap.get(a.getId1());
			/*District*/ target = idMap.get(a.getId2());
			double peso = a.getPeso();
			Graphs.addEdge(graph,source,target,peso);
			System.out.println("AGGIUNTO ARCO TRA: "+source.toString()+" e "+target.toString());
			
		}
		
		System.out.println("#vertici: "graph.vertexSet().size());
		System.out.println("#archi: "graph.edgeSet().size());
		
	}
	
	//inserire tipo valori lista best
	public List<E> trovaSequenza() {
		
		best = new ArrayList<E>();
		List<E> partial = new ArrayList<E>();
		
		recursion(partial);
		
		return best;
	}
	
	private void recursion(partial) {
		
		//in caso ci fosse un L max
		if () {
			
			System.out.println("NEW BEST");
			best = new ArrayList<E>(partial);
			System.out.println("ESCO LIVELLO MAX");
			return;
		}
		
		if ( ) {
			
			System.out.println("ESCO");
			return;
		}
		
		for ( ) {
			
			partial.add(/*valore da aggiungere*/);
			System.out.println("AGGIUNO");
			recursion(partial);
			System.out.println("RICORRO");
			partial.remove(partial.size()-1);
			System.out.println("RIMUOVO");
		}
		
		//senza L max
		if() {
			
			best = new ArrayList<E>(partial);
			System.out.println("NEW BEST");
			
		}
		
		if(controlla(/*parametri*/)) {
			
			parziale.add(/*valore da aggiungere*/);
			System.out.println("AGGIUNO");
			recursion(partial);
			System.out.println("RICORRO");
			partial.remove(partial.size()-1);
			System.out.println("RIMUOVO");
			
		} else {
			
			System.out.println("ESCO");
			return;
		}
	}
	
	public Graph<E, /*DefaultWeightedEdge*/> getGrafo() {
		return graph;
	}

	public Map<Integer, E> getidMap() {
		return idMap;
	}

	public List<E> getVertex() {
		return vertex;
	}

	public List<Adiacenza> getEdges() {
		return edges;
	}

	public List<E> getBest() {
		return best;
	}

}
