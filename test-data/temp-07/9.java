import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import java.util.Optional;@Servicepublic class EntityService {    @Autowired    private EntityRepository entityRepository;Enter code here...extends Crudrepository<Entity, Long> {}nublic boolean checkEntityExistence(Long id) {Enter code here...tional<Entity> optionalEntity = entityRepository.findById(id);Enter code here...t (optionalEntity.isPresent());Enter code here...}}
