package tetris;
import java.util.Random;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Thanh Le
 */

public class Tetris extends JFrame implements KeyListener {
    
    public static final int SCREEN_WIDTH = Interface.SQUARE_SIZE * (TetrisBoard.NUMBER_OF_COLUMS + 6);
    public static final int SCREEN_LENGTH = Interface.SQUARE_SIZE * (TetrisBoard.NUMBER_OF_ROWS + 1);
        
    public Tetris() {
        super("Nanotech's Tetris!");
        newBoard = new TetrisBoard(0);
        setSize( SCREEN_WIDTH , SCREEN_LENGTH );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newInterface = new Interface();
        add(newInterface);
        setVisible(true);
        addKeyListener(this);
        
        Random GenRandom = new Random();
        int type = GenRandom.nextInt() % 7;
        if ( type < 0 ) type = -type;
        type++;
        nextPiece = new Piece(type);
        newBoard.setNextPiece(nextPiece);
    }
            
    public static void main(String[] args) throws InterruptedException {
        
        
        Tetris newTetris = new Tetris();      
        Random GenRandom = new Random();
        
        // time counter variables
        long tStart = System.currentTimeMillis();
        long tStartLR = System.currentTimeMillis();
        
        // get the next piece randomly
        int type = GenRandom.nextInt() % 7;
        if ( type < 0 ) type = -type;
        type++;
        
        // initialize the piece
        currentPiece = new Piece( type );
        newBoard.setPosition(currentPiece);
        
        Thread.sleep(1000L);
        
        // game loop
        while ( !gameOver ) {
            // Delay a few millisecond in order to run the game smoothly
            try {
                Thread.sleep(60L);
            } catch (InterruptedException ex) {}
            
            // Second time counter
            long tEnd = System.currentTimeMillis();
            long tEndLR = System.currentTimeMillis();
            
            // Moving left or right according to time
            if ( speedLeftRight != 0 ) {
                // Temporarily remove the currentPiece from the board
                newBoard.removePosition(currentPiece);
                if ( tEndLR - tStartLR >= speedLeftRight ) {
                    tStartLR = System.currentTimeMillis();
                    Piece newPiece = currentPiece.clonePiece();
                    if ( speedLeftRight > 0 ) 
                        newPiece.moveRight();
                    if ( speedLeftRight < 0 )
                        newPiece.moveLeft();
                    if ( newPiece.checkLegalPosition() ) {
                        currentPiece = newPiece;
                    }
                }
                newBoard.setPosition(currentPiece);
                newInterface.repaint();
            }
            
            
            // Moving down according to time
            // Temporarily remove the currentPiece from the board
            newBoard.removePosition(currentPiece);
            if ( tEnd - tStart >= newBoard.getMode() ) {
                //System.out.println("Satisfied!");
                tStart = System.currentTimeMillis();
                Piece newPiece = currentPiece.clonePiece();
                newPiece.moveDown();
                if ( newPiece.checkLegalPosition() ) { 
                    currentPiece = newPiece;
                } else {
                    //Thread.sleep(50);
                    if ( !newPiece.checkLegalPosition() ) {
                        System.out.println("New Piece Generated!");
                        newBoard.setPosition(currentPiece);
                        newBoard.update( currentPiece.getCurrentX() );
                        type = GenRandom.nextInt() % 7;
                        if ( type < 0 ) type = -type;
                        type++;
                        currentPiece = nextPiece.clonePiece();
                        nextPiece = new Piece( type );
                        newBoard.setNextPiece(nextPiece);
                        if ( !currentPiece.checkLegalPosition() ) {
                            gameOver = true;
                            break;
                        }
                    } else {
                        currentPiece = newPiece;
                    }
                }
            }
            
            // set the piece on the board
            if ( currentPiece.checkLegalPosition())
                newBoard.setPosition(currentPiece);
            newInterface.repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        //System.out.println("You pressed " + event.getKeyChar());
        if ( event.getKeyChar() == 'a') {
            System.out.println("Move left!");
            speedLeftRight = - TetrisBoard.SUPER_SPEED;
        }
        if ( event.getKeyChar() == 'd') {
            System.out.println("Move right!");
            speedLeftRight = TetrisBoard.SUPER_SPEED;
        }
        
        if ( event.getKeyChar() == 'w' || event.getKeyChar() == ' ') {
            newBoard.removePosition(currentPiece);
            Piece newPiece = currentPiece.clonePiece();
            newPiece.rotate();
            if ( newPiece.checkLegalPosition() ) {
                currentPiece = newPiece;
            } else {
                newPiece.rotate();
                if ( newPiece.checkLegalPosition() ) 
                    currentPiece = newPiece;
                else {
                    newPiece.rotate();
                    if ( newPiece.checkLegalPosition() )
                        currentPiece = newPiece;
                }
            }
            if ( currentPiece.checkLegalPosition())
                newBoard.setPosition(currentPiece);
            newInterface.repaint();
        }
        if ( event.getKeyChar() == 's' ) {
            newBoard.changeMode(TetrisBoard.SUPER_SPEED);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent event) {}

    @Override
    public void keyReleased(KeyEvent event) {
        if ( event.getKeyChar() == 's' ) {
            newBoard.previousSpeed();
        }
        if ( event.getKeyChar() == 'a' ) {
            speedLeftRight = 0;
        }
        if ( event.getKeyChar() == 'd' ) {
            speedLeftRight = 0;
        }
    }
    
    /**************************
     * 
     * PRIVATE VARIABLES
     * 
     **************************/
    
    static private Piece currentPiece;
    static private Piece nextPiece;
    static private TetrisBoard newBoard;
    static private Interface newInterface;
    static private int speedLeftRight = 0;
    static private boolean gameOver = false;    
}
