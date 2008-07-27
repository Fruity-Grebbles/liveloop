package com.marco.automate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DefaultModel implements AutomateModel{

	protected class StateProxy implements State{
		public List<Transition> transitions=new ArrayList<Transition>();
		public State state;
		public boolean exitNode=false;
		public StateProxy(State state){
			this.state=state;
		}
		public void enterState(AutomateModel automate) {
			state.enterState(automate);
			
		}
		public void exitState(AutomateModel automate) {
			state.exitState(automate);
		}		
	}
	protected class TransitionProxy implements Transition{
		public Transition transition;
		public State from;
		public State to;
		public TransitionProxy(Transition transition, State from,State to) {				
			this.transition = transition;
			this.from = from;
			this.to = to;
		}
		public boolean pass(AutomateModel auto) {
			return transition.pass(auto);
		}	
	}
	protected StateProxy rootNode;
	protected Map<State, StateProxy> nodes=new HashMap<State, StateProxy>();
	//protected Map<Transition, NodeTransition> transitions=new HashMap<Transition, NodeTransition>();
	
	public void addState(State st) {
		StateProxy n=new StateProxy(st);
		n.state=st;
		nodes.put(st,n);
	}
	
	StateProxy prox(State st){
		return (StateProxy)st;
	}
	TransitionProxy prox(Transition tr){
		return (TransitionProxy)tr;
	}
	
	StateProxy getNode(State st){
		StateProxy n= nodes.get(st);
		if(n==null)
			throw new IllegalArgumentException("State must be added with the addState method.");
		return n;
	}
	public void addTransition(Transition tr,State from,State to) {
		TransitionProxy n=new TransitionProxy(tr,from,to);
		prox(from).transitions.add(n);
	}

	public State getEnterState() {
		return rootNode;
	}

	public void setEnterState(State st) {
		this.rootNode=getNode(st);
	}

	public void setAsExitState(State st) {
		getNode(st).exitNode=true;		
	}

	public State getFromState(Transition t) {
		return prox(t).from;
	}

	public State getToState(Transition t) {
		return prox(t).to;
	}

	public List<Transition> getTransitions(State st) {
		return prox(st).transitions;

	}
	
	
	
	
}
