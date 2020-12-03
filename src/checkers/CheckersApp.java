package checkers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.JOptionPane;
import java.io.IOException;

public class CheckersApp extends Application
{
    //variables
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private String redPlayer1;
    private String whitePlayer2;
    private Stage primaryStage;
    private Tile[][] board = new Tile[WIDTH][HEIGHT];
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private PieceType pieceTurn = PieceType.RED;
    private int redCount;
    private int whiteCount;

    //methods

    public CheckersApp(String redPlayer1, String whitePlayer2) {
        this.redPlayer1 = redPlayer1;
        this.whitePlayer2 = whitePlayer2;
    }

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
        System.out.println(newX+","+newY);
        this.pieceTurn = (PieceType.RED == this.pieceTurn ? PieceType.WHITE : PieceType.RED);

        if(killedPiece != null)
        {
            System.out.println("white: "+whiteCount+" red: "+redCount);
            board[toBoard(killedPiece.getOldX())][toBoard(killedPiece.getOldY())].setPiece(null);//remove the piece from tile
            pieceGroup.getChildren().remove(killedPiece); //remove piece from group
            checkGameEnd(killedPiece.getType());
        }
    }

    private void checkStuck(PieceType color)
    {
        for(int x = 0; x < WIDTH; x++)
        {
            for(int y = 0; y < HEIGHT; y++)
            {
                Piece temp = board[x][y].getPiece();
                if(temp != null && temp.getType() == color)
                {

                }
            }
        }
    }

    private void checkGameEnd(PieceType killed) {
        int result;
        if(killed == PieceType.RED)
            this.redCount--;
        if(killed == PieceType.WHITE)
            this.whiteCount--;
        if(this.redCount >= 0 || this.whiteCount == 0)
        {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../endGameScreen/end-game-ui.fxml"));
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
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