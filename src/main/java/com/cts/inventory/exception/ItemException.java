/**
 *
 */
package com.cts.inventory.exception;

/**
 * @author: Arindam.Chowdhury@cognizant.com / ctsjavafsd24
 *
 */
public class ItemException extends Exception{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 379642198780718919L;

	public ItemException(String errorMessage, Throwable ex) {
        super(errorMessage, ex);
    }
	
	

}
