<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="/WEB-INF/pages/include-head.jsp"%>
</head>
<body>
    <c:if test="${!empty list }">
        <c:forEach items="${list }" var="admin">
            ${admin }<br/>
        </c:forEach>
    </c:if>
</body>
</html>
