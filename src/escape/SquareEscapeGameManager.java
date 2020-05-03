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

package escape;

import static org.junit.Assume.assumeNoException;
import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * This class is the game manager for an Escape game that is
 * played on a SquareBoard
 * @version May 1, 2020
 */
public class SquareEscapeGameManager extends TwoDimensionalEscapeGameManager
{

	SquareEscapeGameManager(TwoDimensionalBoard b)
	{
		super(b);
	}

	/*
	 * @see escape.EscapeGameManager#move(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean move(Coordinate from, Coordinate to)
	{		
		EscapePiece ep;
		// Need to first verify that a piece exists on the from coordinate
		try {
			ep = getPieceAt(from);
		} catch (EscapeException e) {
			return false;
		}
		
		ep.getName();
		return false;
	}

	/**
	 * This method sets an EscapePiece if given a valid coordinate.
	 * @throws EscapeException
	 * @return the escape piece 
	 */

	
	/**
	 * Returns a coordinate of the appropriate type. If the coordinate cannot be
	 * created, then null is returned and the status message is set appropriately.
	 * @param x the x component
	 * @param y the y component
	 * @return the coordinate or null if the coordinate cannot be 
	 */
	@Override
	public Coordinate makeCoordinate(int x, int y)
	{
		SquareBoard b = (SquareBoard) board;
		if (b.isValidCoordinate(SquareCoordinate.makeCoordinate(x, y)))
			return SquareCoordinate.makeCoordinate(x, y);
		else
			return null;
	}
	
	/*
	 * @see escape.EscapeGameManager#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(Coordinate coord)
	{
		SquareBoard b = (SquareBoard) board;
		SquareCoordinate c = (SquareCoordinate) coord;
		if (coord != null && b.isValidCoordinate(SquareCoordinate.makeCoordinate(c.getX(), c.getY())))
			return board.getPieceAt((TwoDimensionalCoordinate) coord);
		else
			throw new EscapeException("ERROR: invalid coordinate!");
	}

}
