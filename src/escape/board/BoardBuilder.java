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
import escape.board.coordinate1.*;
import escape.piece.EscapePiece;
import escape.util.*;

/**
 * A Builder class for creating Boards. It is only an example and builds
 * just Square boards. If you choose to use this
 * @version Apr 2, 2020
 */
public class BoardBuilder
{
	private BoardInitializer bi;
	/**
	 * The constructor for this takes a file name. It is either an absolute path
	 * or a path relative to the beginning of this project.
	 * @param fileName the absolute or relative path to the file that contains
	 * 				   the XML configuration of the board
	 * @throws FileNotFoundException if the file does not exist, is a directory 
	 * 					 			 rather than a regular file, or for some other 
	 * 								 reason cannot be opened for reading.
	 */
	public BoardBuilder(File fileName) throws Exception
	{
		JAXBContext contextObj = JAXBContext.newInstance(BoardInitializer.class);
        Unmarshaller mub = contextObj.createUnmarshaller();
        bi = (BoardInitializer)mub.unmarshal(new FileReader(fileName));
	}
	
	public Board makeBoard()
	{
		TwoDimensionalBoard board;
		switch (bi.getCoordinateId())
		{
			case SQUARE:
				board = new SquareBoard(bi.getxMax(), bi.getyMax());
		        initializeSquareBoard((SquareBoard) board, bi.getLocationInitializers());
				return board;
			case ORTHOSQUARE:
				board = new OrthoSquareBoard(bi.getxMax(), bi.getyMax());
		        initializeOrthoSquareBoard((OrthoSquareBoard) board, bi.getLocationInitializers());
		        return board;
			case HEX:
				board = new HexBoard(bi.getxMax(), bi.getyMax());
				initializeHexBoard((HexBoard) board, bi.getLocationInitializers());
				return board;
		}
		return null;
	}
	
	private void initializeSquareBoard(SquareBoard b, LocationInitializer... initializers)
	{
		for (LocationInitializer li : initializers) {
			SquareCoordinate c = (SquareCoordinate) SquareCoordinate.makeCoordinate(li.x, li.y);
			if (li.pieceName != null) {
				b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
			}
			
			if (li.locationType != null && li.locationType != CLEAR) {
				b.setLocationType(c, li.locationType);
			}
		}
	}
	
	private void initializeOrthoSquareBoard(OrthoSquareBoard b, LocationInitializer... initializers)
	{
		for (LocationInitializer li : initializers) {
			OrthoSquareCoordinate c = (OrthoSquareCoordinate) OrthoSquareCoordinate.makeCoordinate(li.x, li.y);
			if (li.pieceName != null) {
				b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
			}
			
			if (li.locationType != null && li.locationType != CLEAR) {
				b.setLocationType(c, li.locationType);
			}
		}
	}
	
	private void initializeHexBoard(HexBoard b, LocationInitializer... initializers)
	{
		for (LocationInitializer li : initializers) {
			HexCoordinate c = (HexCoordinate) HexCoordinate.makeCoordinate(li.x, li.y);
			if (li.pieceName != null) {
				b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
			}
			
			if (li.locationType != null && li.locationType != CLEAR) {
				b.setLocationType(c, li.locationType);
			}
		}
	}
}
