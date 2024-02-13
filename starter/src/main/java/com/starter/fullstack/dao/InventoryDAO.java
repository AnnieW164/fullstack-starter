package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

/**
 * Inventory DAO
 */
public class InventoryDAO {
  private final MongoTemplate mongoTemplate;
  private static final String NAME = "name";
  private static final String PRODUCT_TYPE = "productType";

  /**
   * Default Constructor.
   * @param mongoTemplate MongoTemplate.
   */
  public InventoryDAO(MongoTemplate mongoTemplate) {
    Assert.notNull(mongoTemplate, "MongoTemplate must not be null.");
    this.mongoTemplate = mongoTemplate;
  }

  /**
   * Constructor to build indexes for rate blackout object
   */
  @PostConstruct
  public void setupIndexes() {
    IndexOperations indexOps = this.mongoTemplate.indexOps(Inventory.class);
    indexOps.ensureIndex(new Index(NAME, Sort.Direction.ASC));
    indexOps.ensureIndex(new Index(PRODUCT_TYPE, Sort.Direction.ASC));
  }

  /**
   * Find All Inventory.
   * @return List of found Inventory.
   */
  public List<Inventory> findAll() {
    return this.mongoTemplate.findAll(Inventory.class);
  }

  /**
   * Save Inventory.
   * @param inventory Inventory to Save/Update.
   * @return Created/Updated Inventory.
   */
  public Inventory create(Inventory inventory) {
    inventory.setId(null);
    return this.mongoTemplate.save(inventory);
  }

  /**
   * Retrieve Inventory.
   * @param id Inventory id to Retrieve.
   * @return Found Inventory.
   */
  public Optional<Inventory> retrieve(String id) {
    return Optional.ofNullable(this.mongoTemplate.findById(id, Inventory.class));
  }

  /**
   * Update Inventory.
   * @param id Inventory id to Update.
   * @param inventory Inventory to Update.
   * @return Updated Inventory.
   */
  public Optional<Inventory> update(String id, Inventory inventory) {
    // TODO
    Update invUpdate = new Update();
    invUpdate.set("name", inventory.getName());
    invUpdate.set("productType", inventory.getProductType());
    invUpdate.set("description", inventory.getDescription());
    invUpdate.set("averagePrice", inventory.getAveragePrice());
    invUpdate.set("amount", inventory.getAmount());
    invUpdate.set("unitOfMeasurement", inventory.getUnitOfMeasurement());
    invUpdate.set("bestBeforeDate", inventory.getBestBeforeDate());

    this.mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)), invUpdate, Inventory.class);
    Inventory invToUpdate = retrieve(id).get();
    return Optional.of(invToUpdate);

  }

  /**
   * Delete Inventory By Id.
   * @param ids Id of Inventory.
   * @return Deleted Inventory.
   */
  public Optional<List<Inventory>> delete(List<String> ids) {
    List<Inventory> retList = new ArrayList<>();
    for (int i = 0; i < ids.size(); i++) {
      retList.add(this.mongoTemplate.findAndRemove(new Query(Criteria.where("id").is(ids.get(i))), Inventory.class));
    }
    return Optional.ofNullable(retList);
  }
}
