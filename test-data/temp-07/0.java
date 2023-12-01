import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;@Servicepublic class EntityService {    @Autowired    private EntityRepository entityRepository;    public boolean existsById(Long id) {Enter code here
        return entityRepository.existsById(id);Enter code here
}Enter code here
}
