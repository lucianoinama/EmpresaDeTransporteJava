package Tp;

import java.util.ArrayList;
import java.util.Iterator;

public String obtenerTransporteIgual(String matricula) { //Busca un transporte con las mismas caracteristicas que el transporte ingresado por parametro
	
	//Verificar si matricula existe
	//Tomar un punto de verificacion, los transportes comparados son iguales en todos sus parametros en excepcion de su matricula
	//
	if(specialCharT(matricula)) { //O(n)
		throw new RuntimeException("No se permiten caracteres especiales en la busqueda de transportes");
	}
	
	Iterator<Transporte> it = listaTransporte.iterator();
	Transporte tAux = getTransporte(matricula); //Transporte al cual le corresponde la matricula
	String tIgual = null;
	
	if(!existeT(matricula)) { //O(N)
		throw new RuntimeException ("La matricula no corresponde a un transporte en existencia!");
	}
	//Comienza a buscar Transporte igual 
	while(it.hasNext()) { // n 
		Transporte tActual = it.next(); //1*n
		//Si tienen nombre distinto, son del mismo tipo y su carga es igual, entonces son iguales.
		if(tActual.getId() != tAux.getId()  && tAux.cargaIgual(tActual) ) { //4+ O(N^2)*N Caso Promedio // 4 + O(1)*N mejor caso 
			return tIgual = tActual.getId(); //1
		}
	
	}
	//Retorna la matricula del transporte que es igual
	return tIgual; //1



} //Complejidad obtenerTransporteIgual: Mejor caso O(N) // Caso promedio O(N^3)



private boolean existeT(String matricula) { //Comprueba si la matricula pasada por parametro corresponde a un transporte en existencia
	Iterator<Transporte>  transIt = listaTransporte.iterator();

	while(transIt.hasNext()) { //n
		Transporte tActual = transIt.next(); //1*n
		if(tActual.getId().equals(matricula) && matricula !=null) { // 4 *n
			return true;	//1 
		}
	}
	return false; //1

}	//Complejidad existeT O(N)

protected ArrayList getCarga() { // Complejidad O(1)
		return this.carga; // 
}


protected boolean cargaIgual(Transporte T) { //Comprueba si el transporte pasado por parametro tiene la misma carga que el transporte desde el que se llamo este metodo
	
	if(T.getCarga() == null && this.carga == null) { //3
		System.out.println("Ambos transportes no tienen carga");
		return true;// 1
	} 
	return cargaIgual(this.carga, T.getCarga()); //1 + O(n^2)

} // Complejidad cargaIgual: Mejor caso O(1) (Ambos Null) ; Caso promedio O(N^2) (Ambos con carga)

private boolean cargaIgual(ArrayList<Paquete> carga1, ArrayList carga2) { //Llamado desde cargaIgual(Transporte T), realiza la comparaacion entre cargas de ambos transportes. Si son iguales retorna true, en caso contrario, false.
	
	boolean contador = true; //1
	
	Iterator<Paquete> c1 = carga1.iterator();//1
	Iterator<Paquete> c2 = carga2.iterator();//1
	for(Paquete p1 : carga1) { //2n
		boolean aux = false; //1*n
		while(c2.hasNext()) { //n
			Paquete p2Actual = c2.next();//1*n*n
			
			if(p1.equals(p2Actual)) { //1*n*n
				aux = aux || true;//2n*n
			}
		}
		
		contador = contador && aux; //1 *n*n
		aux = false;// 1*n
		c2 = carga2.iterator(); //1*n	

	}
	
	return contador;//1
	
} 	//Complejidad cargaIgual O(n^2)




private boolean specialChar(String str) { // Verifica si la string posee un caracter especial o numero
	
	boolean result = false; //1
	for(int i = 0; i< str.length(); i++) { //2+3n
		boolean acum = true; //1 * n
		int ascii = (int) str.charAt(i); //1 * n 
		
		if((ascii>= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122) || ascii == 32) { //9*n
			acum = acum && false; // 1*n
		}
		
		result = result || acum; //2*n
		acum = true; //1 * n
	}
	return result; //1
	
} //Complejidad specialchar O(N)