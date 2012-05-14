package com.shri.photo.pricing.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.shri.databean.DataAccess;
import com.shri.databean.DataException;
import com.shri.photo.PhotoDBConstants;
import com.shri.photo.common.ConnectionCoordinator;
import com.shri.photo.common.LoggerUtil;
import com.shri.photo.common.PhotoCommonConstants;
import com.shri.photo.common.exception.PhotoError;
import com.shri.photo.common.exception.PhotoException;
import com.shri.photo.common.util.DAOUtil;
import com.shri.photo.common.util.PhotoStringUtil;
import com.shri.photo.orderentry.databean.RetailLevelPriceDatabean;
import com.shri.photo.pricing.PricingConstants;

public class TraceEnterSample implements PriceDAO {

	
	private static String LOAD_RETAIL_LEVEL_INFO_QUERY = new StringBuffer(
			"Select a.PF_PRODUCT_ID,b.PF_EXTRA_PRODUCT_ID,PF_MINIMUM_TIER_QTY,\n")
			.append(
					"PF_MAXIMUM_TIER_QTY,PF_TIER_TYPE,PF_ESTIM_RETAIL,PF_DEVELOPMENT_RETAIL,\n")
			.append(
					"PF_MINIMUM_PRODUCT_RETAIL,PF_MAXIMUM_PRODUCT_RETAIL,PF_RETAIL_PRICE_BASED,\n")
			.append(
					"b.PF_INDEX_INCL_IND, b.PF_FIRST_USAGE_RETAIL, b.PF_SECOND_USAGE_RETAIL,PF_PRODUCT_TYPE_IND,\n")
			.append("a.PF_PRINT_RETAIL,a.PF_PANORAMIC_PRINT_RETAIL,a.PF_FUDGE_FACTOR FROM \n")
			.append(PhotoDBConstants.PRODUCT).append(" c, ").append(
					PhotoDBConstants.PRODUCT_RETAIL_LEVEL_PRICING).append(
					" a LEFT OUTER JOIN \n").append(
					PhotoDBConstants.EXTRA_PRODUCT_ASSOC).append(
					" b ON a.PF_PRODUCT_ID = b.PF_PRODUCT_ID \n").append(
					"AND a.PF_RETAIL_LEVEL_ID = b.PF_RETAIL_LEVEL_ID \n")
			.append(" WHERE \n").append(" a.PF_PRODUCT_ID = c.PF_PRODUCT_ID ")
			.toString();


	/**
	 * This method is called by the BO layer. It returns a retaillevelInfoMap,
	 * in which the key is productID and value is List of retailLevelBeans.
	 * 
	 * It performs following steps - 1.Gets connection from
	 * ConnectionCoordinator. 2.Executes the query 3.Closes the database
	 * connection.
	 * 
	 * @param ProductIDList
	 * 			A list of Product IDs 
	 * @param retailID
	 * 			retail level Id of a store
	 * @return java.util.Map
	 * 
	 */
	public Map getRetailLevelBeanListForOrder(List productIDList, int retailID)
			throws PhotoException {
		LoggerUtil.traceEnter("PriceDAOStoreImpl.getRetailLevelBeanListForOrder entered");
		Connection conn = null;
		DataAccess da = null;
		RetailLevelPriceDatabean mainProductRetailBean = null;
		RetailLevelPriceDatabean extraProductRetailBean = null;
		int extraProductId = 0;
		Map retailLevelPriceMap = new HashMap();
		String strProductIDList = PhotoStringUtil.convertToParanthesizedCommaSepString(productIDList);
		StringBuffer strquery = new StringBuffer(LOAD_RETAIL_LEVEL_INFO_QUERY);
		strquery.append("AND a.PF_PRODUCT_ID IN ").append(strProductIDList)
				.append(" AND a.PF_RETAIL_LEVEL_ID = ").append(retailID)
				.append(" ORDER BY a.PF_PRODUCT_ID,PF_MINIMUM_TIER_QTY");
		String strQueryString = strquery.toString();
		
		try {
			conn = ConnectionCoordinator
					.getDBConnection(PhotoCommonConstants.CONN_SOURCE);
			da = new DataAccess(conn, strQueryString);
			da.executeQuery();
			double firstUsageRetail;double secondUsageRetail;
			while (da.next()) {
				mainProductRetailBean = new RetailLevelPriceDatabean();
				Integer prod_ID = new Integer(da.getInt(1));// PF_PRODUCT_ID
				extraProductId = da.getInt(2);// PF_EXTRA_PRODUCT_ID
				mainProductRetailBean.setMinimumTierQty(da.getInt(3));// PF_MINIMUM_TIER_QTY
				mainProductRetailBean.setMaximumTierQty(da.getInt(4));// PF_MAXIMUM_TIER_QTY
				mainProductRetailBean.setTierType(da.getString(5));// PF_TIER_TYPE
				mainProductRetailBean.setEstimatedRetailPrice(da.getDouble(6));// PF_ESTIM_RETAIL
				mainProductRetailBean.setDevelopmentPrice(da.getDouble(7));// PF_DEVELOPMENT_RETAIL
				mainProductRetailBean.setMinimumProductPrice(da.getDouble(8));// PF_MINIMUM_PRODUCT_RETAIL
				mainProductRetailBean.setMaximumProductPrice(da.getDouble(9));// PF_MAXIMUM_PRODUCT_RETAIL
				mainProductRetailBean.setPriceBased(da.getString(10));// PF_RETAIL_PRICE_BASED
				String indexInclInd = da.getString(11);
				mainProductRetailBean.setRetailIncludedIndicator(indexInclInd);// PF_INDEX_INCL_IND
				firstUsageRetail = da.getDouble(12);
				mainProductRetailBean.setFirstUsagePrice(firstUsageRetail);// PF_FIRST_USAGE_RETAIL
				secondUsageRetail = da.getDouble(13);
				mainProductRetailBean.setSecondUsagePrice(secondUsageRetail);// PF_SECOND_USAGE_RETAIL
				mainProductRetailBean.setProductType(da.getString(14));// PF_PRODUCT_TYPE_IND
				//Added for Calculated Price by Geeta Seth on 7-July-06 
				mainProductRetailBean.setPrintRetail(da.getDouble(15));//PF_PRINT_RETAIL
				mainProductRetailBean.setPanoramicPrintRetail(da.getDouble(16));//PF_PANORAMIC_PRINT_RETAIL
				mainProductRetailBean.setFudgeFactor(da.getDouble(17));//PF_FUDGE_FACTOR
				//Ended for Calculated Price by Geeta Seth on 7-July-06 
				
				// For handling the Extra Product present in the productIDList
				if (extraProductId != 0
						&& productIDList.contains(new Integer(extraProductId))) {
					extraProductRetailBean = new RetailLevelPriceDatabean();
					// Set the pricing attrbutes for Extra Product
					extraProductRetailBean.setRetailIncludedIndicator(indexInclInd);// PF_INDEX_INCL_IND
					extraProductRetailBean.setFirstUsagePrice(firstUsageRetail);// PF_FIRST_USAGE_RETAIL
					extraProductRetailBean
							.setSecondUsagePrice(secondUsageRetail);// PF_SECOND_USAGE_RETAIL
					extraProductRetailBean.setProductType(PricingConstants.EXTRA_PRODUCT_IND);
					List extraProductRetailList = (List) (retailLevelPriceMap
							.get(new Integer(extraProductId)));
					if (extraProductRetailList == null) {
						extraProductRetailList = new ArrayList();
						extraProductRetailList.add(extraProductRetailBean);
					} else {
						//Check if the tier is already present - do not add to the list
						updateExtraTierList(extraProductRetailBean,extraProductRetailList);
					
					}
					retailLevelPriceMap.put(new Integer(extraProductId),
							extraProductRetailList);
				}
				// For handling the main product present in the productIDList
				List retailLevelList = (List) (retailLevelPriceMap.get(prod_ID));
				if (retailLevelList == null) {
					retailLevelList = new ArrayList();
					retailLevelList.add(mainProductRetailBean);
				} else {
				//Check if the tier is already present - do not add to the list
				   updateMainTierList(mainProductRetailBean,retailLevelList);
				   //retailLevelList.add(mainProductRetailBean);
				}
				retailLevelPriceMap.put(prod_ID, retailLevelList);
			}
			//LoggerUtil.traceExit("PriceDAOStoreImpl.getRetailLevelBeanListForOrder exiting");
			return retailLevelPriceMap;
		} catch (DataException e) {
			throw LoggerUtil
					.createAndLogPhotoException(
							"SQL Exception occurred while getting the pricing information",
							new PhotoError("expError.system.dataExp"), e);
		} finally {
			DAOUtil.doFinally(conn, da);
		}
	}

	/** This method is used to add a retaillevel bean for a main product in a list of retaillevel beans 
	 * @param mainProductRetailBean
	 * 			Retail level bean for a main product
	 * @param retailLevelList
	 * 			List of retail level beans for a main product
	 */
	private void updateMainTierList(RetailLevelPriceDatabean mainProductRetailBean, List retailLevelList) {
		Iterator itRetailList = retailLevelList.iterator();
			RetailLevelPriceDatabean tempRetailLevelBean = null;
			boolean productToBeAdded = true;
			while(itRetailList.hasNext()){
				tempRetailLevelBean = (RetailLevelPriceDatabean)itRetailList.next();
				if( (tempRetailLevelBean.getProductID() == mainProductRetailBean.getProductID()) &&
				   (tempRetailLevelBean.getMinimumTierQty() == mainProductRetailBean.getMinimumTierQty())&&
				    (tempRetailLevelBean.getMaximumTierQty() == mainProductRetailBean.getMaximumTierQty())){
				  productToBeAdded = false;
				  break;
				}
			}
			if(productToBeAdded){
				retailLevelList.add(mainProductRetailBean);
			}
		
	}

	/** This method is used to add a retaillevel bean for an extra product in a list 
	 *  if it's first usage price is lesser than the price of the bean that is already present in the list,
	 *  incase the product Id's are same for both.
	 * @param extraProductRetailBean
	 * 			Retail level bean for an extra product
	 * @param extraProductRetailList
	 * 			List of retail level beans for an extra product
	 */
	private void updateExtraTierList(RetailLevelPriceDatabean extraProductRetailBean, List extraProductRetailList) {
			int loopCount = extraProductRetailList.size();
			RetailLevelPriceDatabean tempRetailLevelBean = null;
			for (int i = 0; i < loopCount; i++) {
				tempRetailLevelBean = (RetailLevelPriceDatabean)extraProductRetailList.get(i);
				if(tempRetailLevelBean.getProductID() == extraProductRetailBean.getProductID()){ 
					if(tempRetailLevelBean.getFirstUsagePrice() > extraProductRetailBean.getFirstUsagePrice()){
							extraProductRetailList.remove(i);
							extraProductRetailList.add(extraProductRetailBean);
					}
					break;
				}
			}
		}

}