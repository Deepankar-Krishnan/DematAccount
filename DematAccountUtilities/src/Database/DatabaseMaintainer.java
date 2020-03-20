/**
 * 
 */
package Database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kddeepan
 *
 */
public class DatabaseMaintainer {
	
	public static void writeFile(List <String> dataList, String entityType) throws IOException {
		
		BufferedWriter myWriter = new BufferedWriter(new FileWriter("C:/Users/kddeepan/git/repository/DematAccountUtilities/bin/"+entityType+".txt"));
		for(String data: dataList) {
			myWriter.write(data);
			myWriter.newLine();
		}
		myWriter.close();
	}
	
	public static List <String> readFile(String entityType) throws IOException {
		BufferedReader myReader = new BufferedReader(new FileReader ("C:/Users/kddeepan/git/repository/DematAccountUtilities/bin/"+entityType+".txt")); 
		List <String> dataList = new ArrayList <String>();
		String data;
		while((data = myReader.readLine())!=null) {
			dataList.add(data);
		}
		myReader.close();
		return dataList;
	}
	
	public static void updateFile (List <String> dataList, String entityType) throws IOException {
		DatabaseMaintainer.writeFile(dataList, entityType);
		DatabaseMaintainer.readFile(entityType);
	}
	
	public static void main(String[] args) throws IOException {
        ClassLoader loader = DatabaseMaintainer.class.getClassLoader();
        System.out.println(loader.getResource("Database/DatabaseMaintainer.class"));
        DatabaseMaintainer.writeFile(new ArrayList <String>(Arrays.asList("Deepankar,123,45.60","Krishna,456,123.54","Murthy,675,234.87","Sandeep,3526,780.23","Chaitu,65234,893.20")),"UserData");
        System.out.println(DatabaseMaintainer.readFile("UserData"));
        DatabaseMaintainer.writeFile(new ArrayList <String>(Arrays.asList("Vamsi,123,45.60")),"UserData");
        System.out.println(DatabaseMaintainer.readFile("UserData"));	
	}
}
