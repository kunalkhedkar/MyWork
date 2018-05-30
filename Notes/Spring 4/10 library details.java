JSTl - jsp standard tag library
		
		use for 'for loop'
		
		prefix="c"%
		Attribute starts with c is asume to realetd with jstl lib 
			'<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>'

		<c:forEach items="${todos}" var="todo">
					<tr>
						<td>${todo.desc}</td>
						<td>${todo.targetDate}</td>
						<td>${todo.done}</td>
						<td><a type="button" class="btn btn-warning"
							href="/update-todo?id=${todo.id}">Update</a>
						<a type="button" class="btn btn-danger"
							href="/delete-todo?id=${todo.id}">Delete</a></td>
					</tr>
				</c:forEach>


JSTl fmt
	formating tag
	- Use to format date,currency,numbers in specific fromat

	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<fmt:formatDate pattern="dd/MM/yyyy"
			value="${todo.targetDate}" />

