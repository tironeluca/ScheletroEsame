package scheletri;

public class Simulator_emergency {
	
	//CLASSE EVENTO


	public class Event implements Comparable<Event>{
		
		public enum EventType {
			ARRIVAL,  // arriva un nuovo paziente
			TRIAGE,  // � stato assegnato codice colore e vado in sala d'attesa
			FREE_STUDIO, // si libera uno studio e chiamo un paziente
			TREATED, // paziente trattato e dimesso
			TIMEOUT, // attesa eccessiva in sala d'aspetto
			TICK, // evento periodico per verificare se ci sono studi vuoti
		}
		
		private LocalTime time ;
		private EventType type ;
		private Paziente paziente ;
		

		/**
		 * @param time
		 * @param type
		 */
		public Event(LocalTime time, EventType type, Paziente paziente) {
			super();
			this.time = time;
			this.type = type;
			this.paziente = paziente;
		}
		
		public LocalTime getTime() {
			return time;
		}

		public EventType getType() {
			return type;
		}

		@Override
		public int compareTo(Event other) {
			return this.time.compareTo(other.time);
		}

		public Paziente getPaziente() {
			return paziente;
		}

		
		@Override
		public String toString() {
			return "Event ["+time + ", " + type + ", " + paziente + "]";
		}

	}


	//CLASSE SIMULATORE 

	public class Simulator {

		// PARAMETRI DI SIMULAZIONE
		private int NS = 5; // numero studi medici

		private int NP = 150; // numero di pazienti
		private Duration T_ARRIVAL = Duration.ofMinutes(5); // intervallo tra i pazienti

		private final Duration DURATION_TRIAGE = Duration.ofMinutes(5);
		
		private final Duration DURATION_WHITE = Duration.ofMinutes(10);
		private final Duration DURATION_YELLOW = Duration.ofMinutes(15);
		private final Duration DURATION_RED = Duration.ofMinutes(30);

		private final Duration TIMEOUT_WHITE = Duration.ofMinutes(90);
		private final Duration TIMEOUT_YELLOW = Duration.ofMinutes(30);
		private final Duration TIMEOUT_RED = Duration.ofMinutes(60);

		private final LocalTime oraInizio = LocalTime.of(8, 0);
		private final LocalTime oraFine = LocalTime.of(20, 0);
		
		private final Duration TICK_TIME = Duration.ofMinutes(5);

		// OUTPUT DA CALCOLARE
		private int pazientiTot;
		private int pazientiDimessi;
		private int pazientiAbbandonano;
		private int pazientiMorti;

		// STATO DEL SISTEMA
		private List<Paziente> pazienti;
		private PriorityQueue<Paziente> attesa ; // post-triage prima di essere chiamati
		private int studiLiberi ;

		private CodiceColore coloreAssegnato;

		// CODA DEGLI EVENTI
		private PriorityQueue<Event> queue;

		// INIZIALIZZAZIONE
		public void init() {
			this.queue = new PriorityQueue<>();
			
			this.pazienti = new ArrayList<>();
			this.attesa = new PriorityQueue<>();

			this.pazientiTot = 0;
			this.pazientiDimessi = 0;
			this.pazientiAbbandonano = 0;
			this.pazientiMorti = 0;
			
			this.studiLiberi = this.NS ;

			this.coloreAssegnato = CodiceColore.WHITE;

			// generiamo eventi iniziali
			int nPaz = 0;
			LocalTime oraArrivo = this.oraInizio;

			while (nPaz < this.NP && oraArrivo.isBefore(this.oraFine)) {
				Paziente p = new Paziente(oraArrivo, CodiceColore.UNKNOWN);
				this.pazienti.add(p);
				Event e = new Event(oraArrivo, EventType.ARRIVAL, p);
				queue.add(e);

				nPaz++;
				oraArrivo = oraArrivo.plus(T_ARRIVAL);
			}
			
			// Genera TICK iniziale
			queue.add(new Event(this.oraInizio, EventType.TICK, null));
		}

		public int getPazientiAbbandonano() {
			return pazientiAbbandonano;
		}

		public int getPazientiTot() {
			return pazientiTot;
		}

		public int getPazientiDimessi() {
			return pazientiDimessi;
		}

		public int getPazientiMorti() {
			return pazientiMorti;
		}

		// ESECUZIONE
		public void run() {
			while (!this.queue.isEmpty()) {
				Event e = this.queue.poll();
				System.out.println(e + " Free studios "+this.studiLiberi);
				processEvent(e);
			}
		}

		private void processEvent(Event e) {

			Paziente paz = e.getPaziente();

			switch (e.getType()) {
			case ARRIVAL:
				// arriva un paziente: tra 5 minuti sar� finito il triage
				queue.add(new Event(e.getTime().plus(DURATION_TRIAGE), 
						EventType.TRIAGE, paz));
				this.pazientiTot++;
				break;

			case TRIAGE:
				// assegna codice colore
				paz.setColore(nuovoCodiceColore());
				
				// mette in lista d'attesa
				attesa.add(paz) ;

				// schedula timeout
				if(paz.getColore()==CodiceColore.WHITE)
					queue.add(new Event(e.getTime().plus(TIMEOUT_WHITE),
							EventType.TIMEOUT, paz));
				else if(paz.getColore()==CodiceColore.YELLOW)
					queue.add(new Event(e.getTime().plus(TIMEOUT_YELLOW),
							EventType.TIMEOUT, paz));
				else if(paz.getColore()==CodiceColore.RED)
					queue.add(new Event(e.getTime().plus(TIMEOUT_RED),
							EventType.TIMEOUT, paz));
				break;

			case FREE_STUDIO:
				if(this.studiLiberi==0) // non ci sono studi liberi
					break ;
				Paziente prossimo = attesa.poll();
				if(prossimo != null) {
					// fallo entrare
					this.studiLiberi-- ;
					
					// schedula uscita dallo studio
					if(prossimo.getColore()==CodiceColore.WHITE)
						queue.add(new Event(e.getTime().plus(DURATION_WHITE),
								EventType.TREATED, prossimo));
					else if(prossimo.getColore()==CodiceColore.YELLOW)
						queue.add(new Event(e.getTime().plus(DURATION_YELLOW),
								EventType.TREATED, prossimo));
					else if(prossimo.getColore()==CodiceColore.RED)
						queue.add(new Event(e.getTime().plus(DURATION_RED),
								EventType.TREATED, prossimo));
				}
				break;
				
			case TREATED:
				// libera lo studio
				this.studiLiberi++ ;
				paz.setColore(CodiceColore.OUT);
				
				this.pazientiDimessi++;
				this.queue.add(new Event(e.getTime(), EventType.FREE_STUDIO, null));
				break;
				
			case TIMEOUT:
				// esci dalla lista d'attesa
				boolean eraPresente = attesa.remove(paz);
				if(!eraPresente)
					break;
				
				switch(paz.getColore()) {
				case WHITE:
					//va a casa
					this.pazientiAbbandonano++;
					break;
				case YELLOW:
					// diventa RED
					paz.setColore(CodiceColore.RED);
					attesa.add(paz);
					queue.add(new Event(e.getTime().plus(TIMEOUT_RED),
							EventType.TIMEOUT, paz));
					break;
				case RED:
					// muore
					this.pazientiMorti++;
					paz.setColore(CodiceColore.OUT);

					break;
				}
				break;
				
			case TICK:
				if(this.studiLiberi > 0) {
					this.queue.add(new Event(e.getTime(),
							EventType.FREE_STUDIO, null));
				}
				
				if(e.getTime().isBefore(LocalTime.of(23, 30)))
					this.queue.add(new Event(e.getTime().plus(this.TICK_TIME),
						EventType.TICK, null));
				break;
			}
		}

}
