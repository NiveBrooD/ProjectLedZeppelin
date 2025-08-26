<%--
  Created by IntelliJ IDEA.
  User: ramis
  Date: 8/11/2025
  Time: 3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp" %>
<!DOCTYPE html>
<html lang="ru">

<head>
    <title>Создание ответов</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
            color: #333;
        }

        .question-block {
            border: 1px solid #ffffff;
            padding: 15px;
            margin-bottom: 20px;
        }

        .answer-block {
            margin: 10px 0;
            padding: 10px;
            background: #f5f5f5;
        }

        .add-answer {
            margin: 10px 0;
        }
    </style>
</head>
<body>
<form method="post" action="create-answers">
    <c:forEach items="${sessionScope.newQuestions}" var="question">
        <div class="question-block">
            <h3>Вопрос ID ${question.id}</h3>
            <p>${question.description}</p>

            <c:if test="${not question.end}">
                <div id="answers-${question.id}">
                    <div class="answer-block">
                        <div>
                            <label style="color: #212529">Ответ 1: </label>
                            <input type="text" name="q${question.id}_a1_text" required>
                        </div>
                        <div>
                            <label style="color: #212529">ID следующего вопроса: </label>
                            <input type="number" name="q${question.id}_a1_next" required>
                        </div>
                    </div>
                </div>

                <button type="button" class="add-answer"
                        onclick="addAnswer(${question.id})">
                    Добавить ответ
                </button>
            </c:if>
        </div>
    </c:forEach>
    <input type="submit" value="Закончить">
</form>

<script>
    function addAnswer(questionId) {
        const container = document.getElementById(`answers-\${questionId}`);
        const answerCount = container.querySelectorAll('.answer-block').length + 1;

        const newAnswer = document.createElement('div');
        newAnswer.className = 'answer-block';
        newAnswer.innerHTML = `
                <div>
                    <label style="color: #212529">Ответ \${answerCount}: </label>
                    <input type="text" name="q\${questionId}_a\${answerCount}_text" required>
                </div>
                <div>
                    <label style="color: #212529">ID следующего вопроса: </label>
                    <input type="number" name="q\${questionId}_a\${answerCount}_next" required>
                </div>
            `;

        container.appendChild(newAnswer);
    }
</script>
</body>
</html>
