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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;

/**
 * Description
 * @version Apr 29, 2020
 */
class BetaEscapeGameManagerTests
{

	@Test
	void makeValidCoordinateWithInitializedSquareBoard() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameSquare.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		assertTrue(emg.makeCoordinate(1, 1).equals(SquareCoordinate.makeCoordinate(1, 1)));
		assertFalse(emg.makeCoordinate(1, 1).equals(OrthoSquareCoordinate.makeCoordinate(1, 1)));
	}
	
	@Test
	void makeInvalidCoordinateWithInitializedSquareBoard() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameSquare.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		assertNull(emg.makeCoordinate(-1, 1));
	}

	@Test
	void makeValidCoordinateWithInitializedOrthoSquareBoard() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameOrtho.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		assertTrue(emg.makeCoordinate(4, 1).equals(OrthoSquareCoordinate.makeCoordinate(4, 1)));
		assertFalse(emg.makeCoordinate(4, 1).equals(HexCoordinate.makeCoordinate(4, 1)));
	}
	
	@Test
	void makeInvalidCoordinateWithInitializedOrthoSquareBoard() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameOrtho.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		assertNull(emg.makeCoordinate(-1, 1));
	}
	
	@Test
	void makeValidCoordinateWithInitializedHexBoard() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameHex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		assertTrue(emg.makeCoordinate(10, -1).equals(HexCoordinate.makeCoordinate(10, -1)));
		assertFalse(emg.makeCoordinate(10, -1).equals(SquareCoordinate.makeCoordinate(10, -1)));
		assertFalse(emg.makeCoordinate(1, 1).equals(OrthoSquareCoordinate.makeCoordinate(1, 1)));
	}
	
	@Test
	void getPieceAtWithInitializedSquareBoard() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameSquare.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		assertNull(emg.getPieceAt(emg.makeCoordinate(5, 1)));
		
		EscapePiece p = emg.getPieceAt(emg.makeCoordinate(2, 2));
		assertNotNull(p);
		assertEquals(p.getName(), PieceName.HORSE);
		
	    EscapeException thrown = assertThrows(
    		EscapeException.class,
		    () -> emg.getPieceAt(emg.makeCoordinate(-1, -1)));

	    // assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());
		
	    EscapeException thrown2 = assertThrows(
	    		EscapeException.class,
			    () -> emg.getPieceAt(SquareCoordinate.makeCoordinate(-1, -1)));

		    // assertions on the thrown exception
			assertEquals("ERROR: invalid coordinate!", thrown2.getMessage());
	}
	
	@Test
	void getPieceAtWithInitializedOrthoSquareBoard() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameOrtho.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		assertNull(emg.getPieceAt(emg.makeCoordinate(2, 2)));
		
		EscapePiece p = emg.getPieceAt(emg.makeCoordinate(5, 2));
		assertNotNull(p);
		assertEquals(p.getName(), PieceName.FROG);
		
	    EscapeException thrown = assertThrows(
    		EscapeException.class,
		    () -> emg.getPieceAt(emg.makeCoordinate(-1, -1)));

	    // assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());
		
	    EscapeException thrown2 = assertThrows(
	    		EscapeException.class,
			    () -> emg.getPieceAt(OrthoSquareCoordinate.makeCoordinate(-1, -1)));

		    // assertions on the thrown exception
			assertEquals("ERROR: invalid coordinate!", thrown2.getMessage());
	}
	
	@Test
	void getPieceAtWithInitializedHexSquareBoard() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameHex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		assertNotNull(emg.getPieceAt(emg.makeCoordinate(2, 2)));
		
		EscapePiece p = emg.getPieceAt(emg.makeCoordinate(1, 1));
		assertNotNull(p);
		assertEquals(p.getName(), PieceName.FROG);
	}
	
	@Test
	void validateConfigurationSquareBoardNoPieceTypes() throws Exception
	{
		EscapeGameBuilder egb
			= new EscapeGameBuilder(new File("config/invalidBetaBoards/InvalidEscapeGameSquare1.xml"));
		
		 EscapeException thrown = assertThrows(
		    		EscapeException.class,
				    () -> egb.makeGameManager());

			    // assertions on the thrown exception
				assertEquals("ERROR: Invalid configuration file!",
						thrown.getMessage());
	}
	
	@Test
	void validateConfigurationIncorrectBoardType() throws Exception
	{
		EscapeGameBuilder egb
			= new EscapeGameBuilder(new File("config/invalidBetaBoards/InvalidBoard1.xml"));
		
		 EscapeException thrown = assertThrows(
		    		EscapeException.class,
				    () -> egb.makeGameManager());

			    // assertions on the thrown exception
				assertEquals("ERROR: Invalid configuration file!",
						thrown.getMessage());
	}
	
	@Test
	void validateConfigurationSquareBoardMultiplePieceTypes() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/invalidBetaBoards/InvalidEscapeGameSquare2.xml"));
		
		 EscapeException thrown = assertThrows(
		    		EscapeException.class,
				    () -> egb.makeGameManager());

			    // assertions on the thrown exception
				assertEquals("ERROR: Invalid configuration file!",
						thrown.getMessage());
	}
	
	@Test
	void validateConfigurationSquareBoardFlyOrDistance() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/invalidBetaBoards/InvalidEscapeGameSquare3.xml"));
		
		 EscapeException thrown = assertThrows(
		    		EscapeException.class,
				    () -> egb.makeGameManager());

			    // assertions on the thrown exception
				assertEquals("ERROR: Invalid configuration file!",
						thrown.getMessage());
	}
	
	@Test
	void squareGameBasicMoveChecks() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameSquare.xml"));
		EscapeGameManager emg = egb.makeGameManager();

		// Trying to move piece that doesn't exist
		assertFalse(emg.move(emg.makeCoordinate(3, 4), emg.makeCoordinate(4, 4)));
		
		// Move piece that does exist
		assertTrue(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(2, 3)));
		
		// Confirm that a piece cannot move into a teammate
		assertFalse(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(1, 1)));
		
		// Confirm that a piece can move onto an enemies piece
		assertTrue(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(2, 5)));
		
		// Confirm that the moving piece is moving to a valid coordinate
		assertFalse(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(-1, -1)));
		assertFalse(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(25, 25)));
	}
	
	@Test
	void squareGameLinearMovement() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameSquare.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		// Check some valid moves
		assertTrue(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(1, 3)));
		assertTrue(emg.move(emg.makeCoordinate(7, 7), emg.makeCoordinate(7, 5)));
		assertTrue(emg.move(emg.makeCoordinate(7, 7), emg.makeCoordinate(8, 6)));
		assertTrue(emg.move(emg.makeCoordinate(7, 7), emg.makeCoordinate(8, 7)));
		
		// Check that a piece cannot move if obstructed by a piece (No FLY, JUMP)
		assertFalse(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(2, 6)));
		
		// Check that a piece cannot move in a non linear pattern
		assertFalse(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(3, 4)));
		
		// Check that a piece cant move if too much distance
		assertFalse(emg.move(emg.makeCoordinate(2, 5), emg.makeCoordinate(8, 5)));

		// Check that a piece can move if obstructed by a piece(with fly)
		assertTrue(emg.move(emg.makeCoordinate(7, 7), emg.makeCoordinate(9, 9)));
		
		// Check that you can fly over a blocked square
		assertTrue(emg.move(emg.makeCoordinate(7, 7), emg.makeCoordinate(4, 7)));
		
		// Check that it can fly over both a blocked square and a team piece
		assertTrue(emg.move(emg.makeCoordinate(7, 7), emg.makeCoordinate(7, 10)));
		
		// Check that a piece can land on an enemy piece (with fly)
		assertTrue(emg.move(emg.makeCoordinate(7, 7), emg.makeCoordinate(8, 8)));
		
		// Check that a piece cannot fly in a non linear pattern
		assertFalse(emg.move(emg.makeCoordinate(7, 7), emg.makeCoordinate(5, 6)));

		// Check that a piece can jump a single piece (not a block)
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(5, 1)));

		// Check that a piece cannot jump two pieces consecutively
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(7, 1)));

		// Check that a piece cannot change direction and jump
		assertFalse(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(2, 5)));

		// Check that a piece cannot jump over two pieces
		assertFalse(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(10, 1)));

		// Check that a piece can jump and land on an enemy piece
		assertTrue(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(1, 3)));

		// Check that a piece can move through a blocked location if it's able
		assertTrue(emg.move(emg.makeCoordinate(7, 6), emg.makeCoordinate(7, 4)));
		
		// Check that a piece cannot move through a blocked square
		assertFalse(emg.move(emg.makeCoordinate(7, 9), emg.makeCoordinate(7, 6)));
	}
	
	@Test
	void squareGameDiagonalMovement() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SquareEscapeDiagonalMovement.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		assertTrue(emg.move(emg.makeCoordinate(4, 3), emg.makeCoordinate(7, 2)));
		assertFalse(emg.move(emg.makeCoordinate(4, 3), emg.makeCoordinate(7, 3)));

	}
	
	@Test
	void hexGameLinearMovement() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameHex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		assertFalse(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(10, 1)));
	}
	
	@Test
	void orthoSquareGameMovement() throws Exception
	{
		EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File("config/validBetaBoards/SampleEscapeGameOrtho.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		assertFalse(emg.move(emg.makeCoordinate(1, 1), emg.makeCoordinate(10, 1)));
	}
}
