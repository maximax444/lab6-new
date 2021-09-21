package common.Task;

import java.io.Serializable;

public class Location implements Serializable {
    private float x;
    private Float y; //Поле не может быть null
    private String name; //Поле не может быть null
    public Location(float x, Float y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
