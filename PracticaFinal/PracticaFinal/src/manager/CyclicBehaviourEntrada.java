package manager;

import java.io.IOException;
import java.io.Serializable;

import entities.DatosPlazaDto;
import entities.FacturaDto;
import extra.Utils;
import jade.content.lang.sl.SLCodec;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class CyclicBehaviourEntrada extends CyclicBehaviour{

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		ACLMessage msg=this.myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology("entrar")));
		
		try
		{
			//Numero de serie*matricula
			String[] datosEscaner = ((String) msg.getContentObject()).split("/");
			String parking = ManagerAgent.parkings.get(datosEscaner[0]);
			FacturaDto factura;
			if(parking!=null) {
				String datosPeticion = "reservar" +"/"+ parking +"/"+ datosEscaner[1];
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
				ACLMessage msg2=this.myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology("ontologia")));
				factura = new FacturaDto((DatosPlazaDto)msg2.getContentObject());
			}else {//No existe parking buscado
				factura = new FacturaDto("Error -> no existe parking con numero de serie: "+datosEscaner[0]);
			}
			ACLMessage respuesta = new ACLMessage(ACLMessage.REQUEST);
			respuesta.addReceiver(msg.getSender());
			respuesta.setOntology("ontologia");
			respuesta.setLanguage(new SLCodec().getName());
			respuesta.setEnvelope(new Envelope());
			respuesta.getEnvelope().setPayloadEncoding("ISO8859_1");
			respuesta.setContentObject((Serializable)factura);
			this.myAgent.send(respuesta);       		
		}catch (UnreadableException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
