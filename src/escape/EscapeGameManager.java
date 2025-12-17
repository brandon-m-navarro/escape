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
package escape;

import escape.board.coordinate.Coordinate;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * This interface describes the behavior of the Escape game controller. Every
 * instance
 * of an Escape game controller must implement this interface. The methods
 * described here are
 * the only methods that a client can use when interacting with the game.
 * 
 * @version Mar 27, 2020
 */
public interface EscapeGameManager<C extends Coordinate> {
	/**
	 * Make the move in the current game.
	 * 
	 * @param from starting location
	 * @param to   ending location
	 * @return true if the move was legal, false otherwise
	 */
	boolean move(C from, C to);

	/**
	 * Return the piece located at the specified coordinate. If executing
	 * this method in the game instance causes an exception, then this method
	 * returns null and sets the status message appropriately.
	 * 
	 * @param coordinate the location to probe
	 * @return the piece at the specified location or null if there is none
	 */
	EscapePiece getPieceAt(C coordinate);

	/**
	 * Returns a coordinate of the appropriate type. If the coordinate cannot be
	 * created, then null is returned and the status message is set appropriately.
	 * 
	 * @param x the x component
	 * @param y the y component
	 * @return the coordinate or null if the coordinate cannot be
	 */
	C makeCoordinate(int x, int y);

	/**
	 * Add an observer to this manager. Whever the move() method returns
	 * false, the observer will be notified with a message indication the
	 * problem.
	 * 
	 * @param observer
	 * @return the observer
	 */
	default GameObserver addObserver(GameObserver observer) {
		throw new EscapeException("Not implemented");
	}

	/**
	 * Remove an observer from this manager. The observer will no longer
	 * receive notifications from this game manager.
	 * 
	 * @param observer
	 * @return the observer that was removed or null if it had not previously
	 *         been registered
	 */
	default GameObserver removeObserver(GameObserver observer) {
		throw new EscapeException("Not implemented");
	}
}
