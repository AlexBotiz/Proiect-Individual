/**
 * @author Botiz Alexandru-Gabriel
 */
package codOferte;

import java.util.Comparator;

/**
 * Aceasta este clasa Oferta in care am metode pentru compararea , construirea si afisarea ofertelor
 */
public class Oferta implements Comparable<Oferta>{
	/**
	 * @variable id id-ul ofertei
	 * @variable name numele ofertei
	 * @variable location Reprezinta locatia din oferta
	 * @variable pret Reprezinta pretul ofertei
	 * @variable a_id Reprezinta id-ul agentiei de care apartine oferta
	 */
	public int id;
	public String name;
	public String location;
	int pret;
	public int length;
	int a_id;
	
	/**
	 * Constructorul de baza
	 */
	Oferta(){}
	
	/**
	 * Constructorul care construieste ofertele
	 * @param i id-ul ofertei
	 * @param nume pretul ofertei
	 * @param loc locatia ofertei
	 * @param pr pretul ofertei
	 * @param len durata vacantei
	 * @param agentieid id-ul agentiei de care apartine oferta
	 */
	public Oferta(int i, String nume, String loc, int pr, int len , int agentieid){
		this.id = i;
		this.name = nume;
		this.location = loc;
		this.pret = pr;
		this.length = len;
		this.a_id = agentieid;
	}
	
	/**
	 * Metoda de sortare a ofertelor in functie de pret crescator folosind metoda compare
	 */
	@Override
	public int compareTo(Oferta b) {
		return Float.compare(this.pret ,b.pret);
	}
	
	/**
	 * Metoda de sortare a ofertelor in functie de pret descrescator folosind metoda compare
	 */
	public int compareToD(Oferta b) {
		return Float.compare(b.pret ,this.pret);
	}
	
	/**
	 * Metoda de sortare a ofertelor in functie de locatie folosind metoda compareTo
	 */
	
	 public static Comparator<Oferta> locationComparator = new Comparator<Oferta>() {
	        @Override
	        public int compare(Oferta a,Oferta b) {
	            return a.location.compareTo(b.location);
	        }
	    };
	
	    
	    /**
	     * Metoda de afisare a ofertelor , folosind toString
	     */
	@Override
	public String toString() {
		return id + ". " + name + " - " + location + " | Cost : " + pret + " de lei/persoana " + "| Durata: " + length + " zile" + " | Agentia " + a_id;
	}
	
}
