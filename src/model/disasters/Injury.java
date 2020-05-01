package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;

public class Injury extends Disaster {

	public Injury(int cycle, Citizen target) {
		super(cycle, target);
	}

	public void cycleStep() {

		int x = ((Citizen) this.getTarget()).getBloodLoss();
		x = x + 10;
		((Citizen) this.getTarget()).setBloodLoss(x);
	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		super.strike();
		this.getTarget().struckBy(this);

		this.setActive(true);

		((Citizen) (this.getTarget())).setBloodLoss(((Citizen) (this.getTarget())).getBloodLoss()+30);
	}
}
