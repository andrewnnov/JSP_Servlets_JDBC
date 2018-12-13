<html>

<head><title>Student Confirmation Title</title></head>


<body>

The student is confirmed: ${param.firstName} ${param.lastName}

<br/><br/>



Favorite Programming Languages: <br/>
<!-- display list of "favorite language" -->

<ul>

<%
String[] langs = request.getParameterValues("favoriteLanguage");

   for(String tmpLang : langs) {	   
	   out.println("<li>" + tmpLang + "</li>");	   
   }

%>



</ul>






</body>

</html>