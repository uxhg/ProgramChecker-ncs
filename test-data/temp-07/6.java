import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.ResponseEntity;
 import java.util.Optional;
 import javax.persistence.EntityNotFoundException;
 import javax.persistence.criteria.CriteriaBuilder;
 
 import com.example.myapp.entities.Entity;

@RestController
public class EntityController {

@Autowired
private EntityService entityService;

@GetMapping(/entities/{id})
public ResponseEntity<String> checkEntityExistence(@PathVariable Long id) {
try {
Optional<Entity> optionalEntity = entityService.findById(id);
if (optionalEntity.isPresent()) {
return ResponseEntity.ok().body(Entity exists.);
} else {
throw new EntityNotFoundException();
}
} catch (Exception e) {
return ResponseEntity.status(500).body(e.getMessage());
}
}
}
