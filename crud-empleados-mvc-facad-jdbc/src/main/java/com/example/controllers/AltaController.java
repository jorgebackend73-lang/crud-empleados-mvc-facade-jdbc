package com.example.controllers;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.example.models.Departamento;
import com.example.models.Empleado;
import com.example.models.Genero;
import com.example.services.DepartamentoService;
import com.example.services.DepartamentoServiceImpl;
import com.example.services.EmpleadoService;
import com.example.services.EmpleadosServiceImpl;

/**
 * Servlet implementation class AltaController
 */
@WebServlet("/AltaController")
public class AltaController extends HttpServlet {
	private static final Logger LOG = Logger.getLogger("AltaController");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DepartamentoService departamentoService = new DepartamentoServiceImpl();
		
		List<Departamento> departamentos = null;
		
		try {
			departamentos = departamentoService.getDepartamentos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("departamentos",  departamentos);
		
		request.getRequestDispatcher("views/formularioAltaModificacion.jsp").forward(request, response);
	// recogemos el formulario de alta y lo guardamos en la base de datos.
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// aquí se recogen los datos procedentes de los campos del formulario.
		// Toda la información se recoge en formato String (text)
		
		String nombre = request.getParameter("nombre");
		String primerApellido = request.getParameter("primerApellido");
		String segundoApellido = request.getParameter("segundoApellido") == null 
				? "" : request.getParameter("segundoApellido");
		LocalDate fechaAlta = LocalDate.parse(request.getParameter("fechaAlta"));
		Genero genero = Genero.valueOf(request.getParameter("genero"));
		BigDecimal salario = BigDecimal.valueOf(Double.valueOf(request.getParameter("salario")));
		
		int departamentos_id = Integer.parseInt(request.getParameter("departamento"));
		
		// Hay que tener en cuenta que correos y teléfonos no son requeridos.
		
		List<String> direccionesCorreos = null;
		
		List<String> numerosDeTelefono = null;
		
		if (request.getParameter("correos") != null) {
		
			String direccionesCorreoRecibidas = request.getParameter("correos");
			String[] arrayDirCorreosRecibidos = direccionesCorreoRecibidas.split(";");
			
			direccionesCorreos = Arrays.asList(arrayDirCorreosRecibidos);
			
			// Comprobando
			System.out.println("Direcciones de correos recibidas");
			direccionesCorreos.forEach(System.out::println);
		
		}
		
		if (request.getParameter("telefonos") != null) {
			
			String numerosTelefonoRecibidos = request.getParameter("telefonos");
			String[] arrayNumTelRecibidos = numerosTelefonoRecibidos.split(";");
			
			numerosDeTelefono = Arrays.asList(arrayNumTelRecibidos);
			
			// Comprobando
			System.out.println("Números de teléfono recibidas");
			numerosDeTelefono.forEach(System.out::println);
		
		}
		
		
		
		
		// Comprobamos el flujo con un LOG a ver si recibimos la información adecuada procedente del formulario.
		// LOG.info("Nombre recibido: " + nombre);
		// LOG.info("Segundo Apellido: " + segundoApellido);
		
		// Crear el objeto empleado
		
		Empleado empleado = Empleado.builder()
				.nombre(nombre)
				.primerApellido(primerApellido)
				.segundoApellido(segundoApellido)
				.fechaAlta(fechaAlta)
				.genero(genero)
				.salario(salario)
				.departamentos_id(departamentos_id)
				.build();
		
		// Aquí se llama al servicio para que se encargue de la lógica de negocio y de la interacción con la base de datos.
		// El servicio se encargará de llamar al método del DAO que inserta el empleado en la base de datos,
		// junto con sus correos y teléfonos.
		
		
				
		EmpleadoService empleadoService = new EmpleadosServiceImpl();
		
		try {
			empleadoService.altaEmpleado(empleado, direccionesCorreos, numerosDeTelefono);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	
	List<Empleado> empleados = empleadoService.getEmpleados();
	
	request.setAttribute("empleados", empleados);
	
	request.getRequestDispatcher("views/listadoEmpleados.jsp").forward(request, response);
	
	}

}
	
	
