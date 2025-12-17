/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Brandon Navarro
 *******************************************************************************/

package escape;

import escape.piece.MovementPatternID;

/**
 * This class contains the constraints that a piece can have for movement
 * @version May 2, 2020
 */
public class MovementRules
{
	private int maxDistance;
	private MovementPatternID movementPattern;
	private int value;
	private boolean canJump;
	private boolean canFly;
	private boolean canTravelThroughBlocked;
	
	public MovementRules()
	{
		this.setMaxDistance(0);
		this.setMovementPattern(null);
		this.setValue(0);
		this.setCanJump(false);
		this.setCanFly(false);
		this.setCanTravelThroughBlocked(false);
	}

	/**
	 * @return the maxDistance
	 */
	public int getMaxDistance()
	{
		return maxDistance;
	}

	/**
	 * @param maxDistance the maxDistance to set
	 */
	public void setMaxDistance(int maxDistance)
	{
		this.maxDistance = maxDistance;
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
	 * @return the canJump
	 */
	public boolean isCanJump()
	{
		return canJump;
	}

	/**
	 * @param canJump the canJump to set
	 */
	public void setCanJump(boolean canJump)
	{
		this.canJump = canJump;
	}

	/**
	 * @return the canFly
	 */
	public boolean isCanFly()
	{
		return canFly;
	}

	/**
	 * @param canFly the canFly to set
	 */
	public void setCanFly(boolean canFly)
	{
		this.canFly = canFly;
	}

	/**
	 * @return the canTravelThroughBlocked
	 */
	public boolean isCanTravelThroughBlocked()
	{
		return canTravelThroughBlocked;
	}

	/**
	 * @param canTravelThroughBlocked the canTravelThroughBlocked to set
	 */
	public void setCanTravelThroughBlocked(boolean canTravelThroughBlocked)
	{
		this.canTravelThroughBlocked = canTravelThroughBlocked;
	}

	/**
	 * @return the value
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value)
	{
		this.value = value;
	}
	
	
}
