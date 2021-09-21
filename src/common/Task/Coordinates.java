package common.Task;

import common.Exceptions.CoordinatesException;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private float x; //Максимальное значение поля: 334
    private Double y; //Поле не может быть null
    public Coordinates(float x, Double y) throws CoordinatesException {
        if (x < 70 && y != null) {
            this.x = x;
            this.y = y;
        } else {
            throw new CoordinatesException("Некорректные координаты");
        }
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
