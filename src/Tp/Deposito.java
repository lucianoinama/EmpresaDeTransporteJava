package Tp;

import java.util.ArrayList;
import java.util.Iterator;

public class Deposito  {

	public String identif;
	private ArrayList<Paquete> listaPaquetes;
	private int capMax;
	private double capTotal;
	private boolean lleno;
	boolean refrig;
	
	
	public Deposito(String identif, int capMax, boolean refrig) {
		
		this.identif = identif;
		this.capMax = capMax;
		this.capTotal = capMax - 1; 
		this.refrig = refrig;
		this.lleno = false;
		listaPaquetes = new ArrayList();
		
	}

	//Agregar Paquete a deposito
	
	protected void agregar(Paquete paquete) throws Exception {
		
		if(islleno() || capTotal == capMax) { //Si esta lleno o la capMax se alcanzo tira una excepcion
			throw new RuntimeException("El deposito esta lleno!");
		}			
		
		listaPaquetes.add(paquete);
		capTotal = capTotal - paquete.getVolumen();
		
		if(capTotal == capMax) { //Si se alcanzo el limite de capacidad del deposito el estado cambia a LLENO.
			setEstado(true);
		}
		
	
	}
	protected void eliminar(Paquete paq) throws Exception{
		
		listaPaquetes.remove(paq);
		capTotal = capTotal +1;
	
	}
	
	
	
	//Getters and  Setters
	
	protected ArrayList<Paquete> getPaquetes(String destino) { //Devuelve un paquete que corresponde con el destino
		ArrayList<Paquete> auxPaq = new ArrayList<Paquete>();
		Iterator<Paquete> it = auxPaq.iterator();
		for(Paquete q : listaPaquetes) {
			if(q.getDestino().equals(destino)) {
				auxPaq.add(q);
				
			}
		}
		listaPaquetes.removeAll(auxPaq);		
		
		return auxPaq;
	}
	
	
	
	
	public double getCapTotal() { //Capacidad actual del deposito
		return capTotal;
	}

	protected void setCapTotal(int capTotal) {
		this.capTotal = capTotal;
	}

	protected boolean islleno() {
		return lleno;
	}

	protected void setEstado(boolean estado) { //estado = TRUE si hay lugar,
		this.lleno = estado;
	}

	protected String getIdentif() {
		return identif;
	}

	protected ArrayList getPaquetes() {
		return listaPaquetes;
	}
	
	protected String getListaPaquetes() {
		return listaPaquetes.toString();
	}

	protected int getCapMax() {
		return capMax;
	}
	
	protected boolean isRefrig() {
		return refrig;
	}
	protected String isRefrig2() {
		
		if(this.refrig) {return "Refrigerado";}
		
		return "No refrigerado" ;
	}

//ToString 
	@Override
	public String toString() {
		
		
		return "[Deposito " + this.identif + ", Capacidad Restante: " + this.capTotal  + ", Capacidad Maxima: " + this.capMax + ", " +this.isRefrig2()+ " ]";
			
	}


}
