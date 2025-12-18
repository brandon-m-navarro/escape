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
import escape.PathFinder.*;
import escape.board.*;
import escape.board.coordinate.*;
// import escape.exception.EscapeException;
import escape.piece.*;

/**
 * This class is the game manager for an Escape game that is
 * played on a SquareBoard
 * 
 * @version Dec 2025
 */
public class SquareEscapeGameManager extends TwoDimensionalEscapeGameManager {

	SquareEscapeGameManager(TwoDimensionalBoard b, Map<PieceName, MovementRules> rules) {
		super(b, rules);
	}

	/*
	 * @see escape.EscapeGameManager#move(escape.board.coordinate.Coordinate,
	 * escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean move(Coordinate from, Coordinate to) {
		// Validate coordinate types first
		if (!(from instanceof SquareCoordinate) || !(to instanceof SquareCoordinate)) {
			return false;
			// throw new IllegalArgumentException("SquareEscapeGameManager only accepts SquareCoordinate");
		}

		SquareCoordinate squareFrom = (SquareCoordinate) from;
		SquareCoordinate squareTo = (SquareCoordinate) to;

		boolean validMove = false;
		if (isBasicMove(squareFrom, squareTo)) {
			EscapePiece p = getPieceAt(from);
			switch (getMovementRulesFor(p.getName()).getMovementPattern()) {
				case LINEAR:
					validMove =
						canMoveLinear(squareFrom, squareTo, getMovementRulesFor(p.getName()));
					break;
				case DIAGONAL:
					validMove =
						canMoveDiagonally(squareFrom, squareTo, getMovementRulesFor(p.getName()));
					break;
				case ORTHOGONAL:
					validMove =
						canMoveOrthogonally(squareFrom, squareTo, getMovementRulesFor(p.getName()));
					break;
				case OMNI:
					validMove =
						canMoveOmni(squareFrom, squareTo, getMovementRulesFor(p.getName()));
					break;
				default:
					return false;
			}
		}

		// If the move is valid, move the Piece
		if (validMove) {
			if (board.isExit(squareTo)) {
				board.putPieceAt(null, squareFrom);
			}
			else {
				board.movePiece(squareFrom, squareTo);
			}
		}

		return validMove;
	}

	/**
	 * This method tests that the given move is possible following
	 * LINEAR constraints
	 * 
	 * @param from          the starting coordinate of the piece
	 * @param to            the coordinate the piece is attempting to move to
	 * @param movementRules the movement rules for the piece moving
	 * @return true if the piece is able to make the move
	 */
	private boolean canMoveLinear(SquareCoordinate from, SquareCoordinate to, MovementRules movementRules) {
		if (!isLinearMovement(from, to)) {
			return false;
		} else if (from.distanceTo(to) > movementRules.getMaxDistance()) {
			return false;
		}

		Vector<SquareCoordinate> coordVector = makeLinearCoordinateVector(from, to);
		for (SquareCoordinate coord : coordVector) {
			if (coord == coordVector.lastElement()) {
				if (doesPieceExistAt(coord)) {
					if (!isSameTeam(getPieceAt(from), getPieceAt(to))) {
						return true; // Need to remove piece from board
					} else {
						return false;
					}
				} else if (board.isBlocked(coord)) {
					return false;
				} else {
					return true;
				}
			} else if (movementRules.isCanFly()) {
				return true;
			} else if (getPieceAt(coord) == null && !board.isBlocked(coord)) {
				continue;
			} else if (getPieceAt(coord) == null && board.isBlocked(coord)) {
				if (movementRules.isCanTravelThroughBlocked()) {
					continue;
				} else {
					return false; // BLOCKED
				}
			} else if (doesPieceExistAt(coord) && movementRules.isCanJump()) {
				if (canMakeValidLinearJump(coord, to, getPieceAt(from))) {
					continue;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method tests that the given move is possible following
	 * DIAGONAL constraints
	 * 
	 * @param from          the starting coordinate of the piece
	 * @param to            the coordinate the piece is attempting to move to
	 * @param movementRules the movement rules for the piece moving
	 * @return true if the piece is able to make the move
	 */
	private boolean canMoveDiagonally(SquareCoordinate from, SquareCoordinate to, MovementRules movementRules) {
		if (from.distanceTo(to) > movementRules.getMaxDistance()) {
			return false;
		}
		PathFinder pathFinder = new PathFinder(board, new PathFinderNode(from), movementRules);
		pathFinder.searchDiagonally(from, to, movementRules);
		// Vector<TwoDimensionalCoordinate> path = pathFinder.recreatePath();
		return (
			pathFinder.isCompleted() &&
			pathFinder.getDistanceTravelled() <= movementRules.getMaxDistance()
		);
	}

	/**
	 * This method returns true if the piece can make a valid jump. This
	 * method will return true if attempting to jump into an enemy piece
	 * 
	 * @param start where the piece is starting its jump
	 * @param to    the ending coordinate
	 * @param piece the moving piece
	 * @return true if the piece can make a valid jump
	 */
	private boolean canMakeValidLinearJump(SquareCoordinate start, SquareCoordinate to, EscapePiece piece) {
		Vector<SquareCoordinate> coordVector = makeLinearCoordinateVector(start, to);

		for (int i = 0; i < 1;) { // we're on a piece, check the next spot
			if (doesPieceExistAt(coordVector.get(i)) && coordVector.get(i).equals(to)) {
				return !isSameTeam(piece, getPieceAt(coordVector.get(i)));
			}
			else if (doesPieceExistAt(coordVector.get(i))) {
				return false;
			}
			else if (board.isBlocked(coordVector.get(i))) {
				return false;
			}
			else {
				return true;
			}
		}
		return true;
	}

	/**
	 * This method determines if a move is strictly linear
	 * 
	 * @param from the starting coordinate
	 * @param to   the ending coordinate
	 * @return true if the move is linear
	 */
	private boolean isLinearMovement(SquareCoordinate from, SquareCoordinate to) {
		return (getLinearDirection(from, to) != null);
	}

	/**
	 * Create a Coordinate Vector that contains all the Coordinates
	 * given a starting and ending Coordinate. This method can only make
	 * vectors that follow the Direction enum below
	 * 
	 * @param c1 the starting Coordinate
	 * @param c2 the ending Coordinate
	 * @return a Vector of Coordinates included from c1 and c2
	 */
	private static Vector<SquareCoordinate> makeLinearCoordinateVector(SquareCoordinate c1, SquareCoordinate c2) {
		Vector<SquareCoordinate> result = new Vector<SquareCoordinate>();
		int x = c1.getX();
		int y = c1.getY();
		switch (getLinearDirection(c1, c2)) {
			case UP:
				for (int i = 1; i <= c1.distanceTo(c2); i++)
					result.add(SquareCoordinate.makeCoordinate(++x, y));
				break;
			case DOWN:
				for (int i = 1; i <= c1.distanceTo(c2); i++)
					result.add(SquareCoordinate.makeCoordinate(--x, y));
				break;
			case RIGHT:
				for (int i = 1; i <= c1.distanceTo(c2); i++)
					result.add(SquareCoordinate.makeCoordinate(x, ++y));
				break;
			case LEFT:
				for (int i = 1; i <= c1.distanceTo(c2); i++)
					result.add(SquareCoordinate.makeCoordinate(x, --y));
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
	 * This is a helper method that creates a vector that holds the
	 * appropriate coordinates for a move that is diagonal
	 * 
	 * @param
	 */
	private static Vector<SquareCoordinate> calcDiagonal(SquareCoordinate c1, SquareCoordinate c2) {
		Vector<Direction> orientation = calcDiagonalOrientation(c1, c2);
		Vector<SquareCoordinate> result = new Vector<SquareCoordinate>();
		int x = c1.getX();
		int y = c1.getY();
		if (orientation.contains(Direction.UP) && orientation.contains(Direction.LEFT)) {
			for (int i = 1; i <= c1.distanceTo(c2); i++) {
				result.add(SquareCoordinate.makeCoordinate(++x, --y));
			}
		} else if (orientation.contains(Direction.UP) && orientation.contains(Direction.RIGHT)) {
			for (int i = 1; i <= c1.distanceTo(c2); i++) {
				result.add(SquareCoordinate.makeCoordinate(++x, ++y));
			}
		} else if (orientation.contains(Direction.DOWN) && orientation.contains(Direction.RIGHT)) {
			for (int i = 1; i <= c1.distanceTo(c2); i++) {
				result.add(SquareCoordinate.makeCoordinate(--x, ++y));
			}
		} else {
			for (int i = 1; i <= c1.distanceTo(c2); i++) {
				result.add(SquareCoordinate.makeCoordinate(--x, --y));
			}
		}
		return result;
	}

	/**
	 * This method uses Coordinate values to determine the
	 * orientation of the diagonal in standard square boards
	 * 
	 * @param c1 the Coordinate that we start from
	 * @param c2 the Coordinate we are ending on
	 * @return a vector of Directions
	 */
	private static Vector<Direction> calcDiagonalOrientation(SquareCoordinate c1, SquareCoordinate c2) {
		Vector<Direction> v = new Vector<Direction>();
		if ((c1.getX() - c2.getX()) < 0) {
			v.add(Direction.UP);
		} else {
			v.add(Direction.DOWN);
		}
		if ((c1.getY() - c2.getY()) < 0) {
			v.add(Direction.RIGHT);
		} else {
			v.add(Direction.LEFT);
		}
		return v;
	}

	/**
	 * This method returns the apporpriate Direction enum given a two
	 * Coordinates. Returns null if not a linear direction
	 * 
	 * @param c1 the first Coordinate
	 * @param c2 the second Coordinate
	 * @return a Direction enum @see below
	 */
	private static Direction getLinearDirection(TwoDimensionalCoordinate c1, TwoDimensionalCoordinate c2) {
		if (isDiagonal(c1, c2)) {
			return Direction.DIAGONAL;
		} else if ((c1.getX() == c2.getX()) && (c1.getY() > c2.getY())) {
			return Direction.LEFT;
		} else if ((c1.getX() == c2.getX()) && (c1.getY() < c2.getY())) {
			return Direction.RIGHT;
		} else if ((c1.getX() > c2.getX()) && (c1.getY() == c2.getY())) {
			return Direction.DOWN;
		} else if ((c1.getX() < c2.getX()) && (c1.getY() == c2.getY())) {
			return Direction.UP;
		} else {
			return null;
		}
	}

	/**
	 * Determines whether or not the two coordinates are diagonal from
	 * each other by examining if the rows for both coordinates differ,
	 * and the columns for both coordinates differ. If both coordinates are
	 * the same, they are not diagonal from one another
	 * 
	 * @param c1 the first coordinate
	 * @param c2 the second coordinate
	 * @return boolean on if the two cooordinates are diagonal from each other
	 */
	private static boolean isDiagonal(TwoDimensionalCoordinate c1, TwoDimensionalCoordinate c2) {
		return (c1.getY() - c2.getY() == c1.getX() - c2.getX() ||
				c1.getY() - c2.getY() == c2.getX() - c1.getX());
	}

}
