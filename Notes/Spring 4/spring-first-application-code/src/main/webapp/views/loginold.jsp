<%@ include file="common/header.jspf"%>


	<div class="container">
		<h1>
			<font color="red"> ${ erroressage } </font>
		</h1>
		<h1>
		Login
		</h1>
		
		<form action="/login" method="post">
			Username <input type="text" class="form-control" name="name"><br> <br>
			Password <input type="password" class="form-control" name="password"><br> <br>
			<input type="submit" class="btn btn-success" value="login">
		</form>
	</div>
</body>
</html>