/**
 * 
 */
package Database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import Entities.UserData;

/**
 * @author kddeepan
 *
 */
public class DatabaseMaintainer {
	
	static String binAddress;
	
	//Static block to fetch the bin address of the project
	static {
		binAddress = System.getProperty("java.class.path");
	}
	
	//Method to write entity details into the file
	public static void writeEntities(List <String> dataList, String entityType) {
		
		BufferedWriter myWriter;
		try {
			myWriter = new BufferedWriter(new FileWriter(binAddress+"\\"+entityType+".txt"));
			for(String data: dataList) {
				myWriter.write(data);
				myWriter.newLine();
			}
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Method to read entity details from the file
	public static List <String> readEntities(String entityType) {
		BufferedReader myReader;
		List <String> dataList = new ArrayList <String>();
		String data;
		try {
			myReader = new BufferedReader(new FileReader (binAddress+"\\"+entityType+".txt"));
			while((data = myReader.readLine())!=null) {
				dataList.add(data);
			}
			myReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return dataList;
	}
	
	//Method to update file upon changing any entity details
	public static void updateEntities (List <String> dataList, String entityType) {
		DatabaseMaintainer.writeEntities(dataList, entityType);
		DatabaseMaintainer.readEntities(entityType);
	}
	
	//Method to add details into the file upon creation of a new entity
	public static void addEntities(String entityData, String entityType) {
		BufferedWriter myWriter;
		try {
			myWriter = new BufferedWriter(new FileWriter (binAddress+"\\"+entityType+".txt", true));
			myWriter.write(entityData);
			myWriter.newLine();
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args) {
		ClassLoader loader = DatabaseMaintainer.class.getClassLoader();
        System.out.println(loader.getResource("Database/DatabaseMaintainer.class"));
        System.out.println(System.getProperty("path.separator"));
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(System.getProperty("java.class.path").toString());
        System.out.println("Checking");
        DatabaseMaintainer.addEntities("ABC", "UserData");
        DatabaseMaintainer.addEntities("ABC", "UserData");
        DatabaseMaintainer.addEntities("ABC", "UserData");
        for(String data:DatabaseMaintainer.readEntities("UserData")){
        	System.out.println(data);
        }
        
        System.out.println("..................");
        
        UserData user1 = new UserData("Deepankar","TestPassword");
        UserData user2 = new UserData("Sandeep","TestPassword");
        UserData user3 = new UserData("Ranadheer","TestPassword");
        UserData user4 = new UserData("Dhruv","TestPassword");
        
        try {
        	File entityFile = new File(binAddress+"\\"+"Test.txt");
        	entityFile.createNewFile();
			FileOutputStream f = new FileOutputStream(entityFile,true);
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(user1);
			o.writeObject(user2);
			o.writeObject(user3);
			o.writeObject(user4);
			
			o.close();
			f.close();
			
			FileInputStream fi = new FileInputStream(new File(binAddress+"\\"+"Test.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			UserData u1 = (UserData) oi.readObject();
			UserData u2 = (UserData) oi.readObject();
			UserData u3 = (UserData) oi.readObject();
			UserData u4 = (UserData) oi.readObject();

			System.out.println(u1.toString());
			System.out.println(u2.toString());
			System.out.println(u3.toString());
			System.out.println(u4.toString());

			oi.close();
			fi.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
	}
}