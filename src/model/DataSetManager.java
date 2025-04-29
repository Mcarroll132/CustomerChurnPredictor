package model;

import utility.CsvLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSetManager {
    private List<String[]> data;
    private Map<String, Integer> labelCounts;
    private Map<String, Map<String, Integer>> featureCounts;

    public DataSetManager(String filePath) {
        data = CsvLoader.loadCSV(filePath);
    }

    // Calculate frequencies for current dataset
    public void calculateFrequencies() {
        labelCounts = new HashMap<>();
        featureCounts = new HashMap<>();

        for (int i = 1; i < data.size(); i++) { // Skip header row
            String[] row = data.get(i);

            String subscriptionType = row[0].trim();
            String paymentMethod = row[1].trim();
            String autoRenewal = row[2].trim();
            String customerSegment = row[3].trim();
            String churnLabel = row[4].trim(); // Yes or No

            // Count labels
            labelCounts.put(churnLabel, labelCounts.getOrDefault(churnLabel, 0) + 1);

            // Count features per label
            updateFeatureCount(subscriptionType, churnLabel);
            updateFeatureCount(paymentMethod, churnLabel);
            updateFeatureCount(autoRenewal, churnLabel);
            updateFeatureCount(customerSegment, churnLabel);
        }
    }

    private void updateFeatureCount(String featureValue, String label) {
        featureCounts.putIfAbsent(featureValue + "_" + label, new HashMap<>());
        Map<String, Integer> featureLabelMap = featureCounts.get(featureValue + "_" + label);
        featureLabelMap.put(label, featureLabelMap.getOrDefault(label, 0) + 1);
    }

    public int getLabelCount(String label) {
        return labelCounts.getOrDefault(label, 0);
    }

    public int getFeatureLabelCount(String feature, String label) {
        Map<String, Integer> featureLabelMap = featureCounts.getOrDefault(feature + "_" + label, null);
        if (featureLabelMap != null) {
            return featureLabelMap.getOrDefault(label, 0);
        }
        return 0;
    }

    public int getTotalRows() {
        return data.size() - 1; // Minus header row
    }

    // Add a new training example (Level 3)
    public void addNewExample(String subscriptionType, String paymentMethod, String autoRenewal, String customerSegment, String churnLabel) {
        String[] newRow = new String[] {subscriptionType, paymentMethod, autoRenewal, customerSegment, churnLabel};
        data.add(newRow); // Add new row
        calculateFrequencies(); // Recalculate frequencies after adding
    }

    // --- New methods for Level 4 (Splitting data) ---

    // Returns 75% of the data for training
    public List<String[]> getTrainingSet() {
        int splitIndex = (int) (data.size() * 0.75);
        return new ArrayList<>(data.subList(1, splitIndex)); // Skip header
    }

    // Returns 25% of the data for testing
    public List<String[]> getTestingSet() {
        int splitIndex = (int) (data.size() * 0.75);
        return new ArrayList<>(data.subList(splitIndex, data.size()));
    }
}
