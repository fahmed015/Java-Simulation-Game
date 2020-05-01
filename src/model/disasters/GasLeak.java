package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;

public class GasLeak extends Disaster {

	public GasLeak(int cycle, ResidentialBuilding target) {
		super(cycle, target);
	}

	public void cycleStep() {

		int x = ((ResidentialBuilding) this.getTarget()).getGasLevel();
		x = x + 15;
		((ResidentialBuilding) this.getTarget()).setGasLevel(x);

	}

	public void strike() throws BuildingAlreadyCollapsedException, CitizenAlreadyDeadException {
		super.strike();
		this.getTarget().struckBy(this);

		this.setActive(true);

		((ResidentialBuilding) (this.getTarget())).setGasLevel(((ResidentialBuilding) (this.getTarget())).getGasLevel()+10);
	}

}
