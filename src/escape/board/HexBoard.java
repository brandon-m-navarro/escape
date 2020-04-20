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

import escape.board.coordinate.TwoDimensionalCoordinate;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * This board has hex coordinates and infinite bounds, and extends from an abstract class,
 * TwoDimensionalBoard.
 * @version Apr 19, 2020
 */
public class HexBoard extends TwoDimensionalBoard
{

	public HexBoard(int xMax, int yMax)
	{
		super(yMax, yMax);
	}

	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(TwoDimensionalCoordinate coord)
	{
		return pieces.get(coord);
	}
	
	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, TwoDimensionalCoordinate coord)
	{
		if(isExit(coord))
			return;	 	// Do nothing, don't place piece!
		else if (!isBlocked(coord))
			pieces.put(coord, p);
		else
			throw new EscapeException("ERROR: invalid coordinate!");
	}
}
