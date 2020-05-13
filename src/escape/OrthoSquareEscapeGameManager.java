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
				default:
					return false;
			}
		}
		
		if (validMove)
		{
			if (board.isExit((OrthoSquareCoordinate) to))
				board.putPieceAt(null, (OrthoSquareCoordinate) from);
			else
				board.movePiece((OrthoSquareCoordinate)from, (OrthoSquareCoordinate)to);
		}
		
		return validMove;
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
	
	/**
	 * This method performs basic move checks to basic rules of movement are
	 * being followed. More specifically: 
	 * 		- if a piece exists to move
	 * 		- that it's moving somewhere on the board
	 * @return true if the piece can theoretically move
	 */
	private boolean isBasicMove(OrthoSquareCoordinate from, OrthoSquareCoordinate to)
	{
		OrthoSquareBoard b = (OrthoSquareBoard) board;
		if (doesPieceExistAt(from) && (from != null && to != null) && !board.isBlocked(to))
		{
			if (doesPieceExistAt(to) && (from != null && to != null) && b.isWithinBoundries(to))
				return !isSameTeam(getPieceAt(from), getPieceAt(to));
			else
				return b.isWithinBoundries(to);
		}
		else
			return false;
	}
	
	/**
	 * This method tests that the given move is possible following
	 * ORTHOGONAL constraints
	 * @param from the starting coordinate of the piece
	 * @param to the coordinate the piece is attempting to move to
	 * @param movementRules the movement rules for the piece moving
	 * @return true if the piece is able to make the move
	 */
	private boolean canMoveOrthogonally(OrthoSquareCoordinate from, OrthoSquareCoordinate to,
			MovementRules movementRules)
	{
		if (from.distanceTo(to) > movementRules.getMaxDistance())
			return false;
		PathFinder pathFinder = new PathFinder(board, new PathFinderNode(from), movementRules);
		pathFinder.searchOrthogonally(from, to, movementRules);
		return (pathFinder.isCompleted() && 
				pathFinder.getDistanceTravelled() <= movementRules.getMaxDistance());		
	}
}
