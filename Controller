package Planes;

public class Controller {

	private static final int ARENA_SIZE = 20;
	private static final int ROUNDS = 100;
	private static final int FIGHTS = 10;
	
	public static void main(String []args){
		// Controller code goes here;
    }
	
	private static int fight(PlaneControl player1, PlaneControl player2) {

		Plane[] player1Planes = {new Plane(ARENA_SIZE, new Direction("S"), 0, 8, 0),
								new Plane(ARENA_SIZE, new Direction("S"), 0, 11, 0)};
		Plane[] player2Planes = {new Plane(ARENA_SIZE, new Direction("N"), 19, 11, 19),
				new Plane(ARENA_SIZE, new Direction("N"), 19, 8, 19)};
		
		for (int i=ROUNDS - 1; i>=0; i--) {
			// Update the plane objects of the players.
			player1.setPlane1(player1Planes[0].copy());
			player1.setPlane2(player1Planes[1].copy());
			player1.setEnemyPlane1(player2Planes[0].copy());
			player1.setEnemyPlane2(player2Planes[1].copy());
			
			player2.setPlane1(player2Planes[0].copy());
			player2.setPlane2(player2Planes[1].copy());
			player2.setEnemyPlane1(player1Planes[0].copy());
			player2.setEnemyPlane2(player1Planes[1].copy());
			
			// Let the player do their moves.
			Move[] player1Moves = player1.act();
			Move[] player2Moves = player2.act();
			
			// Calculate the new positions.
			Move[] moves = new Move[4];
			
			if (player1Moves.length <= 0) {
				moves[0] = new Move(player1Planes[0].getDirection(), false, false);
			} else {
				moves[0] = player1Moves[0];
			}

			if (player1Moves.length <= 1) {
				moves[1] = new Move(player1Planes[1].getDirection(), false, false);
			} else {
				moves[1] = player1Moves[1];
			}

			if (player2Moves.length <= 0) {
				moves[2] = new Move(player2Planes[0].getDirection(), false, false);
			} else {
				moves[2] = player2Moves[0];
			}

			if (player2Moves.length <= 1) {
				moves[3] = new Move(player2Planes[1].getDirection(), false, false);
			} else {
				moves[3] = player2Moves[1];
			}

			player1Planes[0] = player1Planes[0].simulateMove(moves[0]);
			player1Planes[1] = player1Planes[1].simulateMove(moves[1]);
			player2Planes[0] = player2Planes[0].simulateMove(moves[2]);
			player2Planes[1] = player2Planes[1].simulateMove(moves[3]);
			
			// Check if any collisions happened
			Plane[] tempPlanes = {player1Planes[0], player1Planes[1], player2Planes[0], player2Planes[1]};
			handleCollisions(tempPlanes);
			
			// Check if anyone got shot
			handleShooting(tempPlanes, moves);

			if (!player1Planes[0].isAlive() && !player1Planes[1].isAlive() && !player2Planes[0].isAlive() && !player2Planes[1].isAlive()) {
				return 0; // Both players are out of planes.
			}
			if (!player1Planes[0].isAlive() && !player1Planes[1].isAlive()) {
				return -1; // Player1 is out of planes.
			}
			if (!player2Planes[0].isAlive() && !player2Planes[1].isAlive()) {
				return 1; // Player2 is out of planes.
			}			
		}
		
		return 0;
	}
	
	// Check if collisions happened and react appropriately
	private static void handleCollisions(Plane[] planes) {
		boolean[] collided = new boolean[planes.length];
		for (int i=0; i<collided.length; i++) {
			collided[i] = false;
		}
		
		for (int i=0; i<planes.length - 1; i++) {
			for (int j=i+1; j<planes.length; j++) {
				if (planes[i].getPosition().equals(planes[j].getPosition())) {
					collided[i] = true;
					collided[j] = true;
				}
			}
		}
		
		for (int i=0; i<collided.length; i++) {
			if (collided[i]) {
			planes[i].setAlive(false);
			}
		}
	}
	
	// Check if anyone got shot and react appropriately.
	private static void handleShooting(Plane[] planes, Move[] moves) {
		boolean[] hit = new boolean[planes.length];
		for (int i=0; i<hit.length; i++) {
			hit[i] = false;
		}
		
		for (int i=0; i<planes.length; i++) {
			if (moves[i].shoot && planes[i].canShoot()) {
				planes[i].setCoolDown(2);
				
				Point3D[] range = planes[i].getShootRange();
				for (int j=0; j<range.length; j++) {
					for (int k=0; k<planes.length; k++) {
						if (range[j].equals(planes[k].getPosition())) {
							hit[k] = true;
							break;
						}
					}
				}
			} else {
				planes[i].setCoolDown(Math.max(0, planes[i].getCoolDown() - 1));
			}
		}
		
		for (int i=0; i<hit.length; i++) {
			if (hit[i]) {
				planes[i].setAlive(false);
			}
		}
	}
}
