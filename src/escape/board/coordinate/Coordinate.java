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
package escape.board.coordinate;

/**
 * This is a interface that every type of coordinate must implement.
 * 
 *  NOTE: Coordinate instances must override equals() and hashCode() methods.
 *  In addition, there should be a static factory method called "makeCoordinate" that
 *  takes the appropriate number of ints, and returns
 *  an instance of the appropriate. For example in the 2D coordinate
 *  you might have:
 *  
 *  public static Coordinate2D makeCoordinate(int x, int y)
 *  
 * @version Mar 31, 2020
 */
public interface Coordinate
{
	/**
     * @param c the other coordinate
     * @return the distance from this coordinate to the other coordinate
     */
    int distanceTo(Coordinate c);
}
