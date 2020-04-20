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
package escape.util;

import static escape.piece.PieceName.*;
import java.io.StringReader;
import javax.xml.bind.*;
import escape.board.coordinate.CoordinateID;
import static escape.piece.Player.*;
import static escape.board.LocationType.*;

/**
 * An example of how to use the JAXB classes for binding XML files to Java classes.
 * @version Mar 30, 2020
 */
public class InitializerTest
{
	public static void main(String[] args) throws Exception
	{
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
			"<boardInitializer>\n" + 
			"    <coordinateId>SQUARE</coordinateId>\n" + 
			"    <locationInitializers>\n" + 
			"        <x>2</x>\n" + 
			"        <y>2</y>\n" + 
			"        <locationType>CLEAR</locationType>\n" + 
			"        <player>PLAYER1</player>\n" + 
			"        <pieceName>HORSE</pieceName>\n" + 
			"    </locationInitializers>\n" + 
			"    <locationInitializers>\n" + 
			"        <x>3</x>\n" + 
			"        <y>5</y>\n" + 
			"        <locationType>BLOCK</locationType>\n" + 
			"    </locationInitializers>\n" + 
			"    <xMax>8</xMax>\n" + 
			"    <yMax>8</yMax>\n" + 
			"</boardInitializer>"
			;
		
		JAXBContext contextObj = JAXBContext.newInstance(BoardInitializer.class);  
        
        Marshaller mob = contextObj.createMarshaller();  
        mob.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        BoardInitializer bi = new BoardInitializer();
		bi.setxMax(8);
		bi.setyMax(8);
		bi.setCoordinateId(CoordinateID.SQUARE);
		bi.setLocationInitializers(
		    new LocationInitializer(2, 2, CLEAR, PLAYER1, HORSE),
		    new LocationInitializer(3, 5, BLOCK, null, null)
		    );
        mob.marshal(bi, System.out);
        
        BoardInitializer bi1;
        Unmarshaller mub = contextObj.createUnmarshaller();
        bi1 = (BoardInitializer)mub.unmarshal(new StringReader(s));
        System.out.println(bi1.toString());
	}
}
