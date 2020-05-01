package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;

public class Fire extends Disaster {

	public Fire(int cycle, ResidentialBuilding target) {
		super(cycle, target);
	}

	public void cycleStep() {

		int x = ((ResidentialBuilding) this.getTarget()).getFireDamage();
		x = x + 10;
		((ResidentialBuilding) this.getTarget()).setFireDamage(x);

	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		super.strike(); 
		this.getTarget().struckBy(this);
		this.setActive(true);
		((ResidentialBuilding) (this.getTarget())).setFireDamage(((ResidentialBuilding) (this.getTarget())).getFireDamage()+10);
	}
}