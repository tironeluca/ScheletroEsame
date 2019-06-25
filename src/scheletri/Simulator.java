package scheletri;

import java.util.PriorityQueue;

public class Simulator {
	
	private PriorityQueue<Evento> queue;
	private Model model;
	
	public Simulator(Model model) {
		this.model = model;
	}
	
	public void init() {
		queue = new PriorityQueue<>();
		
		run();
	}
	
	private void run() {
		
		while ( !queue.isEmpty() ) {
			Evento evento = queue.poll();
			
			switch(evento.getType()) {
			case :
				break;
			case :
				break;
			case :
				break;
			}
		}
		
	}

}
