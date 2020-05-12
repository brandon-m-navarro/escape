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

package escape.master;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.*;

/**
 * Base class for each of the new tests.
 * @version May 7, 2020
 */
class AbstractMasterTest
{
    static String masterTestLocation = "config/master/";
    static String fileName;
    EscapeGameManager game;
    
    @BeforeEach
    void setup() throws Exception
    {
        EscapeGameBuilder egb = new EscapeGameBuilder(new File(fileName));
        game = egb.makeGameManager();
    }
}
