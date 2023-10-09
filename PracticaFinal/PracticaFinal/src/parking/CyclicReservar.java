package parking;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.DatosPlazaDto;
import jade.content.lang.sl.SLCodec;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class CyclicReservar extends CyclicBehaviour{
	
	protected static HashMap<String,Parking> parkings = new HashMap<>();
	
	CyclicReservar() {
		parkings.put("Lusail", new Parking(5,5,5));
		parkings.put("Valdés", new Parking(3,10,10));
	}

	@Override
	public void action() {
		//Recibes mensaje tipo*nombreParking*matricula
		ACLMessage msg=this.myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology("ontologia")));
		
		DatosPlazaDto respuesta;
		try
		{
		String[] mensaje = msg.getContentObject().toString().split("/");
		if (mensaje[0].equals("reservar")) {
			respuesta = reservarPlaza(mensaje[1],mensaje[2]);
		}else {
			respuesta = liberarPlaza(mensaje[1],mensaje[2]);
		}
		
		ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
    	aclMessage.addReceiver(msg.getSender());
        aclMessage.setOntology("ontologia");
        aclMessage.setLanguage(new SLCodec().getName());
        aclMessage.setEnvelope(new Envelope());
		aclMessage.getEnvelope().setPayloadEncoding("ISO8859_1");
		
		aclMessage.setContentObject((Serializable)respuesta );
		this.myAgent.send(aclMessage);       		
		}
		catch (UnreadableException e)
		{
		e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public DatosPlazaDto reservarPlaza(String nombre,String matricula) {
		
		
		Parking parking = this.parkings.get(nombre);
		for(int planta=0; planta<parking.getParking().length; planta++) {
			 Integer altura = parking.getParking()[0].length, anchura = parking.getParking()[0][0].length;

	        for (
	            // Recorre los inicios de cada diagonal en los bordes de la matriz.
	            Integer diagonal = 1 - anchura; // Comienza con un número negativo.
	            diagonal <= altura - 1; // Mientras no llegue a la última diagonal.
	            diagonal += 1 // Avanza hasta el comienzo de la siguiente diagonal.
	        ) {
	            for (
	                // Recorre cada una de las diagonales a partir del extremo superior izquierdo.
	                Integer vertical = Math.max(0, diagonal), horizontal = -Math.min(0, diagonal);
	                vertical < altura && horizontal < anchura; // Mientras no excedan los límites.
	                vertical += 1, horizontal += 1 // Avanza en diagonal incrementando ambos ejes.
	            ) {
	            	if(!parking.getParking()[planta][vertical][horizontal].getOcupada()) {
	            		parking.getParking()[planta][vertical][horizontal].setOcupada(true);
	            		parking.getParking()[planta][vertical][horizontal].setMatricula(matricula);
	            		parking.getParking()[planta][vertical][horizontal].setHoraLlegada(new Date());
	            		return new DatosPlazaDto("El aparcamiento ha sido reservado con exito", planta, vertical, horizontal,
	            				parking.getParking()[planta][vertical][horizontal].getHoraLlegada());
	            	}
	            }
	        }
		}
		return new DatosPlazaDto("Todos los aparcamientos estan ocupados");
	}
	
	public DatosPlazaDto liberarPlaza(String nombre,String matricula) {
		
		Parking parking = this.parkings.get(nombre);
		for(int planta=0; planta<parking.getParking().length; planta++) {
			 Integer altura = parking.getParking()[0].length, anchura = parking.getParking()[0][0].length;

	        for (
	            // Recorre los inicios de cada diagonal en los bordes de la matriz.
	            Integer diagonal = 1 - anchura; // Comienza con un número negativo.
	            diagonal <= altura - 1; // Mientras no llegue a la última diagonal.
	            diagonal += 1 // Avanza hasta el comienzo de la siguiente diagonal.
	        ) {
	            for (
	                // Recorre cada una de las diagonales a partir del extremo superior derecho. Estas diagonales se recorren hacia abajo a la izquierda.
	                Integer vertical = Math.max(0, diagonal), horizontal = -Math.min(0, diagonal);
	                vertical < altura && horizontal < anchura; // Mientras no excedan los límites.
	                vertical += 1, horizontal += 1 // Avanza en diagonal incrementando ambos ejes.
	            ) {
	            	if(parking.getParking()[planta][vertical][horizontal].getOcupada()==true
	            		&&	parking.getParking()[planta][vertical][horizontal].getMatricula().equalsIgnoreCase(matricula)) {
	            		parking.getParking()[planta][vertical][horizontal].setOcupada(false);
	            		parking.getParking()[planta][vertical][horizontal].setMatricula(null);
	            		DatosPlazaDto data = new DatosPlazaDto("El aparcamiento ha sido liberado con exito", planta, vertical, horizontal,
	            				parking.getParking()[planta][vertical][horizontal].getHoraLlegada());
	            		parking.getParking()[planta][vertical][horizontal].setHoraLlegada(null);
	            		return data;
	            	}
	            }
	        }
			
		}
		return new DatosPlazaDto("No se ha encontrado vehiculo con matricula: " + matricula);
	}

}
