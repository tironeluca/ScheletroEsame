package scheletri;

import java.awt.Event;
import java.awt.font.GraphicAttribute;
import java.lang.annotation.Target;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.sun.javafx.scene.traversal.TraverseListener;

import it.polito.tdp.model.District;
import javafx.geometry.Bounds;
import javafx.scene.Node;

public class Model {
	
	// EDITOR
	/*Windows/Preferences/Java/Editor/Content Assist
	Click su Enable Auto Activation
	Auto Activation delay = 0
	Auto Activation trigegrs for java = abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ._
	*/
	
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
		vertex = new ArrayList<E>(dao.getVertex(/*parametri*/, /*idMap*/));
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
		
		System.out.println("#vertici: "+graph.vertexSet().size());
		System.out.println("#archi: "+graph.edgeSet().size());
		
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
	
	//(RAGGIUNGIBILI)
		//Iterazione con la quale attraversiamo il grafo e ci aggiungiamo via via i vertici che troviamo (N.B. NON E' UN CAMMINO!)
			public List<Fermata> fermateRaggiungibili(E source){

			public void vertexTraversed(VertexTraversalEvent<E> arg0) {

				return risultato;
			}
			
			

			//OPPURE
			public List<E> trovaRaggiungibili(Country partenza) {

				List<E> raggiungibili = new ArrayList<E>();

				
				//CREO ITERATORE E LO ASSOCIO AL GRAFO      
				//GraphIterator<Fermata, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo,source); //in ampiezza
				GraphIterator<Country, DefaultEdge> it = new DepthFirstIterator<>(this.grafo,partenza); //in profondita'

				while(it.hasNext()) {
					raggiungibili.add(it.next());
				}

				//Pulisco il primo valore della lista che è l'elemento stesso
				return raggiungibili.subList(1, raggiungibili.size());
			}

			
			private double calcolaDist(District d1, District d2) {
				
				LatLng cord1 = new LatLng(d1.getLat(), d1.getLongi());
				LatLng cord2 = new LatLng(d2.getLat(), d2.getLongi());
				
				return LatLngTool.distance(cord1, cord2, LengthUnit.KILOMETER);
			}

}
