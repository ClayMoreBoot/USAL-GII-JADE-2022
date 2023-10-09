package entities;

import java.io.Serializable;
import java.util.Date;

public class FacturaDto implements Serializable{
	private String texto;
	private Integer planta;
	private Integer fila;
	private Integer columna;
	private Date horaLlegada;
	private Date horaSalida;
	private Integer dias;
	private Integer horas;
	private Integer minutos;
	private Float importe;
	
	
	public FacturaDto(DatosPlazaDto data,Date horaSalida,Integer dias,Integer horas,Integer minutos,Float importe) {
		this.texto = data.getTexto();
		this.planta = data.getPlanta();
		this.fila = data.getFila();
		this.columna = data.getColumna();
		this.horaLlegada = data.getHoraLlegada();
		this.horaSalida = horaSalida;
		this.dias = dias;
		this.horas = horas;
		this.minutos = minutos;
		this.importe = importe;
	}
	public FacturaDto(DatosPlazaDto data) {
		this.texto = data.getTexto();
		this.planta = data.getPlanta();
		this.fila = data.getFila();
		this.columna = data.getColumna();
		this.horaLlegada = data.getHoraLlegada();
		this.horaSalida = null;
		this.dias = null;
		this.horas = null;
		this.minutos = null;
		this.importe = null;
	}
	public FacturaDto(String texto ) {
		this.texto = texto;
		this.planta = null;
		this.fila = null;
		this.columna = null;
		this.horaLlegada = null;
		this.horaSalida = null;
		this.dias = null;
		this.horas = null;
		this.minutos = null;
		this.importe = null;
	}
	
	
	
	@Override
	public String toString() {
		return "\n"+texto.toUpperCase() + "\n"
				+"------------------------------------------------\n"
				+"Planta: "+ planta +"\n"
				+"Fila: "+ fila +"\n"
				+"Columna: "+ columna +"\n"
				+"Hora de llegada: "+ horaLlegada +"\n"
				+"Hora de salida: "+ horaSalida +"\n"
				+"Total de dias (12euros/dia): "+ dias + "\t\t-> subtotal: " + dias*12 + "euros\n"
				+"Total de horas (1euros/hora): "+ horas +"\t\t-> subtotal: " + horas + "euros\n"
				+"Total de minutos(0,025euros/minuto): "+ minutos +"\t-> subtotal: " + minutos*0.025 + "euros\n"
				+"Importe final: "+ importe +"euros\n\n"
				;
	}



	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
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
	public Date getHoraLlegada() {
		return horaLlegada;
	}
	public void setHoraLlegada(Date horaLlegada) {
		this.horaLlegada = horaLlegada;
	}
	public Date getHoraSalida() {
		return horaSalida;
	}
	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}
	public Float getImporte() {
		return importe;
	}
	public void setImporte(Float importe) {
		this.importe = importe;
	}
	public Integer getDias() {
		return dias;
	}
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	public Integer getHoras() {
		return horas;
	}
	public void setHoras(Integer horas) {
		this.horas = horas;
	}
	public Integer getMinutos() {
		return minutos;
	}
	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}
	
	
	
}
