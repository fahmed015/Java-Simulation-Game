package model.units;

import exceptions.CannotTreatException;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public class Evacuator extends PoliceUnit implements SOSResponder {

	public Evacuator(String id, Address location, int stepsPerCycle, WorldListener worldListener, int maxCapacity) {

		super(id, location, stepsPerCycle, worldListener, maxCapacity);

	}

	public void treat() {

		super.treat();

		int x = (((PoliceUnit) this).getPassengers()).size();
		int y = ((ResidentialBuilding) this.getTarget()).getOccupants().size();
		ResidentialBuilding r = (ResidentialBuilding) this.getTarget();

		int i = 0;

		while (y > 0) {

			if (x < this.getMaxCapacity()) {

				this.getPassengers().add((r.getOccupants()).get(i));

				r.getOccupants().remove(i);

				x = x + 1;
				y--;

			} else {
				break;
			}
		}
	}

}
