package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inventory Controller.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
  private final InventoryDAO inventoryDAO;

  /**
   * Default Constructor.
   * @param inventoryDAO inventoryDAO.
   */
  public InventoryController(InventoryDAO inventoryDAO) {
    Assert.notNull(inventoryDAO, "Inventory DAO must not be null.");
    this.inventoryDAO = inventoryDAO;
  }

  /**
   * Find Inventories.
   * @return List of Inventory.
   */
  @GetMapping
  public List<Inventory> findInventories() {
    return this.inventoryDAO.findAll();
  }
  
  /**
   * 
   * 
   * Save Inventory.
   * @param inventory inventory.
   * @return Inventory.
   */
  @PostMapping
  public Inventory saveInventory(@Valid @RequestBody Inventory inventory) {
    return this.inventoryDAO.create(inventory);
  }

    /**
   * Delete Inventory By Id.
   *
   * @param ids id.
   * @return Deleted inventory
   */
  @DeleteMapping
  public Optional<List<Inventory>> deleteInventoryById(@RequestBody List<String> ids) {
    return this.inventoryDAO.delete(ids);
  }

  /**
   * Update Inventory By Id.
   *
   * @param id id
   * @param inventory inventory
   * @return Updated inventory
   */
  @PutMapping("/{id}")
  public Optional<Inventory> updateInventory(@PathVariable("id") String id, @Valid @RequestBody Inventory inventory) {
    return this.inventoryDAO.update(id, inventory);
  }


}

