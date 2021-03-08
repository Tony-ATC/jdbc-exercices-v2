package be.infernalwhale.service;

import be.infernalwhale.service.intImpl.beerServiceATC;
import be.infernalwhale.service.intImpl.brewerServiceATC;
import be.infernalwhale.service.intImpl.categoryServiceATC;
import be.infernalwhale.service.intImpl.connectionManagerATC;

/**
 * This is the ServiceFactory. This class will provide the implementations for the different Service interfaces
 * as defined in the be.infernalwhale.service package to the view layer. The mock (or fake) implementations can
 * be found in the fake subpackage. You will need to write the actual implementations for these interfaces,
 * implementations that actually make use of a real database.
 *
 * You are allowed to add code in this class. The only thing you shouldn't do is change the method signatures
 * of the currently existing methods. You may add methods and properties as you see fit
 *
 * At the moment, this class is 100% static. We'll look into a better solution later in the course.
 */
public class ServiceFactory {

    private static ConnectionManager connectionManager;
    public static ConnectionManager createConnectionManager() {
        if (connectionManager == null) connectionManager = new connectionManagerATC();
        return connectionManager;
    }

    private static CategoryService categoryService;
    public static CategoryService createCategoryService() {
        if(categoryService == null) categoryService = new categoryServiceATC();
        return categoryService;
    }

    private static BrewersService brewersService;
    public static BrewersService createBrewersService() {
        if(brewersService == null) brewersService = new brewerServiceATC();
        return brewersService;
    }

    private static BeerService beerService;
    public static BeerService createBeerService() {
        if(beerService == null) beerService = new beerServiceATC();
        return beerService;
    }
}
