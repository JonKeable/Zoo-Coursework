package ecsZoo;

import java.util.Iterator;
import java.util.Random;

/*
 * For the purpose of this simulation, this includes Elephants and Giraffes
 */
public abstract class Ungulate extends Animal{

static String[] tempDiet = {"hay", "fruit"};
	
	public Ungulate(char gender, int age, int lifeExpectancy, int health, String treat) {
		super(gender, age, lifeExpectancy, health, tempDiet, treat);
	}
	
	
	public boolean aMonthPasses() {
		//takes damage specified in superclass every month
		this.decreaseHealth(); 
		
		//tries to eat and produces waste if applicable
		this.eat(); 
		
		//each month the animal gets 1 month older
		this.incrementAge();
		
		//If the animal is a female of age greater than 8 months, try to become pregnant, or progress a current pregnancy.
		if(this.getGender() == 'f' && this.getAge() >= 8) {
			if(!this.isPregnant()) {
				this.tryToBreed();
			}
			else if(this.gestate()) {
				this.giveBirth();
			}
		}
		
		//once animals reach life expectancy, they die (health := 0), unless they are pregnant in which case they will live long enough to give birth.
		if(this.getAge() >= this.getLifeExpectancy() && !this.isPregnant()) {
			this.die();
		}
		
		//If the animal has less than 1 health, this returns false(can be used to model death)
		if(this.isDead()) {
			this.printDeathMsg();
			return false; //dead 
		}
		else return true; //alive
	}
	
	//If there is male of the same species, try to breed with chance of 1 in 5. Can only try to mate with one individual per month.
	private void tryToBreed() {
		Iterator<Animal> it = this.getEnclosure().getResidentsIt();
		while(it.hasNext()) {
			Animal a = it.next();
			if(a.getClass().equals(this.getClass()) && a.getGender() == 'm') {
				Random random = new Random();
				if(random.nextInt(5) == 4) {
					this.makePregnant(8);
				}
				return;
			}
		}
	}
	
	protected abstract void printDeathMsg();
}
