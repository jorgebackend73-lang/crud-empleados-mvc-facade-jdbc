package com.example.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.example.dao.DBConexion;

public class EmpleadosServiceImpl implements EmpleadoService{

	@Override
	public boolean isConnectionOK() throws SQLException {
		// Connetar con la capa DAO:
		DBConexion dbConexion = new DBConexion("root", "Temp2026");
		
		//TODO cerrar la conexion
		
		boolean connectionOk = false;
		
		Connection conn = null; // varible local por el try catch añadido abajo, hay que iniciarla a null.
				
		try {
			conn = dbConexion.getConexion();
			if (conn != null)
				connectionOk = true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		
		
		return connectionOk;
	}

	
}
