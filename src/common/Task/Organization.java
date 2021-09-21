package common.Task;

import java.io.Serializable;

public class Organization implements Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String fullName; //Поле может быть null
    private Long annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address postalAddress; //Поле может быть null
    public Organization(int id, String orgName, String orgFullName, Long annualTurnover, OrganizationType type, Address postalAddress) {
        this.id = id;
        this.name = orgName;
        this.fullName= orgFullName;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.postalAddress = postalAddress;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", annualTurnover=" + annualTurnover +
                ", type=" + type +
                ", postalAddress=" + postalAddress.toString() +
                '}';
    }
}
