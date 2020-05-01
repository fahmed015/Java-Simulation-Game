package model.infrastructure;

import java.util.*;
import java.util.ArrayList;

import model.disasters.Disaster;
import model.events.SOSListener;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class ResidentialBuilding implements Rescuable, Simulatable {

	private Address location;
	private int structuralIntegrity = 100;
	private int fireDamage = 0;
	private int gasLevel = 0;
	private int foundationDamage = 0;
	private ArrayList<Citizen> occupants;
	private Disaster disaster;
	private SOSListener emergencyService;

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	public ResidentialBuilding(Address location) {
		this.location = location;
		occupants = new ArrayList<Citizen>();
	}

	public void addCitizen(Citizen c) {
		this.occupants.add(c);
	}

	public int getStructuralIntegrity() {
		return structuralIntegrity;
	}

	public int getFireDamage() {
		return fireDamage;
	}

	public int getGasLevel() {
		return gasLevel;
	}

	public int getFoundationDamage() {
		return foundationDamage;
	}

	public ArrayList<Citizen> getOccupants() {
		return occupants;
	}

	public void setStructuralIntegrity(int structuralIntegrity) {

		if (structuralIntegrity <= 0) {
			this.structuralIntegrity = 0;
			for (int i = occupants.size() - 1; i >= 0; i--) {
				(this.occupants.get(i)).setHp(0);

			}
		} else {
			this.structuralIntegrity = structuralIntegrity;
		}
	}

	public void setFireDamage(int fireDamage) {
		if (fireDamage >= 100) {
			this.fireDamage = 100;
		} else if (fireDamage <= 0) {
			this.fireDamage = 0;
		}

		else {
			this.fireDamage = fireDamage;
		}
	}

	public void setGasLevel(int gasLevel) {
		if (gasLevel >= 100) {
			this.gasLevel = 100;
			for (int i = occupants.size() - 1; i >= 0; i--) {
				(this.occupants.get(i)).setHp(0);

			}
		} else if (gasLevel <= 0) {
			this.gasLevel = 0;
		} else {
			this.gasLevel = gasLevel;
		}
	}

	public void setFoundationDamage(int foundationDamage) {
		if (foundationDamage >= 100) {
			this.foundationDamage=100;
			this.setStructuralIntegrity(0);
		}
		this.foundationDamage = foundationDamage;
	}

	public void struckBy(Disaster d) {
		this.disaster = d;

		this.emergencyService.receiveSOSCall(this);

	}

	public Address getLocation() {
		return location;
	}

	public Disaster getDisaster() {
		return disaster;
	}

	public void cycleStep() {
		if (this.foundationDamage > 0) {
			int rand = (int) ((Math.random() * 6) + 5);
			this.setStructuralIntegrity(this.structuralIntegrity - rand);

		}
		if (this.fireDamage > 0 && this.fireDamage < 30) {
			this.setStructuralIntegrity(this.structuralIntegrity - 3);
		}
		if (this.fireDamage >= 30 && this.fireDamage < 70) {
			this.setStructuralIntegrity(this.structuralIntegrity - 5);

		}
		if (this.fireDamage >= 70) {
			this.setStructuralIntegrity(this.structuralIntegrity - 7);

		}

	}

}
