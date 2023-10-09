package entities;

import java.io.Serializable;
import java.util.Date;

public class DatosPlazaDto implements Serializable{
	private String texto;
	private Integer planta;
	private Integer fila;
	private Integer columna;
	private Date horaLlegada;
	
	
	public DatosPlazaDto(String texto) {
		this.texto = texto;
		this.planta = null;
		this.fila = null;
		this.columna = null;
		this.horaLlegada = null;
	}
	
	public DatosPlazaDto(String texto, Integer planta, Integer fila, Integer columna, Date horaLlegada) {
		this.texto = texto;
		this.planta = planta;
		this.fila = fila;
		this.columna = columna;
		this.horaLlegada = horaLlegada;
	}

	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Date getHoraLlegada() {
		return horaLlegada;
	}
	public void setHoraLlegada(Date horaLlegada) {
		this.horaLlegada = horaLlegada;
	}
	public Integer getPlanta() {
		return planta;
	}
	public void setPlanta(Integer planta) {
		this.planta = planta;
	}
	public Integer getFila() {
		return fila;
	}
	public void setFila(Integer fila) {
		this.fila = fila;
	}
	public Integer getColumna() {
		return columna;
	}
	public void setColumna(Integer columna) {
		this.columna = columna;
	}
	
	
	
	

}
