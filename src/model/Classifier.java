package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Classifier {
    private Map<String, Double> yesProbabilities;
    private Map<String, Double> noProbabilities;
    private double overallYesProbability;
    private double overallNoProbability;

    public Classifier() {
        yesProbabilities = new HashMap<>();
        noProbabilities = new HashMap<>();
    }

    // Train from a complete dataset (Level 2 and 3)
    public void train(DataSetManager dataManager) {
        yesProbabilities.clear();
        noProbabilities.clear();

        int totalYes = dataManager.getLabelCount("Yes");
        int totalNo = dataManager.getLabelCount("No");
        int totalRows = dataManager.getTotalRows();

        overallYesProbability = (double) totalYes / totalRows;
        overallNoProbability = (double) totalNo / totalRows;

        String[] features = {
            "Monthly", "Annual", "Pay-as-you-go",
            "Credit Card", "PayPal",
            "Yes", "No", // AutoRenewal values
            "Small Business", "Enterprise"
        };

        for (String feature : features) {
            double yesProb = (double) dataManager.getFeatureLabelCount(feature, "Yes") / totalYes;
            double noProb = (double) dataManager.getFeatureLabelCount(feature, "No") / totalNo;

            yesProbabilities.put(feature, yesProb);
            noProbabilities.put(feature, noProb);
        }
    }

    // Train from a training subset (Level 4 split training)
    public void train(List<String[]> trainingData) {
        yesProbabilities.clear();
        noProbabilities.clear();

        int totalYes = 0;
        int totalNo = 0;

        for (String[] row : trainingData) {
            String label = row[4].trim();
            if (label.equalsIgnoreCase("Yes")) {
                totalYes++;
            } else if (label.equalsIgnoreCase("No")) {
                totalNo++;
            }
        }

        int totalRows = trainingData.size();
        overallYesProbability = (double) totalYes / totalRows;
        overallNoProbability = (double) totalNo / totalRows;

        String[] features = {
            "Monthly", "Annual", "Pay-as-you-go",
            "Credit Card", "PayPal",
            "Yes", "No", // AutoRenewal values
            "Small Business", "Enterprise"
        };

        for (String feature : features) {
            int yesFeatureCount = 0;
            int noFeatureCount = 0;

            for (String[] row : trainingData) {
                String subscriptionType = row[0].trim();
                String paymentMethod = row[1].trim();
                String autoRenewal = row[2].trim();
                String customerSegment = row[3].trim();
                String label = row[4].trim();

                if (subscriptionType.equals(feature) || paymentMethod.equals(feature) || autoRenewal.equals(feature) || customerSegment.equals(feature)) {
                    if (label.equalsIgnoreCase("Yes")) {
                        yesFeatureCount++;
                    } else if (label.equalsIgnoreCase("No")) {
                        noFeatureCount++;
                    }
                }
            }

            double yesProb = (totalYes == 0) ? 0.0 : (double) yesFeatureCount / totalYes;
            double noProb = (totalNo == 0) ? 0.0 : (double) noFeatureCount / totalNo;

            yesProbabilities.put(feature, yesProb);
            noProbabilities.put(feature, noProb);
        }
    }

    // Predict outcome given a new input
    public String predict(String subscriptionType, String paymentMethod, String autoRenewal, String customerSegment) {
        double yesScore = overallYesProbability;
        double noScore = overallNoProbability;

        yesScore *= yesProbabilities.getOrDefault(subscriptionType, 1.0);
        noScore *= noProbabilities.getOrDefault(subscriptionType, 1.0);

        yesScore *= yesProbabilities.getOrDefault(paymentMethod, 1.0);
        noScore *= noProbabilities.getOrDefault(paymentMethod, 1.0);

        yesScore *= yesProbabilities.getOrDefault(autoRenewal, 1.0);
        noScore *= noProbabilities.getOrDefault(autoRenewal, 1.0);

        yesScore *= yesProbabilities.getOrDefault(customerSegment, 1.0);
        noScore *= noProbabilities.getOrDefault(customerSegment, 1.0);

        return yesScore > noScore ? "Yes" : "No";
    }
}
