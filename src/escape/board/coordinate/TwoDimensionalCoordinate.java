/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Brandon Navarro
 *******************************************************************************/

package escape.board.coordinate;

import java.util.Objects;

/**
 * The TwoDimensionalCoordinate class is an abstract class
 * that holds all the common functionality of each Coordinate 
 * type ( @see escape.board.coordinate.CoordinateId )
 * @version Apr 18, 2020
 */
public abstract class TwoDimensionalCoordinate implements Coordinate
{
    private final int x;
    private final int y;
 
	public TwoDimensionalCoordinate(int x, int y) 
	{
    	this.x = x;
    	this.y = y;
	}
	
	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(x, y);
	}
	
	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
//		if (this == obj) {
//			return true;
//		}
//		if (!(obj instanceof SquareCoordinate)) {
//			return false;
//		}
//		SquareCoordinate other = (SquareCoordinate) obj;
//		return x == other.x && y == other.y;
		return true;
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}

	// I like where this is going, but we need to put this into 
	// some sort of factory class
//	public static Coordinate makeCoordinate(SquareCoordinate sc)
//	{
//		return (Coordinate)new SquareCoordinate(sc.getX(), sc.getY());
//	}
}
