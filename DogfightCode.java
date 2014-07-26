public class Controller {

     public static void main(String []args){
        // Controller code goes here;
     }
}

// This is the base class players extends.
// It contains the arena size and 4 plane objects representing the planes in the arena.
public class PlaneControl {
    
    protected Plane[] Planes = Plane[4];
    protected int arenaSize;
    
    public PlaneControl(arenaSize) {
        this.arenaSize = arenaSize;
    }
}

// Objects of this class contain all relevant information about a plane
// as well as some helper functions.
public class Plane {
    private int x, y, z;
    private String direction;
    private int arenaSize;
    private boolean alive = true;
    private int cooldown = 0;
    
    public Plane(arenaSize) {
        this.arenaSize = arenaSize;
    }    
    
    // Returns the x coordinate of the plane
    public int getX() {
        if (alive) {
            return x;
        } else {
            return -1;
        }
    }
    
    // Returns the y coordinate of the plane
    public int getY() {
        if (alive) {
            return y;
        } else {
            return -1;
        }
    }
    
    // Returns the z coordinate of the plane
    public int getZ() {
        if (alive) {
            return z;
        } else {
            return -1;
        }
    }
    
    // Returns the distance between the plane and the specified wall,
    // 0 means right next to it, 19 means at the opposite side.
    // Returns -1 for invalid input.
    public char getDistanceFromWall(char wall) {
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
    public String getDirection() {
        if (alive) {
            return direction;
        } else {
            return null;
        }
    }
    
    // Returns all possible turning directions for the plane.
    public String[] getPossibleDirections() {
        if (alive) {
            return Direction.getPossibleDirectionsFromDirection(direction);
        } else {
            return null;
        }        
    }
    
    // Returns the cooldown befor ethe plane will be able to shoot, 
    // 0 means it is ready to shoot this turn.
    public int getCooldown() {
        return cooldown;
    }
    
    // Returns true if the plane is ready to shoot
    public boolean canShoot() {
        return cooldown = 0;
    }
    
    // Returns all positions this plane can shoot at (without first making a move).
    // Not done yet.
    public Point3D[] getShootRange() {
        
    }
    
    // Returns all positions this plane can move to within one turn.
    // Not done yet.
    public Point3D[] getRange() {
        
    }
    
    // Returns a plane that represents this plane after making a certain move,
    // not taking into account other planes.
    // Not done yet.
    public Plane simulateMove() {
        
    } 
}
    
// A helper class for working with directions. 
public class Direction {
    // Converts a direction into an array of chars representing the primary directions it consists of.
    // Not done yet.
    public static char[] getPrimaryDirections(String direction) {
        
    }
    
    // Converts an array of up to 3 chars representing a  direction into a valid direction.
    // Not done yet.
    public static String getDirection(char[] primaryDirections) {
        
    }
    
    // Returns all possible turning directions given an initial direction.
    // Not done Yet.
    public String[] getPossibleDirectionsFromDirection(String direction) {
        if (direction.length() = 3) {
            Return {direction,  // the direction itself
                                // diagonally connected directions
                    Character.toString(direction.getCharAt(0)), 
                    Character.toString(direction.getCharAt(1)), 
                    Character.toString(direction.getCharAt(2)),
                                // directly conected directions
                    Character.toString(direction.getCharAt(0)) + Character.toString(direction.getCharAt(1)),
                    Character.toString(direction.getCharAt(0)) + Character.toString(direction.getCharAt(2)),
                    Character.toString(direction.getCharAt(1)) + Character.toString(direction.getCharAt(2))}
                    
        } else if (direction.length() = 2 {
            
        } else if ( direction. length () = 1 {
            
        } else {
            return null;
        }       
    }  
}
