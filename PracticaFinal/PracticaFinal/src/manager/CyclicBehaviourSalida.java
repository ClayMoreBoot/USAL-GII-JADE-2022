package manager;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import entities.DatosPlazaDto;
import entities.FacturaDto;
import extra.Utils;
import jade.content.lang.sl.SLCodec;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class CyclicBehaviourSalida extends CyclicBehaviour{

	@Override
	public void action() {
		ACLMessage msg=this.myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology("salir")));
		
		try
		{
			String[] datosEscaner = ((String) msg.getContentObject()).split("/");
			String parking = ManagerAgent.parkings.get(datosEscaner[0]);
			FacturaDto factura;
			if(parking!=null) {
				String datosPeticion = "liberar" +"/"+ parking +"/"+ datosEscaner[1];
				//Mirar el mapa y enviar peticion al agente parking
				ACLMessage peticion = new ACLMessage(ACLMessage.REQUEST);
				peticion.addReceiver(Utils.buscarAgentes(this.myAgent, "gestionar aparcamiento")[0].getName());
				peticion.setOntology("ontologia");
				peticion.setLanguage(new SLCodec().getName());
				peticion.setEnvelope(new Envelope());
				peticion.getEnvelope().setPayloadEncoding("ISO8859_1");
				peticion.setContentObject((Serializable)datosPeticion);
				this.myAgent.send(peticion);       		
				
				
				//La respuesta recibida la devuelves al agente que envio la peticion
				ACLMessage msg2 = this.myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology("ontologia")));
			
				DatosPlazaDto data = (DatosPlazaDto) msg2.getContentObject();
			
				if(data.getHoraLlegada()!=null) { //Liberacion exitosa
					Date horaSalida = new Date();
					Long tiempo = horaSalida.getTime() - data.getHoraLlegada().getTime(); //Seconds between dates
					Integer dias=(int) (tiempo/(24*60*60*1000));
					Integer horas=(int) (tiempo/(60*60*1000) - dias*24);
					Integer minutos=(int) ((tiempo/(60*1000)) - dias*24*60 - horas*60);
					Float importe = (float) (dias * 12.0 + horas * 1 + minutos * 0.025);
					
					factura = new FacturaDto(data,horaSalida, dias, horas, minutos, importe);
				}else {//Error a la hora de liberar
					factura = new FacturaDto(data.getTexto());
				}
			}else {//No existe el parking buscado
				factura = new FacturaDto("Error -> no existe parking con numero de serie" + datosEscaner[0]);
			}
			
			
			ACLMessage respuesta = new ACLMessage(ACLMessage.REQUEST);
			respuesta.addReceiver(msg.getSender());
			respuesta.setOntology("ontologia");
			respuesta.setLanguage(new SLCodec().getName());
			respuesta.setEnvelope(new Envelope());
			respuesta.getEnvelope().setPayloadEncoding("ISO8859_1");
			respuesta.setContentObject((Serializable) factura);
			this.myAgent.send(respuesta);       		
		}catch (UnreadableException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
	
	}

}
