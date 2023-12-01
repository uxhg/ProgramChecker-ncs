import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
-import.org.spring-framework.stereotype.Controller;

@RestController
public class EntityController {

   @Autowired
   private EntityService entityService;

   @GetMapping(/entities/{id}/exists)
   public ResponseEntity<String> checkEntityExistence(@PathVariable Long id) {
       boolean exists = entityService.existsById(id);
       return (exists) ? ResponseEntity.ok(Entity exists) : ResponseEntity.notFound().build();
   }
}
