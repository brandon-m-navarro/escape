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

import java.util.Map;
import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;

/**
 * Description
 * @version May 1, 2020
 */
public class OrthoSquareEscapeGameManager extends TwoDimensionalEscapeGameManager
{
	
	OrthoSquareEscapeGameManager(TwoDimensionalBoard b, Map<PieceName, MovementRules> rules)
	{
		super(b, rules);
	}

	/*
	 * @see escape.EscapeGameManager#move(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean move(Coordinate from, Coordinate to)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * @see escape.EscapeGameManager#makeCoordinate(int, int)
	 */
	@Override
	public Coordinate makeCoordinate(int x, int y)
	{
		OrthoSquareBoard b = (OrthoSquareBoard) board;
		if (b.isValidCoordinate(OrthoSquareCoordinate.makeCoordinate(x, y)))
			return OrthoSquareCoordinate.makeCoordinate(x, y);
		else
			return null;
	}

	/*
	 * @see escape.EscapeGameManager#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(Coordinate coord)
	{
		OrthoSquareBoard b = (OrthoSquareBoard) board;
		OrthoSquareCoordinate c = (OrthoSquareCoordinate) coord;
		if (coord != null && b.isValidCoordinate(OrthoSquareCoordinate.makeCoordinate(c.getX(), c.getY())))
			return board.getPieceAt((TwoDimensionalCoordinate) coord);
		else
			throw new EscapeException("ERROR: invalid coordinate!");
	}
}
