package budgetallocator;

public class Brand {
    String name;
    int cost;
    int rating;

    public Brand(String name, int cost, int rating) {
        this.name = name;
        this.cost = cost;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getRating() {
        return rating;
    }
}
