<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/WEB-INF/partials/head.jsp">
        <jsp:param name="title" value="Edit Ad" />
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/partials/navbar.jsp" />
<div class="container">
    <h1>Edit an Ad</h1>
    <form action="/ads/edit" method="post">
        <div class="form-group">
            <input type="hidden" name="id" value="${ad.id}"/>
            <input type="hidden" name="user_id" value="${ad.userId}"/>
            <label for="title">Title</label>
            <input id="title" name="title" class="form-control" type="text" value="${ad.title}">
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" name="description" class="form-control" type="text" >${ad.description}</textarea>
        </div>



        <div class="form-group">
            <select name="categories" multiple style="height:100px;">
                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}"
                    <c:forEach items="${selectedCategories}" var="selectedCategory">
                        <c:if test="${category.id==selectedCategory.id}"> selected</c:if>
                    </c:forEach>

                    >${category.category}</option>
                </c:forEach>
            </select>
        </div>



        <input type="submit" class="btn btn-block btn-primary">
    </form>
</div>
</body>
</html>
