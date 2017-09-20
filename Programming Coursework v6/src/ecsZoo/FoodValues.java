package ecsZoo;

//This is used to store the healing and waste values produced by each food type
public class FoodValues {

	private int healing;
	private int waste;
	
	public int getHealing() {
		return healing;
	}
	
	public int getWaste() {
		return waste;
	}
	
	public FoodValues(int h,int w) {
		healing = h;
		waste = w;
	}
	
}
