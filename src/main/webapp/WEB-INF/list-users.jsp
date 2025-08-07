<%--
  Created by IntelliJ IDEA.
  User: ramis
  Date: 7/25/2025
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@include file="parts/header.jsp" %>
<head>
    <title>Quest - users</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        .user-table-container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        .table-responsive {
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
        }

        .user-table {
            width: 100%;
            border-collapse: collapse;
            font-family: 'Lato', sans-serif;
            margin: 25px 0;
        }

        .user-table thead tr {
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            color: white;
            text-align: left;
            font-weight: bold;
        }

        .user-table th,
        .user-table td {
            padding: 12px 15px;
        }

        .user-table tbody tr {
            border-bottom: 1px solid #dddddd;
            transition: all 0.2s;
        }

        .user-table tbody tr:nth-of-type(even) {
            background-color: rgba(106, 17, 203, 0.05);
        }

        .user-table tbody tr:last-of-type {
            border-bottom: 2px solid #6a11cb;
        }

        .user-table tbody tr:hover {
            background-color: rgba(106, 17, 203, 0.1);
            transform: translateX(5px);
        }

        .role-badge {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.8em;
            font-weight: 600;
            text-transform: uppercase;
        }

        .role-ADMIN {
            background-color: #dc3545;
            color: white;
        }

        .role-USER {
            background-color: #28a745;
            color: white;
        }

        .role-GUEST {
            background-color: #ffc107;
            color: #212529;
        }

        .empty-message {
            text-align: center;
            padding: 2rem;
            color: #6c757d;
            font-size: 1.2rem;
        }

        @media (max-width: 768px) {
            .user-table-container {
                padding: 0 0.5rem;
            }

            .user-table th,
            .user-table td {
                padding: 8px 10px;
                font-size: 0.9rem;
            }
        }
    </style>
</head>
<body>
<div class="user-table-container">
    <h2 class="text-center mb-4" style="color: #fff">List of Users</h2>

    <div class="table-responsive">
        <table class="user-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Login</th>
                <th>Role</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty users}">
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.login}</td>
                            <td>
                                <span class="role-badge role-${user.role}">
                                        ${user.role}
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="3" class="empty-message">
                            Нет пользователей для отображения
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
