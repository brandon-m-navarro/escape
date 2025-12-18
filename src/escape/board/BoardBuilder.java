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

import static escape.board.LocationType.CLEAR;
import java.io.*;
import javax.xml.bind.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import escape.util.*;

/**
 * A Builder class for creating Boards. It is only an example and builds
 * just Square boards. If you choose to use this
 * 
 * @version Dec 2025
 */
public class BoardBuilder {
	private BoardInitializer bi;

	/**
	 * The constructor for this takes a file name. It is either an absolute path
	 * or a path relative to the beginning of this project.
	 * 
	 * @param fileName the absolute or relative path to the file that contains
	 *                 the XML configuration of the board
	 * @throws FileNotFoundException if the file does not exist, is a directory
	 *                               rather than a regular file, or for some other
	 *                               reason cannot be opened for reading.
	 */
	public BoardBuilder(File fileName) throws Exception {
		JAXBContext contextObj = JAXBContext.newInstance(BoardInitializer.class);
		Unmarshaller mub = contextObj.createUnmarshaller();
		bi = (BoardInitializer) mub.unmarshal(new FileReader(fileName));
	}

	/**
	 * This method simply initializes the appropriate board from the unmarshalled
	 * XML configuration file.
	 * @return a Board with the type specified in the XML config file
	 */
	public Board<? extends Coordinate> makeBoard() {
		TwoDimensionalBoard board;
		switch (bi.getCoordinateId()) {
			case SQUARE:
				board = new SquareBoard(bi.getxMax(), bi.getyMax());
				initializeSquareBoard(board, bi.getLocationInitializers());
				return board;
			case ORTHOSQUARE:
				board = new OrthoSquareBoard(bi.getxMax(), bi.getyMax());
				initializeOrthoSquareBoard(board, bi.getLocationInitializers());
				return board;
			case HEX:
				board = new HexBoard(bi.getxMax(), bi.getyMax());
				initializeHexBoard(board, bi.getLocationInitializers());
				return board;
		}
		throw new EscapeException("ERROR: Unrecognized Board type");
	}

	/**
	 * Initialize each spot on a SquareBoard with either a piece or LocationType,
	 * depending what
	 * was specified in the XML config file
	 * 
	 * @param b            the Board to be initialized
	 * @param initializers 0 .. many general initializers for a board location
	 */
	private void initializeSquareBoard(TwoDimensionalBoard b, LocationInitializer... initializers) {
		for (LocationInitializer li : initializers) {
			SquareCoordinate c = SquareCoordinate.makeCoordinate(li.x, li.y);
			if (li.pieceName != null) {
				b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
			}

			if (li.locationType != null && li.locationType != CLEAR) {
				b.setLocationType(c, li.locationType);
			}
		}
	}

	/**
	 * Initialize each spot on a OrthoSquareBoard with either a piece or
	 * LocationType, depending what
	 * was specified in the XML config file
	 * 
	 * @param b            the Board to be initialized
	 * @param initializers 0 .. many general initializers for a board location
	 */
	private void initializeOrthoSquareBoard(TwoDimensionalBoard b, LocationInitializer... initializers) {
		if (initializers != null) {
			for (LocationInitializer li : initializers) {
				OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(li.x, li.y);
				if (li.pieceName != null) {
					b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
				}

				if (li.locationType != null && li.locationType != CLEAR) {
					b.setLocationType(c, li.locationType);
				}
			}
		} else {
			// Should initialize all spaces as clear
		}

	}

	/**
	 * Initialize each spot on a HexBoard with either a piece or LocationType,
	 * depending what
	 * was specified in the XML config file
	 * 
	 * @param b            the Board to be initialized
	 * @param initializers 0 .. many general initializers for a board location
	 */
	private void initializeHexBoard(TwoDimensionalBoard b, LocationInitializer... initializers) {
		for (LocationInitializer li : initializers) {
			HexCoordinate c = HexCoordinate.makeCoordinate(li.x, li.y);
			if (li.pieceName != null) {
				b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
			}

			if (li.locationType != null && li.locationType != CLEAR) {
				b.setLocationType(c, li.locationType);
			}
		}
	}
}
