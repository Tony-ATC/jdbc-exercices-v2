package be.infernalwhale.service.intImpl;

import be.infernalwhale.model.Beer;
import be.infernalwhale.model.Brewer;
import be.infernalwhale.service.BrewersService;
import be.infernalwhale.service.ConnectionManager;
import be.infernalwhale.service.ServiceFactory;
import be.infernalwhale.service.data.Valuta;
import be.infernalwhale.service.exception.ValidationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class brewerServiceATC implements BrewersService {
    private final List<Brewer> brewers = generateBrewers();

    private final ConnectionManager connectionManager = ServiceFactory.createConnectionManager();
    private Object Brewer;

    public Brewer getBrewers(int brewer_id) {
        String query = "SELECT * FROM brewers WHERE id LIKE ?";
        Brewer brewer = null;

        try (Connection connection = connectionManager.getConnection();

             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, String.valueOf(brewer_id));
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                int zipcode = rs.getInt("Zipcode");
                String city = rs.getString("City");
                int turnover = rs.getInt("Turnover");
                Brewer = new Brewer(brewer_id, name, address, zipcode, city, turnover);
            }

            }
        catch (SQLException IDK) {
            IDK.printStackTrace();
        }
        return brewer;
    }

    @Override
    public List<Brewer> getBrewers() {

        List<Brewer> homerList = new ArrayList<>();
        String query = "SELECT * FROM Brewers";

        try (Connection connection = connectionManager.getConnection();

             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                int zipcode = rs.getInt("Zipcode");
                String city = rs.getString("City");
                int turnover = rs.getInt("Turnover");


                Brewer homer = new Brewer(id, name, address, zipcode, city, turnover);
                homerList.add(homer);
            }

        } catch (SQLException IDK) {
            IDK.printStackTrace();
        }
        return homerList;
    }

    @Override
    public List<Brewer> getBrewers(Valuta valuta) {
        return this.brewers.stream()
                .map(brewer -> convertToCurrency(brewer, valuta))
                .collect(Collectors.toList());
    }

    @Override
    public List<Brewer> getBrewers(String nameFilter) {
        return this.brewers.stream()
                .filter(brewer -> brewer.getName().equalsIgnoreCase(nameFilter))
                .collect(Collectors.toList());
    }

    @Override
    public List<Brewer> getBrewers(String nameFilter, Valuta valuta) {
        return this.brewers.stream()
                .filter(brewer -> brewer.getName().equalsIgnoreCase(nameFilter))
                .map(brewer -> convertToCurrency(brewer, valuta))
                .collect(Collectors.toList());
    }

    @Override
    public Brewer createBrewer(Brewer brewer)  {
        String query = "INSERT INTO Brewer (ID, Name, Address, Zipcode, City, Turnover) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, brewer.getId());
            statement.setString(2, brewer.getName());
            statement.setString(3, brewer.getAddress());
            statement.setInt(4, brewer.getZipcode());
            statement.setString(5, brewer.getCity());
            statement.setInt(6, brewer.getTurnover());
            statement.execute();

        } catch (SQLException IDK) {
            IDK.printStackTrace();
        }
        return brewer;
    }

    @Override
    public Brewer updateBrewer(Brewer brewer) throws ValidationException {
        String query = "UPDATE Brewers Set(name, address, zipcode, city, turnover) = ? WHERE Id (?)";

        try (Connection connection = connectionManager.getConnection();

             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, brewer.getId());
            statement.setString(2, brewer.getName());
            statement.setString(3, brewer.getAddress());
            statement.setInt(4, brewer.getZipcode());
            statement.setString(5, brewer.getCity());
            statement.setInt(6, brewer.getTurnover());
            statement.execute();

            if (brewer.getTurnover() < 0) throw new ValidationException("Turnover can not be negative");


        } catch (SQLException IDK) {
            IDK.printStackTrace();
        }
        return brewer;
    }

    @Override
    public boolean deleteBrewerById(Integer id) {
        String query = "DELETE FROM Brewers WHERE Id (?)";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException IDK) {
            IDK.printStackTrace();
        }

        return true;
    }

    private List<Brewer> generateBrewers() {
        return new ArrayList<>(List.of(
                new Brewer(1, "Achouffe", "Route du Village 32", 6666, "Achouffe-Wibrin", 10000),
                new Brewer(2, "Alken", "Stationstraat 2", 3570, "Alken", 950000),
                new Brewer(3, "Ambly", "Rue Principale 45", 6953, "Ambly-Nassogne", 500),
                new Brewer(4, "Anker", "Guido Gezellelaan 49", 2800, "Mechelen", 3000),
                new Brewer(6, "Artois", "Vaartstraat 94", 3000, "Leuven", 4000000)
        ));
    }

    private Brewer convertToCurrency(Brewer b, Valuta currency) {
        Brewer brewerCopy = new Brewer(b);
        Integer turnover = (int) Math.round(b.getTurnover() * currency.getConversionRate());
        brewerCopy.setTurnover(turnover);
        return brewerCopy;
    }

}
