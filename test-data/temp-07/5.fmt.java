import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; n 
import org. springframework. web. bind . annotation.*; n 
import java.util.Optional; 
n n@RestControllernpublic class EntityController { 
    rAutowire dprivate Entit ySer vice entit yServ ice ;
    rn@ GetMappin g( value '/ent ity/{id}', produces= 'application/json') 
        public ResponseEntity<String> checkEntityExistence(@PathVaria ble Long id ) { 
            Optional<Entity> optionalEntity = entity Service.findById(id); 
            if(optionalEnti ty.isPresent()) { return ResponseEntity.ok().body('Enti ty exists '); } 
            else{ ret urn ResponseEntity.status(HttpStatus.NOT_FOUND).body ('Ent ity not found'); }
        }
