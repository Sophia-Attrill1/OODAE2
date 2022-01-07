<%-- 
    Document   : modifyitem
    Created on : 5 Jan 2022, 04:24:17
    Author     : Sophia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.User"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItem"%>
<c:set var = "selectedPage" value = "users" scope="request"/>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <H1>Item Details ${modifyItem.name} </H1>
        <!-- print error message if there is one -->
        <div style="color:red;">${errorMessage}</div>
        <div style="color:green;">${message}</div>

        <form action="./modifyitem" method="POST">
            <table class="table">
                <thead>
                </thead>

                <tbody>
                    <tr>
                        <td>Item ID</td>
                        <td>${modifyItem.id}</td>
                    </tr>
                    <tr>
                        <td>username</td>
                        <td>${modifyItem.name}</td>
                    </tr>
                    <tr>
                        <td>Name</td>
                        <td><input type="text" name="name" value="${modifyItem.name}" /></td>
                    </tr>
                    <tr>
                        <td>Quantity</td>
                        <td><input type="text" name="quantity" value="${modifyItem.quantity}" /></td>
                    </tr>
                    <tr>
                        <td>Price</td>
                        <td><input type="text" name="price" value="${modifyItem.price}" /></td>
                    </tr>
                    

                </tbody>

            </table>

           
            <input type="hidden" name="username" value="${modifyItem.name}"/>
            <input type="hidden" name="action" value="updateitem"/>
            <button class="btn" type="submit" >Update Item ${modifyItem.name}</button>
        </form>
        
        <form action="./modifyitem" method="POST">
            <input type="hidden" name="name" value="${modifyItem.name}"/>
            <input type="hidden" name="action" value="deleteitem"/>
            <button class="btn" type="submit" >Delete ${modifyItem.name} </button>
        </form>

        <c:if test="${sessionUser.userRole =='ADMINISTRATOR'}">
            <BR>
            <form action="./catalog">
                <button class="btn" type="submit" >Return To Catalog</button>
            </form> 
        </c:if> 
            
            

        </div>

    </main>

<jsp:include page="footer.jsp" />
