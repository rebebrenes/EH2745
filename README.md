# EH2745

Assignment II - EH2745 Computer Applications in Power Systems,
Rebeca Brenes & Nick van Dunné

<>>>>>> FILES SUMMARY >>>>>> 

*** Main File: clusteringAlgorithms.java

*** Auxiliary Files
- CSV.java: This file contains methods that are used to print the information of clusters, centroids and samples into a CSV file.
- DistanceComparator.java: This contains a method that is used to sort distances from smallest distance to largest.
- SortData: Contains a method that calls the SQLdata class and obtains the information, as well as gives it a good format for later use.
- SQLdata: Contains the communication with MySQL and extracts the data from the desired tables.

*** Format Files
- distclData.java: Mainly used as a constructor in order to use List<distclData>, with a format of (double distance, String cluster).
- Measurements.java: Mainly used as a constructor in order to use List<Measurements> with the format(String rdfid, String name, double time, double value, String sub_rdfid).
- SampleData.java: Mainly used as a constructor in order to use List<SampleData> with the format(voltage1, angle1, ..., voltageN, angleN).
- StateData.java: Similar to SampleData, but includes the cluster number and state in the structure.

<>>>>>> HOW TO RUN? >>>>>> 
1. Open SQLdata and change MySQL credentials.
2. Open clusteringAlgorithms.java and run it.
3. Look console for general information.
4. Open CSV in Project location to obtain more detailed data.
5. Three CSV files are produced
	- centroids_data.csv: centroid data, sorted by columns (voltage1, angle1,...voltageN, angleN).
	- kmeans result.csv: training set data with corresponding cluster and state.
	- knn result.csv: test set data with corresponding cluster and state.

<>>>>>> CODE STRUCTURE >>>>>> 
- Inputs
- Sort Data from SQL
- Definitions
- Main:
	1. Initialize kmeans algorithm
	2. Calculate centroids from intialization
	3. Perform k-Means algorithm on training set
	4. Perform k-NN algorithm on test set
	5. Save clustered data in external CSV
