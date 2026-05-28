package com.example.services;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.example.dao.DBConexion;
import com.example.models.Empleado;
import com.example.models.Genero;

public class EmpleadosServiceImpl implements EmpleadoService{
	
	private static final Logger LOG = Logger.getLogger("EmpleadosServiceImpl");
	
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

	@Override
	public List<Empleado> getEmpleados() {
		
		List<Empleado> empleados = new ArrayList<Empleado>();
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConexion()) {
			
			ResultSet rs = dbConexion.getEmpleados(connection); 
			// ResultSet es un tipo de dato que devuelve el resultado de una consulta SQL,
			// es como una tabla con filas y columnas. Pero es muy restringido y es mejor una 
			// lista de empleados, que habrá que recorrer.
			
			// Hacemos una especie de iterador con while. Que ira por los rsultados rs y 
			// mientras haya otro a conntinuación seguira creando líneas de empleados.
			while (rs.next()) {
				
				empleados.add(
						Empleado.builder()
						.id(rs.getInt("id"))
						.nombre(rs.getString("nombre"))
						.primerApellido(rs.getString("primerApellido"))
						.segundoApellido(rs.getString("segundoApellido"))
						.fechaAlta(rs.getDate("fechaAlta").toLocalDate())
						.genero(Genero.valueOf(rs.getString("genero")))
						.salario(new BigDecimal(rs.getDouble("salario")))
						.departamentos_id(rs.getInt("departamentos_id"))
						.build()
						);
				
				
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			LOG.severe("Error al recuperar los empleados" + e.getMessage());
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return empleados;
	}

	
}
