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

import java.util.ArrayList;

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
//		return calcHexDistance(c);
		HexCoordinate to =(HexCoordinate) c;
		return calcHexDistance(this.getX(), this.getY(), to.getX(), to.getY());
	}
	

	/**
	 * This algorithm calculates the distance from one coordinate to another. This
	 * algorithm was adapted from user mikera on StackExchange
	 * https://stackoverflow.com/questions/9697589/need-help-understanding-a-posted-algorithm-calculating-step-distance-between-he/9698940
	 * @param c the coordinate that is being travelled to
	 * @return the distance from one hex coordinate to another
	 */
	public static int calcHexDistance(int x1, int y1, int x2, int y2) {
	    int dx=x2-x1;
	    int dy=y2-y1;

	    if (dx*dy>0) {
	        return Math.abs(dx)+Math.abs(dy);
	    } else {
	        return Math.max(Math.abs(dx),Math.abs(dy));
	    }
	}
}