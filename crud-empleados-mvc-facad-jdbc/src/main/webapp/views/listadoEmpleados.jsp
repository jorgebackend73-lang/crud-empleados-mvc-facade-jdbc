<%@page import="java.math.RoundingMode"%>
<%@page import="com.example.models.Empleado"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado_Empleados</title>
</head>
<body>
	<%
		// Obtenemos la lista de empleados del atributo de la solicitud.
		List<Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
	
	%>
	<h1>Listado de Empleados</h1>
	
	<!-- botón del formulario -->
	<div>
		<a href="AltaController" title="Muestra el formulario de alta o modificación de empleados">
			Alta de Empleado
		</a>
	</div>
	
	<table>
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Primer Apellido</th>
				<th>Segundo Apellido</th>
				<th>Fecha de Alta</th>
				<th>Genero</th>
				<th>Salario</th>
			
			</tr>
		</thead>	
		<tbody> <!-- una fila por cada registro de la tabla  --> 
		    
                <%
                	for (Empleado empleado : empleados) {
                	%>
                	<tr>
                		<td><%=empleado.nombre() %></td>
                		<td><%=empleado.primerApellido() %></td>
                		<td><%=empleado.segundoApellido() %></td>
                		<td><%=empleado.fechaAlta() %></td>
                		<td><%=empleado.genero() %></td>
                		<td><%=empleado.salario().setScale(2, RoundingMode.HALF_UP) %></td>
                		<td><a href="DetallesController?idEmpleado=<%=empleado.id() %>">Detalles</a></td>
                	</tr>
                	
                	<%
                	}
                
                %>
		
		</tbody>
		
	</table>
</body>
</html>