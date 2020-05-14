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

import java.util.*;
import escape.PathFinder.PathFinderNode;
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
		boolean validMove = false;
		if (isBasicMove((OrthoSquareCoordinate)from, (OrthoSquareCoordinate)to))
		{
			EscapePiece p = getPieceAt(from);
			switch (getMovementRulesFor(p.getName()).getMovementPattern())
			{
				case ORTHOGONAL:
					validMove = canMoveOrthogonally((OrthoSquareCoordinate) from, (OrthoSquareCoordinate) to,
							getMovementRulesFor(p.getName()));
					break;
				case OMNI:
					validMove = canMoveOrthogonally((OrthoSquareCoordinate) from, (OrthoSquareCoordinate) to,
							getMovementRulesFor(p.getName()));
					break;
				case LINEAR:
					validMove = false;
				case DIAGONAL:
					validMove = false;
				default:
					validMove = false;
			}
		}
		
		if (validMove)
		{
			if (board.isExit((OrthoSquareCoordinate) to)) 
			{
				board.putPieceAt(null, (OrthoSquareCoordinate) from);
				return true;
			}
			else
				board.movePiece((OrthoSquareCoordinate)from, (OrthoSquareCoordinate)to);
		}
		
		return validMove;
	}
}
