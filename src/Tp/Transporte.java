package Tp;

import java.util.ArrayList;
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
	private Destino destino;
	private ArrayList<Paquete> carga;
	private String tipo;

	
	Transporte(String identificacion, double cargaMax, double capacidad, double costoKm){
		
		this.identificacion = identificacion.toUpperCase();
		this.cargaMax = cargaMax;
		this.capTotal = cargaMax -1;
		this.costoKm = costoKm;
		this.islleno = false;
		this.enViaje = false;
		this.carga = new ArrayList<Paquete>();
	}
	
	
	
	protected void addPaquete(Paquete p) {
		
		if(isLleno() || capTotal == cargaMax) { //Si esta lleno o la capMax se alcanzo tira una excepcion
			throw new RuntimeException("El Camion " + this.identificacion + "esta lleno!");
		}			
		
		carga.add(p);
		capTotal = capTotal - p.getVolumen();
	
		if(capTotal == cargaMax) { //Si se alcanzo el limite de capacidad del Transporte el estado cambia a LLENO.
			setEstado(true);
		}
		
	
	}
	
	//Getters and Setters
	
	
	protected String getId() {
		return this.identificacion;
	}
	
	protected double getCosto() {
		return this.costoKm;
	}
	
	
	protected abstract double getCostoTotal(); //Dependiendo del tipo de transporte y sus parametros, retorna su costo total
		
	
	protected boolean estaVacio() { //Consulta si el transporte se encuentra sin carga
		if(capTotal == (cargaMax -1)) {
			return true;
		}
		return false;
	}
	protected void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	protected String getTipo() {
		return this.tipo;
	}
	
	
	public boolean enViaje() {
		return this.enViaje;
	}
	protected void setViaje(boolean b) { 
		this.enViaje = b;
	}
	
	protected void setDestino(Destino d) {
		this.destino = d;
	}

	protected void setEstado(boolean b) {
		this.islleno = b;
	}
	public boolean isLleno() {
		return islleno;
	}
	protected double getCap() { //Retorna la capacidad actual del transporte
		return this.capTotal;
	}
	
	protected double getCapMax() {
		return this.cargaMax;
	}
	public abstract boolean isRefrig(); //Consulta a las clases Trailer y MegaTrailer si "tienen" refrigeracion
	
	protected Destino getDestinoN() { //Devuelve un objeto destino
		
		return this.destino;
	
	} 
	
	protected String getDestino() { //Devuelve el Dato tipo string de la variable destino del objeto destino
		
		return this.destino.getDestino();
	}
	protected Integer getDestinoKm() {
		return this.destino.getKm();
	}

	protected ArrayList getCarga() {
 		return this.carga;
	}
	
	
	
	
	protected boolean cargaIgual(Transporte T) { //Comprueba si el transporte pasado por parametro tiene la misma carga que el transporte desde el que se llamo este metodo
		
		if(T.getCarga() == null && this.carga == null) {
			System.out.println("Ambos transportes no tienen carga");
			return true;
		} 
		return cargaIgual(this.carga, T.getCarga());
	}

	private boolean cargaIgual(ArrayList<Paquete> carga1, ArrayList carga2) { //Llamado desde cargaIgual(Transporte T), realiza la comparaacion entre cargas de ambos transportes. Si son iguales retorna true, en caso contrario, false.
		
		boolean contador = true;
		
		Iterator<Paquete> c1 = carga1.iterator();
		Iterator<Paquete> c2 = carga2.iterator();	
		for(Paquete p1 : carga1) {
			boolean aux = false;
			while(c2.hasNext()) {
				Paquete p2Actual = c2.next();
				
				if(p1.equals(p2Actual)) {
					aux = aux || true;
				}
			}
			
			contador = contador && aux;
			aux = false;
			c2 = carga2.iterator();	

		}
		
		return contador;
		
	}
	




}
