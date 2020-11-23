package checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane
{
    //variables
    public static final int TILE_SIZE = 100;
    private PieceType type;
    private double mouseX, mouseY;
    private double oldX, oldY;
    //methods
    public PieceType getType() {
        return type;
    }
    public double getOldX() { return oldX; }
    public double getOldY() { return oldY; }
    //constructor
    public Piece(PieceType type, int x, int y) {
        this.type = type;
        move(x, y); //initial position
        Ellipse bg = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26); //shadow of piece
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * .03); //size of ellipse

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.625) / 2); //translation to fit in middle of tile
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.52) / 2 + TILE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        ellipse.setFill(type == PieceType.RED
                ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4")); //piece color red or white

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.05);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.625) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.52) / 2);

        getChildren().addAll(bg, ellipse);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        }); //getting positions on mouse press

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        }); //setting new positions based on mouse drag and old positions
    }

    public void move(int x, int y)
    {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY); //setting piece positions
    }

    public void abortMove() {
        relocate(oldX, oldY);
    } //move piece to old old position
}