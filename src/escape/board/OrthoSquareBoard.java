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

import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * This board has ortho-square coordinates and finite bounds, represented by xMax and yMax
 * that are extended from an abstract class, TwoDimensionalBoard. Because a OrthoSquareBoard
 * has boundries, we need to account for this when placing a piece on the board.
 * @version Apr 19, 2020
 */
public class OrthoSquareBoard extends TwoDimensionalBoard
{

	public OrthoSquareBoard(int xMax, int yMax)
	{
		super(xMax, yMax);
	}

	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, TwoDimensionalCoordinate coord)
	{
		if(isExit(coord))
			return;	 	// Do nothing, don't place piece
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
	public boolean isWithinBoundries(TwoDimensionalCoordinate coord)
	{
		OrthoSquareCoordinate c = (OrthoSquareCoordinate) coord;
		return (c.getX() <= xMax && c.getY() <= yMax && c.getX() > 0 && c.getY() > 0);
	}

	/*
	 * @see escape.board.Board#getCoordType()
	 */
	@Override
	public CoordinateID getCoordType()
	{
		return CoordinateID.ORTHOSQUARE;
	}
}
