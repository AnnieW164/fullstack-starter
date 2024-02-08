package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import java.util.List;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;


/**
 * Test Inventory DAO.
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class InventoryDAOTest {
  @ClassRule
  public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

  @Resource
  private MongoTemplate mongoTemplate;
  private InventoryDAO inventoryDAO;
  private static final String NAME = "Amber";
  private static final String PRODUCT_TYPE = "hops";

  @Before
  public void setup() {
    this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
  }

  @After
  public void tearDown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  /**
   * Test Find All method.
   */
  @Test
  public void findAll() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventory);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertFalse(actualInventory.isEmpty());
  }

  /** 
   * 
   * Test Create method
   */
  @Test
  public void create() {
    
    Inventory inventory1 = new Inventory();
    inventory1.setName(NAME);
    inventory1.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory1);
    Assert.assertEquals(1, this.mongoTemplate.findAll(Inventory.class).size());

  }
  /**
   * Test Delete method.
   */
  @Test
  public void delete() {
    Inventory inventory1 = new Inventory();
    inventory1.setName(NAME);
    inventory1.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory1);

    Inventory inventory2 = new Inventory();
    inventory2.setName(NAME);
    inventory2.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory2);

    
    Assert.assertEquals(2, this.mongoTemplate.findAll(Inventory.class).size());
    String[] ids = {inventory1.getId(), inventory2.getId()};
    this.inventoryDAO.delete(ids);
    Assert.assertEquals(0, this.mongoTemplate.findAll(Inventory.class).size());

  }

  /*
   * Test Update method
   */
  @Test
  public void update() {
    Inventory inventory1 = new Inventory();
    inventory1.setName("Annie");
    inventory1.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory1);
    String id1 = inventory1.getId();

    Inventory inventory2 = new Inventory();
    inventory2.setName("Leah");
    inventory2.setProductType(PRODUCT_TYPE);

    Inventory inventory3 = this.inventoryDAO.update(id1, inventory2).get();

    Assert.assertEquals(inventory3.getName(), inventory2.getName());
    Assert.assertEquals(id1, inventory3.getId());
  }

}
