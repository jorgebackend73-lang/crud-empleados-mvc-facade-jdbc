package com.example.services;

import java.sql.Connection;

import com.example.dao.DBConexion;

public class EmpleadosServiceImpl implements EmpleadoService{

	@Override
	public boolean isConnectionOK() throws Exception {
		
		
		//TODO cerrar la conexion
		
		boolean connectionOk = false;
		
		// Connection conn = null; // varible local por el try catch añadido abajo, hay que iniciarla a null.
				
		// Hay que cerrar las cosas en orden, eliminamos bloque finally y lo ponemos todo en try.
		try (// Connetar con la capa DAO:
				DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection conn = dbConexion.getConexion()) {
			
			if (conn != null)
				connectionOk = true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		/*finally {
			if (conn != null)
				conn.close();
		}*/
		
		
		return connectionOk;
	}

	
}
