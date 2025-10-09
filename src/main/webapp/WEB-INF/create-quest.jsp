<%--
  Created by IntelliJ IDEA.
  User: ramis
  Date: 8/10/2025
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp"%>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
            color: #333;
        }

        form {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        div {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input[type="text"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="checkbox"] {
            margin-right: 5px;
        }

        button, input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 10px;
        }

        button:hover, input[type="submit"]:hover {
            background-color: #45a049;
        }

        .question-container {
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 15px;
            border-left: 4px solid #4CAF50;
        }
    </style>
</head>
<body>
<form method="post" action="create-quest">
    <input type="hidden" name="totalQuestions" value="1">
    <div>
        <label>Название Квеста</label>
        <input type="text" name="title" required maxlength="128">
    </div>
    <div>
        <label>Описание квеста</label>
        <input type="text" name="description" maxlength="255" required>
    </div>
    <div id="questions">
        <div class="question-container">
            <label>Вопрос 1</label>
            <input type="text" name="q1" maxlength="255" required>
            <input type="hidden" name="q1ID" value="1">
            <label>
                <input type="checkbox" name="q1_end"> Это последний вопрос?
            </label>
        </div>
    </div>
    <button type="button" onclick="addQuestion()">Добавить вопрос</button>
    <input type="submit" value="Продолжить">
</form>

<script>
    let questionCounter = 1;

    function addQuestion() {
        questionCounter++;
        const container = document.getElementById("questions");
        const newQuestion = document.createElement("div");
        newQuestion.className = "question-container";
        newQuestion.innerHTML = `
                <label>Вопрос \${questionCounter}</label>
                <input type="text" name="q\${questionCounter}" maxlength="255" required>
                <input type="hidden" name="q\${questionCounter}ID" value="\${questionCounter}">
                <label>
                    <input type="checkbox" name="q\${questionCounter}_end" value="true">
                    Это последний вопрос?
                </label>
            `;
        container.appendChild(newQuestion);
        document.querySelector("input[name='totalQuestions']").value = questionCounter;
    }
</script>
</body>
</html>
