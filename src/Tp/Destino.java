package Tp;


//Destino es un objeto con 2 parametros, El destino y sus KM
class Destino<T1, T2> {
	private String destino;
	private Integer km;


	protected Destino(String destino, Integer km) {
		this.destino = destino;
		this.km = km;
	}
	
	protected String getDestino() {
		return this.destino;
	}
 
	protected Integer getKm() {
		return this.km;
	}
 
	@Override
	public java.lang.String toString() {
		return "[" + destino + " " + km.toString() + "]";
	}
	
 
 }
