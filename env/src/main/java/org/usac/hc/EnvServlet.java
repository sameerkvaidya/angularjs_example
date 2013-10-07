/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usac.hc;

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
@WebServlet(name = "EnvServlet", urlPatterns = {"/serv/*"})
public class EnvServlet extends HttpServlet {


    

  final CouchbaseClient client = ConnectionManager.getInstance();
  final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("path :"+request.getPathInfo());
    if(request.getPathInfo() == null) {
        handleShowAll(request, response);
      } else if(request.getPathInfo().startsWith("/edit")) {
        handleEdit(request, response);
      } else if(request.getPathInfo().startsWith("/add")) {
        handleAdd(request, response);
      } else if(request.getPathInfo().startsWith("/all")) {
        handleShowAll(request, response);
      }else if(request.getPathInfo().startsWith("/save")) {
        handleSave(request, response);
      }
    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "servlet to handles env actions";
    }// </editor-fold>
    
    
     public void handleShowAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
     
        try {
            View view = client.getView("dev_view", "by_name");
            Query query = new Query();
            query.setIncludeDocs(false).setLimit(20);
            
            query.setStale(Stale.FALSE);
            ViewResponse result = client.query(view, query);
               //query.
            ArrayList<HashMap<String, String>> shortEnvs =
            new ArrayList<HashMap<String, String>>();

            for (ViewRow row : result) {

              HashMap<String, String> shortEnv = new HashMap<String, String>();
              shortEnv.put("id", row.getId());
              shortEnv.put("name", row.getKey());
              shortEnv.put("user", row.getValue());

              shortEnvs.add(shortEnv);

            }

            request.setAttribute("shortEnvs", shortEnvs);

            request.getRequestDispatcher("/showall.jsp")
                  .forward(request, response);
      } catch (InvalidViewException e) {
        e.printStackTrace();
      }
    }
    
    
    public void handleEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String[] param = request.getPathInfo().split("/");
        HashMap<String, Object> env = null;
        String doc = null;
        System.out.println("In Edit "+param[2]);
                
        if(param.length > 2 && param[2].startsWith("ENV::")){
            CASValue casValue = client.gets(param[2]);
            doc = (String) casValue.getValue();
            long cas = casValue.getCas();
            request.setAttribute("DOC_ID", param[2]);
            request.setAttribute("title", "Update the item");
            request.setAttribute("cas", cas);
            
        }
       
        if(doc != null) {
            env = gson.fromJson(doc, HashMap.class);
            request.setAttribute("env", env);
        }
        
        request.getRequestDispatcher("/update.jsp")
            .forward(request, response);
        
    }
    /*
     * using add for update and new 
     * will be called from post
     */
    public void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("IN handleAdd..");

        request.setAttribute("title", "Create new item");
        request.getRequestDispatcher("/update.jsp")
            .forward(request, response);
    }
    
        
    public void handleSave(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        System.out.println("IN handleSave..");
        String docId = null;
        if(request.getParameter("DOC_ID") != null){
            docId = request.getParameter("DOC_ID");
        }
        long cas = -1;
        if (request.getParameter("cas") != null && request.getParameter("cas")!= "") {
            cas = Long.parseLong(request.getParameter("cas"));
        }
        
        HashMap<String, Object> env = new HashMap<String, Object>();
        Enumeration<String> params = request.getParameterNames();
        boolean data = false;
        while(params.hasMoreElements()) {
          data = true;  
          String key = params.nextElement();
          
          Object value = request.getParameter(key);
          
          System.out.println("Parameters = "+key+ " : "+value);
          if(!key.equalsIgnoreCase("CAS") && !key.equalsIgnoreCase("MSG"))
            env.put(key.substring(4), value);
        }
        
        env.put("type", "env");
        env.put("updated", new Date().toString());
        if(data){
            if(docId == null || docId.isEmpty()){
                System.out.println("New Entry...");
                docId = "ENV::"+client.incr("ID", 1);
                System.out.println("New Entry..."+docId);
            }
            //client.set(docId, 0, gson.toJson(env));
            if(cas > 0){
                CASResponse casResponse = client.cas(docId, cas, gson.toJson(env));
                if (casResponse.equals(CASResponse.EXISTS) ) {
                    System.out.println("Errrrrrrrrrrrr Data changed!!!!");
                    request.setAttribute("msg_err","Data is modified by another user.");
                }else{
                    request.setAttribute("msg","Data is successfully saved.");
                }
            }else{
                client.set(docId, 0, gson.toJson(env));
                request.setAttribute("msg","Data is successfully saved.");   
                
            }            
            
            handleShowAll(request, response);
            
        } 
    }
}
