package tetris;

/**
 * @author Thanh Le
 */
public class Piece {
    // Pieces constants
    public static final int EMPTY = 0;
    public static final int LSHAPE = 1;
    public static final int LSHAPE_REVERSE = 2;
    public static final int ZICZAC = 3;
    public static final int ZICZAC_REVERSE = 4;
    public static final int STRAIGHT_LINE = 5;
    public static final int SQUARE = 6;
    public static final int TSHAPE = 7;
    
    private int currentX, currentY;
    private final int color;
    private int[][] grid;
    
    
    public Piece(int type) {
        grid = new int[4][4];
        for ( int row = 0; row < 4; row ++ )
            for ( int col = 0; col < 4; col ++ ) 
                grid[ row ][ col ] = PIECE[ type ][ row ][ col ];
        color = type;
        currentX = TetrisBoard.INITIAL_X;
        currentY = TetrisBoard.INITIAL_Y;
    }
    
    public Piece clonePiece() {
        Piece newPiece = new Piece(color);
        newPiece.currentX = currentX;
        newPiece.currentY = currentY;
        for ( int i = 0 ; i < 4; i ++ ) 
            for ( int j = 0 ; j < 4 ; j ++ )
                newPiece.grid[i][j] = grid[i][j];
        return newPiece;
    }
    
    public void moveDown() {
        currentX++;
    }
    
    public void moveLeft() {
        currentY--;
    }
    
    public void moveRight() {
        currentY++;
    }
    
    public void rotate() {
        int[][] tmp = new int[4][4];
        for ( int row = 0; row < 4; row++ )
            for ( int col = 0; col < 4; col++ )
                tmp[row][3-col] = grid[col][row];
        for ( int row = 0; row < 4; row++ )
            for ( int col = 0; col < 4; col ++ )
                grid[row][col] = tmp[row][col];
        
        boolean check = true;
        while ( check ) {
            for ( int col = 0; col < 4; col++ )
                if ( grid[ 0 ][ col ] != 0 ) {
                    check = false;
                    break;
                }
            if ( check ) {
                for ( int row = 0; row < 3; row++ ) {
                    for ( int col = 0; col < 4; col++ )
                        grid[ row ][ col ] = grid[ row + 1][ col ];
                }
                for ( int col = 0; col < 4; col++ )
                    grid[ 3 ][ col ] = 0;
            }
        }
        if (!checkLegalPosition()) {
            moveLeft();
            if (!checkLegalPosition()) {
                moveRight();
                moveRight();
            }
        }
    }
    
    public boolean checkLegalPosition() {
        for ( int i = 0; i < 4; i ++ ) 
            for ( int j = 0; j < 4; j++ ) 
                if ( grid[ i ][ j ] != 0 ) {
                    if ( currentX + i >= TetrisBoard.NUMBER_OF_ROWS ) return false;
                    if ( currentX + i < 0 ) return false;
                    if ( currentY + j >= TetrisBoard.NUMBER_OF_COLUMS ) return false;
                    if ( currentY + j < 0 ) return false;
                }
        for ( int i = 0; i < 4; i ++ )
            for ( int j = 0; j < 4; j++ ) 
                if (grid[ i ][ j ] > 0 )
                    if ( TetrisBoard.Board[ currentX + i ][ currentY + j ] > 0 )
                        return false;
        return true;
    }
    
    public int getGrid(int x, int y) {
        return grid[x][y];
    }
    
    public int getCurrentX() {
        return currentX;
    }
    
    public int getCurrentY() {
        return currentY;
    }
    
    
    // Private variables
    
    private static final int PIECE[][][];
    
    
    static {
        PIECE = new int[ 8 ][ 4 ][ 4 ];
        // L shape -> (0,1), (1,1), (2,1), (2,2)
        PIECE[ LSHAPE ][ 0 ][ 1 ] = LSHAPE;
        PIECE[ LSHAPE ][ 1 ][ 1 ] = LSHAPE;
        PIECE[ LSHAPE ][ 2 ][ 1 ] = LSHAPE;
        PIECE[ LSHAPE ][ 2 ][ 2 ] = LSHAPE;
        
        // reverse L shape -> (0,2), (1,2), (2,2), (2,1)
        PIECE[ LSHAPE_REVERSE ][ 0 ][ 2 ] = LSHAPE_REVERSE;
        PIECE[ LSHAPE_REVERSE ][ 1 ][ 2 ] = LSHAPE_REVERSE;
        PIECE[ LSHAPE_REVERSE ][ 2 ][ 2 ] = LSHAPE_REVERSE;
        PIECE[ LSHAPE_REVERSE ][ 2 ][ 1 ] = LSHAPE_REVERSE;
        
        // ziczac -> (0,1), (1,1), (1,2), (2,2)
        PIECE[ ZICZAC ][ 0 ][ 1 ] = ZICZAC;
        PIECE[ ZICZAC ][ 1 ][ 1 ] = ZICZAC;
        PIECE[ ZICZAC ][ 1 ][ 2 ] = ZICZAC;
        PIECE[ ZICZAC ][ 2 ][ 2 ] = ZICZAC;
        
        // ziczac reverse -> (0,2), (1,2) , (1,1) , (2,1)
        PIECE[ ZICZAC_REVERSE ][ 0 ][ 2 ] = ZICZAC_REVERSE;
        PIECE[ ZICZAC_REVERSE ][ 1 ][ 2 ] = ZICZAC_REVERSE;
        PIECE[ ZICZAC_REVERSE ][ 1 ][ 1 ] = ZICZAC_REVERSE;
        PIECE[ ZICZAC_REVERSE ][ 2 ][ 1 ] = ZICZAC_REVERSE;
        
        // square
        PIECE[ SQUARE ][ 0 ][ 1 ] = SQUARE;
        PIECE[ SQUARE ][ 0 ][ 2 ] = SQUARE;
        PIECE[ SQUARE ][ 1 ][ 1 ] = SQUARE;
        PIECE[ SQUARE ][ 1 ][ 2 ] = SQUARE;
        
        // straight line -> (1,0), (1,1), (1,2), (1,3)
        PIECE[ STRAIGHT_LINE ][ 0 ][ 2 ] = STRAIGHT_LINE;
        PIECE[ STRAIGHT_LINE ][ 1 ][ 2 ] = STRAIGHT_LINE;
        PIECE[ STRAIGHT_LINE ][ 2 ][ 2 ] = STRAIGHT_LINE;
        PIECE[ STRAIGHT_LINE ][ 3 ][ 2 ] = STRAIGHT_LINE;
        
        // T shape -> (0,1), (1,0), (1,1), (1,2)
        PIECE[ TSHAPE ][ 0 ][ 1 ] = TSHAPE;
        PIECE[ TSHAPE ][ 1 ][ 0 ] = TSHAPE;
        PIECE[ TSHAPE ][ 1 ][ 1 ] = TSHAPE;
        PIECE[ TSHAPE ][ 1 ][ 2 ] = TSHAPE;
    }
    
}
