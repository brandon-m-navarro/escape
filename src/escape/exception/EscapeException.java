/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Copyright ©2016-2020 Gary F. Pollice
 *******************************************************************************/
package escape.exception;

/**
 * This is the base exception class for the Escape game. It is a subclass of
 * RuntimeException. This is simply convenience since these are unchecked. 
 * We expect exceptions to be meaningful, so any new exception types should be a
 * subclass of this class.
 * @version Mar 28, 2020
 */
public class EscapeException extends RuntimeException
{
	/**
	 * There must at least be a message associated with any exception.
	 * @param message a meaningful message describing the problem
	 */
	public EscapeException(String message)
	{
		super(message);
	}
	
	/**
	 * Use this constructor if the exception were caused by something like
	 * a NullPointerException and you want to provide that for debugging
	 * purposes.
	 * @param message a meaningful message describing the problem
	 * @param cause the exception that caused the problem
	 */
	public EscapeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
