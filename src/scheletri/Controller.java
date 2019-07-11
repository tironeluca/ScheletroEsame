package scheletri;

public class Controller {

// CONTROLLO SU TXTBOX (STRINGA VUOTA, CONVERSIONE IN INT, ESISTENZA ELEMENTO)

	String codice= txtObjectId.getText();
	
	if ( codice.length()==0)
	{
		txtResult.appendText("ERRORE: inserire valore\n");
		return;
	}
	
	Integer cod;
	try {
	cod= Integer.parseInt(codice);}catch(NumberFormatException n) {txtResult.appendText("ERRORE: il formato è errato\n");
	return;}
	
	if (!model.esiste(cod))
	{
		txtResult.appendText("ERRORE: l'oggetto non è esistente\n");
		return;
	}
	
	
	
// ESISTENZA NEL MODEL
	
	// ESISTENZA ELEMENTO TRA I VERTICI

	public boolean esiste(Integer cod) {
		
		ArtObject a= idMap.get(cod);
		
		if (a==null)
		{ return false;}
		
		return this.graph.containsVertex(idMap.get(cod));
	}
	
// 
}
