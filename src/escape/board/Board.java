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

package escape.board;

import escape.board.coordinate.*;
import escape.piece.EscapePiece;

/**
 * Interface that defines the methods that any board instance must apply.
 * @version Mar 27, 2020
 */
public interface Board<C extends Coordinate>
{
    /**
     * Get the piece at the specified coordinate
     * @param coord the coordinate to inspect
     * @return the piece or null if there is none
     */
    EscapePiece getPieceAt(C coord);
    
    /**
     * Place a piece on the board at a specified location.
     * @param the piece to place. NOTE: if this is null, then if there
     * were a piece at the coordinate, it will be removed.
     * @param coord the coordinate where the piece must be placed
     */
    void putPieceAt(EscapePiece p, C coord);
    
    /**
     * 
     */
    CoordinateID getCoordType();
}
