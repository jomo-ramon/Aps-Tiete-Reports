package ChatClient.controller;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import ChatClient.controller.Controller;
import ChatClient.model.Message;
import ChatClient.model.Usuario;
import ChatClient.view.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.event.*;

public class ViewController implements Initializable {

private ArrayList<UsrPane> usrPane;
private Controller mainController;
private View view;
private String sender;

@FXML Button btLogin;
@FXML Button btCadastro;
@FXML TextField raField;
@FXML PasswordField pass;
@FXML Label label;

@FXML TextField userName;
@FXML TextField userRa;
@FXML TextField userCpf;
@FXML TextField userCargo;
@FXML TextField userEmail;
@FXML TextField userIndustria;
@FXML TextField userPass;

@FXML Label nameTag;
@FXML FlowPane flwPane;
@FXML TextArea msgBox;
@FXML Button sendBtn;
@FXML ScrollPane chatBox;
	
	public ViewController(View view, Controller aController) {
		this.view = view;
		this.mainController = aController;
		mainController.setViewCon(this);
	}
	
	@FXML
	public void handleLogin(ActionEvent e) throws ClassNotFoundException, IOException {
		String[] token = new String[2];
		token[0] = raField.getText();
		token[1] = pass.getText();
		if(mainController.handleLogin(token)) {
			view.showChat();
			ArrayList<Usuario> usrs = mainController.getOnline();
			onlineUsers(usrs);
			sender = token[0];
		}
	}
	
	@FXML
	public void btCadastroEvent(ActionEvent e) throws IOException {
		view.showCadastro(this);
	}
	
	@FXML
	public void btVoltarCadastroEvent(ActionEvent e) throws IOException {
		view.showLogin(this);
	}
	
	@FXML
	public void btCadastrarEvent(ActionEvent e) throws IOException, NoSuchAlgorithmException {
		String[] userData = new String[7];
		userData[0] = userName.getText();
		userData[1] = userCpf.getText();
		userData[2] = userRa.getText();
		userData[3] = userCargo.getText();
		userData[4] = userEmail.getText();
		userData[5] = userIndustria.getText();
		userData[6] = userPass.getText();
		mainController.handleCadastro(userData);
	}
	
	public void addMessage(String[] msg) {
		for(UsrPane p : usrPane) {
			System.out.println("msg recebida no viewCOnt: " + msg[0] + " " + msg[1] + " " + msg[2]);
			if(p.getMembers().getRa().equalsIgnoreCase(msg[2])) {
				p.addMsg(new Message(msg[0], msg[1], msg[2]));
				p.setStyle("-fx-background-color: #e3e3e3;");
				System.out.println(((view.getCurrentChat()) != null) 
					&& (msg[2].equalsIgnoreCase(view.getCurrentChat().getMembers().getRa())));
				
				if (((view.getCurrentChat()) != null) 
					&& (msg[2].equalsIgnoreCase(view.getCurrentChat().getMembers().getRa()))) {
					view.showMessages(p, chatBox);
				}
			}
		}
	}
	
	public void sendMsgEvent (ActionEvent e)throws IOException{
		if  (msgBox.getText() != null) {
			String currentSendTo = view.getCurrentChat().getMembers().getRa();
			String[] msg = {msgBox.getText(), currentSendTo, sender};
			msgBox.setText("");
			for(UsrPane p : usrPane) {
				if(p.getMembers().getRa().equalsIgnoreCase(msg[1])) {
					p.addMsg(new Message(msg[0], msg[1], msg[2]));
					p.setStyle("-fx-background-color: #e3e3e3;");
					view.showMessages(p, chatBox);
				}
			}
			mainController.sendMsg(msg);
		}
	}
	
	public void onlineUsers(ArrayList<Usuario> onUsers) {
		System.out.println("Dentro do view COntroller");
		for(Usuario s : onUsers) {
			System.out.println(s.toString());
		}
		usrPane = new ArrayList<>();
		System.out.println("iterando sobre onUsers");
		for(Usuario usr : onUsers) {
			Label nome = new Label();
			Label cargo = new Label();
			Label cidade = new Label();
			nome.setText(usr.getNome()+"   ");
			cargo.setText(usr.getCargo()+ "   ");
			cidade.setText(usr.getIndustria());
			UsrPane pn = new UsrPane(usr);
			pn.setPrefSize(270, 50);
			String style = "-fx-background-color: #ffffff;";
			pn.setStyle(style);
			pn.getChildren().add(nome);
		    pn.getChildren().add(cargo);
		    pn.getChildren().add(cidade);
			usrPane.add(pn);
		}
		for(UsrPane pn : usrPane) {
			pn.setOnMouseReleased(mouseEvent -> {
				view.setCurrent(pn);
				view.showMessages(pn,chatBox);
				nameTag.setText(pn.getMembers().getNome());
			});
			flwPane.getChildren().add(pn);
			Separator sp = new Separator();
			sp.setLayoutX(5);
			sp.setStyle(new String("-fx-background-color: #e3e3e3;"));
			sp.setPrefWidth(250);
			flwPane.getChildren().add(sp);
		}
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("up and running \n");
		
	}
}
