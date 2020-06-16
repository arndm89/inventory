package com.cts.inventory.vo;

/**
 * @author: Arindam.Chowdhury@cognizant.com / ctsjavafsd24
 *
 */
public class AppConstantVO {
	
	 private AppConstantVO() {
		    throw new IllegalStateException("Utility class can not be instantiate.");
		  }
	
	public static final String OPERATION_FAILED="FAILED";
	
	public static final String OPERATION_CREATED="CREATED";
	
	public static final String OPERATION_SUCCESS="SUCCESS";
	
	public static final String DUPLICATE_ENTRY = "DUPLICATE_ENTRY";
	
	public static final String ENTRY_NOT_FOUND = "ENTRY_NOT_FOUND";
	
	public static final String EXCEPTION_OCURED = "Exception occured";
	
}
