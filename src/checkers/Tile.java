package checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle
{
    //variables
    private Piece piece;
    //constructors
    public Tile(boolean light, int x, int y)
    {
        setWidth(CheckersApp.TILE_SIZE);
        setHeight(CheckersApp.TILE_SIZE);

        relocate(x * CheckersApp.TILE_SIZE, y * CheckersApp.TILE_SIZE); //move tiles around to fit board

        setFill(light ? Color.valueOf("#ffccbb") : Color.valueOf("#eeffbb"));
    }
    //methods
    public boolean hasPiece() {
        return piece != null;
    }
    //getters and setters
    public Piece getPiece() {
        return piece;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

}