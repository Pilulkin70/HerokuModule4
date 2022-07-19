<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>Item info</title>
    <style>
       td {
        padding: 5px;
       }
    </style>
</head>
<body>
<section>
    <h1 style="color:#0033ff">Full information about detail</h1>
    <jsp:useBean id="detailInfo" type="java.util.ArrayList" scope="request"/>
    <table border="1" cellpadding="8">
        <tr>
            <th><i> ID </i></th>
            <th><i> Date </i></th>
            <th><i> Start time </i></th>
            <th><i> Finish time </i></th>
            <th><i> Broken chips </i></th>
            <th><i> Used fuel </i></th>
            <th><i> Produced fuel </i></th>
            <th><i> Status </i></th>
        </tr>
        <d:forEach items="${detailInfo}" var="info">
            <tr align="center">
                <td>${info.id}</td>
                <fmt:parseDate value="${info.date}" pattern="y-M-dd" var="myParseDate"></fmt:parseDate>
                <td><fmt:formatDate value="${myParseDate}" pattern="dd.MM.yyyy"></fmt:formatDate></td>
                <td>${fn:substring(info.startTimestamp,0,8)}</td>
                <td>${fn:substring(info.finishTimestamp,0,8)}</td>
                <td>${info.amountOfBrokenChips}</td>
                <td>${info.usedFuel}</td>
                <td>${info.producedFuel}</td>
                <td>${info.status}</td>
            </tr>
        </d:forEach>
    </table>
</section>

<div style="margin-left: 5%; padding: 20px;">
    <INPUT TYPE="button" VALUE="Back" style="width:100px;height:45px" onClick="history.go(-1);">
</div>
</body>
</html>


