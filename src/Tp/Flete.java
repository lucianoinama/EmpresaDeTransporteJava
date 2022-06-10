package Tp;

public class Flete<T> extends Transporte {

	
	private int cantAcompaniantes;
	private double costoPorAcompaniante;
	
	public Flete(String matricula, double cargaMax, double capacidad, double costoKm, 
			int cantAcompaniantes, double costoPorAcompaniante) {
		super(matricula, cargaMax, capacidad, costoKm);	
		
		this.cantAcompaniantes = cantAcompaniantes;
		this.costoPorAcompaniante = costoPorAcompaniante;
		setTipo("Flete");
	
	
	}

	@Override
	public String toString() {
		return "[" + getId()+ ", " + "Flete" + "]" ;
	}

	@Override
	public boolean isRefrig() {
		return false;
	}

	@Override
	protected double getCostoTotal() {
		double aux = (getCosto()*getDestinoKm()) + (this.cantAcompaniantes*this.costoPorAcompaniante);
		return aux;
	}

}
