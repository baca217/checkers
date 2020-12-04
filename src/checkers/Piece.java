package checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import static checkers.CheckersApp.TILE_SIZE;
import static checkers.CheckersApp.WIDTH;
import static checkers.CheckersApp.HEIGHT;

public class Piece extends StackPane
{
    //variables
    private PieceType type;
    private double mouseX, mouseY;
    private double oldX, oldY;
    private double kingPos;
    private boolean king;
    private CheckersApp appRef;
    //constructor
    public Piece(PieceType type, int x, int y, CheckersApp app)
    {
        this.type = type;
        this.king = false;
        this.appRef = app;
        this.kingPos = (type == PieceType.RED ? (HEIGHT - 1) * TILE_SIZE : 0);
        move(x, y); //initial position

        Circle shape = new Circle(); //creating graphic for checkers piece
        shape.setRadius((TILE_SIZE - 10)/2);
        shape.setFill(type == PieceType.RED
                ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4")); //set color for piece
        shape.setStroke(Color.BLACK); //outline of piece
        shape.setStrokeWidth(TILE_SIZE/20);
        shape.setTranslateX((TILE_SIZE/(100/3))); //centering piece graphic
        shape.setTranslateY((TILE_SIZE/(100/3)));

        getChildren().addAll(shape);

        setOnMouseReleased(e ->  //behavior to happen after piece release
        {
            int newX = toBoard(this.getLayoutX()); //piece coordinates
            int newY = toBoard(this.getLayoutY());
            MoveResult result;
            PieceType curTurn = this.appRef.getTurn();

            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT || curTurn != this.type)
            {//checks bounds of move
                if(curTurn != this.type)
                {
                    JOptionPane.showMessageDialog(null, "Currently team "+curTurn.name()+"\'s turn!");
                }
                result = new MoveResult(MoveType.NONE);
            } else {//check move validity
                result = tryMove(newX, newY);
            }

            int x0 = toBoard(this.getOldX()); //old coordinates
            int y0 = toBoard(this.getOldY());

            switch (result.getType())
            {
                case NONE://no move
                    this.abortMove();
                    break;
                case NORMAL://one tile move
                    this.move(newX, newY);
                    this.appRef.updateBoard(x0, y0, newX, newY, null);
                    break;
                case KILL://two tile move
                    this.move(newX, newY); //move the piece
                    this.appRef.updateBoard(x0, y0, newX, newY, result.getPiece());
            }
        });

        setOnMousePressed(e ->
        {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        }); //getting positions on mouse press

        setOnMouseDragged(e ->
        {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        }); //setting new positions based on mouse drag and old positions
    }
    //methods
    private int toBoard(double pixel)
    {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    } //convert pixel coordinate to board coordinate

    public MoveResult tryMove(int newX, int newY)
    {
        Tile[][] board = this.appRef.getBoard();

        if(newX < 0 || newX > WIDTH - 1 || newY < 0 || newY > HEIGHT - 1 ) //boundary checking
            return new MoveResult(MoveType.NONE);

        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) //no move on odd tiles
        {
            return new MoveResult(MoveType.NONE);
        }

        int x0 = toBoard(this.getOldX());
        int y0 = toBoard(this.getOldY());
        if(this.getKingStatus())
        {//piece is a king, can move in any direction
            if (Math.abs(newX - x0) == 1 && Math.abs(newY - y0) == 1)
            { //check if move is valid
                return new MoveResult(MoveType.NORMAL);
            } else if (Math.abs(newX - x0) == 2 && Math.abs(newY - y0) == 2)
            {//check if kill move is valid
                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;
                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != this.getType())
                {//check if piece exists and is an enemy
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());//remove enemy
                }
            }
        }
        else
        {//piece is not king, must check move direction
            if (Math.abs(newX - x0) == 1 && newY - y0 == this.getType().moveDir)
            { //check if move is valid
                return new MoveResult(MoveType.NORMAL);
            } else if (Math.abs(newX - x0) == 2 && newY - y0 == this.getType().moveDir * 2)
            {//check if kill move is valid
                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;
                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != this.getType())
                {//check if piece exists and is an enemy
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());//remove enemy
                }
            }
        }

        return new MoveResult(MoveType.NONE);
    }
    public void move(int x, int y)
    {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        if(oldY == kingPos) //check if we have a new king
            setKingStatus();
        relocate(oldX, oldY); //moving graphic piece
    }
    public void abortMove() { relocate(oldX, oldY); } //move piece to old old position
    //getters
    public PieceType getType() {
        return type;
    }
    public boolean getKingStatus(){ return king; }
    public void setKingStatus()
    {
        this.king = true;
        Text k = new Text();
        k.setText("K");
        k.setFont(Font.font("Verdana", 30));
        k.setFill(Color.BLACK);
        k.setTranslateX((TILE_SIZE/(100/3))); //centering piece graphic
        k.setTranslateY((TILE_SIZE/(100/3)));

        getChildren().addAll(k);
        setOnMousePressed(e ->
        {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        }); //getting positions on mouse press

        setOnMouseDragged(e ->
        {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        }); //setting new positions based on mouse drag and old positions
    }
    public double getOldX() { return oldX; }
    public double getOldY() { return oldY; }
}