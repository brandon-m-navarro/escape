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

import static escape.board.coordinate.CoordinateID.*;
import static org.junit.Assert.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 * Tests for various coordinates
 * @version Mar 28, 2020
 */
class MasterCoordinateTest
{
    @ParameterizedTest
    @MethodSource("distanceTestProvider")
    void distanceTests(String n, Coordinate c1, Coordinate c2, int expected)
    {
        assertEquals(n, expected, c1.distanceTo(c2));
    }
   
    static Stream<Arguments> distanceTestProvider()
    {
        return Stream.of(
        	// Square coordinates
            Arguments.of("#1", mc(SQUARE, 4, 4), mc(SQUARE, 5, 4), 1),
            Arguments.of("#2", mc(SQUARE, 1, 1), mc(SQUARE, 3, 1), 2),
            Arguments.of("#3", mc(SQUARE, 4, 4), mc(SQUARE, 3, 4), 1),
            Arguments.of("#4", mc(SQUARE, 4, 4), mc(SQUARE, 4, 3), 1),
            Arguments.of("#5", mc(SQUARE, 4, 4), mc(SQUARE, 4, 5), 1),
            Arguments.of("#6", mc(SQUARE, 4, 4), mc(SQUARE, 5, 5), 1),
            Arguments.of("#7", mc(SQUARE, 4, 4), mc(SQUARE, 5, 3), 1),
            Arguments.of("#8", mc(SQUARE, 4, 4), mc(SQUARE, 3, 3), 1),
            Arguments.of("#9", mc(SQUARE, 4, 4), mc(SQUARE, 3, 5), 1),
            Arguments.of("#10", mc(SQUARE, 5, 4), mc(SQUARE, 25, 5), 20),
            Arguments.of("#11", mc(SQUARE, 25, 5), mc(SQUARE, 5, 4), 20),
            Arguments.of("#12", mc(SQUARE, 25, 5), mc(SQUARE, 25, 5), 0),
            Arguments.of("#13", mc(SQUARE, 5, 4), mc(SQUARE, 25, 4), 20),
            Arguments.of("#14", mc(SQUARE, 5, 4), mc(SQUARE, 1, 6), 4),
            Arguments.of("#25", mc(SQUARE, -5, 4), mc(SQUARE, 4, -6), 10),
            
            // OrthoSquare coordinates
            Arguments.of("#15", mc(ORTHOSQUARE, 4, 4), mc(ORTHOSQUARE, 5, 4), 1),
            Arguments.of("#16", mc(ORTHOSQUARE, 6, 6), mc(ORTHOSQUARE, 4, 4), 4),
            Arguments.of("#17", mc(ORTHOSQUARE, 6, 6), mc(ORTHOSQUARE, 8, 4), 4),
            Arguments.of("#18", mc(ORTHOSQUARE, 6, 6), mc(ORTHOSQUARE, 4, 8), 4),
            Arguments.of("#19", mc(ORTHOSQUARE, 6, 6), mc(ORTHOSQUARE, 8, 8), 4),
            Arguments.of("#20", mc(ORTHOSQUARE, 6, 6), mc(ORTHOSQUARE, 6, 6), 0),
            Arguments.of("#26", mc(ORTHOSQUARE, -6, 6), mc(ORTHOSQUARE, 6, -2), 20),
            // Hex coordinates
            Arguments.of("#21", mc(HEX, 0, 0), mc(HEX, 0, 1), 1),
            Arguments.of("#22", mc(HEX, -3, -1), mc(HEX, 1, 2), 7),
            Arguments.of("#23", mc(HEX, -4, 5), mc(HEX, 4, -3), 8),
            Arguments.of("#24", mc(HEX, -4, 5), mc(HEX, 4, -4), 9)
        );
    }
    
    /**
     * Helper to make a coordinate
     * @param type the type of coordinate
     * @param x
     * @param y
     * @return the coordinate instance
     */
    private static Coordinate mc(CoordinateID type, int x, int y)
    {
        return type == SQUARE ? SquareCoordinate.makeCoordinate(x, y)
        	: type == ORTHOSQUARE ? OrthoSquareCoordinate.makeCoordinate(x, y)
        	: HexCoordinate.makeCoordinate(x, y);
    }
}
