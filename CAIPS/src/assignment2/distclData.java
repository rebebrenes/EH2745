package assignment2;

public class distclData {

	public double distance;
	public String cluster;
	
	public distclData(double distance, String cluster){
		this.distance = distance;
		this.cluster = cluster;
	}	
	
	public String getCluster(){
		return cluster;
	}
	
	public double getDistance(){
		return distance;
	}
}