
/**
 * Classifier class for predicting customer churn
 * based on frequency counts of feature values.
 */



package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Classifier {
    private Map<String, Double> yesProbabilities; // Feature probabilities given "Yes"
    private Map<String, Double> noProbabilities;  // Feature probabilities given "No"
    private double overallYesProbability;         // Overall probability of churn "Yes"
    private double overallNoProbability;          // Overall probability of churn "No"

    /**
     * Constructor initializes the probability maps
     */
    public Classifier() {
        yesProbabilities = new HashMap<>();
        noProbabilities = new HashMap<>();
    }

    /**
     * Train the model using the full dataset for Level 2 and 3
     *  dataManager DataSetManager object containing the loaded dataset
     */
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

        // Calculate feature probabilities for "Yes" and "No"
        for (String feature : features) {
            double yesProb = (double) dataManager.getFeatureLabelCount(feature, "Yes") / totalYes;
            double noProb = (double) dataManager.getFeatureLabelCount(feature, "No") / totalNo;

            yesProbabilities.put(feature, yesProb);
            noProbabilities.put(feature, noProb);
        }
    }

    /**
     * Train the model using a training subset
     * trainingData List of training examples from the dataset
     */
    public void train(List<String[]> trainingData) {
        yesProbabilities.clear();
        noProbabilities.clear();

        int totalYes = 0;
        int totalNo = 0;

        // Count total Yes and No labels
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
            "Yes", "No", 
            "Small Business", "Enterprise"
        };

        // Calculate feature probabilities from training subset
        for (String feature : features) {
            int yesFeatureCount = 0;
            int noFeatureCount = 0;

            for (String[] row : trainingData) {
                String subscriptionType = row[0].trim();
                String paymentMethod = row[1].trim();
                String autoRenewal = row[2].trim();
                String customerSegment = row[3].trim();
                String label = row[4].trim();

                if (subscriptionType.equals(feature) || paymentMethod.equals(feature)
                        || autoRenewal.equals(feature) || customerSegment.equals(feature)) {
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

    /**
     * Predicts whether a customer will churn ("Yes") or stay ("No")
     * based on their selected features.
     *
     *  subscriptionType The customer's subscription type
     *  paymentMethod The customer's payment method
     *  autoRenewal Whether the customer has auto-renewal enabled
     *  customerSegment The customer's business segment
     *  "Yes" if customer will churn, "No" otherwise
     */
    public String predict(String subscriptionType, String paymentMethod, String autoRenewal, String customerSegment) {
        double yesScore = overallYesProbability;
        double noScore = overallNoProbability;

        // Multiply feature probabilities
        yesScore *= yesProbabilities.getOrDefault(subscriptionType, 1.0);
        noScore *= noProbabilities.getOrDefault(subscriptionType, 1.0);

        yesScore *= yesProbabilities.getOrDefault(paymentMethod, 1.0);
        noScore *= noProbabilities.getOrDefault(paymentMethod, 1.0);

        yesScore *= yesProbabilities.getOrDefault(autoRenewal, 1.0);
        noScore *= noProbabilities.getOrDefault(autoRenewal, 1.0);

        yesScore *= yesProbabilities.getOrDefault(customerSegment, 1.0);
        noScore *= noProbabilities.getOrDefault(customerSegment, 1.0);

        // Predict label with higher score
        return yesScore > noScore ? "Yes" : "No";
    }
}
