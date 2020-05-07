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
import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;

/**
 * This class is a is an abstract implementation of the EscapeGameManager interface
 * that collects all the shared functionality of TwoDimensionalBoard game managers.
 * @version Apr 29, 2020
 */
public abstract class TwoDimensionalEscapeGameManager implements EscapeGameManager
{
	protected TwoDimensionalBoard board;
	protected Map<PieceName, MovementRules> rules;

	TwoDimensionalEscapeGameManager(TwoDimensionalBoard b, Map<PieceName, MovementRules> rules)
	{
		this.board = b;
		this.rules = new HashMap<PieceName, MovementRules>(rules);
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
			throw new EscapeException("ERROR: Invalid coordinate!");
	}
	
	/**
	 * This method return true if the both pieces are of the same
	 * player
	 * @param p1 the piece being compared to p2
	 * @param p2 the piece being compared to p1
	 * @return true if both pieces are of the same player 
	 */
	protected boolean isSameTeam(EscapePiece p1, EscapePiece p2)
	{
		return p1.getPlayer() == p2.getPlayer();
	}
	
	/**
	 * This method returns the MovementRules of the specified PieceName
	 * @param pieceName the PieceName enum of the piece
	 * @return the MovementRules associated 
	 * @throws EscapeException if that PieceName was not initialized
	 */
	protected MovementRules getMovementRulesFor(PieceName pieceName)
	{
		try
		{
			rules.get(pieceName);
		}
		catch(Exception e)
		{
			throw new EscapeException("ERROR: PieceName was never given a PieceType");

		}
		return rules.get(pieceName);
	}
}
