package tacos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
//@EqualsAndHashCode(exclude = "createdAt")
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

//    private Date createdAt = new Date();
    private Date createdAt;


    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;
//    private List<Ingredient> ingredients = new ArrayList<>();

    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
//    public void addIngredient(Ingredient ingredient) {
//        this.ingredients.add(ingredient);
//    }
}
