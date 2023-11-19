package clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class GestionTwitter {
	private static HashMap<String,UsuarioTwitter> mapaUsuariosId = new HashMap<String,UsuarioTwitter>();
	private static HashMap<String,UsuarioTwitter> mapaUsuariosUsername = new HashMap<String,UsuarioTwitter>();
	private static HashMap<UsuarioTwitter,HashSet<UsuarioTwitter>> mapaAmigos = new HashMap<UsuarioTwitter,HashSet<UsuarioTwitter>>();
	//	Para la creacion del set ordenado debo de implementar comparable en UsuarioTwitter
	private static TreeSet<UsuarioTwitter> setOrdenado = new TreeSet<UsuarioTwitter>();
	
	public static HashMap<String, UsuarioTwitter> getMapaUsuariosUsername() {
		return mapaUsuariosUsername;
	}

	public static void setMapaUsuariosUsername(HashMap<String, UsuarioTwitter> mapaUsuariosUsername) {
		GestionTwitter.mapaUsuariosUsername = mapaUsuariosUsername;
	}

	public static void setMapaAmigos(HashMap<UsuarioTwitter, HashSet<UsuarioTwitter>> mapaAmigos) {
		GestionTwitter.mapaAmigos = mapaAmigos;
	}


	public static HashMap<UsuarioTwitter, HashSet<UsuarioTwitter>> getMapaAmigos() {
		return mapaAmigos;
	}
	
	public static TreeSet<UsuarioTwitter> getSetOrdenado() {
		return setOrdenado;
	}

	public static void setSetOrdenado(TreeSet<UsuarioTwitter> setOrdenado) {
		GestionTwitter.setOrdenado = setOrdenado;
	}

	public static HashMap<String, UsuarioTwitter> getMapaUsuariosId() {
		return mapaUsuariosId;
	}

	public static void setMapaUsuariosId(HashMap<String, UsuarioTwitter> mapaUsuarios) {
		GestionTwitter.mapaUsuariosId = mapaUsuarios;
	}
	


	public static void main(String[] args) {
		String fileName = "data2.csv";
		try {
			CSV.processCSV( new File( fileName ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		crearMapaUsuariosUsernameYSetOrdenadoConsola();
		imprimirUsuariosConAmigosEnSistemaConsola();
		imprimirSetOrdenadoConsola();
		

	}
	
	
	
	public static void crearMapaUsuariosUsernameYSetOrdenadoVentana(){
		HashMap<String,UsuarioTwitter> mapa = new HashMap<String,UsuarioTwitter>();
		TreeSet<UsuarioTwitter> set = new TreeSet<UsuarioTwitter>();
		for (String id : mapaUsuariosId.keySet()) { 
			UsuarioTwitter usuario = mapaUsuariosId.get(id);
			contarAmigosEnSistema(usuario);
			if (mapaAmigos.get(usuario).size()>=10) { //Pongo >= porque con solo > no son 4449
				set.add(usuario);
			}
			mapa.put(usuario.getScreenName(),usuario);
			VentanaCargaCSV.incPB();
		}
		
		mapaUsuariosUsername = mapa;
		setOrdenado = set;
		
	};
	
	//Es la misma función que la de arriba pero sin incluir la linea que hace aumentar la progressBar de la ventana
	public static void crearMapaUsuariosUsernameYSetOrdenadoConsola(){
		HashMap<String,UsuarioTwitter> mapa = new HashMap<String,UsuarioTwitter>();
		TreeSet<UsuarioTwitter> set = new TreeSet<UsuarioTwitter>();
		for (String id : mapaUsuariosId.keySet()) { 
			UsuarioTwitter usuario = mapaUsuariosId.get(id);
			contarAmigosEnSistema(usuario);
			if (mapaAmigos.get(usuario).size()>=1) { 
				set.add(usuario);
			}
			mapa.put(usuario.getScreenName(),usuario);
		}
		
		mapaUsuariosUsername = mapa;
		setOrdenado = set;
//		System.out.println("Set ordenado: " + setOrdenado);
	};
	

	
	
//	Metodo para ver amigos en sistema
	public static void imprimirUsuariosConAmigosEnSistemaConsola() {
		int cont = 0;
		ArrayList<String> listaAux = new ArrayList<String>(mapaUsuariosUsername.keySet());
		Collections.sort(listaAux);
		for (String username : listaAux) {
			UsuarioTwitter usuario = mapaUsuariosUsername.get(username);
			int usuariosEnSistema = mapaAmigos.get(usuario).size();
			if (usuariosEnSistema > 0) {
				System.out.println("Usuario " + username + " tiene " + (usuario.getFriendsCount()-usuariosEnSistema) + " amigos fuera de nuestro sistema y "
						+ usuariosEnSistema + " dentro.");
				cont++;
			}
		}
		System.out.println("Hay " + cont  + " usuarios con algunos amigos dentro de nuestro sistema");
	}
	
	public static void imprimirUsuariosConAmigosEnSistemaVentana() {
		int cont = 0;
		ArrayList<String> listaAux = new ArrayList<String>(mapaUsuariosUsername.keySet());
		Collections.sort(listaAux);
		for (String username : listaAux) {
			UsuarioTwitter usuario = mapaUsuariosUsername.get(username);
			int usuariosEnSistema = mapaAmigos.get(usuario).size();
			if (usuariosEnSistema > 0) {
				VentanaCargaCSV.printInTA("Usuario " + username + " tiene " + (usuario.getFriendsCount()-usuariosEnSistema) + " amigos fuera de nuestro sistema y "
						+ usuariosEnSistema + " dentro.");

				cont++;
			}
		}
		VentanaCargaCSV.printInTA("Hay " + cont  + " usuarios con algunos amigos dentro de nuestro sistema");
	}
	
	public static void contarAmigosEnSistema(UsuarioTwitter usuario) {
		mapaAmigos.put(usuario, new HashSet<UsuarioTwitter>());
		for (String id : usuario.getFriends()) {
			if(!(mapaUsuariosId.get(id)==null)) {
				mapaAmigos.get(usuario).add(mapaUsuariosId.get(id));
			}
		};
	}
	
	
//	Metodo para crear el Set Ordenado, pero lo he implementado en la funcion crearMapaUsuariosUsernameYSetOrdenado para que la ejecución sea más rápida
	public static void crearSetOrdenado() {
		TreeSet<UsuarioTwitter> set = new TreeSet<UsuarioTwitter>();
		for (String id : mapaUsuariosId.keySet()) {
			UsuarioTwitter usuario = mapaUsuariosId.get(id);
			if (getMapaAmigos().get(usuario).size()>0) {
				set.add(usuario);
			}
		}
		GestionTwitter.setOrdenado = set;
	}
	
	public static void imprimirSetOrdenadoConsola() {
		for (UsuarioTwitter usuario : setOrdenado) {
			System.out.println(usuario.getScreenName() + " - " + getMapaAmigos().get(usuario).size() + " amigos.");
		}
	}
	
	public static void imprimirSetOrdenadoVentana() {
		for (UsuarioTwitter usuario : setOrdenado) {
			VentanaCargaCSV.printInTA(usuario.getScreenName() + " - " + getMapaAmigos().get(usuario).size() + " amigos.");
		}
	}
}
