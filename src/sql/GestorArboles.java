package sql;
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
		while(resultado.next()) {
			System.out.println(resultado.getInt(1) + " - " + resultado.getString(2) + " - " + resultado.getString(3)+ " - " + resultado.getString(4)+ " - " + resultado.getInt(5)+ " - " + resultado.getString(6));
		}

		
	}

	private static void modificarArbol() throws SQLException{
		Connection con = DriverManager.getConnection("jdbc:mysql://"+HOST+"/" + BBDD, USERNAME_STRING, PASSWORD_STRING );//abrir conexion			
		Scanner scan = new Scanner(System.in);
		
		PreparedStatement preparedSt = con.prepareStatement("UPDATE arboles SET nombre_comun=?, nombre_cientifico=?, habitat=?, altura=?, origen=? WHERE id=? ");
		
		System.out.println("Introduce la id del arbol que quieres modificar");
		int id = Integer.parseInt(scan.nextLine());
		preparedSt.setInt(6, id);
		System.out.println("Introduce el nuevo nombre comun del arbol");
		String NombreComun = scan.nextLine();
		preparedSt.setString(1, NombreComun);
		System.out.println("Introduce el nuevo nombre cientifico del arbol");
		String NombreCientifico = scan.nextLine();
		preparedSt.setString(2, NombreCientifico);
		System.out.println("Introduce el nuevo habitat del arbol");
		String HabitatArbol = scan.nextLine();
		preparedSt.setString(3, HabitatArbol);
		System.out.println("Introduce la nueva altura del arbol");
		int AlturaArbol = Integer.parseInt(scan.nextLine());
		preparedSt.setInt(4, AlturaArbol);
		System.out.println("Introduce el nuevo origen del arbol");
		String OrigenArbol = scan.nextLine();
		preparedSt.setString(5, OrigenArbol);

		preparedSt.execute();
		
	}

	private static void eliminarArbol() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://"+HOST+"/" + BBDD, USERNAME_STRING, PASSWORD_STRING );//abrir conexion			
		Scanner scan = new Scanner(System.in);
		
		PreparedStatement preparedSt = con.prepareStatement("DELETE FROM arboles WHERE id = (?)");
		
		System.out.println("Introduce el id del arbol que quieres eliminar");
		int id = Integer.parseInt(scan.nextLine());
		preparedSt.setInt(1, id);
		preparedSt.execute();

		
	}

	private static void insertarArbol() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://"+HOST+"/" + BBDD, USERNAME_STRING, PASSWORD_STRING );//abrir conexion			
		Scanner scan = new Scanner(System.in);
		
		PreparedStatement preparedSt = con.prepareStatement("INSERT INTO animales (nombre) VALUES (null, ?, ?, ?, ?, ?)");

		System.out.println("Introduce el nombre comun del arbol");
		String nombreComun = scan.nextLine();
		preparedSt.setString(1, nombreComun);
		System.out.println("Introduce el nombre cientifico del arbol");
		String nombreCientifico = scan.nextLine();
		preparedSt.setString(2, nombreCientifico);
		System.out.println("Introduce el habitat del arbol");
		String habitatArbol = scan.nextLine();
		preparedSt.setString(3, habitatArbol);
		System.out.println("Introduce la altura del arbol");
		int alturaArbol = Integer.parseInt(scan.nextLine());
		preparedSt.setInt(4, alturaArbol);
		System.out.println("Introduce el origen del arbol");
		String origenArbol = scan.nextLine();
		preparedSt.setString(5, origenArbol);
		
		preparedSt.execute();
	}
}
	
		
	
	


