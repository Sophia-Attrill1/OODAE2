<%-- 
    Document   : setproperties
    Created on : 3 Jan 2022, 15:44:38
    Author     : Sophia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
<main role="main" class="container">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
            <link rel="stylesheet" href="./resources/css/numpad-dark.css"/>
            <script src="./resources/js/numpad.js"></script>
        </head>
        <body>
            <h1>Admin Details</h1>

            <form action="./setproperties" method="post" id="urlform">
                URL <input type="text" name="url" value="${url}"/> <br>

                <input type="hidden" name="action" value="submiturl">
                <button type="submit" id="submit" >Submit URL</button>

                <p id="erroroutput"></p>

            </form>

            <form action="./setproperties" method="post" id="adminform" onsubmit="return validate()">

                To CreditCard: <input type="text" id="cardno" name="cardto" maxlength="16" value="${cardto}"/> <br>
                Name on Card: <input type="text" name="cardtoname" value="${cardtoname}"/> <br>
                Expiry date: <input type="text" name="cardtoexpdate" value="${cardtoexpdate}"/> <br>
                CVV code: <input type="text" name="cardtocvv" maxlength="3" value="${cardtocvv}"/> <br>




                <input type="hidden" name="action" value="submitadmindetails">
                <button type="submit" id="submit" >Submit</button>

                <p id="erroroutput"></p>
            </form>

        
    </body>
</html>
