package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.example.models.Detalle;
import com.example.models.Empleado;
import com.example.services.EmpleadoService;
import com.example.services.EmpleadosServiceImpl;

/**
 * Servlet implementation class DetallesController
 */
@WebServlet("/DetallesController")
public class DetallesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 private static final Logger LOG = Logger.getLogger("DetallesController");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetallesController() {
        super();
        // TODO Auto-generated constructor stub
        
        
       
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recibir el idEmpleado, parametro enviado desde la vista, para mostrar los detalles de ese empleado (request).
		
		int idEmpleado = Integer.parseInt(request.getParameter("idEmpleado"));
		
		// Buena idea comprobar si estamos recibiendo el idEmpleado correctamente, con un log:
		
		LOG.info("ID EMPLEADO RECIBIDO: " + idEmpleado);
		
		// Un join para conectar con la capa de servicios para recuperar el empleado con ese id, 
		// y mostrar sus detalles en la vista. Pasamos por DBConexion.
		
		EmpleadoService empleadoService = new EmpleadosServiceImpl();
		
		/*Recuperamos a todos los empleados, y filtramos para obtener la información el empleado
		 * cuyo id se ha recibido.*/
		
			List<Empleado> empleados = empleadoService.getEmpleados();
		
			Empleado empleado = empleados.stream()
					.filter(e -> e.id() == idEmpleado)
					.findFirst().orElseThrow(() -> 
						new RuntimeException("Empleado no encontrado")); 
			// Si no se encuentra el empleado, lanzamos una excepción.
		request.setAttribute("empleado", empleado); 
		// al empleado lo pasamos a la vista, para mostrar su nombre y apellidos en la vista detallesEmpleado.jsp
			
		Detalle detalles = empleadoService.detalles(idEmpleado);
		
		// Mostrar la vista detallesEmpleado.jsp, con los detalles del empleado. 
		// Para eso, hay que pasarle el objeto detalles a la vista, con un setAttribute.
		
		request.setAttribute("detalles", detalles);
		
		request.getRequestDispatcher("views/detallesEmpleado.jsp")
			.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
