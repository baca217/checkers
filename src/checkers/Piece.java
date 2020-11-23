package checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static checkers.CheckersApp.TILE_SIZE;

public class Piece extends StackPane
{
    //variables
    private PieceType type;
    private double mouseX, mouseY;
    private double oldX, oldY;
    private boolean king;
    //constructor
    public Piece(PieceType type, int x, int y)
    {
        this.type = type;
        this.king = false;
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
    public void move(int x, int y)
    {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY); //moving graphic piece
    }
    public void abortMove() { relocate(oldX, oldY); } //move piece to old old position
    //getters
    public PieceType getType() {
        return type;
    }
    public boolean getKingStatus(){ return king; }
    public void setKingStatus(boolean status)
    {
        this.king = status;
        if(this.king)
        {
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
    }
    public double getOldX() { return oldX; }
    public double getOldY() { return oldY; }
}