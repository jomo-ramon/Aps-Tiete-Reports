package ChatClient.teste;

import ChatClient.model.*;
import ChatClient.controller.*;
import javafx.application.Application;
import javafx.stage.Stage;
import ChatClient.view.*;
import java.security.NoSuchAlgorithmException;

public class TieteReports extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		View view = new View();
		ViewController vControl = new ViewController(view, new Controller(new ChatClient("localhost", 8818)));
		view.load(primaryStage, vControl);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		launch(args);
	}


}
