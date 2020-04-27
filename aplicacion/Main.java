package aplicacion;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/vista/ElTiempo.fxml"));
			
			// Cargar la ventana
			Pane ventana = (Pane) loader.load();
			
			// Cargar la Scene
			Scene scene = new Scene(ventana);
			
			// Asignar propiedades al Stage
			primaryStage.setTitle("El Tiempo 1daw");
			primaryStage.setResizable(false);	
			
			// Asignar la scene y mostrar
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
