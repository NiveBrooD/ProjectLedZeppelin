<%--
  Created by IntelliJ IDEA.
  User: ramis
  Date: 8/10/2025
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%--<%@include file="parts/header.jsp" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="parts/header.jsp"%>
<body>
<form method="post" action="create-quest">
    <input type="hidden" name="totalQuestions" value="1">
    <div>
        <label>Название Квеста</label>
        <input type="text" name="title" required>
    </div>
    <div>
        <label>Описание квеста</label>
        <input type="text" name="description" required>
    </div>
    <div id="questions">
        <div class="question-container">
            <label>Вопрос 1</label>
            <input type="text" name="q1" required>
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
                <input type="text" name="q\${questionCounter}" required>
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
