import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;@Servicepublic class EntityService {    @Autowired    private EntityRepository entityRepository;    public boolean checkEntityExists(Long id) {        return entityRepository.existsById(id);   }}