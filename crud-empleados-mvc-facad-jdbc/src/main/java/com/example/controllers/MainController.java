package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.example.services.EmpleadoService;
import com.example.services.EmpleadosServiceImpl;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/MainController")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
	
		// Comprobar la conexión con la ase de datos a traves de la capa de servicios:
		
		EmpleadoService empleadoService = new EmpleadosServiceImpl();
		empleadoService.isConnectionOK();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
