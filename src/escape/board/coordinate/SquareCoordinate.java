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
 * The SquareCoordinate class extends from the TwoDimensionalCoordinate
 * class ( @see escape.board.coordinate ) and only holds the logic to
 * calculate the distance from itself to another square coordinate, 
 * as well as statically create one of itself
 * 
 * @version Mar 27, 2020
 */
public class SquareCoordinate extends TwoDimensionalCoordinate
{
    
    private SquareCoordinate(int x, int y)
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
		// We can cast here because we can make the assumption that this coordinate
		// is in face a square because we are always given a valid config of a board
		SquareCoordinate to = (SquareCoordinate) c;

		if (this.getX() == to.getX())
			return calcSimpleDistance(to.getY(), this.getY());
		else if (this.getY() == to.getY())
			return calcSimpleDistance(to.getX(), this.getX());
		else if (isPerfectlyDiagonal(to))
			return calcSimpleDistance(to.getX(), this.getX());
		else
			return calcShortestDistance(to);
	}
	
	/**
	 * This is a helper method that simply calculates the distance between two points
	 * that are on the same axis
	 * @param from the single x or y component
	 * @return the absolute value between the two ints
	 */
	private int calcSimpleDistance(int from, int to)
	{
		return Math.abs(Math.abs(to) - Math.abs(from));
	}
	
	/**
	 * This method determines if the two coordinates are perfectly diagonal from
	 * one another. In other words, there is no orthogonal movement allowed to get
	 * from one square to the other
	 * @param to the coordinate that is being checked against
	 * @return true if the coordinates are perfectly diagonal
	 */
	private boolean isPerfectlyDiagonal(SquareCoordinate to)
	{
		return calcSimpleDistance(this.getX(), to.getX()) == 
				calcSimpleDistance(this.getY(), to.getY());
	}
	
	/**
	 * This method contains the algorithm to find the shortest route between two
	 * coordinates. It works by first traversing diagonally 
	 * @param to the coordinate that is being travelled to
	 * @return the shortest distance between two points
	 */
	private int calcShortestDistance(SquareCoordinate to)
	{
		int travellerX = this.getX();
		int travellerY = this.getY();
		int distance = 0;
		
		while (travellerX != to.getX() && travellerY != to.getY())
		{
			if (travellerX > to.getX())
				travellerX--;
			else
				travellerX++;
			if (travellerY > to.getY())
				travellerY--;
			else
				travellerY++;
			distance++;
		}
		
		if (travellerX == to.getX())
			return distance += calcSimpleDistance(travellerY, to.getY());
		else
			return distance += calcSimpleDistance(travellerX, to.getX());
	}
}
