package assignment2;
import java.util.List;
import java.util.ArrayList;

import assignment2.SQLdata;

public class SortData {
	
	public List<SampleData> sortedData(String table_name) {
		
		List<SampleData> sample_data = new ArrayList<SampleData>();
		
		// Create a new SQLdata object and invoking the communication method (for Java and MySQL)	
		SQLdata MySQL = new SQLdata();
		List<Measurements> info = MySQL.commsSQLJAVA(table_name);	
		
		for (int i=0; i<(info.size()); i=i+18){
			
			double[] extract_sampledata = new double[18];
			
			for(int j=0; j<18; j++ ){ // 18 attributes (9 buses with voltages and angles)
				extract_sampledata[j] = info.get(i+j).value;			
			}
			sample_data.add(new SampleData(extract_sampledata));		
		}
				
		return sample_data;
	}
	
}