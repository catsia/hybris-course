<%@ page trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>Questions</div>
<c:forEach items="${questions}" var="question">
<div>
    ${question.questionCustomer} ${question.question}
</div>
<div>

    ${question.answerCustomer} ${question.answer}
</div>
</c:forEach>
