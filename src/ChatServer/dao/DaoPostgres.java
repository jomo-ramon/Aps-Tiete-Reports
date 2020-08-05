package ChatServer.dao;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ChatServer.dao.*;
import ChatClient.model.Usuario;

public class DaoPostgres implements Dao{
	
	public ArrayList<Usuario> getUsers(){
		
		ArrayList<Usuario> usuario = new ArrayList<>();
		try(Connection c = MyConnection.getConnection()){
			String query = "SELECT *"
						+ " FROM inspetores";
			PreparedStatement pstm = c.prepareStatement(query);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				usuario.add(new Usuario(rs.getString("nome"), rs.getString("cpf"), rs.getString("ra"), rs.getString("cargo"), rs.getString("email"),rs.getString("industria")));
			}
		} 
		catch(SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}
	 
	public Usuario searchUsuario(String token[]){
		
		Usuario usuario = null;
		try(Connection c = MyConnection.getConnection()){
			String query = "SELECT *"
						+ " FROM inspetores"
						+ " WHERE LOWER(ra) LIKE LOWER(?)";
			PreparedStatement pstm = c.prepareStatement(query);
			pstm.setString(1,"%"+token[0]+"%");
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				usuario = new Usuario(rs.getString("nome"), rs.getString("cpf"), rs.getString("ra"), rs.getString("cargo"), rs.getString("email"),rs.getString("industria"));
			}
		} 
		catch(SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	public boolean validateLogin(String[] token) {		
		boolean isCorrect = false;
		DataDecod dataCrypt = new DataDecod();
		
		try(Connection c = MyConnection.getConnection()){
			String query = "SELECT ra, hashpass, salt"
							+" FROM inspetores"
							+" WHERE LOWER (ra) like LOWER(?)";
			
			PreparedStatement pstm = c.prepareStatement(query);
			pstm.setString(1,"%"+token[0]+"%");
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()) {
				System.out.println("senha entrada:  " + dataCrypt.generateHash(token[1],rs.getBytes("salt"))); 
				System.out.println("senha d banco:  " + rs.getString("hashpass") + "\n"); 
				isCorrect = ((rs.getString("ra").equalsIgnoreCase(token[0])) 
						    && (rs.getString("hashpass").equals(dataCrypt.generateHash(token[1],rs.getBytes("salt"))))); 
			}
			
		} catch(SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return isCorrect;
	}
	
	public boolean criaUsuario(Usuario usr, String pass, byte[] salt) {
		boolean rs = true;
		try(Connection c = MyConnection.getConnection()){
			String query = "INSERT INTO"
						+ " inspetores"
						+ " VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement pstm = c.prepareStatement(query);
			pstm.setString(1,usr.getNome());
			pstm.setString(2,usr.getCpf());
			pstm.setString(3,usr.getRa());
			pstm.setString(4,usr.getCargo());
			pstm.setString(5,usr.getEmail());
			pstm.setString(6,usr.getIndustria());
			pstm.setString(7, pass);
			pstm.setBytes(8, salt);
			 rs = pstm.executeUpdate()>0 ? true:false;
			return rs;
			
		} 
		catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
