package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.example.models.Departamento;
import com.example.models.EmpleadoUpdate;
import com.example.services.DepartamentoService;
import com.example.services.DepartamentoServiceImpl;
import com.example.services.EmpleadoService;
import com.example.services.EmpleadosServiceImpl;

/**
 * Servlet implementation class UpdateController
 */
@WebServlet("/UpdateController")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idEmpleado = Integer.parseInt(request.getParameter("idEmpleado"));
		
		// ya con el id del empleado, conectamos con la capa de servicios y recuperamos todo
		// lo necesario para mostrar el formulario de actualización con los daos del empleado.
		
		EmpleadoService empleadoService = new EmpleadosServiceImpl();
		
		// Recuperamos toda la informacion del empleado a actualizar para mostrarla en el 
		// formulario de actualización.
		
		EmpleadoUpdate empleadoUpdate = empleadoService.getEmpleadoById(idEmpleado);
		
		// Guardamos el empleadoUpdate en el request, para que esté disponible. Se pasa como un atributo de la petición 
		// para que esté disponible en la vista. Y formulario de actualización pueda mostrar sus datos.
		request.setAttribute("empleadoUpdate", empleadoUpdate);
		
		// también necesitamos conectarnos con el servicio de departamentos para recuperar todos los departamentos
		// el formulario los está esperando y no los recibe.
		
		DepartamentoService departamentoService = new DepartamentoServiceImpl();
		
		try {
			List<Departamento> departamentos = departamentoService.getDepartamentos();
			request.setAttribute("departamentos", departamentos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Redirigimos a la vista del formulario de actualización
		request.getRequestDispatcher("views/formularioAltaModificacion.jsp")
			.forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
