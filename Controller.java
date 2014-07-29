package Planes;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Controller {

	private static final int ARENA_SIZE = 20;
	private static final int ROUNDS = 100;
	private static final int FIGHTS = 10;
	private static final int COOLDOWN = 1;

	public static void main(String []args){
		try {
			PrintWriter out = new PrintWriter("test.txt");
		
		fight(new DumbPlanes(ARENA_SIZE, ROUNDS), new DumbPlanes(ARENA_SIZE, ROUNDS), out);
		out.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

	private static int fight(PlaneControl player1, PlaneControl player2, PrintWriter out) {

		Plane[] player1Planes = {new Plane(ARENA_SIZE, 0, new Direction("S"), 0, ARENA_SIZE/2 - 2, 0),
								new Plane(ARENA_SIZE, 0, new Direction("S"), 0, ARENA_SIZE/2 + 1, 0)};
		Plane[] player2Planes = {new Plane(ARENA_SIZE, 0, new Direction("N"), ARENA_SIZE - 1, ARENA_SIZE/2 + 1, ARENA_SIZE - 1),
								new Plane(ARENA_SIZE, 0, new Direction("N"), ARENA_SIZE - 1, ARENA_SIZE/2 - 2, ARENA_SIZE - 1)};

		String player1String = "DumbPlanes1";
		String player2String = "DumbPlanes2";
		
		String eol = System.getProperty("line.separator");
		out.println("The match between " + player1String + " and " + player2String + " begins and will go on for " + ROUNDS + " rounds." + eol);
		
		for (int i=ROUNDS - 1; i>=0; i--) {
			// Announce the round.
			out.println(eol + "ROUND " + Integer.toString(ROUNDS - i) + eol);
			
			// Update the plane objects of the players.
			player1.setPlane1(player1Planes[0].copy());
			player1.setPlane2(player1Planes[1].copy());
			player1.setEnemyPlane1(player2Planes[0].copy());
			player1.setEnemyPlane2(player2Planes[1].copy());

			player2.setPlane1(player2Planes[0].copy());
			player2.setPlane2(player2Planes[1].copy());
			player2.setEnemyPlane1(player1Planes[0].copy());
			player2.setEnemyPlane2(player1Planes[1].copy());
			
			// Report the planes' status.
			out.println(player1String + "'s first plane: " + player1Planes[0].getAsString() + eol);
			out.println(player1String + "'s second plane: " + player1Planes[1].getAsString() + eol);
			out.println(player2String + "'s first plane: " + player2Planes[0].getAsString() + eol);
			out.println(player2String + "'s second plane: " + player2Planes[1].getAsString() + eol);
			
			// Tell the players how many rounds are left.
			player1.setRoundsLeft(i);
			player2.setRoundsLeft(i);

			// Let the player do their moves.
			Move[] player1Moves = player1.act();
			Move[] player2Moves = player2.act();

			
			
			// Check if valid moves were submitted and report them.
			Move[] moves = new Move[4];

			if (player1Moves.length <= 0) {
				moves[0] = new Move(player1Planes[0].getDirection(), false, false);
				out.println(eol + player1String + "'s first plane failed to decide what to do and will fly straight ahead");
			} else {
				moves[0] = player1Moves[0];
				out.println(eol + player1String + "'s first plane move: " + player1Moves[0].getAsString() + eol);
			}

			if (player1Moves.length <= 1) {
				moves[1] = new Move(player1Planes[1].getDirection(), false, false);
				out.println(player1String + "'s second plane failed to decide what to do and will fly straight ahead");
			} else {
				moves[1] = player1Moves[1];
				out.println(player1String + "'s second plane move: " + player1Moves[1].getAsString() + eol);
			}

			if (player2Moves.length <= 0) {
				moves[2] = new Move(player2Planes[0].getDirection(), false, false);
				out.println(player2String + "'s first plane failed to decide what to do and will fly straight ahead");
			} else {
				moves[2] = player2Moves[0];
				out.println(player2String + "'s first plane move: " + player2Moves[0].getAsString() + eol);
			}

			if (player2Moves.length <= 1) {
				moves[3] = new Move(player2Planes[1].getDirection(), false, false);
				out.println(player2String + "'s first plane failed to decide what to do and will fly straight ahead");
			} else {
				moves[3] = player2Moves[1];
				out.println(player2String + "'s second plane move: " + player2Moves[1].getAsString() + eol);
			}
			
			// Calculate the new positions.
			player1Planes[0] = player1Planes[0].simulateMove(moves[0]);
			player1Planes[1] = player1Planes[1].simulateMove(moves[1]);
			player2Planes[0] = player2Planes[0].simulateMove(moves[2]);
			player2Planes[1] = player2Planes[1].simulateMove(moves[3]);

			// Check if any collisions happened
			Plane[] tempPlanes = {player1Planes[0], player1Planes[1], player2Planes[0], player2Planes[1]};
			handleCollisions(tempPlanes, out);

			// Check if anyone got shot
			handleShooting(tempPlanes, moves, out);

			if (!player1Planes[0].isAlive() && !player1Planes[1].isAlive() && !player2Planes[0].isAlive() && !player2Planes[1].isAlive()) {
				// Report the planes' status.
				out.println(eol + player1String + "'s first plane: " + player1Planes[0].getAsString() + eol);
				out.println(player1String + "'s second plane: " + player1Planes[1].getAsString() + eol);
				out.println(player2String + "'s first plane: " + player2Planes[0].getAsString() + eol);
				out.println(player2String + "'s second plane: " + player2Planes[1].getAsString() + eol);
				
				out.println(eol + "And that's it, both players are out of planes, it's a draw!" + eol);
				return 0; // Both players are out of planes.
			}
			if (!player1Planes[0].isAlive() && !player1Planes[1].isAlive()) {
				// Report the planes' status.
				out.println(eol + player1String + "'s first plane: " + player1Planes[0].getAsString() + eol);
				out.println(player1String + "'s second plane: " + player1Planes[1].getAsString() + eol);
				out.println(player2String + "'s first plane: " + player2Planes[0].getAsString() + eol);
				out.println(player2String + "'s second plane: " + player2Planes[1].getAsString() + eol);
				
				out.println(eol + "And that's it, " + player1String + " is out of planes, " + player2String + " Wins" + eol);
				return -1; // Player1 is out of planes.
			}
			if (!player2Planes[0].isAlive() && !player2Planes[1].isAlive()) {
				// Report the planes' status.
				out.println(eol + player1String + "'s first plane: " + player1Planes[0].getAsString() + eol);
				out.println(player1String + "'s second plane: " + player1Planes[1].getAsString() + eol);
				out.println(player2String + "'s first plane: " + player2Planes[0].getAsString() + eol);
				out.println(player2String + "'s second plane: " + player2Planes[1].getAsString() + eol);
				
				out.println(eol + "And that's it, " + player2String + " is out of planes, " + player1String + " Wins" + eol);
				return 1; // Player2 is out of planes.
			}			
		}
		
		out.println("And that's it, we're out of time" + eol);

		// Report the planes' status.
		out.println(eol + player1String + "'s first plane: " + player1Planes[0].getAsString() + eol);
		out.println(player1String + "'s second plane: " + player1Planes[1].getAsString() + eol);
		out.println(player2String + "'s first plane: " + player2Planes[0].getAsString() + eol);
		out.println(player2String + "'s second plane: " + player2Planes[1].getAsString() + eol);
		
		int winner = ((player1Planes[0].isAlive())?1:0) + ((player1Planes[1].isAlive())?1:0) + 
						((player2Planes[0].isAlive())?-1:0) + ((player2Planes[1].isAlive())?-1:0);
		
		if (winner > 0) {
			out.println(eol + player1String + " wins!" + eol);
		}
		if (winner < 0) {
			out.println(eol + player2String + " wins!" + eol);
		}
		if (winner == 0) {
			out.println(eol + "It's a draw!" + eol);
		}
		
		return winner;
	}

	// Check if collisions happened and react appropriately
	private static void handleCollisions(Plane[] planes, PrintWriter out) {
		String eol = System.getProperty("line.separator");
		
		boolean[] collided = new boolean[planes.length];
		for (int i=0; i<collided.length; i++) {
			collided[i] = false;
		}

		for (int i=0; i<planes.length - 1; i++) {
			for (int j=i+1; j<planes.length; j++) {
				if (planes[i].isAlive() && planes[j].isAlive() && planes[i].getPosition().equals(planes[j].getPosition())) {
					collided[i] = collided[j] = true;
					out.println("A mid-air collision!!!" + eol);
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
	private static void handleShooting(Plane[] planes, Move[] moves, PrintWriter out) {
		String eol = System.getProperty("line.separator");
		
		boolean[] hit = new boolean[planes.length];
		for (int i=0; i<hit.length; i++) {
			hit[i] = false;
		}

		for (int i=0; i<planes.length; i++) {
			if (moves[i].shoot && planes[i].canShoot()) {
				planes[i].setCoolDown(COOLDOWN);

				Point3D[] range = planes[i].getShootRange();
				for (int j=0; j<range.length; j++) {
					for (int k=0; k<planes.length; k++) {
						if (range[j].equals(planes[k].getPosition())) {
							hit[k] = true;
							out.println("Someone got shot!!!!" + eol);
							break;
						}
					}
				}
			}
		}

		for (int i=0; i<hit.length; i++) {
			if (hit[i]) {
				planes[i].setAlive(false);
			}
		}
	}
}
