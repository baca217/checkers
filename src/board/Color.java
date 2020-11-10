package board;

public class Color
{
    LIGHT, DARK;
    public Color opponent()
    {
        return this == LIGHT ? DARK : LIGHT;
    }
}
