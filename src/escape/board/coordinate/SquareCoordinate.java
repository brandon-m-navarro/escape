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

import java.util.Objects;

/**
 * The SquareCoordinate class extends from the TwoDimensionalCoordinate
 * class ( @see escape.board.coordinate ) and only holds the logic to
 * calculate the distance from itself to another square coordinate, 
 * as well as statically create one of itself
 * 
 * @version Mar 27, 2020
 */
public class SquareCoordinate extends TwoDimensionalCoordinate
{

    protected SquareCoordinate(int x, int y)
    {
    	super(x, y);
    }
    
    public static SquareCoordinate makeCoordinate(int x, int y)
    {
    	return new SquareCoordinate(x, y);
    }
    
    /*
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 */
	@Override
	public int distanceTo(Coordinate c)
	{
		return 0;
	}
}
