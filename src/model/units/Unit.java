package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.disasters.Collapse;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {

	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target = null;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String id, Address location, int stepsPerCycle, WorldListener worldListener) {

		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.unitID = id;
		state = UnitState.IDLE;
		this.worldListener = worldListener;

	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	public void treat() {

		this.setState(UnitState.TREATING);

	}

	public boolean canTreat(Rescuable r) {
		boolean b = true;
		if (r instanceof Citizen) {
			if (this instanceof Ambulance) {
				if (((Citizen) (r)).getBloodLoss() == 0) {
					b = false;
				} else {
					b = true;
				}
			} else if (this instanceof DiseaseControlUnit) {
				if (((Citizen) (r)).getToxicity() == 0) {
					b = false;
				} else {
					b = true;
				}

			}
		}

		else if (r instanceof ResidentialBuilding) {
			if (this instanceof Evacuator) {
				if (((ResidentialBuilding) (r)).getFoundationDamage() == 0) {
					b = false;

				} else {
					b = true;
				}
			} else if (this instanceof FireTruck) {
				if (((ResidentialBuilding) (r)).getFireDamage() == 0) {
					return false;
				} else {
					b = true;
				}
			} else if (this instanceof GasControlUnit) {
				if (((ResidentialBuilding) (r)).getGasLevel() == 0) {
					return false;

				} else {
					b = true;
				}

			}

		}
		return b;

	}

	public void cycleStep() {
		if (this.state != UnitState.IDLE) {

			if (this instanceof Evacuator) {

				if (((Evacuator) this).getPassengers().size() == 0) {

					if (this.distanceToTarget == 0) {

						if (((ResidentialBuilding) this.target).getOccupants().size() == 0
								|| ((ResidentialBuilding) this.target).getStructuralIntegrity() == 0) {

							this.jobsDone();
						}

						else {

							((Evacuator) this).treat();
						}
					}

					else {

						this.distanceToTarget = this.distanceToTarget - this.stepsPerCycle;
						if (this.distanceToTarget <= 0) {
							this.distanceToTarget = 0;

							int f = this.target.getLocation().getX();
							int y = this.target.getLocation().getY();
							this.worldListener.assignAddress(this, f, y);
							((Evacuator) this).setDistanceToBase(f + y);

							if (((ResidentialBuilding) this.target).getOccupants().size() == 0
									|| ((ResidentialBuilding) this.target).getStructuralIntegrity() == 0) {

								this.jobsDone();
							}
						}

					}
				}

				else {
					if (((Evacuator) this).getDistanceToBase() > 0) {
						((Evacuator) this)
								.setDistanceToBase(((Evacuator) this).getDistanceToBase() - (this.stepsPerCycle));

					}

					if (((Evacuator) this).getDistanceToBase() <= 0) {

						((Evacuator) this).setDistanceToBase(0);
						int a = this.target.getLocation().getX();
						int b = this.target.getLocation().getY();
						this.distanceToTarget = a + b;
						this.worldListener.assignAddress(this, 0, 0);

						int c = ((Evacuator) this).getPassengers().size();
						int j = 0;
						while (c != 0) {
							(((Evacuator) this).getPassengers().get(j)).setState(CitizenState.RESCUED);
							this.getWorldListener().assignAddress((((Evacuator) this).getPassengers().get(j)), 0, 0);
							((Evacuator) this).getPassengers().remove(j);
							c--;

						}

						if (((ResidentialBuilding) this.target).getOccupants().size() == 0) {

							this.jobsDone();
						}
					}

				}
			}

			else

			{
				if (this.target instanceof ResidentialBuilding) {
					if (this.distanceToTarget == 0) {
						int f = this.target.getLocation().getX();
						int y = this.target.getLocation().getY();
						this.worldListener.assignAddress(this, f, y);

						if (((ResidentialBuilding) this.target).getStructuralIntegrity() == 0) {

							this.jobsDone();
						}

						else {

							(this).treat();
						}
					}

					else {

						this.distanceToTarget = this.distanceToTarget - this.stepsPerCycle;
						if (this.distanceToTarget <= 0) {
							this.distanceToTarget = 0;

							int f = this.target.getLocation().getX();
							int y = this.target.getLocation().getY();
							this.worldListener.assignAddress(this, f, y);

							if (((ResidentialBuilding) this.target).getStructuralIntegrity() == 0) {

								this.jobsDone();
							}
						}

					}

				} else {
					if (this.target instanceof Citizen) {
						if (this.distanceToTarget == 0) {
							int f = this.target.getLocation().getX();
							int y = this.target.getLocation().getY();
							this.worldListener.assignAddress(this, f, y);

							if (((Citizen) this.getTarget()).getState().equals(CitizenState.DECEASED)) {

								this.jobsDone();
							}

							else {

								(this).treat();
							}
						}

						else {

							this.distanceToTarget = this.distanceToTarget - this.stepsPerCycle;
							if (this.distanceToTarget <= 0) {

								this.distanceToTarget = 0;
								int f = this.target.getLocation().getX();
								int y = this.target.getLocation().getY();
								this.worldListener.assignAddress(this, f, y);

								if (((Citizen) this.getTarget()).getState().equals(CitizenState.DECEASED)) {
									this.jobsDone();
								}
							}

						}
					}

				}
			}
		}
	}

	public void jobsDone() {

		this.target = null;
		this.state = UnitState.IDLE;

	}

	public void respond(Rescuable r) throws UnitException, CannotTreatException {

		if (!(this.canTreat(r))) {

			throw new CannotTreatException(this, this.target, "This unit cannot treat this target");
		}

		if (this instanceof MedicalUnit && r instanceof ResidentialBuilding) {
			throw new IncompatibleTargetException(this, r,
					"Resdiential Buildings are not compatible with the selected Unit");
		}

		else if ((this instanceof PoliceUnit || this instanceof FireUnit) && r instanceof Citizen) {
			throw new IncompatibleTargetException(this, r, "Citizens are not compatible with the  selected Unit");

		} else {
			this.setState(UnitState.RESPONDING);

			if (this instanceof MedicalUnit && ((Citizen) r).getBloodLoss() == 0 && ((Citizen) r).getToxicity() == 0
					&& ((Citizen) r).getHp() < 100) {

				this.target = r;

				this.setState(UnitState.RESPONDING);

			}

			else if (this.target != null) {
				this.target.getDisaster().setActive(true);
				this.target = r;
				this.setState(UnitState.RESPONDING);
			}

			else if (this.target == null) {
				this.target = r;
				this.setState(UnitState.RESPONDING);
			}

			Address a = this.location;
			Address b = this.target.getLocation();
			int x = a.getX();
			int y = a.getY();
			int z = b.getX();
			int w = b.getY();

			int f = Math.abs(x - z);
			int q = Math.abs(y - w);

			this.distanceToTarget = f + q;

		}

	}

}
