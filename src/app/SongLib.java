	//Yifan Zhang, yz745
//Yifei Li, yl1160

package app;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;


import javafx.stage.Stage;
import view.SonglibController;

public class SongLib extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/songlib.fxml"));  // an
		SplitPane root = (SplitPane)loader.load();

		SonglibController songlibController = 
				loader.getController();
		songlibController.start(primaryStage); 

		Scene scene = new Scene(root, 500, 300);   //这里决定窗口的大小
		primaryStage.setResizable(false);
		primaryStage.setTitle("Song Library");
		primaryStage.setScene(scene);
		primaryStage.show(); 

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
