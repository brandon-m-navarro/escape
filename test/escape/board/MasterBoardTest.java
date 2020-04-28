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
package escape.board;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.File;
import org.junit.jupiter.api.Test;
import escape.board.coordinate.*;
import escape.piece.EscapePiece;
import static escape.piece.Player.*;
import static escape.piece.PieceName.*;
import static escape.piece.EscapePiece.*;


/**
 * Tests for the Escape Board class(es).
 * @version Apr 2, 2020
 */
class MasterBoardTest
{
	private Board board = null;
	/**
	 * Make sure that if there are no initializers, you still have a board.
	 * @throws Exception
	 */
	@Test
	void emptySquareBoard() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/EmptySquareBoard.xml"));
		board = bb.makeBoard();
		assertNotNull(board);
		// Now I will do some tests on this board and its contents.
		SquareCoordinate sc = SquareCoordinate.makeCoordinate(5, 5);
		EscapePiece ep = makePiece(PLAYER1, SNAIL);
		assertNull(board.getPieceAt(sc));
		board.putPieceAt(ep, sc);
		EscapePiece ep1 = board.getPieceAt(sc);
		assertNotNull(ep1);
		assertEquals(ep.getName(), ep1.getName());
		assertEquals(ep.getPlayer(), ep1.getPlayer());
	}
	
	@Test
	void orthoSquareBoard() throws Exception
	{
	    BoardBuilder bb = new BoardBuilder(
	        new File("config/board/OrthoSquareBoard.xml"));
        board = bb.makeBoard();
        assertNotNull(board);
        // Now I will do some tests on this board and its contents.
        OrthoSquareCoordinate sc = OrthoSquareCoordinate.makeCoordinate(5, 5);
        EscapePiece ep = makePiece(PLAYER1, SNAIL);
        assertNull(board.getPieceAt(sc));
        board.putPieceAt(ep, sc);
        EscapePiece ep1 = board.getPieceAt(sc);
        assertNotNull(ep1);
        assertEquals(ep.getName(), ep1.getName());
        assertEquals(ep.getPlayer(), ep1.getPlayer());
	}
	
	@Test
    void hexBoard() throws Exception
    {
        BoardBuilder bb = new BoardBuilder(
            new File("config/board/HexBoardConfig.xml"));
        board = bb.makeBoard();
        assertNotNull(board);
        // Now I will do some tests on this board and its contents.
        HexCoordinate sc = HexCoordinate.makeCoordinate(-55, 23);
        EscapePiece ep = makePiece(PLAYER1, SNAIL);
        assertNull(board.getPieceAt(sc));
        board.putPieceAt(ep, sc);
        EscapePiece ep1 = board.getPieceAt(sc);
        assertNotNull(ep1);
        assertEquals(ep.getName(), ep1.getName());
        assertEquals(ep.getPlayer(), ep1.getPlayer());
    }
}
