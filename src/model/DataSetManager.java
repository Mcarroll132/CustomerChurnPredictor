/**
 * DataSetManager is responsible for loading the dataset,
 * calculating feature and label frequencies, adding new examples,
 * and splitting the data for training and testing.
 */



package model;

import utility.CsvLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataSetManager {
    private List<String[]> data; // List of rows from the dataset
    private Map<String, Integer> labelCounts; // Count of Yes/No labels
    private Map<String, Map<String, Integer>> featureCounts; // Count of each feature value per label

    /**
     * Constructor that loads the dataset from a CSV file.
     * @param filePath Path to the CSV dataset.
     */
    public DataSetManager(String filePath) {
        data = CsvLoader.loadCSV(filePath);
    }

    /**
     * Calculates frequency counts for labels and feature-label combinations
     * based on the current dataset.
     */
    public void calculateFrequencies() {
        labelCounts = new HashMap<>();
        featureCounts = new HashMap<>();

        // Loop through dataset
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);

            String subscriptionType = row[0].trim();
            String paymentMethod = row[1].trim();
            String autoRenewal = row[2].trim();
            String customerSegment = row[3].trim();
            String churnLabel = row[4].trim(); // "Yes" or "No"

            // Count the label value
            labelCounts.put(churnLabel, labelCounts.getOrDefault(churnLabel, 0) + 1);

            // Count each feature value with its label
            updateFeatureCount(subscriptionType, churnLabel);
            updateFeatureCount(paymentMethod, churnLabel);
            updateFeatureCount(autoRenewal, churnLabel);
            updateFeatureCount(customerSegment, churnLabel);
        }
    }

    /**
     * Updates the count for a specific feature value and its associated label.
     *  featureValue The value of the feature (e.g., "Monthly")
     *  label The corresponding label ("Yes" or "No")
     */
    private void updateFeatureCount(String featureValue, String label) {
        featureCounts.putIfAbsent(featureValue + "_" + label, new HashMap<>());
        Map<String, Integer> featureLabelMap = featureCounts.get(featureValue + "_" + label);
        featureLabelMap.put(label, featureLabelMap.getOrDefault(label, 0) + 1);
    }

    /**
     * Gets the total number of times a specific label appears in the dataset.
     *  label The label to count ("Yes" or "No")
     *  Count of that label
     */
    public int getLabelCount(String label) {
        return labelCounts.getOrDefault(label, 0);
    }

    /**
     * Gets the frequency count of a specific feature value with a given label.
     *  feature The feature value (e.g., "Monthly")
     *  label The label ("Yes" or "No")
     *  Count of that feature-label combination
     */
    public int getFeatureLabelCount(String feature, String label) {
        Map<String, Integer> featureLabelMap = featureCounts.getOrDefault(feature + "_" + label, null);
        if (featureLabelMap != null) {
            return featureLabelMap.getOrDefault(label, 0);
        }
        return 0;
    }

    /**
     * Returns the total number of rows in the dataset, excluding the header.
     * returns Number of data rows
     */
    public int getTotalRows() {
        return data.size() - 1; // Subtract header row
    }

    /**
     * Adds a new example (row) to the dataset and recalculates frequencies.
     * Used for Level 3 functionality.
     *  subscriptionType Feature 1
     *  paymentMethod Feature 2
     *  autoRenewal Feature 3
     *  customerSegment Feature 4
     *  churnLabel The label ("Yes" or "No")
     */
    public void addNewExample(String subscriptionType, String paymentMethod, String autoRenewal, String customerSegment, String churnLabel) {
        String[] newRow = new String[] {subscriptionType, paymentMethod, autoRenewal, customerSegment, churnLabel};
        data.add(newRow); // Add new row to memory
        calculateFrequencies(); // Recalculate counts including new row
    }

    //  Level 4: Train/Test Split Methods 

    /**
     * Returns the first 75% of the dataset (excluding header) for training.
     * @return List of training rows
     */
    public List<String[]> getTrainingSet() {
        int splitIndex = (int) (data.size() * 0.75);
        return new ArrayList<>(data.subList(1, splitIndex)); // Skip header
    }

    /**
     * Returns the last 25% of the dataset for testing accuracy.
     * returns List of testing rows
     */
    public List<String[]> getTestingSet() {
        int splitIndex = (int) (data.size() * 0.75);
        return new ArrayList<>(data.subList(splitIndex, data.size()));
    }
}
