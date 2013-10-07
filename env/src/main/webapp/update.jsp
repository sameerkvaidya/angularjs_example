<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:layout>
    <jsp:body>
        <h3>${title}</h3>

               
        <c:if test="${msg != null}">
            <div class="alert alert-danger text-error">${msg}</div>
        </c:if>
        
            
        <form method="post" action="/env/serv/save">
            <fieldset>
              <legend>General Info</legend>
              <div class="span12">
                <div class="span6">
                  <label>Name</label>
                  <input type="text" name="env_name" placeholder="The name of the env." value="${env.name}">

                  <label>Description</label>
                  <input type="text" name="env_description" placeholder="A short description." value="${env.description}">
                </div>
                <div class="span6">
                  <label>URL</label>
                  <input type="text" name="env_url" placeholder="url http://google.com" value="${env.url}">

                  <label>User</label>
                  <input type="text" name="env_user" placeholder="sameer/david" value="${env.user}">
                </div>
                
                <div class="span6">
                  <label>Project</label>
                  <input type="text" name="env_proj" placeholder="HC or CAR" value="${env.proj}">

                  <label>Jboss</label>
                  <input type="text" name="env_serv" placeholder="EAP 4.3 or 6.1" value="${env.serv}">
                </div>
                
              </div>
            </fieldset>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Save changes</button>
            </div>
                <input type="hidden" name="DOC_ID" value="${DOC_ID}">
                <input type="hidden" name="cas" value="${cas}">
                <input type="hidden" name="msg" value="${msg}">
        </form>
    </jsp:body>
</t:layout>