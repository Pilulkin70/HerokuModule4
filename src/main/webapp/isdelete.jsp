<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
<head>
    <title>Item is delete</title>
    <style>
       td {
        padding: 5px;
       }
    </style>
</head>
<body>
<section>
    <jsp:useBean id="idDeletedItem" type="String" scope="request"/>
    <jsp:useBean id="homePath" type="String" scope="request"/>
    <h1 align="center" style="color:#ff0000">Item with ID=${idDeletedItem} is delete!</h1>
    <div style="text-align:center">
        <INPUT TYPE="button" VALUE="Back" style="width:120px;height:30px" onClick="window.location='${homePath}/list'">
    </div>
    <p></p>
    <div style="text-align:center">
        <INPUT TYPE="button" VALUE="Home" style="width:120px;height:30px" onClick="window.location='${homePath}/index.html'">
    </div>
</section>
</body>
</html>


