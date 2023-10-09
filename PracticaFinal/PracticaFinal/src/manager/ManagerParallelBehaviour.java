package manager;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;

public class ManagerParallelBehaviour extends ParallelBehaviour{
	protected Behaviour cyclicBehaviourEntrada;	
	protected Behaviour cyclicBehaviourSalida;	
	
	public ManagerParallelBehaviour() 
	{
		super();
		
		ThreadedBehaviourFactory threadedBehaviourFactory;
		
		threadedBehaviourFactory = new ThreadedBehaviourFactory();
		cyclicBehaviourEntrada = new CyclicBehaviourEntrada();
		addSubBehaviour(threadedBehaviourFactory.wrap(cyclicBehaviourEntrada));
		
		threadedBehaviourFactory = new ThreadedBehaviourFactory();
		cyclicBehaviourSalida = new CyclicBehaviourSalida();
		addSubBehaviour(threadedBehaviourFactory.wrap(cyclicBehaviourSalida));
		
	}

}
