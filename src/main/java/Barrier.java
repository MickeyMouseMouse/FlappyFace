import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Barrier {
    final private int width = 90;
    final private int height = 533;
    final private Canvas barrier = new Canvas(width, height);

    final private int holeHeight = 130;
    private int holeStartAt;

    final private double speed = 2.5;

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public Canvas getBarrier() { return barrier; }

    public int getHoleHeight() { return holeHeight; }

    public int getHoleStartAt() { return holeStartAt; }

    public double getSpeed() { return speed; }

    public double getLayoutX() { return barrier.getLayoutX(); }

    public void setLayoutX(double x) { barrier.setLayoutX(x); }

    public void update() {
        updateHole();

        GraphicsContext picture = barrier.getGraphicsContext2D();
        int borderWidth = 3;

        // draw black border around the barrier (upper and lower parts)
        picture.setFill(Color.BLACK);
        picture.fillRect(0, 0, width, holeStartAt);
        picture.fillRect(0, holeStartAt + holeHeight,
                width, height - holeHeight - holeStartAt);

        // draw the barrier (upper and lower parts)
        picture.setFill(Color.LIGHTGREEN);
        picture.fillRect(borderWidth, 0, width - 2 * borderWidth,
                holeStartAt - borderWidth);
        picture.fillRect(borderWidth, holeStartAt + holeHeight + borderWidth,
                width - 2 * borderWidth,
                height - holeHeight - holeStartAt);

        // draw the hole in the barrier
        picture.setFill(Color.LIGHTBLUE);
        picture.fillRect(0, holeStartAt, width, holeHeight);
    }

    private void updateHole() {
        int min = 80;
        int max = 270;
        holeStartAt = min + (int) (Math.random() * max);
    }
}