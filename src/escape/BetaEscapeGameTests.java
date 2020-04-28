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

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;

/**
 * Description
 * @version Apr 24, 2020
 */
class BetaEscapeGameTests
{
    
    /**
     * Example of how the game manager tests will be structured.
     * @throws Exception
     */
    @Test
    void test() throws Exception
    {
        EscapeGameBuilder egb 
            = new EscapeGameBuilder(new File("config/SampleEscapeGame.xml"));
        EscapeGameManager emg = egb.makeGameManager();
        // Exercise the game now: make moves, check the board, etc.
    }
    
}
