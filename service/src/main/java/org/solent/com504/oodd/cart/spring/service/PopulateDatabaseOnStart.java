/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.spring.service;

import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.cart.dao.impl.UserRepository;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.cart.dao.impl.ShoppingItemCatalogRepository;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.service.ShoppingCartImpl;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import java.util.UUID;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author cgallen
 */
@Component
public class PopulateDatabaseOnStart {

    private static final Logger LOG = LogManager.getLogger(PopulateDatabaseOnStart.class);

    private static final String DEFAULT_ADMIN_USERNAME = "globaladmin";
    private static final String DEFAULT_ADMIN_PASSWORD = "globaladmin";

    private static final String DEFAULT_USER_PASSWORD = "user1234";
    private static final String DEFAULT_USER_USERNAME = "user1234";

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ShoppingItemCatalogRepository ShoppingItemCatalogRepository;
    
    @Autowired
    private InvoiceRepository InvoiceRepository;
    
    @Autowired
    ShoppingCart shoppingCart = null;

    @PostConstruct
    public void initDatabase() {
        LOG.debug("initialising database with startup values");

        // initialising admin and normal user if dont exist
        User adminUser = new User();
        adminUser.setUsername(DEFAULT_ADMIN_USERNAME);
        adminUser.setFirstName("default administrator");
        adminUser.setPassword(DEFAULT_ADMIN_PASSWORD);
        adminUser.setUserRole(UserRole.ADMINISTRATOR);

        List<User> users = userRepository.findByUsername(DEFAULT_ADMIN_USERNAME);
        if (users.isEmpty()) {
            userRepository.save(adminUser);
            LOG.info("creating new default admin user:" + adminUser);
        } else {
            LOG.info("default admin user already exists. Not creating new :" + adminUser);
        }

        User defaultUser = new User();
        defaultUser.setUsername(DEFAULT_USER_USERNAME);
        defaultUser.setFirstName("default user");
        defaultUser.setPassword(DEFAULT_USER_PASSWORD);
        defaultUser.setUserRole(UserRole.CUSTOMER);

        users = userRepository.findByUsername(DEFAULT_USER_USERNAME);
        if (users.isEmpty()) {
            userRepository.save(defaultUser);
            LOG.info("creating new default user:" + defaultUser);
        } else {
            LOG.info("defaultuser already exists. Not creating new :" + defaultUser);
        }

        LOG.debug("database initialised");
    }
    
    @PostConstruct
    public void initShopDatabase() {
        LOG.debug("initialising database with startup values");

        // initialising admin and normal user if dont exist
        ShoppingItem item = new ShoppingItem();
        item.setName("Dog");
        item.setPrice(4.0);
        item.setQuantity(3);
        item.setUuid("1");
        
        
        ShoppingItem item2 = new ShoppingItem();
        item2.setName("Cat");
        item2.setPrice(6.0);
        item2.setQuantity(7);
        item2.setUuid("2");
        
        ShoppingItem item3 = new ShoppingItem();
        item3.setName("Elephant");
        item3.setPrice(8.0);
        item3.setQuantity(3);
        item3.setUuid("3");
        
        ShoppingItemCatalogRepository.save(item);
        ShoppingItemCatalogRepository.save(item2);
        ShoppingItemCatalogRepository.save(item3);
        
        
        
        

        LOG.debug("database initialised");
    }
    
//     @PostConstruct
//    public void initInvoiceDatabase() {
//        LOG.debug("initialising database with startup values");
//
//        // initialising admin and normal user if dont exist
//        
//        UUID uuidrandom = UUID.randomUUID();
//        String invoicenumberrandom = uuidrandom.toString();
//        
//        User defaultUser = new User();
//        defaultUser.setUsername(DEFAULT_USER_USERNAME);
//        defaultUser.setFirstName("default user");
//        defaultUser.setPassword(DEFAULT_USER_PASSWORD);
//        defaultUser.setUserRole(UserRole.CUSTOMER);
//        
//        long millis=System.currentTimeMillis();  
//        java.sql.Date date =new java.sql.Date(millis); 
//        
//        String itemName = "Dog";
//        
//        List<ShoppingItem> ShoppingItemList = ShoppingItemCatalogRepository.findByName(itemName);
//        
//        ShoppingItem shoppingItem = ShoppingItemList.get(0);
//        
//        shoppingCart.addItemToCart(shoppingItem);
//        
//        List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();
//            
//            
//           
//            
//        Double shoppingcartTotal = shoppingCart.getTotal();
//            
//            
//            
//            
//        Invoice invoice = new Invoice();
//                invoice.setAmountDue(shoppingcartTotal);
//                invoice.setPurchasedItems(shoppingCartItems);
//                invoice.setPurchaser(defaultUser);
//                invoice.setDateOfPurchase(date);
//                invoice.setInvoiceNumber(invoicenumberrandom);
//             
//        
//        
//        
//        InvoiceRepository.save(invoice);
//    
//
//        
//        
//        
//        
//
//        LOG.debug("database initialised");
//    }
}
