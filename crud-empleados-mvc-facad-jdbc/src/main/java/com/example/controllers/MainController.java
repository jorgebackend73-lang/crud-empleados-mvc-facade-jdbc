package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.example.services.EmpleadoService;
import com.example.services.EmpleadosServiceImpl;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/MainController")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger("MainController");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Hay que conectar con la capa de servicios, 
		// que a su vez conectará con la capa DAO (donde se implementa JDBC),
		// para hacer las consultas SQL correspondientes
		// y finalmenete el Servlet mostrará la respuesta renderizando una vista JSP.
		// Este recibe y procesa las peticiones del cliente, y se encarga de coordinar la lógica de negocio y la presentación de la información.
	
		// Comprobar la conexión con la base de datos a traves de la capa de servicios:
		
		EmpleadoService empleadoService = new EmpleadosServiceImpl();
		
		boolean connectionResult = false;
		
		try {
			connectionResult = empleadoService.isConnectionOK();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (connectionResult = true)
			LOG.info("Conexión Exitosa");
		else
			LOG.info("Error de Conexión");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	// no conectar con la capa de servicios directamente. Para eso hacemos la capa de servicios, con el servicio de empleados
	// y todo lo que necesite un servicio.

}
