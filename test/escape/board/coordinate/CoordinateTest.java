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

import static escape.board.coordinate.SquareCoordinate.makeCoordinate;
import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
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
		assertEquals(expected, makeCoordinate(new SquareCoordinate(x1, y1)).distanceTo(makeCoordinate(new SquareCoordinate(x2, y2))));
	}
	
	static Stream<Arguments> squareDistanceProvider()
	{
		return Stream.of(
				Arguments.of(2, 3, 4, 3, 6)
				);
	}	
    
}
