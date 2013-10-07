<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:layout>
    <jsp:body>
        <h3>${title}</h3>

               
        <c:if test="${msg != null}">
            <div class="alert alert-danger text-error">${msg}</div>
        </c:if>
        
            
        <form method="post" action="/env/form/save">
            <fieldset>
              <legend>General Info</legend>
              <div class="span12">
                <div class="span6">
                  <label>Name</label>
                  <input type="text" name="name" placeholder="The name of the env." value="${form.name}">

                  <label>Contact</label>
                  <input type="text" name="contact" placeholder="email" value="${form.email}">
                </div>
                <div class="span6">
                  <label>Phone</label>
                  <input type="text" name="phone" placeholder="202-772-2000" value="${form.phone}">

                  <label>Title</label>
                  <input type="text" name="title" placeholder="Developer" value="${form.position}">
                </div>
                
                <div class="span6">
                  <label>Accept</label>
                  <input type="checkbox" name="tnc" value="${form.tnc}">

                  
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