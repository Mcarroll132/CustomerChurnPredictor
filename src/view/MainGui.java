/**
 * MainGui class builds the graphical user interface (GUI) for the Customer Churn Predictor application.
 * It allows users to train the model, predict churn, add new examples, and test model accuracy.
 
 *Lines 25 to 34 are AI generated and lines 56 - 114 also AI generated
 *
 *
 */



package view;

import model.Classifier;
import model.DataSetManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class MainGui {
    private JFrame frame;
    private JComboBox<String> subscriptionBox;
    private JComboBox<String> paymentBox;
    private JComboBox<String> autoRenewalBox;
    private JComboBox<String> segmentBox;
    private JButton predictButton;
    private JButton trainButton;
    private JButton addExampleButton;
    private JButton testAccuracyButton;
    private JLabel resultLabel;

    private JComboBox<String> newSubscriptionBox;
    private JComboBox<String> newPaymentBox;
    private JComboBox<String> newAutoRenewalBox;
    private JComboBox<String> newSegmentBox;
    private JComboBox<String> newLabelBox;

    private Classifier classifier;
    private DataSetManager dataManager;

    /**
     * Constructor that initializes the GUI and classifier object.
     */
    public MainGui() {
        classifier = new Classifier();
        initialize();
    }

    /**
     * Initializes the GUI layout, components, and button actions.
     */
    private void initialize() {
        frame = new JFrame("Customer Churn Predictor");
        frame.setSize(700, 900); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(14, 2));

        // --- Main Prediction Input Fields ---
        frame.add(new JLabel("Subscription Type:"));
        subscriptionBox = new JComboBox<>(new String[]{"Monthly", "Annual", "Pay-as-you-go"});
        frame.add(subscriptionBox);

        frame.add(new JLabel("Payment Method:"));
        paymentBox = new JComboBox<>(new String[]{"Credit Card", "PayPal"});
        frame.add(paymentBox);

        frame.add(new JLabel("Auto Renewal:"));
        autoRenewalBox = new JComboBox<>(new String[]{"Yes", "No"});
        frame.add(autoRenewalBox);

        frame.add(new JLabel("Customer Segment:"));
        segmentBox = new JComboBox<>(new String[]{"Small Business", "Enterprise"});
        frame.add(segmentBox);

        //  Buttons for Main Actions
        trainButton = new JButton("Train Model");
        frame.add(trainButton);

        predictButton = new JButton("Predict Churn");
        frame.add(predictButton);

        resultLabel = new JLabel("Prediction: ");
        frame.add(resultLabel);

        // --- New Example Input Fields (for Level 3) ---
        frame.add(new JLabel("New Subscription Type:"));
        newSubscriptionBox = new JComboBox<>(new String[]{"Monthly", "Annual", "Pay-as-you-go"});
        frame.add(newSubscriptionBox);

        frame.add(new JLabel("New Payment Method:"));
        newPaymentBox = new JComboBox<>(new String[]{"Credit Card", "PayPal"});
        frame.add(newPaymentBox);

        frame.add(new JLabel("New Auto Renewal:"));
        newAutoRenewalBox = new JComboBox<>(new String[]{"Yes", "No"});
        frame.add(newAutoRenewalBox);

        frame.add(new JLabel("New Customer Segment:"));
        newSegmentBox = new JComboBox<>(new String[]{"Small Business", "Enterprise"});
        frame.add(newSegmentBox);

        frame.add(new JLabel("New Known Churn (Yes/No):"));
        newLabelBox = new JComboBox<>(new String[]{"Yes", "No"});
        frame.add(newLabelBox);

        addExampleButton = new JButton("Add New Example");
        frame.add(addExampleButton);

        testAccuracyButton = new JButton("Test Accuracy");
        frame.add(testAccuracyButton);

      
        // Train model from CSV dataset
        trainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataManager = new DataSetManager("resources/CustomerChurnDataFinal.csv");
                dataManager.calculateFrequencies();
                classifier.train(dataManager);
                JOptionPane.showMessageDialog(frame, "Training completed!");
            }
        });

        // Predict churn based on selected input values
        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                predict();
            }
        });

        // Add a new training example
        addExampleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewExample();
            }
        });

        // Test model accuracy using train/test split
        testAccuracyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testAccuracy();
            }
        });

        frame.setVisible(true);
    }

    /**
     * Predicts churn for the selected input features.
     */
    private void predict() {
        if (classifier == null) {
            JOptionPane.showMessageDialog(frame, "Please train the model first!");
            return;
        }

        String subscription = (String) subscriptionBox.getSelectedItem();
        String payment = (String) paymentBox.getSelectedItem();
        String autoRenewal = (String) autoRenewalBox.getSelectedItem();
        String segment = (String) segmentBox.getSelectedItem();

        String prediction = classifier.predict(subscription, payment, autoRenewal, segment);
        resultLabel.setText("Prediction: " + prediction);
    }

    /**
     * Adds a new example to the training data and retrains the model.
     */
    private void addNewExample() {
        if (dataManager == null) {
            JOptionPane.showMessageDialog(frame, "Please train the model first!");
            return;
        }

        String subscription = (String) newSubscriptionBox.getSelectedItem();
        String payment = (String) newPaymentBox.getSelectedItem();
        String autoRenewal = (String) newAutoRenewalBox.getSelectedItem();
        String segment = (String) newSegmentBox.getSelectedItem();
        String label = (String) newLabelBox.getSelectedItem();

        dataManager.addNewExample(subscription, payment, autoRenewal, segment, label);
        classifier.train(dataManager);

        JOptionPane.showMessageDialog(frame, "New training example added and model retrained!");
    }

    /**
     * Tests model accuracy by training on 75% of data and testing on 25%.
     */
    private void testAccuracy() {
        if (dataManager == null) {
            JOptionPane.showMessageDialog(frame, "Please train the model first!");
            return;
        }

        List<String[]> trainingData = dataManager.getTrainingSet();
        List<String[]> testingData = dataManager.getTestingSet();

        classifier.train(trainingData); // Train only on training data

        int correct = 0;
        int total = testingData.size();

        // Loop through testing data and compare predictions
        for (String[] row : testingData) {
            String subscription = row[0].trim();
            String payment = row[1].trim();
            String autoRenewal = row[2].trim();
            String segment = row[3].trim();
            String actualLabel = row[4].trim();

            String predictedLabel = classifier.predict(subscription, payment, autoRenewal, segment);

            if (predictedLabel.equalsIgnoreCase(actualLabel)) {
                correct++;
            }
        }

        // Calculate and display model accuracy
        double accuracy = (double) correct / total * 100;
        JOptionPane.showMessageDialog(frame, "Model Accuracy: " + String.format("%.2f", accuracy) + "%");
    }
}
