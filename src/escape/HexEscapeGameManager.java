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
import escape.piece.EscapePiece;

/**
 * Description
 * @version May 1, 2020
 */
public class HexEscapeGameManager extends TwoDimensionalEscapeGameManager
{

	HexEscapeGameManager(TwoDimensionalBoard b)
	{
		super(b);
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
		HexBoard b = (HexBoard) board;
		if (b.isValidCoordinate(HexCoordinate.makeCoordinate(x, y)))
			return HexCoordinate.makeCoordinate(x, y);
		else
			return null;
	}

}
