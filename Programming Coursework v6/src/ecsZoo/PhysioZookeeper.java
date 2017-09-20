package ecsZoo;

import java.util.ArrayList;

public class PhysioZookeeper extends Zookeeper{

	/*
	 * Physio zookeepers can also use the bath and neckMassage treats, as well as hug and stroke.
	 */
	public PhysioZookeeper(Foodstore fs, ArrayList<Enclosure> exhbs) {
		super(fs, exhbs);
		this.addTreatAvailable("bath");
		this.addTreatAvailable("massage");
	}
	
	public PhysioZookeeper(Foodstore fs, Enclosure enc) {
		super(fs, enc);
		treatsAvailable.add("bath");
		treatsAvailable.add("massage");
	}
	
	public PhysioZookeeper(Foodstore fs) {
		this(fs, new ArrayList<Enclosure>());
	}
}
