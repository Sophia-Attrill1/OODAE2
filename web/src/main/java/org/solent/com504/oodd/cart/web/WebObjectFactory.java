/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.web;



/**
 *
 * @author cgallen
 */
import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebObjectFactory {

    final static Logger LOG = LogManager.getLogger(WebObjectFactory.class);

    private static daoShopProperties propertiesDaoFile = null;

    public static daoShopProperties getPropertiesDaoFile() {
        if (propertiesDaoFile == null) {
            synchronized (WebObjectFactory.class) {
                if (propertiesDaoFile == null) {
                    // creates a single instance of the dao
                    String TEMP_DIR = System.getProperty("java.io.tmpdir");
                    File propertiesFile = new File(TEMP_DIR + "/shopdetails.properties");
                    LOG.debug("using system temp directory: " + TEMP_DIR);
                    LOG.debug("using application properties file : " + propertiesFile.getAbsolutePath());
                    propertiesDaoFile = new daoShopProperties(propertiesFile.getAbsolutePath());
                }
            }
        }
        return propertiesDaoFile;
    }

}
