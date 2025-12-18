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
 * @version Dec 2025
 */
class BetaEscapeGameManagerTests {

	@Test
	void makeValidCoordinateWithInitializedSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameSquare.xml")
		);
		EscapeGameManager<? extends Coordinate> egm = egb.makeGameManager();
		assertTrue(egm.makeCoordinate(1, 1).equals(SquareCoordinate.makeCoordinate(1, 1)));
		assertFalse(egm.makeCoordinate(1, 1).equals(OrthoSquareCoordinate.makeCoordinate(1, 1)));
	}

	@Test
	void makeInvalidCoordinateWithInitializedSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameSquare.xml")
		);
		EscapeGameManager<? extends Coordinate> egm = egb.makeGameManager();
		assertNull(egm.makeCoordinate(-1, 1));
	}

	@Test
	void makeValidCoordinateWithInitializedOrthoSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameOrtho.xml")
		);
		EscapeGameManager<? extends Coordinate> egm = egb.makeGameManager();

		assertTrue(egm.makeCoordinate(4, 1).equals(OrthoSquareCoordinate.makeCoordinate(4, 1)));
		assertFalse(egm.makeCoordinate(4, 1).equals(HexCoordinate.makeCoordinate(4, 1)));
	}

	@Test
	void makeInvalidCoordinateWithInitializedOrthoSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameOrtho.xml")
		);
		EscapeGameManager<? extends Coordinate> egm = egb.makeGameManager();
		assertNull(egm.makeCoordinate(-1, 1));
	}

	@Test
	void makeValidCoordinateWithInitializedHexBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameHex.xml")
		);
		EscapeGameManager<? extends Coordinate> egm = egb.makeGameManager();

		assertTrue(egm.makeCoordinate(10, -1).equals(HexCoordinate.makeCoordinate(10, -1)));
		assertFalse(egm.makeCoordinate(10, 10).equals(SquareCoordinate.makeCoordinate(10, 10)));
		assertFalse(egm.makeCoordinate(1, 1).equals(OrthoSquareCoordinate.makeCoordinate(1, 1)));
	}

	@Test
	void getPieceAtWithInitializedSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameSquare.xml")
		);

		// Pass to generic helper
		testSquareGetPieceAt(egb.makeGameManager());
	}

	private <C extends Coordinate> void testSquareGetPieceAt(EscapeGameManager<C> manager) {
		C emptyCoord = manager.makeCoordinate(5, 1);
		assertNull(manager.getPieceAt(emptyCoord));

		C horseCoord = manager.makeCoordinate(2, 2);
		EscapePiece p = manager.getPieceAt(horseCoord);
		assertNotNull(p);
		assertEquals(PieceName.HORSE, p.getName());

		C outOfBounds = manager.makeCoordinate(100, 100);
		EscapeException e = assertThrows(
			EscapeException.class,
			() -> manager.getPieceAt(outOfBounds)
		);
		assertEquals("ERROR: invalid coordinate!", e.getMessage());
	}

	@Test
	void getPieceAtWithInitializedOrthoSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameOrtho.xml")
		);
		testOrthoGetPieceAt(egb.makeGameManager());
	}

	private <C extends Coordinate> void testOrthoGetPieceAt(EscapeGameManager<C> manager) {

		assertNull(manager.getPieceAt(manager.makeCoordinate(2, 2)));

		EscapePiece p = manager.getPieceAt(manager.makeCoordinate(5, 2));
		assertNotNull(p);
		assertEquals(p.getName(), PieceName.FROG);

		EscapeException thrown = assertThrows(
			EscapeException.class,
			() -> manager.getPieceAt(manager.makeCoordinate(-1, -1))
		);

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());

		EscapeException thrown2 = assertThrows(
				EscapeException.class,
				() -> manager.getPieceAt(manager.makeCoordinate(-1, -1)));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown2.getMessage());
	}

	@Test
	void getPieceAtWithInitializedHexSquareBoard() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameHex.xml")
		);
		testHexGetPieceAt(egb.makeGameManager());
	}

	private <C extends Coordinate> void testHexGetPieceAt(EscapeGameManager<C> manager) {
		assertNotNull(manager.getPieceAt(manager.makeCoordinate(2, 2)));

		EscapePiece p = manager.getPieceAt(manager.makeCoordinate(1, 1));
		assertNotNull(p);
		assertEquals(p.getName(), PieceName.FROG);
	}


	@Test
	void validateConfigurationSquareBoardNoPieceTypes() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File(
				"config/invalidBetaBoards/InvalidEscapeGameSquare1.xml"
			)
		);

		EscapeException thrown = assertThrows(
			EscapeException.class,
			() -> egb.makeGameManager()
		);

		// assertions on the thrown exception
		assertEquals(
			"ERROR: Invalid configuration file!",
			thrown.getMessage()
		);
	}

	@Test
	void validateConfigurationIncorrectBoardType() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/invalidBetaBoards/InvalidBoard1.xml")
		);

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> egb.makeGameManager()
		);

		// assertions on the thrown exception
		assertEquals(
			"ERROR: Invalid configuration file!",
			thrown.getMessage()
		);
	}

	@Test
	void validateConfigurationSquareBoardMultiplePieceTypes() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/invalidBetaBoards/InvalidEscapeGameSquare2.xml")
		);

		EscapeException thrown = assertThrows(
			EscapeException.class,
			() -> egb.makeGameManager()
		);

		// assertions on the thrown exception
		assertEquals(
			"ERROR: Invalid configuration file!",
			thrown.getMessage()
		);
	}

	@Test
	void validateConfigurationSquareBoardFlyOrDistance() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/invalidBetaBoards/InvalidEscapeGameSquare3.xml")
		);

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> egb.makeGameManager());

		// assertions on the thrown exception
		assertEquals("ERROR: Invalid configuration file!", thrown.getMessage());
	}

	@Test
	void squareGameBasicMoveChecks() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameSquare.xml")
		);
		testSquareMove(egb.makeGameManager());
	}

	private <C extends Coordinate> void testSquareMove(EscapeGameManager<C> manager) {

		// Trying to move piece that doesn't exist
		assertFalse(manager.move(manager.makeCoordinate(3, 4), manager.makeCoordinate(4, 4)));

		// Move piece that does exist
		assertTrue(manager.move(manager.makeCoordinate(2, 2), manager.makeCoordinate(2, 3)));

		// Confirm that a piece cannot move into a teammate
		assertFalse(manager.move(manager.makeCoordinate(2, 2), manager.makeCoordinate(1, 1)));

		// Confirm that a piece can move onto an enemies piece
		assertTrue(manager.move(manager.makeCoordinate(2, 2), manager.makeCoordinate(2, 5)));

		// Confirm that the moving piece is moving to a valid coordinate
		assertFalse(manager.move(manager.makeCoordinate(2, 2), manager.makeCoordinate(-1, -1)));
		assertFalse(manager.move(manager.makeCoordinate(2, 2), manager.makeCoordinate(25, 25)));
	}

	@Test
	void squareGameLinearMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameSquare.xml")
		);
		testLinearMove(egb.makeGameManager());
	}

	private <C extends Coordinate> void testLinearMove(EscapeGameManager<C> manager) {

		// Check some valid moves
		assertTrue(manager.move(manager.makeCoordinate(2, 2), manager.makeCoordinate(1, 3)));
		assertTrue(manager.move(manager.makeCoordinate(7, 7), manager.makeCoordinate(7, 5)));
		assertTrue(manager.move(manager.makeCoordinate(7, 7), manager.makeCoordinate(8, 6)));
		assertTrue(manager.move(manager.makeCoordinate(7, 7), manager.makeCoordinate(8, 7)));

		// Check that a piece cannot move if obstructed by a piece (No FLY, JUMP)
		assertFalse(manager.move(manager.makeCoordinate(2, 2), manager.makeCoordinate(2, 6)));

		// Check that a piece cannot move in a non linear pattern
		assertFalse(manager.move(manager.makeCoordinate(2, 2), manager.makeCoordinate(3, 4)));

		// Check that a piece cant move if too much distance
		assertFalse(manager.move(manager.makeCoordinate(2, 5), manager.makeCoordinate(8, 5)));

		// Check that a piece can move if obstructed by a piece(with fly)
		assertTrue(manager.move(manager.makeCoordinate(7, 7), manager.makeCoordinate(9, 9)));

		// Check that you can fly over a blocked square
		assertTrue(manager.move(manager.makeCoordinate(7, 7), manager.makeCoordinate(4, 7)));

		// Check that it can fly over both a blocked square and a team piece
		assertTrue(manager.move(manager.makeCoordinate(7, 7), manager.makeCoordinate(7, 10)));

		// Check that a piece can land on an enemy piece (with fly)
		assertTrue(manager.move(manager.makeCoordinate(7, 7), manager.makeCoordinate(8, 8)));

		// Check that a piece cannot fly in a non linear pattern
		assertFalse(manager.move(manager.makeCoordinate(7, 7), manager.makeCoordinate(5, 6)));

		// Check that a piece can jump a single piece (not a block)
		assertTrue(manager.move(manager.makeCoordinate(1, 1), manager.makeCoordinate(5, 1)));

		// Check that a piece cannot jump two pieces consecutively
		assertTrue(manager.move(manager.makeCoordinate(1, 1), manager.makeCoordinate(7, 1)));

		// Check that a piece cannot change direction and jump
		assertFalse(manager.move(manager.makeCoordinate(1, 1), manager.makeCoordinate(2, 5)));

		// Check that a piece cannot jump over two pieces
		assertFalse(manager.move(manager.makeCoordinate(1, 1), manager.makeCoordinate(10, 1)));

		// Check that a piece can jump and land on an enemy piece
		assertTrue(manager.move(manager.makeCoordinate(1, 1), manager.makeCoordinate(1, 3)));

		// Check that a piece can move through a blocked location if it's able
		assertTrue(manager.move(manager.makeCoordinate(7, 6), manager.makeCoordinate(7, 4)));

		// Check that a piece cannot move through a blocked square
		assertFalse(manager.move(manager.makeCoordinate(7, 9), manager.makeCoordinate(7, 6)));
	}

	@Test
	void squareGameDiagonalMovement1() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SquareEscapeDiagonalMovement.xml")
		);
		testSquareDiagonalMove1(egb.makeGameManager());
	}

	private <C extends Coordinate> void testSquareDiagonalMove1(EscapeGameManager<C> manager) {
		assertTrue(manager.move(manager.makeCoordinate(4, 3), manager.makeCoordinate(7, 2)));
		assertFalse(manager.move(manager.makeCoordinate(4, 3), manager.makeCoordinate(7, 3)));
		assertTrue(manager.move(manager.makeCoordinate(6, 2), manager.makeCoordinate(2, 4)));
	}

	@Test
	void squareGameDiagonalMovement2() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SquareEscapeDiagonalMovement2.xml")
		);
		testSquareDiagonalMove2(egb.makeGameManager());
	}

	private <C extends Coordinate> void testSquareDiagonalMove2(EscapeGameManager<C> manager) {
		assertTrue(manager.move(manager.makeCoordinate(4, 1), manager.makeCoordinate(6, 1)));
		assertFalse(manager.move(manager.makeCoordinate(1, 3), manager.makeCoordinate(3, 5)));
		assertTrue(manager.move(manager.makeCoordinate(1, 3), manager.makeCoordinate(2, 4)));
		assertTrue(manager.move(manager.makeCoordinate(4, 3), manager.makeCoordinate(6, 1)));
	}

	@Test
	void squareGameDiagonalMovement3() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SquareEscapeDiagonalMovement3.xml")
		);
		testSquareDiagonalMove3(egb.makeGameManager());
	}

	private <C extends Coordinate> void testSquareDiagonalMove3(EscapeGameManager<C> manager) {
		assertTrue(manager.move(manager.makeCoordinate(4, 1), manager.makeCoordinate(7, 4)));
		assertFalse(manager.move(manager.makeCoordinate(4, 1), manager.makeCoordinate(8, 5)));
		assertTrue(manager.move(manager.makeCoordinate(8, 3), manager.makeCoordinate(5, 6)));
	}

	@Test
	void squareGameDiagonalMovement4() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SquareEscapeDiagonalMovement4.xml")
		);
		testSquareDiagonalMove4(egb.makeGameManager());
	}
	private <C extends Coordinate> void testSquareDiagonalMove4(EscapeGameManager<C> manager) {
		assertFalse(manager.move(manager.makeCoordinate(3, 1), manager.makeCoordinate(6, 4)));
	}

	@Test
	void squareGameOrthogonalMovement1() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SquareEscapeOrthogonalMovement.xml")
		);
		testSquareOrthoMove1(egb.makeGameManager());
	}
	private <C extends Coordinate> void testSquareOrthoMove1(EscapeGameManager<C> manager) {
		assertTrue(manager.move(manager.makeCoordinate(3, 1), manager.makeCoordinate(5, 4)));
		assertFalse(manager.move(manager.makeCoordinate(3, 1), manager.makeCoordinate(6, 3)));
		assertTrue(manager.move(manager.makeCoordinate(3, 1), manager.makeCoordinate(1, 2)));
	}

	@Test
	void hexGameLinearMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameHex.xml")
		);
		testHexLinearMove(egb.makeGameManager());
	}
	private <C extends Coordinate> void testHexLinearMove(EscapeGameManager<C> manager) {
		assertFalse(manager.move(manager.makeCoordinate(1, 1), manager.makeCoordinate(10, 1)));
	}

	@Test
	void orthoSquareGameMovement() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(
			new File("config/validBetaBoards/SampleEscapeGameOrtho.xml")
		);
		testOrthoMove(egb.makeGameManager());
	}
	private <C extends Coordinate> void testOrthoMove(EscapeGameManager<C> manager) {
		assertFalse(manager.move(manager.makeCoordinate(1, 1), manager.makeCoordinate(10, 1)));
	}
}
