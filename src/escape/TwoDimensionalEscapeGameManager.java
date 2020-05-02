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

import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * This class is a is an abstract implementation of the EscapeGameManager interface
 * that collects all the shared functionality of TwoDimensionalBoard game managers.
 * @version Apr 29, 2020
 */
public abstract class TwoDimensionalEscapeGameManager implements EscapeGameManager
{
	protected TwoDimensionalBoard board;

	TwoDimensionalEscapeGameManager(TwoDimensionalBoard b)
	{
		this.board = b;
	}

	/*
	 * @see escape.EscapeGameManager#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(Coordinate coord)
	{
		if (coord != null)
			return board.getPieceAt((TwoDimensionalCoordinate) coord);
		else
			throw new EscapeException("ERROR: invalid coordinate!");
	}
}
