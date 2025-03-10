package org.onlin.election.excep;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionCenter {
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleAllExceptions(Exception ex) {
	        // Retrieve the stack trace elements from the exception
	        StackTraceElement[] stackTrace = ex.getStackTrace();

	        // Check if we have at least one element
	        if (stackTrace != null && stackTrace.length > 0) {
	            // The first element is usually the point where the exception was thrown.
	            StackTraceElement origin = stackTrace[0];
	            String className = origin.getClassName();
	            String methodName = origin.getMethodName();
	            int lineNumber = origin.getLineNumber();

	            // Log or use the details as needed
	            System.err.println("Exception occurred in " + className + "." + methodName +
	                    " at line " + lineNumber);
	        } else {
	            System.err.println("No stack trace available.");
	        }

	        // Return a generic error response (customize as needed)
	        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

}
