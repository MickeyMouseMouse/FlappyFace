import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class FlappyFace {
	final Canvas sky = new Canvas(500, 530);
	final Canvas ground = new Canvas(500, 48);

	final Button playButton = new Button("Play");
	final Button helpButton = new Button("Help");
	final Text scoreLabel = new Text("0");

	final Face face = new Face();
	
	final Barrier barrier1 = new Barrier();
	final Barrier barrier2 = new Barrier();

	// true (barrier1 before face), false (barrier2 before face)
	private boolean orderOfBarriers = true;
	
	private int score = 0;
	
	private double upToY; // face up to this y-coordinate after player click
	final private double barrierInterval = 350;

	public void drawSkyAndGround() {
		GraphicsContext picture = sky.getGraphicsContext2D();
		
		picture.setFill(Color.LIGHTBLUE);
		picture.fillRect(0, 0, 500, 530); // draw blue sky

		picture = ground.getGraphicsContext2D();

		picture.setFill(Color.BLACK);
		picture.fillRect(0, 0, 500, 3); // draw black border
		picture.setFill(Color.LIGHTGREEN);
		picture.fillRect(0, 3, 500, 45); // draw green ground
	}

	public void play() {
		score = 0;
		scoreLabel.setText("0");

		playButton.setVisible(false);
		helpButton.setVisible(false);

		face.getFace().setLayoutY(280); // default position
		face.update(true);

		barrier1.setLayoutX(545); // default position
		barrier1.update();

		barrier2.setLayoutX(895); // default position
		barrier2.update();

		orderOfBarriers = true;

		timerBarriers.start();
		timerFaceDown.start();
	}

	// movement of barriers
	private AnimationTimer timerBarriers = new AnimationTimer() {
		@Override
		public void handle(long now) {
			makeBarriersLeft();
		}
	};	
	
	// movement of face down
	private AnimationTimer timerFaceDown = new AnimationTimer() {
		@Override
		public void handle(long now) {
			makeFaceDown();
		}
	};
	
	// for mouse or keys event
	public void playerClick() {
		// uplift face on 50 px after player command
		upToY = face.getFace().getLayoutY() - 50;
		timerFaceDown.stop();
		timerFaceUp.start();		
	}

	// movement of face up
	private AnimationTimer timerFaceUp = new AnimationTimer() {
		@Override
		public void handle(long now) {
			makeFaceUp();

			if (face.getFace().getLayoutY() <= upToY) {
				timerFaceDown.start();
				timerFaceUp.stop();
			}
		}
	};

	// for AnimationTimer
	private void makeFaceUp() {
		face.setLayoutY(face.getLayoutY() - face.getSpeedUp());
		if (face.getLayoutY() <= face.getMinFaceY() || crash()) gameOver();
	}	
	
	// for AnimationTimer
	private void makeFaceDown() {
		face.setLayoutY(face.getLayoutY() + face.getSpeedDown());
		if (face.getLayoutY() >= face.getMaxFaceY() || crash()) gameOver();
	}
	
	// for AnimationTimer
	private void makeBarriersLeft() {
		// set the barrier1 in default position and redraw
		if (barrier1.getLayoutX() <= -barrier1.getWidth()) {
			barrier1.setLayoutX(barrier2.getLayoutX() + barrierInterval);
			barrier1.update();
		}

		barrier1.setLayoutX(barrier1.getLayoutX() - barrier1.getSpeed());

		// set the barrier2 in default position and redraw
		if (barrier2.getLayoutX() <= -barrier1.getWidth()) {
			barrier2.setLayoutX(barrier1.getLayoutX() + barrierInterval);
			barrier2.update();
		}

		barrier2.setLayoutX(barrier2.getLayoutX() - barrier2.getSpeed());
		
		updateScore();
		if (crash()) gameOver();
	}	
	
	// crash = collision of face with the barrier/ground/top
	private boolean crash() {
		double barrierX1;
		double barrierY1;
		double barrierX2;
		double barrierY3;

		/*				|
		 | 				|
		X1,Y1---------X2,Y2
			-> face ->
		X4,Y4---------X3,Y3
		|				|
		|				*/
		if (orderOfBarriers) {
			barrierX1 = barrier1.getLayoutX();
			barrierY1 = barrier1.getHoleStartAt();
			barrierX2 = barrierX1 + barrier1.getWidth();
			barrierY3 = barrier1.getHoleStartAt() + barrier1.getHoleHeight();
		} else {
			barrierX1 = barrier2.getLayoutX();
			barrierY1 = barrier2.getHoleStartAt();
			barrierX2 = barrierX1 + barrier2.getWidth();
			barrierY3 = barrier2.getHoleStartAt() + barrier2.getHoleHeight();
		}
		
		// necessary conditions for crash
		return ( ((face.getXCoorR() > barrierX1 && face.getXCoorR() < barrierX2) ||
			  (face.getXCoorL() > barrierX1 && face.getXCoorL() < barrierX2)) &&
			 ((face.getFace().getLayoutY() < barrierY1) ||
			  (face.getFace().getLayoutY() + face.getSize() > barrierY3)) );
	}

	private void updateScore() {
		if (orderOfBarriers) {
			if (face.getXCoorL() >= barrier1.getLayoutX() + barrier1.getWidth()) {
				score += 1;
				scoreLabel.setText(((Integer)score).toString());
				orderOfBarriers = false;
			}
		} else
			if (face.getXCoorL() >= barrier2.getLayoutX() + barrier1.getWidth()) {
				score += 1;
				scoreLabel.setText(((Integer)score).toString());
				orderOfBarriers = true;
			}
	}

	private void gameOver() {
		timerBarriers.stop();
		timerFaceDown.stop();
		timerFaceUp.stop();
		
		playButton.setVisible(true);
		helpButton.setVisible(true);
		face.update(false);
	}

	public void showHelp() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("Help");
		alert.setContentText("Press any key to make the Face fly higher.\n" +
				"Help him overcome as many obstacles as possible.");
		alert.getDialogPane().setStyle("-fx-font-size: 14px");
		alert.showAndWait();
	}
}