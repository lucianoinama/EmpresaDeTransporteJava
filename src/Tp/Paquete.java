package Tp;

import java.util.Objects;

public class Paquete implements Comparable{

	private final double peso;
	private final double volumen;
	private final String destino;
	private final boolean refrig;
	
	public Paquete(String destino, double peso, double volumen, boolean refrig) {
		
		if (destino == null) {
			throw new RuntimeException("Destino no puede ser un parametro vacio!");
		}

		if (hayCaracterEspecial(destino)) {
			throw new RuntimeException("No se permiten caracteres especiales ni numeros para ingresar el destino");
		}

		if (volumen < 0 || peso < 0) {
			throw new RuntimeException("Los valores peso y/o volumen no deben ser menores a cero(0)!");
		}
		
		
		
		
		
		this.peso = peso;
		this.volumen = volumen;
		this.destino = destino.toUpperCase(); //En el caso de este trabajo, el destino es la identificacion del paquete
		this.refrig = refrig;
	
	
	}
	//Getter
	

	@Override
	public String toString() {
		
		return "[Paquete Destino: " + this.destino + ", Volumen: "+ this.volumen +", Peso: " + this.peso + ", Refrigeracion: " + this.refrig + "]";
	}

	public double obtenerPeso() {
		return peso;
	}

	protected double obtenerVolumen() {
		return volumen;
	}


	protected String getDestinoP() {
		return destino;
	}

	public boolean necesitaRefrig() {
		return refrig;
	}


	
	private boolean hayCaracterEspecial(String str) { // Verifica si la string posee un caracter especial o numero

		boolean result = false;
		for (int i = 0; i < str.length(); i++) {
			boolean acum = true;
			int ascii = (int) str.charAt(i);

			if ((ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122) || ascii == 32) {
				acum = acum && false;
			}

			result = result || acum;
			acum = true;
		}
		return result;

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
	@Override
	public int compareTo(Object p) {
		double compararPeso= ((Paquete)p).obtenerPeso();

		if(this.obtenerPeso()> compararPeso) {
			return 1;
		}
		else if(this.obtenerPeso() < compararPeso) {
			return -1;
		}
        
        return 0;

	}

	


}
