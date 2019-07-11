package scheletri;

import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;

public class VisiteGrafi {

	//DIJKSTRA
	public List<E> trovaCamminoMinimo(E partenza, E arrivo){

		DijkstraShortestPath<E, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(this.grafo);
		GraphPath<E, DefaultWeightedEdge> path = dijkstra.getPath(partenza, arrivo);

		return path.getVertexList();	

	}

	//COMPONENTI CONNESSE (Connectivity Inspector)
	public int getNumComponentiConnesse() {

		ConnectivityInspector<E, DefaultEdge> inspector = new ConnectivityInspector<>(grafo);
		return inspector.connectedSets().size();
	}

	//Definisco classe dentro altra classe  (UFO SIGHTINGS)
	//Classe per intercettare gli eventi del grafo quando è scandito da un iteratore (LISTENER)
	public class EdgeTraversedListener implements TraversalListener<E, DefaultWeightedEdge> {

		Graph<E, DefaultWeightedEdge> grafo;
		Map<E,E> back;

		public EdgeTraversedListener(Map<Fermata, Fermata> back,Graph<Fermata, DefaultWeightedEdge> grafo) {
			super();
			this.back = back;
			this.grafo=grafo;
		}

		@Override
		public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {		
		}

		@Override
		public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {		
		}

		public void edgeTraversed(EdgeTraversalEvent<DefaultWeightedEdge> ev) {	

			/*Back codifica le relazioni si tipo CHILD -> PARENT
			 * 
			 * Per un nuovo vertice 'CHILD' scoperto devo avere che:
			 * 
			 * -CHILD è ancora sconosciuto (non ancora scoperto)
			 * -PARENT è gia stato visitato
			 */

			//Estraggo gli estremi dell'arco
			E sourceVertex = grafo.getEdgeSource(ev.getEdge());
			E targetVertex = grafo.getEdgeTarget(ev.getEdge());

			/*
			 * Se il grafo e' orientato, allora SOURCE==PARENT , TARGET==CHILD
			 * Se il grafo NON è orientato potrebbe essere il contrario..
			 */

			//Codice da riutilizzare
			if( !back.containsKey(targetVertex) && back.containsKey(sourceVertex)) {
				back.put(targetVertex, sourceVertex);
			} else if(!back.containsKey(sourceVertex) && back.containsKey(targetVertex)) {
				back.put(sourceVertex, targetVertex);
			}
		}

		@Override
		public void vertexFinished(VertexTraversalEvent<E> arg0) {		
		}

		@Override
		public void vertexTraversed(VertexTraversalEvent<E> arg0) {		
		}

	}

	//Iterazione con la quale attraversiamo il grafo e ci aggiungiamo via via i vertici che troviamo (N.B. NON E' UN CAMMINO!)
		public List<Fermata> fermateRaggiungibili(E source){

			List<E> risultato = new ArrayList<E>();
			backVisit = new HashMap<>();

			//Creo iteratore e lo associo al grafo       
			//GraphIterator<Fermata, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo,source); //in ampiezza
			GraphIterator<E, DefaultWeightedEdge> it = new DepthFirstIterator<>(this.grafo,source); //in profondita'

			it.addTraversalListener(new EdgeTraversedListener(backVisit, grafo)); //Questa classe potrebbe essere definita anche dentro la classe Model
			//A fine iterazione mi ritroverò la mappa back riempita

			//Devo popolare la mappa almeno col nodo sorgente
			backVisit.put(source, null);

			while(it.hasNext()) {
				risultato.add(it.next());
			}

			return risultato;
		}
		
	// LISTA DI ARCHI CON PESO MASSIMO
		
		private List<Adiacenza> trovaMigliori() {
			
			int temp = 0;

			List<Adiacenza> result = new LinkedList<Adiacenza>();

			for(DefaultWeightedEdge d: this.graph.edgeSet()) {

				if(graph.getEdgeWeight(d)>temp)

					temp=(int) graph.getEdgeWeight(d);

			}

			for(DefaultWeightedEdge d: this.graph.edgeSet()) {

				if(graph.getEdgeWeight(d)==temp) {

					result.add(new Adiacenza(graph.getEdgeSource(d).getRaceId(), graph.getEdgeTarget(d).getRaceId(),(int)(graph.getEdgeWeight(d))));

					

				}

			}

		return result;
		}
		
		
		// VERTICE CON PESO MASSIMO (INCOMING E OUTGOING)
		
		private String trovaMigliore() {
			
			String ris= "Il miglior pilota e' ";
			int peso=0;
			Driver best= null;
			
			
			for (Driver d: this.graph.vertexSet())
			{
				
				int temp= calcolaPeso(d);
				
				if (temp>peso)
				{
					peso=temp;
					best=d;
				}
			}
			
			
			ris+= best.getForename() + " " + best.getSurname()+ " "+ peso;
			
			
			return ris;
		}




		private int calcolaPeso(Driver d) {
			
			Set<DefaultWeightedEdge> incoming= this.graph.incomingEdgesOf(d);
			Set<DefaultWeightedEdge> outgoing= this.graph.outgoingEdgesOf(d);
			
			int inc=0;
			int out=0;
			
			for (DefaultWeightedEdge e: incoming) {
				
				inc+= this.graph.getEdgeWeight(e);
				
			}
			
			for (DefaultWeightedEdge e: outgoing) {
				
				out+= this.graph.getEdgeWeight(e);
				
			}
			
			int peso= out-inc;
			
			return peso;
			
		}
		
		
	// VERTICI CON SOMMA DEI PESI DEGLI STATI VICINI (NEIGHBOR LIST)
		
		private String calcolaVicini() {
			String ris= "Ogni stato con relativo peso: \n";
			
			for(State s: this.graph.vertexSet())
			{ 	int peso=0;
				List<State> vicini= Graphs.neighborListOf(this.graph, s);
				for(State v: vicini)
				{
					DefaultWeightedEdge e= graph.getEdge(s, v);
					int temp= (int) graph.getEdgeWeight(e);
					peso+= temp;
					
				}
				
				ris+= s + " "+ peso + "\n";
			}
			
			return ris;
		}
		
	
	// DIMENSIONE COMPONENTE CONNESSA
		
		
public int componenteConnessa(Integer cod) {
			
			ArtObject a= idMap.get(cod);
			
			Set<ArtObject> visitati = new HashSet<>(); 
			DepthFirstIterator<ArtObject, DefaultWeightedEdge> dfv = new DepthFirstIterator<>(this.graph, a); 
			while (dfv.hasNext()) 
			{visitati.add(dfv.next());}
			
			return visitati.size();
// SE SERVE NUMERO DI ELEMENTI CONNESSI AGGIUNGI -1 AL SIZE PER TOGLIERE ELEMENTO STESSO
			
		}






		
		
}
