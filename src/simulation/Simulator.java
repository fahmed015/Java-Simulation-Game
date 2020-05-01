package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import exceptions.CannotTreatException;
import exceptions.DisasterException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.MedicalUnit;
import model.units.Unit;
import model.units.UnitState;

public class Simulator implements WorldListener {
	private int currentCycle=0;
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][] world;
	private SOSListener emergencyService;

	public Simulator(SOSListener emergencyService) throws IOException {

		buildings = new ArrayList<ResidentialBuilding>();
		citizens = new ArrayList<Citizen>();
		emergencyUnits = new ArrayList<Unit>();
		plannedDisasters = new ArrayList<Disaster>();
		executedDisasters = new ArrayList<Disaster>();
		this.emergencyService=emergencyService;


		world = new Address[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				world[i][j] = new Address(i, j);
			}
		}

		loadBuildings("buildings.csv");
		loadCitizens("citizens.csv");
		loadDisasters("disasters.csv");
		loadUnits("units.csv");
		

		int e = this.emergencyUnits.size();
		int c = this.citizens.size();
		int b = this.buildings.size();
		
		for (int i = 0; i < b; i++) {
			buildings.get(i).setEmergencyService(emergencyService);
			
		}
		

		for (int i = 0; i < e; i++) {
			(this.emergencyUnits.get(i)).setWorldListener(this);
		}
		for (int i = 0; i < c; i++) {
			(this.citizens.get(i)).setWorldListener(this);
			(this.citizens.get(i)).setEmergencyService(emergencyService);
		}
		System.out.println("t" +emergencyUnits.size());
	}

	private static ArrayList<String> readFile(String filePath) throws IOException {
		ArrayList<String> result = new ArrayList<String>();
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			result.add(currentLine);

		}
		return result;

	}

	private void loadBuildings(String filePath) throws IOException {

		ArrayList<String> result = readFile(filePath);
		for (int i = 0; i < result.size(); i++) {
			String line = result.get(i);
			String[] r = line.split(",");
			int LOCATION_X = Integer.parseInt(r[0]);
			int LOCATION_Y = Integer.parseInt(r[1]);
			Address LOCATION = world[LOCATION_X][LOCATION_Y];
			ResidentialBuilding R = new ResidentialBuilding(LOCATION);
			buildings.add(R);
		}
	}

	private void loadCitizens(String filePath) throws IOException {

		ArrayList<String> result = readFile(filePath);
		for (int i = 0; i < result.size(); i++) {
			String line = result.get(i);
			String[] r = line.split(",");
			int LOCATION_X = Integer.parseInt(r[0]);
			int LOCATION_Y = Integer.parseInt(r[1]);
			Address LOCATION = world[LOCATION_X][LOCATION_Y];
			String NATIONAL_ID = r[2];
			String NAME = r[3];
			int AGE = Integer.parseInt(r[4]);
			Citizen C = new Citizen(LOCATION, NATIONAL_ID, NAME, AGE,this);
			C.setEmergencyService(emergencyService);
			citizens.add(C);
			for (int j = 0; j < buildings.size(); j++) {
				if (buildings.get(j).getLocation().equals(LOCATION)) {
					buildings.get(j).addCitizen(C);
				}

			}
		}
	}

	private void loadDisasters(String filePath) throws IOException {
		ArrayList<String> result = readFile(filePath);
		for (int i = 0; i < result.size(); i++) {
			String line = result.get(i);
			String[] r = line.split(",");
			int START_CYCLE = Integer.parseInt(r[0]);

			if (r[1].equals("INJ")) {
				String nationalID = r[2];
				for (int j = 0; j < citizens.size(); j++) {
					if (citizens.get(j).getNationalID().equals(nationalID)) {

						Disaster DISASTER_TYPE = new Injury(START_CYCLE, citizens.get(j));
						plannedDisasters.add(DISASTER_TYPE);
					}
				}

			} else if (r[1].equals("INF")) {
				String nationalID = r[2];
				for (int j = 0; j < citizens.size(); j++) {
					if (citizens.get(j).getNationalID().equals(nationalID)) {

						Disaster DISASTER_TYPE = new Infection(START_CYCLE, citizens.get(j));
						plannedDisasters.add(DISASTER_TYPE);
					}
				}

			} else if (r[1].equals("FIR")) {
				int x = Integer.parseInt(r[2]);
				int y = Integer.parseInt(r[3]);
				Address A = world[x][y];
				for (int j = 0; j < buildings.size(); j++) {
					if (buildings.get(j).getLocation().equals(A)) {
						Disaster DISASTER_TYPE = new Fire(START_CYCLE, buildings.get(j));
						plannedDisasters.add(DISASTER_TYPE);
					}

				}
			} else if (r[1].equals("GLK")) {
				int x = Integer.parseInt(r[2]);
				int y = Integer.parseInt(r[3]);
				Address A = world[x][y];
				for (int j = 0; j < buildings.size(); j++) {
					if (buildings.get(j).getLocation().equals(A)) {
						Disaster DISASTER_TYPE = new GasLeak(START_CYCLE, buildings.get(j));
						plannedDisasters.add(DISASTER_TYPE);
					}

				}

			}
		}
	}

	private void loadUnits(String filePath) throws IOException {
		ArrayList<String> result = readFile(filePath);
		for (int i = 0; i < result.size(); i++) {
			String line = result.get(i);
			String[] r = line.split(",");
			Address LOCATION = world[0][0];
			String UNIT_ID = r[1];
			int STEPS_PER_CYCLE = Integer.parseInt(r[2]);
			if (r[0].equals("AMB")) {
				Unit U = new Ambulance(UNIT_ID, LOCATION, STEPS_PER_CYCLE,this);
				emergencyUnits.add(U);
			} else if (r[0].equals("DCU")) {
				Unit U = new DiseaseControlUnit(UNIT_ID, LOCATION, STEPS_PER_CYCLE,this);
				emergencyUnits.add(U);

			} else if (r[0].equals("EVC")) {
				int CAPACITY = Integer.parseInt(r[3]);

				Unit U = new Evacuator(UNIT_ID, LOCATION, STEPS_PER_CYCLE, this,CAPACITY);
				emergencyUnits.add(U);
			} else if (r[0].equals("FTK")) {
				Unit U = new FireTruck(UNIT_ID, LOCATION, STEPS_PER_CYCLE,this);
				emergencyUnits.add(U);

			} else if (r[0].equals("GCU")) {
				Unit U = new GasControlUnit(UNIT_ID, LOCATION, STEPS_PER_CYCLE,this);
				emergencyUnits.add(U);

			}

		}

	}

	public ArrayList<Unit> getEmergencyUnits() {
		return emergencyUnits;
	}

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	public void assignAddress(Simulatable sim, int x, int y) {

		if (sim instanceof Unit) {
			((Unit) sim).setLocation(world[x][y]);
		}
		if (sim instanceof Citizen) {
			((Citizen) sim).setLocation(world[x][y]);
		}
	}

	public boolean checkGameOver() {

		if (plannedDisasters.size() != 0)
			return false;

		for (int i = 0; i < executedDisasters.size(); i++) {
			if (executedDisasters.get(i).isActive()) {

				Disaster d = executedDisasters.get(i);
				Rescuable r = d.getTarget();
				if (r instanceof Citizen) {
					Citizen c = (Citizen) r;
					if (c.getState() != CitizenState.DECEASED)
						return false;
				} else {

					ResidentialBuilding b = (ResidentialBuilding) r;
					if (b.getStructuralIntegrity() != 0)
						return false;
				}

			}

		}

		for (int i = 0; i < emergencyUnits.size(); i++) {
			if (emergencyUnits.get(i).getState() != UnitState.IDLE)
				return false;
		}

		return true;
	}

	public int calculateCasualties() {
		int diednumber = 0;
		for (int i = 0; i < citizens.size(); i++) {
			if ((citizens.get(i).getState()).equals(CitizenState.DECEASED)) {
				diednumber = diednumber + 1;
			}
		}

		return diednumber;
	}

	public void nextCycle() throws Exception{

		currentCycle++;

		for (int i = 0; i < plannedDisasters.size(); i++) {
			Disaster d = plannedDisasters.get(i);
			if (d.getStartCycle() == currentCycle) {
				plannedDisasters.remove(d);
				i--;
				if (d instanceof Fire)
					handleFire(d);
				else if (d instanceof GasLeak)
					handleGas(d);
				else {
					d.strike();
					executedDisasters.add(d);
				}
			}
		}

		for (int i = 0; i < buildings.size(); i++) {
			ResidentialBuilding b = buildings.get(i);
			if (b.getFireDamage() >= 100) {
				b.getDisaster().setActive(false);
				b.setFireDamage(0);
				Collapse c = new Collapse(currentCycle, b);
				c.strike();
				executedDisasters.add(c);
			}
		}

		for (int i = 0; i < emergencyUnits.size(); i++) {
			emergencyUnits.get(i).cycleStep();
		}

		for (int i = 0; i < executedDisasters.size(); i++) {
			Disaster d = executedDisasters.get(i);
			if (d.getStartCycle() < currentCycle && d.isActive())
				d.cycleStep();
		}

		for (int i = 0; i < buildings.size(); i++) {
			buildings.get(i).cycleStep();
		}

		for (int i = 0; i < citizens.size(); i++) {
			citizens.get(i).cycleStep();
		}


	}

	private void handleGas(Disaster d) throws Exception {
		ResidentialBuilding b = (ResidentialBuilding) d.getTarget();
		if (b.getFireDamage() != 0) {
			b.setFireDamage(0);
			Collapse c = new Collapse(currentCycle, b);
			c.strike();
			executedDisasters.add(c);
		} else {
			d.strike();
			executedDisasters.add(d);
		}
	}

	private void handleFire(Disaster d)throws Exception {
		ResidentialBuilding b = (ResidentialBuilding) d.getTarget();
		if (b.getGasLevel() == 0) {
			d.strike();
			executedDisasters.add(d);
		} else if (b.getGasLevel() < 70) {
			b.setFireDamage(0);
			Collapse c = new Collapse(currentCycle, b);
			c.strike();
			executedDisasters.add(c);
		} else
			b.setStructuralIntegrity(0);

	}


	public static void main(String[] args) throws IOException {

	}

}
