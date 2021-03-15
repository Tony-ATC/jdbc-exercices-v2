package be.infernalwhale.service.intImpl;

import be.infernalwhale.model.Beer;
import be.infernalwhale.model.Brewer;
import be.infernalwhale.model.Category;
import be.infernalwhale.service.BeerService;
import be.infernalwhale.service.ConnectionManager;
import be.infernalwhale.service.ServiceFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class beerServiceATC implements BeerService {
    private final List<Beer> beers = generateBeerList();

    private final ConnectionManager connectionManager = ServiceFactory.createConnectionManager();

    @Override
    public List<Beer> getBeers() {
        List<Beer> duffList = new ArrayList<>();
        String query = "SELECT * FROM Beers";

        try (Connection connection = connectionManager.getConnection();

             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                double price = rs.getDouble("price");
                int alcohol = rs.getInt("alcohol");
                int stock = rs.getInt("stock");
                Brewer brewer = new brewerServiceATC().getBrewers(rs.getInt("Brewer_id"));
                Category category = new categoryServiceATC().getCategories(rs.getInt("Category_id"));

                Beer duff = new Beer(id, name, brewer, category, price, alcohol, stock);
                duffList.add(duff);
            }

        } catch (SQLException IDK) {
            IDK.printStackTrace();
        }
        return duffList;
    }

    @Override
    public List<Beer> getBeers(int alcoholConsumed) {
        List<Beer> duffManList = new ArrayList<>();
        String query = "SELECT * FROM Beers WHERE alcohol <= LIMIT 25";

        try (Connection connection = connectionManager.getConnection();

             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                double price = rs.getDouble("price");
                int alcohol = rs.getInt("alcohol");
                int stock = rs.getInt("stock");
                Brewer brewer = new brewerServiceATC().getBrewers(rs.getInt("Brewer_id"));
                Category category = new categoryServiceATC().getCategories(rs.getInt("Category_id"));

                Beer duff = new Beer(id, name, brewer, category, price, alcohol, stock);
                duffManList.add(duff);
            }

        } catch (SQLException IDK) {
            IDK.printStackTrace();
        }
        return duffManList;
    }


    private List<Beer> generateBeerList() {
        return new ArrayList<>(List.of(
                new Beer(4,"A.C.O.",new Brewer(104, "Test", null, null, null, null),new Category(18, null),2.75,188,7),
                new Beer(5,"Aalbeeks St. Corneliusbier.",new Brewer(113, null, null, null, null, null),new Category(18, null),2.65,12,6),
                new Beer(6,"Aardbeien witbier",new Brewer(56, null, null, null, null, null),new Category(53, null),2.65,44,6),
                new Beer(7,"Adler",new Brewer(51, null, null, null, null, null),new Category(42, null),2.65,44,6)
        ));
    }
}
