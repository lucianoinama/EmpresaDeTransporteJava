package Tp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Empresa{

	private String Nombre;
	private String cuit;
	private int capMaxDepositos;

	private HashSet<Transporte> listaTransporte;//Lista a la cual se agregan todos los transportes
	private ArrayList<Deposito> listaDepositos; //Lista que contiene todos los depositos
	private ArrayList<Destino<String, Integer>> destinos; //Lista de todos los destinos
	
	public Empresa(String cuit, String nombre, int capDepo) {
		
		if(nombre == null || cuit == null || nombre.charAt(1) == ' '  || cuit.charAt(1) == ' ') {
			throw new RuntimeException("No se pueden ingresar parametros vacios!");
		}
		
		if(specialChar(nombre) || specialCharT(cuit)) {
			throw new RuntimeException("No se permiten caracteres especiales ni numeros para definir el nombre o el CUIT");
		}
		
		if(capDepo <= 0) {
			throw new RuntimeException("El tamaño maximo de los depositos no puede ser menor o igual a cero(0)!");
		}
		
		this.Nombre = nombre;
		this.cuit = cuit;
		this.capMaxDepositos = capDepo;
		this.destinos = new ArrayList<Destino<String, Integer>>();
		this.listaTransporte = new HashSet<Transporte>();
		this.listaDepositos = new ArrayList<Deposito>();
		
		Deposito dep1 = new Deposito("dep1", this.capMaxDepositos, false);
		Deposito dep2 = new Deposito("dep2", this.capMaxDepositos, true);
	
		listaDepositos.add(dep1);
		listaDepositos.add(dep2);
		System.out.println("Empresa Creada!" + "\n" + toString());
	
	}

/* USO DE DESTINO*/

	 //Crear un nuevo destino
	public void agregarDestino(String destino, int km) throws Exception {
		String auxDest = destino.toUpperCase();
		Destino<String, Integer> des = new Destino<String, Integer>(auxDest, km);
		if(destino == null || destino.charAt(1) == ' ') {
			throw new RuntimeException("No se puede agregar un destino vacio!");
		}
		
		if(specialChar(auxDest)) {
			throw new RuntimeException("No se permiten caracteres especiales ni numeros para definir el destino");
		}
		
		if(existeD(auxDest)) {
			throw new RuntimeException("El destino ingresado ya existe en la lista de destinos!");
		}
		if(km < 0) {
			throw new RuntimeException("Los Km hasta destino no pueden ser menores a cero(0)!");
		}	
		
		destinos.add(des);
	
		}
	
	//Asigna un DESTINO a un TRANSPORTE 
	public void asignarDestino(String matricula, String destino) throws Exception {
		double Volumen = 0;
		Destino auxDest = null;
		Iterator<Transporte>  transIt = listaTransporte.iterator();
		Iterator<Destino<String, Integer>> destIt = destinos.iterator();
		
		if(!existeD(destino.toUpperCase())) {
			throw new RuntimeException("El destino ingresado no existe en la lista de destinos.");
		} 
		if(!existeT(matricula)) {
			throw new RuntimeException("La matricula ingresada no corresponde a un transporte existente.");
		}
		
		while(destIt.hasNext()) {
			
			Destino<String, Integer> dActual = destIt.next();
			if(dActual.getDestino().equals(destino.toUpperCase()) && destino != null) {
				auxDest = dActual;
				break;
			}			
		} 
		
		while(transIt.hasNext()) {
			Transporte tActual = transIt.next();
			if(tActual.getId().equals(matricula) && matricula !=null) {
				if(auxDest.getKm() > 500 && (tActual instanceof Flete || tActual instanceof Trailer) ) {
					throw new RuntimeException("Los kilometros de este destino son demasiado grandes para este tipo de transporte!");
				}
				tActual.setDestino(auxDest);
				System.out.println("Se asigno el destino: ' " + destino + "' al transporte: '" + matricula + "'");
				break;	
			}
		
		}
	}
	
	private boolean existeD(String destino) { //Verifica si ese destino existe en la lista de destinos
		Iterator<Destino<String, Integer>> destIt = destinos.iterator();

		while(destIt.hasNext()) {
			
			Destino<String, Integer> dActual = destIt.next();
			if(dActual.getDestino().equals(destino.toUpperCase()) && destino != null) {
				return true;
				
			}
		}
		return false;
	}
//<---------------------------------------------------------------------------------->	
/* USO DE TRANSPORTE*/	
	//CREACION DE TRANSPORTES
	public void agregarFlete(String matricula, double cargaMax, double capacidad,
			double costoKm, int cantAcompaniantes,
			double costoPorAcompaniante) {
		
		
		
		if(matricula == null || matricula.charAt(1) == ' ') {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}
		
		
		if(specialCharT(matricula)) {
			throw new RuntimeException("La matricula no debe contener caracteres especiales!");
		}
		
		if(existeT(matricula)) {
			throw new RuntimeException("Ya existe un transporte con esa matricula!");
		}
		
		
		if(capacidad < 0 || cargaMax < 0 || costoKm < 0 || cantAcompaniantes < 0 || costoPorAcompaniante < 0) {
			throw new RuntimeException("Los parametros ingresados no deben ser menor a cero(0)!");
		}

		if(capacidad == 0 || cargaMax == 0 || costoKm == 0) {
			throw new RuntimeException("Los parametros 'capacidad', 'cargaMax' y/o 'costoKm' no deben ser 0");
		}
			
		Flete<String> flete = new Flete<String>(matricula, cargaMax, capacidad,
			costoKm, cantAcompaniantes,
			costoPorAcompaniante);	
		listaTransporte.add(flete);
	
		System.out.println("Se añadio el " + flete.getTipo() + ", cuya matricula es: " + flete.getId());
			
			
	}

	public void agregarTrailer(String matricula, double cargaMax,
			double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga) {
		
		if(matricula == null || matricula.charAt(1) == ' ') {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}
		
		if(specialCharT(matricula)) {
			throw new RuntimeException("La matricula no debe contener caracteres especiales!");
		}
		
		if(existeT(matricula)) {
			throw new RuntimeException("Ya existe un transporte con esa matricula!");
		}
		
		if(capacidad < 0 || cargaMax < 0 || costoKm < 0 || segCarga < 0) {
			throw new RuntimeException("No se pueden ingresar parametros negativos!");
		}
		if(capacidad == 0 || cargaMax == 0 || costoKm == 0 || segCarga == 0) {
			throw new RuntimeException("Los parametros 'capacidad', 'cargaMax', 'costoKm', y/o 'seguroCarga'  no deben ser 0");
		}
		
		
		Trailer<String> trailer = new Trailer<String>(matricula, cargaMax, capacidad, tieneRefrigeracion, costoKm, segCarga);
		listaTransporte.add(trailer);
		System.out.println("Se añadio el " + trailer.getTipo() + ", cuya matricula es: " + trailer.getId());

	}

	public void agregarMegaTrailer(String matricula, double cargaMax,
			double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga, double costoFijo,
			double costoComida) {
		
		if(matricula == null || matricula.charAt(1) == ' ') {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}
		
		if(specialCharT(matricula)) {
			throw new RuntimeException("La matricula no debe contener caracteres especiales!");
		}
		
		if(existeT(matricula)) {
			throw new RuntimeException("Ya existe un transporte con esa matricula!");
		}
		
		if(capacidad < 0 || cargaMax < 0 || costoKm < 0 || segCarga < 0 || costoFijo < 0 || costoComida < 0 ) {
			throw new RuntimeException("Los parametros ingresados no deben ser menor a cero(0)!");
		}
		
		
		if(capacidad == 0 || cargaMax == 0 || costoKm == 0 || costoFijo == 0 || segCarga == 0) {
			throw new RuntimeException("Los parametros 'capacidad', 'cargaMax', 'costoKm', 'costoFijo, y/o 'seguroCarga'  no deben ser 0");
		}
		
		
		MegaTrailer<String> mega = new MegaTrailer<String>(matricula, cargaMax, capacidad, tieneRefrigeracion, costoKm, segCarga, costoFijo, costoComida);
		listaTransporte.add(mega);
		System.out.println("Se añadio el " + mega.getTipo() + ", cuya matricula es: " + mega.getId());

	
	}

	
	//CARGAR PAQUETES
	double cargarTransporte(String matricula) { //Teniendo en cuenta su destino, su volumen y necesidad de refrigeracion, carga los paquetes al transporte pasado por parametro
		
		if(matricula == null || matricula.charAt(1) == ' ') {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}
		
		if(!existeT(matricula)) {
			throw new RuntimeException("La matricula no corresponde a un transporte en existencia");
		}
		
		double volumen = 0;
		Iterator<Transporte>  transIt = listaTransporte.iterator(); //Iterador de lista de transporte
		Transporte auxTrans = null; //Transporte al que le corresponde la matricula
		
		
		Iterator<Deposito> destIt = listaDepositos.iterator();
		
		ArrayList<Paquete> auxPaq = new ArrayList();
		//Iterator<Paquete> paqIt = (Iterator<Paquete>) desActual.getPaquetes();
		
		while(transIt.hasNext()) { //Busca el transporte que corresponde a la matricula 
			Transporte tActual = transIt.next();
			if(tActual.getId()== matricula) {
				auxTrans = tActual;
			}
		}
		
		if(auxTrans.isLleno()) {
			throw new RuntimeException("El transporte " + auxTrans.getId() + " ya tiene carga!");
		}
		//
		 //Aqui se elimino un if que verificaba si auxTrans era null y retornaba una excepcion. Luego se creo el metodo existeT para suplir la funcion de este if.
		//

		while(destIt.hasNext()) {
			Deposito dActual = destIt.next();


			auxPaq.addAll(dActual.getPaquetes(auxTrans.getDestino())); //Añade a lista auxiliar todos los paquetes de todos los depositos que tengan el destino de transporte.
			

		}
		 
		if(auxTrans.isRefrig()) { // Si transporte TIENE refrigeracion
			for(Paquete p : auxPaq) {
				double AuxNuevoVol = auxTrans.getCap() - p.getVolumen(); //Variable que verifica si el peso despues de cargar superara la carga maxima
				if(AuxNuevoVol < auxTrans.getCapMax() && p.isRefrig()) { //Si el volumen del paquete supera la carga max no se carga
				auxTrans.addPaquete(p); //Añadir cualquier paquete requiera o no refrigeracion.
				volumen = volumen + p.getVolumen();
				AuxNuevoVol = auxTrans.getCap();
				
				}
				}
		}
		if(!auxTrans.isRefrig()) { // Si Transporte NO tiene refrigeracion
			for(Paquete p : auxPaq) { 
				double AuxNuevoVol = auxTrans.getCap() - p.getVolumen();
				if(!p.isRefrig() && AuxNuevoVol < auxTrans.getCapMax()  ) {	//Añadir paquetes que no requieran refrigeracion
					auxTrans.addPaquete(p);
					volumen = volumen + p.getVolumen();
					AuxNuevoVol = auxTrans.getCap();
					
				}
			}
		}
		auxTrans.setEstado(true);
		System.out.println("El transporte " + auxTrans.getId() + " se cargo con los paquetes correspondientes a Destino: " + auxTrans.getDestino());
	return volumen;
	
	}
	
	
	//INICIAR Y FINALIZAR VIAJES
	
	public void iniciarViaje(String matricula) { //Inicia el viaje del transporte pasado como parametro
		if(matricula == null || matricula.charAt(1) == ' ') {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}
		
		if(!existeT(matricula)) {
			throw new RuntimeException ("La matricula no corresponde a un transporte en existencia!");}	
		Iterator<Transporte>  transIt = listaTransporte.iterator();

		while(transIt.hasNext()) {
			Transporte tActual = transIt.next();
			if(tActual.getId().equals(matricula) && tActual.enViaje()){
				throw new RuntimeException("El transporte " + tActual.getId() + " ya se encuentra en viaje!");
			}
			if(tActual.getId().equals(matricula) && (tActual.getDestinoN() == null)){
				throw new RuntimeException ("El transporte " + tActual.getId() + " no tiene un destino asignado!");	
			}
			if(tActual.getId().equals(matricula) && tActual.estaVacio()) {
				throw new RuntimeException ("El transporte " + tActual.getId() + " no tiene carga!");
			}
			if(tActual.getId().equals(matricula)) {
				tActual.setViaje(true);	
				System.out.println("El transporte " + tActual.getId() + " inicio su viaje hacia destino " + tActual.getDestino() + ".");
				break;
			}
			
		}	
	
	}
	
	public void finalizarViaje(String matricula) { //Finaliza el viaje del transporte pasado como parametro
		if(matricula == null || matricula.charAt(1) == ' ') {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}
		
		if(!existeT(matricula)) {
			throw new RuntimeException ("La matricula no corresponde a un transporte en existencia!");}	
		Iterator<Transporte>  transIt = listaTransporte.iterator();

		while(transIt.hasNext()) {
			Transporte tActual = transIt.next();
			if(tActual.getId().equals(matricula) && !tActual.enViaje()){
				throw new RuntimeException("El transporte " + tActual.getId() + " no se encuentra en viaje!");
			}
			if(tActual.getId().equals(matricula)) {
				tActual.setViaje(false);	
				System.out.println("El transporte " + tActual.getId() + " finalizo su viaje.");
				break;
			}
			
		}	
	}
	
	//COSTO DE TRANSPORTE
	public double obtenerCostoViaje(String matricula) { //Devuelve el costo del viaje del transporte pasado como existencia 
		if(matricula == null) {
			throw new RuntimeException("Matricula no puede ser un parametro vacio!");
		}
		
		if(!existeT(matricula)) {
			throw new RuntimeException("La matricula no corresponde a un transporte en existencia!");
		}
		//IF no tiene viaje retorna excepcion
		if(getTransporte(matricula).getDestino() == null) {
			throw new RuntimeException("El transporte " + matricula + " no tiene un viaje asignado");
		}
		
		return getTransporte(matricula).getCostoTotal();
	}
	
	
	//HERRAMIENTAS TRANSPORTE	**********
	private boolean existeT(String matricula) { //Comprueba si la matricula pasada por parametro corresponde a un transporte en existencia
		Iterator<Transporte>  transIt = listaTransporte.iterator();

		while(transIt.hasNext()) {
			Transporte tActual = transIt.next();
			if(tActual.getId().equals(matricula) && matricula !=null) {
				return true;	
			}
		}
		return false;
	
		}
	private Transporte getTransporte(String matricula) { 
		
		Iterator<Transporte> aux = listaTransporte.iterator();
		
		if(!existeT(matricula)) {
			throw new RuntimeException ("getTransporte no pudo encontrar el transporte solicitado");
		}
		while(aux.hasNext()) {
			Transporte tActual = aux.next();
			if(tActual.getId().equals(matricula) && matricula != null ) {
				return tActual;
			}
		}
		//Retorna un transporte
		return null;
	}
	private Transporte getTransporte(String matricula, double cargaMax, double capacidad, double costoKm) { //Devuelve un Objeto transporte que contenta estos parametros
		
		Iterator<Transporte> aux = listaTransporte.iterator();
		
		if(!existeT(matricula)) {
			throw new RuntimeException ("getTransporte no pudo encontrar el transporte solicitado");
		}
		while(aux.hasNext()) {
			Transporte tActual = aux.next();
			if(tActual.getId().equals(matricula) && matricula != null ) {
				return tActual;
			}
		}
		return null;
	}
	
	
	public String obtenerTransporteIgual(String matricula) { //Busca un transporte con las mismas caracteristicas que el transporte ingresado por parametro
		
		//Verificar si matricula existe
		//Tomar un punto de verificacion, los transportes comparados son iguales en todos sus parametros en excepcion de su matricula
		//
		if(specialCharT(matricula)) {
			throw new RuntimeException("No se permiten caracteres especiales en la busqueda de transportes");
		}
		
		Iterator<Transporte> it = listaTransporte.iterator();
		Transporte tAux = getTransporte(matricula); //Transporte al cual le corresponde la matricula
		String tIgual = null;
		
		if(!existeT(matricula)) {
			throw new RuntimeException ("La matricula no corresponde a un transporte en existencia!");
		}
		while(it.hasNext()) { //Comienza a buscar Transporte igual
			Transporte tActual = it.next();
			//System.out.println("TACTUAL " +tActual.getId());
			//System.out.println("AUXILIAR " + tAux.getId());
			if(tActual.getId() != tAux.getId()  && tAux.cargaIgual(tActual) ) { //Si tienen nombre distinto, son del mismo tipo y su carga es igual, entonces son iguales.
				return tIgual = tActual.getId();
			}
		
		}
				
		return tIgual; //Retorna la matricula del transporte que es igual
	
	
	
	
	}
	
// < ------------------------------------------------------------------------------->	
// USO deposito
	
	public void crearDeposito(String nombre, int capacidad, boolean refrigeracion) {
		
	//nombre no puede contener caracteres especiales
	if(specialCharT(nombre)) {
		throw new RuntimeException("No se permiten caracteres especiales en el nombre!");
	}
		
	if(existeDep(nombre)) {
		throw new RuntimeException("Ya existe un deposito con ese nombre!");
	}
	
	//Capacidad debe ser igual o menor que capMax
		if(capacidad > this.capMaxDepositos) {
		throw new RuntimeException("La capacidad del deposito no puede exceder la capacidad maxima establecida por la empresa");
	}
		
		Deposito dep = new Deposito (nombre, capacidad, refrigeracion);
	
	listaDepositos.add(dep);
	
	}
	
	private boolean existeDep(String nombre) {
		
		for(Deposito d : listaDepositos) {
			if(d.getIdentif() == nombre) {
				return true;
			}
		}
		return false;
	}
	
// < ------------------------------------------------------------------------------->
//USO DE PAQUETE // 
	
	
	public boolean incorporarPaquete(String destino, double peso, double volumen, boolean necesitaRefrigeracion) throws Exception { //Incorpora un paquete a un deposito que cumpla con las condiciones que requiere dicho paquete. 
		
		String auxDes = destino.toUpperCase();
		if(!existeD(auxDes)) {
			throw new RuntimeException("El destino de este paquete no existe en la lista de destinos!");
		}
		
		if(destino == null) {
			throw new RuntimeException("Destino no puede ser un parametro vacio!");
		}
		
		if(specialChar(destino)) {
			throw new RuntimeException("No se permiten caracteres especiales ni numeros para ingresar el destino");
		}
		
		if(volumen < 0 || peso < 0) {
			throw new RuntimeException("Los valores peso y/o volumen no deben ser menores a cero(0)!");
		}
		
		
		Paquete paq = new Paquete(auxDes, peso, volumen, necesitaRefrigeracion);
		Iterator<Deposito> destIt = listaDepositos.iterator();

		
		while(destIt.hasNext()) {
		Deposito depActual = destIt.next();
		//System.out.println(depActual.toString());
			
		//La primer comparacion es sobre si el paquete necesita o no refrigeracion
			if(necesitaRefrigeracion) { 
				if(depActual.isRefrig() && volumen <= depActual.getCapTotal()) {  //Verifica que el volumen del paquete no sea mayor a la capacidad del deposito
					//System.out.println(depActual.getCapTotal());
					depActual.agregar(paq);
					System.out.println("El paquete con destino  " + destino + " se incorporo al Deposito " + depActual.isRefrig2()+ " " + depActual.getIdentif() + " satisfactoriamente.");
					return true;
				}
				
			}
			if(!necesitaRefrigeracion ) {
				if(!depActual.isRefrig() && volumen <= depActual.getCapTotal()) {
					depActual.agregar(paq);
					//System.out.println(paq.toString());
					System.out.println("El paquete con destino  " + destino + " se incorporo al Deposito " + depActual.isRefrig2()+ " " + depActual.getIdentif() + " satisfactoriamente.");
					return true;
				}
			}
			
		}
		return false;
	}	
	
	public boolean eliminarPaquete(String destino, double peso, double volumen, boolean necesitaRefrigeracion) throws Exception { //Elimina el paquete pasado por parametro
		
		if(destino == null) {
			throw new RuntimeException("Destino no puede ser un parametro vacio!");
		}
		
		if(specialChar(destino)) {
			throw new RuntimeException("No se permiten caracteres especiales ni numeros para ingresar el destino");
		}
		
		if(volumen < 0 || peso < 0) {
			throw new RuntimeException("Los valores peso y/o volumen no deben ser menores a cero(0)!");
		}
		
		
		
		
		Paquete paq = new Paquete(destino.toUpperCase(), peso, volumen, necesitaRefrigeracion);
		Iterator<Deposito> destIt = listaDepositos.iterator();

		while(destIt.hasNext()) {
			Deposito depActual = destIt.next();
				
			//La primer comparacion es sobre si el paquete necesita o no refrigeracion
				if(necesitaRefrigeracion) { 
					if(depActual.isRefrig()) {
						//System.out.println(depActual.getCapTotal());
						if(depActual.getPaquetes().contains(paq)) {
							depActual.eliminar(paq);
							System.out.println("El paquete: " +  paq.toString() + ", se elimino satisfactoriamente");
							return true;
						}
					}
					
				}
				if(!necesitaRefrigeracion ) {
					if(!depActual.isRefrig() && volumen <= depActual.getCapTotal()) {
						if(depActual.getPaquetes().contains(paq)) {
							depActual.eliminar(paq);
							System.out.println("El paquete: " +  paq.toString() + ", se elimino satisfactoriamente");
							return true;
					}
				}
				
			}
		}	
		return false;
	}
	
	
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
	//GETTERS y HERRAMIENTAS
	public String getListaTransportes() { //Devuelve la lista de transportes .tostring()
		
		return this.listaTransporte.toString();
	}
	
	protected int getCapMaxDepo() { //Devuelve capacidad maxima de los depositos
		
		return this.capMaxDepositos;
	}
	
	public String getListaDepo() { //Devuelve la lista con los depositos .toString()
		return this.listaDepositos.toString();
	}
	
	public String getPaquetesDepo(String des) { //Devuelve una lista con los paquetes del deposito pasado como parametro
		Iterator<Deposito> destIt = listaDepositos.iterator();
		
		while(destIt.hasNext()) {
			Deposito auxDes = destIt.next();
			if(auxDes.getIdentif() == des) {
				return auxDes.getListaPaquetes();
			}
			
		}
		return null;
	}
	public String getAllPaquetes() { //Devuelve una lista con TODOS los paquetes de TODOS los depositos
		Iterator<Deposito> destIt = listaDepositos.iterator();
		
		ArrayList auxList = new ArrayList<String>();
		
		while(destIt.hasNext()) {
			Deposito auxDes = destIt.next(); 
			auxList.add(auxDes.getListaPaquetes());
			
		}
		return auxList.toString();
	}
	
	public String getDestinos() {
		return this.destinos.toString();
	}
	
	private boolean specialChar(String str) { // Verifica si la string posee un caracter especial o numero
					
		boolean result = false;
		for(int i = 0; i< str.length(); i++) {
			boolean acum = true;
			int ascii = (int) str.charAt(i);
			
			if((ascii>= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122) || ascii == 32) {
				acum = acum && false;
			}
			
			result = result || acum;
			acum = true;
		}
		return result;
		
	}
	protected boolean specialCharT(String str) { // Funciona como specialChar pero NO excluye numeros. Esta pensado para matriculas.
		
		boolean result = false;
		for(int i = 0; i< str.length(); i++) {
			boolean acum = true;
			int ascii = (int) str.charAt(i);
			
			if((ascii>= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122) || ascii == 32 || (ascii >= 48 && ascii <= 57) ) {
				acum = acum && false;
			}
			
			result = result || acum;
			acum = true;
		}
		return result;
		
	}
	//To string
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		 str.append("Nombre de la empresa: ").append(this.Nombre).append( "\n ").append("CUIT: ").append(this.cuit).append(", Depositos: ").append(this.listaDepositos.size());
		 return str.toString();
	}

}
