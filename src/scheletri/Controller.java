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
	
	
	
// CONTROLLO TEXTFIELD E CONTROLLO COMBOBOX
	
	txtResult.clear();
	String ris= txtMinuti.getText();
	
	if(ris.equals(null))
	{
		txtResult.appendText("ERRORE: inserire valore\n");
		return;
	}
	
	Integer minuti;
	try {
	minuti = Integer.parseInt(ris);}catch(NumberFormatException n) {txtResult.appendText("ERRORE: il formato è errato\n");
	return;}
	
	if( minuti<1 || minuti>90)
	{
		txtResult.appendText("ERRORE: il minutaggio inserito è troppo piccolo o troppo elevato\n");
		return;
	}
	
	Integer mese= cmbMese.getValue();
	
	if ( mese == null)
	{
		txtResult.appendText("ERRORE: scegliere un valore dalla lista\n");
		return;
	}
}
