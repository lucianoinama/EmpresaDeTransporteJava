package Tp;

public class MegaTrailer<T> extends Transporte{

	private double segCarga;
	private double costoFijo;
	private boolean tieneRefrigeracion;
	private double costoComida;
	
	public MegaTrailer(String matricula, double cargaMax,
			double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga, double costoFijo,
			double costoComida) {
		super(matricula, cargaMax, capacidad, costoKm);
		this.segCarga = segCarga;
		this.costoFijo = costoFijo;
		this.tieneRefrigeracion = tieneRefrigeracion;
		this.costoComida = costoComida;
		setTipo("Mega Trailer");
	
	}

	@Override
	public String toString() {
		return "[" + getId()+ ", " + "Mega Trailer" + "]" ;
	}

	@Override
	public boolean isRefrig() {
		return this.tieneRefrigeracion;
	}

	@Override
	protected double getCostoTotal() {
		double aux = (getCosto()*getDestinoKm()) + this.costoFijo + this.costoComida +this.segCarga;
		return aux;
	}


}
