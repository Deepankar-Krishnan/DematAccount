/**
 * 
 */
package DataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.DatabaseMaintainer;
import DematAccountEntities.ShareMarket;

/**
 * @author kddeepan
 *
 */
public class ShareMarketHashMap {
	
	private static Map<String, ShareMarket> shareMarketMap;
	
	static {
		ShareMarketHashMap.shareMarketMap = new HashMap <String, ShareMarket>();
		ShareMarketHashMap.createMap(DatabaseMaintainer.readEntities("ShareMarket"));
	}
	
	private ShareMarketHashMap() {
		
	}

	private static List <String> storeMap() {
		List <String> dataList = new ArrayList<String> ();
		for(String shareName : ShareMarketHashMap.shareMarketMap.keySet()) {
			dataList.add(ShareMarketHashMap.shareMarketMap.get(shareName).toString());
		}
		return dataList;
	}
	
	private static void createMap(List <String> dataList) {		
		for(String shareData:dataList) {
			String [] shareAttributes = shareData.split(",");
			ShareMarketHashMap.shareMarketMap.put(shareAttributes[0], new ShareMarket(shareAttributes[0],Double.parseDouble(shareAttributes[1]),Integer.parseInt(shareAttributes[2])));
		}		
	}
	
	public static Map <String, ShareMarket> getShareMarket(){
		return ShareMarketHashMap.shareMarketMap;
	}
	
	public static void addShare(ShareMarket newShare) {
		ShareMarketHashMap.shareMarketMap.put(newShare.getShareName(),newShare);
		DatabaseMaintainer.addEntities(newShare.toString(), "ShareMarket");
	}
	
	public static void editShare() {
		DatabaseMaintainer.writeEntities(ShareMarketHashMap.storeMap(), "ShareMarket");
	}
}
