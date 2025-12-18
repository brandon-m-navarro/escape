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
package escape.master;

import static escape.piece.PieceName.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import escape.board.coordinate.HexCoordinate;
import escape.exception.EscapeException;
import escape.piece.PieceName;

/**
 * @version Dec 2025
 */
class MasterHexTest extends AbstractMasterTest<HexCoordinate> {
    /**
     * Create path to xml
     * @throws java.lang.Exception
     */
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        fileName = masterTestLocation + "MasterHexGame.xml";
    }

    /**
     * Valid moves
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param p
     */
    @ParameterizedTest
    @MethodSource("validMoveProvider")
    void validMove(int x1, int y1, int x2, int y2, PieceName p, String test) {
        HexCoordinate c1 = game.makeCoordinate(x1, y1);
        HexCoordinate c2 = game.makeCoordinate(x2, y2);
        assertEquals(test, p, game.getPieceAt(c1).getName());
        assertTrue(test, game.move(c1, c2));
        assertEquals(test, p, game.getPieceAt(c2).getName());
        assertNull(test, game.getPieceAt(c1));
    }

    static Stream<Arguments> validMoveProvider() {
        return Stream.of(
                // Test one in all directions
                Arguments.of(-3, 4, -3, 5, FROG, "move 1"),
                Arguments.of(-3, 4, -2, 4, FROG, "move 1"),
                Arguments.of(-3, 4, -2, 3, FROG, "move 1"),
                Arguments.of(-3, 4, -3, 3, FROG, "move 1"),
                Arguments.of(-3, 4, -4, 4, FROG, "move 1"),
                Arguments.of(-4, 5, -5, 6, FROG, "move 1"),
                // Test > 1 and limits
                Arguments.of(2, 3, 2, -3, FOX, "limit"),
                Arguments.of(-1, 6, 6, 6, HUMMINGBIRD, "limit"),
                Arguments.of(-3, 4, -5, 3, FROG, "limit"),
                // Jump
                Arguments.of(-3, 4, -5, 6, FROG, "jump"),
                // Fly
                Arguments.of(-1, 6, 4, 1, HUMMINGBIRD, "fly"),
                // UNBLOCK
                Arguments.of(2, 3, 8, 3, FOX, "unblock"),
                // Land on opponent
                Arguments.of(2, 3, -1, 6, FOX, "land on opponent"));
    }

    /**
     * Throw an exception or return false for invalid
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param test
     */
    @ParameterizedTest
    @MethodSource("invalidMoveProvider")
    void invalidMove(int x1, int y1, int x2, int y2, String test) {
        HexCoordinate c1 = game.makeCoordinate(x1, y1);
        HexCoordinate c2 = game.makeCoordinate(x2, y2);
        try {
            assertFalse(test, game.move(c1, c2));
        } catch (EscapeException e) {
            assertTrue(test, true);
        }
    }

    static Stream<Arguments> invalidMoveProvider() {
        return Stream.of(
                Arguments.of(2, 3, -4, 5, "land on same player"),
                Arguments.of(-1, 6, 1, 3, "non-linear"),
                Arguments.of(2, 3, 3, 3, "land on block"),
                Arguments.of(-1, 0, -5, 4, "> limit"));
                // Arguments.of(-1, 0, -1, -3, "attempt double jump"),
                // Arguments.of(-1, -1, -4, -1, "attempt to go over exit"));
    }

    @Test
    void escape() {
        // assertTrue(game.move(game.makeCoordinate(-1, -1), game.makeCoordinate(-3, -1)));
        assertNull(game.getPieceAt(game.makeCoordinate(-3, -1)));
    }
}
