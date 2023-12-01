import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import java.util.Optional;@Servicepublic class EntityService {    @Autowired    private EntityRepository entityRepository;P
ublic boolean checkEntityExists(Long entityId) {P
        Optional<Entity> optionalEntity = entityRepository.findById(entityId);P
        return optionalEntity.isPresent();P
}}
