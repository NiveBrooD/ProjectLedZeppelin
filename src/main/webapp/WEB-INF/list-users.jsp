<%--
  Created by IntelliJ IDEA.
  User: ramis
  Date: 7/25/2025
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@include file="parts/header.jsp"%>
<body>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Login</th>
                <th>Role</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}"  var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.login}</td>
                    <td>${user.role}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
