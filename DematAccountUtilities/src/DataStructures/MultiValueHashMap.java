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
		MultiValueHashMap.createUserTransactionMap(DatabaseMaintainer.readEntities("UserTransaction"));
	}

	private MultiValueHashMap(){

	}
	
	private static void createUserTransactionMap(List <String> dataList) {
		String [] userTransactionAttribute;
		for(String userTransactionData: dataList) {
			userTransactionAttribute = userTransactionData.split(",");
			if(MultiValueHashMap.userTransactionMap.containsKey(Integer.parseInt(userTransactionAttribute[0]))){
				MultiValueHashMap.userTransactionMap.get(Integer.parseInt(userTransactionAttribute[0])).add(new UserTransaction(Integer.parseInt(userTransactionAttribute[0]),Integer.parseInt(userTransactionAttribute[1]),userTransactionAttribute[2],userTransactionAttribute[3],userTransactionAttribute[4],userTransactionAttribute[5],Double.parseDouble(userTransactionAttribute[6]),Integer.parseInt(userTransactionAttribute[7]),Double.parseDouble(userTransactionAttribute[8]),Double.parseDouble(userTransactionAttribute[9])));
			}
			else {
				MultiValueHashMap.userTransactionMap.put(Integer.parseInt(userTransactionAttribute[0]), new ArrayList <UserTransaction> ());
				MultiValueHashMap.userTransactionMap.get(Integer.parseInt(userTransactionAttribute[0])).add(new UserTransaction(Integer.parseInt(userTransactionAttribute[0]),Integer.parseInt(userTransactionAttribute[1]),userTransactionAttribute[2],userTransactionAttribute[3],userTransactionAttribute[4],userTransactionAttribute[5],Double.parseDouble(userTransactionAttribute[6]),Integer.parseInt(userTransactionAttribute[7]),Double.parseDouble(userTransactionAttribute[8]),Double.parseDouble(userTransactionAttribute[9])));
			}
		}		
	}
	
	public static List <UserTransaction> getUserTransactionMap(int accountNumber, String shareName){
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
	
	public static void updateUserTransactionMap(UserTransaction newUserTransaction){
		if(MultiValueHashMap.userTransactionMap.containsKey(newUserTransaction.getAccountNumber())) {
			MultiValueHashMap.userTransactionMap.get(newUserTransaction.getAccountNumber()).add(newUserTransaction);			
		}
		else {
			MultiValueHashMap.userTransactionMap.put(newUserTransaction.getAccountNumber(), new ArrayList <UserTransaction> ());
			MultiValueHashMap.userTransactionMap.get(newUserTransaction.getAccountNumber()).add(newUserTransaction);
		}
		DatabaseMaintainer.addEntities(newUserTransaction.toString(), "UserTransaction");
	}
}
