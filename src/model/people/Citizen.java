package model.people;

import model.disasters.Disaster;

import model.events.SOSListener;
import model.events.SOSResponder;
import model.events.WorldListener;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class Citizen implements Rescuable, Simulatable {

	private CitizenState state;
	private Disaster disaster;
	private Address location;
	private String nationalID;
	private String name;
	private int age;
	private int hp = 100;
	private int bloodLoss = 0;
	private int toxicity = 0;
	private SOSListener emergencyService;
	private WorldListener worldListener;

	public Citizen(Address location, String nationalID, String name, int age, WorldListener worldListener) {
		this.location = location;
		this.nationalID = nationalID;
		this.name = name;
		this.age = age;
		state = CitizenState.SAFE;
		this.worldListener = worldListener;

	}

	public int getToxicity() {
		return toxicity;

	}

	public void setToxicity(int toxicity) {

		if (toxicity >= 100) {
			this.toxicity = 100;
			this.setHp(0);

		} else if (toxicity <= 0) {
			this.toxicity = 0;
		} else {
			this.toxicity = toxicity;
		}
	}

	public int getBloodLoss() {
		return bloodLoss;
	}

	public void setBloodLoss(int bloodLoss) {

		if (bloodLoss >= 100) {
			this.bloodLoss = 100;
			this.setHp(0);
		} else if (bloodLoss <= 0) {

			this.bloodLoss = 0;
		}

		else {
			this.bloodLoss = bloodLoss;
		}
	}

	public CitizenState getState() {
		return state;
	}

	public void setState(CitizenState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		if (hp >= 100) {
			this.hp = 100;
		} else if (hp <= 0) {
			this.hp = 0;
			this.state = CitizenState.DECEASED;
			

		} else {
			this.hp = hp;
		}
	}

	public String getNationalID() {

		return nationalID;
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	public Disaster getDisaster() {

		return disaster;

	}

	public void struckBy(Disaster d) {
		this.setDisaster(d);
		this.state = CitizenState.IN_TROUBLE;

		this.emergencyService.receiveSOSCall(this);

	}

	public void setDisaster(Disaster disaster) {
		this.disaster = disaster;
	}

	public void setNationalID(String nationalID) {
		this.nationalID = nationalID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void cycleStep() {

		if (this.bloodLoss > 0 && this.bloodLoss < 30) {
			this.setHp(this.hp - 5);
		}
		if (this.bloodLoss >= 30 && this.bloodLoss < 70) {
			this.setHp(this.hp - 10);
		}
		if (this.bloodLoss >= 70) {
			this.setHp(this.hp - 15);
		}
		if (this.toxicity > 0 && this.toxicity < 30) {
			this.setHp(this.hp - 5);
		}
		if (this.toxicity >= 30 && this.toxicity < 70) {
			this.setHp(this.hp - 10);
		}
		if (this.toxicity >= 70) {
			this.setHp(this.hp - 15);
		}

	}

}
