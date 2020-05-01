package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;

public class Collapse extends Disaster {

	public Collapse(int cycle, ResidentialBuilding target) {
		super(cycle, target);
	}

	public void cycleStep() {

		int x = ((ResidentialBuilding) this.getTarget()).getFoundationDamage();

		x = x + 10;
		((ResidentialBuilding) this.getTarget()).setFoundationDamage(x);

	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		super.strike();
		this.getTarget().struckBy(this);

		this.setActive(true);

		((ResidentialBuilding) (this.getTarget())).setFoundationDamage(((ResidentialBuilding) (this.getTarget())).getFoundationDamage()+10);

	}

}
