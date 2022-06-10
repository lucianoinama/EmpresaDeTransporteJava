package Tp;

import static org.junit.Assert.assertEquals;

public class Main {
	public static void main(String[] args) throws Exception {
		double volumen;
		String test = "Buenos Aires";
		System.out.println(test.toUpperCase());
		Empresa e = new Empresa("30112223334","Expreso Libre", 40000);
		//System.out.println(e.toString());
		e.agregarDestino("Rosario", 100);
		e.agregarDestino("Buenos Aires", 400);
		e.agregarDestino("Mar del Plata", 800);
		System.out.println(e.getDestinos());
		e.agregarTrailer("AA333XQ", 10000, 60, true, 2, 100);
		e.agregarMegaTrailer("AA444PR", 15000, 100, false, 3, 150, 200, 50);
		e.agregarFlete("AB555MN", 5000, 20, 4, 2, 300);
		//System.out.println(e.getListaTransportes());

		e.asignarDestino("AA333XQ", "Buenos Aires");
		e.asignarDestino("AB555MN", "Rosario");
		//System.out.println(e.getListaTransportes());
		//System.out.println(e.getListaDepo());
		
		// paquetes que necesitan frio
		e.incorporarPaquete("Buenos Aires", 100, 2, true);
		e.incorporarPaquete("Buenos Aires", 150, 1, true);
		e.incorporarPaquete("Mar del Plata", 100, 2, true);
		e.incorporarPaquete("Mar del Plata", 150, 1, true);
		e.incorporarPaquete("Rosario", 100, 2, true);
		e.incorporarPaquete("Rosario", 150, 1, true);
		// paquetes que NO necesitan frio
		e.incorporarPaquete("Buenos Aires", 200, 3, false);
		e.incorporarPaquete("Buenos Aires", 200, 3, false);
		e.incorporarPaquete("Buenos Aires", 400, 4, false);
		e.incorporarPaquete("Mar del Plata", 200, 3, false);
		e.incorporarPaquete("Rosario", 80, 2, false);
		e.incorporarPaquete("Rosario", 250, 2, false);
		//System.out.println(e.getListaDepo());
		//System.out.println(e.getPaquetesDepo());
		//System.out.println(e.getListaDepo("dep1"));
		volumen = e.cargarTransporte("AA333XQ");
		e.cargarTransporte("AB555MN");
		System.out.println("Se cargaron " + volumen
		+" metros cubicos en el transp AA333XQ");
		e.iniciarViaje("AA333XQ");
		e.finalizarViaje("AA333XQ");
		
		//e.agregarTrailer("AC314PI", 12000, 60, true, 5, 100);
		//e.iniciarViaje("AC314PI");
		System.out.println("Costo del viaje:"
		+e.obtenerCostoViaje("AA333XQ"));
		
		System.out.println(e.toString());
		//e.finalizarViaje("AA333XQ");
		
		
		e.agregarDestino("Parana", 30);
		
		e.agregarFlete("AB271NE", 8000, 40, 3, 2, 200);
		e.agregarFlete("AB271NS", 8000, 40, 3, 2, 200);
		System.out.println(e.getListaTransportes());
		e.asignarDestino("AB271NE", "Parana");
		e.incorporarPaquete("Parana", 100, 5, false);
		e.incorporarPaquete("Parana", 400, 12, false);
		e.incorporarPaquete("Parana", 50, 8, false);
		e.cargarTransporte("AB271NE");

		e.agregarFlete("AD235NP", 6000, 30, 2, 1, 100);
		e.asignarDestino("AD235NP", "Parana");
		e.incorporarPaquete("Parana", 400, 12, false);
		e.incorporarPaquete("Parana", 50, 8, false);
		e.incorporarPaquete("Parana", 100, 5, false);
		
		e.incorporarPaquete("Parana", 100, 5, false);
		e.incorporarPaquete("Parana", 400, 12, false);
		e.incorporarPaquete("Parana", 50, 8, false);
		e.cargarTransporte("AD235NP");
		//e.cargarTransporte("AD235NP");

		e.iniciarViaje("AD235NP");
		assertEquals("AD235NP", e.obtenerTransporteIgual("AB271NE"));
		
		

	
		
		//System.out.println(e.getListaDepo());
		System.out.println(e.getPaquetesDepo("dep2"));
		
		//System.out.println(e.getAllPaquetes());
		e.agregarTrailer("AC314PI", 12000, 60, true, 5, 100);
		e.asignarDestino("AC314PI", "Mar del Plata");
	}
		

}
