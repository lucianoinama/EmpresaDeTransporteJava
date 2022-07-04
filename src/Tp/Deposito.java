package Tp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Deposito  {

	public String identif;
	private ArrayList<Paquete> listaPaquetes;
	private int capMax;
	private double capTotal;
	private boolean lleno;
	private boolean refrig;
	
	
	public Deposito(String identif, int capacidad, boolean refrig, int CapMaxEmpresa) {
		
		
		// nombre no puede contener caracteres especiales
				if (identif == null) {
					throw new RuntimeException("El nombre del deposito no puede ser null");

				}
				
				if(identif == "" || identif == " ") {
					throw new RuntimeException("El nombre del deposito no puede ser vacio!");

				}
				
				if (hayCaracterEspecialTransport(identif)) {
					throw new RuntimeException("No se permiten caracteres especiales en el nombre!");
				}
				
				if(capacidad <= 0) {
					throw new RuntimeException("La capacidad del deposito no puede ser menor o igual a cero (0)");
				}
				
				if (capacidad > CapMaxEmpresa) {
					throw new RuntimeException(
							"La capacidad del deposito no puede exceder la capacidad maxima establecida por la empresa (" + CapMaxEmpresa + ")" );
				}
		
		this.identif = identif.toUpperCase();
		this.capMax = capacidad;
		this.capTotal = capacidad - 1; 
		this.refrig = refrig;
		this.lleno = false;
		listaPaquetes = new ArrayList<Paquete>();
		
	}

	//Agregar Paquete a deposito
	
	protected boolean agregar(String destino, double peso, double volumen, boolean necesitaRefrigeracion) throws Exception {
		
		
		Paquete paquete = new Paquete(destino.toUpperCase(), peso, volumen, necesitaRefrigeracion);
		
		if(islleno() || capTotal == capMax) { //Si esta lleno o la capMax se alcanzo tira una excepcion
			throw new RuntimeException("El deposito esta lleno!");
		}			
		if(paquete.necesitaRefrig() == this.refrig && paquete.obtenerVolumen() <= this.capTotal) {
			listaPaquetes.add(paquete);
			
		

			capTotal = capTotal - paquete.obtenerVolumen();
			if(capTotal == capMax) { //Si se alcanzo el limite de capacidad del deposito el estado cambia a LLENO.
				setEstado(true);
			}
			return true;
		}
		
		
		
		return false;
		
		
		
		
	
	}
	protected boolean eliminar(String destino, double peso, double volumen, boolean necesitaRefrigeracion) throws Exception{
		
		Paquete paq = new Paquete(destino.toUpperCase(), peso, volumen, necesitaRefrigeracion);
		
		
		if(listaPaquetes.contains(paq)) {
			listaPaquetes.remove(paq);
			capTotal = capTotal +1;
		
			return true;
		}
		
		return false;
	
	}
	
	
	
	//Getters and  Setters
	
	protected ArrayList<Paquete> obtenerPaquetes(String destino) { //Devuelve un paquete que corresponde con el destino
		ArrayList<Paquete> auxPaq = new ArrayList<Paquete>();
		for(Paquete q : listaPaquetes) {
			if(q.getDestinoP().equals(destino)) {
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

	protected ArrayList<Paquete> obtenerPaquetes() {
		return listaPaquetes;
	}

	
	protected void eliminarPaqCargados(ArrayList<Paquete> arr) {
		
		
		
	
	
	}
	protected String getListaPaquetes() {
		return listaPaquetes.toString();
	}

	protected int getCapMax() {
		return capMax;
	}
	
	protected boolean estaRefrigerado() {
		return refrig;
	}
	protected String isRefrig2() {
		
		if(this.refrig) {return "Refrigerado";}
		
		return "No refrigerado" ;
	}


	protected boolean hayCaracterEspecialTransport(String str) { // Funciona como specialChar pero NO excluye numeros. Esta pensado para
		// matriculas.

		boolean result = false;
		for (int i = 0; i < str.length(); i++) {
			boolean acum = true;
			int ascii = (int) str.charAt(i);

			if ((ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122) || ascii == 32 || (ascii >= 48 && ascii <= 57)) {
				acum = acum && false;
			}

			result = result || acum;
			acum = true;
			}
	return result;

}
	
	
	
	//ToString 
	@Override
	public String toString() {
		
		
		return "[Deposito " + this.identif + ", Capacidad Restante: " + this.capTotal  + ", Capacidad Maxima: " + this.capMax + ", " +this.isRefrig2()+ " ]";
			
	}


}
