package com.example.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConexion {

	private String username;
	private String password;
	private Connection connection;
	
	// Logger para registrar eventos relacionados con la conexión a la base de datos
	private static final Logger LOG = Logger.getLogger("DBConexion");
	
	public DBConexion(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	// Método para establecer la conexión a la base de datos	
	public Connection getConexion() throws ClassNotFoundException {
		
		String urlConnection = "jdbc:mysql://localhost:3306/empresa-crud-empleados";
				// + "empresa-crud-empleados";
				
		
		Properties info = new Properties();
		
		info.put("user", this.username);
		info.put("password", this.password);
		
		// Con este método tenemos que manejar las posibles excepiones.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection(urlConnection, info);
			LOG.info("¡¡Conectado a la base de datos correctamente!!");
		} catch (SQLException e) {
			LOG.info("¡¡Error de conexión a la base de datos!!");
			e.printStackTrace();
		}
		
		return this.connection;
	}
	
}
