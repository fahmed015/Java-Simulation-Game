package model.units;

import model.events.SOSResponder;
import model.events.WorldListener;
import simulation.Address;

public abstract class FireUnit extends Unit implements SOSResponder{

	public FireUnit(String id, Address location, int stepsPerCycle,WorldListener worldListener) {
		super(id, location, stepsPerCycle, worldListener);

	}

}
