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

package escape.board.coordinate;
import escape.exception.EscapeException;

/**
 * Enum factory for Coordinates.
 * @version Dec 2025
 */
public class CoordinateFactory {

	public CoordinateFactory() {}

	/**
	 * TwoDimensionalCoordinates have an x & y value, and must have a valid
	 * CoordinateID.
	 * @param x - horizontal axis, with the left column being 0
	 * @param y - vertical axis, with the bottom row being 0
	 * @param id - SQUARE, HEX, or ORTHOSQUARE
	 * @return
	 */
	public static TwoDimensionalCoordinate makeCoordinate(int x, int y, CoordinateID id) {
		switch (id) {
			case SQUARE:
				return new SquareCoordinate(x, y);
			case ORTHOSQUARE:
				return new OrthoSquareCoordinate(x, y);
			case HEX:
				return new HexCoordinate(x, y);
		}
		throw new EscapeException("ERROR: Invalid CoordinateID - " + id);
	}
}
