package Planes;

//Objects of this class contain all relevant information about a plane
//as well as some helper functions.
public class Plane {
	private Point3D position;
	private Direction direction;
	private int arenaSize;
	private boolean alive = true;
	private int coolDown = 0;
 
	public Plane(int arenaSize, int coolDown, Direction direction, int x, int y, int z) {
		this.arenaSize = arenaSize;
		this.position = new Point3D(x, y, z);
		alive = position.isInArena(arenaSize);
		this.direction = direction;
		this.coolDown = coolDown;
	}

	public Plane(int arenaSize, int coolDown, Direction direction, Point3D position) {
		this(arenaSize, coolDown, direction, position.x, position.y, position.z);
	}
 
	// Returns the x coordinate of the plane
	public int getX() {
		return (alive)?position.x:-1;
	}
 
	// Returns the y coordinate of the plane
	public int getY() {
		return (alive)?position.y:-1;
	}

	// Returns the z coordinate of the plane
	public int getZ() {
		return (alive)?position.z:-1;
	}

	// Returns the position as a Point3D.
	public Point3D getPosition() {
		return position;
	}

	// Returns the distance between the plane and the specified wall,
	// 0 means right next to it, (arenaSize - 1) means at the opposite side.
	// Returns -1 for invalid input.
	public int getDistanceFromWall(char wall) {
	    if (alive) {
	        switch (wall) {
	            case 'N':
	                return position.x;
	            case 'S':
	                return arenaSize - position.x - 1;
	            case 'W':
	                return position.y;
	            case 'E':
	                return arenaSize - position.y - 1;
	            case 'D':
	                return position.z;
	            case 'U':
	                return arenaSize - position.z - 1;
	            default:
	                return -1;
	        }
	    } else {
	        return -1;
	    }
	}

	// Returns the direction of the plane.
	public Direction getDirection() {
	    return direction;
	}

	// Returns all possible turning directions for the plane.
	public Direction[] getPossibleDirections() {
	    if (alive) {
	        return direction.getPossibleDirections();
	    } else {
	        return new Direction[0];
	    }        
	}

	// Returns the cool down before the plane will be able to shoot, 
	// 0 means it is ready to shoot this turn.
	public int getCoolDown() {
	    return coolDown;
	}

	public void setCoolDown(int coolDown) {
		this.coolDown = coolDown;
	}

	// Returns true if the plane is ready to shoot
	public boolean canShoot() {
	    return coolDown == 0 && alive;
	}

	// Returns all positions this plane can shoot at (without first making a move).
	public Point3D[] getShootRange() {		
		if (alive) {
			int maxDistance = arenasize;

			for (int i=0; i<direction.getMainDirections().length; i++) {
				maxDistance = Math.min(maxDistance, getDistanceFromWall(direction.getMainDirections()[i]));
			}

			Point3D[] range = new Point3D[maxDistance];

			for (int i=0; i<maxDistance; i++) {
				range[i] = position.add(direction.getAsPoint3D().multiply(i+1));
			}

			return range;
		} else {
			return null;
		}
	}

	// Returns all positions this plane can move to within one turn.
	public Point3D[] getRange() {
		if (alive) {
			Direction[] directions = getPossibleDirections();
			Point3D[] range = new Point3D[directions.length];

			for (int i=0; i<directions.length; i++) {
				range[i] = position.add(directions[i].getAsPoint3D());
			}

			return range;
		} else {
			return null;
		}

	}

	// Returns a plane that represents this plane after making a certain move,
	// not taking into account other planes.
	// Doesn't update cool down, see updateCoolDown() for that.
	public Plane simulateMove(Move move) {
		if (!alive) {
			return copy();
		}

		Direction newDirection;

		if (!direction.isValidDirection(move.direction)) {
			move.direction = direction; // If a direction is invalid, you fly straight ahead.
		}

		if (move.changeDirection) {
			newDirection = move.direction;
		} else {
			newDirection = direction;
		}
		
		return new Plane(arenaSize, coolDown, newDirection, position.add(move.direction.getAsPoint3D()));
	}
	
	// modifies this plane's cool down
	public void updateCoolDown(boolean shot) {
		coolDown = (shot && canShoot())?Controller.COOLDOWN:Math.max(0, coolDown - 1);
	}

	// Returns true if the plane is alive.
	public boolean isAlive() {
		return alive;
	}

	// Sets alive to the specified value.
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	// Returns a copy of itself.
	public Plane copy() {
		Plane copyPlane = new Plane(arenaSize, coolDown, new Direction(direction.getNSDir(), direction.getWEDir(), direction.getDUDir()), new Point3D(position.x, position.y, position.z));
		copyPlane.setAlive(alive);
		return copyPlane;
	}

	// Returns a string representing its status.
	public String getAsString() {
		if (!alive) {
			return "dead";
		}
		return "x: " + Integer.toString(position.x) + " y: " + Integer.toString(position.y) + " z: " + Integer.toString(position.z) +
				" direction: " + direction.getAsString() + " cool down: " + Integer.toString(coolDown);
	}

	// Returns a string suitable for passing to a wrapped plane process
	public String getDataString() {
		if (!alive) {
			return "dead 0 0 0 N 0";
		}
		return 
			"alive " + 
			Integer.toString(position.x) + " " + 
			Integer.toString(position.y) + " " + 
			Integer.toString(position.z) + " " +
			direction.getAsString() + " " + 
			Integer.toString(coolDown);
	}
	
	// Returns true if a plane is on an irreversable colision course with the wall.
	// Use this along with simulateMove() to avoid hitting walls or prune possible emeny moves.
	public boolean isSuicidal() {
		char[] directions = direction.getMainDirections;
		if(directions.length = 3) {
			return getDistanceFromWall(directions[0])<2 && getDistanceFromWall(directions[1])<2 && getDistanceFromWall(directions[2])<2;
		}
		return getDistanceFromWall(directions[0])<1 && (directions.length = 1 || getDistanceFromWall(directions[1])<1);
	}
}
