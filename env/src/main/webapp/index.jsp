<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:layout>
    <jsp:body>
    <h3>All Environments in HC</h3>



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
                                   href="/beers/edit/${shortEnv.id}">Edit</a>
                                
                            </td>
                        </tr>
                      </c:forEach>
                </tbody>
            </table>
    </jsp:body>
</t:layout>