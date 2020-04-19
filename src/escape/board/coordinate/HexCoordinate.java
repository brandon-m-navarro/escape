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
public class HexCoordinate extends TwoDimensionalCoordinate
{

	public HexCoordinate(int x, int y)
	{
		super(x, y);
	}
	
	public static Coordinate makeCoordinate(int x, int y)
	{
		return new HexCoordinate(x, y);
	}

	/*
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 */
	@Override
	public int distanceTo(Coordinate c)
	{
		return calcHexDistance(c);
	}
	
	/**
	 * This algorithm calculates the distance from one coordinate to another. This
	 * algorithm was adapted from user jonathankoren on StackExchange
	 * https://stackoverflow.com/questions/14491444/calculating-distance-on-a-hexagon-grid
	 * @param c the coordinate that is being travelled to
	 * @return the distance from one hex coordinate to another
	 */
	private int calcHexDistance(Coordinate c)
	{
		HexCoordinate to = (HexCoordinate) c;
		if (this.getX() == to.getX())
		{
			return calcSimpleHexDistance(to.getY(), this.getY());
		} 
		else if (this.getY() == to.getY())
		{
			return calcSimpleHexDistance(to.getX(), this.getX());
		}
		else
		{
			int dx = Math.abs(to.getX() - this.getX());
			int dy = Math.abs(to.getY() - this.getY());
			if (this.getY() < to.getY())
			{
				System.out.println("(fromX, fromY) - " + this.getX() + this.getY());
				System.out.println("(toX, toY) - " + to.getX() + to.getY());
				System.out.println("dx - " + dx);
				System.out.println("dy - " + dy);
				return dx + dy - (int) (Math.ceil(dx / 2.0));
			}
			else if (this.getX() < to.getX())
			{
				return dx;
			}
			else
			{
				return dx + dy - (int) (Math.floor(dx / 2.0));
			}
		}
	}
	
	private int calcSimpleHexDistance(int from, int to)
	{
		return Math.abs(to - from);
	}
}