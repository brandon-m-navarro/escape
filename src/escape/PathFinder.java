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

import java.util.*;
import escape.PathFinder.PathFinderNode;
import escape.board.TwoDimensionalBoard;
import escape.board.coordinate.*;
import escape.piece.MovementRules;

/**
 * This class contains the logic needed to perform a BFS search of
 * a grid (represented by a map of Coordinates). A PathFinder is consisted
 * of 0..n PathFinderNode's (PFNs) as well as all data structures needed to build
 * a path from one coordinate to the target.  
 * @version May 10, 2020
 */
public class PathFinder
{
	public static class PathFinderNode
	{
		private TwoDimensionalCoordinate coord;
		private Vector<PathFinderNode> neighbors;
		private Vector<PathFinderNode> path;
		
		public PathFinderNode(TwoDimensionalCoordinate coord)
		{
			this.setCoord(coord);
			this.setNeighbors(new Vector<PathFinderNode>());
			this.setPath(new Vector<PathFinderNode>());
		}

		/**
		 * Adds the node to the path Vector
		 * @param p the PFN to add
		 */
		private void addToPath(PathFinderNode p)
		{
			this.getPath().add(p);
		}
		
		/**
		 * @return the coord
		 */
		public TwoDimensionalCoordinate getCoord()
		{
			return coord;
		}
		/**
		 * @param coord the coord to set
		 */
		public void setCoord(TwoDimensionalCoordinate coord)
		{
			this.coord = coord;
		}

		/**
		 * @return the neighbors
		 */
		public Vector<PathFinderNode> getNeighbors()
		{
			return neighbors;
		}

		/**
		 * @param neighbors the neighbors to set
		 */
		public void setNeighbors(Vector<PathFinderNode> neighbors)
		{
			this.neighbors = neighbors;
		}
		
		/**
		 * Add a neighbor (PFN) to this node
		 * @param p the node to be added
		 */
		public void addNeighbor(PathFinderNode p)
		{
			this.getNeighbors().add(p);
		}

		/**
		 * @return the path
		 */
		public Vector<PathFinderNode> getPath()
		{
			return path;
		}

		/**
		 * @param path the path to set
		 */
		public void setPath(Vector<PathFinderNode> path)
		{
			this.path = path;
		}

	}
	private TwoDimensionalBoard board;
	private PathFinderNode root;
	private MovementRules movementRules;
	private Queue queue;
	private int distanceTravelled;
	private int nodesLeftInLayer;
	private int nodesInNextLayer;
	private Map<TwoDimensionalCoordinate, Boolean> visited;
	private boolean completed;
	private PathFinderNode pathNode;
	
	/**
	 * Constructor for a PathFinder object.
	 * @param b the board used for traversing
	 * @param root the root coordinate where the search starts
	 * @param mr the MovementRules for the moving piece
	 */
	public PathFinder(TwoDimensionalBoard b, PathFinderNode root, MovementRules mr)
	{
		this.setBoard(b);
		this.setRoot(root);
		this.setMovementRules(mr);
		this.setQueue(new LinkedList<PathFinderNode>());
		this.setDistanceTravelled(0);
		this.setNodesLeftInLayer(1);
		this.setNodesInNextLayer(0);
		this.setCompleted(false);
		this.visited = new HashMap<TwoDimensionalCoordinate, Boolean>();
	}
	
	/**
	 * Performs a BFS following DIAGONAL constraints
	 * @param from the root coordinate for the search
	 * @param to the coordinate that is attempted to reach
	 * @return true if the piece is able to get to the coordinate with it's constraints
	 */
	public boolean searchDiagonally(SquareCoordinate from, SquareCoordinate to,
			MovementRules movementRules)
	{
		visited.put(root.getCoord(), true);
		
		this.getQueue().add(this.getRoot());

		while (!this.getQueue().isEmpty())
		{
			PathFinderNode p = this.getQueue().poll();
			if(p.getCoord().equals(to))
			{
				this.setCompleted(true);
				this.setPathNode(p);
				break;
			}
			exploreNeighbors(p);
			
			this.setNodesLeftInLayer(this.getNodesLeftInLayer() - 1);
			if (this.getNodesLeftInLayer() == 0)
			{
				this.setNodesLeftInLayer(this.getNodesInNextLayer());
				this.setNodesInNextLayer(0);
				this.setDistanceTravelled(this.getDistanceTravelled() + 1);
			}
		}
		return this.isCompleted();		
	}

	/**
	 * This method explores all the neighbors of the given node
	 * @param p the node to get the neighbors from
	 */
	private void exploreNeighbors(PathFinderNode p)
	{
		getDiagonalNeighbors(p);
		
		for (PathFinderNode n : p.getNeighbors())
		{
			// Validate bounds
			if (!board.isValidCoordinate(n.getCoord()))
				continue;
			// Skip visited locations
			if (visited.containsKey(n.getCoord()))
			{
				if (visited.get(n.getCoord()))
					continue;
			}
			// Check blocked locations
			if (board.isBlocked(n.getCoord()))
				continue;
			
			// We can now queue the neighbor as a potential spot to look
			visited.put(n.getCoord(), true);
			n.addToPath(p);
			queue.add(n);
			
			this.setNodesInNextLayer(this.getNodesInNextLayer() + 1);
		}
	}

	/**
	 * Collect all neighboring coordinate diagonal from the given coordinate
	 * into the neighbors Vector for the given node. {UL, UR, LL, LR}
	 * @param p the node of interest
	 * @return a Vector<SquareCoordinate> of all diagonal coordinates
	 */
	private void getDiagonalNeighbors(PathFinderNode p)
	{
		p.addNeighbor(new PathFinderNode(SquareCoordinate.makeCoordinate(p.getCoord().getX() - 1, p.getCoord().getY() + 1)));
		p.addNeighbor(new PathFinderNode(SquareCoordinate.makeCoordinate(p.getCoord().getX() + 1, p.getCoord().getY() + 1)));
		p.addNeighbor(new PathFinderNode(SquareCoordinate.makeCoordinate(p.getCoord().getX() - 1, p.getCoord().getY() - 1)));
		p.addNeighbor(new PathFinderNode(SquareCoordinate.makeCoordinate(p.getCoord().getX() + 1, p.getCoord().getY() - 1)));
	}
	

	/**
	 * This method reconstruct the path taken
	 * @return a Vector of coordinates on the path
	 */
	public Vector<TwoDimensionalCoordinate> recreatePath()
	{
		Vector<TwoDimensionalCoordinate> result = new Vector<>();
		
		while (this.pathNode != null)
		{
			result.add(this.pathNode.coord);
			if (!pathNode.path.isEmpty())
				this.pathNode = pathNode.path.firstElement();
			else
				this.pathNode = null;
		}
		return result;
	}
	
	/**
	 * @return the board
	 */
	private TwoDimensionalBoard getBoard(TwoDimensionalBoard b)
	{
		return this.board;
	}
	
	/**
	 * @param b the board to set
	 */
	private void setBoard(TwoDimensionalBoard b)
	{
		this.board = b;
	}
	
	/**
	 * @return the root
	 */
	public PathFinderNode getRoot()
	{
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(PathFinderNode root)
	{
		this.root = root;
	}

	/**
	 * @return the queue
	 */
	public Queue<PathFinderNode> getQueue()
	{
		return queue;
	}

	/**
	 * @param queue the queue to set
	 */
	public void setQueue(Queue<PathFinderNode> queue)
	{
		this.queue = queue;
	}

	/**
	 * @return the distanceTravelled
	 */
	public int getDistanceTravelled()
	{
		return distanceTravelled;
	}

	/**
	 * @param distanceTravelled the distanceTravelled to set
	 */
	public void setDistanceTravelled(int distanceTravelled)
	{
		this.distanceTravelled = distanceTravelled;
	}

	/**
	 * @return the nodesLeftInLayer
	 */
	public int getNodesLeftInLayer()
	{
		return nodesLeftInLayer;
	}

	/**
	 * @param nodesLeftInLayer the nodesLeftInLayer to set
	 */
	public void setNodesLeftInLayer(int nodesLeftInLayer)
	{
		this.nodesLeftInLayer = nodesLeftInLayer;
	}

	/**
	 * @return the nodesInNextLayer
	 */
	public int getNodesInNextLayer()
	{
		return nodesInNextLayer;
	}

	/**
	 * @param nodesInNextLayer the nodesInNextLayer to set
	 */
	public void setNodesInNextLayer(int nodesInNextLayer)
	{
		this.nodesInNextLayer = nodesInNextLayer;
	}

	/**
	 * @return the completed
	 */
	public boolean isCompleted()
	{
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}


	/**
	 * @return the movementRules
	 */
	public MovementRules getMovementRules()
	{
		return movementRules;
	}


	/**
	 * @param movementRules the movementRules to set
	 */
	public void setMovementRules(MovementRules movementRules)
	{
		this.movementRules = movementRules;
	}

	/**
	 * @param p the path to set
	 */
	public void setPathNode(PathFinderNode p)
	{
		this.pathNode = p;
	}
	
}
