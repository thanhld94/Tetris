package tetris;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Ryo
 */
public class Interface extends JPanel {
    
    static public final int SQUARE_SIZE = 30;
    static public final int FRAME_SIZE = 1;
    static public final int NEXT_PIECE_X = TetrisBoard.NUMBER_OF_COLUMS + 1;
    static public final int NEXT_PIECE_Y = 2;
    static public final int BOARD_MODE = 0;
    static public final int NEXT_PIECE_MODE = 1;
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.DARK_GRAY);
        
        // paint the tetris board
        for ( int i = 0; i < TetrisBoard.NUMBER_OF_ROWS; i++ ) 
            for ( int j = 0; j < TetrisBoard.NUMBER_OF_COLUMS; j++ ) {
                if ( TetrisBoard.Board[ i ][ j ] == Piece.EMPTY ) g.setColor(Color.black);
                if ( TetrisBoard.Board[ i ][ j ] == Piece.LSHAPE ) g.setColor(Color.pink);
                if ( TetrisBoard.Board[ i ][ j ] == Piece.LSHAPE_REVERSE ) g.setColor(Color.orange);
                if ( TetrisBoard.Board[ i ][ j ] == Piece.ZICZAC ) g.setColor(Color.red);
                if ( TetrisBoard.Board[ i ][ j ] == Piece.ZICZAC_REVERSE ) g.setColor(Color.green);
                if ( TetrisBoard.Board[ i ][ j ] == Piece.STRAIGHT_LINE ) g.setColor(Color.blue);
                if ( TetrisBoard.Board[ i ][ j ] == Piece.SQUARE ) g.setColor(Color.yellow);
                if ( TetrisBoard.Board[ i ][ j ] == Piece.TSHAPE ) g.setColor(Color.white);
                g.fillRect( j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE );
                drawFrame(g,i,j,BOARD_MODE);
            }
        
        // paint the next piece board
        for ( int i = 0; i < 4; i++ ) 
            for ( int j = 0; j < 4; j++ ) {
                if ( TetrisBoard.NextPieceBoard[ i ][ j ] == Piece.EMPTY ) g.setColor(Color.DARK_GRAY);
                if ( TetrisBoard.NextPieceBoard[ i ][ j ] == Piece.LSHAPE ) g.setColor(Color.pink);
                if ( TetrisBoard.NextPieceBoard[ i ][ j ] == Piece.LSHAPE_REVERSE ) g.setColor(Color.orange);
                if ( TetrisBoard.NextPieceBoard[ i ][ j ] == Piece.ZICZAC ) g.setColor(Color.red);
                if ( TetrisBoard.NextPieceBoard[ i ][ j ] == Piece.ZICZAC_REVERSE ) g.setColor(Color.green);
                if ( TetrisBoard.NextPieceBoard[ i ][ j ] == Piece.STRAIGHT_LINE ) g.setColor(Color.blue);
                if ( TetrisBoard.NextPieceBoard[ i ][ j ] == Piece.SQUARE ) g.setColor(Color.yellow);
                if ( TetrisBoard.NextPieceBoard[ i ][ j ] == Piece.TSHAPE ) g.setColor(Color.white);
                g.fillRect( (NEXT_PIECE_X + j) * SQUARE_SIZE, (NEXT_PIECE_Y + i) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE );
                drawFrame(g,(NEXT_PIECE_Y + i),(NEXT_PIECE_X + j),NEXT_PIECE_MODE);
            }
        
        // print score
        g.setColor(Color.yellow);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.drawString("Next Piece", (TetrisBoard.NUMBER_OF_COLUMS + 1) * SQUARE_SIZE, (1) * SQUARE_SIZE );
        g.drawString("Score: " + TetrisBoard.getScore(), (TetrisBoard.NUMBER_OF_COLUMS + 1) * SQUARE_SIZE, (8) * SQUARE_SIZE );
        g.drawString("Level " + (TetrisBoard.getLevel() + 1), (TetrisBoard.NUMBER_OF_COLUMS + 1) * SQUARE_SIZE, (9) * SQUARE_SIZE );
    }
    
    private void drawFrame(Graphics g, int X, int Y, int Mode) {
        g.setColor(Color.BLACK);
        
        if ( Mode == BOARD_MODE ) {
            if ( TetrisBoard.Board[ X ][ Y ] != 0 ) g.setColor(Color.GRAY);
        } else {
            if ( TetrisBoard.NextPieceBoard[ X - NEXT_PIECE_Y ][ Y - NEXT_PIECE_X ] != 0 ) 
                g.setColor(Color.LIGHT_GRAY);
            else 
                g.setColor(Color.DARK_GRAY);
        }
        
        g.fillRect( Y * SQUARE_SIZE, X * SQUARE_SIZE, SQUARE_SIZE, FRAME_SIZE );
        g.fillRect( Y * SQUARE_SIZE, X * SQUARE_SIZE, FRAME_SIZE, SQUARE_SIZE);
        g.fillRect( (Y+1) * SQUARE_SIZE - FRAME_SIZE, X * SQUARE_SIZE, FRAME_SIZE, SQUARE_SIZE );
        g.fillRect( Y * SQUARE_SIZE, (X+1) * SQUARE_SIZE - FRAME_SIZE, SQUARE_SIZE, FRAME_SIZE );
    }
}
