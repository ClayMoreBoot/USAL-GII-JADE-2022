package escaner;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import entities.FacturaDto;
import extra.Utils;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class EscanerAgent extends Agent{
	public void setup() {
		CyclicBehaviour cyclicBehaviour=new CyclicBehaviour(this) 
		{
			private static final long serialVersionUID = 1L;
		
			public void action() 
			{
			
				Scanner scanner = new Scanner(System.in);
				System.out.print("Introduzca si quiere entrar o salir del parking(entrar/salir): ");
				String opcion=scanner.nextLine();
				System.out.print("Introduzca matricula: ");
				String matricula=scanner.nextLine();
				System.out.print("Introduzca numero serie(7v9v5ETw9|oq94aGz7w): ");
				String serie=scanner.nextLine();
				
				
				String info = serie + "/" + matricula;
				ACLMessage peticion = new ACLMessage(ACLMessage.REQUEST);
				peticion.setLanguage(new SLCodec().getName());
				peticion.setEnvelope(new Envelope());
				peticion.getEnvelope().setPayloadEncoding("ISO8859_1");
				try {
					peticion.setContentObject((Serializable)info);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				if(opcion.equalsIgnoreCase("entrar")) {
					peticion.addReceiver(Utils.buscarAgentes(this.myAgent, "entrada parking")[0].getName());
					peticion.setOntology("entrar");
					this.myAgent.send(peticion);
				}else {
					peticion.addReceiver(Utils.buscarAgentes(this.myAgent, "salida parking")[0].getName());
					peticion.setOntology("salir");
					this.myAgent.send(peticion);
				}
				ACLMessage msg=this.myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology("ontologia")));

				FacturaDto data=null;
				try
				{
					data = (FacturaDto) msg.getContentObject();
				}catch (UnreadableException e){
					e.printStackTrace();
				}
				if(data.getHoraSalida()!=null) { //Devuelve los datos despues de haber liberado la plaza 
					System.out.println(data.toString());
				}else if(data.getPlanta()!=null) {//Devuelve los datos despues de haber reservado la plaza
					System.out.println("\n" + data.getTexto().toUpperCase());
					System.out.println("----------------------------------------------");
					System.out.println("Planta: " + data.getPlanta());
					System.out.println("Fila: " + data.getFila());
					System.out.println("Columna: " + data.getColumna());
					System.out.println("Hora llegada: " + data.getHoraLlegada());
					System.out.println("\n");
				}else { //En caso de no haber podido reservar o liberar
					System.out.println("\n" + data.getTexto().toUpperCase()+"\n");
				}
			}
		};
		addBehaviour(cyclicBehaviour);
	}
}
