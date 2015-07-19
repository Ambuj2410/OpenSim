package org.opensim.examples;

import java.io.IOException;

import org.opensim.OpensimRunnerSingle;

public class tryForStorage {
	public static void main(String[] args) throws IOException {
		boolean enableOutput = true;
		boolean outputToFile = true;
		String inputFolder = "E:\\cloud\\src\\org\\opensim\\examples\\new";
		String outputFolder = "E:\\cloud\\src\\org\\opensim\\examples\\newoutput";
		String workload = "";
		String vmAllocationPolicy = "openstack";
		String vmSelectionPolicy = "";
		String parameter = "";

		new OpensimRunnerSingle(
				enableOutput,
				outputToFile,
				inputFolder,
				outputFolder,
				workload,
				vmAllocationPolicy,
				vmSelectionPolicy,
				parameter);
	}

}
