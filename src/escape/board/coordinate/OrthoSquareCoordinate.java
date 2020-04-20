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

/**
 * Description
 * @version Apr 18, 2020
 */
public class OrthoSquareCoordinate extends TwoDimensionalCoordinate
{

	/**
	 * Description
	 * @param x
	 * @param y
	 */
	public OrthoSquareCoordinate(int x, int y)
	{
		super(x, y);
	}
	
	public static Coordinate makeCoordinate(int x, int y)
	{
		return new OrthoSquareCoordinate(x, y);
	}

	/*
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 */
	@Override
	public int distanceTo(Coordinate c)
	{
		// We can cast here because we can make the assumption that this coordinate
		// is in face a square because we are always given a valid config of a board
		OrthoSquareCoordinate to = (OrthoSquareCoordinate) c;
		
		if (this.getX() == to.getX())
			return calcSimpleDistance(to.getY(), this.getY());
		else if (this.getY() == to.getY())
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
	 * This method calculates the shortest distance between two points using only
	 * orthogonal movements
	 * @param to the coordinate that is being travelled to
	 * @return the shortest distance between two points
	 */
	private int calcShortestDistance(OrthoSquareCoordinate to)
	{
		int travellerX = this.getX();
		int travellerY = this.getY();
		int distance = 0;
		
		while (travellerX != to.getX() && travellerY != to.getY())
		{
			if (travellerX > to.getX())
				travellerX--;
			else if (travellerX < to.getX())
				travellerX++;
			else if (travellerY > to.getY())
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
