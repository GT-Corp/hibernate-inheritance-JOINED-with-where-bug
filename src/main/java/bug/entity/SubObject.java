package bug.entity;

import javax.persistence.Entity;

@Entity
public class SubObject extends Super {

    public String name;

    public int age;

    public SubObject() {
    }

    public SubObject(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
