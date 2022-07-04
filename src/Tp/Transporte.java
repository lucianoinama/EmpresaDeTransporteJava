package Tp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import Tp.Empresa;
import Tp.Destino;

public abstract class Transporte {

	private String identificacion;
	private double cargaMax;
	private double capTotal;
	protected double costoKm;
	private boolean enViaje;
	private boolean islleno;
	public Destino destino;
	private ArrayList<Paquete> carga;
	
	private double costoTotalActual;

	
	Transporte(String identificacion, double cargaMax, double capacidad, double costoKm){
		
		
		
		if (identificacion == null || identificacion.charAt(1) == ' ') {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}
		
		if (hayCaracterEspecialTransport(identificacion)) {
			throw new RuntimeException("La matricula no debe contener caracteres especiales!");
		}

		if (capacidad < 0 || cargaMax < 0 || costoKm < 0 ) {
			throw new RuntimeException("Los parametros ingresados no deben ser menor a cero(0)!");
		}
		
		if (capacidad == 0 || cargaMax == 0 || costoKm == 0) {
			throw new RuntimeException("Los parametros 'capacidad', 'cargaMax' y/o 'costoKm' no deben ser 0");
		}

		
		
		this.identificacion = identificacion.toUpperCase();
		this.cargaMax = cargaMax;
		this.capTotal = cargaMax -1;
		this.costoKm = costoKm;
		this.islleno = false;
		this.enViaje = false;
		this.carga = new ArrayList<Paquete>();
		this.costoTotalActual = 0;
	}
	
	
	
	protected void addPaquete(Paquete p) {
		
		if(estaLleno() || capTotal == cargaMax) { //Si esta lleno o la capMax se alcanzo tira una excepcion
			throw new RuntimeException("El Camion " + this.identificacion + "esta lleno!");
		}			
		
		carga.add(p);
		//Collections.sort(carga);
		capTotal = capTotal - p.obtenerVolumen();
	
		if(capTotal == cargaMax) { //Si se alcanzo el limite de capacidad del Transporte el estado cambia a LLENO.
			setEstado(true);
		}
		
	
	}
	
	
	protected boolean transporIgual(Transporte T) { //Comprueba si el transporte pasado por parametro tiene la misma carga que el transporte desde el que se llamo este metodo
	
//	if(!this.identificacion.equals(T.obtenerMatricula()) && this.destino.equals(T.getDestinoN()) && this.carga.equals(T.obtenerCarga()) && this.getClass() == T.getClass()  ){
//		return true;
//	}
	
	if(this.identificacion != T.obtenerMatricula() && this.destino.equals(T.getDestinoN()) ) {	
		if(T.obtenerCarga() == null && this.carga == null) {	//	System.out.println("Ambos transportes no tienen carga");
		return true;
		} 
		return cargaIgual(this.carga, T.obtenerCarga());
		}
	
	return false;
	}
	
	private boolean cargaIgual(ArrayList<Paquete> carga1, ArrayList<Paquete> carga2) { //Llamado desde cargaIgual(Transporte T), realiza la comparaacion entre cargas de ambos transportes. Si son iguales retorna true, en caso contrario, false.
		
		Iterator<Paquete> c2 = carga2.iterator();	
		
		
		boolean contador = true;
		while(c2.hasNext()) {
			Paquete p2Paquete = c2.next();
			
			if(carga1.contains(p2Paquete)) {
				contador = contador && true;
			}
			else {
				contador = false;
			}
		}
		
		
		return contador;
		
	}
	
	
	
	protected double cargarTransporte(ArrayList<Paquete> auxPaq) { // Teniendo en cuenta su destino, su volumen y necesidad de
		// refrigeracion, carga los paquetes al transporte pasado por parametro

		double volumen = 0;

		//Iterator<Deposito> destIt = depos.iterator();

		
		
		if (estaLleno()) {
			throw new RuntimeException("El transporte " + obtenerMatricula() + " ya tiene carga!");
		}
		if(getDestinoN() == null) {
			throw new RuntimeException("No se puede cargar un transporte sin destino asignado!");
		}
		
		if(auxPaq == null || auxPaq.size() == 0) {
			throw new RuntimeException("No hay carga disponible para este transporte");
		}
//		for(Deposito dep : depos.values()) {
//			
//			auxPaq.addAll(dep.getPaquetes(getDestino()));
//		}
		
//		while (destIt.hasNext()) {
//			Deposito dActual = destIt.next();
//
//			auxPaq.addAll(dActual.getPaquetes(getDestino())); // Añade a lista auxiliar todos los paquetes de
//								// todos los depositos que tengan el destino de
//								// transporte.
//
//		}
		
		if (this.estaRefrig()) { // Si transporte TIENE refrigeracion
			for (Paquete p : auxPaq) {
				double AuxNuevoVol = getCap() - p.obtenerVolumen(); // Variable que verifica si el peso despues de
									// cargar superara la carga maxima
				if (AuxNuevoVol < this.getCapMax() && p.necesitaRefrig() && p.getDestinoP().equals(this.getDestino())) { // Si el volumen del paquete supera la carga
									// max no se carga
					this.addPaquete(p); // Añadir cualquier paquete requiera o no refrigeracion.
					
					Collections.sort(carga);

					volumen = volumen + p.obtenerVolumen();
					AuxNuevoVol = this.getCap();

				}
			}
		}
		if (!this.estaRefrig()) { // Si Transporte NO tiene refrigeracion
			for (Paquete p : auxPaq) {
				double AuxNuevoVol = getCap() - p.obtenerVolumen();
				if (!p.necesitaRefrig() && AuxNuevoVol < this.getCapMax() && p.getDestinoP().equals(this.getDestino())) { // Añadir paquetes que no requieran
									// refrigeracion
					this.addPaquete(p);
					Collections.sort(carga);

					volumen = volumen + p.obtenerVolumen();
					AuxNuevoVol = this.getCap();
					
				}
			}
		}
		
		
		
		
		
		this.setEstado(true);

		return volumen;

	}
	
	//Iniciar y finalizar Viajes
	

	protected void iniciarViaje() { // Inicia el viaje del transporte pasado como parametro
		
		
		if (this.enViaje()) {
			throw new RuntimeException("El transporte " + this.obtenerMatricula() + " ya se encuentra en viaje!");
		}
		if (this.getDestinoN() == null) {
			throw new RuntimeException("El transporte " + this.obtenerMatricula() + " no tiene un destino asignado!");
		}
		if (this.estaVacio()) {
			throw new RuntimeException("El transporte " + this.obtenerMatricula() + " no tiene carga!");
		}
		else {
			this.costoTotalActual = this.obtenerCostoTotalActual();
			this.setViaje(true);
//			System.out.println("El transporte " + this.obtenerMatricula() + " inicio su viaje hacia destino " + this.getDestino() + ".");
		}

	}
	

	public void finalizarViaje() { // Finaliza el viaje del transporte pasado como parametro
	
		
		if (!this.enViaje()) {
			throw new RuntimeException("El transporte " + this.obtenerMatricula() + " no se encuentra en viaje!");
		}
		else {
			this.setViaje(false);
			this.destino = null;
			carga.clear();
			//System.out.println("El transporte " + this.obtenerMatricula() + " finalizo su viaje.");
		
		}

		 }
	
	//Getters and Setters
	
	
		protected String obtenerMatricula() {
			return this.identificacion;
		}
		
		protected double getCosto() {
			return this.costoKm;
		}
		
		
		protected abstract double obtenerCostoTotalActual(); //Dependiendo del tipo de transporte y sus parametros, retorna su costo total
		
		protected double obtenerCostoTotal() {
			
			return this.costoTotalActual;
		}
		
		protected boolean estaVacio() { //Consulta si el transporte se encuentra sin carga
			if(capTotal == (cargaMax -1)) {
				return true;
			}
			return false;
		}
	
		
		
		public boolean enViaje() {
			return this.enViaje;
		}
		protected void setViaje(boolean b) { 
			this.enViaje = b;
		}
		
		protected abstract void comprobarDestinoKm(Destino d); //Comprueba que los km del destino sean validos para el tipo de transporte
		
		protected void asignarDestino(Destino d) {
			this.destino = d;
		}
		
		protected void setEstado(boolean b) {
			this.islleno = b;
		}
		public boolean estaLleno() {
			return islleno;
		}
		protected double getCap() { //Retorna la capacidad actual del transporte
			return this.capTotal;
		}
		
		protected double getCapMax() {
			return this.cargaMax;
		}
		public abstract boolean estaRefrig(); //Consulta a las clases Trailer y MegaTrailer si "tienen" refrigeracion
		
		protected Destino getDestinoN() { //Devuelve un objeto destino
			
			return this.destino;
			
		} 
		
		protected String getDestino() { //Devuelve el Dato tipo string de la variable destino del objeto destino
			
			return this.destino.getDestino();
		}
		protected Integer getDestinoKm() {
			return this.destino.getKm();
		}
		
		protected ArrayList<Paquete> obtenerCarga() {
			return this.carga;
		}
		
		protected boolean hayCaracterEspecialTransport(String str) { // Funciona como specialChar pero NO excluye numeros. Esta pensado para
			// matriculas.
			
			boolean result = false;
			for (int i = 0; i < str.length(); i++) {
				boolean acum = true;
				int ascii = (int) str.charAt(i);
				
				if ((ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122) || ascii == 32
						|| (ascii >= 48 && ascii <= 57)) {
					acum = acum && false;
				}
				
				result = result || acum;
				acum = true;
			}
			return result;
			
		}



	
		@Override
		public boolean equals(Object objT) {

			
			
			if(objT == null || !(objT instanceof Transporte)) {
				return false;
			}
			
			
		Transporte T = (Transporte) objT;
			
		if(this.getClass() == T.getClass()) {
			if(!this.identificacion.equals((T).obtenerMatricula()) && this.destino.equals(T.getDestinoN()) 
					&& this.carga.equals(T.obtenerCarga())){
				return true;
		}
			}
//		if(this.identificacion != T.obtenerMatricula() && this.destino.equals(T.getDestinoN()) ) {	
//			if(T.obtenerCarga() == null && this.carga == null) {	//	System.out.println("Ambos transportes no tienen carga");
//			return true;
//			} 
//			return cargaIgual(this.carga, T.obtenerCarga());
//			}
		
		return false;
		
		
		
		}	













}
