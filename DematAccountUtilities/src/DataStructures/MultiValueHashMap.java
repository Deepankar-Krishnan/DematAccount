/**
 * 
 */
package DataStructures;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.DatabaseMaintainer;
import Entities.UserTransaction;

/**
 * @author kddeepan
 *
 */
public class MultiValueHashMap {
	
	private static Map<Integer, List <UserTransaction>> userTransactionMap;
	
	static {
		MultiValueHashMap.userTransactionMap = new HashMap<Integer, List<UserTransaction>>();
	}

	private MultiValueHashMap(){

	}
	
	public Map<Integer, List <UserTransaction>> getUserTransactionMap(){
		return MultiValueHashMap.userTransactionMap;
	}
	
	public void setUserTransactionMap(Map <Integer, List <UserTransaction>> userTransactionMap) {
		MultiValueHashMap.userTransactionMap = userTransactionMap;
	}
	
	private static void createUserTransactionMap(List <String> dataList) {
		String [] userTransactionAttribute;
		for(String userTransactionData: dataList) {
			userTransactionAttribute = userTransactionData.split(",");
			if(MultiValueHashMap.userTransactionMap.containsKey(Integer.parseInt(userTransactionAttribute[0]))){
				MultiValueHashMap.userTransactionMap.get(Integer.parseInt(userTransactionAttribute[0])).add(new UserTransaction(Integer.parseInt(userTransactionAttribute[0]),Integer.parseInt(userTransactionAttribute[1]),userTransactionAttribute[2],userTransactionAttribute[3],userTransactionAttribute[4],userTransactionAttribute[5],Double.parseDouble(userTransactionAttribute[6]),Integer.parseInt(userTransactionAttribute[7])));
			}
			else {
				MultiValueHashMap.userTransactionMap.put(Integer.parseInt(userTransactionAttribute[0]), new ArrayList <UserTransaction> ());
				MultiValueHashMap.userTransactionMap.get(Integer.parseInt(userTransactionAttribute[0])).add(new UserTransaction(Integer.parseInt(userTransactionAttribute[0]),Integer.parseInt(userTransactionAttribute[1]),userTransactionAttribute[2],userTransactionAttribute[3],userTransactionAttribute[4],userTransactionAttribute[5],Double.parseDouble(userTransactionAttribute[6]),Integer.parseInt(userTransactionAttribute[7])));
			}
		}		
	}
	
	public static List <UserTransaction> getUserTransactionMap(int accountNumber, String shareName){
		MultiValueHashMap.createUserTransactionMap(DatabaseMaintainer.readEntities("UserTransaction"));
		List <UserTransaction> userTransactionList = new ArrayList <UserTransaction>();
		if(MultiValueHashMap.userTransactionMap.containsKey(accountNumber)) {
			for(UserTransaction userTransaction: MultiValueHashMap.userTransactionMap.get(accountNumber)) {
				if (shareName.equals(userTransaction.getshareName())) {
				userTransactionList.add(userTransaction);
				}
			}
		}
		return userTransactionList;
	}
	
	public static List <UserTransaction> getUserTransactionMap(int accountNumber, LocalDate startDate, LocalDate endDate){
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate transactionDate;
		MultiValueHashMap.createUserTransactionMap(DatabaseMaintainer.readEntities("UserTransaction"));
		List <UserTransaction> userTransactionList = new ArrayList <UserTransaction>();
		if(MultiValueHashMap.userTransactionMap.containsKey(accountNumber)) {
			for(UserTransaction userTransaction: MultiValueHashMap.userTransactionMap.get(accountNumber)) {
				transactionDate = (LocalDate.parse(userTransaction.getTransactionDate(), dateFormatter));
				if (transactionDate.isAfter(startDate) && transactionDate.isBefore(endDate) || transactionDate.isEqual(startDate) || transactionDate.isEqual(endDate)) {
				userTransactionList.add(userTransaction);
				}
			}
		}
		return userTransactionList;
	}
	
	public void updateUserTransactionMap(UserTransaction newUserTransaction){
		MultiValueHashMap.userTransactionMap.get(newUserTransaction.getAccountNumber()).add(newUserTransaction);
		DatabaseMaintainer.addEntities(newUserTransaction.toString(), "UserTransaction");
	}
	
	
	public static void main(String[] args) {
		System.out.println("CheckMultiValueHashMap");
		 
	}

	}
