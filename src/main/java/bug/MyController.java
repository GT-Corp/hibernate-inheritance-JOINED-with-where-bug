package bug;

import bug.repository.SuperRepository;
import bug.repository.SubObjectRepository;
import bug.entity.SubObject;
import bug.entity.Super;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    //https://hibernate.atlassian.net/browse/HHH-13712?jql=text%20~%20%22Inheritance%20count%20where%22
    final SuperRepository superRepo;
    final SubObjectRepository subObjRepo;

    public MyController(SuperRepository superRepo, SubObjectRepository subObjRepo) {
        this.superRepo = superRepo;
        this.subObjRepo = subObjRepo;
    }

    @GetMapping(value = "/super")
    public Page<Super> list() {

        for (int i = 0; i < 20; i++)
            superRepo.save(new Super());

        return superRepo.findAll(PageRequest.of(0, 10));

    }

    @GetMapping(value = "/subobj")
    public Page<SubObject> listA() {

        for (int i = 0; i < 20; i++) {
            SubObject a = new SubObject(String.valueOf(i), i);
            if (i % 2 == 0) {
                a.deleted = true;
            }
            subObjRepo.save(a);
        }

        return subObjRepo.findAll(PageRequest.of(0, 10));

    }
}
