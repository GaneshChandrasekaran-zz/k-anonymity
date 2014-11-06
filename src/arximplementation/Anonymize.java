package arximplementation;

import java.io.IOException;

import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXLattice.ARXNode;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.criteria.KAnonymity;

public class Anonymize {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Data data;

		try {
			// Import CSV Data
			data = Data
					.create("C:\\Users\\Ganesh\\Desktop\\FinalImplementation\\SensorDataCSV.txt",
							',');
			// Define data type

			// Define Attribute Type
			// (Sensitive/Insensitive/Quasi-Identifier/Identifying)
			data.getDefinition().setAttributeType("User",
					AttributeType.QUASI_IDENTIFYING_ATTRIBUTE);
			data.getDefinition().setAttributeType("Latitude",
					AttributeType.QUASI_IDENTIFYING_ATTRIBUTE);
			data.getDefinition().setAttributeType("Longitude",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType(" Light",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType("Accuracy",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType("Pitch",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType("Azimuth",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType("xForce",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType("yForce",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType("zForce",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType("Time",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType("xMagnitude",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType("yMagnitude",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType("zMagnitude",
					AttributeType.INSENSITIVE_ATTRIBUTE);
			data.getDefinition().setAttributeType("Roll",
					AttributeType.INSENSITIVE_ATTRIBUTE);

			// Defining Hierarchies
			Hierarchy userHier = Hierarchy
					.create("C:\\Users\\Ganesh\\Desktop\\FinalImplementation\\UserHier.csv",
							';');
			Hierarchy latHier = Hierarchy
					.create("C:\\Users\\Ganesh\\Desktop\\FinalImplementation\\LatHier.csv",
							';');
			Hierarchy longHier = Hierarchy
					.create("C:\\Users\\Ganesh\\Desktop\\FinalImplementation\\LongHier.csv",
							';');

			//
			data.getDefinition().setAttributeType("User", userHier);
			data.getDefinition().setAttributeType("Latitude", latHier);
			// data.getDefinition().setAttributeType("Longitude", longHier);

			ARXConfiguration config = ARXConfiguration.create();
			config.addCriterion(new KAnonymity(2));
			config.setMaxOutliers(0.02d);

			ARXAnonymizer anonymizer = new ARXAnonymizer();
			ARXResult result = anonymizer.anonymize(data, config);

			ARXNode node = result.getGlobalOptimum();
			// Lower bound for the information loss
			System.out.println(node.getMinimumInformationLoss());
			// Upper bound for the information loss
			node.getMaximumInformationLoss();
			// Predecessors
			node.getPredecessors();
			// Successors
			node.getSuccessors();
			// Returns whether the transformation fulfills all privacy criteria
			node.isAnonymous();
			// Generalization defined for the given quasi identifier
			node.getGeneralization("User");

			// Original Data Handle
			DataHandle dataHandle = data.getHandle();
			// Resulting Outputs Data Handle
			DataHandle resultHandle = result.getOutput();
			// Fetch view of the result data handle
			DataHandle view = dataHandle.getView();
			System.out.println(view.getStatistics());
			
			// Saving and writing the output anonymized data to a file on local disk
			resultHandle.save("C:\\Users\\Ganesh\\Desktop\\FinalImplementation\\output.txt", ',');

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
}
