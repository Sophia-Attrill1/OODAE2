package org.solent.com504.oodd.cart.spring.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.model.service.ShoppingService;
import org.solent.com504.oodd.cart.web.WebObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.solent.com504.oodd.bank.client.impl.BankRestClientImpl;
import org.solent.com504.oodd.bank.model.dto.CreditCard;
import org.solent.com504.oodd.bank.model.dto.TransactionReplyMessage;
import org.solent.com504.oodd.bank.model.dto.BankTransaction;
import org.solent.com504.oodd.cart.web.daoShopProperties;
import org.solent.com504.oodd.cart.web.WebObjectFactory;
import org.solent.com504.oodd.cart.web.shoplogger.shoplogger;
import org.solent.com504.oodd.bank.client.impl.BankRestClientImpl;
import org.solent.com504.oodd.bank.model.dto.CreditCard;
import org.solent.com504.oodd.bank.model.dto.TransactionReplyMessage;
import org.solent.com504.oodd.bank.model.dto.BankTransaction;
import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.dao.impl.ShoppingItemCatalogRepository;
import org.solent.com504.oodd.cart.model.dto.Address;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class MVCController {

    final static Logger LOG = LogManager.getLogger(MVCController.class);

    // this could be done with an autowired bean
    //private ShoppingService shoppingService = WebObjectFactory.getShoppingService();
    @Autowired
    ShoppingService shoppingService = null;

    // note that scope is session in configuration
    // so the shopping cart is unique for each web session
    @Autowired
    ShoppingCart shoppingCart = null;

    @Autowired
    InvoiceRepository InvoiceRepository;

    @Autowired
    ShoppingItemCatalogRepository ShoppingItemCatalogRepository;

    private User getSessionUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            sessionUser = new User();
            sessionUser.setUsername("anonymous");
            sessionUser.setUserRole(UserRole.ANONYMOUS);
            session.setAttribute("sessionUser", sessionUser);
        }
        return sessionUser;
    }

    // this redirects calls to the root of our application to index.html
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewCart(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemUUID", required = false) String itemUuid,
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "home");

        String message = "";
        String errorMessage = "";

        // note that the shopping cart is is stored in the sessionUser's session
        // so there is one cart per sessionUser
//        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
//        if (shoppingCart == null) synchronized (this) {
//            if (shoppingCart == null) {
//                shoppingCart = WebObjectFactory.getNewShoppingCart();
//                session.setAttribute("shoppingCart", shoppingCart);
//            }
//        }
        if (action == null) {
            // do nothing but show page
        } else if ("addItemToCart".equals(action)) {
            List<ShoppingItem> ShoppingItemList = ShoppingItemCatalogRepository.findByName(itemName);
            ShoppingItem shoppingItem = ShoppingItemList.get(0);
            if (shoppingItem == null) {
                message = "cannot add unknown " + itemName + " to cart";
            } else {
                message = "adding " + itemName + " to cart price= " + shoppingItem.getPrice();

//                Integer quant = shoppingItem.getQuantity();
//                if (quant <= 0 ) {
//                    message = "This item has no more stock";
//                }
//                else {
                shoppingCart.addItemToCart(shoppingItem);
//                    Integer newquant = quant - 1;
//                    shoppingItem.setQuantity(newquant);
//                    ShoppingItemCatalogRepository.save(shoppingItem);
//                }
            }
        } else if ("removeItemFromCart".equals(action)) {
            message = "removed " + itemName + " from cart";
            shoppingCart.removeItemFromCart(itemUuid);
//            List<ShoppingItem> ShoppingItemList = ShoppingItemCatalogRepository.findByName(itemName);
//            ShoppingItem shoppingItem = ShoppingItemList.get(0);
//            Integer quant = shoppingItem.getQuantity();
//            Integer newquant = quant + 1;
//            shoppingItem.setQuantity(newquant);
//            ShoppingItemCatalogRepository.save(shoppingItem);

        } else {
            message = "unknown action=" + action;
        }

        List<ShoppingItem> availableItems = ShoppingItemCatalogRepository.findAll();

        List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();

        // populate model with values
        model.addAttribute("availableItems", availableItems);
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        return "home";

    }

    @RequestMapping(value = "/about", method = {RequestMethod.GET, RequestMethod.POST})
    public String aboutCart(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "about");
        return "about";
    }

    @RequestMapping(value = "/transaction", method = {RequestMethod.GET, RequestMethod.POST})
    public String transactionCart(
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemUUID", required = false) String itemUuid,
            Model model, HttpSession session, HttpServletRequest request) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "transaction");

        daoShopProperties daoShopProperties = WebObjectFactory.getPropertiesDaoFile();

        UUID uuidrandom = UUID.randomUUID();
        String invoicenumberrandom = uuidrandom.toString();

        String cardno = (String) request.getParameter("cardno");
        String cardfromname = (String) request.getParameter("cardfromname");
        String cardfromexpdate = (String) request.getParameter("cardfromexpdate");
        String cardfromcvv = (String) request.getParameter("cardfromcvv");
        String cardto = daoShopProperties.getProperty("admincardno");
        String cardtoname = daoShopProperties.getProperty("admincardname");
        String cardtoexpdate = daoShopProperties.getProperty("admincardexpdat");
        String cardtocvv = daoShopProperties.getProperty("admincardcvv");
        String url = daoShopProperties.getProperty("adminbankurl");

        String action = (String) request.getParameter("action");

        String message = "";
        String errormessage = "";
        try {
            BankRestClientImpl rester = new BankRestClientImpl(url);
            CreditCard fromCard = new CreditCard();
            CreditCard toCard = new CreditCard();

            Double shoppingcartTotal = shoppingCart.getTotal();
            List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

            // get the action
            if ("submitdetails".equals(action)) {
                // set numbers
                fromCard.setCardnumber(cardno);
                fromCard.setName(cardfromname);
                fromCard.setEndDate(cardfromexpdate);
                fromCard.setCvv(cardfromcvv);

                toCard.setCardnumber(cardto);
                toCard.setName(cardtoname);
                toCard.setEndDate(cardtoexpdate);
                toCard.setCvv(cardtocvv);

                long millis = System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);

                UserRole userrole = sessionUser.getUserRole();
                if (UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {

                    message = "user must be logged in to generate an invoice";

                } else {

                    Invoice invoice = new Invoice();

                    invoice.setAmountDue(shoppingcartTotal);
                    invoice.setPurchasedItems(shoppingCartItems);
                    invoice.setPurchaser(sessionUser);
                    invoice.setDateOfPurchase(date);
                    invoice.setInvoiceNumber(invoicenumberrandom);
                    invoice = InvoiceRepository.save(invoice);

                    model.addAttribute("invoice", invoice);

                }

                TransactionReplyMessage query = rester.transferMoney(fromCard, toCard, shoppingcartTotal);
                String transactionMessage = "";
                TransactionReplyMessage transactionReplyMessage = new TransactionReplyMessage();
                transactionMessage = transactionReplyMessage.toString();
                errormessage = query.getMessage();

                if (errormessage == null) {
                    String logmsg = "Transaction complete with card" + " " + cardno + " " + "for the amount of" + " " + shoppingcartTotal;
                    shoplogger.Logger(logmsg);
                    message = "transaction sending";
                } else {
                    String logmsg = "Transaction was unsuccessful with card" + " " + cardno + " " + "for the amount of" + " " + shoppingcartTotal + "." + "Error message: " + errormessage;
                    shoplogger.Logger(logmsg);
                    message = "transaction failed, Error:" + errormessage;
                }
            }
            
            

            List<ShoppingItem> availableItems = ShoppingItemCatalogRepository.findAll();
            
            model.addAttribute("availableItems", availableItems);
            model.addAttribute("shoppingCartItems", shoppingCartItems);
            model.addAttribute("shoppingcartTotal", shoppingcartTotal);

        } catch (Exception e) {
            message = "Error, could not complete transaction";
        }

        // populate model with values
        
        model.addAttribute("message", message);
        model.addAttribute("errormessage", errormessage);
        model.addAttribute("cardto", cardto);

        return "transaction";
    }

    @RequestMapping(value = "/createitem", method = {RequestMethod.GET, RequestMethod.POST})
    public String createitemCart(String action,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "price", required = false) Double price,
            Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "createitem");

        String message = "";
        String errorMessage = "";

        LOG.debug("register new item=" + name);

        if (name == null || name.trim().isEmpty()) {
            errorMessage = "you must enter a name for the item";
            model.addAttribute("errorMessage", errorMessage);
            return "createitem";
        }

        List<ShoppingItem> shoppingItemList = ShoppingItemCatalogRepository.findByName(name);

        if ("createNewItem".equals(action)) {
            if (!shoppingItemList.isEmpty()) {
                errorMessage = "trying to create an item with a name which already exists :" + name;
                LOG.warn(errorMessage);
                model.addAttribute("errorMessage", errorMessage);
                return "createitem";

            }

//            if (!shoppingItemListUuid.isEmpty()) {
//                errorMessage = "trying to create an item with a uuid which already exists :" + uuid;
//                LOG.warn(errorMessage);
//                model.addAttribute("errorMessage", errorMessage);
//                return "createitem";
//                
//            }
        }

        UUID uuidrandom = UUID.randomUUID();
        String uuid = uuidrandom.toString();

        ShoppingItem newitem = new ShoppingItem();
        newitem.setName(name);
        newitem.setQuantity(quantity);
        newitem.setPrice(price);
        newitem.setUuid(uuid);
        newitem = ShoppingItemCatalogRepository.save(newitem);

        LOG.debug("createNewItem created new item item=" + newitem);
        message = "enter user details";
        model.addAttribute("newitem", newitem);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        //      }

        return "createitem";
    }

    @RequestMapping(value = {"/catalog"}, method = RequestMethod.GET)
    @Transactional
    public String catalog(Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        
        
        try{

            if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
                errorMessage = "you must be an administrator to access the catalog information";
                return "home";
            }

            List<ShoppingItem> shoppingItemList = ShoppingItemCatalogRepository.findAll();

            model.addAttribute("shoppingItemListSize", shoppingItemList.size());
            model.addAttribute("shoppingItemList", shoppingItemList);
            model.addAttribute("selectedPage", "catalog");
        
        }catch(Exception e){
            errorMessage = "error connecting to the page";
        }
        return "catalog";
    }

    @RequestMapping(value = "/contact", method = {RequestMethod.GET, RequestMethod.POST})
    public String contactCart(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "contact");
        return "contact";
    }

    @RequestMapping(value = "/setproperties", method = {RequestMethod.GET, RequestMethod.POST})
    public String setpropertiesCart(Model model, HttpSession session, HttpServletRequest request) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "setproperties");

        daoShopProperties daoShopProperties = WebObjectFactory.getPropertiesDaoFile();
        String cardto = daoShopProperties.getProperty("admincardno");
        String cardtoname = daoShopProperties.getProperty("admincardname");
        String cardtoexpdate = daoShopProperties.getProperty("admincardexpdate");
        String cardtocvv = daoShopProperties.getProperty("admincardcvv");
        String url = daoShopProperties.getProperty("adminbankurl");
        String action = (String) request.getParameter("action");

        String message = "";
        {
// rest client

            CreditCard toCard = new CreditCard();
            CreditCard fromCard = new CreditCard();
            // get the action
            if ("submitadmindetails".equals(action)) {

                cardto = (String) request.getParameter("cardto");
                cardtoname = (String) request.getParameter("cardtoname");
                cardtoexpdate = (String) request.getParameter("cardtoexpdate");
                cardtocvv = (String) request.getParameter("cardtocvv");

                daoShopProperties.setProperty("admincardno", cardto);
                daoShopProperties.setProperty("admincardname", cardtoname);
                daoShopProperties.setProperty("admincardexpdate", cardtoexpdate);
                daoShopProperties.setProperty("admincardcvv", cardtocvv);

                // set numbers
                toCard.setCardnumber(cardto);
                toCard.setName(cardtoname);
                toCard.setEndDate(cardtoexpdate);
                toCard.setCvv(cardtocvv);

                // do the transfer
            }

            if ("submiturl".equals(action)) {
                message = "url for the bank is set";
                url = (String) request.getParameter("url");
                daoShopProperties.setProperty("adminbankurl", url);

            }

        }
        model.addAttribute("cardto", cardto);
        model.addAttribute("cardtoname", cardtoname);
        model.addAttribute("cardtoexpdate", cardtoexpdate);
        model.addAttribute("cardtocvv", cardtocvv);
        model.addAttribute("url", url);

        model.addAttribute("message", message);

        return "setproperties";
    }

    @RequestMapping(value = {"/modifyitem"}, method = RequestMethod.GET)
    public String modifyitem(
            @RequestParam(value = "name", required = true) String name,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        model.addAttribute("selectedPage", "modifyitem");

        LOG.debug("get modifyitem called for item name=" + name);

        // check secure access to modifyUser profile
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            errorMessage = "you must be logged in to access user information";
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        }

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            // if not an administrator you can only access your own account info

            errorMessage = "security non admin viewModifyUser called for non administrator which is not the logged in user =" + sessionUser.getUsername();
            LOG.warn(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return ("home");

        }

        List<ShoppingItem> ShoppingItemList = ShoppingItemCatalogRepository.findByName(name);
        if (ShoppingItemList.isEmpty()) {
            LOG.error("viewModifyUser called for unknown item=" + name);
            return ("home");
        }

        ShoppingItem modifyItem = ShoppingItemList.get(0);
        model.addAttribute("modifyItem", modifyItem);

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        return "modifyitem";
    }

    @RequestMapping(value = {"/modifyitem"}, method = RequestMethod.POST)
    public String updateitem(
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "price", required = false) Double price,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        LOG.debug("post updateItem called for item=" + name);

        // security check if party is allowed to access or modify this party
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            errorMessage = "you must be logged in to access items information";
            model.addAttribute("errorMessage", errorMessage);
            return "home";
        }

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            if (!sessionUser.getUsername().equals(name)) {
                errorMessage = "security viewModifyUser called for non admin username which is not the logged in user =" + sessionUser.getUsername();
                model.addAttribute("errorMessage", errorMessage);
                LOG.warn(errorMessage);
                return ("home");
            }
        }

        List<ShoppingItem> ShoppingItemList = ShoppingItemCatalogRepository.findByName(name);
        if (ShoppingItemList.isEmpty()) {
            errorMessage = "update user called for unknown username:" + name;
            LOG.warn(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return ("home");
        }

        ShoppingItem modifyItem = ShoppingItemList.get(0);

        if ("updateitem".equals(action)) {

            modifyItem.setName(name);
            modifyItem.setQuantity(quantity);
            modifyItem.setPrice(price);

            modifyItem = ShoppingItemCatalogRepository.save(modifyItem);

            model.addAttribute("modifyItem", modifyItem);

        }

        // add message if there are any 
        
        
        try{

        if ("deleteitem".equals(action)) {

            ShoppingItemCatalogRepository.delete(modifyItem);

            ShoppingItemList.remove(0);
            ShoppingItemList.remove(modifyItem);

            model.addAttribute("modifyItem", modifyItem);
            return "modifyitem";

        }

        return "modifyitem";
        
        }
        catch(Exception e){
            errorMessage = "Cannot delete an item in an invoice";
        }
        
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("message", "User " + modifyItem.getName() + " updated successfully");

        model.addAttribute("selectedPage", "modifyitem");
        
        return "modifyitem";
    }

    @RequestMapping(value = "/invoices", method = {RequestMethod.GET, RequestMethod.POST})
    public String invoicesCart(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        List<Invoice> invoiceList = InvoiceRepository.findAll();
        // used to set tab selected
        model.addAttribute("invoiceList", invoiceList);
        model.addAttribute("invoiceListSize", invoiceList.size());
        model.addAttribute("selectedPage", "invoices");
        return "invoices";
    }

//     @RequestMapping(value = {"/modifyinvoices"}, method = RequestMethod.GET)
//    public String modifyinvoice(
//            @RequestParam(value = "id", required = true) String id,
//            Model model,
//            HttpSession session) {
//        String message = "";
//        String errorMessage = "";
//
//        model.addAttribute("selectedPage", "modifyitem");
//
//        LOG.debug("get modifyitem called for invoice id=" + id);
//
//        // check secure access to modifyUser profile
//        User sessionUser = getSessionUser(session);
//        model.addAttribute("sessionUser", sessionUser);
//
//        if (UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
//            errorMessage = "you must be logged in to access user information";
//            model.addAttribute("errorMessage", errorMessage);
//            return "home";
//        }
//
//        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
//            // if not an administrator you can only access your own account info
//            
//                errorMessage = "security non admin viewModifyUser called for non administrator which is not the logged in user =" + sessionUser.getUsername();
//                LOG.warn(errorMessage);
//                model.addAttribute("errorMessage", errorMessage);
//                return ("home");
//            
//        }
//
//        List<Invoice> InvoiceList = InvoiceRepository.findById(id);
//        if (InvoiceList.isEmpty()) {
//            LOG.error("modifyinvoices called for unknown invoice=" + id);
//            return ("home");
//        }
//
//        Invoice modifyInvoice = InvoiceList.get(0);
//        model.addAttribute("modifyInvoice", modifyInvoice);
//
//        model.addAttribute("message", message);
//        model.addAttribute("errorMessage", errorMessage);
//        return "modifyinvoices";
//    }
//
//    @RequestMapping(value = {"/modifyinvoices"}, method = RequestMethod.POST)
//    public String updateinvoice(
//            @RequestParam(value = "action", required = false) String action,
//            @RequestParam(value = "id", required = true) String id,
//            @RequestParam(value = "dateOfPurchase", required = false) Date dateOfPurchase,
//            @RequestParam(value = "amountDue", required = false) Double amountDue,
//            @RequestParam(value = "purchaser", required = false) User purchaser,
//            @RequestParam(value = "invoiceNumber", required = false) String invoiceNumber,
//            Model model,
//            HttpSession session) {
//        String message = "";
//        String errorMessage = "";
//
//        LOG.debug("post updateInvoice called for invoice=" + id);
//
//        // security check if party is allowed to access or modify this party
//        User sessionUser = getSessionUser(session);
//        model.addAttribute("sessionUser", sessionUser);
//
//        if (UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
//            errorMessage = "you must be logged in to access items information";
//            model.addAttribute("errorMessage", errorMessage);
//            return "home";
//        }
//
//        
//
//        List<Invoice> InvoiceList = InvoiceRepository.findById(id);
//        
//        Invoice modifyInvoice = InvoiceList.get(0);
//        
//        if ("updateinvoice".equals(action)) {
//        
//            modifyInvoice.setAmountDue(amountDue);
//            modifyInvoice.setDateOfPurchase(dateOfPurchase);
//            modifyInvoice.setInvoiceNumber(invoiceNumber);
//            modifyInvoice.setPurchaser(purchaser);
//
//            modifyInvoice = InvoiceRepository.save(modifyInvoice);
//
//
//
//            model.addAttribute("modifyInvoice", modifyInvoice);
//        
//        }
//        
//        
//
//        // add message if there are any 
//        model.addAttribute("errorMessage", errorMessage);
//        model.addAttribute("message", "User " + modifyInvoice.getInvoiceNumber()+ " updated successfully");
//
//        model.addAttribute("selectedPage", "modifyitem");
//        
//        if ("deleteitem".equals(action)) {
//                
//                
//                InvoiceRepository.delete(modifyInvoice);
//                
//                InvoiceList.remove(0);
//                InvoiceList.remove(modifyInvoice);
//                
//                model.addAttribute("modifyInvoice", modifyInvoice);
//               return "modifyInvoice";
//                
//        }
//
//        return "modifyinvoices";
//    }

    /*
     * Default exception handler, catches all exceptions, redirects to friendly
     * error page. Does not catch request mapping errors
     */
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model, HttpServletRequest request) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        final String strStackTrace = sw.toString(); // stack trace as a string
        String urlStr = "not defined";
        if (request != null) {
            StringBuffer url = request.getRequestURL();
            urlStr = url.toString();
        }
        model.addAttribute("requestUrl", urlStr);
        model.addAttribute("strStackTrace", strStackTrace);
        model.addAttribute("exception", e);
        //logger.error(strStackTrace); // send to logger first
        return "error"; // default friendly exception message for sessionUser
    }

}
