package checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class CheckersApp extends Application
{
    //variables
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private Stage primaryStage;
    private Tile[][] board = new Tile[WIDTH][HEIGHT];
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private PieceType pieceTurn = PieceType.RED;
    private int redCount;
    private int whiteCount;
    //methods
    private Parent createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE); //create screen
        root.getChildren().addAll(tileGroup, pieceGroup); //adding tile and pieces containers

        for (int y = 0; y < HEIGHT; y++)
        {
            for (int x = 0; x < WIDTH; x++)
            {
                Tile tile = new Tile((x + y) % 2 == 0, x, y); //create tile
                board[x][y] = tile; //place tile

                tileGroup.getChildren().add(tile); //add tile to group

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) //populating red checkers pieces
                {
                    piece = makePiece(PieceType.RED, x, y);
                    redCount++;
                }

                if (y >= 5 && (x + y) % 2 != 0) //populating white checkers pieces
                {
                    piece = makePiece(PieceType.WHITE, x, y);
                    whiteCount++;
                }

                if (piece != null)
                {
                    tile.setPiece(piece); //assigning tile a piece
                    pieceGroup.getChildren().add(piece); //add piece to group and board
                }
            }
        }
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        Scene scene = new Scene(createContent());
        this.primaryStage.setTitle("CheckersApp");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void restart()
    {
        for(int y = 0; y < HEIGHT; y++)
        {
            for(int x = 0; x < WIDTH; x++)
            {
                Piece temp = board[x][y].getPiece();
                if( temp != null)
                {
                    board[x][y].setPiece(null);
                    pieceGroup.getChildren().remove(temp);
                }
            }
        }
        this.redCount = 0;
        this.whiteCount = 0;
        Scene scene = new Scene(createContent());
        this.primaryStage.setTitle("CheckersApp");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void updateBoard(int oldX, int oldY, int newX, int newY, Piece killedPiece)
    {
        Piece temp = board[oldX][oldY].getPiece();
        board[oldX][oldY].setPiece(null); //remove piece from tile
        board[newX][newY].setPiece(temp);//place piece on new tile
        this.pieceTurn = (PieceType.RED == this.pieceTurn ? PieceType.WHITE : PieceType.RED);

        if(killedPiece != null)
        {
            board[toBoard(killedPiece.getOldX())][toBoard(killedPiece.getOldY())].setPiece(null);//remove the piece from tile
            pieceGroup.getChildren().remove(killedPiece); //remove piece from group
            if(killedPiece.getType() == PieceType.RED)
                this.redCount--;
            if(killedPiece.getType() == PieceType.WHITE)
                this.whiteCount--;
            System.out.println("white: "+whiteCount+" red: "+redCount);
        }
        checkGameEnd();
    }

    private PieceType checkStuck()
    {
        boolean redStuck = true;
        boolean whiteStuck = true;
        for(int x = 0; x < WIDTH; x++)
        {
            for(int y = 0; y < HEIGHT; y++)
            {
                Piece temp = board[x][y].getPiece(); //checking if piece exists
                if(temp != null)
                {
                    int curX = toBoard(temp.getOldX());
                    int curY = toBoard(temp.getOldY());
                    int movDir = (temp.getType() == PieceType.RED ? 1 : -1);
                    int loop = (temp.getKingStatus() == true ? 2 : 1);

                    for(int i = 0 ; i < loop; i++)
                    {
                        if(temp.tryMove(curX-1, curY+movDir).getType() != MoveType.NONE ||
                                temp.tryMove(curX+1, curY+movDir).getType() != MoveType.NONE ||
                                temp.tryMove(curX-2, curY+(movDir*2)).getType() != MoveType.NONE ||
                                temp.tryMove(curX+2, curY+(movDir*2)).getType() != MoveType.NONE)
                        {
                            if (temp.getType() == PieceType.RED && redStuck == true)
                                redStuck = false;
                            else if (temp.getType() == PieceType.WHITE && whiteStuck == true)
                                whiteStuck = false;
                        }
                        movDir *= -1;
                    }
                }
            }
        }
        if(redStuck)
            return PieceType.RED;
        if(whiteStuck)
            return  PieceType.WHITE;
        return null;
    }

    private void checkGameEnd()
    {
        int result;
        PieceType winner;
        PieceType loser;
        String message = "";
        PieceType stuck = checkStuck();
        boolean gameOver = false;

        if(this.redCount == 0 || this.whiteCount == 0) //win condition by enemy has no more pieces
        {
            winner = (this.redCount == 0 ? PieceType.WHITE : PieceType.RED);
            loser = (this.redCount == 0 ? PieceType.RED : PieceType.WHITE);
            message = "Team "+winner.name()+" Won!\nTeam "+loser.name()+" Lost.\n";
            gameOver = true;
        }
        else if( stuck != null)
        {

            message = "Team "+stuck.name()+" is stuck\n";
            if(this.redCount == this.whiteCount)
                message += "Even number of pieces on both sides\nGame is a draw!";
            else
            {
                winner = (this.redCount > this.whiteCount ? PieceType.RED : PieceType.WHITE);
                loser = (this.redCount > this.whiteCount ? PieceType.WHITE : PieceType.RED);
                message += "Team " + winner + " has more Pieces\n";
                message += "Team "+winner+" Won!\nTeam "+loser+" Lost.\n";
            }
            gameOver = true;
        }
        if(gameOver)
        {
            message += "\nWould you like to play again?";
            result = JOptionPane.showConfirmDialog(null, message);
            switch (result)
            {
                case JOptionPane.YES_OPTION:
                    this.restart();
                    break;
                case JOptionPane.NO_OPTION:
                case JOptionPane.CANCEL_OPTION:
                case JOptionPane.CLOSED_OPTION:
                    this.primaryStage.close();
                    break;
            }
        }
    }

    private int toBoard(double pixel)
    {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    } //convert pixel coordinate to board coordinate

    private Piece makePiece(PieceType type, int x, int y)
    {
        Piece piece = new Piece(type, x, y, this); //placing new piece
        return piece;
    }

    //getters
    public Tile[][] getBoard(){return this.board;}
    public PieceType getTurn(){return this.pieceTurn;}
}