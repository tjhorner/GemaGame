package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LevelLoader {
	public static void loadTxt(String fileName) throws IOException {
		ArrayList<String> lineList = new ArrayList<String>();
		ArrayList<Double> platformsToAddXPos = new ArrayList<Double>();
		ArrayList<Double> platformsToAddYPos = new ArrayList<Double>();
		ArrayList<Double> platformsToAddXLength = new ArrayList<Double>();
		ArrayList<Double> platformsToAddYLength = new ArrayList<Double>();
		int numberOfPlatformsToAdd = 0;
		// These four ArrayLists store the platform properties that are to be
		// added at end of function.

		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		while (true) {
			String currentLine = reader.readLine();
			// no more lines to read
			if (currentLine == null) {
				reader.close();
				break;
			}

			if (!currentLine.startsWith("#")) {
				lineList.add(currentLine);
			}
		}

		// Variables to be used in the following for loop.
		char currentChar;
		int nextNumberEquals;
		// This variable will keep track of what to do with the next number in
		// the sequence in the line. it basically keeps track of what the next
		// number represents, for instance whether it is the x position or y
		// position of the platform.
		// 0 is x position
		// 1 is y position
		// 2 is x length
		// 3 is y length
		// 4 is quit loop

		// Define variable constants to use, for name simplicity.

		// For loops that go through every character in the line.
		for (int lineNumber = 0; lineNumber < lineList.size(); lineNumber++) {

			double[] currentAdditions = new double[4];
			for (int i = 0; i < 4; i++) {
				currentAdditions[i] = 0;
			}
			// This is a double[] storing all the current platform's properties.
			// 0 is x position
			// 1 is y position
			// 2 is x length
			// 3 is y length

			int lineLength = lineList.get(lineNumber).length();
			// Gets the current line's length for later use

			nextNumberEquals = 0;// reset the nextNumberEquals variable.
			// Loop to go through all characters in this line.

			for (int charNumber = 0; charNumber < lineLength
					&& nextNumberEquals < 4;) {
				currentChar = lineList.get(lineNumber).charAt(charNumber);
				// This is the current Character.

				if (currentChar == ';') {
					boolean isDone = false;
					// Boolean used to tell if the mini number finder loop is
					// done.
					charNumber++;
					// Adds one to the current character number, so that the
					// mini number finder loop will use the digit after ;
					String currentSequence = "";
					// A string used to holding the current sequence of numbers
					// that the mini loop uses.
					//
					// This loop ;launches when a ; is found
					// It will combined the digits after the ; into one number,
					// then put that number into the currentAdditions Variable
					// After adding to currentAdditions, it changes the
					// nextNumberEquals by one so that next time it is launched,
					// it puts the number into the next field (xPos, yPos,
					// xLength, and yLength are all stored in currentAdditions)
					for (; !isDone; charNumber++) {
						currentChar = lineList.get(lineNumber).charAt(
								charNumber);
						if (java.lang.Character.isDigit(currentChar)) {
							currentSequence = (currentSequence + currentChar);
						} else {
							isDone = true;
							currentAdditions[nextNumberEquals] = Integer
									.valueOf(currentSequence);
							nextNumberEquals++;
						}
					}// End of Mini Number Getter Loop
				} else {
					// If the value of the character isn't ;, then go to the
					// next character
					System.out.println("Invalid Formating in: "+fileName);
					System.out.println("Invalid Line: "+ lineList.get(lineNumber));
					System.out.println("Line Number: "+lineNumber);
				}
			}// End of current line Loop

			// This checks if the current addition platform has a length and
			// height, and all 4 values have been gone through and added.
			if (currentAdditions[2] > 0 && currentAdditions[3] > 0
					&& nextNumberEquals == 4) {
				// If it does have a width and height, then add it to the
				// Platforms To Add ArrayLists
				platformsToAddXPos.add(currentAdditions[0]);
				platformsToAddYPos.add(currentAdditions[1]);
				platformsToAddXLength.add(currentAdditions[2]);
				platformsToAddYLength.add(currentAdditions[3]);
				numberOfPlatformsToAdd++;
			} else {
				System.out.println("Invalid Formating in: "+fileName);
				System.out.println("Invalid Line: "+ lineList.get(lineNumber));
				System.out.println("Line Number: "+lineNumber);
			}

		}// End of main loop

		for (int i = 0; i < numberOfPlatformsToAdd; i++) {
			// Loops through all the platform properties that have been gotten
			// from the file, and adds each of them to the platform Handler
			// stored in Main Class
			MainClass.getPlatformHandler().addPlatForm(
					platformsToAddXPos.get(i), platformsToAddYPos.get(i),
					platformsToAddXLength.get(i), platformsToAddYLength.get(i));
		}
		System.out.println("Loaded " + numberOfPlatformsToAdd
				+ " platforms from file " + fileName + ".");
	}// End of Function
}// End Of Class
