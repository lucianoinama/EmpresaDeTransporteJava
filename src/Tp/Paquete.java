package Tp;

import java.util.Objects;

public class Paquete {

	private double peso;
	private double volumen;
	private String destino;
	private boolean refrig;
	
	public Paquete(String destino, double peso, double volumen, boolean refrig) {
		
		this.peso = peso;
		this.volumen = volumen;
		this.destino = destino; //En el caso de este trabajo, el destino es la identificacion del paquete
		this.refrig = refrig;
	
	
	}
	//Getter
	

	@Override
	public String toString() {
		
		return "[Paquete Destino: " + this.destino + ", Volumen: "+ this.volumen +", Peso: " + this.peso + ", Refrigeracion: " + this.refrig + "]";
		//return "[Paquete identif: " + this.identificacion + ", Destino: " + this.destino + "]"; 
	}

	public double getPeso() {
		return peso;
	}


	protected void setPeso(double peso) {
		this.peso = peso;
	}


	public double getVolumen() {
		return volumen;
	}


	protected void setVolumen(double volumen) {
		this.volumen = volumen;
	}


	public String getDestino() {
		return destino;
	}


	public void setDestino(String destino) {
		this.destino = destino;
	}


	public boolean isRefrig() {
		return refrig;
	}


	protected void setRefrig(boolean refrig) {
		this.refrig = refrig;
	}

	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paquete other = (Paquete) obj;
		return Objects.equals(destino, other.destino)
				&& Double.doubleToLongBits(peso) == Double.doubleToLongBits(other.peso) && refrig == other.refrig
				&& Double.doubleToLongBits(volumen) == Double.doubleToLongBits(other.volumen);
	}

	


}
