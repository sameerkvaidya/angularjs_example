/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usac.psd;

import com.couchbase.client.CouchbaseClient;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;

/**
     *
 * @author sameerkvaidya
 */
public class ConnectionManager {

  /**
   * Holds the connected Couchbase instance.
   */
  private static CouchbaseClient client;

  /**
   * The Logger to use.
   */
  private static final Logger logger = Logger.getLogger(
    ConnectionManager.class.getName());
  


  /**
   * Returns the current CouchbaseClient object.
   *
   * @return the current Couchbase connection.
   */
  public static CouchbaseClient getInstance() {
      if(client == null){
          ArrayList<URI> nodes = new ArrayList<URI>();
            
          nodes.add(URI.create("http://127.0.0.1:8091/pools"));
          
          try {
          
              client = new CouchbaseClient(nodes, "default", "");
            
              if(client.get("ID") == null){
                  client.set("ID", "1");
              }
          } catch (IOException ex) {
          
              logger.log(Level.SEVERE, ex.getMessage());
            
          }
      }
    return client;
  }

}
