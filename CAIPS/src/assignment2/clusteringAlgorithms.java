package assignment2;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import assignment2.SortData;

public class clusteringAlgorithms {
	
	// -------> INPUTS <-------
	static double tolerance = 0.00000000000001; 	// Tolerance for the k means algorithm
	
	// -------> Sorts Data <-------
	// Sorts the data from MySQL so that everything is in the require format double[][]
	// The format is [voltage1, angle1, voltage2, angle2... voltage9, angle9] for 200 samples in case of measurements table
	static SortData sData = new SortData();
	
	static List<SampleData> values_set = sData.sortedData("measurements");
	static List<SampleData> test_set = sData.sortedData("analog_values");
	static double[][] values = new double[values_set.size()][18];
	static double[][] test_values = new double[test_set.size()][18];
			
	//-------> Declarations <-------
	static int k_number = 4; 						// Number of operational states
	
	// Some declarations that are needed to run the algorithm
	static double [][] centroid_distances = new double[k_number][18]; // Initial centroid distance change for the tolerance 
	static double [][] centroids = new double[k_number][18]; 	      // Centroids empty matrix
	
	// Distances
	static double dist1;
	static double dist2;
	static double dist3;
	static double dist4;
	
	// Clusters	
	static List<SampleData> cluster1 = new ArrayList<SampleData>();
	static List<SampleData> cluster2 = new ArrayList<SampleData>();
	static List<SampleData> cluster3 = new ArrayList<SampleData>();
	static List<SampleData> cluster4 = new ArrayList<SampleData>();
	
	// Data with cluster
	static List<StateData> data_wcluster = new ArrayList<StateData>();
	static List<StateData> test_wcluster = new ArrayList<StateData>();
	
	public static void main(String[] args) {
		
		// Requirements
		for(int i =0; i<values_set.size(); i++){ 
			values[i]= values_set.get(i).listData();
		}
		
		for(int i =0; i<test_set.size(); i++){ 
			test_values[i]= test_set.get(i).listData();
		}
		CSV writeCSV = new CSV();
		
		// Starts the program, assigns random centroids and then it creates the starting clusters
		initialize();
				
		// Calculates the centroids based on the cluster information
		calculateCentroids();	
				
		kClusters();
		
		System.out.println("\n");
		System.out.println(">>>>>>> RESULTS >>>>>>> \n");
		
		//Clusters Data Checker				
		System.out.println("Training set: " + values.length + " samples");
		System.out.println(kLabel(centroids[0]) + ": " + cluster1.size() + " samples");
		System.out.println(kLabel(centroids[1]) + ": " + cluster2.size() + " samples");
		System.out.println(kLabel(centroids[2]) + ": " + cluster3.size() + " samples");
		System.out.println(kLabel(centroids[3]) + ": " + cluster4.size() + " samples");

		
		writeCSV.CSVwriter("centroids_data", centroids);
		
		//Now we run the kNN algorithm for the test set
		knnAlgorithm();
		writeCSV.CSVwriterCluster("knn result", test_wcluster);
		writeCSV.CSVwriterCluster("kmeans result", data_wcluster);
		
		//Checker		
		int count1 = clusterFrequency(test_wcluster, "1");
		int count2 = clusterFrequency(test_wcluster, "2");
		int count3 = clusterFrequency(test_wcluster, "3");
		int count4 = clusterFrequency(test_wcluster, "4");
				
		System.out.println("\n");
		
		System.out.println("Test set: " + test_wcluster.size() + " samples");
		System.out.println(kLabel(centroids[0]) + ": " + count1 + " samples");
		System.out.println(kLabel(centroids[1]) + ": " + count2 + " samples");
		System.out.println(kLabel(centroids[2]) + ": " + count3 + " samples");
		System.out.println(kLabel(centroids[3]) + ": " + count4 + " samples");
				
	}
		
	//--------------- METHODS for K-means ----------------------

	// Finds the index of the minimum element
	public static int minIndex (double [] arr1){

	       int index = 0;
	       double min = arr1[index];
	       for (int i=1; i<arr1.length; i++){

	           if (arr1[i] < min ){
	               min = arr1[i];
	               index = i;
	           }
	       }
	       return index;
	}

	
	// Checks if an element is contained in an array
	public static boolean contains(int[] array, int element) {
		for (int n : array) {
			if (element == n) {
				return true;
			}
		}
		return false;
	}

	public static int[] randomList(double[][] valuesdata) {
		// Random points generator
	    Random r = new Random();
	    int[] random_list = new int[k_number];
	    random_list[0] = r.nextInt(valuesdata.length);
	
	    for(int i = 1; i < random_list.length; ++i){
	    	random_list[i] = r.nextInt(valuesdata.length);
	
	        for( int j = 0; j < i; ++j ) {
	            if( random_list[j] == random_list[i]){
	            	random_list[i] = r.nextInt(valuesdata.length);
	                j = 0;
	            }
	        }
	    }
	    System.out.println("Random indexes " + random_list[0] + ", " + random_list[1] + ", " + random_list[2] + ", " +random_list[3]);
		return random_list; 	
	}
	
	public static int[] NotSoRandomList(double[][] valuesdata) {
		// Random points generator
	    Random r = new Random();
	    int[] random_list = new int[k_number];
	    Arrays.fill(random_list,1000);
	
	    boolean condition = true;	    
	    while(condition==true){
	    	int temp= r.nextInt(valuesdata.length);    	
	    	
	    	double[] random_values = values[temp];
	    	String temp_state = kLabel(random_values);
	    		    	
	    	if(temp_state.equals("High Load")){
	    		random_list[0] = temp;
	    	}else if(temp_state.equals("Shut Down")){
	    		random_list[1] = temp;	
	    	}else if(temp_state.equals("Low Load")){
	    		random_list[2] = temp;	
	    	}else if(temp_state.equals("Disconnect")){
	    		random_list[3] = temp;	
		    }
	    	
	    	condition = checkerBoolean(random_list, 1000);
	    }
	    
	    return random_list; 	
	}
	
	// Checks if element in int[] array
	public static boolean checkerBoolean(int[] array, int target) {
		for(int s: array){
			if(s==target){
				return true;
			}
		}
		return false;
	}
	
   // Makes an exact copy of an array so that it is not only a reference
	public static void arrayCopy(double[][] source, double[][] destination) {
	    for (int i = 0; i < source.length; i++) {
	        System.arraycopy(source[i], 0, destination[i], 0, source[i].length);
	    }
	}
	
	// Finds the maximum element in double[][]
	public static double maxValue (double [][] array){

	       double max = array[0][0];
	       
	       for (int i=0; i<array.length; i++){
	    	   for(int j=0; j<array[i].length; j++){
		           if (array[i][j] > max ){
		               max = array[i][j];
		           }
	    	   }
	       }
	       return max;
	}	
	
	// Finds the minimum element in double[]
	public static double minValue (double [] array){

	       double min = array[0];
	       
	       for (int i=0; i<array.length; i=i+2){
	           if (array[i]< min ){
	               min = array[i];      
	    	   }
	       }
	       return min;
	}
	
	// Initializes centroids and clusters
	public static void initialize(){ 
		
		// Assign random centroids
		int[] random_list = NotSoRandomList(values);
		
		for (int i=0; i<18; i++){
			centroids[0][i]=values[random_list[0]][i];
			centroids[1][i]=values[random_list[1]][i];
			centroids[2][i]=values[random_list[2]][i];
			centroids[3][i]=values[random_list[3]][i];		
		}
		
		List<Integer> clusters_definition = new ArrayList<Integer>();
		
		for(int i=0; i<values.length; i++){ //200
			dist1 = 0;
			dist2 = 0;
			dist3 = 0;
			dist4 = 0;
			
		  	// Calculate the distances
			for(int j=0; j<18; j++) // J = 18 because it is 18 dimensions 9 buses(voltage and angle)
			{
				dist1 += ((centroids[0][j]-values[i][j])*(centroids[0][j]-values[i][j]));				
				dist2 += ((centroids[1][j]-values[i][j])*(centroids[1][j]-values[i][j]));				
				dist3 += ((centroids[2][j]-values[i][j])*(centroids[2][j]-values[i][j]));
				dist4 += ((centroids[3][j]-values[i][j])*(centroids[3][j]-values[i][j]));				
			}
		  	// Take the square root
		  	dist1=Math.sqrt(dist1);	
			dist2=Math.sqrt(dist2);	
			dist3=Math.sqrt(dist3);	
			dist4=Math.sqrt(dist4);	
			
			// Finds the closest cluster
			double[] distArray = new double[]{dist1, dist2, dist3, dist4};
			clusters_definition.add(minIndex(distArray));		
		 }
		
		// Adds points into the clusters
		for(int i=0; i<clusters_definition.size(); i++){
			if(clusters_definition.get(i)==0){ 
				cluster1.add(new SampleData(values[i]));				
			}else if(clusters_definition.get(i)==1){
				cluster2.add(new SampleData(values[i]));
			}else if(clusters_definition.get(i)==2){
				cluster3.add(new SampleData(values[i]));
			}else{
				cluster4.add(new SampleData(values[i]));
			}		
		}				
	}
	
	// Calculates the new centroids
	public static void calculateCentroids(){ 
		
		double [][] temp_centroids = new double [k_number][18];
				
		for(int i=0; i<cluster1.size(); i++){ 
			for(int j=0; j<18; j++){ 
				temp_centroids[0][j] += cluster1.get(i).listData()[j]/cluster1.size();
			}
		}
		
		for(int i=0; i<cluster2.size(); i++){ 
			for(int j=0; j<18; j++){ 
				temp_centroids[1][j] += cluster2.get(i).listData()[j]/cluster2.size();
			}
		}
		
		for(int i=0; i<cluster3.size(); i++){ 
			for(int j=0; j<18; j++){ 
				temp_centroids[2][j] += cluster3.get(i).listData()[j]/cluster3.size();
			}
		}
		
		for(int i=0; i<cluster4.size(); i++){ 
			for(int j=0; j<18; j++){ 
				temp_centroids[3][j] += cluster4.get(i).listData()[j]/cluster4.size();
			}
		}

		centroids = temp_centroids;
	}	

	// k-means Clustering Algorithm
	public static void kClusters(){
		
		int counter = 0;
		double maxChange = 1000;
		while ((maxChange>tolerance)==true){ // Compares the maximum change in centroids position
			
			// Makes a copy of the centroids found previously
			double [][] temp_centroids = new double [k_number][18];
			arrayCopy(centroids, temp_centroids);
		
			cluster1.clear();
			cluster2.clear();
			cluster3.clear();
			cluster4.clear();
			data_wcluster.clear();
			
			List<Integer> clusters_definition = new ArrayList<Integer>();
			for(int i=0; i<values.length; i++){
				
				dist1 = 0;
				dist2 = 0;
				dist3 = 0;
				dist4 = 0;
				
			  	// Calculate the distances
				for(int j=0; j<18; j++) // 
				{
					dist1 += ((centroids[0][j]-values[i][j])*(centroids[0][j]-values[i][j]));				
					dist2 += ((centroids[1][j]-values[i][j])*(centroids[1][j]-values[i][j]));				
					dist3 += ((centroids[2][j]-values[i][j])*(centroids[2][j]-values[i][j]));
					dist4 += ((centroids[3][j]-values[i][j])*(centroids[3][j]-values[i][j]));				
				}
			  	// Take the square root
			  	dist1=Math.sqrt(dist1);	
				dist2=Math.sqrt(dist2);	
				dist3=Math.sqrt(dist3);	
				dist4=Math.sqrt(dist4);	
				
				// Finds the closest cluster
				double[] distArray = new double[]{dist1, dist2, dist3, dist4};
				clusters_definition.add(minIndex(distArray));
			 }
			
			for(int i=0; i<clusters_definition.size(); i++){
				if(clusters_definition.get(i)==0){ 
					cluster1.add(new SampleData(values[i])); 
					data_wcluster.add(new StateData(values[i], "1", kLabel(centroids[0])));
				}else if(clusters_definition.get(i)==1){
					cluster2.add(new SampleData(values[i]));
					data_wcluster.add(new StateData(values[i], "2", kLabel(centroids[1])));
				}else if(clusters_definition.get(i)==2){
					cluster3.add(new SampleData(values[i]));
					data_wcluster.add(new StateData(values[i], "3", kLabel(centroids[2])));
				}else{
					cluster4.add(new SampleData(values[i]));
					data_wcluster.add(new StateData(values[i], "4", kLabel(centroids[3])));
				}		
			}		
			
			// Calculates the new centroids, again based on the cluster information
			calculateCentroids();
			
			// Calculates the distance between centroids
			centroid_distances = toleranceCalculator(temp_centroids, centroids);
			
			maxChange = maxValue(centroid_distances);
			//System.out.println("max change: " + maxChange);
			
			counter += 1;
			
		}// end while
		//System.out.println("Iterated " + counter + " times");	
	}
	
	// Calculates the distances to be later compared with the tolerance
	public static double[][] toleranceCalculator(double[][] old_cent, double[][] new_cent){
		
		double [][] cluster_distances = new double [k_number][18];
		for(int i=0; i<k_number; i++){
			for(int j=0; j<18; j++){
				double temp_val = (old_cent[i][j] - new_cent[i][j])*(old_cent[i][j] - new_cent[i][j]);
				cluster_distances[i][j] = Math.sqrt((temp_val*temp_val));
			}
		}
		return cluster_distances;
	}	
	
	// Cluster frequency counter
	public static int clusterFrequency(List<StateData> data_list, String cluster){
		int counter = 0;
		for (StateData sample : data_list) {
			if (sample.cluster.equals(cluster)) {
				counter++;
			}
		}
		return counter;
	}
	
	//--------------- METHODS for K-NN    ----------------------
	// Finds the index of the maximum element
	public static int maxIndex (double [] array){

	       int index = 0;
	       double max = array[index];
	       for (int i=1; i<array.length; i++){

	           if (array[i] > max ){
	               max = array[i];
	               index = i;
	           }
	       }
	       return index;
	}	
	
	// kNN algorithm
	public static void knnAlgorithm(){
		for(int n=0; n<test_values.length; n++){ 
			
			// For data storage
			List<distclData> distance_cluster = new ArrayList<distclData>();
						
			// Distance between one value of test set and all training set, including the cluster
			for(int i=0; i<values.length; i++){		
			  
				// Calculate the distances
				double distance_testset=0;
				
				for(int j=0; j<18; j++) // 
				{
					distance_testset += ((test_values[n][j]-values[i][j])*(test_values[n][j]-values[i][j]));								
				}
				
			  	// Take the square root and store in the distance array
				// It also stores the cluster of the training set value
				distance_cluster.add(new distclData(Math.sqrt(distance_testset), data_wcluster.get(i).cluster));			
			 }
			
			
			// Find the k nearest objects in the training set
								
			// Step 1. Sort the data (distance, cluster) from smallest distance to largest
			Collections.sort(distance_cluster, new DistanceComparator());
						
			// Step 3. Find the k nearest objects (k smallest distances)
			// OBS: Here k is not 4
			// OBS: k for kNN is defined by k=n^(1/2) where n is the amount of values in training set
			
			double temp_val = Math.sqrt(values.length);
			int k_nn = (int) Math.round(temp_val);  
			
			// kNN nearest clusters 
			List<String>  nearest_clusters = new ArrayList<String>();
			for (int i=0; i<k_nn; i++){
				nearest_clusters.add(distance_cluster.get(i).cluster);
			}
				
			// Step 4. Analyze which is the one with most incidence			
			// Calculate the frequency of each cluster in k nearest objects
			int freq1 = Collections.frequency(nearest_clusters, "1");
			int freq2 = Collections.frequency(nearest_clusters, "2");
			int freq3 = Collections.frequency(nearest_clusters, "3");
			int freq4 = Collections.frequency(nearest_clusters, "4");
			
			// Define which cluster is the one with most frequency
			double[] freq_vector = {freq1, freq2, freq3, freq4};
			int maxfreq_index = maxIndex(freq_vector);
			
			if (maxfreq_index == 0){
				test_wcluster.add(new StateData(test_values[n], "1", kLabel(centroids[0])));
			}else if(maxfreq_index == 1){
				test_wcluster.add(new StateData(test_values[n], "2", kLabel(centroids[1])));
			}else if(maxfreq_index == 2){
				test_wcluster.add(new StateData(test_values[n], "3", kLabel(centroids[2])));
			}else{
				test_wcluster.add(new StateData(test_values[n], "4", kLabel(centroids[3])));
			}	
		} 
	}
	
	// Method for Labeling	
	public static String kLabel(double[] attributes){
		String state = null;
		double sum579 = attributes[8]+attributes[12]+attributes[16];
		if(sum579<2.92 && sum579>2.6){
			state = "High Load";
		}else if(sum579>2.97){
			state = "Low Load";
		}else if((attributes[1]-attributes[7])<0.01 || (attributes[3]-attributes[15])<0.01 || (attributes[5]-attributes[11])<0.01){
			state = "Shut Down";
		}else if(minValue(attributes)<0.85){
			state = "Disconnect";	
		}
		return state;
	}
}