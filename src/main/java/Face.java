import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Face {
    final private int size = 50; // face: 50x50 px
    final private int xCoorL = 150; // x-coordinate Of Left Corners Of face

    final private double speedDown = 3;
    final private double speedUp = 4.5;
    final private double minFaceY = -15;
    final private double maxFaceY = 480;

    final private Canvas face = new Canvas(size,  size);

    public int getSize() { return size; }

    public int getXCoorL() { return xCoorL; }

    public int getXCoorR() { return xCoorL + size; }

    public double getSpeedDown() { return speedDown; }

    public double getSpeedUp() { return speedUp; }

    public double getMinFaceY() { return minFaceY; }

    public double getMaxFaceY() { return maxFaceY; }

    public Canvas getFace() { return face; }

    public double getLayoutY() { return face.getLayoutY(); }

    public void setLayoutY(double y) { face.setLayoutY(y); }

    // emotion = true (smile face) / false (angry face)
    public void update(boolean emotion) {
        GraphicsContext picture = face.getGraphicsContext2D();

        picture.setStroke(Color.BLACK);
        picture.setLineWidth(3.0);
        picture.strokeRect(0, 0, 50, 50); // draw border around the Face

        picture.setFill(Color.LIGHTSEAGREEN);
        picture.fillRect(2, 2, 46, 46); // draw square

        picture.setFill(Color.YELLOW);
        picture.fillOval(3, 5, 20, 20); // draw left eye
        picture.fillOval(27, 5, 20, 20); // draw right eye

        picture.setFill(Color.BLACK);
        picture.fillOval(9, 11, 8, 8); // draw left pupil
        picture.fillOval(34, 11, 8, 8); // draw right pupil

        picture.setFill(Color.DARKRED);
        if (emotion)
            // draw smile
            picture.fillPolygon(new double[] {5.0, 20.0, 30.0, 45.0, 25.0},
                    new double[] {30.0, 35.0, 35.0, 30.0, 45.0}, 5);
        else
            // draw anger (open mouth)
            picture.fillOval(13, 24, 22, 22);
    }
}