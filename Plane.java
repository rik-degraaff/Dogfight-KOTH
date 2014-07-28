package Planes;

//Objects of this class contain all relevant information about a plane
//as well as some helper functions.
public class Plane {
	private int x, y, z;
	private Direction direction;
	private int arenaSize;
	private boolean alive = true;
	private int coolDown = 0;
 
	public Plane(int arenaSize) {
		this.arenaSize = arenaSize;
	}    
 
	// Returns the x coordinate of the plane
	public int getX() {
		return (alive)?x:-1;
	}
 
	// Returns the y coordinate of the plane
	public int getY() {
		return (alive)?y:-1;
	}
	 
	// Returns the z coordinate of the plane
	public int getZ() {
		return (alive)?z:-1;
	}
	 
	// Returns the distance between the plane and the specified wall,
	// 0 means right next to it, 19 means at the opposite side.
	// Returns -1 for invalid input.
	public int getDistanceFromWall(char wall) {
	    if (alive) {
	        switch (wall) {
	            case 'N':
	                return x;
	            case 'S':
	                return arenaSize - x - 1;
	            case 'W':
	                return y;
	            case 'E':
	                return arenaSize - y - 1;
	            case 'D':
	                return z;
	            case 'U':
	                return arenaSize - z - 1;
	            default:
	                return -1;
	        }
	    } else {
	        return -1;
	    }
	}
	 
	// Returns the direction of the plane.
	public Direction getDirection() {
	    if (alive) {
	        return direction;
	    } else {
	        return null;
	    }
	}
	 
	// Returns all possible turning directions for the plane.
	public Direction[] getPossibleDirections() {
	    if (alive) {
	        return direction.getPossibleDirections();
	    } else {
	        return null;
	    }        
	}
	 
	// Returns the cool down before the plane will be able to shoot, 
	// 0 means it is ready to shoot this turn.
	public int getCoolDown() {
	    return coolDown;
	}
	 
	// Returns true if the plane is ready to shoot
	public boolean canShoot() {
	    return coolDown == 0;
	}
	 
	// Returns all positions this plane can shoot at (without first making a move).
	// Not done yet.
	public Point3D[] getShootRange() {
		return null;
	     
	}
	 
	// Returns all positions this plane can move to within one turn.
	// Not done yet.
	public Point3D[] getRange() {
		return null;
	     
	}
	 
	// Returns a plane that represents this plane after making a certain move,
	// not taking into account other planes.
	// Not done yet.
	public Plane simulateMove(Move move) {
	return null;
	     
	} 
}
