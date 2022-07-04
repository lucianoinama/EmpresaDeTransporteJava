package Tp;


//Destino es un objeto con 2 parametros, El destino y sus KM
class Destino {
	final String destino;
	final Integer km;


	protected Destino(String destino, Integer km) {
		
		if (destino == null || destino.charAt(1) == ' ') {
			throw new RuntimeException("No se puede agregar un destino vacio!");
		}

		if (specialChar(destino)) {
			throw new RuntimeException("No se permiten caracteres especiales ni numeros para definir el destino");
		}

		if (km < 0) {
			throw new RuntimeException("Los Km hasta destino no pueden ser menores a cero(0)!");
		}
		
		this.destino = destino.toUpperCase();
		this.km = km;
	
	
	}
	
	protected String getDestino() {
		return this.destino;
	}
 
	protected Integer getKm() {
		return this.km;
	}
 
	private boolean specialChar(String str) { // Verifica si la string posee un caracter especial o numero

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
	public java.lang.String toString() {
		return "[" + destino + " " + km.toString() + "]";
	}
	
 
 }
