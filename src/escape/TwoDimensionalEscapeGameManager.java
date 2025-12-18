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
 * This class is a is an abstract implementation of the EscapeGameManager
 * interface
 * that collects all the shared functionality of TwoDimensionalBoard game
 * managers.
 * 
 * @version Dec 2025
 */
public abstract class TwoDimensionalEscapeGameManager implements EscapeGameManager<Coordinate> {
	protected TwoDimensionalBoard board;
	protected Map<PieceName, MovementRules> rules;

	TwoDimensionalEscapeGameManager(TwoDimensionalBoard b, Map<PieceName, MovementRules> rules) {
		this.board = b;
		this.rules = new HashMap<PieceName, MovementRules>(rules);
	}

	/*
	 * @see escape.EscapeGameManager#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(Coordinate coord) {
		validateCoordinate(coord);
		TwoDimensionalCoordinate c = (TwoDimensionalCoordinate) coord;
		boolean isValidCoord = board.isValidCoordinate(
			CoordinateFactory.makeCoordinate(
				c.getX(),
				c.getY(),
				board.getCoordType()
			)
		);

		if (isValidCoord) {
			return board.getPieceAt(c);
		} else {
			throw new EscapeException("ERROR: invalid coordinate!");
		}
	}

	/**
	 * Ensure the specified coordinate is not NULL and is a TwoDimensionalCoordinate
	 * @param coordinate
	 * @throws EscapeException - If NULL or not instance of TwoDimensionalCoordinate
	 */
	private void validateCoordinate(Coordinate coordinate) {
		if (coordinate == null) {
			// throw new EscapeException("ERROR: Coordinate cannot be null!");
			throw new EscapeException("ERROR: invalid coordinate!"); // A Coordinate is null if it cannot be created, need to improve error handling to be more specific (eg: Coordinate does not exist on board, etc)
		}
		if (!(coordinate instanceof TwoDimensionalCoordinate)) {
			throw new EscapeException(
				"ERROR: Getting a piece from a TwoDimensionalBoard requires " +
				"the use of a TwoDimensionalCoordinate!"
			);
		}
	}

	/**
	 * Returns a coordinate of the appropriate type. If the coordinate cannot be
	 * created, then null is returned and the status message is set appropriately.
	 * 
	 * @param x the x component
	 * @param y the y component
	 * @return the coordinate or null if the coordinate cannot be
	 */
	public Coordinate makeCoordinate(int x, int y) {
		if (board.isValidCoordinate(CoordinateFactory.makeCoordinate(x, y, board.getCoordType()))) {
			return CoordinateFactory.makeCoordinate(x, y, board.getCoordType());
		}
		else {
			return null;
		}
	}

	/**
	 * This method return true if the both pieces are of the same
	 * player
	 * 
	 * @param p1 the piece being compared to p2
	 * @param p2 the piece being compared to p1
	 * @return true if both pieces are of the same player
	 */
	protected boolean isSameTeam(EscapePiece p1, EscapePiece p2) {
		return p1.getPlayer() == p2.getPlayer();
	}

	/**
	 * This method returns the MovementRules of the specified PieceName
	 * 
	 * @param pieceName the PieceName enum of the piece
	 * @return the MovementRules associated
	 * @throws EscapeException if that PieceName was not initialized
	 */
	protected MovementRules getMovementRulesFor(PieceName pieceName) {
		try {
			rules.get(pieceName);
		} catch (Exception e) {
			throw new EscapeException("ERROR: PieceName was never given a PieceType");

		}
		return rules.get(pieceName);
	}

	/**
	 * This method simply checks if a piece exists at the given coordinate
	 * 
	 * @param coord the coordinate to check
	 * @return true if a piece exists at the given coordinate
	 */
	protected boolean doesPieceExistAt(Coordinate coord) {
		EscapePiece p;
		try {
			p = getPieceAt(coord);
		} catch (EscapeException e) {
			p = null;
		}
		return p != null;
	}

	/**
	 * This method performs basic move checks to basic rules of movement are
	 * being followed. More specifically:
	 * - if a piece exists to move
	 * - that it's moving somewhere on the board
	 * 
	 * @return true if the piece can theoretically move
	 */
	protected boolean isBasicMove(TwoDimensionalCoordinate from, TwoDimensionalCoordinate to) {

		boolean isAPieceMoving = doesPieceExistAt(from);
		boolean doesMoveEndOnPiece = doesPieceExistAt(to);
		boolean isNotMovingToBlockedCoord = !board.isBlocked(to);
		boolean isMoveOnBoard = board.isValidCoordinate(to) && board.isValidCoordinate(from);
System.out.println(isAPieceMoving); 
System.out.println(doesMoveEndOnPiece); 
System.out.println(isNotMovingToBlockedCoord); 
System.out.println(isMoveOnBoard); 


		if (isAPieceMoving && isMoveOnBoard && isNotMovingToBlockedCoord) {

			// If a Piece exists on the ending Coord, the move is only valid if
			// that Piece is on the other team. All pieces have a default value of 1
			if (doesMoveEndOnPiece) {
				boolean arePiecesOnDifferentTeams = !isSameTeam(getPieceAt(from), getPieceAt(to));

				return arePiecesOnDifferentTeams;
			}
			else {
				return board.isValidCoordinate(to);
			}
		} else
			return false;
	}

	/**
	 * This method tests that the given move is possible following
	 * OMNI constraints
	 * 
	 * @param from          the starting coordinate of the piece
	 * @param to            the coordinate the piece is attempting to move to
	 * @param movementRules the movement rules for the piece moving
	 * @return true if the piece is able to make the move
	 */
	protected boolean canMoveOmni(TwoDimensionalCoordinate from, TwoDimensionalCoordinate to,
			MovementRules movementRules) {
		if (from.distanceTo(to) > movementRules.getMaxDistance())
			return false;
		PathFinder pathFinder = new PathFinder(board, new PathFinderNode(from), movementRules);
		pathFinder.searchOmni(from, to, movementRules);
		return (pathFinder.isCompleted() &&
				pathFinder.getDistanceTravelled() <= movementRules.getMaxDistance());
	}

	/**
	 * This method tests that the given move is possible following
	 * ORTHOGONAL constraints
	 * 
	 * @param from          the starting coordinate of the piece
	 * @param to            the coordinate the piece is attempting to move to
	 * @param movementRules the movement rules for the piece moving
	 * @return true if the piece is able to make the move
	 */
	protected boolean canMoveOrthogonally(TwoDimensionalCoordinate from, TwoDimensionalCoordinate to,
			MovementRules movementRules) {
		if (from.distanceTo(to) > movementRules.getMaxDistance())
			return false;
		PathFinder pathFinder = new PathFinder(board, new PathFinderNode(from), movementRules);
		pathFinder.searchOrthogonally(from, to, movementRules);
		return (pathFinder.isCompleted() &&
				pathFinder.getDistanceTravelled() <= movementRules.getMaxDistance());
	}
}
