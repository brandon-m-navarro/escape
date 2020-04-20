/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape.board.coordinate;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 * Tests for various coordinates
 * @version Mar 28, 2020
 */
class CoordinateTest
{
    
	@ParameterizedTest
	@MethodSource("squareDistanceProvider")
	void squareDistanceTests(int expected, int x1, int y1, int x2, int y2) 
	{
		Coordinate c1 = SquareCoordinate.makeCoordinate(x1, y1);
		Coordinate c2 = SquareCoordinate.makeCoordinate(x2, y2);
		assertEquals(expected, c1.distanceTo(c2));
	}
	
	static Stream<Arguments> squareDistanceProvider()
	{
		return Stream.of(
				Arguments.of(2, 3, 4, 3, 6),
				Arguments.of(1, 3, 6, 3, 5),
				Arguments.of(3, 5, 5, 8, 5),
				Arguments.of(4, 8, 5, 4, 5),
				Arguments.of(4, 4, 2, 8, 6),
				Arguments.of(3, 7, 5, 4, 2),
				Arguments.of(1, 3, 7, 2, 6),
				Arguments.of(2, 2, 6, 4, 8),
				Arguments.of(4, 4, 4, 8, 5),
				Arguments.of(3, 4, 4, 1, 5),
				Arguments.of(4, 4, 6, 6, 2),
				Arguments.of(4, 6, 2, 4, 6),
				Arguments.of(4, 5, 6, 2, 2),
				Arguments.of(4, 5, 6, 1, 4),
				Arguments.of(2, 2, 5, 4, 4),
				Arguments.of(3, 4, 7, 2, 4));
	}
	
	 
	@ParameterizedTest
	@MethodSource("orthogonalDistanceProvider")
	void orthogonalDistanceTests(int expected, int x1, int y1, int x2, int y2) 
	{
		Coordinate c1 = OrthoSquareCoordinate.makeCoordinate(x1, y1);
		Coordinate c2 = OrthoSquareCoordinate.makeCoordinate(x2, y2);
		assertEquals(expected, c1.distanceTo(c2));
	}
	
	static Stream<Arguments> orthogonalDistanceProvider()
	{
		return Stream.of(
				Arguments.of(2, 3, 4, 3, 6),
				Arguments.of(1, 3, 6, 3, 5),
				Arguments.of(3, 5, 5, 8, 5),
				Arguments.of(4, 8, 5, 4, 5),
				Arguments.of(8, 4, 2, 8, 6),
				Arguments.of(2, 7, 5, 6, 4),
				Arguments.of(2, 3, 7, 2, 6),
				Arguments.of(4, 2, 6, 4, 8),
				Arguments.of(5, 4, 4, 8, 5),
				Arguments.of(4, 4, 4, 1, 5),
				Arguments.of(6, 4, 6, 6, 2),
				Arguments.of(6, 6, 2, 4, 6),
				Arguments.of(4, 5, 6, 4, 3)
				);
	}
	
	@ParameterizedTest
	@MethodSource("hexDistanceProvider")
	void hexDistanceTests(int expected, int x1, int y1, int x2, int y2) 
	{
		Coordinate c1 = HexCoordinate.makeCoordinate(x1, y1);
		Coordinate c2 = HexCoordinate.makeCoordinate(x2, y2);
		assertEquals(expected, c1.distanceTo(c2));
	}
	
	static Stream<Arguments> hexDistanceProvider()
	{
		return Stream.of(
				Arguments.of(1, 0, 0, -1, 1),
				Arguments.of(1, 0, 0, -1, 0),
				Arguments.of(1, -1, 0, 0, 0),
				Arguments.of(1, 0, 0, 0, -1),
				Arguments.of(1, 0, -1, 0, 0),
				Arguments.of(1, 0, 0, 1, -1),
				Arguments.of(1, 1, -1, 0, 0),
				Arguments.of(1, 0, 0, 1, 0),
				Arguments.of(1, 1, 0, 0, 0),
				Arguments.of(1, 0, 0, 0, 1),
				Arguments.of(1, 0, 1, 0, 0),
				Arguments.of(2, 2, -1, 1, -2),
				Arguments.of(4, -1, -1, 2, 0),
				Arguments.of(2, 2, -1, 1, -2),
				Arguments.of(2, 1, -2, 2, -1),
				Arguments.of(3, 0, 3, 0, 0),
				Arguments.of(3, 0, 0, 0, 3),
				Arguments.of(3, 3, 0, 0, 0),
				Arguments.of(3, 0, 0, 3, 0),
				Arguments.of(3, 0, -3, 0, 0),
				Arguments.of(3, 0, 0, 0, -3),
				Arguments.of(4, -2, -2, 0, 0),
				Arguments.of(5, -1, -1, 0, 3)
				);
	}
}
