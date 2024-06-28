package gm.tienda_libros.servicio;


import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
@ToString
public class DataBaseUtils {
    private final EntityManager entityManager;

    @Autowired
    public DataBaseUtils(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<String> getEntityNames() {
        List<String> entityNames = new ArrayList<>();
        for (EntityType<?> entityType : entityManager.getMetamodel().getEntities()) {
            entityNames.add(entityType.getName());
        }
        return entityNames;
    }
}
