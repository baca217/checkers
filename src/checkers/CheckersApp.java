package checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckersApp extends Application
{
    //variables
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private Tile[][] board = new Tile[WIDTH][HEIGHT];
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private PieceType pieceTurn = PieceType.RED;
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
                }

                if (y >= 5 && (x + y) % 2 != 0) //populating white checkers pieces
                {
                    piece = makePiece(PieceType.WHITE, x, y);
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
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("CheckersApp");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    public static void main(String[] args)
    {
        launch(args);
    }
}