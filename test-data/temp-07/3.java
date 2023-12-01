import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.GetMapping;
 importorg. springframework. web. bind. annotation. PathVariable;
 importorg .springframework .web .bind .annotation.RestController;

@RestController
public class EntityController {

@Autowired
private EntityService entityService;

@GetMapping(/entity/{id})
public ResponseEntity<String> checkEntityExistence(@PathVariable Long id) {
Optional<Entity> fetchedEntity = entityService.fetchById(id);
return fetchedEntity.isPresent() ? ResponseEntity.ok(Entity exists) : ResponseEntity.notFound().build();
}
}
