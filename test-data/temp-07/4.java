import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;@Servicepublic class EntityService {    @Autowired    private EntityRepository entityRepository;    public boolean checkEntityExistence(Long id) {TheExistsById(id);} }
