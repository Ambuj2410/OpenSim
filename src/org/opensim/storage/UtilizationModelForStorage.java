package org.opensim.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.cloudbus.cloudsim.*;

/**
 * The Class UtilizationModelPlanetLab.
 */
public class UtilizationModelForStorage implements UtilizationModel {

	/** The scheduling interval. */
	private double schedulingInterval;

	/** The data (5 min * 288 = 24 hours). */
	private final double[] dataRead = new double[301];
	private final double[] dataWrite = new double[301];


	/**
	 * Instantiates a new utilization model PlanetLab.
	 * 
	 * @param inputPath the input path
	 * @throws NumberFormatException the number format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public UtilizationModelForStorage(String inputPathRead ,String inputPathWrite, double schedulingInterval)
			throws NumberFormatException,
			IOException {
		setSchedulingInterval(schedulingInterval);
		BufferedReader inputRead = new BufferedReader(new FileReader(inputPathRead));
		BufferedReader inputWrite = new BufferedReader(new FileReader(inputPathWrite));

		int n = dataRead.length;
		for (int i = 0; i < n - 1; i++) {
			
			dataRead[i] = Integer.valueOf(inputRead.readLine()) / 100.0;
			dataWrite[i] = Integer.valueOf(inputWrite.readLine()) / 100.0;

		}
		dataRead[n - 1] = dataRead[n - 2];
		dataWrite[n - 1] = dataWrite[n - 2];

		inputRead.close();
		inputWrite.close();

	}

	/*
	 * (non-Javadoc)
	 * @see cloudsim.power.UtilizationModel#getUtilization(double)
	 */
	@Override
	public double getUtilization(double time) {
	
		/*
		if (time % getSchedulingInterval() == 0) {
			return data[(int) time / (int) getSchedulingInterval()];
		}
		int time1 = (int) Math.floor(time / getSchedulingInterval());
		int time2 = (int) Math.ceil(time / getSchedulingInterval());
		double utilization1 = data[time1];
		double utilization2 = data[time2];
		double delta = (utilization2 - utilization1) / ((time2 - time1) * getSchedulingInterval());
		double utilization = utilization1 + delta * (time - time1 * getSchedulingInterval());
		utilization = utilization * 3;
		if(utilization < 100 ){
			return utilization;
		}
		else{
			return 100;
		}
		return utilization;
		*/
		double totalRead=0;
		double totalWrite=0;
		
		for(int i=0;i<dataRead.length;i++)
		{
			totalRead=totalRead+dataRead[i];
			totalWrite=totalWrite+dataWrite[i];
		}
		double avgreadps=totalRead/dataRead.length;
		double readps=dataRead[(int) time];
		double writeps=dataWrite[(int) time];
		double oneReadTime=1/readps;
		double oneWriteTime=1/writeps;
		double latency=0; ///Not init
		double avgseektime=0; ///Not init
		double oneIOTime=oneReadTime+oneWriteTime+latency+avgseektime;
		double iops=1/oneIOTime;
		return iops;
		//double iops=readps+writeps+latency+avgseektime;

	}

	/**
	 * Sets the scheduling interval.
	 * 
	 * @param schedulingInterval the new scheduling interval
	 */
	public void setSchedulingInterval(double schedulingInterval) {
		this.schedulingInterval = schedulingInterval;
	}

	/**
	 * Gets the scheduling interval.
	 * 
	 * @return the scheduling interval
	 */
	public double getSchedulingInterval() {
		return schedulingInterval;
	}
}