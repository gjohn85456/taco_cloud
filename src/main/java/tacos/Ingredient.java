package tacos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Ingredient {
    @Id
    private final String id;
    private final String name;
    private final Type type;

//    @Override
//    public boolean isNew() {
//        return true;
//    }

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}


