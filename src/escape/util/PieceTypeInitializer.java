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

import java.util.Arrays;
import escape.piece.*;

/**
 * Description
 * @version Apr 21, 2020
 */
public class PieceTypeInitializer
{
    /**
     * Inner class that's a Java Bean.
     * @version Apr 21, 2020
     */
    public static class PieceAttribute {
    	
        PieceAttributeID id;
        PieceAttributeType attrType;
        int intValue;
        boolean booleanValue;
        
        public PieceAttribute() {}
        
        public PieceAttributeID getId() { return id; }
        public void setId(PieceAttributeID id) { this.id = id; }
        public int getIntValue() { return intValue; }
        public void setIntValue(int intValue) { this.intValue = intValue; }
        public boolean isBooleanValue() { return booleanValue;  }
        public void setBooleanValue(boolean booleanValue) { this.booleanValue = booleanValue; }

		/**
		 * @return the attrType
		 */
		public PieceAttributeType getAttrType()
		{
			return attrType;
		}

		/**
		 * @param attrType the attrType to set
		 */
		public void setAttrType(PieceAttributeType attrType)
		{
			this.attrType = attrType;
		}

		/*
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "PieceAttribute [id=" + id + ", attrType=" + attrType
			    + ", intValue=" + intValue + ", booleanValue=" + booleanValue + "]";
		}
    }
    
    private PieceName pieceName;
    private MovementPatternID movementPattern;
    private PieceAttribute[] attributes;
    
    public PieceTypeInitializer() {}
    
    /**
     * @return the pieceName
     */
    public PieceName getPieceName()
    {
        return pieceName;
    }
    /**
     * @param pieceName the pieceName to set
     */
    public void setPieceName(PieceName pieceName)
    {
        this.pieceName = pieceName;
    }
    /**
     * @return the movementPattern
     */
    public MovementPatternID getMovementPattern()
    {
        return movementPattern;
    }
    /**
     * @param movementPattern the movementPattern to set
     */
    public void setMovementPattern(MovementPatternID movementPattern)
    {
        this.movementPattern = movementPattern;
    }
    /**
     * @return the attributes
     */
    public PieceAttribute[] getAttributes()
    {
        return attributes;
    }
    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(PieceAttribute ... attributes)
    {
        this.attributes = attributes;
    }

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "PieceTypeInitializer [pieceName=" + pieceName + ", movementPattern="
		    + movementPattern + ", attributes=" + Arrays.toString(attributes) + "]";
	}
    
    
}
