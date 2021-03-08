package be.infernalwhale.service;

import be.infernalwhale.model.Category;
import be.infernalwhale.service.exception.ValidationException;

import java.sql.SQLException;
import java.util.List;

/**
 * Welcome to the Category interface.
 *
 * This one is pretty straightforward. You will need to create an implementation that can perform the 4 CRUD operations
 * for the Category table.
 * INSERT/SELECT/UPDATE/DELETE
 *
 */
public interface CategoryService {
    List<Category> getCategories();
    Category createCategory(Category category) throws ValidationException, SQLException;
    Category updateCategory(Category category);
    boolean deleteCategory(Category category);
}
