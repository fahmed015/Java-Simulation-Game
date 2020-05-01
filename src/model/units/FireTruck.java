package model.units;

import exceptions.CannotTreatException;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class FireTruck extends FireUnit implements SOSResponder {

	public FireTruck(String id, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(id, location, stepsPerCycle, worldListener);
	}

	public void treat() {
		super.treat();
		(this.getTarget().getDisaster()).setActive(false);

		int x = (((ResidentialBuilding) (this.getTarget())).getFireDamage()) - 10;
		((ResidentialBuilding) this.getTarget()).setFireDamage(x);
	
		if ((((ResidentialBuilding) ((FireUnit) this).getTarget()).getFireDamage()) <= 0) {
			this.jobsDone();
		}
		
	}
}