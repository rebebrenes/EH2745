package assignment2;

public class StateData {

	public double voltage1;
	public double angle1;
	public double voltage2;
	public double angle2;
	public double voltage3;
	public double angle3;
	public double voltage4;
	public double angle4;
	public double voltage5;
	public double angle5;
	public double voltage6;
	public double angle6;
	public double voltage7;
	public double angle7;
	public double voltage8;
	public double angle8;
	public double voltage9;
	public double angle9;
	public String cluster;
	public String state;
	
	public StateData(double[] test, String cluster, String state){
		this.voltage1=test[0];
		this.angle1=test[1];
		this.voltage2=test[2];
		this.angle2=test[3]; 
		this.voltage3=test[4];
		this.angle3=test[5];
		this.voltage4=test[6];
		this.angle4=test[7];
		this.voltage5=test[8];
		this.angle5=test[9];
		this.voltage6=test[10];
		this.angle6=test[11];
		this.voltage7=test[12];
		this.angle7=test[13];
		this.voltage8=test[14];
		this.angle8=test[15];
		this.voltage9=test[16];
		this.angle9=test[17];
		this.cluster = cluster;
		this.state = state;
	}
	
	public double[] listData(){
		double[] attributes = {voltage1, angle1, voltage2, angle2, voltage3, angle3, voltage4, angle4, voltage5, angle5, voltage6, angle6, voltage7, angle7, voltage8, angle8, voltage9, angle9};
		return attributes;
	}
}