package budgetallocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BudgetAllocator {

    public static void allocateBudget(int budget, List<Component> components) {
        int totalSatisfaction = 0;
        List<Brand> selectedBrands = new ArrayList<>();

        // Sort each component's brands by rating-to-cost ratio in descending order
        for (Component component : components) {
            component.getBrands().sort((b1, b2) -> {
                double ratio1 = (double) b1.getRating() / b1.getCost();
                double ratio2 = (double) b2.getRating() / b1.getCost();
                return Double.compare(ratio2, ratio1);
            });
        }

        // Try to allocate budget greedily
        for (Component component : components) {
            for (Brand brand : component.getBrands()) {
                if (brand.getCost() <= budget) {
                    budget -= brand.getCost();
                    totalSatisfaction += brand.getRating();
                    selectedBrands.add(brand);
                    break;
                }
            }
        }

        // Output the total satisfaction achieved
        System.out.println("Total satisfaction: " + totalSatisfaction);

        // Output the selected brands for each component
        System.out.println("\nSelected brands for each component:");
        for (Component component : components) {
            boolean brandSelected = false;
            for (Brand brand : selectedBrands) {
                if (component.getBrands().contains(brand)) {
                    System.out.println("Component " + component.getName() + ": " +
                            "Brand " + brand.getName() + " with cost " + brand.getCost() + ", rating " + brand.getRating());
                    brandSelected = true;
                    break;
                }
            }
            if (!brandSelected) {
                System.out.println("Component " + component.getName() + ": No brand selected.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input for total budget
        System.out.print("Enter total budget: ");
        int budget = getValidIntInput(sc, "Please enter a valid budget (positive integer):");

        // Input for number of components
        System.out.print("Enter number of components: ");
        int numComponents = getValidIntInput(sc, "Please enter a valid number of components:");

        List<Component> components = new ArrayList<>();

        // Input for each component
        for (int i = 0; i < numComponents; i++) {
            // Get component name
            System.out.print("Enter name for component " + (i + 1) + ": ");
            String componentName = getValidStringInput(sc);

            Component component = new Component(componentName);

            // Get number of brands for the component
            System.out.print("Enter number of brands for " + componentName + ": ");
            int numBrands = getValidIntInput(sc, "Please enter a valid number of brands:");

            for (int j = 0; j < numBrands; j++) {
                // Get brand name
                System.out.print("Enter name for brand " + (j + 1) + " of " + componentName + ": ");
                String brandName = getValidStringInput(sc);

                // Get brand cost
                System.out.print("Enter cost for brand " + (j + 1) + " of " + componentName + ": ");
                int cost = getValidIntInput(sc, "Please enter a valid cost (positive integer).");

                // Get brand rating
                System.out.print("Enter rating for brand " + (j + 1) + " of " + componentName + " (out of 5): ");
                int rating = getValidIntInput(sc, "Please enter a valid rating (between 1 and 5).");

                try {
                    component.addBrand(brandName, cost, rating);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    j--; // Decrement to re-enter the invalid brand
                }
            }

            components.add(component);
        }

        // Perform budget allocation using a greedy algorithm
        allocateBudget(budget, components);
        sc.close();
    }

    // Utility function to handle valid integer input
    private static int getValidIntInput(Scanner sc, String errorMessage) {
        while (!sc.hasNextInt()) {
            System.out.println(errorMessage);
            sc.next(); // Consume the invalid input
        }
        int value = sc.nextInt();
        sc.nextLine(); // Consume the newline character left after nextInt
        return value;
    }

    // Utility function to handle valid string input
    private static String getValidStringInput(Scanner sc) {
        String input = sc.nextLine().trim();
        while (input.isEmpty() || isNumeric(input)) { // Check if input is empty or numeric
            if (input.isEmpty()) {
                System.out.println("Name cannot be empty.");
            } else {
                System.out.println("Name cannot be a number, add a string.");
            }
            input = sc.nextLine().trim();
        }
        return input;
    }

    // Helper function to check if a string is numeric
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str); // Try to parse as an integer
            return true;
        } catch (NumberFormatException e) {
            return false; // It's not a number
        }
    }
}
