package Tp;

public class Trailer<T> extends Transporte {

	private double segCarga;
	
	private boolean tieneRefrigeracion;
	
	
	public Trailer(String matricula, double cargaMax,
			double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga) {
		super(matricula, cargaMax, capacidad, costoKm);
		this.segCarga = segCarga;	
		this.tieneRefrigeracion = tieneRefrigeracion;
		setTipo("Trailer");
		
	}
	
	
	@Override
	public String toString() {
		return "[" + getId()+ ", " + "Trailer" + "]" ;
	}


	@Override
	public boolean isRefrig() {
		return this.tieneRefrigeracion;
	}


	@Override
	protected double getCostoTotal() {
		double aux = (getCosto()*getDestinoKm()) +this.segCarga;
		return aux;
	}

}
