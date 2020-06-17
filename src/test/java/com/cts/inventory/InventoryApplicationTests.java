package com.cts.inventory;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.cts.inventory.controller.InventoryApplicationCtrlTests;
import com.cts.inventory.repository.InventoryApplicationRepositoryTests;
import com.cts.inventory.service.InventoryApplicationServiceTests;

@RunWith(Suite.class)

//specify an array of test classes
@Suite.SuiteClasses({
	InventoryApplicationCtrlTests.class,
	InventoryApplicationServiceTests.class,
	InventoryApplicationRepositoryTests.class
})


public class InventoryApplicationTests {
	
	

}

