package com.example.services;

import java.sql.SQLException;
import java.util.List;

import com.example.models.Empleado;

public interface EmpleadoService {
	// método para comprobar la conexión a la base de datos.
	// todos los métodos de una interfaz son abstractos, no hace falta ponerlo, pero lo ponemos para que se vea claro.
	public abstract boolean isConnectionOK() throws SQLException, Exception;
	public abstract List<Empleado> getEmpleados();
	

}
