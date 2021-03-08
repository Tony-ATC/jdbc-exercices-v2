package be.infernalwhale.service.intImpl;

import be.infernalwhale.model.Category;
import be.infernalwhale.service.CategoryService;
import be.infernalwhale.service.ConnectionManager;
import be.infernalwhale.service.ServiceFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class categoryServiceATC implements CategoryService {
    private final List<Category> categories = generateListDBATC();

    private final ConnectionManager connectionManager = ServiceFactory.createConnectionManager();

    @Override
    public List<Category> getCategories() {
        List<Category> catList = new ArrayList<>();
        String query = "SELECT * FROM Cat";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Category");

                Category cat = new Category(id, name);
                catList.add(cat);
            }

        } catch (SQLException IDK) {
            IDK.printStackTrace();
        }
        return catList;
    }

    @Override
    public Category createCategory(Category category) {
        String query = "INSERT INTO Cat (Id, Cat) VALUES (?, ?)";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, category.getId());
            statement.setString(2, category.getCategoryName());
            statement.execute();

        } catch (SQLException IDK) {
            IDK.printStackTrace();
        }
        return category;
    }

    @Override
    public Category updateCategory(Category category) {
        String query = "UPDATE Cat Set Cat = ? WHERE Id (?)";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, category.getId());
            statement.setString(2, category.getCategoryName());
            statement.execute();

        } catch (SQLException IDK) {
            IDK.printStackTrace();
        }
        return category;
    }

    @Override
    public boolean deleteCategory(Category category) {
        String query = "DELETE FROM Cat WHERE Id (?)";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, category.getId());
            statement.setString(2, category.getCategoryName());
            statement.execute();

        } catch (SQLException IDK) {
            IDK.printStackTrace();
        }

        return true;
    }

    private List<Category> generateListDBATC() {
        return new ArrayList<>(List.of(
                new Category(2, "Alcoholarm"),
                new Category(3, "Alcoholvrij"),
                new Category(4, "Ale"),
                new Category(5, "Alt"),
                new Category(6, "Amber"),
                new Category(7, "Bierrette"),
                new Category(8, "Bitter"),
                new Category(9, "Donkerbok"),
                new Category(11, "Dort"),
                new Category(12, "Dubbel Donker"),
                new Category(13, "Edelbier"),
                new Category(14, "Extra"),
                new Category(15, "Faro"),
                new Category(16, "Geuze")
        ));
    }

}
