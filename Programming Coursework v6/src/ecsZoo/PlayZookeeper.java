package ecsZoo;

import java.util.ArrayList;

public class PlayZookeeper extends Zookeeper {

	/*
	 * Play zookeepers can also use the watchAFilm and playChase treats, as well as hug and stroke.
	 */
	public PlayZookeeper(Foodstore fs, ArrayList<Enclosure> exhbs) {
		super(fs, exhbs);
		this.addTreatAvailable("film");
		this.addTreatAvailable("chase");
	}
	
	public PlayZookeeper(Foodstore fs, Enclosure enc) {
		super(fs, enc);
		treatsAvailable.add("film");
		treatsAvailable.add("chase");
	}
	
	public PlayZookeeper(Foodstore fs) {
		this(fs, new ArrayList<Enclosure>());
	}
}
