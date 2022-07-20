<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
<head>
    <title>List of details</title>
    <style>
       td {
        padding: 5px;
       }
    </style>
</head>
<body>
<section>
    <h1 style="color:#ff0000">List of produced details (ID is clickable)</h1>
    <jsp:useBean id="infoList" type="java.util.ArrayList" scope="request"/>
    <table border="1" cellpadding="8">
        <tr>
            <th><i> ID </i></th>
            <th><i> Date </i></th>
            <th><i> Start time </i></th>
            <th><i> Finish time </i></th>
            <th><i> Status </i></th>
            <th> </th>
        </tr>
        <c:forEach items="${infoList}" var="info">
            <tr align="center">
                <td><a href="stats/${info.id}">${info.id}</a></td>
                <fmt:parseDate value="${info.date}" pattern="y-M-dd" var="myParseDate"></fmt:parseDate>
                <td><fmt:formatDate value="${myParseDate}" pattern="dd.MM.yyyy"></fmt:formatDate></td>
                <td>${fn:substring(info.startTimestamp,0,8)}</td>
                <td>${fn:substring(info.finishTimestamp,0,8)}</td>
                <td>${info.status}</td>
                <td width="20pt"><a href="delete/${info.id}"><img src="${pageContext.request.contextPath}/delet.png" alt="" title="Delete" width="100%" height="100%"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<div style="margin-left: 7%; padding: 20px;">
    <INPUT TYPE="button" VALUE="Home" style="width:100px;height:25px" onClick="window.location='index.html'">
</div>
</body>
</html>


