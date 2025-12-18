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

import static escape.board.LocationType.CLEAR;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * This board has square coordinates and finite bounds, represented by xMax and yMax
 * that are extended from an abstract class, TwoDimensionalBoard. Because a SquareBoard
 * has boundries, we need to account for this when placing a piece on the board.
 * As a default, all spots on a SquareBoard are set to CLEAR.
 * @version Apr 2, 2020
 */
public class SquareBoard extends TwoDimensionalBoard
{
	
	/**
	 * The constructor for a SquareBoard simply takes its x & y 
	 * constraints and sets all tbe contained spots to CLEAR.
	 * @param xMax
	 * @param yMax
	 */
	public SquareBoard(int xMax, int yMax)
	{
		super(xMax, yMax);
		initializeEmptyBoard();
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
			throw new EscapeException("ERROR: Could not place piece - " + p + " at the specified coodinates - "  + coord);
	}

	/**
	 * This method checks whether the given coordinate is within the
	 * specified boundries
	 * @param coord the coordinate that is being checked
	 * @return true if it is within the boards xMax & yMax
	 */
	public boolean isWithinBoundries(TwoDimensionalCoordinate coord)
	{
		SquareCoordinate c = (SquareCoordinate) coord;
		return (c.getX() <= xMax && c.getY() <= yMax);
	}
	
	
	/**
	 * This method intializes all the spots on a board to CLEAR to account 
	 * for lazy configuration boards.
	 */
	private void initializeEmptyBoard()
	{
		for (int x = 0; x <= xMax; x++)
		{
			for (int y = 0; y <= yMax; y++)
			{
				this.setLocationType(SquareCoordinate.makeCoordinate(x, y), CLEAR);
			}
		}
	}

	/*
	 * @see escape.board.Board#getCoordType()
	 */
	@Override
	public CoordinateID getCoordType()
	{
		return CoordinateID.SQUARE;
	}
	
	
}

