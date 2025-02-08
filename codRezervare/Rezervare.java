/**
 * @author Botiz Alecandru-Gabriel
 */
package codRezervare;

public class Rezervare {
	/**
	 * @variable dataInceperii Este data inceperii rezervarii
	 * @variable dataFinalizarii Este data finalizarii rezervarii
	 * @variable locatia Este locatia din oferta pentru care faci rezervarea
	 * @variable persoane Este numarul de persoane care vor rezerva oferta
	 * @variable oID este id-ul ofertei
	 * @variable oName este numele ofertei
	 */
	public String dataInceperii;
    String dataFinalizarii;
	String locatia;
	int persoane;
	public int oID;
	String oName;
	/**
	 * Constructorul default
	 */
	Rezervare(){}
	
	
	/**
	 * @param dataInceperii este data cand incepe vacanta
	 * @param dataFinalizarii este data cand se termina vacanta 
	 * @param locatia locatia vacantei
	 * @param persoane numarul de persoane 
	 * @param oID Id-ul ofertei rezervate
	 * @param oName numele ofertei
	 */
public Rezervare(String dataInceperii, String dataFinalizarii, String locatia,int persoane, int oID, String oName) {
        this.dataInceperii = dataInceperii;
        this.dataFinalizarii = dataFinalizarii;
        this.locatia = locatia;
        this.persoane = persoane;
        this.oID = oID;
        this.oName = oName;
    }
	
	/**
	 * Metoda toString , afiseaza rezervarile
	 */ 
	@Override
	public String toString() {
	    return "Oferta #" + oID + " | Data inceput: " + dataInceperii;
	}

}
