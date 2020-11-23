package checkers;
import checkers.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

    private MoveResult tryMove(Piece piece, int newX, int newY)
    {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) //no move on odd tiles
        {
            return new MoveResult(MoveType.NONE);
        }

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        if(piece.getKingStatus())
        {
            if (Math.abs(newX - x0) == 1 && Math.abs(newY - y0) == 1)
            { //check if move is valid
                return new MoveResult(MoveType.NORMAL);
            } else if (Math.abs(newX - x0) == 2 && Math.abs(newY - y0) == 2)
            {//check if kill move is valid
                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;
                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType())
                {//check if piece exists and is an enemy
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());//remove enemy
                }
            }
        }
        else
        {
            if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir)
            { //check if move is valid
                return new MoveResult(MoveType.NORMAL);
            } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2)
            {//check if kill move is valid
                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;
                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType())
                {//check if piece exists and is an enemy
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());//remove enemy
                }
            }
        }

        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel)
    {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    } //convert pixel coordinate to board coordinate

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("CheckersApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Piece makePiece(PieceType type, int x, int y)
    {
        Piece piece = new Piece(type, x, y); //placing new piece
        piece.setOnMouseReleased(e ->
        {
            int newX = toBoard(piece.getLayoutX()); //piece coordinates
            int newY = toBoard(piece.getLayoutY());
            MoveResult result;

            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT)
            {//checks bounds of move
                result = new MoveResult(MoveType.NONE);
            } else {//check move validity
                result = tryMove(piece, newX, newY);
            }

            int x0 = toBoard(piece.getOldX()); //old coordinates
            int y0 = toBoard(piece.getOldY());

            switch (result.getType())
            {
                case NONE://no move
                    piece.abortMove();
                    break;
                case NORMAL://one tile move
                    if(kingMe(newY, piece.getType()))
                        piece.setKingStatus(true);
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null); //remove piece from tile
                    board[newX][newY].setPiece(piece);//place piece on new tile
                    break;
                case KILL://two tile move
                    if(kingMe(newY, piece.getType()))
                        piece.setKingStatus(true);
                    piece.move(newX, newY); //move the piece
                    board[x0][y0].setPiece(null); //remove piece from tile
                    board[newX][newY].setPiece(piece); //place piece on new tile

                    Piece otherPiece = result.getPiece(); //piece to remove
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);//remove the piece from tile
                    pieceGroup.getChildren().remove(otherPiece); //remove piece from group
                    break;
            }
        });
        return piece;
    }

    private boolean kingMe(int yPos, PieceType type)
    {
        switch (type)
        {
            case RED:
                System.out.println("Red "+yPos);
                return yPos == HEIGHT - 1;
            case WHITE:
                System.out.println("White "+yPos);
                return yPos == 0;
            default:
                System.exit(-1);
        }
        System.exit(-1);
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}