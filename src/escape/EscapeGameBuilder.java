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
import java.util.*;
import javax.xml.bind.*;
import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;
import escape.util.*;
import escape.util.PieceTypeInitializer.PieceAttribute;

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
		// Validate the XML configuration
		try 
		{
			validateConfiguration(gameInitializer);
		} 
		catch (EscapeException e)
		{
			throw new EscapeException("ERROR: Invalid configuration file!");
		}

		// Need to create a board
		TwoDimensionalBoard board = (TwoDimensionalBoard) makeBoard();
		
		// Attach movement rules to pieces
		Map<PieceName, MovementRules> rules = bindRules();

		switch (gameInitializer.getCoordinateType())
		{
			case SQUARE:
				return new SquareEscapeGameManager(board, rules);
			case ORTHOSQUARE:
				return new OrthoSquareEscapeGameManager(board, rules);
			case HEX:
				return new HexEscapeGameManager(board, rules);
			default:
				throw new EscapeException("ERROR: Board could not be created!");
		}
	}

	/**
	 * This method binds the the movement rules to each piece by their piece name.
	 * This method assumes that the config file is valid.
	 * @return
	 */
	private Map<PieceName, MovementRules> bindRules()
	{
		Map<PieceName, MovementRules> rules = new HashMap<PieceName, MovementRules>();
		for(PieceTypeInitializer p : gameInitializer.getPieceTypes())
		{
			p.getMovementPattern();
			rules.put(p.getPieceName(), createMovementRules(p));
		}
		return rules;
	}

	/**
	 * This method creates the MovementRules object
	 * @param attributes the PieceAttributes used to initialize
	 * @return an instance of MovementRules
	 */
	private MovementRules createMovementRules(PieceTypeInitializer initializer)
	{
		MovementRules mr = new MovementRules();
		mr.setMovementPattern(initializer.getMovementPattern());
		for(PieceAttribute pa : initializer.getAttributes())
		{
			switch(pa.getId())
			{
				case DISTANCE:
					mr.setMaxDistance(pa.getIntValue());
					break;
				case FLY:
					mr.setCanFly(true);
					mr.setMaxDistance(pa.getIntValue());
					break;
				case VALUE:
					mr.setValue(pa.getIntValue());
					break;
				case JUMP:
					mr.setCanJump(true);
					break;
				case UNBLOCK:
					mr.setCanTravelThroughBlocked(true);
					break;
			}
		}
		return mr;
	}

	/**
	 * This method should perform the validation of a given configuration file.
	 * The given initializer :
	 * 		X - has at LEAST one piece type
	 * 		X - does not define a two rules for a single piece type
	 * 		X - has at least a distance or fly attribute for every piece
	 * 		X - That the MovementPatternID is correct for the given board
	 * @param initializer the given configuration for the game 
	 * @return true i
	 */
	private void validateConfiguration(EscapeGameInitializer initializer)
	{
		if (!validatePieceTypes(initializer.getPieceTypes()))
			throw new EscapeException("ERROR: Invalid PieceType!");
		if (!oneRulePerPiece(initializer.getPieceTypes()))
			throw new EscapeException("ERROR: A PieceName has multiple PieceTypes " + 
									  "associated with it!");
	}

	/**
	 * Validate the attributes for all PieceTypes 
	 * @param pieceTypes the specified PieceTypes in the configuration file
	 * @return true if a valid PieceType
	 */
	private boolean validatePieceTypes(PieceTypeInitializer[] pieceTypes)
	{
		if (pieceTypes == null)
			return false;

		for (PieceTypeInitializer pt : pieceTypes)
		{
			if (!validateAttributes(pt.getAttributes()))
				return false;
		}
		return true;
	}

	/**
	 * This method validates an array of attributes
	 * @param pieceAttributes the attributes of a PieceType
	 * @return true if the attributes are valid
	 */
	private boolean validateAttributes(PieceAttribute[] pieceAttributes)
	{
		ArrayList collectedAttributeIDs = new ArrayList<PieceAttributeID>();
		for (PieceAttribute pa : pieceAttributes)
		{
			if (!collectedAttributeIDs.contains(pa.getId()))
				collectedAttributeIDs.add(pa.getId());
			else
				return false;
		}
		return hasMovementAttribute(collectedAttributeIDs);
	}

	/**
	 * Validate that a given piece has either a FLY or DISTANCE
	 * attribute
	 * @param attributes all the attributes of the Piece
	 * @return true if either a FLY or DISTANCE attribute is specified
	 */
	private boolean hasMovementAttribute(ArrayList<PieceAttributeID> collectedAttributeIDs)
	{
		return (collectedAttributeIDs.contains(PieceAttributeID.FLY) ||
			    collectedAttributeIDs.contains(PieceAttributeID.DISTANCE)
				&&
				!(collectedAttributeIDs.contains(PieceAttributeID.FLY) && 
				  collectedAttributeIDs.contains(PieceAttributeID.DISTANCE)));
	}

	/**
	 * Validate that each PieceName has one associated PieceType
	 * @param pieceTypeInitializers the array of pieceTypesInitializer
	 * @return true if each PieceName has only one PieceType associated with it
	 */
	private boolean oneRulePerPiece(PieceTypeInitializer[] pieceTypeInitializers)
	{
		ArrayList pieces = new ArrayList<PieceName>();
		for (PieceTypeInitializer pt : pieceTypeInitializers)
		{
			if (!pieces.contains(pt.getPieceName()))
				pieces.add(pt.getPieceName());
			else
				return false;
		}
		return true;
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
		if (initializers != null)
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

	}

	/**
	 * Initialize each spot on a HexBoard with either a piece or LocationType, depending what
	 * was specified in the XML config file 
	 * @param b the Board to be initialized
	 * @param initializers 0 .. many general initializers for a board location
	 */
	private void initializeHexBoard(TwoDimensionalBoard b, LocationInitializer... initializers)
	{
		if (initializers != null)
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
}
