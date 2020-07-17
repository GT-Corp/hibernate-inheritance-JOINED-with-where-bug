package bug.entity;

import javax.persistence.*;

import org.hibernate.annotations.Where;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Where(clause = "DELETED = 0")
public class Super {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public boolean deleted;
}