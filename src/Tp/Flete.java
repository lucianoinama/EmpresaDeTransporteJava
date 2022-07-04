package Tp;

public class Flete extends Transporte {

	
	private int cantAcompaniantes;
	private double costoPorAcompaniante;
	
	public Flete(String matricula, double cargaMax, double capacidad, double costoKm, 
			int cantAcompaniantes, double costoPorAcompaniante) {
		super(matricula, cargaMax, capacidad, costoKm);	
		
		if (cantAcompaniantes < 0 || costoPorAcompaniante < 0) {
			throw new RuntimeException("Los parametros ingresados no deben ser menor a cero(0)!");
		}
		
		this.cantAcompaniantes = cantAcompaniantes;
		this.costoPorAcompaniante = costoPorAcompaniante;
		
	
	
	}

	@Override
	public String toString() {
		return "[" + this.obtenerMatricula()+ ", " + "Flete" + "]" ;
	}

	@Override
	public boolean estaRefrig() {
		return false;
	}

	@Override
	protected double obtenerCostoTotalActual() {
		double aux = (getCosto()*getDestinoKm()) + (this.cantAcompaniantes*this.costoPorAcompaniante);
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
