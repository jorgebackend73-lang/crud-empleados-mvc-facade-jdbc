package com.example.dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.example.models.Empleado;

public class DBConexion implements AutoCloseable {

	private String user;
	private String password;
	private Connection connection;
	
	// Logger para registrar eventos relacionados con la conexión a la base de datos
	// Loger es de java.util.logging.
	private static final Logger LOG = Logger.getLogger("DBConexion");
	
	public DBConexion(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}
	
	// Método para establecer la conexión a la base de datos, la que llama.	
	public Connection getConexion() throws ClassNotFoundException {
		
		// Definimos la URL de conexión a la DB con su puerto y todo del proyecto:
		String urlConnection = "jdbc:mysql://localhost:3306/empresa-crud-empleados";
				// + "empresa-crud-empleados";
				
		// Especificamos usuario y contraseña en objeto Propeerties, con info.put,
		// para meter user y password. Así se lo damos a DriverManager.getConnection.
		Properties info = new Properties();
		
		info.put("user", this.user);
		info.put("password", this.password);
		
		// Con este método tenemos que manejar las posibles excepiones. Nos pusimos sobre
		// lo subrayado en rojo y sorround with try-catch, que nos añade un manejo de excepciones
		// necesario.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // hay que pasar el driver de conexión en libraries, Maven Dependencies y el nobre que vemos entre comillas.
			// al class le agregamos un throw para manejar la excepción de ClassNotFoundException, que es la que se lanza si no encuentra el driver de conexión.
			this.connection = DriverManager.getConnection(urlConnection, info);
			LOG.info("¡¡Conectado a la base de datos correctamente!!"); // nos olvidamos de println para la consola, ahora todo al LOG.
		} catch (SQLException e) {
			LOG.info("¡¡Error de conexión a la base de datos!!");
			e.printStackTrace();
		}
		
		return this.connection;
	}

	@Override
	public void close() throws Exception {
		this.connection.close();
		// TODO Auto-generated method stub
		
	}
	
	// Metodo que recupera todos los egistros de la tabla empleados:
	// ResultSet es una clase de java.sql que representa el resultado
	// de una consulta SQL. Es una especie de tabla virtual que contiene
	// los datos devueltos por la consulta. Permite iterar sobre las filas
	// y acceder a los valores de cada columna.
	
	public ResultSet getEmpleados(Connection connection) {
		
		ResultSet rs = null;
		String query = "SELECT * FROM `empresa-crud-empleados`.empleados"; // hay que
		// poner el nombre de la base de datos y el nombre de la tabla, separados por un punto.
		// Entre comillas porque tiene guiones.
		// lo anterior entre comillas viene del query de workbench.
		
		Statement stmt = null; // hay que inicializar el statement a null para poder manejarlo 
		// en el bloque finally, para cerrarlo.
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query); // executeQuery se utiliza para ejecutar consultas SQL
			// que devuelven un conjunto de resultados, como SELECT. Devuelve un objeto ResultSet 
			// que contiene los datos obtenidos de la consulta.
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs; // para evitar el error de que no devuelve nada, aunque no haga nada el método,
		// lo dejamos así por ahora.
	}
	
	// Méto que recupera todos los reistros de la tabla departamentos:
	public ResultSet getDptos(Connection connection) {
		
		ResultSet rs = null;
		String query = "SELECT * FROM departamentos";
		Statement stmt = null;
		
		try {
		stmt = connection.createStatement();
		rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			LOG.severe("Error recuperando departamentos y la causa más probable es: " + e.getMessage());
			e.printStackTrace();
		}
		
	return rs;
	}
	
	// Metodo que inserta empleado y sus correos y teléfonos en la base de datos,
	// en el marco de una transacción.
	
	public void altaEmpleado(Empleado empleado, List<String> dirCorreos, List<String> numerosTelefono) {
		
		// Inserta empleado y devuelve el last inserted id en la tabla de empleados
		String query1;
		
		// Con el id del empleado, tenemos que insertar sus correos y sus telefonos correspondientes
		// Inserta correos
		String query2;
		
		// Inseta telefonos
		String query3;
		
		
		
	}
	
}
