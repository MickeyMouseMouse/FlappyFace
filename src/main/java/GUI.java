import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class GUI extends Application {
	final private FlappyFace fFace = new FlappyFace();
	
	public static void main(String[] args) { Application.launch(args); }
	
	@Override
	public void start(Stage stage) {
        stage.getIcons().add(new Image("/icon.png"));
        stage.setTitle("Flappy Face");
        stage.setHeight(600);
        stage.setWidth(500);
        stage.setResizable(false);
        stage.centerOnScreen();

	    fFace.sky.setLayoutX(0);
        fFace.sky.setLayoutY(0);
        
        fFace.ground.setLayoutX(0);
        fFace.ground.setLayoutY(530);
        
        fFace.face.getFace().setLayoutX(150);
        fFace.face.getFace().setLayoutY(280);

        fFace.playButton.setStyle("-fx-background-radius: 30; -fx-font-size: 25px");
        fFace.helpButton.setStyle("-fx-background-radius: 30; -fx-font-size: 15px");
        fFace.scoreLabel.setStyle("-fx-font-size: 40px");

        GridPane grid = new GridPane(); // Grid Pane for score and buttons

        grid.getColumnConstraints().addAll(new ColumnConstraints(stage.getWidth()));

        grid.getRowConstraints().addAll(new RowConstraints(150));
        GridPane.setRowIndex(fFace.scoreLabel, 0);
        GridPane.setHalignment(fFace.scoreLabel, HPos.CENTER);
        GridPane.setValignment(fFace.scoreLabel, VPos.BOTTOM);

        grid.getRowConstraints().addAll(new RowConstraints(70));
        GridPane.setRowIndex(fFace.playButton, 1);
        GridPane.setHalignment(fFace.playButton, HPos.CENTER);
        GridPane.setValignment(fFace.playButton, VPos.CENTER);

        grid.getRowConstraints().addAll(new RowConstraints());
        GridPane.setRowIndex(fFace.helpButton, 2);
        GridPane.setHalignment(fFace.helpButton, HPos.CENTER);
        GridPane.setValignment(fFace.helpButton, VPos.TOP);

        grid.getChildren().addAll(fFace.scoreLabel, fFace.playButton,
                fFace.helpButton);
        
        final Group root = new Group(fFace.sky, fFace.ground,
        		fFace.barrier1.getBarrier(), fFace.barrier2.getBarrier(),
                fFace.face.getFace(), grid);
        final Scene scene = new Scene(root);
        stage.setScene(scene);
		stage.show();

		fFace.drawSkyAndGround();
		fFace.face.update(true);
        
		fFace.playButton.setOnMouseClicked((e) -> fFace.play());

		fFace.helpButton.setOnMouseClicked((e) -> fFace.showHelp());
		
		// face up by mouse
        scene.setOnMouseClicked((e) -> { 
        	if (!fFace.playButton.isVisible()) fFace.playerClick();
        });
        
        // face up by press any key
        scene.setOnKeyTyped((e) -> {
        	if (!fFace.playButton.isVisible()) fFace.playerClick();
        });
	}
}