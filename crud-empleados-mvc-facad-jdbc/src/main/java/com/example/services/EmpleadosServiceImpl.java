package com.example.services;

import java.sql.Connection;

import com.example.dao.DBConexion;

public class EmpleadosServiceImpl implements EmpleadoService{

	@Override
	public boolean isConnectionOK() {
		// Connetar con la capa DAO:
		DBConexion dbConexion = new DBConexion("root", "Temp2026");
		
		//TODO cerrar la conexion
		
		Connection conn = null; // varible loca por el try catch añadido abajo, hay que iniciarla a null.
		
		try {
			conn = dbConexion.getConexion();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return conn == null ? false : true;
	}

	
}
