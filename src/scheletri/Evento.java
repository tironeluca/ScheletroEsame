package scheletri;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	public enum Tipo {
		
	}
	
	private LocalDateTime time;
	private Tipo type;
	//da aggiungere il resto
	

	@Override
	public int compareTo(Evento o) {
		return this.time.compareTo(o.time);
	}

}
