package budgetallocator;

import java.util.ArrayList;
import java.util.List;

public class Component {
    String name;
    List<Brand> brands;

    public Component(String name) {
        this.name = name;
        this.brands = new ArrayList<>();
    }

    public void addBrand(String name, int cost, int rating) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand name cannot be empty.");
        }
        if (cost <= 0) {
            throw new IllegalArgumentException("Cost must be positive.");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.brands.add(new Brand(name, cost, rating));
    }

    public String getName() {
        return name;
    }

    public List<Brand> getBrands() {
        return brands;
    }
}
