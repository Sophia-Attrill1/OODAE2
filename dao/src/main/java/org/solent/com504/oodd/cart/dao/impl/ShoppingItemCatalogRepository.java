package org.solent.com504.oodd.cart.dao.impl;

import java.util.List;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemCatalogRepository  extends JpaRepository<ShoppingItem, Long>{
    
    @Query("select s from ShoppingItem s where s.name = :name")
    public List<ShoppingItem> findByName(@Param("name")String name);
    
    @Query("select s from ShoppingItem s where s.uuid = :uuid")
    public List<ShoppingItem> findByUuid(@Param("uuid")String uuid);
    
    
}
