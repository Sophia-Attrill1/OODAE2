<%-- 
    Document   : createitem
    Created on : 5 Jan 2022, 03:06:32
    Author     : Sophia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
// request set in controller
//    request.setAttribute("selectedPage","contact");
%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Create a New Item</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>


    
    <form action="./createitem" method="POST">
        <input type="hidden" name="action" value="createNewItem">
        <p>Name <input type="text" name="name" ></input></p><BR>
        <p>Quantity <input type="number" name="quantity" ></input></p>
        <p>Price <input type="number" name="price" ></input></p>
        <p><button type="submit" >Create New Item</button></p>
    </form> 

</main>


<jsp:include page="footer.jsp" />
