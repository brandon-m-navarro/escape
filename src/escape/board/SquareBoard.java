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
package escape.board;

import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * This board has square coordinates and finite bounds, represented by xMax and yMax
 * that are extended from an abstract class, TwoDimensionalBoard. Because a SquareBoard
 * has boundries, we need to account for this when placing a piece on the board.
 * @version Apr 2, 2020
 */
public class SquareBoard extends TwoDimensionalBoard
{
	
	public SquareBoard(int xMax, int yMax)
	{
		super(xMax, xMax);
	}
	
	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, TwoDimensionalCoordinate coord)
	{
		if(isExit(coord))
			return;	 	// Do nothing, don't place piece!
		else if (isWithinBoundries(coord) && !isBlocked(coord))
			pieces.put(coord, p);
		else
			throw new EscapeException("ERROR: invalid coordinate!");
	}

	/**
	 * This method checks whether the given coordinate is within the
	 * specified boundries
	 * @param coord the coordinate that is being checked
	 * @return true if it is within the boards xMax & yMax
	 */
	private boolean isWithinBoundries(TwoDimensionalCoordinate coord)
	{
		SquareCoordinate c = (SquareCoordinate) coord;
		return (c.getX() <= xMax && c.getY() <= yMax);
	}
}
