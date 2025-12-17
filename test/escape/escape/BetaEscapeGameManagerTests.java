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

package escape.escape;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.File;
import escape.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;

/**
 * Description
 * 
 * @version Apr 29, 2020
 */
class BetaEscapeGameManagerTests {

	@Test
	void makeValidCoordinateWithInitializedSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameSquare.xml"));
		EscapeGameManager egm = egb.makeGameManager();
		assertTrue(egm.makeCoordinate(1, 1).equals(SquareCoordinate.makeCoordinate(1, 1)));
		assertFalse(egm.makeCoordinate(1, 1).equals(OrthoSquareCoordinate.makeCoordinate(1, 1)));
	}

	@Test
	void makeInvalidCoordinateWithInitializedSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameSquare.xml"));
		EscapeGameManager egm = egb.makeGameManager();
		assertNull(egm.makeCoordinate(-1, 1));
	}

	@Test
	void makeValidCoordinateWithInitializedOrthoSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameOrtho.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertTrue(egm.makeCoordinate(4, 1).equals(OrthoSquareCoordinate.makeCoordinate(4, 1)));
		assertFalse(egm.makeCoordinate(4, 1).equals(HexCoordinate.makeCoordinate(4, 1)));
	}

	@Test
	void makeInvalidCoordinateWithInitializedOrthoSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameOrtho.xml"));
		EscapeGameManager egm = egb.makeGameManager();
		assertNull(egm.makeCoordinate(-1, 1));
	}

	@Test
	void makeValidCoordinateWithInitializedHexBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameHex.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertTrue(egm.makeCoordinate(10, -1).equals(HexCoordinate.makeCoordinate(10, -1)));
		assertFalse(egm.makeCoordinate(10, -1).equals(SquareCoordinate.makeCoordinate(10, -1)));
		assertFalse(egm.makeCoordinate(1, 1).equals(OrthoSquareCoordinate.makeCoordinate(1, 1)));
	}

	@Test
	void getPieceAtWithInitializedSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameSquare.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertNull(egm.getPieceAt(egm.makeCoordinate(5, 1)));

		EscapePiece p = egm.getPieceAt(egm.makeCoordinate(2, 2));
		assertNotNull(p);
		assertEquals(p.getName(), PieceName.HORSE);

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> egm.getPieceAt(egm.makeCoordinate(-1, -1)));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());

		EscapeException thrown2 = assertThrows(
				EscapeException.class,
				() -> egm.getPieceAt(SquareCoordinate.makeCoordinate(-1, -1)));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown2.getMessage());
	}

	@Test
	void getPieceAtWithInitializedOrthoSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameOrtho.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertNull(egm.getPieceAt(egm.makeCoordinate(2, 2)));

		EscapePiece p = egm.getPieceAt(egm.makeCoordinate(5, 2));
		assertNotNull(p);
		assertEquals(p.getName(), PieceName.FROG);

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> egm.getPieceAt(egm.makeCoordinate(-1, -1)));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());

		EscapeException thrown2 = assertThrows(
				EscapeException.class,
				() -> egm.getPieceAt(OrthoSquareCoordinate.makeCoordinate(-1, -1)));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown2.getMessage());
	}

	@Test
	void getPieceAtWithInitializedHexSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameHex.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertNotNull(egm.getPieceAt(egm.makeCoordinate(2, 2)));

		EscapePiece p = egm.getPieceAt(egm.makeCoordinate(1, 1));
		assertNotNull(p);
		assertEquals(p.getName(), PieceName.FROG);
	}

	@Test
	void validateConfigurationSquareBoardNoPieceTypes() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
				new File("config/invalidBetaBoards/InvalidEscapeGameSquare1.xml"));

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> egb.makeGameManager());

		// assertions on the thrown exception
		assertEquals("ERROR: Invalid configuration file!",
				thrown.getMessage());
	}

	@Test
	void validateConfigurationIncorrectBoardType() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/invalidBetaBoards/InvalidBoard1.xml"));

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> egb.makeGameManager());

		// assertions on the thrown exception
		assertEquals("ERROR: Invalid configuration file!",
				thrown.getMessage());
	}

	@Test
	void validateConfigurationSquareBoardMultiplePieceTypes() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
				new File("config/invalidBetaBoards/InvalidEscapeGameSquare2.xml"));

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> egb.makeGameManager());

		// assertions on the thrown exception
		assertEquals("ERROR: Invalid configuration file!",
				thrown.getMessage());
	}

	@Test
	void validateConfigurationSquareBoardFlyOrDistance() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
				new File("config/invalidBetaBoards/InvalidEscapeGameSquare3.xml"));

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> egb.makeGameManager());

		// assertions on the thrown exception
		assertEquals("ERROR: Invalid configuration file!",
				thrown.getMessage());
	}

	@Test
	void squareGameBasicMoveChecks() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameSquare.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		// Trying to move piece that doesn't exist
		assertFalse(egm.move(egm.makeCoordinate(3, 4), egm.makeCoordinate(4, 4)));

		// Move piece that does exist
		assertTrue(egm.move(egm.makeCoordinate(2, 2), egm.makeCoordinate(2, 3)));

		// Confirm that a piece cannot move into a teammate
		assertFalse(egm.move(egm.makeCoordinate(2, 2), egm.makeCoordinate(1, 1)));

		// Confirm that a piece can move onto an enemies piece
		assertTrue(egm.move(egm.makeCoordinate(2, 2), egm.makeCoordinate(2, 5)));

		// Confirm that the moving piece is moving to a valid coordinate
		assertFalse(egm.move(egm.makeCoordinate(2, 2), egm.makeCoordinate(-1, -1)));
		assertFalse(egm.move(egm.makeCoordinate(2, 2), egm.makeCoordinate(25, 25)));
	}

	@Test
	void squareGameLinearMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameSquare.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		// Check some valid moves
		assertTrue(egm.move(egm.makeCoordinate(2, 2), egm.makeCoordinate(1, 3)));
		assertTrue(egm.move(egm.makeCoordinate(7, 7), egm.makeCoordinate(7, 5)));
		assertTrue(egm.move(egm.makeCoordinate(7, 7), egm.makeCoordinate(8, 6)));
		assertTrue(egm.move(egm.makeCoordinate(7, 7), egm.makeCoordinate(8, 7)));

		// Check that a piece cannot move if obstructed by a piece (No FLY, JUMP)
		assertFalse(egm.move(egm.makeCoordinate(2, 2), egm.makeCoordinate(2, 6)));

		// Check that a piece cannot move in a non linear pattern
		assertFalse(egm.move(egm.makeCoordinate(2, 2), egm.makeCoordinate(3, 4)));

		// Check that a piece cant move if too much distance
		assertFalse(egm.move(egm.makeCoordinate(2, 5), egm.makeCoordinate(8, 5)));

		// Check that a piece can move if obstructed by a piece(with fly)
		assertTrue(egm.move(egm.makeCoordinate(7, 7), egm.makeCoordinate(9, 9)));

		// Check that you can fly over a blocked square
		assertTrue(egm.move(egm.makeCoordinate(7, 7), egm.makeCoordinate(4, 7)));

		// Check that it can fly over both a blocked square and a team piece
		assertTrue(egm.move(egm.makeCoordinate(7, 7), egm.makeCoordinate(7, 10)));

		// Check that a piece can land on an enemy piece (with fly)
		assertTrue(egm.move(egm.makeCoordinate(7, 7), egm.makeCoordinate(8, 8)));

		// Check that a piece cannot fly in a non linear pattern
		assertFalse(egm.move(egm.makeCoordinate(7, 7), egm.makeCoordinate(5, 6)));

		// Check that a piece can jump a single piece (not a block)
		assertTrue(egm.move(egm.makeCoordinate(1, 1), egm.makeCoordinate(5, 1)));

		// Check that a piece cannot jump two pieces consecutively
		assertTrue(egm.move(egm.makeCoordinate(1, 1), egm.makeCoordinate(7, 1)));

		// Check that a piece cannot change direction and jump
		assertFalse(egm.move(egm.makeCoordinate(1, 1), egm.makeCoordinate(2, 5)));

		// Check that a piece cannot jump over two pieces
		assertFalse(egm.move(egm.makeCoordinate(1, 1), egm.makeCoordinate(10, 1)));

		// Check that a piece can jump and land on an enemy piece
		assertTrue(egm.move(egm.makeCoordinate(1, 1), egm.makeCoordinate(1, 3)));

		// Check that a piece can move through a blocked location if it's able
		assertTrue(egm.move(egm.makeCoordinate(7, 6), egm.makeCoordinate(7, 4)));

		// Check that a piece cannot move through a blocked square
		assertFalse(egm.move(egm.makeCoordinate(7, 9), egm.makeCoordinate(7, 6)));
	}

	@Test
	void squareGameDiagonalMovement1() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
				new File("config/validBetaBoards/SquareEscapeDiagonalMovement.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertTrue(egm.move(egm.makeCoordinate(4, 3), egm.makeCoordinate(7, 2)));
		assertFalse(egm.move(egm.makeCoordinate(4, 3), egm.makeCoordinate(7, 3)));
		assertTrue(egm.move(egm.makeCoordinate(6, 2), egm.makeCoordinate(2, 4)));

	}

	@Test
	void squareGameDiagonalMovement2() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
				new File("config/validBetaBoards/SquareEscapeDiagonalMovement2.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertTrue(egm.move(egm.makeCoordinate(4, 1), egm.makeCoordinate(6, 1)));
		assertFalse(egm.move(egm.makeCoordinate(1, 3), egm.makeCoordinate(3, 5)));
		assertTrue(egm.move(egm.makeCoordinate(1, 3), egm.makeCoordinate(2, 4)));
		assertTrue(egm.move(egm.makeCoordinate(4, 3), egm.makeCoordinate(6, 1)));

	}

	@Test
	void squareGameDiagonalMovement3() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
				new File("config/validBetaBoards/SquareEscapeDiagonalMovement3.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertTrue(egm.move(egm.makeCoordinate(4, 1), egm.makeCoordinate(7, 4)));
		assertFalse(egm.move(egm.makeCoordinate(4, 1), egm.makeCoordinate(8, 5)));
		assertTrue(egm.move(egm.makeCoordinate(8, 3), egm.makeCoordinate(5, 6)));
	}

	@Test
	void squareGameDiagonalMovement4() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
				new File("config/validBetaBoards/SquareEscapeDiagonalMovement4.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertFalse(egm.move(egm.makeCoordinate(3, 1), egm.makeCoordinate(6, 4)));

	}

	@Test
	void squareGameOrthogonalMovement1() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
				new File("config/validBetaBoards/SquareEscapeOrthogonalMovement.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertTrue(egm.move(egm.makeCoordinate(3, 1), egm.makeCoordinate(5, 4)));
		assertFalse(egm.move(egm.makeCoordinate(3, 1), egm.makeCoordinate(6, 3)));
		assertTrue(egm.move(egm.makeCoordinate(3, 1), egm.makeCoordinate(1, 2)));

	}

	@Test
	void hexGameLinearMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameHex.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertFalse(egm.move(egm.makeCoordinate(1, 1), egm.makeCoordinate(10, 1)));

	}

	@Test
	void orthoSquareGameMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameOrtho.xml"));
		EscapeGameManager egm = egb.makeGameManager();

		assertFalse(egm.move(egm.makeCoordinate(1, 1), egm.makeCoordinate(10, 1)));
	}
}
