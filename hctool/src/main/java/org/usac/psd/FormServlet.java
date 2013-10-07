/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usac.psd;

//import com.couchbase.client.CouchbaseClient;
//import com.google.gson.Gson;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.InvalidViewException;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.Stale;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;



/**
 *
 * @author sameerkvaidya
 */
@WebServlet(name = "FormServlet", urlPatterns = {"/form/*"})
public class FormServlet extends HttpServlet {


    

  final CouchbaseClient client = ConnectionManager.getInstance();
  final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("path :"+request.getPathInfo());
//    if(request.getPathInfo() == null) {
//        handleRetrieve(request, response);
//      } else if(request.getPathInfo().startsWith("/create")) {
//        handleCreate(request, response);
//      } else if(request.getPathInfo().startsWith("/retrieve")) {
//        handleRetrieve(request, response);
//      } else if(request.getPathInfo().startsWith("/update")) {
//        handleUpdate(request, response);
//      }else if(request.getPathInfo().startsWith("/delete")) {
//        handleDelete(request, response);
//      }
//  
        handleRetrieve(request, response);
        
    }

        
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        handleCreate(request, response);
        
        
    }

    //env/form/retrieve
    private void handleRetrieve(HttpServletRequest request, HttpServletResponse response) {
         
        System.out.println("In retrieve...."+request.getPathInfo());
        response.setContentType("application/json");
        
        if(request.getPathInfo()!=null && request.getPathInfo().contains("ENV::")){
            String id = request.getPathInfo().replace("/", "");
            try {
                response.getWriter().write(client.get(id).toString());
            } catch (IOException ex) {
                Logger.getLogger(FormServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
        
            StringBuffer json = new StringBuffer();

            try {
                View view = client.getView("dev_view", "by_name");
                Query query = new Query();
                query.setIncludeDocs(false).setLimit(20);

                query.setStale(Stale.FALSE);
                ViewResponse result = client.query(view, query);
                   //query.
                ArrayList<HashMap<String, String>> shortEnvs =
                new ArrayList<HashMap<String, String>>();
                boolean first = true;
                for (ViewRow row : result) {

                    if(first){
                        json.append("[");
                    }else{
                        json.append(",");
                    }
                    first = false;
                    json.append("{");  
                    json.append("\"id\":\""+ row.getId()+"\",");
                    json.append("\"name\":\""+ row.getKey()+"\",");
                    json.append("\"user\":\""+ row.getValue()+"\"}");

                }

                json.append("]");

                response.getWriter().write(json.toString());

          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        
          
      
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response) {
      try {
          
          String data = request.getReader().readLine();
          
          Env e = gson.fromJson(data, Env.class);
          e.setCreated(new Date());
          e.setUpdated(new Date());
          e.setType("env");
          e.setId("ENV::"+client.incr("ID", 1));
          
          client.add(e.getId(), gson.toJson(e));
          
      } catch (IOException ex) {
          Logger.getLogger(FormServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
        
        
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Delete item: "+req.getPathInfo());
        String id = req.getPathInfo().replace("/","");
        if(id.startsWith("ENV::")){
        System.out.println("Delete item: "+id);
            client.delete(id);
            
        }else{
        
            System.out.println("Id Not received, Delete Failed!!!");
        }
        
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        System.out.println("IN Do PUT");
    
        String data = req.getReader().readLine();
          
        System.out.println("Data: "+data);
        
        Env e = gson.fromJson(data, Env.class);
        e.setUpdated(new Date());

        client.set(e.getId(), gson.toJson(e));
          
    }

    
}
class Env{
    private String id, user, name, desc, type, url;
    private Date created, updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    

}