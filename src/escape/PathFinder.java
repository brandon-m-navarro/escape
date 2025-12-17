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
// import escape.PathFinder.PathFinderNode;
import escape.board.TwoDimensionalBoard;
import escape.board.coordinate.*;
import escape.piece.*;

/**
 * This class contains the logic needed to perform a BFS search of
 * a grid (represented by a map of Coordinates). A PathFinder is consisted
 * of 0..n PathFinderNode's (PFNs) as well as all data structures needed to
 * build
 * a path from one coordinate to the target.
 * 
 * @version May 10, 2020
 */
public class PathFinder {
	public static class PathFinderNode {
		private TwoDimensionalCoordinate coord;
		private Vector<PathFinderNode> neighbors;
		private Vector<PathFinderNode> path;

		public PathFinderNode(TwoDimensionalCoordinate coord) {
			this.setCoord(coord);
			this.setNeighbors(new Vector<PathFinderNode>(8));
			this.setPath(new Vector<PathFinderNode>());
		}

		/**
		 * Adds the node to the path Vector
		 * 
		 * @param p the PFN to add
		 */
		private void addToPath(PathFinderNode p) {
			this.getPath().add(p);
		}

		/**
		 * @return the coord
		 */
		public TwoDimensionalCoordinate getCoord() {
			return coord;
		}

		/**
		 * @param coord the coord to set
		 */
		public void setCoord(TwoDimensionalCoordinate coord) {
			this.coord = coord;
		}

		/**
		 * @return the neighbors
		 */
		public Vector<PathFinderNode> getNeighbors() {
			return neighbors;
		}

		/**
		 * @param neighbors the neighbors to set
		 */
		public void setNeighbors(Vector<PathFinderNode> neighbors) {
			this.neighbors = neighbors;
		}

		/**
		 * Add a neighbor (PFN) to this node
		 * 
		 * @param p the node to be added
		 */
		public void addNeighbor(PathFinderNode p) {
			this.getNeighbors().add(p);
		}

		/**
		 * @return the path
		 */
		public Vector<PathFinderNode> getPath() {
			return path;
		}

		/**
		 * @param path the path to set
		 */
		public void setPath(Vector<PathFinderNode> path) {
			this.path = path;
		}

	}

	private TwoDimensionalBoard board;
	private PathFinderNode root;
	private MovementRules movementRules;
	private Queue<PathFinderNode> queue;
	private int distanceTravelled;
	private int nodesLeftInLayer;
	private int nodesInNextLayer;
	private Map<TwoDimensionalCoordinate, Boolean> visited;
	private boolean completed;
	private PathFinderNode pathNode;
	private CoordinateID coordType;

	/**
	 * Constructor for a PathFinder object.
	 * 
	 * @param b    the board used for traversing
	 * @param root the root coordinate where the search starts
	 * @param mr   the MovementRules for the moving piece
	 */
	public PathFinder(TwoDimensionalBoard b, PathFinderNode root, MovementRules mr) {
		this.setBoard(b);
		this.setRoot(root);
		this.setMovementRules(mr);
		this.setQueue(new LinkedList<PathFinderNode>());
		this.setDistanceTravelled(0);
		this.setNodesLeftInLayer(1);
		this.setNodesInNextLayer(0);
		this.setCompleted(false);
		this.visited = new HashMap<TwoDimensionalCoordinate, Boolean>();
		this.coordType = b.getCoordType();
	}

	/**
	 * Performs a BFS following DIAGONAL constraints and modifies the class
	 * variables to reflect information regarding the search
	 * 
	 * @param from the root coordinate for the search
	 * @param to   the coordinate that is attempted to reach
	 */
	public void searchDiagonally(TwoDimensionalCoordinate from, TwoDimensionalCoordinate to,
			MovementRules movementRules) {
		this.setPathNode(new PathFinderNode(to));
		visited.put(root.getCoord(), true);

		this.getQueue().add(this.getRoot());

		while (!this.getQueue().isEmpty()) {
			PathFinderNode p = this.getQueue().poll();
			if (p.getCoord().equals(to)) {
				this.setCompleted(true);
				this.setPathNode(p);
				break;
			}
			exploreDiagonalNeighbors(p);

			this.setNodesLeftInLayer(this.getNodesLeftInLayer() - 1);
			if (this.getNodesLeftInLayer() == 0) {
				this.setNodesLeftInLayer(this.getNodesInNextLayer());
				this.setNodesInNextLayer(0);
				this.setDistanceTravelled(this.getDistanceTravelled() + 1);
			}
		}
	}

	/**
	 * Performs a BFS following Orthogonal constraints and modifies the class
	 * variables to reflect information regarding the search
	 * 
	 * @param from the root coordinate for the search
	 * @param to   the coordinate that is attempted to reach
	 */
	public void searchOrthogonally(TwoDimensionalCoordinate from, TwoDimensionalCoordinate to,
			MovementRules movementRules) {
		this.setPathNode(new PathFinderNode(to));
		visited.put(root.getCoord(), true);

		this.getQueue().add(this.getRoot());

		while (!this.getQueue().isEmpty()) {
			PathFinderNode p = this.getQueue().poll();
			if (p.getCoord().equals(to)) {
				this.setCompleted(true);
				this.setPathNode(p);
				break;
			}
			exploreOrthogonalNeighbors(p);

			this.setNodesLeftInLayer(this.getNodesLeftInLayer() - 1);
			if (this.getNodesLeftInLayer() == 0) {
				this.setNodesLeftInLayer(this.getNodesInNextLayer());
				this.setNodesInNextLayer(0);
				this.setDistanceTravelled(this.getDistanceTravelled() + 1);
			}
		}
	}

	/**
	 * Performs a BFS following Omni constraints and modifies the class
	 * variables to reflect information regarding the search
	 * 
	 * @param from the root coordinate for the search
	 * @param to   the coordinate that is attempted to reach
	 */
	public void searchOmni(TwoDimensionalCoordinate from, TwoDimensionalCoordinate to,
			MovementRules movementRules) {
		this.setPathNode(new PathFinderNode(to));
		visited.put(root.getCoord(), true);

		this.getQueue().add(this.getRoot());

		while (!this.getQueue().isEmpty()) {
			PathFinderNode p = this.getQueue().poll();
			if (p.getCoord().equals(to)) {
				this.setCompleted(true);
				this.setPathNode(p);
				break;
			}
			exploreOmniNeighbors(p);

			this.setNodesLeftInLayer(this.getNodesLeftInLayer() - 1);
			if (this.getNodesLeftInLayer() == 0) {
				this.setNodesLeftInLayer(this.getNodesInNextLayer());
				this.setNodesInNextLayer(0);
				this.setDistanceTravelled(this.getDistanceTravelled() + 1);
			}
		}
	}

	/**
	 * This method adds a viable neighbor to the queue
	 * as a potential spot to look
	 * 
	 * @param n the node to add
	 * @param p the node that it's coming from
	 */
	private void addValidNeighbor(PathFinderNode n, PathFinderNode p) {
		visited.put(n.getCoord(), true);
		n.addToPath(p);
		queue.add(n);
		this.setNodesInNextLayer(this.getNodesInNextLayer() + 1);
	}

	/**
	 * This method explores all the neighbors of the given node
	 * 
	 * @param p the node to get the neighbors from
	 */
	private void exploreOmniNeighbors(PathFinderNode p) {
		getOrthogonalNeighbors(p);
		getDiagonalNeighbors(p);

		for (PathFinderNode n : p.getNeighbors()) {
			// Validate bounds
			if (!board.isValidCoordinate(n.getCoord()))
				continue;

			// Skip visited locations
			if (visited.containsKey(n.getCoord())) {
				if (visited.get(n.getCoord()))
					continue;
			}

			// Check if trying to move into another piece
			if (board.getPieceAt(n.getCoord()) != null) {
				EscapePiece encounteredPiece = board.getPieceAt(n.getCoord());
				// Check to see if we're at the ending node
				if (this.pathNode.getCoord().equals(n.getCoord())) {
					// Check if the other piece is on the enemy team
					if (encounteredPiece.getPlayer() != board.getPieceAt(this.getRoot().getCoord()).getPlayer()) {
						// We should move here and return true
						addValidNeighbor(n, p);
						return;
					}
				} else if (this.getMovementRules().isCanFly()) {
					addValidNeighbor(n, p);
					continue;
				} else if (this.getMovementRules().isCanJump()) {
					if (isJumpPossible(n, getDirection(p.getCoord(), n.getCoord()))) {
						continue;
					} else
						continue;
				} else
					continue;
			}

			// Check if we are moving into a BLOCKED spot
			if (board.isBlocked(n.getCoord())) {
				if (this.getMovementRules().isCanFly()) {
					addValidNeighbor(n, p);
					continue;
				} else if (this.getMovementRules().isCanTravelThroughBlocked()) {
					addValidNeighbor(n, p);
					continue;
				} else
					continue;
			}

			// Check if we are moving into a BLOCKED spot
			if (board.isExit(n.getCoord())) {
				if (this.getMovementRules().isCanFly()) {
					addValidNeighbor(n, p);
					continue;
				} else
					continue;
			}

			// We can now queue the neighbor as a potential spot to look
			addValidNeighbor(n, p);
		}
	}

	/**
	 * This method explores all the neighbors of the given node
	 * 
	 * @param p the node to get the neighbors from
	 */
	private void exploreOrthogonalNeighbors(PathFinderNode p) {
		getOrthogonalNeighbors(p);

		for (PathFinderNode n : p.getNeighbors()) {
			// Validate bounds
			if (!board.isValidCoordinate(n.getCoord()))
				continue;

			// Skip visited locations
			if (visited.containsKey(n.getCoord())) {
				if (visited.get(n.getCoord()))
					continue;
			}

			// Check if trying to move into another piece
			if (board.getPieceAt(n.getCoord()) != null) {
				EscapePiece encounteredPiece = board.getPieceAt(n.getCoord());
				// Check to see if we're at the ending node
				if (this.pathNode.getCoord().equals(n.getCoord())) {
					// Check if the other piece is on the enemy team
					if (encounteredPiece.getPlayer() != board.getPieceAt(this.getRoot().getCoord()).getPlayer()) {
						// We should move here and return true
						addValidNeighbor(n, p);
						return;
					}
				} else if (this.getMovementRules().isCanFly()) {
					addValidNeighbor(n, p);
					continue;
				} else if (this.getMovementRules().isCanJump()) {
					if (isJumpPossible(n, getDirection(p.getCoord(), n.getCoord()))) {
						continue;
					} else
						continue;
				} else
					continue;
			}

			// Check if we are moving into a BLOCKED spot
			if (board.isBlocked(n.getCoord())) {
				if (this.getMovementRules().isCanFly()) {
					addValidNeighbor(n, p);
					continue;
				} else if (this.getMovementRules().isCanTravelThroughBlocked()) {
					addValidNeighbor(n, p);
					continue;
				} else
					continue;
			}

			// Check if we are moving into a BLOCKED spot
			if (board.isExit(n.getCoord())) {
				if (this.getMovementRules().isCanFly()) {
					addValidNeighbor(n, p);
					continue;
				} else
					continue;
			}

			// We can now queue the neighbor as a potential spot to look
			addValidNeighbor(n, p);
		}
	}

	/**
	 * This method explores all the neighbors of the given node
	 * 
	 * @param p the node to get the neighbors from
	 */
	private void exploreDiagonalNeighbors(PathFinderNode p) {
		getDiagonalNeighbors(p);

		for (PathFinderNode n : p.getNeighbors()) {
			// Validate bounds
			if (!board.isValidCoordinate(n.getCoord()))
				continue;

			// Skip visited locations
			if (visited.containsKey(n.getCoord())) {
				if (visited.get(n.getCoord()))
					continue;
			}

			// Check if trying to move into another piece
			if (board.getPieceAt(n.getCoord()) != null) {
				EscapePiece encounteredPiece = board.getPieceAt(n.getCoord());
				// Check to see if we're at the ending node
				if (this.pathNode.getCoord().equals(n.getCoord())) {
					// Check if the other piece is on the enemy team
					if (encounteredPiece.getPlayer() != board.getPieceAt(this.getRoot().getCoord()).getPlayer()) {
						// We should move here and return true
						addValidNeighbor(n, p);
						return;
					}
				} else if (this.getMovementRules().isCanFly()) {
					addValidNeighbor(n, p);
					continue;
				} else if (this.getMovementRules().isCanJump()) {
					if (isJumpPossible(n, getDirection(p.getCoord(), n.getCoord())))
						continue;
					else
						continue;
				} else
					continue;
			}

			// Check if we are moving into a BLOCKED spot
			if (board.isBlocked(n.getCoord())) {
				if (this.getMovementRules().isCanFly()) {
					addValidNeighbor(n, p);
					continue;
				} else if (this.getMovementRules().isCanTravelThroughBlocked()) {
					addValidNeighbor(n, p);
					continue;
				} else
					continue;
			}

			// Check if we are moving into a BLOCKED spot
			if (board.isExit(n.getCoord())) {
				if (this.getMovementRules().isCanFly()) {
					addValidNeighbor(n, p);
					continue;
				} else
					continue;
			}

			// We can now queue the neighbor as a potential spot to look
			addValidNeighbor(n, p);
		}
	}

	/**
	 * This method looks at if a path can exist using
	 * a jump. Basically checks one spot ahead of the
	 * given node in the given direction to see whether or
	 * not a jump could be completed.
	 * 
	 * @param p   the node we're looking from
	 * @param dir the direction we're jumping
	 * @return true if a jump can be made
	 */
	private boolean isJumpPossible(PathFinderNode p, Vector<Direction> dir) {
		int x = p.getCoord().getX();
		int y = p.getCoord().getY();

		for (Direction d : dir) {
			switch (d) {
				case UP:
					x++;
					break;
				case DOWN:
					x--;
					break;
				case LEFT:
					y--;
					break;
				case RIGHT:
					y++;
					break;
				case DIAGONAL:
					break;
				default:
					break;
			}
		}

		TwoDimensionalCoordinate coord = CoordinateFactory.makeCoordinate(x, y, this.coordType);

		if (board.isBlocked(coord))
			return false;

		if (board.getPieceAt(coord) != null)
			return false;

		if (!board.isValidCoordinate(coord))
			return false;

		addValidNeighbor(new PathFinderNode(coord), p);
		return true;
	}

	/**
	 * Given two adjacent coordinates, this returns a vector describing
	 * the direction from one to the other
	 * 
	 * @param from
	 * @param to
	 * @return a Direction enum
	 */
	private Vector<Direction> getDirection(TwoDimensionalCoordinate from,
			TwoDimensionalCoordinate to) {
		Vector<Direction> results = new Vector<Direction>();
		if (isDiagonal(from, to)) {
			results = calcDiagonalOrientation(from, to);
			return results;
		} else if ((from.getX() == to.getX()) && (from.getY() > to.getY())) {
			results.add(Direction.LEFT);
			return results;
		} else if ((from.getX() == to.getX()) && (from.getY() < to.getY())) {
			results.add(Direction.RIGHT);
			return results;
		} else if ((from.getX() > to.getX()) && (from.getY() == to.getY())) {
			results.add(Direction.DOWN);
			return results;
		} else if ((from.getX() < to.getX()) && (from.getY() == to.getY())) {
			results.add(Direction.UP);
			return results;
		} else
			return results;
	}

	/**
	 * Determines whether or not the two coordinates are diagonal from
	 * each other by examining if the rows for both coordinates differ,
	 * and the columns for both coordinates differ. If both coordinates are
	 * the same, they are not diagonal from one another
	 * 
	 * @param c1 the first coordinate
	 * @param c2 the second coordinate
	 * @return boolean on if the two cooordinates are diagonal from each other
	 */
	private static boolean isDiagonal(TwoDimensionalCoordinate c1, TwoDimensionalCoordinate c2) {
		return (c1.getY() - c2.getY() == c1.getX() - c2.getX() ||
				c1.getY() - c2.getY() == c2.getX() - c1.getX());
	}

	/**
	 * This method uses Coordinate values to determine the
	 * orientation of the diagonal in standard square boards
	 * 
	 * @param c1 the Coordinate that we start from
	 * @param c2 the Coordinate we are ending on
	 * @return a vector of Directions
	 */
	private static Vector<Direction> calcDiagonalOrientation(TwoDimensionalCoordinate c1, TwoDimensionalCoordinate c2) {
		Vector<Direction> v = new Vector<>();
		if ((c1.getX() - c2.getX()) < 0)
			v.add(Direction.UP);
		else
			v.add(Direction.DOWN);
		if ((c1.getY() - c2.getY()) < 0)
			v.add(Direction.RIGHT);
		else
			v.add(Direction.LEFT);
		return v;
	}

	/**
	 * Collect all neighboring coordinate diagonal from the given coordinate
	 * into the neighbors Vector for the given node. {UL, UR, LL, LR}
	 * 
	 * @param p the node of interest
	 * @return a Vector<TwoDimensionalCoordinate> of all diagonal coordinates
	 */
	private void getDiagonalNeighbors(PathFinderNode p) {
		p.addNeighbor(new PathFinderNode(
				CoordinateFactory.makeCoordinate(p.getCoord().getX() - 1, p.getCoord().getY() + 1, this.coordType)));
		p.addNeighbor(new PathFinderNode(
				CoordinateFactory.makeCoordinate(p.getCoord().getX() + 1, p.getCoord().getY() + 1, this.coordType)));
		p.addNeighbor(new PathFinderNode(
				CoordinateFactory.makeCoordinate(p.getCoord().getX() - 1, p.getCoord().getY() - 1, this.coordType)));
		p.addNeighbor(new PathFinderNode(
				CoordinateFactory.makeCoordinate(p.getCoord().getX() + 1, p.getCoord().getY() - 1, this.coordType)));
	}

	/**
	 * Collect all neighboring coordinate orthogonally from the given coordinate
	 * into the neighbors Vector for the given node. {U, R, D, L}
	 * 
	 * @param p the node of interest
	 * @return a Vector<TwoDimensionalCoordinate> of all orthognal coordinates
	 */
	private void getOrthogonalNeighbors(PathFinderNode p) {
		p.addNeighbor(new PathFinderNode(
				CoordinateFactory.makeCoordinate(p.getCoord().getX() + 1, p.getCoord().getY(), this.coordType)));
		p.addNeighbor(new PathFinderNode(
				CoordinateFactory.makeCoordinate(p.getCoord().getX(), p.getCoord().getY() + 1, this.coordType)));
		p.addNeighbor(new PathFinderNode(
				CoordinateFactory.makeCoordinate(p.getCoord().getX() - 1, p.getCoord().getY(), this.coordType)));
		p.addNeighbor(new PathFinderNode(
				CoordinateFactory.makeCoordinate(p.getCoord().getX(), p.getCoord().getY() - 1, this.coordType)));
	}

	/**
	 * This method reconstruct the path taken
	 * 
	 * @return a Vector of coordinates on the path
	 */
	public Vector<TwoDimensionalCoordinate> recreatePath() {
		Vector<TwoDimensionalCoordinate> result = new Vector<>();

		while (this.pathNode != null) {
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
	// private TwoDimensionalBoard getBoard(TwoDimensionalBoard b) {
	// 	return this.board;
	// }

	/**
	 * @param b the board to set
	 */
	private void setBoard(TwoDimensionalBoard b) {
		this.board = b;
	}

	/**
	 * @return the root
	 */
	public PathFinderNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(PathFinderNode root) {
		this.root = root;
	}

	/**
	 * @return the queue
	 */
	public Queue<PathFinderNode> getQueue() {
		return queue;
	}

	/**
	 * @param queue the queue to set
	 */
	public void setQueue(Queue<PathFinderNode> queue) {
		this.queue = queue;
	}

	/**
	 * @return the distanceTravelled
	 */
	public int getDistanceTravelled() {
		return distanceTravelled;
	}

	/**
	 * @param distanceTravelled the distanceTravelled to set
	 */
	public void setDistanceTravelled(int distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	/**
	 * @return the nodesLeftInLayer
	 */
	public int getNodesLeftInLayer() {
		return nodesLeftInLayer;
	}

	/**
	 * @param nodesLeftInLayer the nodesLeftInLayer to set
	 */
	public void setNodesLeftInLayer(int nodesLeftInLayer) {
		this.nodesLeftInLayer = nodesLeftInLayer;
	}

	/**
	 * @return the nodesInNextLayer
	 */
	public int getNodesInNextLayer() {
		return nodesInNextLayer;
	}

	/**
	 * @param nodesInNextLayer the nodesInNextLayer to set
	 */
	public void setNodesInNextLayer(int nodesInNextLayer) {
		this.nodesInNextLayer = nodesInNextLayer;
	}

	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * @return the movementRules
	 */
	public MovementRules getMovementRules() {
		return movementRules;
	}

	/**
	 * @param movementRules the movementRules to set
	 */
	public void setMovementRules(MovementRules movementRules) {
		this.movementRules = movementRules;
	}

	/**
	 * @param p the path to set
	 */
	public void setPathNode(PathFinderNode p) {
		this.pathNode = p;
	}

}
