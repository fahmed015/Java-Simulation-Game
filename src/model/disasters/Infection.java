package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;

public class Infection extends Disaster {

	public Infection(int cycle, Citizen target) {
		super(cycle, target);
	}

	public void cycleStep() {

		int x = ((Citizen) this.getTarget()).getToxicity();
		x = x + 15;
		((Citizen) this.getTarget()).setToxicity(x);

	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		super.strike();
		this.getTarget().struckBy(this);

		this.setActive(true);

		((Citizen) (this.getTarget())).setToxicity(((Citizen) (this.getTarget())).getToxicity()+25);

	}
}
