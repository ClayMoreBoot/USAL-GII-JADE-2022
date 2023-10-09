package manager;

import java.util.HashMap;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class ManagerAgent extends Agent{
	
	protected static HashMap<String,String> parkings = new HashMap<>();
	
	public ManagerAgent() {
		//Numero de serie del escaner -> parking
		parkings.put("7v9v5ETw9", "Lusail");
		parkings.put("oq94aGz7w", "Valdios");
	}
	
	public void setup() {
		
		//Registrar dos servicios para reserva y liberación de plazas del parking
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName("entrada parking");
		sd.setType("entrada parking");
		sd.addOntologies("ontologia");
		sd.addLanguages(new SLCodec().getName());
		dfd.addServices(sd);
		sd = new ServiceDescription();
		sd.setName("salida parking");
		sd.setType("salida parking");
		sd.addOntologies("ontologia");
		sd.addLanguages(new SLCodec().getName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addBehaviour(new ManagerParallelBehaviour());
	}

}
