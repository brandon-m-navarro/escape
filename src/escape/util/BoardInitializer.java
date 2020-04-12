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

import java.util.Arrays;
import javax.xml.bind.annotation.*;
import escape.board.coordinate.CoordinateID;

/**
 * This class is basically a Java Bean that contains all of the information
 * needed to initialize a Board.
 * @version Mar 30, 2020
 */
@XmlRootElement
public class BoardInitializer
{
	private int xMax, yMax;
	private CoordinateID coordinateId;
	private LocationInitializer[] locationInitializers;
	
	public BoardInitializer()
	{
	    // Needed for JAXB
	}
	
    /**
     * @return the xMax
     */
	@XmlElement
    public int getxMax()
    {
        return xMax;
    }
	
    /**
     * @param xMax the xMax to set
     */
    public void setxMax(int xMax)
    {
        this.xMax = xMax;
    }
    
    /**
     * @return the yMax
     */
    @XmlElement
    public int getyMax()
    {
        return yMax;
    }
    
    /**
     * @param yMax the yMax to set
     */
    public void setyMax(int yMax)
    {
        this.yMax = yMax;
    }
    
    /**
     * @return the coordinateId
     */
    @XmlElement
    public CoordinateID getCoordinateId()
    {
        return coordinateId;
    }
    
    /**
     * @param coordinateId the coordinateId to set
     */
    public void setCoordinateId(CoordinateID coordinateId)
    {
        this.coordinateId = coordinateId;
    }
    
    /**
     * @return the locationInitializers
     */
    @XmlElement
    public LocationInitializer[] getLocationInitializers()
    {
        return locationInitializers;
    }
    
    /**
     * @param locationInitializers the locationInitializers to set
     */
    public void setLocationInitializers(LocationInitializer... locationInitializers)
    {
        this.locationInitializers = locationInitializers;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "BoardInitializer [xMax=" + xMax + ", yMax=" + yMax
            + ", coordinateId=" + coordinateId + ", locationInitializers="
            + Arrays.toString(locationInitializers) + "]";
    }
}
