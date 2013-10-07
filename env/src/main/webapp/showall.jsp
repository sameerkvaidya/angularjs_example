<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:layout>
    <jsp:body>
    <h3>All Environments in HC</h3>
        <c:if test="${msg != null}">
            <div class="alert alert-info text-info">${msg}</div>
        </c:if>
        <c:if test="${msg_err != null}">
            <div class="alert alert-danger text-error">${msg_err}</div>
        </c:if>

            <table id="beer-table" class="table table-striped">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>User</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${shortEnvs}" var="shortEnv">
                        <tr>
                            <td>${shortEnv.name}</td>
                            <td>${shortEnv.user}</td>
                            <td><a class="btn btn-small btn-warning"
                                   href="/env/serv/edit/${shortEnv.id}">Edit</a>
                                
                            </td>
                        </tr>
                      </c:forEach>
                </tbody>
            </table>
    </jsp:body>
</t:layout>