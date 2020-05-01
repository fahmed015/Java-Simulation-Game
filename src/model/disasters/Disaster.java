package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.DisasterException;
import model.events.SOSListener;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Disaster implements Simulatable {
	private int startCycle;
	private Rescuable target;
	private boolean active;

	public Disaster(int startCycle, Rescuable target) {
		this.startCycle = startCycle;
		this.target = target;
		this.active = false;
	}

	public int getStartCycle() {
		return startCycle;
	}

	public Rescuable getTarget() {
		return target;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public abstract void cycleStep();

	public void strike() throws BuildingAlreadyCollapsedException,CitizenAlreadyDeadException {
		if( this.target instanceof Citizen) {
			if(((Citizen) this.target).getState().equals(CitizenState.DECEASED)) {
				throw new CitizenAlreadyDeadException(this,"The disaster is striking a citizen that is already dead");
			}
		}
		if( this.target instanceof ResidentialBuilding) {
			if(((ResidentialBuilding) this.target).getStructuralIntegrity()==0) {
				throw new BuildingAlreadyCollapsedException(this," The disaster is striking a building that is already collapsed");
			}
		}
		
		
	}
	
}
