package model.test_cases;

// We did not test the functionality for buying a coco tile because this functionality is already tested across
// multiple different tests.

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ 
	BuildTest.class,
    StockpileTradeTest.class, 
    MarketplaceTradeTest.class, 
    ProduceResourcesTest.class,
    MoveGhostCaptainTest.class,
    SetupBoardTest.class,
    SetupPlayersTest.class,
    CheckWinTest.class
})

public class AllTests {

}