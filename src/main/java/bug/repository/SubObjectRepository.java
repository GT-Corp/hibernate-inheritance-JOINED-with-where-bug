package bug.repository;

import bug.entity.SubObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubObjectRepository extends JpaRepository<SubObject, Long> {

}
