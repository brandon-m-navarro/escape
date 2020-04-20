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

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;
import escape.board.coordinate.*;
import escape.piece.*;

/**
 * Description
 * @version Apr 2, 2020
 */
class BoardTest
{
	
	@Test
	void initializeSquareBoard() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		assertNotNull(bb.makeBoard());
	}
	
	@Test
	void getInitializedPieceSquareBoard() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		assertNotNull(bb.makeBoard());
		
		SquareCoordinate sc = (SquareCoordinate) SquareCoordinate.makeCoordinate(2, 2);
		Board<SquareCoordinate> b = bb.makeBoard();
		assertNotNull(b.getPieceAt(sc));
	}
	
	@Test
	void placePieceSquareBoard() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		assertNotNull(bb.makeBoard());
		
		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		SquareCoordinate sc = (SquareCoordinate) SquareCoordinate.makeCoordinate(2, 2);
		Board<SquareCoordinate> b = bb.makeBoard();
		b.putPieceAt(ep, sc);
		assertEquals(ep, b.getPieceAt(sc));
	}
	
	@Test
	void orthoSquareBoard() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		assertNotNull(bb.makeBoard());
		// Now I will do some tests on this board and its contents.
		
		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		OrthoSquareCoordinate sc = (OrthoSquareCoordinate) OrthoSquareCoordinate.makeCoordinate(2, 2);
		Board<OrthoSquareCoordinate> b = bb.makeBoard();
		b.putPieceAt(ep, sc);
		assertNotNull(b.getPieceAt(sc));
	}
	
	@Test
	void buildhexBoard() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig3.xml"));
		assertNotNull(bb.makeBoard());
		// Now I will do some tests on this board and its contents.
		
		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		HexCoordinate sc = (HexCoordinate) HexCoordinate.makeCoordinate(2, 2);
		Board<HexCoordinate> b = bb.makeBoard();
		b.putPieceAt(ep, sc);
		assertNotNull(b.getPieceAt(sc));
	}
}
