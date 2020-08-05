package ChatClient.view;

import ChatClient.controller.ViewController;
import ChatClient.model.Chat;
import ChatClient.model.Message;
import ChatClient.model.Usuario;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View {
	
	private UsrPane current;
	private Scene chatScene;
	private Scene cadastroScene;
	private Stage primaryStage;
	ArrayList<Usuario> usuarios = new ArrayList<>();
	ViewController fxController;
	
	public UsrPane getCurrentChat() {
		return this.current;
	}
	
	public void setCurrent(UsrPane s) {
		this.current = s;
	}
	
	public void load(Stage primStage, ViewController fxController) throws IOException {
		this.fxController = fxController;
		FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/IndexLogin.fxml"));
		loginLoader.setController(fxController);
		AnchorPane lPane = loginLoader.load();
		chatScene = new Scene(lPane, 700, 400);
		primaryStage = primStage;
		primaryStage.setScene(chatScene);
		
		FXMLLoader cadastroLoader = new FXMLLoader(getClass().getResource("/Cdastro.fxml"));
		cadastroLoader.setController(fxController);
		Parent cPane = cadastroLoader.load();
		cadastroScene = new Scene(cPane, 800, 500);
		primaryStage.show();
	}
	
	
	public void showChat() throws IOException {
		FXMLLoader chatLoader = new FXMLLoader(getClass().getResource("/Chat.fxml"));
		chatLoader.setController(fxController);
		AnchorPane lPane = chatLoader.load();
		chatScene = new Scene(lPane, 800, 600);
		primaryStage.setScene(chatScene);
	}
	
	public void showLogin(ViewController fxController) throws IOException {
		primaryStage.setScene(chatScene);
	}
	
	public void showCadastro(ViewController fxController) throws IOException {
		primaryStage.setScene(cadastroScene);
	}


	public void showMessages(UsrPane pn, ScrollPane chatBox) {
		VBox p = new VBox(); 
		p.setPrefWidth(400);
		p.setSpacing(9);
		Label l;
		for(Message msg : pn.getChat().getMessages()) {
			l = new Label(msg.toString());
			p.getChildren().add(l);
		}
		Platform.runLater(() ->  {
			chatBox.setContent(p);
        });
	}
}
