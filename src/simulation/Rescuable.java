package simulation;

import model.disasters.Disaster;
import model.events.SOSListener;

public interface Rescuable{
	public void struckBy(Disaster d);
	public Address getLocation();
	public Disaster getDisaster();
}
