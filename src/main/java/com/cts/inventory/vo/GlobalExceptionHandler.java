/**
 *
 */
package com.cts.inventory.vo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author: Arindam.Chowdhury@cognizant.com / ctsjavafsd24
 *
 */

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value={ArithmeticException.class, Exception.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		
        String exceptionTxt = "An Exception occured at the application level";
        
        return handleExceptionInternal(ex, exceptionTxt, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
