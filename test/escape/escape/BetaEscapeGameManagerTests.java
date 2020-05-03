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
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.*;
import escape.board.*;
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
		
		assertNull(emg.getPieceAt(emg.makeCoordinate(1, 1)));
		
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
		
		assertNull(emg.getPieceAt(emg.makeCoordinate(2, 2)));
		
		EscapePiece p = emg.getPieceAt(emg.makeCoordinate(0, 0));
		assertNotNull(p);
		assertEquals(p.getName(), PieceName.HORSE);
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
}
