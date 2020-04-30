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

package escape.util;

import javax.xml.bind.*;
import static escape.board.coordinate.CoordinateID.*;
import static escape.piece.MovementPatternID.LINEAR;
import static escape.piece.PieceAttributeID.DISTANCE;
import static escape.piece.PieceAttributeType.INTEGER;
import static escape.piece.PieceName.FROG;
import java.io.StringReader;
import static escape.board.LocationType.*;

/**
 * Test of how to get the EscapeGameInitializer.
 * @version Apr 22, 2020
 */
public class EscapeGameInitializerTest
{
    public static void main(String[] args) throws Exception
    {
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        	"<escapeGameInitializer>\n" + 
        	"	<coordinateType>SQUARE</coordinateType>\n" + 
        	"    <!-- Board items -->\n" + 
        	"    <xMax>20</xMax>\n" + 
        	"    <yMax>25</yMax>\n" + 
        	"    <locationInitializers> <!-- An array of these, 0 or more -->\n" + 
        	"        <x>3</x>\n" + 
        	"        <y>4</y>\n" + 
        	"        <locationType>CLEAR</locationType>\n" + 
        	"    </locationInitializers>\n" + 
        	"    <locationInitializers>\n" + 
        	"        <x>5</x>\n" + 
        	"        <y>6</y>\n" + 
        	"        <locationType>BLOCK</locationType>\n" + 
        	"    </locationInitializers>\n" + 
        	"    \n" + 
        	"    <!-- Piece items, an array of pieceTypes, 1 or more -->\n" + 
        	"    <pieceTypes>\n" + 
        	"        <movementPattern>LINEAR</movementPattern>\n" + 
        	"        <pieceName>FROG</pieceName>\n" + 
        	"        <attributes>\n" + 
        	"            <id>DISTANCE</id>\n" + 
        	"            <attrType>INTEGER</attrType>\n" + 
        	"            <intValue>5</intValue>\n" + 
        	"        </attributes>\n" + 
        	"    </pieceTypes>\n" + 
        	"    <pieceTypes>\n" + 
        	"        <attributes>\n" + 
        	"        <movementPattern>LINEAR</movementPattern>\n" + 
        	"        <pieceName>HORSE</pieceName>\n" + 
        	"            <id>UNBLOCK</id>\n" + 
        	"            <attrType>BOOLEAN</attrType>\n" + 
        	"            <booleanValue>false</booleanValue>\n" + 
        	"        </attributes>\n" + 
        	"    </pieceTypes>\n" + 
        	"</escapeGameInitializer>";
        JAXBContext contextObj 
            = JAXBContext.newInstance(EscapeGameInitializer.class);  
        Marshaller mob = contextObj.createMarshaller();  
        mob.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        EscapeGameInitializer egi = new EscapeGameInitializer();
        egi.setxMax(20);
        egi.setyMax(25);
        egi.setCoordinateType(SQUARE);
        egi.setLocationInitializers(
        	new LocationInitializer(3,4,CLEAR, null, null),
        	new LocationInitializer(5, 6, BLOCK, null, null));
        PieceTypeInitializer.PieceAttribute attr = new PieceTypeInitializer.PieceAttribute();
        attr.setAttrType(INTEGER);
        attr.setId(DISTANCE);
        attr.setIntValue(5);
        PieceTypeInitializer pi = new PieceTypeInitializer();
        pi.setPieceName(FROG);
        pi.setMovementPattern(LINEAR);
        pi.setAttributes(attr);
        egi.setPieceTypes(pi);
        
        mob.marshal(egi, System.out);
        
        EscapeGameInitializer egi1;
        Unmarshaller mub = contextObj.createUnmarshaller();
        egi1 = (EscapeGameInitializer)mub.unmarshal(new StringReader(s));
        System.out.println(egi1.toString());
        
    }
}
