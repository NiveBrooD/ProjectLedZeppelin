<%--
  Created by IntelliJ IDEA.
  User: ramis
  Date: 8/5/2025
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<c:set var="question" value="${quest.currentQuestion}"/>
<head>
    <style>

        .question-container {
            background: white;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h3 {
            color: #333;
            margin-bottom: 20px;
            text-align: center;
        }
        .answers {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 10px;
        }
        .answer-btn {
            background: #4CAF50;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.2s;
        }
        .answer-btn:hover {
            background: #45a049;
        }
        .end-message {
            text-align: center;
            margin-top: 20px;
        }
        .end-message button {
            background: #3498db;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            margin: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="question-container">
    <h3> ${question.description} </h3>
    <c:choose>
        <c:when test="${not question.end}">
            <div class="answers">
                <c:forEach var="answer" items="${question.answers}">
                    <form action="quest" method="post">
                        <input type="hidden" name="answerId" value="${answer.id}">
                        <input type="hidden" name="questId" value="${quest.id}">
                        <button type="submit" class="answer-btn">
                                ${answer.description}
                        </button>
                    </form>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="end-message">
                <p style="color: #0f2537">Квест завершен!</p>
                <button onclick="location.href='/'">Вернуться к списку квестов</button>
                <button onclick="location.href='/restart'">Начать заново</button>
            </div>

        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
