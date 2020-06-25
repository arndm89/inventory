/**
 *
 */
package com.cts.inventory.exception;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author: Arindam.Chowdhury@cognizant.com / ctsjavafsd24
 *
 */
public class ItemException extends Exception{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 379642198780718919L;
	
	private static final Logger logger = LogManager.getLogger(ItemException.class);

	public ItemException(String errorMessage, Throwable ex) {
		
		logger.log(Level.ERROR, "ItemException :: {0}.", errorMessage);
		logger.log(Level.ERROR, "REASON :: {0}.", ex.getLocalizedMessage());
		
    }
	
	

}
