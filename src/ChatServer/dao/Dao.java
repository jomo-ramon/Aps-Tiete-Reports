package ChatServer.dao;

import java.util.ArrayList;

import ChatClient.model.Usuario;

public interface Dao {
		ArrayList<Usuario> getUsers();
		Usuario searchUsuario(String token[]);
		boolean criaUsuario(Usuario usr, String pass, byte[] salt);
		boolean validateLogin(String[] token);
	
}
