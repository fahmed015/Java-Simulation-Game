package model.events;

import exceptions.UnitException;
import simulation.Address;
import simulation.Rescuable;

public interface SOSResponder {
	
	public void respond(Rescuable r) throws UnitException;

}

