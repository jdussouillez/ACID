<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link href="${pageContext.request.contextPath}/resources/styles/home.css" rel="stylesheet" type="text/css"
	title="Default Style">
<script src="jquery-2.1.3.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html">ACID</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<div class="navbar-form navbar-right">
					<a id="logoutBtn" class="btn btn-danger" href="logout.html">Logout</a>
				</div>
			</div>
		</div>
	</nav>

	<div class="board-title">
            ${projets}
	</div>

	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>