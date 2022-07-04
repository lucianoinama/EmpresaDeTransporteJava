package Tp;

public class Trailer extends Transporte {

	private double segCarga;
	
	private boolean tieneRefrigeracion;
	
	
	public Trailer(String matricula, double cargaMax,
			double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga) {
		super(matricula, cargaMax, capacidad, costoKm);
		
		
		if (segCarga < 0) {
			throw new RuntimeException("No se pueden ingresar parametros menores a cero (0)!");
		}
		if (segCarga == 0) {
			throw new RuntimeException(
					"El parametros 'seguroCarga' no puede ser cero (0)");
		}
		
		
		
		this.segCarga = segCarga;	
		this.tieneRefrigeracion = tieneRefrigeracion;
		
		
	}
	
	
	@Override
	public String toString() {
		return "[" + obtenerMatricula()+ ", " + "Trailer" + "]" ;
	}


	@Override
	public boolean estaRefrig() {
		return this.tieneRefrigeracion;
	}


	@Override
	protected double obtenerCostoTotalActual() {
		double aux = (getCosto()*getDestinoKm()) +this.segCarga;
		return aux;
	}


	@Override
	protected void comprobarDestinoKm(Destino d) {
		if(d.getKm() > 500) {
			throw new RuntimeException(
					"Los kilometros de este destino son demasiado grandes para este tipo de transporte!");
		}
		else {
			asignarDestino(d);
		}
	}

}
