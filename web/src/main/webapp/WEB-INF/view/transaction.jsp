<%-- 
    Document   : transaction
    Created on : 29 Dec 2021, 17:38:46
    Author     : Sophia
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
// request set in controller


%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    
    <div style="color:red;">${errorMessage}</div>
        <div style="color:green;">${message}</div>

    
    <h1>Enter card details</h1>
    <table class="table">



        
        <tr>
            <td>TOTAL</td>
            <td>${shoppingcartTotal}</td>
            
           

        <c:forEach var="item" items="${shoppingCartItems}">

            
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>${item.quantity}</td>
                
            
        </c:forEach>
        
    </table>
    
    <form action="./transaction" method="post" id="transactionform" onsubmit="return validate()">
                Your Card Number: <input type="text" id="cardno" name="cardno" maxlength="16"/> <br>
                Name on Card: <input type="text" name="cardfromname"/> <br>
                Expiry date: <input type="text" name="cardfromexpdate"/> <br>
                CVV code: <input type="text" name="cardfromcvv" maxlength="3"/> <br> <br>
                
              



                <input type="hidden" name="action" value="submitdetails">
                <button type="submit" id="submit" >Submit</button>

              
            </form>
</main>


<jsp:include page="footer.jsp" />