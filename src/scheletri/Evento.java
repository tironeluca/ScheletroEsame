package scheletri;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	public enum Type {
		
	}
	
	private LocalDateTime time;
	private Type type;
	

	@Override
	public int compareTo(Evento o) {
		return this.time.compareTo(o.time);
	}

}
