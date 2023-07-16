package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tacos.data.IngredientRepository;

import tacos.Ingredient.Type;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}
	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo) {
		return args -> {
			repo.deleteAll(); // TODO: Quick hack to avoid tests from stepping on each other with constraint violations
			repo.save(new Ingredient("FLTO", "Flour Tortilla1", Type.WRAP));
			repo.save(new Ingredient("COTO", "Corn Tortilla1", Type.WRAP));
			repo.save(new Ingredient("GRBF", "Ground Beef1", Type.PROTEIN));
			repo.save(new Ingredient("CARN", "Carnitas1", Type.PROTEIN));
			repo.save(new Ingredient("TMTO", "Diced Tomatoes1", Type.VEGGIES));
			repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
			repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
			repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
			repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
			repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
		};
	}
}
