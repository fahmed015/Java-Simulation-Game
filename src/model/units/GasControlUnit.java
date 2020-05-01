package model.units;

import exceptions.CannotTreatException;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class GasControlUnit extends FireUnit implements SOSResponder {

	public GasControlUnit(String id, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(id, location, stepsPerCycle, worldListener);
	}

	public void treat() {
		super.treat();
		(this.getTarget().getDisaster()).setActive(false);
		int x = (((ResidentialBuilding) (this.getTarget())).getGasLevel()) - 10;
		((ResidentialBuilding) this.getTarget()).setGasLevel(x);
		if ((((ResidentialBuilding) ((FireUnit) this).getTarget()).getGasLevel()) <= 0) {
			this.jobsDone();
		}
	}

}
