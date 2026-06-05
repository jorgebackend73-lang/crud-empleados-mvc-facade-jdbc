package com.example.dao;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	// No se usa guardar, el termino en java y web es peristir.
	
	public void altaEmpleado(Empleado empleado, 
			List<String> dirCorreos, 
			List<String> numerosTelefono,
			Connection connection) 
			throws SQLException {
		
		// Inserta empleado y devuelve el last inserted id en la tabla de empleados.
		// Usamos preparedStatement para evitar inyecciones SQL, que es una técnica maliciosa
		// que consiste en insertar código SQL malicioso en una consulta para manipular la base de datos.
		// Hacemos como que vamos a crear un empleado en workben y al ir a aplicar la modificación lo copiamos y
		// pegamos a continuación del igual y lo llenamos de interrogaciones en los valores que nos inventamos.
		String query1 = "INSERT INTO `empleados` (`nombre`,"
				+ " `primerApellido`, `segundoApellido`,"
				+ " `fechaAlta`, `genero`, `salario`,"
				+ " `departamentos_id`) VALUES"
				+ " (?, ?, ?, ?, ?, ?, ?)";
		
		/* 
		 * PreparedStatement es la primera línea de defensa contra inyecciones SQL.
		 * Separan la parte fija de la consulta de los parametros que se van a insertar, 
		 * lo que hace que el código SQL malicioso no pueda ser ejecutado.
		 * El rendimiento es muy similar al de los procedimientos almacenados
		 * pq una vez se ejecuta la consulta, el componente analizador de cunsulta
		 * u optimizador de consulta no tiene que analizar 
		 * nuevamente el plan de ejecución de la consulta y la misma es compilada y
		 * guardada  en el servidor, de forma tal que la próxima vez solamente hay que pasarle
		 * los parametros variables a la consulta para ejcutarla y la ejecución  será
		 * lo más rápido, eficiente y seguro posible.
		 * 
		 * Tener en cuenta que los parametros que se le pasan al PreparedStatemen 
		 * comienzan en 1 no en 0.		 * 
		 * */
		
		// Con el id del empleado, tenemos que insertar sus correos y sus telefonos correspondientes
		// Inserta correos
		String query2 = "INSERT INTO `correos` (`email`, `empleados_id`)"
				+ " VALUES (?, ?)";
		
		// Inseta telefonos
		String query3 = "INSERT INTO `telefonos` (`numero`, `empleados_id`)"
				+ " VALUES (?, ?)";
		
		// Pedir id de un empleado y toda la entrada de datos de un empleado debe realizarse dentro de una transacción,
		// para garantizar la integridad de los datos. Si algo falla, se hace un rollback y no se inserta nada.
		
		try {
			// iniciar transacción
			connection.setAutoCommit(false); // desactivamos el autocommit para controlar manualmente. Entramos en modo transacción.
			
			// Insertar empleado
			PreparedStatement stmt1 = connection.prepareStatement(query1,
					Statement.RETURN_GENERATED_KEYS); 
					// con esta opción le decimos que queremos recuperar las claves generadas, en este caso el id del empleado.
			stmt1.setString(1,empleado.nombre());
			stmt1.setString(2, empleado.primerApellido());
			stmt1.setString(3, empleado.segundoApellido()); 
			// considerar que el segundo apellido no es requerido, por lo que podría ser null,
			// pero el método setString lo acepta sin problemas. Lo tuvimos en cuenta en AltaController.
			// Por tanto no es necesario hacer nada más aquí.
			stmt1.setDate(4, Date.valueOf(empleado.fechaAlta())); 
			// hay que convertir la fecha de LocalDate a Date, que es el formato que espera la base de datos.
			// Date lo importamos de java.sql, no de java.util.
			stmt1.setString(5, empleado.genero().name());
			// el método setString espera un String, pero el género es un enum, 
			// por lo que tenemos que convertirlo a String con el método name().
			//stmt1.setBigDecimal(6, empleado.salario()); 
			stmt1.setDouble(6, empleado.salario().doubleValue()); 
			// el método setBigDecimal espera un BigDecimal, pero el salario es un double en la BD,
			// por lo que tenemos que convertirlo a double con el método doubleValue().
			stmt1.setInt(7, empleado.departamentos_id());
			// Con lo anterior tenemos todos lo parametros seteados, ahora ejecutamos la consulta.
			
			int totalFilas = stmt1.executeUpdate();			
			// executeUpdate se utiliza para ejecutar consultas SQL que modifican los datos,
			// como INSERT, UPDATE o DELETE. Devuelve un entero que indica el número de filas afectadas por la consulta.
			
			if (totalFilas != 0) {
				
				// Recuperar el id del empleado insertado, que es necesario para insertar sus correos y teléfonos.
				
				long lastInsertedId = 0L;
				
				ResultSet rs = stmt1.getGeneratedKeys(); 
				// con este método recuperamos las claves generadas, en este caso el id del empleado insertado, en una fila.
				
				if (rs.next())
					lastInsertedId = rs.getLong(1); 
				// con este método recuperamos el valor de la primera columna de la fila, que es el id del empleado insertado.
				
				// Insertar correos si los hay. Si la lista de correos es null, no se hace nada.
				if (dirCorreos != null && dirCorreos.size() > 0) {
					
					PreparedStatement stmt2 = connection.prepareStatement(query2); 
					// no necesitamos recuperar claves generadas, por lo que no le pasamos la opción.
					// consulta lista.
					
					stmt2.setInt(2, Math.toIntExact(lastInsertedId)); 
					// el método setInt espera un int, pero el id del empleado es un long, 
					// por lo que tenemos que convertirlo a int con el método toIntExact de la clase Math.
					
					
					// El código siguientte funciona pero no es muy eficiente, porque ejecuta una consulta por cada correo,
					// lo que puede ser un problema si hay muchos correos.
					
					/*for(String email : dirCorreos) {
						
						stmt2.setString(1, email);
						stmt2.executeUpdate();*/
					
					for (String email : dirCorreos) {
						
						stmt2.setString(1,  email);
						stmt2.addBatch(); 
						// con este método añadimos la consulta al batch, que es un conjunto de consultas que se ejecutan juntas.
					}
						
					stmt2.executeBatch(); 
					// con este método ejecutamos todas las consultas del batch de una sola vez, lo que es más eficiente.
												
					}
				// Insertar teléfonos si los hay. Si la lista de teléfonos es null, no se hace nada.
				if (numerosTelefono != null && numerosTelefono.size() > 0) {
					
					// long lastInsertedId = 0L;
					
					// ResultSet rs = stmt1.getGeneratedKeys();
					
					PreparedStatement stmt3 = connection.prepareStatement(query3); 
					// no necesitamos recuperar claves generadas, por lo que no le pasamos la opción.
					// consulta lista.
					
					stmt3.setInt(2, Math.toIntExact(lastInsertedId)); 
					// el método setInt espera un int, pero el id del empleado es un long, 
					// por lo que tenemos que convertirlo a int con el método toIntExact de la clase Math.
					
					
					// El código siguientte funciona pero no es muy eficiente, porque ejecuta una consulta por cada correo,
					// lo que puede ser un problema si hay muchos correos.
					
					/*for(String email : numerosTelefono) {
						
						stmt3.setString(1, numero);
						stmt3.executeUpdate();*/
					
					for (String numero : numerosTelefono) {
						
						stmt3.setString(1,  numero);
						stmt3.addBatch(); 
						// con este método añadimos la consulta al batch, que es un conjunto de consultas que se ejecutan juntas.
					}
						
					stmt3.executeBatch(); 
					// con este método ejecutamos todas las consultas del batch de una sola vez, lo que es más eficiente.
												
					}
				}
				
			
				
			
			
			
			
			
			connection.commit(); // si todo va bien, hacemos commit.
			
		} catch (Exception e) {
			
			LOG.severe("Error en la transacción de alta de empleado y la causa más probable es: " 
					+ e.getMessage());
			e.printStackTrace();
			connection.rollback(); // si algo falla, hacemos rollback para deshacer los cambios realizados en la
			LOG.info("¡¡Transacción de alta de empleado revertida, no se ha insertado al nuevo empleado!!");
			
		} finally {
			connection.setAutoCommit(true); // volvemos a activar el autocommit para que no afecte a otras operaciones.
		}
		
	}
	
	// Otra buena práctica es cerrar el método anterior para no escribir donde no debemos.
	/* Método que recupera los detalles (Nombre Dpto, correos y teléfonos, que no se muestran en la vista inicial) 
	 * del empleado cuyo id se recibe como parametro. Es un método público y va a devolver un ResultSet.
	 * No comernos la cabeza ya nos va bien eso en jdbc
	 * */
	public ResultSet detallesEmpleado(int idEmpleado, Connection connection) {
		
		ResultSet rs = null;
		// lanzamos una consulta con un join para recuperar el nombre del departamento,
		// los correos y los teléfonos del empleado con ese id.
		String query = "select dep.nombre nombreDpto, tel.numero numeroTelefono, cor.email email\n"
				+ "from empleados emp left join departamentos dep on\n"
				+ "	emp.departamentos_id = dep.id left join telefonos tel on\n"
				+ "		emp.id = tel.empleados_id left join correos cor on\n"
				+ "			emp.id = cor.empleados_id\n"
				+ "            \n"
				+ "where emp.id = ?";
		
		PreparedStatement stmt1 = null;
		
		try {
			stmt1 = connection.prepareStatement(query, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE);
			 // con esta opción le decimos que el ResultSet sea desplazable, 
			 // para poder recorrerlo varias veces y usando varios hilos del procesador.
			stmt1.setInt(1, idEmpleado);
			rs = stmt1.executeQuery();
		} catch (SQLException e) {
			LOG.severe("¡¡Error recuperando detalles del empleado!!");
			e.printStackTrace();
		}
		
		return rs;
	}
}
