/*
 * Andrew Mehta (am3258)
 * Jake Manning (jsm652)
 * Data Communication and Networking
 * Programming Assignment #3 : Backoff simulator
 * 10/28/2019
 *
 * Backoff File
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
public class myBackoff {
	
	//------------------- Private Variable Definitions -------------------//

		private BufferedWriter linearLatencyWriter = null;
		private BufferedWriter binaryLatencyWriter = null;
		private BufferedWriter logLatencyWriter = null;
		
		//--- openWriters - Opens the files that are written to log the program. No return.
		 private void openWriters() {
			 	File linearLatencyFile = new File("linearLatency.txt");
			 	File binaryLatencyFile = new File("binaryLatency.txt");
			 	File logLatencyFile = new File("logLatency.txt");
				try {
					linearLatencyWriter = new BufferedWriter(new FileWriter("linearLatency.txt", false));
					binaryLatencyWriter = new BufferedWriter(new FileWriter("binaryLatency.txt", false));
					logLatencyWriter = new BufferedWriter(new FileWriter("logLatency.tx", false));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		 
		//--- writeToLinearLatency - Writes the average received to the appropriate log file. No return. 
		 private void writeToLinearLatency(double average) {
				try {
					linearLatencyWriter.write(average + "\n");
					linearLatencyWriter.flush();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		 
		//--- writeToBinaryLatency - Writes the average received to the appropriate log file. No return. 
		 private void writeToBinaryLatency(double average) {
				try {
					binaryLatencyWriter.write(average + "\n");
					binaryLatencyWriter.flush();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		 
		//--- writeToLogLatency - Writes the average received to the appropriate log file. No return. 
		 private void writeToLogLatency(double average) {
				try {
					logLatencyWriter.write(average + "\n");
					logLatencyWriter.flush();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		 
		 
		//------------------- End Private Function Definitions -------------------//
		 
		
		 
		//------------------- Begin Public Function Definitions -------------------// 
		//Class Constructor - has no inputs
		public myBackoff() {
			openWriters();
		}
		
		//--- linearSimulation determine a simulated latency given N number of devices. Returns latency calculation
		public int linearSimulation(int N) {
			Random rand = new Random();
			int windowSize = 2;
			int[] countOfSlotsHit = new int[256];
			int latency = 0;
			int lastSlotUsed = 0;
			 
			while(N > 0) {
				 for(int i = 0; i < N; i++) {
				 int randomSlot = rand.nextInt(windowSize);
				 countOfSlotsHit[randomSlot] += 1;
				 }
				 for(int i = 0; i < windowSize; i++) {
					 //System.out.println("countOfSlotsHit[" + i + "] = " + countOfSlotsHit[i]);
					 if(countOfSlotsHit[i] == 1) {
						 N -= 1;
						 lastSlotUsed = i + 1; //slots are counted from 1, so add 1 here
					 }
					 countOfSlotsHit[i] = 0;
				 }
				 latency += windowSize;
				 //System.out.println("\nN = " + N + "\nlatency = " + latency + "\n");
				 windowSize += 1; //only line that should change between each sim
			 }
			 
			 latency = latency - (windowSize - 1) + lastSlotUsed; //make sure to add only the slots used, not all available slots in the final window
			 return latency;
		 }
		public static void main(String[] args) {
			int numberOfDevices = 100;
			double average = 0.0;
			myBackoff backoff = new myBackoff();
			
			for(int i = 0; i < 10; i++) {
				average += backoff.linearSimulation(numberOfDevices);
			}
			backoff.writeToLinearLatency(average/10);
			
		}
}
