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

package escape.board;

import java.util.*;
import escape.board.coordinate.TwoDimensionalCoordinate;
import escape.board.coordinate1.*;
import escape.piece.EscapePiece;

/**
 * This class is to hold the commonattributes and functionality of any 
 * board implementation. Each possible board will have a map of spots, 
 * and pieces. Spots represent the location type for each valid coordinate
 * on the board, while pieces represent all the pieces on the board.
 * Each class also shares a xMax and a yMax that represents the boundries
 * of the board. A board that is infinite will have an xMax and yMax of 0.
 * @version Apr 19, 2020
 */
public abstract class TwoDimensionalBoard implements Board<TwoDimensionalCoordinate>
{
	Map<TwoDimensionalCoordinate, LocationType> spots;
	Map<TwoDimensionalCoordinate, EscapePiece> pieces;
	protected final int xMax, yMax;
	
	public TwoDimensionalBoard(int xMax, int yMax)
	{
		this.xMax = xMax;
		this.yMax = yMax;
		pieces = new HashMap<TwoDimensionalCoordinate, EscapePiece>();
		spots = new HashMap<TwoDimensionalCoordinate, LocationType>();
	}
	
	public void setLocationType(TwoDimensionalCoordinate c, LocationType lt)
	{
		spots.put(c, lt);
	}
	
}
