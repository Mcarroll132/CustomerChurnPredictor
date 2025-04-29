Customer Churn Predictor Project Description This project is a Java-based GUI application that predicts customer churn using a classifier. The application reads a dataset, trains itself, allows new data entry, and tests accuracy.

Subscription Type | Payment Method | Auto Renewal| Customer Segment | Churn Label | Count Monthly | Credit Card | Yes | Small Business | Yes | 8 Monthly | Credit Card | No | Enterprise | No | 5 Annual | PayPal | Yes | Enterprise | No | 7 Monthly | PayPal | No | Small Business | Yes | 12 Annual | Credit Card | Yes | Small Business | No | 6 Pay-as-you-go | PayPal | No | Small Business | Yes | 9 Monthly | Credit Card | Yes | Enterprise | No | 10 Annual | PayPal | No | Enterprise | No | 11 Monthly | PayPal | Yes | Small Business | Yes | 5 Pay-as-you-go | Credit Card | No | Enterprise | No | 4

Features
 Level 1: Hardcoded prediction based on offline-calculated probabilities 
Level 2: Dynamic model training from dataset file 

Level 3: Add new training examples and retrain dynamically 

Level 4: Train/test split evaluation and model accuracy calculation 

Class Description DataSetManager: Loads the dataset, calculates frequencies, splits training/testing data, adds new examples NaiveBayesClassifier: Implements the Naive Bayes training and prediction logic MainGui: Allows user to train, predict churn, add new training examples, and test the accuracy of the apllication CsvLoader: loads the CSV file into memory Frequency Table The dataset consists of 200 examples, each with 4 features:

Subscription Type (Monthly, Annual, Pay-as-you-go)

Payment Method (Credit Card, PayPal)

Auto Renewal (Yes, No)

Customer Segment (Small Business, Enterprise)

The label column (CustomerWillChurn) has two possible values: "Yes" or "No".

How To Run the Application Launch the application via App.java in Eclipse.

Click "Train Model" to load and train from CustomerChurnDataFinal.csv.

Select customer features and click "Predict Churn" to predict churn.

Add new examples using the "Add New Example" section and retrain.

Test the model's accuracy by clicking "Test Accuracy".

Accuracy Results Achieved Accuracy: ~45% on test data after 75/25 train/test split.

If I had more time I would:

Look to use a much bigger data set as it is hard to get accurate results off of only 200 rows I have found. A much more user friendly GUI as mine was used to just allow for the functianilty which was of more concern. I would also allow users to make up their own split instead of the 75/25
Customer Churn Predictor Project Description This project is a Java-based GUI application that predicts customer churn using a classifier. The application reads a dataset, trains itself, allows new data entry, and tests accuracy.

Subscription Type | Payment Method | Auto Renewal| Customer Segment | Churn Label | Count Monthly | Credit Card | Yes | Small Business | Yes | 8 Monthly | Credit Card | No | Enterprise | No | 5 Annual | PayPal | Yes | Enterprise | No | 7 Monthly | PayPal | No | Small Business | Yes | 12 Annual | Credit Card | Yes | Small Business | No | 6 Pay-as-you-go | PayPal | No | Small Business | Yes | 9 Monthly | Credit Card | Yes | Enterprise | No | 10 Annual | PayPal | No | Enterprise | No | 11 Monthly | PayPal | Yes | Small Business | Yes | 5 Pay-as-you-go | Credit Card | No | Enterprise | No | 4

FeaturesLevel 1: Hardcoded prediction based on offline-calculated probabilities Level 2: Dynamic model training from dataset file 

Level 3: Add new training examples and retrain dynamically 

Level 4: Train/test split evaluation and model accuracy calculation 

Class Description DataSetManager: Loads the dataset, calculates frequencies, splits training/testing data, adds new examples Classifier: Implements the training and prediction logic MainGui: Allows user to train, predict churn, add new training examples, and test the accuracy of the apllication CsvLoader: loads the CSV file into memory Frequency Table The dataset consists of 200 examples, each with 4 features:

Subscription Type (Monthly, Annual, Pay-as-you-go)

Payment Method (Credit Card, PayPal)

Auto Renewal (Yes, No)

Customer Segment (Small Business, Enterprise)

The label column (CustomerWillChurn) has two possible values: "Yes" or "No".

How To Run the Application Launch the application via App.java in Eclipse.

Click "Train Model" to load and train from CustomerChurnDataFinal.csv.

Select customer features and click "Predict Churn" to predict churn.

Add new examples using the "Add New Example" section and retrain.

Test the model's accuracy by clicking "Test Accuracy".

Accuracy Results Achieved Accuracy: ~45% on test data after 75/25 train/test split.

If I had more time I would:

Look to use a much bigger data set as it is hard to get accurate results off of only 200 rows I have found. A much more user friendly GUI as mine was used to just allow for the functianilty which was of more concern. I would also allow users to make up their own split instead of the 75/25

Link to Github: https://github.com/Mcarroll132/CustomerChurnPredictor
Link to youtube video: https://youtu.be/ue56saCeC8E