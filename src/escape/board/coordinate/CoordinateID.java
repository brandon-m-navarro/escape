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
package escape.board.coordinate;

/**
 * Description
 * @version Mar 30, 2020
 */
public enum CoordinateID
{
	// Standard squares where distance is measure as shortest combination of
	// orthogonal and diagonal. Examples: (1,1)->(2,2) is distance 1,
	// (1,2)->(3,5) is distance 3
	SQUARE, 
	// Standard hex coordinates
	// The distance from (0,0) -> (-1, 2) is 2, (-1, 2) -> (2, -2) is 4.
	HEX, 
	// Squares where distance is calculates by the shortest combination of
	// orthogonal paths. Examples: (1,1)->(2,2) is distance 2,
	// (1,2)->(3,5) is distance 5
	ORTHOSQUARE;
}
