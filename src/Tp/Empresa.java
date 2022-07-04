package Tp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;

public class Empresa {

	private String Nombre;
	private String cuit;
	private int capMaxDepositos;

	private HashMap<String, Transporte> listaTransporte;// Lista a la cual se agregan todos los transportes
	private HashMap<String, Deposito> listaDepositos; // Lista que contiene todos los depositos
	private HashMap<String, Destino> destinos; // Lista de todos los destinos

	public Empresa(String cuit, String nombre, int capDepo) {

		if (nombre == null || cuit == null || nombre.charAt(1) == ' ' || cuit.charAt(1) == ' ') {
			throw new RuntimeException("No se pueden ingresar parametros vacios!");
		}

		if (hayCaracterEspecial(nombre) || hayCaracterEspecialTransport(cuit)) {
			throw new RuntimeException(
					"No se permiten caracteres especiales ni numeros para definir el nombre o el CUIT");
		}

		if (capDepo <= 0) {
			throw new RuntimeException("El tamaño maximo de los depositos no puede ser menor o igual a cero(0)!");
		}

		this.Nombre = nombre;
		this.cuit = cuit;
		this.capMaxDepositos = capDepo;
		this.destinos = new HashMap<String, Destino>();
		this.listaTransporte = new HashMap<String, Transporte>();
		this.listaDepositos = new HashMap<String, Deposito>();

		Deposito dep1 = new Deposito("dep1", this.capMaxDepositos, false, this.capMaxDepositos);
		Deposito dep2 = new Deposito("dep2", this.capMaxDepositos, true, this.capMaxDepositos);

		listaDepositos.put("dep1", dep1);
		listaDepositos.put("dep2", dep2);
		//System.out.println("Empresa Creada!" + "\n" + toString());

	}

	/* USO DE DESTINO */

	// Crear un nuevo destino
	public void agregarDestino(String destino, int km) throws Exception {
		
		if(existeD(destino)) {
			throw new RuntimeException ("El destino ingresado ya existe en la lista de destinos!");
		}
		
		destinos.put(destino.toUpperCase(),new Destino(destino, km));

	}

	// Asigna un DESTINO a un TRANSPORTE
	
	public void asignarDestino(String matricula, String destino) throws Exception {
		
		if (!existeD(destino)) {
			throw new RuntimeException("El destino ingresado no existe en la lista de destinos.");
			
		}
		Destino des = destinos.get(destino.toUpperCase());
		if (!existeTransporte(matricula)) {
			throw new RuntimeException("La matricula ingresada no corresponde a un transporte existente.");
		}
		
		Transporte tActual = obtenerTransporte(matricula);

		tActual.asignarDestino(des);

	}

	private boolean existeD(String destino) { // Verifica si ese destino existe en la lista de destinos
		
		return destinos.containsKey(destino.toUpperCase());
	}

//<---------------------------------------------------------------------------------->	
	/* USO DE TRANSPORTE */
	// CREACION DE TRANSPORTES
	public void agregarFlete(String matricula, double cargaMax, double capacidad, double costoKm, int cantAcompaniantes,
			double costoPorAcompaniante) {

		if (existeTransporte(matricula)) {
			throw new RuntimeException("Ya existe un transporte con esa matricula!");
		}


		Transporte flete = new Flete(matricula, cargaMax, capacidad, costoKm, cantAcompaniantes,
				costoPorAcompaniante);
		listaTransporte.put(matricula.toUpperCase(), flete);


	}

	public void agregarTrailer(String matricula, double cargaMax, double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga) {

		if (existeTransporte(matricula)) {
			throw new RuntimeException("Ya existe un transporte con esa matricula!");
		}

		Transporte trailer = new Trailer(matricula, cargaMax, capacidad, tieneRefrigeracion, costoKm,
				segCarga);
		listaTransporte.put(matricula.toUpperCase(), trailer);

	}

	public void agregarMegaTrailer(String matricula, double cargaMax, double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga, double costoFijo, double costoComida) {


		if (existeTransporte(matricula)) {
			throw new RuntimeException("Ya existe un transporte con esa matricula!");
		}


		Transporte mega = new MegaTrailer(matricula, cargaMax, capacidad, tieneRefrigeracion, costoKm,
				segCarga, costoFijo, costoComida);
		listaTransporte.put(matricula.toUpperCase(), mega);

	}

	// CARGAR PAQUETES
	double cargarTransporte(String matricula) { // Teniendo en cuenta su destino, su volumen y necesidad de
												// refrigeracion, carga los paquetes al transporte pasado por parametro

		if (matricula == null || matricula.charAt(1) == ' ') {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}

		if (!existeTransporte(matricula)) {
			throw new RuntimeException("La matricula no corresponde a un transporte en existencia");
		}
 
		// Transporte al que le corresponde la matricula

		Transporte auxT = obtenerTransporte(matricula);
		boolean refrig = auxT.estaRefrig();
		String dest = auxT.getDestino();
		
		return obtenerTransporte(matricula).cargarTransporte(obtenerPaquetes(refrig, dest));
	}

	// INICIAR Y FINALIZAR VIAJES

	public void iniciarViaje(String matricula) { // Inicia el viaje del transporte pasado como parametro
		if (matricula == null || matricula.charAt(1) == ' ') {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}

		if (!existeTransporte(matricula)) {
			throw new RuntimeException("La matricula no corresponde a un transporte en existencia!");
		}
		

		obtenerTransporte(matricula).iniciarViaje();

		

	}

	public void finalizarViaje(String matricula) { // Finaliza el viaje del transporte pasado como parametro
		if (matricula == null || matricula.charAt(1) == ' ') {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}

		if (!existeTransporte(matricula)) {
			throw new RuntimeException("La matricula no corresponde a un transporte en existencia!");
		}
		
		obtenerTransporte(matricula).finalizarViaje();
	}

	// COSTO DE TRANSPORTE
	public double obtenerCostoViaje(String matricula) { // Devuelve el costo del viaje del transporte pasado como
														// existencia
		if (matricula == null) {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}

		if (!existeTransporte(matricula)) {
			throw new RuntimeException("La matricula no corresponde a un transporte en existencia!");
		}
		// IF no tiene viaje retorna excepcion
//		if (obtenerTransporte(matricula).getDestino() == null) {
//			throw new RuntimeException("El transporte " + matricula + " no tiene un viaje asignado");
//		}

		return obtenerTransporte(matricula).obtenerCostoTotal();
	}

	// HERRAMIENTAS TRANSPORTE **********
	
	  private boolean existeTransporte(String matricula) { //Comprueba si la matricula
	  //pasada por parametro corresponde a un transporte en existencia

		  return listaTransporte.containsKey(matricula.toUpperCase());
	  
	  }
	 
	private Transporte obtenerTransporte(String matricula) {

		

		if (!existeTransporte(matricula)) {
			throw new RuntimeException("getTransporte no pudo encontrar el transporte solicitado");
		}

		// Retorna un transporte
		return listaTransporte.get(matricula);
	}

	
	public String obtenerTransporteIgual(String matricula) { // Busca un transporte con las mismas caracteristicas que
																// el transporte ingresado por parametro

//		if (hayCaracterEspecialTransport(matricula)) {
//			throw new RuntimeException("No se permiten caracteres especiales en la busqueda de transportes");
//		}


		if (!existeTransporte(matricula)) {
			throw new RuntimeException("La matricula no corresponde a un transporte en existencia!");
		}
		
		Transporte tAux = obtenerTransporte(matricula); // Transporte al cual le corresponde la matricula
		String tIgual = null;
		for(Transporte tActual : listaTransporte.values()) {
			
			
			
			if (tAux.equals(tActual)) { // Si tienen nombre distinto, son del mismo tipo y su carga es igual, entonces son																// iguales.
			
				return tIgual = tActual.obtenerMatricula();
				
			
			}
			
			
			}

	

	return tIgual; // Retorna la matricula del transporte que es igual

	}

// < ------------------------------------------------------------------------------->	
// USO deposito

	public void crearDeposito(String nombre, int capacidad, boolean refrigeracion) {

		listaDepositos.put(nombre,new Deposito(nombre, capacidad, refrigeracion, this.capMaxDepositos));

	}

	private boolean existeDep(String nombre) {
				
		
		return listaDepositos.containsKey(nombre);
		
	}

// < ------------------------------------------------------------------------------->
//USO DE PAQUETE // 

	public boolean incorporarPaquete(String destino, double peso, double volumen, boolean necesitaRefrigeracion)
			throws Exception { // Incorpora un paquete a un deposito que cumpla con las condiciones que
								// requiere dicho paquete.

		if (!existeD(destino.toUpperCase())) {
			throw new RuntimeException("El destino de este paquete no existe en la lista de destinos!");
		}

		//Paquete paq = new Paquete(destino, peso, volumen, necesitaRefrigeracion);
		//Iterator<Deposito> destIt = listaDepositos.iterator();

		for(Deposito dep : listaDepositos.values()) {
			
			if(dep.agregar(destino, peso, volumen, necesitaRefrigeracion)) {
				return true;
			}
		}
		return false;
	}

	public boolean eliminarPaquete(String destino, double peso, double volumen, boolean necesitaRefrigeracion)
			throws Exception { // Elimina el paquete pasado por parametro


		//Iterator<Deposito> destIt = listaDepositos.iterator();

		for(Deposito dep : listaDepositos.values()) {
			
			if(dep.eliminar(destino, peso, volumen, necesitaRefrigeracion)) {
				return true;
			}
		}
		return false;
		
	}

	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
	// GETTERS y HERRAMIENTAS
	public String ListaTransportes() { // Devuelve la lista de transportes .tostring()

		return this.listaTransporte.toString();
	}

	protected int obtenerCapMaxDepo() { // Devuelve capacidad maxima de los depositos

		return this.capMaxDepositos;
	}

	public String obtenerListaDepo() { // Devuelve la lista con los depositos .toString()
		return this.listaDepositos.toString();
	}

	protected HashMap<String, Deposito> obtenerDepos() {
		return this.listaDepositos;
	}
	

	protected ArrayList<Paquete> obtenerPaquetes(boolean refrig, String destino){
		ArrayList<Paquete> aux = new ArrayList<Paquete>();
		Transporte gg = new Flete("Bisonte", 50, 4, 50, 1, 1);

		for(Deposito dep : listaDepositos.values()) {
			
			gg.equals(dep);
			if(dep.estaRefrigerado() == refrig) {
				
				aux.addAll(dep.obtenerPaquetes(destino));
			}
			
		
		}
		
		
		return aux;
	
	}
	
	public String PaquetesEnDepo(String des) { // Devuelve una lista con los paquetes del deposito pasado como parametro

		
		for(Deposito dep : listaDepositos.values()) {
			
			if(dep.getIdentif() == des.toUpperCase()) {
				return dep.getListaPaquetes();
			}
		}
		return null;
		
		
	}

	public String AllPaquetes() { // Devuelve una lista con TODOS los paquetes de TODOS los depositos
		//Iterator<Deposito> destIt = listaDepositos.iterator();

		ArrayList auxList = new ArrayList<String>();
		
	for(Deposito dep : listaDepositos.values()) {
			
			
			auxList.add(dep.getListaPaquetes());
		
	
	}
		return auxList.toString();
		
		
		
		
	
	}

	public String Destinos() {
		return this.destinos.toString();
	}

	private static boolean hayCaracterEspecial(String str) { // Verifica si la string posee un caracter especial o numero

		boolean result = false;
		for (int i = 0; i < str.length(); i++) {
			boolean acum = true;
			int ascii = (int) str.charAt(i);

			if ((ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122) || ascii == 32) {
				acum = acum && false;
			}

			result = result || acum;
			acum = true;
		}
		return result;

	}

	protected static boolean hayCaracterEspecialTransport(String str) { // Funciona como specialChar pero NO excluye numeros. Esta pensado para
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

	// To string
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		str.append("Nombre de la empresa: ").append(this.Nombre).append("\n ").append("CUIT: ").append(this.cuit)
				.append(", Depositos: ").append(this.listaDepositos.size());
		return str.toString();
	}

	

}
