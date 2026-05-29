<%@page import="com.example.models.Departamento"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Formulario</title>
</head>
<body>
	<h1>Formulario de Alta/Modificación de Empleado</h1>
	<fieldset>
		<legend>Formulario de Gestión de Empleados</legend>
		<form action="#" method="post">
			<div>
				<label for="nombre">Nombre: </label> 
				<input type="text" id="nombre" name="nombre" required placeholder="Escriba su nombre.">
			</div>
			<br>
			<div>
                <label for="primerApellido">Primer Apellido: </label> 
                <input type="text" id="primerApellido" name="primerApellido" required placeholder="Escriba su Primer Apellido.">
            </div>
            <br>
            <div>
                <label for="segundoApellido">Segundo Apellido: </label> 
                <input type="text" id="segundoApellido" name="segundoApellido" placeholder="Escriba su Segundo Apellido.">
			</div>
			<br>
			<div>
				<label>Fecha de Alta: </label>
				<input type="date" name="fechaAlta" id="fechaAlta" required>
			</div>
			<br>
			<div>
				<fieldset>
					<legend>Genero </legend>
					<label for="hombre">Hombre: </label>
					<input type="radio" id="hombre" required name="genero" value="HOMBRE">					
					<label for="mujer">Mujer: </label>
					<input type="radio" id="mujer" required name="genero" value="MUJER">					
					<label for="otro">Otro: </label>
					<input type="radio" id="otro" required name="genero" value="OTRO">
				</fieldset>				
			</div>
			<br>
			<div>
			    <label for="salario">Salario: </label>
                <input type="text" id="salario" name="salario" required>
			</div>
			<br>
			<div>
				<%
					List<Departamento> departamentos = (List<Departamento>) request.getAttribute("departamentos");
				%>
			    <label for="departamento">Departamento: </label>
				<select id="departamento" name="departamento" required>
					<option></option>
					<%
						for (Departamento departamento : departamentos) {
							%>
							<option value="<%=departamento.id() %>"><%= departamento.nombre() %></option>
							<%
						}
					%>					
				</select>
			</div>
			<br>
			<div>
				<label for="correos">Correos: </label>
				<input type="text" id="correos" name="correos" placeholder="Uno o varios separados por ;">
			</div>
			<br>
			<div>
				<label for="telefonos">Números de Teléfono: </label>
				<input type="text" id="telefonos" name="telefonos" placeholder="Uno o varios separados por ;">
			</div>
			
			<br>
			<br>
			
			<input type="submit" value="Enviar">			
		</form>
	</fieldset>

</body>
</html>