package HibernateOrm1;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class Main {

	public static void main(String[] args) {
		//codigo (int), nombre cantante (string), año (int) , album (string)
		Scanner teclado=new Scanner(System.in);
		boolean salida=false;
		
		do {
			System.out.println("1.Alta De Album.\n2.Consulta De Album.\n3.Actualizar Cantante.\n4.Borrar Album.\n5.Consultar Cantantes De Un Genero.\n6.Ver carntantes.\n0.Salir.\n");
			System.out.println("Inserta la opción que desea ejecutar:");
			int menu=teclado.nextInt();
			teclado.nextLine();
			
			switch(menu) {
			 case 1:{
				 crearCantante(teclado);
				 System.out.println("");
				 break;
			 }
			 case 2:{
				 System.out.println("Inserta el nombre del cantante:");
				 mostrarAlbums(buscarCantante(teclado.nextLine()));
				 break;
			 }
			 case 3:{
				 actualizarCantante(teclado);
				 break;
			 }
			 case 4:{
				 borrarCantante(teclado);
				 break;
			 }
			 case 5:{
				 System.out.println("Inserta el genero que deseas consultar:");
				 consultarCantantesGen(teclado.nextLine());
				 break;
			 }
			 case 6:{
				 mostrarCantantes();
				 break;
			 }
			 case 0:{
				 teclado.close();
				 salida=true;
				 System.out.println("Hasta la proximaaaa.....");
				 break;
			 }
			 default:{
				 System.out.printf("La opción %d no existe...\n\n",menu);
			 }
			}
		}while(!salida);
	}
	public static void consultarCantantesGen(String nombre) {
		List<Cantante>cantantes=new ArrayList<>();
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			session.beginTransaction();
			Genero genero=session.createQuery("from Genero where genero= :genero",Genero.class).setParameter("genero", nombre).getSingleResult();
			cantantes.addAll(session.createQuery("from Cantante where genero= :genero",Cantante.class).setParameter("genero", genero).list());

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println();
		for (Cantante i : cantantes) {
			System.out.println(i.toString());
		}
		System.out.println();
	}
	
	public static void mostrarCantantes() {
		List <Cantante>cantantes=new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			
			cantantes.addAll(session.createQuery("from Cantante", Cantante.class).list());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println();
		for (Cantante i : cantantes) {
			System.out.println(i.toString());
		}
		System.out.println();
	}
	
	public static void borrarCantante(Scanner teclado) {
		System.out.println("Inserta el nombre del cantante:");
		String nombre=teclado.nextLine();
			 
		System.out.println("Inserta el album de el cantante:");
		String album=teclado.nextLine();
		
		if(borrarAlbum(nombre,album)) {
			System.out.printf("Se borro el album con nombre %s con exito...\n\n",album);
		}
		else {
			System.out.printf("No se pudo borrar el album con nombre %s...\n\n",album);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static boolean borrarAlbum(String nombre, String album) {				
		boolean resultado=false;
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			
			Cantante cantante=null;
			
			for (Cantante i : buscarCantante(nombre)) {
				if(i.getAlbum().equals(album)) {
					cantante=i;
				}
			}
			
			if(cantante!=null) {
				session.delete(cantante);
				transaction.commit();
				resultado=true;
			}	
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return resultado;
	}
	
	//Actualiza un cantante por su nombre
	public static void actualizarCantante(Scanner teclado) {
		System.out.println("Inserta el nombre del cantante:");
		String nombre=teclado.nextLine();
		
		System.out.println("Inserta el nuevo nombre del cantante:");
		String nuevo=teclado.nextLine();
		
		if(actualizarNombre(nombre,nuevo)) {
			System.out.printf("El cantante %s se ha renombrado como %s.\n\n",nombre,nuevo);
		}
		else {
			System.out.println("No se pudo actualizar el nombre...\n");
		}
	}
	
	//Actualiza el nombre de un cantante
	@SuppressWarnings("deprecation")
	public static boolean actualizarNombre(String nombre, String nuevoNombre) {				
		boolean resultado=false;
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			
			List<Cantante> cantantes=buscarCantante(nombre);
			
			if(cantantes!=null) {
				for (Cantante cantante : cantantes) {
					cantante.setNombre(nuevoNombre);
					session.update(cantante);
				}
				transaction.commit();
				resultado=true;
			}	
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return resultado;
	}
	
	//Muestra los albunes del cantante que le hayamos indicado
	public static void mostrarAlbums(List<Cantante>lista) {
		
		if(!lista.isEmpty()) {
			for(Cantante i:lista) {
				System.out.println(i.toString());
			}
			System.out.println();
		}else {
			System.out.println("No existe dicho cantante...\n");
		}
	}
	
	//Crea un cantante
	public static void crearCantante(Scanner teclado) {
		System.out.println("Inserta el nombre del cantante:");
		String nombre=teclado.nextLine();
		 
		System.out.println("Inserta el año:");
		LocalDate ano=LocalDate.parse(teclado.nextLine());
			 
		System.out.println("Inserta el album de el cantante:");
		String album=teclado.nextLine();
		
		System.out.println("Inserta el genero:");
		String genero=teclado.nextLine();
		
		insertarCantante(nombre,ano,album,genero);
	}
	
	
	//Inserta un cantante
	public static void insertarCantante(String nombre,LocalDate ano, String album,String genero) {	
		Cantante cantante=new Cantante(album,ano,nombre,new Genero());
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			Genero generoExistente = session.createQuery("from Genero where genero = :genero", Genero.class).setParameter("genero", genero).uniqueResult();
			if (generoExistente != null) {
				cantante.setGenero(generoExistente);
				session.persist(cantante);
			} 
			else {
				// Crear un nuevo género si no existe
				Genero nuevoGenero = new Genero(genero);
				session.persist(nuevoGenero);
				cantante.setGenero(nuevoGenero);
				session.persist(cantante);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Busca y devuelve el objeto con el nombre indicado
	public static List<Cantante> buscarCantante(String nombre) {		
		List <Cantante>cantantes=new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			
			cantantes.addAll(session.createQuery("from Cantante where nombre = :nombre", Cantante.class).setParameter("nombre", nombre).list());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cantantes;
	}
}
