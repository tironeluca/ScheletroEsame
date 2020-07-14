package scheletri;

public class visita_percorso_interno {
	

	// RICHIESTA


	 Alla pressione del bottone “Test Connessione”:
	• verificare se è possibile raggiungere l’aeroporto di arrivo a partire da quello di partenza;
	• stampare un possibile percorso (se esiste) tra i due aeroporti.


	//RISPOSTA 

	public List<Airport> trovaPercorso(Airport a1, Airport a2){
			List<Airport> percorso = new ArrayList<Airport>();
			
			//reinizializzo la mappa della visita
			visita = new HashMap<>();
			
			BreadthFirstIterator<Airport, DefaultWeightedEdge> it = new BreadthFirstIterator<>(this.grafo,a1);
			
			
			//aggiungo la "radice" del mio albero di visita
			visita.put(a1, null);
			
			it.addTraversalListener(new TraversalListener<Airport, DefaultWeightedEdge>(){

				@Override
				public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
					// TODO Auto-generated method stub
					
				}

		
				
				@Override
				public void edgeTraversed(EdgeTraversalEvent<DefaultWeightedEdge> e) {
					Airport sorgente = grafo.getEdgeSource(e.getEdge());
					Airport destinazione = grafo.getEdgeTarget(e.getEdge());
					
					if(!visita.containsKey(destinazione) && visita.containsKey(sorgente))
						visita.put(destinazione,sorgente);
					else if (!visita.containsKey(sorgente) && visita.containsKey(destinazione)){
						visita.put(sorgente, destinazione);
					}

				}

				@Override
				public void vertexTraversed(VertexTraversalEvent<Airport> e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void vertexFinished(VertexTraversalEvent<Airport> e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			
			while(it.hasNext()) {
				it.next();
			}
			
			
			if(!visita.containsKey(a1) || !visita.containsKey(a2)) {
				//i due aeroporti non sono collegati
				return null;
			}
			
			Airport step = a2;
			
			while(!step.equals(a1)) {
				percorso.add(step);
				step = visita.get(step);
			}
			
			percorso.add(a1);
			
			return percorso;
			
		}

}
