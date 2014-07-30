package Planes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// This class allows an external script to control a plane
// communication is handled via arguments, stdin, stdout
public class PlaneWrapper extends PlaneControl {

    private ProcessBuilder processbuilder;
    private Process process;
    private InputStream input;
    private BufferedReader reader;
    private OutputStream output;
    private BufferedWriter writer;

     public PlaneWrapper(int arenaSize, int rounds) {
        super(arenaSize, rounds);
     }

     public PlaneWrapper(int arenaSize, int rounds, String binary) {
        super(arenaSize, rounds);
         this.processbuilder = new ProcessBuilder(binary, Integer.toString(arenaSize), Integer.toString(rounds));
        try{this.process = this.processbuilder.start();}catch(IOException e){e.printStackTrace();}
        this.input = this.process.getInputStream();
        this.output = this.process.getOutputStream();
        this.reader = new BufferedReader(new InputStreamReader(this.input));
        this.writer = new BufferedWriter(new OutputStreamWriter(this.output));
        try{
            writer.write(("NEW CONTEST " + 
                Integer.toString(arenaSize) + " " +
                Integer.toString(rounds) + "\n"));
            writer.flush();
        }catch(IOException e){e.printStackTrace();}
    }

    @Override
    public final void setRoundsLeft(int rounds) {
        try{
            writer.write(("ROUNDS LEFT " + Integer.toString(rounds) + "\n"));
            writer.flush();
        }catch(IOException e){e.printStackTrace();}
    }

    // Notifies you that a new fight is starting
    // FightsFought tells you how many fights will be fought.
    // the scores tell you how many fights each player has won.
    @Override
    public void newFight(int fightsFought, int myScore, int enemyScore) {
        try{
            writer.write(("NEW FIGHT " + 
                Integer.toString(fightsFought) + " " + 
                Integer.toString(myScore) + " " + 
                Integer.toString(enemyScore) + "\n")
            );
            writer.flush();
        }catch(IOException e){e.printStackTrace();}    
    }

    // notifies you that you'll be fighting anew opponent.
    // Fights is the amount of fights that will be fought against this opponent
    @Override
    public void newOpponent(int fights) {
        try{
            writer.write(("NEW OPPONENT " + Integer.toString(fights) + "\n"));
            writer.flush();
        }catch(IOException e){e.printStackTrace();}    
    }

    // This will be called once every round, you must return an array of two moves.
    // The move at index 0 will be applied to your plane at index 0,
    // The move at index1 will be applied to your plane at index1.
    // Any further move will be ignored.
    // A missing or invalid move will be treated as flying forward without shooting.
    @Override
    public Move[] act() {
        Move[] moves = new Move[2];
        try{
            writer.write(("NEW TURN\n"));
            writer.write((myPlanes[0].getDataString() + "\n"));
            writer.write((myPlanes[1].getDataString() + "\n"));
            writer.write((enemyPlanes[0].getDataString() + "\n"));
            writer.write((enemyPlanes[1].getDataString() + "\n"));
            writer.flush();
        }catch(IOException e){e.printStackTrace();}    
        for (int i=0; i<2; i++) {
            String moveString = "N 0 0";
            try{
                moveString = reader.readLine();
            } catch(IOException e) {
                e.printStackTrace();
            }
            Pattern p = Pattern.compile("([NSWEDU]+) ([01]) ([01])");
            Matcher m = p.matcher(moveString);
            m.matches();
            try {
                moves[i] = new Move(new Direction(m.group(1)), m.group(2).equals("1"), m.group(3).equals("1"));
            } catch(Exception e) {
                moves[i] = new Move(new Direction("N"),false,false);
            }
        }
        return moves;
    }
}
