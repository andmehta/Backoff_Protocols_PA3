# Backoff_Protocols_PA3

## Assignment Objective and Overview
Programming assignment 3 for CSE 4153 Data Communications Networks. This program calculates average latency for three different types of backoff. Linear, Binary, and Logarithmic. These simulations use time as a discretized unit. As in there is no half used time slots. This is to simplify the calculations of use and to demonstrate more succinctly the actual backoff protocols being displayed. The average latency is wrote to a specific textfile (`linearLatency.txt`, `binaryLatency.txt`) containing the average latency from each 10 trial simulations per N number of devices. 

### Backoff explanation
Multiple device tries to send a packet to a receiver, which can result in a __collision__. A __collision__ is when multiple devices are trying to communicate on the physical layer leading to an inability to read the data transmitted. Backoff protocols are designed to give each device an equal opportunity to relay their data within a set period of time. In this program, each device is sending exactly 1 simulated packet that fits exactly within a unit of time. Once that device has successfully sent, it no longer attempts to send any more packets and is complete. 

### Linear Backoff `linearSimulation`
Linear backoff increases window size by 1 each time. 

### Binary Backoff `binarySimulation`
__TODO__ Finish this

### Logarithmic Backoff `logSimulation`
__TODO__ Finish this

## Report of Findings
__TODO__ Finish this


### Chart/graph displaying average latency
__TODO__ Finish this. Take picture of chart/graph and add here
