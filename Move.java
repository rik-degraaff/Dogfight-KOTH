package Planes;

public class Move {
	public boolean changeDirection;
	public Direction direction;
	
	public Move(Direction direction, boolean changeDirection) {
		this.direction = direction;
		this.changeDirection = changeDirection;
	}
}
