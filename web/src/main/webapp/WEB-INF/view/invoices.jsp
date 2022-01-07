<%-- 
    Document   : invoices
    Created on : 5 Jan 2022, 23:49:52
    Author     : Sophia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItem"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.UserRole"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.Invoice"%>
<c:set var = "selectedPage" value = "admin" scope="request"/>
<jsp:include page="header.jsp" />
<!-- start of users.jsp selectedPage=${selectedPage}-->

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <h1>Manage Items</h1>
        <p>showing ${invoiceListSize} Invoices: </p>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Date of purchase</th>
                    <th scope="col">Amount due</th>
                    <th scope="col">Purchaser</th>
                    <th scope="col">Invoice Number</th>
                    
                    <th></th>
                </tr>
                
                <c:forEach var="invoice" items="${invoiceList}">
                    <tr>
                        <td>${invoice.id}</td>
                        <td>${invoice.dateOfPurchase}</td>
                        <td>${invoice.amountDue}</td>
                        <td>${invoice.purchaser.username}</td>
                        <td>${invoice.invoiceNumber}</td>
                        
                        
                      
                    </tr>
                </c:forEach>
            </thead>
            <tbody>
                

            </tbody>
        </table>
     
    </div>
</main>

<jsp:include page="footer.jsp" />