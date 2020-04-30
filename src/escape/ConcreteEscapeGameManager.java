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
 * This class is a concrete implementation of the EscapeGameManager interface.
 * @version Apr 29, 2020
 */
public class ConcreteEscapeGameManager implements EscapeGameManager
{
	private Board<Coordinate> board;

	ConcreteEscapeGameManager(Board b)
	{
		this.board = b;
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

	/**
	 * Return the piece located at the specified coordinate. If executing
	 * this method in the game instance causes an exception, then this method
	 * returns null and sets the status message appropriately.
	 * @param coordinate the location to probe
	 * @return the piece at the specified location or null if there is none
	 */
	@Override
	public EscapePiece getPieceAt(Coordinate coordinate)
	{
		return board.getPieceAt(coordinate);
	}

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
		if (board.getClass() == (SquareBoard.class))
			return SquareCoordinate.makeCoordinate(x, y);
		else if (board.getClass() == OrthoSquareBoard.class)
			return OrthoSquareCoordinate.makeCoordinate(x, y);
		else if (board.getClass() == HexBoard.class)
			return HexCoordinate.makeCoordinate(x, y);
		else
			return null;
	}

}
