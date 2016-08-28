package tetris;

/**
 * @author Thanh Le
 */
public class TetrisBoard {
    static public final int NUMBER_OF_ROWS = 20;
    static public final int NUMBER_OF_COLUMS = 12;
    static public final int INITIAL_X = 0;
    static public final int INITIAL_Y = NUMBER_OF_COLUMS/2-3;
    
    static public final int LEVEL_DIFFERENCE = 25;
    static public final int STARTING_SPEED = 550;
    
    // Difficulties constant
    
    static public final int[] LEVEL;
    static public final int SUPER_SPEED = 10;
    static public int[][] Board;
    static public int[][] NextPieceBoard;
    
    static {
        LEVEL = new int[50];
        LEVEL[0] = STARTING_SPEED;
        for ( int i = 1; i < LEVEL.length; i++ )
            LEVEL[i] = LEVEL[i-1] - LEVEL_DIFFERENCE;
    }
            
    public TetrisBoard(int level) {
        Mode = LEVEL[level];
        this.level = level;
        initVariables();
        initStartBoard(Mode);
        initNextPieceBoard();
        score = 0;
    }
    
    public void changeMode(int Mode) {
        previousMode = this.Mode;
        this.Mode = Mode;
    }
    
    public void previousSpeed() {
        Mode = previousMode;
    }
    
    public int getMode() {
        return Mode;
    }
    
    static public int getLevel() {
        return level;
    }
    
    public void changeLevel(int newLevel) {
        level = newLevel;
    }
    
    static public int getScore() {
        return score;
    }
    
    // checking for score and update board
    public void update( int position ) {
        for ( int i = position; i < position + 4; i++ ) 
            if ( i < NUMBER_OF_ROWS ) {
                //System.out.println("Checking row " + i );
                boolean filled = true;
                for ( int j = 0; j < NUMBER_OF_COLUMS; j++ ) 
                    if ( Board[ i ][ j ] <= 0 ) {
                        filled = false;
                        break;
                    }
                if ( filled ) {
                    System.out.println("Scored!");
                    score += ( 600 - LEVEL[level] );
                    for ( int row = i; row > 0; row-- )
                        for ( int col = 0; col < NUMBER_OF_COLUMS; col++ )
                            Board[ row ][ col ] = Board[ row - 1][ col ];
                    for ( int col = 0; col < NUMBER_OF_COLUMS; col++ )
                        Board[ 0 ][ col ] = 0;
                }
            }
        if ( LEVEL[level] > 25 ) 
            if ( score >= ((level+1)*LEVEL_DIFFERENCE*20) ) {
                Mode = LEVEL[ level + 1];
                level++;
                System.out.println("Increased Level");
                System.out.println(score);
            };
    }
    
    public void setNextPiece(Piece aPiece) {
        for ( int i = 0; i < 4; i++ ) 
            for ( int j = 0; j < 4; j++ )
                NextPieceBoard[ i ][ j ] = aPiece.getGrid(i, j);
    }
    
    public void setPosition(Piece aPiece) {
        for ( int i = 0; i < 4; i ++ )
            for ( int j = 0; j < 4; j++ ) 
                if ( aPiece.getGrid(i, j) != 0 ) 
                    Board[ aPiece.getCurrentX() + i ][ aPiece.getCurrentY() + j ] += aPiece.getGrid(i,j);
    }
    
    public void removePosition(Piece aPiece) {
        for ( int i = 0; i < 4; i ++ ) 
            for ( int j = 0 ; j < 4; j ++ ) 
                if ( aPiece.getGrid(i, j) != 0 ) {
                    Board[ aPiece.getCurrentX() + i ][ aPiece.getCurrentY() + j ] -= aPiece.getGrid(i, j);
                };
    }
    
     /*******************************
     * 
     *      PRIVATE METHODS
     *  
     *******************************/
    
    
    private void initVariables() {
        Board = new int[ NUMBER_OF_ROWS ][ NUMBER_OF_COLUMS ];
    }
    
    private void initStartBoard(int Mode) {
        for ( int i = 0; i < NUMBER_OF_ROWS; i++ )
            for ( int j = 0; j < NUMBER_OF_COLUMS; j++ )
                Board[ i ][ j ] = 0;
        this.Mode = Mode;
    }
    
    private void initNextPieceBoard() {
        NextPieceBoard = new int[4][4];
        for ( int i = 0; i < 4; i++ )
            for ( int j = 0; j < 4; j++ )
                NextPieceBoard[ i ][ j ] = 0;
    }
    
    static private int Mode;
    static private int level;
    static private int score = 0;
    private int previousMode;
}
