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
import escape.board.coordinate.*;
import escape.exception.EscapeException;
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
	protected final CoordinateID coordType;
	
	public TwoDimensionalBoard(int xMax, int yMax, CoordinateID coordType)
	{
		this.xMax = xMax;
		this.yMax = yMax;
		pieces = new HashMap<TwoDimensionalCoordinate, EscapePiece>();
		spots = new HashMap<TwoDimensionalCoordinate, LocationType>();
		this.coordType = null;
	}
	
	public void setLocationType(TwoDimensionalCoordinate c, LocationType lt)
	{
		spots.put(c, lt);
	}
	
	public CoordinateID getCoordType()
	{
		return this.coordType;
	}
	
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(TwoDimensionalCoordinate from)
	{
		if (pieces.containsKey(from))
			return pieces.get(from);
		else
			return null;
	}
	
	/**
	 * This method checks whether the given coordinate was initialized
	 * with a LocationType of BLOCKED
	 * @param coord the Coordinate that is being checked
	 * @return true if the LocationType of the Coordinate is BLOCKED
	 */
	public boolean isBlocked(TwoDimensionalCoordinate coord)
	{
		return spots.get(coord) == LocationType.BLOCK;
	}
	
	/**
	 * This method checks whether the given coordinate was initialized
	 * with a LocationType of EXIT
	 * @param coord the Coordinate that is being checked
	 * @return true if the LocationType of the Coordinate is EXIT
	 */
	public boolean isExit(TwoDimensionalCoordinate coord)
	{
		return spots.get(coord) == LocationType.EXIT;
	}
	
	/**
	 * Check that the given coordinate is on the board
	 * @param coord the coordinate being checked
	 * @return true if the coordinate lies on the board
	 */
	public boolean isValidCoordinate(TwoDimensionalCoordinate coord)
	{
		return ((coord.getX() <= xMax && coord.getY() <= yMax) && (coord.getX() > 0 && coord.getY() > 0));
	}

	/**
	 * This method moves a piece, replacing any piece at that coordinate with the moving
	 * piece. This method DOES NOT check whether or not the move itself is valid!
	 * @throws EscapeException if the from coordinate doesn't contain an EscapePiece
	 * @param from the coordinate the EscapePiece is moving from
	 * @param to the coordinate that the EscapePiece will move to
	 */
	public void movePiece(TwoDimensionalCoordinate from, TwoDimensionalCoordinate to)
	{
		EscapePiece piece = getPieceAt(from);
		if (piece != null)
		{
			piece = getPieceAt(from);
			pieces.put(from, null);
			pieces.put(to, piece);
		}
		else
		{
			throw new EscapeException("Error: could not move piece!");
		}
	}
}
