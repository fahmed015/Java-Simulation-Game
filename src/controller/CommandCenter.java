package controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;

import exceptions.CannotTreatException;
import exceptions.UnitException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.PoliceUnit;
import model.units.Unit;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulator;

import view.GameView;
import view.buildingbutton;
import view.citizenbutton;
import view.startgamebutton;
import view.unitbutton;

public class CommandCenter implements SOSListener, ActionListener {
	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<unitbutton> buttonsofunit;
	private ArrayList<citizenbutton> buttonsofcitizen;
	private ArrayList<buildingbutton> buttonsofbuilding;
	private ArrayList<String> activedisasters;
	private ArrayList<Disaster> struckdisasters;
	private boolean unitclicked = false;
	private Unit thisunit;
	private Citizen thiscitizen;
	private ResidentialBuilding thisbuilding;
	private GameView view;
	private buildingbutton Building;
	private citizenbutton citizen;
	private citizenbutton citizen2;
	private unitbutton unit;
	private ArrayList<Citizen> incitizen;

	private int counter = 1;

	private ArrayList<Address> unitaddresses;

	private ArrayList<Citizen> diedcitizens;
	private ArrayList<Unit> unitsselected;
	private ArrayList<Citizen> passengerslist;

	private boolean diedfound = false;
	private boolean struckfound = false;

	public CommandCenter() throws Exception {

		view = new GameView();
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = new ArrayList<Unit>();
		buttonsofunit = new ArrayList<unitbutton>();
		buttonsofcitizen = new ArrayList<citizenbutton>();
		buttonsofbuilding = new ArrayList<buildingbutton>();
		diedcitizens = new ArrayList<Citizen>();
		unitaddresses = new ArrayList<Address>();
		unitsselected = new ArrayList<Unit>();
		activedisasters = new ArrayList<String>();
		struckdisasters = new ArrayList<Disaster>();
		passengerslist = new ArrayList<Citizen>();
		incitizen = new ArrayList<Citizen>();

		engine = new Simulator(this);
		for (int i = 0; i < engine.getEmergencyUnits().size(); i++) {
			emergencyUnits.add(engine.getEmergencyUnits().get(i));

		}

		view.getEndcycle().addActionListener(this);
		view.getStart().addActionListener(this);

		int d = this.emergencyUnits.size();
		int b = this.visibleBuildings.size();
		int c = this.visibleCitizens.size();
		for (int i = 0; i < b; i++) {
			(this.visibleBuildings.get(i)).setEmergencyService(this);
		}
		for (int i = 0; i < b; i++) {
			(this.visibleCitizens.get(i)).setEmergencyService(this);
		}

		view.setVisible(true);
	}

	public void receiveSOSCall(Rescuable r) {

		if (r instanceof ResidentialBuilding) {

			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);

		} else {

			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}

	public void cont() {

		int d = this.emergencyUnits.size();
		int b = this.visibleBuildings.size();
		int c = this.visibleCitizens.size();
		if (counter > 1) {
			view.erasebuilding();
			view.eraseunit();
			view.erasecitizen();
			view.eraseactivedisasters();
			buttonsofunit.clear();
			buttonsofcitizen.clear();
			buttonsofbuilding.clear();
			activedisasters.clear();

		}
		for (int i = 0; i < d; i++) {
			String name = "-";
			String pic = "null";

			Unit g = ((Unit) this.emergencyUnits.get(i));
			String target = "null";
			int w = ((Unit) this.emergencyUnits.get(i)).getLocation().getX();
			int r = ((Unit) this.emergencyUnits.get(i)).getLocation().getY();
			unit = new unitbutton();
			unit.addActionListener(this);

			if (g instanceof Ambulance) {
				name = "AMB";
				target = "Citizen";
				pic = "6a0133f2340c48970b01bb09aa450a970d-600wi2.png";
			}

			if (g instanceof FireTruck) {
				name = "Fire Truck";
				target = "Building";
				pic = "il_340x270.1602444079_l5ob.jpg";

			}

			if (g instanceof Evacuator) {
				name = "Evacuator";
				target = "Building";
				pic = "download.png";

			}

			if (g instanceof DiseaseControlUnit) {
				name = "Disease Control Unit";
				target = "Citizen";
				pic = "Safety-Care-Information-Danger-Symbol-Free-Image-W-3864.jpg";
			}
			if (g instanceof GasControlUnit) {
				name = "Gas Control Unit";
				target = "Building";
				pic = "57402802-gas-leak-warning-sign-pollution-gas-pipe-icon-sign.jpg";

			}

			unit.setIcon(new ImageIcon(pic));

			unit.setPreferredSize(new Dimension(20, 20));
			if (g.getTarget() == null) {

				unit.setToolTipText("<html>" + "Unit Type :" + name + "<br>" + "Unit ID :" + g.getUnitID() + "<br>"
						+ "Steps per Cycle ::" + g.getStepsPerCycle() + "<br>" + "Unit State :" + g.getState() + "<br>"
						+ "Unit Location :" + "(" + g.getLocation().getX() + "," + g.getLocation().getY() + ")" + "<br>"
						+ "<html>");
			} else {
				unit.setToolTipText("<html>" + "Unit Type :" + name + "<br>" + "Unit ID :" + g.getUnitID() + "<br>"
						+ "Steps per Cycle ::" + g.getStepsPerCycle() + "<br>" + "Unit State :" + g.getState() + "<br>"
						+ "Unit Location :" + "(" + g.getLocation().getX() + "," + g.getLocation().getY() + ")" + "<br>"
						+ "Target :" + target + " in Location (" + g.getTarget().getLocation().getX() + ","
						+ g.getTarget().getLocation().getY() + ")" + "<html>");
			}

			if (g instanceof Evacuator) {

				unit.setToolTipText(
						unit.getToolTipText() + "<br>" + "Passenger Number :" + ((Evacuator) g).getPassengers().size()
								+ "<br>" + "Maximum Capacity :" + ((Evacuator) g).getMaxCapacity() + "<br>"
								+ "passengers Information :" + "<br>" + "__________ " + "<html>");

				for (int y = 0; y < ((Evacuator) g).getPassengers().size(); y++) {
					Citizen k = ((Evacuator) g).getPassengers().get(y);

					unit.setToolTipText("<html>" + unit.getToolTipText() + "<br>" + " <b> Passenger</b> " + (y + 1)
							+ "<br>" + "Citizen Name:" + k.getName() + "<br>" + "Citizen Age:" + k.getAge() + "<br>"
							+ "NationlID:" + k.getNationalID() + "<br>" + "HP:" + k.getHp() + "<br>" + "Blood Loss :"
							+ k.getBloodLoss() + "<br>" + "Toxicity :" + k.getToxicity() + "<br>" + "State :"
							+ k.getState() + "<br>" + "----------------------------" + "<html>");
				}
			}

			buttonsofunit.add(unit);
			view.addUnit(unit, w, r);

		}
		
		for(int e =0 ; e < incitizen.size();e++){

			if((incitizen.get(e)).getLocation().getX()==0 && (incitizen.get(e)).getLocation().getY()==0 ){
				//System.out.print("hot citizen fel base");
				Citizen h = incitizen.get(e);
				citizenbutton citizeninn = new citizenbutton();
				citizeninn.setPreferredSize(new Dimension(20, 20));
				
				if(h.getState().equals(CitizenState.DECEASED)){
				 citizeninn.setIcon(new ImageIcon("ICS_Victor2.svg.png"));
				 citizeninn.setToolTipText("<html>" +"<b>  CITIZEN IS DEAD </b>" +"<br>"+"Citizen Name:" + h.getName() +  "<br>"  + "Citizen Age:" + h.getAge()+ 
							"<br>"+ "NationlID:" + h.getNationalID() + "<br>"+ "HP:" + h.getHp() + "<br>"  + "Blood Loss :" + h.getBloodLoss()
							+ "<br>"+ "Toxicity :" + h.getToxicity() + "<br>"  + "State :" +h.getState()+"<br>" +"Location :" + "(0,0)" +"<br>" +"<html>");
				 
				}
				else{
					
					citizeninn.setIcon(new ImageIcon("person-icon-clipart-1.jpg"));
					citizeninn.setToolTipText("<html>" +"Citizen Name:" + h.getName() +  "<br>"  + "Citizen Age:" + h.getAge()+ 
							"<br>"+ "NationlID:" + h.getNationalID() + "<br>"+ "HP:" + h.getHp() + "<br>"  + "Blood Loss :" + h.getBloodLoss()
							+ "<br>"+ "Toxicity :" + h.getToxicity() + "<br>"  + "State :" +h.getState()+"<br>" +"Location :" + "(0,0)" +"<br>" +"<html>");
				}
				
				view.addCitizen(citizeninn, 0, 0);
			}	
				
			}

		

		for (int i = 0; i < b; i++) {

			int w = ((ResidentialBuilding) this.visibleBuildings.get(i)).getLocation().getX();
			int r = ((ResidentialBuilding) this.visibleBuildings.get(i)).getLocation().getY();
			Building = new buildingbutton();
			struckfound = false;
			ResidentialBuilding x = ((ResidentialBuilding) this.visibleBuildings.get(i));
			String picc = "null";
			// Building.setText("BUILDING");

			Building.addActionListener(this);

			String t = "none";
			if (x.getDisaster().isActive() == false || x.getDisaster() == null) {
				t = "No Disaster";
			} else {
				if (x.getDisaster() instanceof Collapse) {
					t = "Collapse";
					picc = "clipart-building-504.jpg";

				}
				if (x.getDisaster() instanceof Fire) {
					t = "Fire";
					picc = "Untitled.jpg";

				}
				if (x.getDisaster() instanceof GasLeak) {
					t = "GasLeak";
					picc = "clipart-building-50 - Copy1.jpg";
				}
			}

			Building.setToolTipText("<html>" + "Fire Damage :" + x.getFireDamage() + "<br>" + "Foundation Damage :"
					+ x.getFoundationDamage() + "<br>" + "Gas Level:" + x.getGasLevel() + "<br>"
					+ "Structural Integrity :" + x.getStructuralIntegrity() + "<br>" + "Disaster :" + t + "<br>"
					+ "Number of Occupants :" + x.getOccupants().size() + "<br>" + "Building Location :" + "("
					+ x.getLocation().getX() + "," + x.getLocation().getY() + ")" + "<br>" + "_________ " + "<br>"
					+ "Occupants information :" + "<br>" + "<html>");
			for (int j = 0; j < x.getOccupants().size(); j++) {
				diedfound = false;
				Citizen k = x.getOccupants().get(j);
				String l = "No Disaster";

				if (k.getDisaster() instanceof Infection && (k.getDisaster().isActive() == true)) {
					l = "Infection";

				} else if (k.getDisaster() instanceof Injury && (k.getDisaster().isActive() == true)) {
					l = "Injury";
				}

				Building.setToolTipText(Building.getToolTipText() + "<br>" + " <b> Occupant</b> " + (j + 1) + "<br>"
						+ "Citizen Name:" + k.getName() + "<br>" + "Citizen Age:" + k.getAge() + "<br>" + "NationlID:"
						+ k.getNationalID() + "<br>" + "HP:" + k.getHp() + "<br>" + "Blood Loss :" + k.getBloodLoss()
						+ "<br>" + "Toxicity :" + k.getToxicity() + "<br>" + "Disaster :" + l + "<br>" + "State :"
						+ k.getState() + "<br>" + "----------------------------" + "<html>");

				if (k.getState().equals(CitizenState.DECEASED)) {
					for (int u = 0; u < diedcitizens.size(); u++) {
						if (diedcitizens.get(u).equals(k)) {
							diedfound = true;
							break;
						}
					}
					if (diedfound == false) {
						diedcitizens.add(k);
						view.updatedied(k);

					}
				}

			}

			if (x.getStructuralIntegrity() == 0) {
				Building.setToolTipText(
						"<html>" + "<b>  THE BUILDING COLLAPSED </b>" + "<br>" + Building.getToolTipText());
				Building.setIcon(new ImageIcon("images (1).png"));

			} else {
				Building.setIcon(new ImageIcon("clipart-building-50.jpg"));
				if (!picc.equals("null")) {
					Building.setIcon(new ImageIcon(picc));

				}
			}

			buttonsofbuilding.add(Building);
			view.addBuilding(Building, w, r);
			Building.setPreferredSize(new Dimension(30, 45));
			String location = "(" + w + "," + r + ")";

			ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);

			if (x.getDisaster().isActive() == true) {
				activedisasters.add(t);
			}
			for (int s = 0; s < struckdisasters.size(); s++) {
				if (x.getDisaster().equals(struckdisasters.get(s))) {
					struckfound = true;

				}
			}
			if (struckfound == false) {
				view.updatestruck(t, location, "Building");
				struckdisasters.add(x.getDisaster());

			}

		}

		for (int i = 0; i < c; i++) {
			diedfound = false;
			struckfound = false;
			Citizen x = ((Citizen) this.visibleCitizens.get(i));
			int w = ((Citizen) this.visibleCitizens.get(i)).getLocation().getX();
			int r = ((Citizen) this.visibleCitizens.get(i)).getLocation().getY();
			citizen = new citizenbutton();
			(citizen).setPreferredSize(new Dimension(20, 20));
			citizen.addActionListener(this);
			String picc = "null";
			String t = "none";
			if (x.getDisaster().isActive() == false || x.getDisaster() == null) {
				t = "No Disaster";
			} else {

				if (x.getDisaster() instanceof Infection) {
					t = "Infection";
					picc = "images2.png";

				}
				if (x.getDisaster() instanceof Injury) {
					t = "Injury";
					picc = "images.jpg";

				}
			}
			citizen.setToolTipText("<html>" + "Citizen Name:" + x.getName() + "<br>" + "Citizen Age:" + x.getAge()
					+ "<br>" + "NationlID:" + x.getNationalID() + "<br>" + "HP:" + x.getHp() + "<br>" + "Blood Loss :"
					+ x.getBloodLoss() + "<br>" + "Toxicity :" + x.getToxicity() + "<br>" + "Disaster :" + t + "<br>"
					+ "State :" + x.getState() + "<br>" + "Citizen Location :" + "(" + x.getLocation().getX() + ","
					+ x.getLocation().getY() + ")" + "<br>" + "<html>");

			if (x.getState().equals(CitizenState.DECEASED)) {
				citizen.setToolTipText("<html>" + "<b>  CITIZEN IS DEAD </b>" + "<br>" + citizen.getToolTipText());
				citizen.setIcon(new ImageIcon("ICS_Victor2.svg.png"));
				for (int u = 0; u < diedcitizens.size(); u++) {
					if (diedcitizens.get(u).equals(x)) {
						diedfound = true;
						break;
					}
				}
				if (diedfound == false) {
					diedcitizens.add(x);
					view.updatedied(x);

				}
			} else {

				citizen.setIcon(new ImageIcon("person-icon-clipart-1.jpg"));
				if (!picc.equals("null")) {

					citizen.setIcon(new ImageIcon(picc));
				}

			}
			ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);

			int a = x.getLocation().getX();
			int l = x.getLocation().getY();
			String location = "(" + a + "," + l + ")";

			view.addCitizen(citizen, w, r);
			buttonsofcitizen.add(citizen);
			if (x.getDisaster().isActive() == true) {
				activedisasters.add(t);
			}
			for (int s = 0; s < struckdisasters.size(); s++) {
				if (x.getDisaster().equals(struckdisasters.get(s))) {
					struckfound = true;

				}
			}
			if (struckfound == false) {
				view.updatestruck(t, location, "Citizen " + x.getName());
				struckdisasters.add(x.getDisaster());

			}

		}
		for (int a = 0; a < activedisasters.size(); a++) {
			view.updateactive(activedisasters.get(a));
		}
		view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getStart()) {
			// GameView view= new GameView();

			((JButton) e.getSource()).setVisible(false);
			int counter = 1;
			view.getCycle().setText("Cycle : " + counter);
			try {
				engine.nextCycle();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(view, e1.getMessage());

			}
			this.cont();

		}

		else if (e.getSource() == view.getEndcycle()) {

			if (engine.checkGameOver() == false) {
				counter++;
				view.getCycle().setText("Cycle : " + counter);

				try {
					engine.nextCycle();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(view, e1.getMessage());

				}

				this.cont();

				view.getDeadpeople().setText("Dead People : " + "  " + engine.calculateCasualties());

			} else {
				JOptionPane.showMessageDialog(view,
						"GAME OVER" + "\n" + " YOUR SCORE IS  " + "\n" + engine.calculateCasualties() + "  " + "DEAD");

				view.setVisible(false);

			}
		} else if (e.getSource() instanceof unitbutton) {

			unitclicked = true;
			for (int i = 0; i < buttonsofunit.size(); i++) {
				if (e.getSource() == buttonsofunit.get(i)) {
					unit = buttonsofunit.get(i);

					thisunit = emergencyUnits.get(i);

				}

			}
		}

		else if (e.getSource() instanceof citizenbutton) {
			if (unitclicked == true) {
				unitclicked = false;
				for (int i = 0; i < buttonsofcitizen.size(); i++) {
					if (e.getSource() == buttonsofcitizen.get(i)) {
						thiscitizen = visibleCitizens.get(i);

					}

				}
				try {
					thisunit.respond(thiscitizen);
					String name = "null";
					if (thisunit instanceof Ambulance) {
						name = "AMB";

					}

					if (thisunit instanceof DiseaseControlUnit) {
						name = "Disease Control Unit";

					}

					unit.setToolTipText("<html>" + "Unit Type :" + name + "<br>" + "Unit ID :" + thisunit.getUnitID()
							+ "<br>" + "Steps per Cycle :" + thisunit.getStepsPerCycle() + "<br>" + "Unit State :"
							+ thisunit.getState() + "<br>" + "Unit Location :" + "(" + thisunit.getLocation().getX()
							+ "," + thisunit.getLocation().getY() + ")" + "<br>" + "Target : Citizen in Location ("
							+ thisunit.getTarget().getLocation().getX() + ","
							+ thisunit.getTarget().getLocation().getY() + ")" + "<html>");

				} catch (UnitException e1) {
					JOptionPane.showMessageDialog(view, e1.getMessage());

				}
			} else {
				JOptionPane.showMessageDialog(view, "Select a Unit first");

			}

		} else if (e.getSource() instanceof buildingbutton) {
			if (unitclicked == true) {
				unitclicked = false;
				for (int i = 0; i < buttonsofbuilding.size(); i++) {
					if (e.getSource().equals(buttonsofbuilding.get(i))) {
						thisbuilding = visibleBuildings.get(i);

					}

				}
				try {
					thisunit.respond(thisbuilding);
					String name = "null";

					if (thisunit instanceof FireTruck) {
						name = "Fire Truck";

					}

					if (thisunit instanceof Evacuator) {
						name = "Evacuator";

					}

					if (thisunit instanceof GasControlUnit) {
						name = "Gas Control Unit";

					}

					unit.setToolTipText("<html>" + "Unit Type :" + name + "<br>" + "Unit ID :" + thisunit.getUnitID()
							+ "<br>" + "Steps per Cycle :" + thisunit.getStepsPerCycle() + "<br>" + "Unit State :"
							+ thisunit.getState() + "<br>" + "Unit Location :" + "(" + thisunit.getLocation().getX()
							+ "," + thisunit.getLocation().getY() + ")" + "<br>" + "Target : Building in Location ("
							+ thisunit.getTarget().getLocation().getX() + ","
							+ thisunit.getTarget().getLocation().getY() + ")" + "<html>");

					if (thisunit instanceof Evacuator) {

						unit.setToolTipText(unit.getToolTipText() + "<br>" + "Passenger Number :"
								+ ((Evacuator) thisunit).getPassengers().size() + "<br>" + "Maximum Capacity :"
								+ ((Evacuator) thisunit).getMaxCapacity() + "<br>" + "passengers Information :" + "<br>"
								+ "__________ " + "<html>");

						for (int y = 0; y < ((Evacuator) thisunit).getPassengers().size(); y++) {
							Citizen k = ((Evacuator) thisunit).getPassengers().get(y);

							unit.setToolTipText("<html>" + unit.getToolTipText() + "<br>" + " <b> Passenger</b> "
									+ (y + 1) + "<br>" + "Citizen Name:" + k.getName() + "<br>" + "Citizen Age:"
									+ k.getAge() + "<br>" + "NationlID:" + k.getNationalID() + "<br>" + "HP:"
									+ k.getHp() + "<br>" + "Blood Loss :" + k.getBloodLoss() + "<br>" + "Toxicity :"
									+ k.getToxicity() + "<br>" + "State :" + k.getState() + "<br>"
									+ "----------------------------" + "<html>");
							
							
							
								
								

						}
                   for(int p =0 ; p<thisbuilding.getOccupants().size();p++){
							
							if(visibleCitizens.contains(thisbuilding.getOccupants().get(p))==false){
							incitizen.add(thisbuilding.getOccupants().get(p));
							}
							
						}
						
						
						
						
						
					}
				} catch (UnitException e1) {
					JOptionPane.showMessageDialog(view, e1.getMessage());

				}
			} else {
				JOptionPane.showMessageDialog(view, "Select a Unit first");

			}

		}

	}

	public static void main(String[] args) throws Exception {
		new CommandCenter();

	}
}
