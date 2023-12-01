
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityService {

    @Autowired
    private EntityRepository entityRepository;

    public boolean checkEntityExistence(Long id) {
        return entityRepository.existsById(id);
    }
}
