package ecsZoo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Simulation {
	
	private static ArrayList<Zoo> simZoos = new ArrayList<Zoo>();
	
	public static void main(String[] args){
		
		//If launched using 1 argument
		if(args.length == 1) {
			
			//Try to read the config file, and configure the zoo based upon it
			try {
				
				File configFile = new File(args[0]);
				configZoo(configFile);
				
			}
			catch(Exception e) {
				System.err.println("A problem occured trying to read configuration file");
			}
			
			//As long as at least one zoo was created, run the simulation
			if(!simZoos.isEmpty()) {
				go();
			}
			else {
				System.err.println("No zoos created based on configuration file, exiting...");
				System.exit(-1);
			}
		}
		
		// Sets up a default zoo if there is no valid argument
		else {
			Zoo myZoo = new Zoo();
			
			myZoo.fillStore();
			
			for(int i = 0; i < 5; i++) {
				myZoo.addEnclosure(new Enclosure());
			}
			
			for(int i = 0; i < 5; i++) {
				myZoo.addKeeper(new Zookeeper(myZoo.getStore()));
			}
			
			//Some default animals
			myZoo.getEnclosure(0).addAnimal(new Bear());
			myZoo.getEnclosure(1).addAnimal(new Tiger('m', 5, 7));
			myZoo.getEnclosure(1).addAnimal(new Tiger('f', 6, 8));
			myZoo.getEnclosure(2).addAnimal(new Gorilla('m', 8, 7));
			myZoo.getEnclosure(2).addAnimal(new Gorilla('f', 7, 6));
			myZoo.getEnclosure(3).addAnimal(new Elephant('f', 5, 7));
			myZoo.getEnclosure(3).addAnimal(new Elephant('f', 12, 8));
			myZoo.getEnclosure(3).addAnimal(new Elephant('m', 5, 7));
			myZoo.getEnclosure(4).addAnimal(new Penguin('m', 7, 6));
			myZoo.getEnclosure(4).addAnimal(new Penguin('m', 7, 6));
			myZoo.getEnclosure(4).addAnimal(new Penguin('f', 7, 6));
			myZoo.getEnclosure(4).addAnimal(new Penguin('f', 7, 6));
			
			//Starts the default zoo
			myZoo.go();
		}

	}

	//Lets the user choose which zoo to run based on a config file which could contain multiple zoos
	private static void go() {
		
		boolean validInt = false;
		int zooNum = simZoos.size();
		
		//Keeps looping until the user input is a valid integer which corresponds to an existing zoo
		while(!validInt) {
			System.out.println(zooNum + " zoos in simulator");
			System.out.println("Please choos a zoo to run, numbered in the order they were added in the configuration file, from 1 to " + zooNum);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String UserIn;
			int IntIn;
	 
			try {
				UserIn = br.readLine();
				try
				{
					IntIn = Integer.parseInt(UserIn);
					if(IntIn > 0 && IntIn <= zooNum) {
						System.out.println("Starting zoo simulation...");
						System.out.println();
						simZoos.get(IntIn-1).go();
					}
				}
				catch (NumberFormatException nfe)
				{
					System.err.println("Enterd number was not a valid integer");
				}
			}
			catch (IOException ioe) {
				System.err.println("There was an error reading user Input");
			}
		}
		
 
		
		
	}

	//Converts a config file into a Zoo (or Zoos)
	private static void configZoo(File f){
		
		BufferedReader br;
		// Tries to read the file specified, but catches an exception and returns if the file cannot be found
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find specified file!");
			return;
		}
		
		
		
		try {
			
			//Used to store values and references temporarily, so that the command in the config file has the right effect on the zoo
			boolean isZoo = false;
			String line;
			Zoo currentZoo = null;
			Enclosure currentEnc = null;
			Foodstore currentFS = null;
			
			// For each line of the file convert the text into a parameter of the zoo
			while((line = br.readLine()) != null) {
				
				String[] lineParts = line.split(":");
				String cmd = lineParts[0];
				int lineLength = lineParts.length;
				
				// If there is content in the line of the config file, process it.
				if(lineLength != 0) {
					
					
					switch(cmd) {
					
					// If the first word in a line is zoo then make a new zoo, anything beyond that is ignored
					case "zoo":
						simZoos.add(new Zoo());
						currentZoo = simZoos.get(simZoos.size()-1);
						currentFS = currentZoo.getStore();
						isZoo = true;
						System.out.println("New zoo created");
						break;
					
					// If the first word in a line is zookeeper then make a new basic keeper, as long as a zoo exists to add it to.
					case "zookeeper":
						if(isZoo) {
								currentZoo.addKeeper(new Zookeeper(currentZoo.getStore()));
								System.out.println("Added new zookeeper, will assign enclosure later");					
						}
						else {
							System.err.println("No zoo to add keeper to exists");
						}
						break;
					
					// If the first word in a line is playzookeeper then make a new play keeper, as long as a zoo exists to add it to.
					case "playZookeeper":
						if(isZoo) {
							currentZoo.addKeeper(new PlayZookeeper(currentZoo.getStore()));
						}
						else {
							System.err.println("No zoo to add keeper to exists");
						}
						break;
				
					// If the first word in a line is physioZookeeper then make a new physio keeper, as long as a zoo exists to add it to.
					case "physioZookeeper":
						if(isZoo) {
							currentZoo.addKeeper(new PhysioZookeeper(currentZoo.getStore()));
						}
						else {
							System.err.println("No zoo to add keeper to exists");
						}
						break;
					
					/*
					 * If the first word in a line is enclosure then try to add an enclosure to the zoo, if one exists.
					 * If there is no waste value specified make a default enclosure, otherwise make an enclosure with
					 * the amount of waste specified by the second part of the line.
					 */
					case "enclosure":
						if(isZoo) {
							
							if(lineLength < 2) {
								currentZoo.addEnclosure(new Enclosure());
								currentEnc = currentZoo.getLastEnc();
								currentFS = currentEnc.getFoodstore();
								System.out.println("New Enclosure added to zoo");
							}
							
							else {
								try {
									int wasteVal;
									wasteVal = Integer.parseInt(lineParts[1]);
									currentZoo.addEnclosure(new Enclosure(wasteVal));
									currentEnc = currentZoo.getLastEnc();
									currentFS = currentEnc.getFoodstore();
									System.out.println("New Enclosure added to zoo, with " + wasteVal + " waste.");
								}
								catch (NumberFormatException nfe) {
									System.err.println("The second part of an enclosure declaration in the configuration file was not a valid integer.");
								}
							}
						}
						else {
							System.err.println("No zoo to add enclosure to exists");
						}
						break;
					
					// If the first word is a valid food type, try too add it to the zoo if it exists
					case "hay": case"steak": case"fruit": case"celery": case"fish": case"ice cream":	
						if(isZoo) {
							
							if(lineLength < 2) {
								System.err.println("No amount of food specified");
							}
							
							else {
								try {
									int foodVal;
									foodVal = Integer.parseInt(lineParts[1]);
									currentFS.addFood(cmd, foodVal);
									System.out.println(foodVal + " " + cmd + " added to foodstore.");
								}
								catch (NumberFormatException nfe) {
									System.err.println("The second part of a food declaration in the configuration file was not a valid integer.");
								}
							}
						}
						else {
							System.err.println("No zoo exists to add food to a foodstore");
						}
						break;
					
					//finally check for animals
					case "chimpanzee": case "gorilla": case "giraffe": case "elephant": case "lion":
					case "tiger": case "bear": case "penguin":
						
						if(lineLength == 2) {
							if(isZoo) {
								checkAnimalCmds(cmd, lineParts[1], currentEnc, currentZoo);
							}
							else {
								System.err.println("You need to define a zoo in the configuration file in order to add things to it");
							}
						}
						else {
							System.err.println("Wrong number of parts in configuration file line. (should be in \"xxx:yyy\" format, or \"xxx:\" format");
						}
					break;
					
					//Otherwise, tell the user that something in the configuration file was not a valid input/
					default:
						System.err.println("The config file contains an invalid declaration (not an object for the zoo)");	
						break;
					
					} //end switch
					
				} //end if (if it is a blank line the code block will not run)
				
			} //end while
			
		} //end try
		catch (IOException e) {
			System.err.println("There was a problem trying to read form the configuration file.");
		} 
		
	}

	//Check to see if the command is valid for animals
	private static void checkAnimalCmds(String animal, String parameters, Enclosure currentEnc, Zoo currentZoo) {
		
		//These will be passed to an animal constructor
		char gender;
		int age;
		int health;
		
		//The parameters specified in the config file for animals are separated by a comma
		String[] paramParts = parameters.split(",");
		int paramLength = paramParts.length;
		
		// We don't want animals running loose!
		if(currentEnc == null) {
			System.err.println("You need at least one enclosure in a zoo to add animals to.");
		}
		
		else {
			switch(paramLength) {
			
				//If no enclosure is specified, add to the last enclosure that was used in the config
				case 3:
					
					gender = paramParts[0].toLowerCase().charAt(0);
					if(!(gender == 'f' || gender == 'm')) {
						System.err.println("Gender of animal in config file was not \"M/F\" (male/female), defaulting to female");
						gender = 'f';
					}
					
					try {
						age = Integer.parseInt(paramParts[1]);
					}
					catch(NumberFormatException nfe) {
						System.err.println("The age of an animal in config file was not a valid integer, defaulting to 0");
						age = 0;
					}
					
					try {
						health = Integer.parseInt(paramParts[2]);
						if(health < 1) {
							health = 1;
							System.err.println("The health specified for an animal in the config file was not greater than 0, setting to 1");
						}
						if(health > 10) {
							health = 10;
							System.err.println("The health specified for an animal in the config file was not less than or equal to 10, setting to 10");
						}
					}
					catch(NumberFormatException nfe) {
						System.err.println("The health of an animal in config file was not a valid integer, defaulting to 10");
						health = 10;
					}
					
					generateAnimal(animal, gender, age, health, currentEnc);
					System.out.println("Successfully added animal to zoo");
					break;
					
				//end case 3:
				
				//if an enclosure is specified
				case 4:
					
					Enclosure enc;
					int encNum;
					
					try {
						encNum = Integer.parseInt(paramParts[3]) - 1;
						enc = currentZoo.getEnclosure(encNum);
						if(enc == null) {
							System.err.println("A specified enclosure index in an animal was not valid, using current enclosure instead");
							enc = currentEnc;
						}
						else {
							//So more animals can be created for the same enclosure without specifying it if they succeed this animal in the config file.
							currentEnc = enc;
						}
					}
					catch(NumberFormatException nfe) {
						System.err.println("The enclosure of an animal in config file was not a valid integer, defaulting current enclosure");
						enc = currentEnc;
					}
					
					gender = paramParts[0].toUpperCase().charAt(0);
					if(!(gender == 'F' || gender == 'M')) {
						System.err.println("Gender of animal in config file was not \"M/F\" (male/female), defaulting to female");
						gender = 'f';
					}
					
					try {
						age = Integer.parseInt(paramParts[1]);
					}
					catch(NumberFormatException nfe) {
						System.err.println("The age of an animal in config file was not a valid integer, defaulting to 0");
						age = 0;
					}
					
					try {
						health = Integer.parseInt(paramParts[2]);
					}
					catch(NumberFormatException nfe) {
						System.err.println("The health of an animal in config file was not a valid integer, defaulting to 10");
						health = 10;
					}
					
					generateAnimal(animal, gender, age, health, enc);
					System.out.println("Successfully added animal to zoo");
					break;
					
				//end case 4:
			
				default: 
					System.err.println("Invalid number of parts provided for an animal declaration in the congfig file. (must be 3 or 4 parameters provided)");

			} //end Switch
		
		} //end else
		
	} //end method

	
	// Tries to add an animal with the specified properties to the specified enclosure
	private static void generateAnimal(String animal, char gender, int age, int health, Enclosure enc) {

		switch(animal) {
		
			case "chimpanzee":
				enc.addAnimal(new Chimpanzee(gender, age, health));
				break;
				
			case "gorilla":
				enc.addAnimal(new Gorilla(gender, age, health));
				break;
				
			case "lion":
				enc.addAnimal(new Lion(gender, age, health));
				break;
				
			case "tiger":
				enc.addAnimal(new Tiger(gender, age, health));
				break;
				
			case "giraffe":
				enc.addAnimal(new Giraffe(gender, age, health));
				break;
				
			case "elephant":
				enc.addAnimal(new Elephant(gender, age, health));
				break;
				
			case "bear":
				enc.addAnimal(new Bear(gender, age, health));
				break;
				
			case "penguin":
				enc.addAnimal(new Penguin(gender, age, health));
				break;
			
			//Theoretically this should not ever be called, because of the case statement in configZoo()
			default:
				System.err.println("The config file contains an invalid declaration (not an object for the zoo)");
				break;
		}
		
	}
		


	
}
