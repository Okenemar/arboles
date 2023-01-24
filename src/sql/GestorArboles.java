package sql;
import java.util.ArrayList;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestorArboles {
	
	private static final String HOST = "localhost";
	private static final String BBDD = "eh_garden";
	private static final String USERNAME_STRING = "root";
	private static final String PASSWORD_STRING = "";
	
	public static void run(String[] args) throws SQLException, ClassNotFoundException{	
		final int INSERTAR_ARBOL = 1;
		final int ELIMINAR_ARBOL = 2;
		final int MODIFICAR_INFORMACION_DEL_ARBOL = 3;
		final int VISUALIZAR_ARBOLES = 4;
		final int SALIR = 0;
		
		
		Scanner scan = new Scanner(System.in);
		int opcion_menu;
		Class.forName("com.mysql.cj.jdbc.Driver");//cargar driver
		Connection con = DriverManager.getConnection("jdbc:mysql://"+HOST+"/" + BBDD, USERNAME_STRING, PASSWORD_STRING );//abrir conexion			
		Statement st = con.createStatement();//crear el ejecutor de sentencias
		
		do {
			System.out.println("------MENU-------");
			System.out.println(INSERTAR_ARBOL +". insertar arbol");
			System.out.println(ELIMINAR_ARBOL + ". eliminar arbol");
			System.out.println(MODIFICAR_INFORMACION_DEL_ARBOL + ". modificar informacion");
			System.out.println(VISUALIZAR_ARBOLES + ". visualizar arboles");
			System.out.println(SALIR + ". Salir");
			System.out.println("Elije una de las opciones");
			opcion_menu = Integer.parseInt(scan.nextLine());
			
			switch (opcion_menu) {
			case INSERTAR_ARBOL:
				insertarArbol();
				System.out.println("primera opcion seleccionada\n");
				break;
			case ELIMINAR_ARBOL:
				eliminarArbol();
				System.out.println("segunda opcion seleccionada\n");
				break;
			case MODIFICAR_INFORMACION_DEL_ARBOL:
				modificarArbol();
				System.out.println("tercera opcion seleccionada\n");
				break;
			case VISUALIZAR_ARBOLES:
				visualiarArboles();
				break;
			case SALIR:
				System.out.println("ADIOS");
				break;
			default:
				System.out.println("Opcion incorrecta!");
			}
			
		}while (opcion_menu != SALIR);
	
	}

	private static void visualiarArboles( )throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://"+HOST+"/" + BBDD, USERNAME_STRING, PASSWORD_STRING );//abrir conexion			
		Statement st = con.createStatement();//crear el ejecutor de sentencias

		String sentenciaSelect = "SELECT * FROM arboles";
		ResultSet resultado = st.executeQuery(sentenciaSelect);
		ArrayList<Arbol> arboles = new ArrayList<Arbol>();
		while(resultado.next()) {
			Arbol arbol = new Arbol();
			arbol.setId(resultado.getInt("id"));
			arbol.setNombreComun(resultado.getString("nombre_comun"));
			arbol.setNombreCientifico(resultado.getString("nombre_cientifico"));
			arbol.setHabitat(resultado.getString("habitat"));
			arbol.setAltura(resultado.getInt("altura"));
			arbol.setOrigen(resultado.getString("origen"));
			
			arboles.add(arbol);
		}
			for (Arbol arbol : arboles) {
				System.out.println(arbol);
				
			}		
	}
		
	

	private static void modificarArbol() throws SQLException{
		Connection con = DriverManager.getConnection("jdbc:mysql://"+HOST+"/" + BBDD, USERNAME_STRING, PASSWORD_STRING );//abrir conexion			
		Scanner scan = new Scanner(System.in);
		Arbol arbol = new Arbol();

		PreparedStatement preparedSt = con.prepareStatement("UPDATE arboles SET nombre_comun=?, nombre_cientifico=?, habitat=?, altura=?, origen=? WHERE id=? ");
		
		System.out.println("Introduce la id del arbol que quieres modificar");
		arbol.setId(Integer.parseInt(scan.nextLine()));
		System.out.println("Introduce el nuevo nombre comun del arbol");
		arbol.setNombreComun(scan.nextLine());
		System.out.println("Introduce el nuevo nombre cientifico del arbol");
		arbol.setNombreCientifico(scan.nextLine());
		System.out.println("Introduce el nuevo habitat del arbol");
		arbol.setHabitat(scan.nextLine());
		System.out.println("Introduce la nueva altura del arbol");
		arbol.setAltura(Integer.parseInt(scan.nextLine()));
		System.out.println("Introduce el nuevo origen del arbol");
		arbol.setOrigen(scan.nextLine());
				
		preparedSt.setString(1, arbol.getNombreComun());
		preparedSt.setString(2, arbol.getNombreCientifico());
		preparedSt.setString(3, arbol.getHabitat());
		preparedSt.setInt(4, arbol.getAltura());
		preparedSt.setString(5, arbol.getOrigen());
		preparedSt.setInt(6, arbol.getId());
		
		preparedSt.execute();
				
	}

	private static void eliminarArbol() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://"+HOST+"/" + BBDD, USERNAME_STRING, PASSWORD_STRING );//abrir conexion			
		Scanner scan = new Scanner(System.in);
		Arbol arbol = new Arbol();

		PreparedStatement preparedSt = con.prepareStatement("DELETE FROM arboles WHERE id = (?)");
		
		System.out.println("Introduce el id del arbol que quieres eliminar");
		int id = Integer.parseInt(scan.nextLine());
		arbol.setId(id);
		
		preparedSt.setInt(1, arbol.getId());
		preparedSt.execute();

		
	}

	private static void insertarArbol() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://"+HOST+"/" + BBDD, USERNAME_STRING, PASSWORD_STRING );//abrir conexion			
		Scanner scan = new Scanner(System.in);
		Arbol arbol = new Arbol();
		
		System.out.println("Introduce el nombre comun del arbol");
		arbol.setNombreComun(scan.nextLine());
		System.out.println("Introduce el nombre cientifico del arbol");
		arbol.setNombreCientifico(scan.nextLine());
		System.out.println("Introduce el habitat del arbol");
		arbol.setHabitat(scan.nextLine());
		System.out.println("Introduce la altura del arbol");
		arbol.setAltura(Integer.parseInt(scan.nextLine()));
		System.out.println("Introduce el origen del arbol");
		arbol.setOrigen(scan.nextLine());
		
		PreparedStatement preparedSt = con.prepareStatement("INSERT INTO arboles VALUES (null, ?,?,?,?,?)");
		preparedSt.setString(1, arbol.getNombreComun());
		preparedSt.setString(2, arbol.getNombreCientifico());
		preparedSt.setString(3, arbol.getHabitat());
		preparedSt.setInt(4, arbol.getAltura());
		preparedSt.setString(5, arbol.getOrigen());
		
		preparedSt.execute();
		

	}
}
	
		
	
	


