package Tp;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class EmpresaTestExtra {
	
	Empresa emp;
	double volumen, ctoViaje;

	@Before
	public void setUp() throws Exception {
		emp = new Empresa("30112223334", "Expreso Libre", 30000); //
		emp.agregarDestino("Cordoba", 350);
	}

//Empresa
	@Test(expected = RuntimeException.class)
	public void testNombreEmpresaConSpecialChar() {
		Empresa e;
		e = new Empresa("30112223334", "Expreso%$%^ Libre#$", 300000);
		
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testNombreEmpresaConCapNegativa() {
		Empresa e;
		e = new Empresa("30112223334", "Expreso Libre", -300000);
		
	}
	
//TRANSPORTE	
	
	
	@Test(expected = RuntimeException.class)
	public void testNombreconCharsSpeciales() throws Exception {
	emp.agregarTrailer("AC31%^4PI", 12000, 60, true, 5, 100);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTransporteRepetido() throws Exception {
	emp.agregarTrailer("AC314PI", 12000, 60, true, 5, 100);
	emp.agregarFlete("AC314PI", 12000, 60, 6, 0, 0);
	}
	
	
	@Test(expected = RuntimeException.class)
	public void testCargaNegativa() throws Exception {
	emp.agregarTrailer("AC314PI", -12000, 60, true, 5, 100);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCapNegativa() throws Exception {
	emp.agregarTrailer("AC314PI", 12000, -60, true, 5, 100);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCostoNegativo() throws Exception {
	emp.agregarTrailer("AC314PI", 12000, 60, true, -5, 100);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSeguroNegativo() throws Exception {
	emp.agregarTrailer("AC314PI", 12000, 60, true, 5, -100);
	}
	
	
	@Test(expected = RuntimeException.class)
	public void testAcompNegativos() throws Exception {
	emp.agregarFlete("AC314PI", 12000, 60,
			5, -1,
			1);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCostoAcompNegativo() throws Exception {
	emp.agregarFlete("AC314PI", 12000, 60,
			5, 1,
			-1);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCostoFijoNeg() throws Exception {
	emp.agregarMegaTrailer("AC314PI", 12000, 600, false, 5, 4, -2, 5);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCostoComidaNeg() throws Exception {
	emp.agregarMegaTrailer("AC314PI", 12000, 600, false, 5, 4, 2, -5);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCargarTnull() throws Exception {
	emp.cargarTransporte(null);}
	
	
	@Test(expected = RuntimeException.class)
	public void testCargarTnoexistente() throws Exception {
	emp.cargarTransporte("ABCDF");}
	
	@Test(expected = RuntimeException.class)
	public void testCargarTDosVeces() throws Exception {
		emp.agregarMegaTrailer("ABCDFG", 12000, 600, false, 5, 4, 2, -5);
		emp.cargarTransporte("ABCDFG");
		emp.cargarTransporte("ABCDFG");
	
	}
	
	@Test(expected = RuntimeException.class)
	public void testInciarYFinalizarMatNull() throws Exception {
	emp.iniciarViaje(null);}
	
	@Test(expected = RuntimeException.class)
	public void testInciarYFinalizarMatVacia() throws Exception {
	emp.iniciarViaje(" ");}
	
	@Test(expected = RuntimeException.class)
	public void testObtenerCostoMatVacia() throws Exception {
	emp.obtenerCostoViaje(" ");}
	
	@Test(expected = RuntimeException.class)
	public void testObtenerTransIgualVacio() throws Exception {
	emp.obtenerTransporteIgual(" ");}
	
//DESTINO
	@Test(expected = RuntimeException.class)
	public void testAgregarDestinoVacio() throws Exception {
	emp.agregarDestino(" ", 0);}


	@Test(expected = RuntimeException.class)
	public void testAgregarDestinoNull() throws Exception {
	emp.agregarDestino(null, 0);}
	
	
	@Test(expected = RuntimeException.class)
	public void testAgregarDestinoKmNeg() throws Exception {
	emp.agregarDestino("Buenos Aires ", -1);}
	
//DEPOSITO
	
	@Test(expected = RuntimeException.class)
	public void testAgregarDepNombreconCharSpec() throws Exception {
	emp.crearDeposito("dep%$", 0, false);}
	
	@Test(expected = RuntimeException.class)
	public void testAgregarDepNombreRepetido() throws Exception {
		emp.crearDeposito("dep", 0, false);
		emp.crearDeposito("dep", 0, false);}
	
	@Test(expected = RuntimeException.class)
	public void testAgregarDepCapacidadExcesiva() throws Exception {
	emp.crearDeposito("dep", 30001, false);}
	
//PAQUETE
	
	@Test(expected = RuntimeException.class)
	public void testAgregarPaqueteDestinoNull() throws Exception {
	emp.incorporarPaquete(null, 20, 15, false);}
	
	@Test(expected = RuntimeException.class)
	public void testAgregarPaqueteDestinoSpecialChar() throws Exception {
	emp.incorporarPaquete("+Cordoba?!", 20, 15, false);}
	
	@Test(expected = RuntimeException.class)
	public void testAgregarPaquetePesoOVolNeg() throws Exception {
	emp.incorporarPaquete(null, 20, -15, false);} // Verifica si Vol < 0 || peso < 0. Misma Exception
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public EmpresaTestExtra() {
		// TODO Auto-generated constructor stub
	}

}
