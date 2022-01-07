<%-- 
    Document   : catalog
    Created on : 5 Jan 2022, 03:03:01
    Author     : Sophia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItem"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.UserRole"%>
<c:set var = "selectedPage" value = "admin" scope="request"/>
<jsp:include page="header.jsp" />
<!-- start of users.jsp selectedPage=${selectedPage}-->

<!-- Begin page content -->
<main role="main" class="container">
    
    <div style="color:red;">${errorMessage}</div>
        <div style="color:green;">${message}</div>

    <div>
        <h1>Manage Items</h1>
        <p>showing ${shoppingItemListSize} Items: </p>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Price</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${shoppingItemList}">
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>${item.quantity}</td>
                        <td>${item.price}</td>
                        
                      <td>
                            <form action="./modifyitem" method="GET">
                                <input type="hidden" name="name" value="${item.name}">
                                <button class="btn" type="submit" >Modify Item</button>
                            </form> 
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
        <form action="./createitem" method="GET">
            <button class="btn" type="submit" >Add item</button>
        </form> 
    </div>
</main>

<jsp:include page="footer.jsp" />