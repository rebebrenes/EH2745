package assignment2;

public class SampleData {

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
	
	public SampleData(double[] test){
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
	}

//	public SampleData(double voltage1, double angle1,double voltage2, double angle2,double voltage3, double angle3, double voltage4, double angle4, double voltage5, double angle5, double voltage6, double angle6, double voltage7, double angle7, double voltage8, double angle8, double voltage9, double angle9){
//		this.voltage1=voltage1;
//		this.angle1=angle1;
//		this.voltage2=voltage2;
//		this.angle2=angle2;
//		this.voltage3=voltage3;
//		this.angle3=angle3;
//		this.voltage4=voltage4;
//		this.angle4=angle4;
//		this.voltage5=voltage5;
//		this.angle5=angle5;
//		this.voltage6=voltage6;
//		this.angle6=angle6;
//		this.voltage7=voltage7;
//		this.angle7=angle7;
//		this.voltage8=voltage8;
//		this.angle8=angle8;
//		this.voltage9=voltage9;
//		this.angle9=angle9;
//	}
	public double[] listData() {
		double[] summary_data = {voltage1, angle1, voltage2, angle2, voltage3, angle3, voltage4, angle4, voltage5, angle5, voltage6, angle6, voltage7, angle7, voltage8, angle8, voltage9, angle9};
		return summary_data;
	}
}