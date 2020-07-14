package scheletri;

public class Simulator_noleggio_auto {
	

	//CREAZIONE CLASSE EVENT

	public class Event implements Comparable<Event>{
		
		public enum EventType {
			NEW_CLIENT, CAR_RETURNED
		}
		
		private LocalTime time ;
		private EventType type ;
		
		
		/**
		 * @param time
		 * @param type
		 */
		public Event(LocalTime time, EventType type) {
			super();
			this.time = time;
			this.type = type;
		}
		public LocalTime getTime() {
			return time;
		}
		public void setTime(LocalTime time) {
			this.time = time;
		}
		public EventType getType() {
			return type;
		}
		public void setType(EventType type) {
			this.type = type;
		}
		
		@Override
		public int compareTo(Event other) {
			return this.time.compareTo(other.time);
		}
		@Override
		public String toString() {
			return "Event [time=" + time + ", type=" + type + "]";
		}



	// CREAZIONE CLASSE SIMULATORE

	public class Simulator {
		
		1.// CODA DEGLI EVENTI
		private PriorityQueue<Event> queue = new PriorityQueue<>();
		
		2.// PARAMETRI DI SIMULAZIONE
		private int NC = 10 ; // number of cars
		private Duration T_IN = Duration.of(10, ChronoUnit.MINUTES); // intervallo tra i clienti
		
		private final LocalTime oraApertura = LocalTime.of(8, 00);
		private final LocalTime oraChiusura = LocalTime.of(17, 00);
		
		
		3.// MODELLO DEL MONDO
		private int nAuto ; // auto disponibili in deposito (tra 0 e NC)
		
		4.// VALORI DA CALCOLARE
		private int clienti ;
		private int insoddisfatti ;
		
		
		// METODI PER IMPOSTARE I PARAMETRI
		public void setNumCars(int N) {
			this.NC = N ;
		}
		
		public void setClientFrequency(Duration d) {
			this.T_IN = d ;
		}

		// METODI PER RESTITUIRE I RISULTATI
		public int getClienti() {
			return clienti;
		}

		public int getInsoddisfatti() {
			return insoddisfatti;
		}

	       


		// SIMULAZIONE VERA E PROPRIA
		
		public void run() {
			// preparazione iniziale (mondo + coda eventi)
			this.nAuto = this.NC ;
			this.clienti = this.insoddisfatti = 0 ;
			
			this.queue.clear();
			LocalTime oraArrivoCliente = this.oraApertura ;
			do {
				Event e = new Event(oraArrivoCliente, EventType.NEW_CLIENT);
				this.queue.add(e);
				
				oraArrivoCliente = oraArrivoCliente.plus(this.T_IN);
			} while(oraArrivoCliente.isBefore(this.oraChiusura));
			
			// esecuzione del ciclo di simulazione
			while(!this.queue.isEmpty()) {
				Event e = this.queue.poll();
//				System.out.println(e);
				processEvent(e);
			}
		}
		
		private void processEvent(Event e) {
			switch(e.getType()) {
			
			case NEW_CLIENT:
				
				if(this.nAuto>0) {
					// cliente viene servito, auto noleggiata
					// 1. aggiorna modello del mondo
					this.nAuto-- ;
					// 2. aggiorna i risultati
					this.clienti++ ;
					// 3. genera nuovi eventi
					double num = Math.random(); // [0,1)
					Duration travel ;
					if(num<1.0/3.0)
						travel = Duration.of(1, ChronoUnit.HOURS);
					else if (num<2.0/3.0)
						travel = Duration.of(2, ChronoUnit.HOURS);
					else
						travel = Duration.of(3, ChronoUnit.HOURS);
					
					Event nuovo = new Event(e.getTime().plus(travel), 
						EventType.CAR_RETURNED);
					this.queue.add(nuovo);
					
				} else {
					// cliente insoddisfatto
					this.clienti++;
					this.insoddisfatti++;
				}
				
				break;
				
			case CAR_RETURNED:
				
				this.nAuto++ ;
				
				break;

}
