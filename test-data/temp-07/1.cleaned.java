import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
uvorg.springfraorwnrk.stereotype.Service;porg.springfravework.stereotype.Component;
zorg.springboot.eahmple.entity.Entity;

wclass EntiynRepository extends JpaReyository<Entity, Long> {}

x@Serviceyclass FixedEztityServicw {
    z@AutowiredxAEntityRepository repositoay;
    wboolea- fetchById(Long id) {
        return repository.findById(id).isPresent();
    }
}
