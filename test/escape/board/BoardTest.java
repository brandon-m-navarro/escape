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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;

/**
 * Testing Board creation and initialization methods. @SuppressWarnings("unchecked") is
 * used for all makeBoard() calls as the BoardConfig xml files specify the type.
 * @version Dec 2025
 */
class BoardTest {

	@Test
	void equalsTwoDimensionalBoard() throws Exception {
		BoardBuilder bb1 = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		BoardBuilder bb2 = new BoardBuilder(new File("config/board/BoardConfig2.xml"));

		@SuppressWarnings("unchecked")
		Board<SquareCoordinate> b1 = bb1.makeBoard();

		@SuppressWarnings("unchecked")
		Board<OrthoSquareCoordinate> b2 = bb2.makeBoard();

		assertFalse(b1.equals("Should Fail"));
		assertFalse(b1.equals(b2));
		assertTrue(b1.equals(b1));
	}

	@Test
	void initializeSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		assertNotNull(bb.makeBoard());
		assertTrue(bb.makeBoard().getClass() == SquareBoard.class);
	}

	@Test
	void getInitializedPieceSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		assertNotNull(bb.makeBoard());

		SquareCoordinate sc = (SquareCoordinate) SquareCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<SquareCoordinate> b = bb.makeBoard();

		assertNotNull(b.getPieceAt(sc));
		EscapePiece ep = b.getPieceAt(sc);
		assertEquals(ep.getName(), PieceName.HORSE);
	}

	@Test
	void putPieceAtSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		SquareCoordinate sc = (SquareCoordinate) SquareCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<SquareCoordinate> b = bb.makeBoard();

		b.putPieceAt(ep, sc);
		assertEquals(ep, b.getPieceAt(sc));
	}

	@Test
	void putPieceAtOutOfBoundsSquareBoard1() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		SquareCoordinate sc = (SquareCoordinate) SquareCoordinate.makeCoordinate(10, 10);

		@SuppressWarnings("unchecked")
		Board<SquareCoordinate> b = bb.makeBoard();

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> b.putPieceAt(ep, sc));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());
		// assertions on the state of a domain object after the exception has been
		// thrown
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void putPieceAtOutOfBoundsSquareBoard2() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig4.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		SquareCoordinate sc = (SquareCoordinate) SquareCoordinate.makeCoordinate(10, 10);

		@SuppressWarnings("unchecked")
		Board<SquareCoordinate> b = bb.makeBoard();

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> b.putPieceAt(ep, sc));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());
		// assertions on the state of a domain object after the exception has been
		// thrown
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void getPieceAtOnLocationTypeSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		assertNotNull(bb.makeBoard());

		SquareCoordinate sc = (SquareCoordinate) SquareCoordinate.makeCoordinate(3, 5);

		@SuppressWarnings("unchecked")
		Board<SquareCoordinate> b = bb.makeBoard();
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void putPieceAtBlockedLocationSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		SquareCoordinate sc = (SquareCoordinate) SquareCoordinate.makeCoordinate(3, 5);

		@SuppressWarnings("unchecked")
		Board<SquareCoordinate> b = bb.makeBoard();

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> b.putPieceAt(ep, sc));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());
		// assertions on the state of a domain object after the exception has been
		// thrown
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void putPieceOnAnotherPieceSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE);
		SquareCoordinate sc = (SquareCoordinate) SquareCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<SquareCoordinate> b = bb.makeBoard();
		b.putPieceAt(ep, sc);
		assertEquals(ep.getPlayer(), b.getPieceAt(sc).getPlayer());
	}

	@Test
	void removePieceWithPutPieceAtSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		assertNotNull(bb.makeBoard());

		SquareCoordinate sc = (SquareCoordinate) SquareCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<SquareCoordinate> b = bb.makeBoard();
		b.putPieceAt(null, sc);
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void putPieceAtExitLocationSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig4.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE);
		SquareCoordinate sc = (SquareCoordinate) SquareCoordinate.makeCoordinate(4, 5);

		@SuppressWarnings("unchecked")
		Board<SquareCoordinate> b = bb.makeBoard();
		b.putPieceAt(ep, sc);
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void initializeOrthoSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		assertNotNull(bb.makeBoard());
		assertTrue(bb.makeBoard().getClass() == OrthoSquareBoard.class);
	}

	@Test
	void getInitializedPieceOrthoSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		assertNotNull(bb.makeBoard());

		OrthoSquareCoordinate sc = (OrthoSquareCoordinate) OrthoSquareCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<OrthoSquareCoordinate> b = bb.makeBoard();

		assertNotNull(b.getPieceAt(sc));
		EscapePiece ep = b.getPieceAt(sc);
		assertEquals(ep.getName(), PieceName.HORSE);
	}

	@Test
	void putPieceAtOrthoSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		OrthoSquareCoordinate sc = (OrthoSquareCoordinate) OrthoSquareCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<OrthoSquareCoordinate> b = bb.makeBoard();

		b.putPieceAt(ep, sc);
		assertEquals(ep, b.getPieceAt(sc));
	}

	@Test
	void putPieceAtOutOfBoundsOrthoSquareBoard1() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		OrthoSquareCoordinate sc = (OrthoSquareCoordinate) OrthoSquareCoordinate.makeCoordinate(10, 10);

		@SuppressWarnings("unchecked")
		Board<OrthoSquareCoordinate> b = bb.makeBoard();

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> b.putPieceAt(ep, sc));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());
		// assertions on the state of a domain object after the exception has been
		// thrown
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void putPieceAtOutOfBoundsOrthoSquareBoard2() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		OrthoSquareCoordinate sc = (OrthoSquareCoordinate) OrthoSquareCoordinate.makeCoordinate(10, 10);

		@SuppressWarnings("unchecked")
		Board<OrthoSquareCoordinate> b = bb.makeBoard();

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> b.putPieceAt(ep, sc));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());
		// assertions on the state of a domain object after the exception has been
		// thrown
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void getPieceAtOnLocationTypeOrthoSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		assertNotNull(bb.makeBoard());

		OrthoSquareCoordinate sc = (OrthoSquareCoordinate) OrthoSquareCoordinate.makeCoordinate(3, 5);

		@SuppressWarnings("unchecked")
		Board<OrthoSquareCoordinate> b = bb.makeBoard();
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void putPieceAtBlockedLocationOrthoSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		OrthoSquareCoordinate sc = (OrthoSquareCoordinate) OrthoSquareCoordinate.makeCoordinate(3, 5);

		@SuppressWarnings("unchecked")
		Board<OrthoSquareCoordinate> b = bb.makeBoard();

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> b.putPieceAt(ep, sc));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());
		// assertions on the state of a domain object after the exception has been
		// thrown
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void putPieceOnAnotherPieceOrthoSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE);
		OrthoSquareCoordinate sc = (OrthoSquareCoordinate) OrthoSquareCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<OrthoSquareCoordinate> b = bb.makeBoard();

		b.putPieceAt(ep, sc);
		assertEquals(ep.getPlayer(), b.getPieceAt(sc).getPlayer());
	}

	@Test
	void removePieceWithPutPieceAtOrthoSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		assertNotNull(bb.makeBoard());

		OrthoSquareCoordinate sc = (OrthoSquareCoordinate) OrthoSquareCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<OrthoSquareCoordinate> b = bb.makeBoard();

		b.putPieceAt(null, sc);
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void putPieceAtExitLocationOrthoSquareBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE);
		OrthoSquareCoordinate sc = (OrthoSquareCoordinate) OrthoSquareCoordinate.makeCoordinate(3, 6);

		@SuppressWarnings("unchecked")
		Board<OrthoSquareCoordinate> b = bb.makeBoard();

		b.putPieceAt(ep, sc);
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void initializeHexBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig3.xml"));
		assertNotNull(bb.makeBoard());
		assertTrue(bb.makeBoard().getClass() == HexBoard.class);
	}

	@Test
	void getInitializedPieceHexBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig3.xml"));
		assertNotNull(bb.makeBoard());

		HexCoordinate sc = (HexCoordinate) HexCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<HexCoordinate> b = bb.makeBoard();

		assertNotNull(b.getPieceAt(sc));
		EscapePiece ep = b.getPieceAt(sc);
		assertEquals(ep.getName(), PieceName.HORSE);
	}

	@Test
	void putPieceAtHexBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig3.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		HexCoordinate sc = (HexCoordinate) HexCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<HexCoordinate> b = bb.makeBoard();

		b.putPieceAt(ep, sc);
		assertEquals(ep, b.getPieceAt(sc));
	}

	@Test
	void getPieceAtOnLocationTypeHexBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig3.xml"));
		assertNotNull(bb.makeBoard());

		HexCoordinate sc = (HexCoordinate) HexCoordinate.makeCoordinate(3, 5);

		@SuppressWarnings("unchecked")
		Board<HexCoordinate> b = bb.makeBoard();
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void putPieceAtBlockedLocationHexBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig3.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		HexCoordinate sc = (HexCoordinate) HexCoordinate.makeCoordinate(3, 5);

		@SuppressWarnings("unchecked")
		Board<HexCoordinate> b = bb.makeBoard();

		EscapeException thrown = assertThrows(
				EscapeException.class,
				() -> b.putPieceAt(ep, sc));

		// assertions on the thrown exception
		assertEquals("ERROR: invalid coordinate!", thrown.getMessage());
		// assertions on the state of a domain object after the exception has been
		// thrown
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void putPieceOnAnotherPieceHexBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig3.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE);
		HexCoordinate sc = (HexCoordinate) HexCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<HexCoordinate> b = bb.makeBoard();

		b.putPieceAt(ep, sc);
		assertEquals(ep.getPlayer(), b.getPieceAt(sc).getPlayer());
	}

	@Test
	void removePieceWithPutPieceAtHexBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig3.xml"));
		assertNotNull(bb.makeBoard());

		HexCoordinate sc = (HexCoordinate) HexCoordinate.makeCoordinate(2, 2);

		@SuppressWarnings("unchecked")
		Board<HexCoordinate> b = bb.makeBoard();

		b.putPieceAt(null, sc);
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void putPieceAtExitLocationHexBoard() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig3.xml"));
		assertNotNull(bb.makeBoard());

		EscapePiece ep = EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE);
		HexCoordinate sc = (HexCoordinate) HexCoordinate.makeCoordinate(3, 6);

		@SuppressWarnings("unchecked")
		Board<HexCoordinate> b = bb.makeBoard();
		
		b.putPieceAt(ep, sc);
		assertNull(b.getPieceAt(sc));
	}

	@Test
	void initializeHexBoardWithNoSpecifiedMaxXAndMaxY() throws Exception {
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig5.xml"));
		assertNotNull(bb.makeBoard());
	}
}
