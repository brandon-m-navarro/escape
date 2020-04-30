/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape;

import static escape.board.LocationType.CLEAR;
import java.io.*;
import javax.xml.bind.*;
import escape.board.*;
import escape.board.coordinate.*;
import escape.piece.EscapePiece;
import escape.util.*;

/**
 * This class is what a client will use to create an instance of a game, given
 * an Escape game configuration file. The configuration file contains the 
 * information needed to create an instance of the Escape game.
 * @version Apr 22, 2020
 */
public class EscapeGameBuilder
{
    private EscapeGameInitializer gameInitializer;
    
    /**
     * The constructor takes a file that points to the Escape game
     * configuration file. It should get the necessary information 
     * to be ready to create the game manager specified by the configuration
     * file and other configuration files that it links to.
     * @param fileName the file for the Escape game configuration file.
     * @throws Exception 
     */
    public EscapeGameBuilder(File fileName) throws Exception
    {
        JAXBContext contextObj = JAXBContext.newInstance(EscapeGameInitializer.class);
        Unmarshaller mub = contextObj.createUnmarshaller();
        gameInitializer = 
            (EscapeGameInitializer)mub.unmarshal(new FileReader(fileName));
    }
    
    /**
     * Once the builder is constructed, this method creates the
     * EscapeGameManager instance.
     * @return
     */
    public EscapeGameManager makeGameManager()
    {
    	// Need to create a board
    	Board b = makeBoard();
        return new ConcreteEscapeGameManager(b);
    }
    
    /**
	 * This method simply initializes the appropriate board from the unmarshalled XML
	 * configuration file.
	 * @return a Board with the type specified in the XML config file 
	 */
	public Board makeBoard()
	{
		TwoDimensionalBoard board;
		switch (gameInitializer.getCoordinateType())
		{
			case SQUARE:
				board = new SquareBoard(gameInitializer.getxMax(), gameInitializer.getyMax());
		        initializeSquareBoard(board, gameInitializer.getLocationInitializers());
				return board;
			case ORTHOSQUARE:
				board = new OrthoSquareBoard(gameInitializer.getxMax(), gameInitializer.getyMax());
		        initializeOrthoSquareBoard(board, gameInitializer.getLocationInitializers());
		        return board;
			case HEX:
				board = new HexBoard(gameInitializer.getxMax(), gameInitializer.getyMax());
				initializeHexBoard(board, gameInitializer.getLocationInitializers());
				return board;
		}
		return null;
	}
	
	/**
	 * Initialize each spot on a SquareBoard with either a piece or LocationType, depending what
	 * was specified in the XML config file 
	 * @param b the Board to be initialized
	 * @param initializers 0 .. many general initializers for a board location
	 */
	private void initializeSquareBoard(TwoDimensionalBoard b, LocationInitializer... initializers)
	{
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
	 * Initialize each spot on a OrthoSquareBoard with either a piece or LocationType, depending what
	 * was specified in the XML config file 
	 * @param b the Board to be initialized
	 * @param initializers 0 .. many general initializers for a board location
	 */
	private void initializeOrthoSquareBoard(TwoDimensionalBoard b, LocationInitializer... initializers)
	{
		if (initializers != null)
		{
			for (LocationInitializer li : initializers) {
				OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(li.x, li.y);
				if (li.pieceName != null) {
					b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
				}
				
				if (li.locationType != null && li.locationType != CLEAR) {
					b.setLocationType(c, li.locationType);
				}
			}	
		}
		else
		{
			// Should initialize all spaces as clear 
		}
		
	}
	
	/**
	 * Initialize each spot on a HexBoard with either a piece or LocationType, depending what
	 * was specified in the XML config file 
	 * @param b the Board to be initialized
	 * @param initializers 0 .. many general initializers for a board location
	 */
	private void initializeHexBoard(TwoDimensionalBoard b, LocationInitializer... initializers)
	{
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
