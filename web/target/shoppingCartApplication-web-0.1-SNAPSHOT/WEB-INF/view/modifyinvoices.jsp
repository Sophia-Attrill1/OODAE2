<%-- 
    Document   : modifyinvoices
    Created on : 5 Jan 2022, 23:50:01
    Author     : Sophia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.User"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItem"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.Invoice"%>
<c:set var = "selectedPage" value = "users" scope="request"/>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <H1>Item Details ${modifyInvoice.invoiceNumber} </H1>
        <!-- print error message if there is one -->
        <div style="color:red;">${errorMessage}</div>
        <div style="color:green;">${message}</div>

        <form action="./modifyinvoices" method="POST">
            <table class="table">
                <thead>
                </thead>

                <tbody>
                    <tr>
                        <td>Invoice ID</td>
                        <td>${modifyInvoice.id}</td>
                    </tr>
                    <tr>
                        <td>Invoice number</td>
                        <td>${modifyInvoice.invoiceNumber}</td>
                    </tr>
                    <tr>
                        <td>Date of purchase</td>
                        <td><input type="text" name="dateOfPurchase" value="${modifyInvoice.dateOfPurchase}" /></td>
                    </tr>
                    <tr>
                        <td>Amount Due</td>
                        <td><input type="text" name="amountDue" value="${modifyInvoice.amountDue}" /></td>
                    </tr>
                    <tr>
                        <td>Purchaser</td>
                        <td><input type="text" name="purchaser" value="${modifyInvoice.purchaser.username}" /></td>
                    </tr>
                    
                    <tr>
                        <td>Invoice Number</td>
                        <td><input type="text" name="invoiceNumber" value="${modifyInvoice.invoiceNumber}" /></td>
                    </tr>
                    

                </tbody>

            </table>

           
            <input type="hidden" name="username" value="${modifyInvoice.invoiceNumber}"/>
            <input type="hidden" name="action" value="updateinvoice"/>
            <button class="btn" type="submit" >Update Invoice ${modifyInvoice.invoiceNumber}</button>
        </form>
        
        <form action="./modifyinvoices" method="POST">
            <input type="hidden" name="name" value="${modifyInvoice.invoiceNumber}"/>
            <input type="hidden" name="action" value="deleteinvoice"/>
            <button class="btn" type="submit" >Delete ${modifyInvoice.invoiceNumber} </button>
        </form>

        <c:if test="${sessionUser.userRole =='ADMINISTRATOR'}">
            <BR>
            <form action="./invoices">
                <button class="btn" type="submit" >Return To invoices</button>
            </form> 
        </c:if> 
            
            

        </div>

    </main>

<jsp:include page="footer.jsp" />
