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
					linearLatencyWriter = new BufferedWriter(new FileWriter("linearLatency.txt", true));
					binaryLatencyWriter = new BufferedWriter(new FileWriter("binaryLatency.txt", true));
					logLatencyWriter = new BufferedWriter(new FileWriter("logLatency.txt", true));
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
		 
		 //log base 2 function found here https://www.techiedelight.com/calculate-log-base-2-in-java/
		 public static int log(int x)
		 {
		     return (int) (Math.log(x) / Math.log(2) + 1e-10);
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
			int[] countOfSlotsHit = new int[1048];
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
				 if(N > 0) {
					 latency += windowSize;
				 } else {
					 latency += lastSlotUsed;
				 }
				 //System.out.println("\nN = " + N + "\nlatency = " + latency + "\n");
				 windowSize += 1; //only line that should change between each sim
			 }
			 
			 return latency;
		 }
		
		//--- binarySimulation determine a simulated latency given N number of devices. Returns latency calculation
		public int binarySimulation(int N) {
			Random rand = new Random();
			int windowSize = 2;
			int[] countOfSlotsHit = new int[65536];
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
				 if(N > 0) {
					 latency += windowSize;
				 } else {
					 latency += lastSlotUsed;
				 }
				 windowSize = windowSize*2; //only line that should change between each sim
			 }
			 
			 return latency;
		 }
				
		//--- logSimulation determine a simulated latency given N number of devices. Returns latency calculation
		public int logSimulation(int N) {
			Random rand = new Random();
			int windowSize = 2;
			int[] countOfSlotsHit = new int[1048];
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
				 if(N > 0) {
					 latency += windowSize;
				 } else {
					 latency += lastSlotUsed;
				 }
				 windowSize = (1 + (1/log(windowSize))*2); //only line that should change between each sim
			 }
			 
			 return latency;
		 }
		
		//--- main - calculates and writes averages to necessary files. No return. 
		public static void main(String[] args) {
			int numberOfDevices = 100;
			double average = 0.0;
			myBackoff backoff = new myBackoff();
			
			while(numberOfDevices <= 6000) {
				//Linear backoff first
				for(int i = 0; i < 10; i++) {
					average += backoff.linearSimulation(numberOfDevices);
				}
				average = average/10; //take the average latency by dividing by number of simulations
				backoff.writeToLinearLatency(average);
				average = 0.0; //reset average
				
				//then do binary backoff
				for(int i = 0; i < 10; i++) {
					average += backoff.binarySimulation(numberOfDevices);
				}
				average = average/10; //take the average latency by dividing by number of simulations
				backoff.writeToBinaryLatency(average);
				average = 0.0; //reset average
				
				//finally do logarithmic backoff
				for(int i = 0; i < 10; i++) {
					average += backoff.binarySimulation(numberOfDevices);
				}
				average = average/10; //take the average latency by dividing by number of simulations
				backoff.writeToLogLatency(average);
				average = 0.0; //reset average
				
				//increase number of devices and start over
				numberOfDevices += 100; //increase number of devices by 100
			}
		}
}
