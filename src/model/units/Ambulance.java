package model.units;

import exceptions.CannotTreatException;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public class Ambulance extends MedicalUnit implements SOSResponder {

	public Ambulance(String id, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(id, location, stepsPerCycle, worldListener);
	}

	public void treat()  {
		super.treat();
		
		(this.getTarget().getDisaster()).setActive(false);

		if (((Citizen) (this.getTarget())).getBloodLoss() > 0) {
			int x = ((Citizen) (this.getTarget())).getBloodLoss() - (((MedicalUnit) this).getTreatmentAmount());
			((Citizen) this.getTarget()).setBloodLoss(x);
		}
		if (((Citizen) (this.getTarget())).getBloodLoss() == 0) {
			((Citizen) this.getTarget()).setState(CitizenState.RESCUED);
			((MedicalUnit) this).heal();

		}

	}
}
