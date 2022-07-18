<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <h1 style="color:#0033ff">Detail information</h1>
    <jsp:useBean id="detailInfo" type="java.util.ArrayList" scope="request"/>
    <table border="1">
        <tr>
            <th><i>ID</i></th>
            <th><i>Date</i></th>
            <th><i>Start time</i></th>
            <th><i>Finish time</i></th>
            <th><i>Broken chips</i></th>
            <th><i>Used fuel</i></th>
            <th><i>Produced fuel</i></th>
            <th><i>Status</i></th>
        </tr>
        <d:forEach items="${detailInfo}" var="info">
            <tr>
                <td>${info.id}</td>
                <td>${info.date}</td>
                <td>${info.startTimestamp}</td>
                <td>${info.finishTimestamp}</td>
                <td>${info.amountOfBrokenChips}</td>
                <td>${info.usedFuel}</td>
                <td>${info.producedFuel}</td>
                <td>${info.status}</td>
            </tr>
        </d:forEach>
    </table>
</section>
<div style="margin-left: 5%; padding: 20px;">
    <INPUT TYPE="button" VALUE="Back" onClick="history.go(-1);">
</div>
</body>
</html>


