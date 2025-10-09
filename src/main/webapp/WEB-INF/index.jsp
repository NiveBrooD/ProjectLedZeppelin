<%--
  Created by IntelliJ IDEA.
  User: ramis
  Date: 7/25/2025
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Выбор квеста</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }

        .page-title {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }

        .quest-list {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 15px;
            max-width: 800px;
            margin: 0 auto;
        }

        .quest-item {
            width: 100%;
            padding: 15px;
            border: 1px solid #e0e0e0;
            border-radius: 5px;
            background-color: #f9f9f9;
            transition: all 0.2s;
        }

        .quest-item:hover {
            border-color: #4CAF50;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .quest-title {
            color: #2c3e50;
            margin-top: 0;
            margin-bottom: 10px;
        }

        .quest-description {
            color: #555;
            margin: 0;
        }

        .play-button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }

        .play-button:hover {
            background-color: #45a049;
        }

        .no-quests {
            text-align: center;
            color: #777;
            font-style: italic;
            padding: 20px;
        }
    </style>
</head>
<body>
<h1 class="page-title" style="color: white">Выберите квест</h1>

<div class="quest-list">
    <c:choose>
        <c:when test="${not empty quests}">
            <c:forEach items="${quests}" var="quest">
                <div class="quest-item">
                    <h3 class="quest-title">${quest.title}</h3>
                    <p class="quest-description">${quest.description}</p>
                    <p class="quest-description">Автор: ${quest.author.login}</p>
                    <button class="play-button" onclick="location.href='/quest?id=${quest.id}'">
                        Играть
                    </button>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p class="no-quests">Нет доступных квестов</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
