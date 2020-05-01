package model.units;

import java.util.ArrayList;

import simulation.Address;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.people.Citizen;

public abstract class PoliceUnit extends Unit implements SOSResponder {

	private ArrayList<Citizen> passengers;
	private int maxCapacity;
	private int distanceToBase;

	public PoliceUnit(String id, Address location, int stepsPerCycle,WorldListener worldListener, int maxCapacity) {

		super(id, location, stepsPerCycle,worldListener);
		this.maxCapacity = maxCapacity;
		passengers = new ArrayList<Citizen>();

	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public int getDistanceToBase() {
		return distanceToBase;
	}

	public void setDistanceToBase(int distanceToBase) {
		this.distanceToBase = distanceToBase;
	}

	public ArrayList<Citizen> getPassengers() {
		return passengers;
	}

}
