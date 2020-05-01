package model.units;

import model.events.SOSResponder;
import model.events.WorldListener;
import model.people.Citizen;
import simulation.Address;

public abstract class MedicalUnit extends Unit implements SOSResponder {

	private int healingAmount = 10;
	private int treatmentAmount = 10;


	public MedicalUnit(String id, Address location, int stepsPerCycle,WorldListener worldListener) {

		super(id, location, stepsPerCycle, worldListener);

	}
	public int getTreatmentAmount() {
		return treatmentAmount;
	}
	public void heal() {
			int x=((Citizen)((this).getTarget())).getHp();
			x=x+this.healingAmount;
			((Citizen)((this).getTarget())).setHp(x);
			if(((Citizen)getTarget()).getHp()==100){
			this.jobsDone();
			
			}
	}
}
	