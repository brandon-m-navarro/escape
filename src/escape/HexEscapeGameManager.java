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
public class HexEscapeGameManager extends TwoDimensionalEscapeGameManager
{

	HexEscapeGameManager(TwoDimensionalBoard b, Map<PieceName, MovementRules> rules)
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
		if (isBasicMove((HexCoordinate)from, (HexCoordinate)to))
		{
			EscapePiece p = getPieceAt(from);
			switch (getMovementRulesFor(p.getName()).getMovementPattern())
			{
				case LINEAR:
					validMove = canMoveLinear((HexCoordinate) from, (HexCoordinate) to, 
							   getMovementRulesFor(p.getName()));
					break;
				case OMNI:
					validMove = canMoveOmni((HexCoordinate) from, (HexCoordinate) to,
							getMovementRulesFor(p.getName()));
					break;
				case DIAGONAL:
					return false;
				case ORTHOGONAL:
					validMove = false;
				default:
					validMove =  false;
			}
		}
		if (validMove)
		{
			if (board.isExit((HexCoordinate) to))
				board.putPieceAt(null, (HexCoordinate) from);
			else
				board.movePiece((HexCoordinate)from, (HexCoordinate)to);
		}
		
		return validMove;
	}

	/**
	 * This method tests that the given move is possible following
	 * LINEAR constraints
	 * @param from the starting coordinate of the piece
	 * @param to the coordinate the piece is attempting to move to
	 * @param movementRules the movement rules for the piece moving
	 * @return true if the piece is able to make the move
	 */
	private boolean canMoveLinear(HexCoordinate from, HexCoordinate to,
			MovementRules movementRules)
	{
		if (!isLinearMovement(from, to))
			return false;
		else if (from.distanceTo(to) > movementRules.getMaxDistance())
			return false;
		Vector<HexCoordinate> coordVector = createLinearCoordinateVector(from, to);
		for (HexCoordinate coord : coordVector)
		{
			if (coord == coordVector.lastElement())
			{
				if (doesPieceExistAt(coord))
				{
					if (!isSameTeam(getPieceAt(from), getPieceAt(to)))
						return true;	// Need to remove piece from board
					else
						return false;
				}
				else if (board.isBlocked(coord))
					return false;
				else
					return true;
			}
			else if (movementRules.isCanFly())
				return true;
			else if (getPieceAt(coord) == null && !board.isBlocked(coord))
				continue;
			else if (getPieceAt(coord) == null && board.isBlocked(coord))
			{
				if (movementRules.isCanTravelThroughBlocked())
					continue;
				else
					return false;	// BLOCKED
			}
			else if (doesPieceExistAt(coord) && movementRules.isCanJump())
			{
				if (canMakeValidLinearJump(coord, to, getPieceAt(from)))
					continue;
				else
					return false;
			}
			else
				return false;
		}
		return true;
	}

	/**
	 * This method returns true if the piece can make a valid jump. This
	 * method will return true if attempting to jump into an enemy piece 
	 * @param start where the piece is starting its jump
	 * @param to the ending coordinate
	 * @param piece the moving piece
	 * @return true if the piece can make a valid jump
	 */
	private boolean canMakeValidLinearJump(HexCoordinate start, HexCoordinate to, EscapePiece piece)
	{
		Vector<HexCoordinate> coordVector = createLinearCoordinateVector(start, to);

		for (int i = 0; i < 1;) // we're on a piece, check the next spot
		{
			if (doesPieceExistAt(coordVector.get(i)) && coordVector.get(i).equals(to))
				return !isSameTeam(piece, getPieceAt(coordVector.get(i)));
			else if (doesPieceExistAt(coordVector.get(i)))
				return false;
			else if (board.isBlocked(coordVector.get(i)))
				return false;
			else
				return true;
		}
		return true;
	}

	/**
	 * This method return trua if c2 can be reached from c1
	 * while going in a single linear move
	 * @param from the starting coordinate
	 * @param to the ending coordinate
	 * @return true if the move is linear
	 */
	private boolean isLinearMovement(HexCoordinate from, HexCoordinate to)
	{
		Vector <HexCoordinate> result = createLinearCoordinateVector(from, to);
		if (result.isEmpty())
			return false;
		else
			return result.lastElement().equals(to);
	}

	/**
	 * Create a Coordinate Vector that contains all the Coordinates
	 * given a starting and ending Coordinate. This method can only make 
	 * vectors that follow the Direction enum below.
	 * @param c1 the starting Coordinate
	 * @param c2 the ending Coordinate
	 * @return a Vector of Coordinates included from c1 and c2 if one can be created
	 */
	private static Vector<HexCoordinate> createLinearCoordinateVector(HexCoordinate c1, HexCoordinate c2) 
	{
		Vector<HexCoordinate> result = new Vector<>();
		int x = c1.getX();
		int y = c1.getY();
		switch (calcLinearDirection(c1, c2))
		{
			case UP:
				// We need to check whether we can reach c2 from c1 with some repeated operation
				for (int i = 0; i < c1.distanceTo(c2); i++)
					result.add(HexCoordinate.makeCoordinate(x, ++y));
				break;
			case DOWN:
				for (int i = 0; i < c1.distanceTo(c2); i++)
					result.add(HexCoordinate.makeCoordinate(x, --y));
				break;
			case DIAGONAL:
				result = calcDiagonal(c1, c2);
				break;
			default:
				break;
		}
		return result;
	}
	
	/**
	 * This method returns a Direction of the two coordinates
	 * @param c1 the Coordinate that we start from
	 * @param c2 the Coordinate we are ending on
	 * @return a vector of Directions
	 */
	private static Direction calcLinearDirection(TwoDimensionalCoordinate c1, TwoDimensionalCoordinate c2)
	{
		if ((c1.getX() == c2.getX()) && (c1.getY() > c2.getY()))
			return Direction.DOWN;
		else if ((c1.getX() == c2.getX()) && (c1.getY() < c2.getY()))
			return Direction.UP;
		else
			return Direction.DIAGONAL;
	}
	
	/**
	 * This is a helper method that creates a vector that holds the 
	 * appropriate coordinates for a move that is diagonal
	 * @param
	 */
	private static Vector<HexCoordinate> calcDiagonal(TwoDimensionalCoordinate c1, TwoDimensionalCoordinate c2)
	{
		Vector<Direction> orientation = calcDiagonalDirection(c1, c2);
		Vector<HexCoordinate> result = new Vector<>();
		int x = c1.getX();
		int y = c1.getY();
		if (orientation.contains(Direction.UP) && orientation.contains(Direction.LEFT))
		{
			for (int i = 0; i < c1.distanceTo(c2); i++)
				result.add(HexCoordinate.makeCoordinate(--x, ++y));
		}
		else if (orientation.contains(Direction.UP) && orientation.contains(Direction.RIGHT))
		{
			for (int i = 0; i < c1.distanceTo(c2); i++)
				result.add(HexCoordinate.makeCoordinate(++x, y));
		}
		else if (orientation.contains(Direction.DOWN) && orientation.contains(Direction.LEFT))
		{
			for (int i = 0; i < c1.distanceTo(c2); i++)
				result.add(HexCoordinate.makeCoordinate(--x, y));
		}
		else
		{
			for (int i = 0; i < c1.distanceTo(c2); i++)
				result.add(HexCoordinate.makeCoordinate(++x, --y));
		}
		return result;
	}

	/**
	 * This method returns a Direction vector containing two Direction
	 * enums (UL, UR, LL, LR) or null if non exist
	 * @param c1
	 * @param c2
	 * @return
	 */
	private static Vector<Direction> calcDiagonalDirection(
			TwoDimensionalCoordinate c1, TwoDimensionalCoordinate c2)
	{
		Vector<Direction> result = new Vector<Direction>(2);
		if (c1.getX() > c2.getX() && c1.getY() < c2.getY())
		{
			result.add(Direction.UP);
			result.add(Direction.LEFT);
		}
		else if (c1.getX() < c2.getX() && c1.getY() == c2.getY())
		{
			result.add(Direction.UP);
			result.add(Direction.RIGHT);
		}
		else if (c1.getX() > c2.getX() && c1.getY() == c2.getY())
		{
			result.add(Direction.DOWN);
			result.add(Direction.LEFT);
		}
		else if (c1.getX() < c2.getX() && c1.getY() > c2.getY())
		{
			result.add(Direction.DOWN);
			result.add(Direction.RIGHT);
		}
		else
			throw new EscapeException("Critical failure");
		return result;
	}
}
