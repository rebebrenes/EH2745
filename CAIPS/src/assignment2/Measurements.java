package assignment2;

public class Measurements {

	public String rdfid;
	public String name;
	public double time;
	public double value;
	public String sub_rdfid;
	
	public Measurements(String rdfid, String name, double time, double value, String sub_rdfid){
		this.rdfid = rdfid;
		this.name = name;
		this.time = time;
		this.value = value;
		this.sub_rdfid = sub_rdfid;
	}

	public String getRdfid() {
		return rdfid;
	}
	
	public String getName() {
		return name;
	}
	
	public double getTime() {
		return time;
	}
	
	public double getValue() {
		return value;
	}
	
	public String getSubrdfif() {
		return sub_rdfid;
	}
}