package Tp;

public class MegaTrailer extends Transporte{

	private double segCarga;
	private double costoFijo;
	private boolean tieneRefrigeracion;
	private double costoComida;
	
	public MegaTrailer(String matricula, double cargaMax,
			double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga, double costoFijo,
			double costoComida) {
		super(matricula, cargaMax, capacidad, costoKm);
		
		if (segCarga < 0 || costoFijo < 0 || costoComida < 0) {
			throw new RuntimeException("Los parametros ingresados no deben ser menor a cero(0)!");
		}

		if (costoFijo == 0 || segCarga == 0) {
			throw new RuntimeException(
					"Los parametros 'capacidad', 'cargaMax', 'costoKm', 'costoFijo, y/o 'seguroCarga'  no deben ser 0");
		}
				
		
		this.segCarga = segCarga;
		this.costoFijo = costoFijo;
		this.tieneRefrigeracion = tieneRefrigeracion;
		this.costoComida = costoComida;
		
	
	}

	@Override
	public String toString() {
		return "[" + this.obtenerMatricula()+ ", " + "Mega Trailer" + "]" ;
	}

	@Override
	public boolean estaRefrig() {
		return this.tieneRefrigeracion;
	}

	@Override
	protected double obtenerCostoTotalActual() {
		double aux = (getCosto()*getDestinoKm()) + this.costoFijo + this.costoComida +this.segCarga;
		return aux;
	}

	@Override
	protected void comprobarDestinoKm(Destino d) {
		
		asignarDestino(d);
	}


}
