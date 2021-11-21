package collection;

import annotations.GreaterThan;
import annotations.NotNull;

import java.io.Serializable;

/**
 * class for pointing the location of an object in space
 */
public class Coordinates implements Serializable {

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @NotNull
    @GreaterThan(-958)
    private Float x; //Значение поля должно быть больше -958, Поле не может быть null
    @NotNull
    private Float y; //Поле не может быть null

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }
}
