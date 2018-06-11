package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CSV {
	
    public void CSVwriter(String file_name, double[][] data_set){
    	
        PrintWriter pw;
		try {
			pw = new PrintWriter(new File(file_name + ".csv"));

			StringBuilder sb = new StringBuilder();
			
	        sb.append("V1");
	        sb.append(',');
	        sb.append("A1");
	        sb.append(',');
	        sb.append("V2");
	        sb.append(',');
	        sb.append("A2");
	        sb.append(',');
	        sb.append("V3");
	        sb.append(',');
	        sb.append("A3");
	        sb.append(',');
	        sb.append("V4");
	        sb.append(',');
	        sb.append("A4");
	        sb.append(',');
	        sb.append("V5");
	        sb.append(',');
	        sb.append("A5");
	        sb.append(',');
	        sb.append("V6");
	        sb.append(',');
	        sb.append("A6");
	        sb.append(',');
	        sb.append("V7");
	        sb.append(',');
	        sb.append("A7");
	        sb.append(',');
	        sb.append("V8");
	        sb.append(',');
	        sb.append("A8");
	        sb.append(',');
	        sb.append("V9");
	        sb.append(',');
	        sb.append("A9");
	        sb.append('\n');
			
	        for(int i=0; i<data_set.length; i++){
	        	
	        	for(int j=0; j<data_set[i].length; j++){
		            sb.append(data_set[i][j]);
		            if(j<data_set[i].length-1){
		            	sb.append(',');
		            }else{
		            	sb.append('\n');
		            }
		             
	        	}		            
	            
	        }
	        pw.write(sb.toString());
	        pw.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
    }
    
    public void CSVwriterCluster(String file_name, List<StateData> data){
    	
        PrintWriter pw;
		try {
			pw = new PrintWriter(new File(file_name + ".csv"));

			StringBuilder sb = new StringBuilder();
			
	        sb.append("V1");
	        sb.append(',');
	        sb.append("A1");
	        sb.append(',');
	        sb.append("V2");
	        sb.append(',');
	        sb.append("A2");
	        sb.append(',');
	        sb.append("V3");
	        sb.append(',');
	        sb.append("A3");
	        sb.append(',');
	        sb.append("V4");
	        sb.append(',');
	        sb.append("A4");
	        sb.append(',');
	        sb.append("V5");
	        sb.append(',');
	        sb.append("A5");
	        sb.append(',');
	        sb.append("V6");
	        sb.append(',');
	        sb.append("A6");
	        sb.append(',');
	        sb.append("V7");
	        sb.append(',');
	        sb.append("A7");
	        sb.append(',');
	        sb.append("V8");
	        sb.append(',');
	        sb.append("A8");
	        sb.append(',');
	        sb.append("V9");
	        sb.append(',');
	        sb.append("A9");
	        sb.append(',');
	        sb.append("Cluster");
	        sb.append(',');
	        sb.append("State");
	        sb.append('\n');
			  
	        
	        for(int i=0; i<data.size(); i++){
	        	double[] attributes = data.get(i).listData();
	        	String cluster = data.get(i).cluster;
	        	String state = data.get(i).state;
	        	
	        	for(int j=0; j<attributes.length; j++){
		            sb.append(attributes[j]);
		            
		            if(j<attributes.length-1){
		            	sb.append(',');
		            }else{
		            	sb.append(',');
		            	sb.append(cluster);
		            	sb.append(',');
		            	sb.append(state);
		            	sb.append('\n');
		            }
	        	}		            
	        }
	        pw.write(sb.toString());
	        pw.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
    }
}