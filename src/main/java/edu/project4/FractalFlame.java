package edu.project4;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

@SuppressWarnings("MagicNumber")

public class FractalFlame {

    private final int height = 1080;
    private final int width = 1920;
    private final double minX = -1.777;
    private final double maxX = 1.777;
    private final double minY = -1.;
    private final double maxY = 1.;
    private final int samples;
    private final int countOfThreads;
    private final int samplesPerThread;
    private final int iterationsPerSample;
    private final int someSkippedIterations = -20;
    private final ExecutorService executorService;
    private final ReentrantLock lock = new ReentrantLock();
    private final boolean symmetry;
    private final double gamma;
    private final Pixel[][] image;

    public FractalFlame(
        int samples,
        int iterationsPerSample,
        int countOfThreads,
        boolean symmetry,
        double gamma
    ) {

        image = new Pixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image[i][j] = new Pixel();
            }
        }
        this.samples = samples;
        this.iterationsPerSample = iterationsPerSample;
        this.countOfThreads = countOfThreads;
        this.samplesPerThread = samples / countOfThreads;
        this.executorService = Executors.newFixedThreadPool(countOfThreads);
        this.symmetry = symmetry;
        this.gamma = gamma;
    }

    public Pixel[][] getImage() {
        return image;
    }

    public void render() {
        var tasks = Stream.generate(() -> CompletableFuture.runAsync(
            this::renderPerThread,
            executorService
        )).limit(countOfThreads).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(tasks).join();
    }

    public void gammaCorrection() {
        double max = 0.0;
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                if (image[row][col].getCountHit() != 0) {
                    image[row][col].setNormal(Math.log10(image[row][col].getCountHit()));
                    if (image[row][col].getNormal() > max) {
                        max = image[row][col].getNormal();
                    }
                }
            }
        }
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                image[row][col].setNormal(image[row][col].getNormal() / max);
                image[row][col].setColor(new Colour(
                    (int) (image[row][col].getColor().getRed() * Math.pow(image[row][col].getNormal(), (1.0 / gamma))),
                    (int) (image[row][col].getColor().getGreen()
                        * Math.pow(image[row][col].getNormal(), (1.0 / gamma))),
                    (int) (image[row][col].getColor().getBlue() * Math.pow(image[row][col].getNormal(), (1.0 / gamma)))
                ));
            }
        }
    }

    private void renderPerThread() {
        for (int i = 0; i < samplesPerThread; i++) {
            double newX = ThreadLocalRandom.current().nextDouble(minX, maxX);
            double newY = ThreadLocalRandom.current().nextDouble(minY, maxY);
            for (int j = someSkippedIterations; j < iterationsPerSample; j++) {
                Function function = Functions.getFunction();
                Point point = transform(newX, newY, function, j);
                newX = point.x();
                newY = point.y();
                if (j >= 0 && isPointInRange(point)) {
                    point = findLocation(point);
                    if (isPointInDisplay(point)) {
                        try {
                            lock.lock();
                            int x = (int) point.x();
                            int y = (int) point.y();
                            image[x][y].incrementCountHit();
                            image[x][y].setColor(new Colour(
                                (image[x][y].getColor().getRed() + function.rgb().getRed()) / 2,
                                (image[x][y].getColor().getGreen() + function.rgb().getGreen()) / 2,
                                (image[x][y].getColor().getBlue() + function.rgb().getBlue()) / 2
                            ));
                        } finally {
                            lock.unlock();
                        }
                    }

                }
            }
        }
    }

    private Point transform(double x, double y, Function function, int numberOfIter) {
        //Афинное преобразование
        double finalX = function.coefficients().a() * x + function.coefficients().b() * y
            + function.coefficients().c();
        double finalY = function.coefficients().d() * x + function.coefficients().e() * y
            + function.coefficients().f();

        //Нелинейное преобразование
        List<Fractal> fractals = function.fractals();
        Point point = new Point(finalX, finalY);
        for (Fractal fractal : fractals) {
            point = fractal.apply(function.coefficients(), point);
        }
        finalX = point.x();
        finalY = point.y();
        // Поддержка симметрии
        if (symmetry) {
            if (numberOfIter % 4 == 0) {
                finalX *= -1;
            } else if (numberOfIter % 3 == 0) {
                finalX *= -1;
                finalY *= -1;
            } else if (numberOfIter % 2 == 0) {
                finalY *= -1;
            }
        }
        return new Point(finalX, finalY);
    }

    private boolean isPointInRange(Point point) {
        return minX <= point.x() && point.x() <= maxX
            && minY <= point.y() && point.y() <= maxY;
    }

    private Point findLocation(Point point) {
        return new Point(
            (point.x() - minX) / (maxX - minX) * width,
            (point.y() - minY) / (maxY - minY) * height
        );
    }

    private boolean isPointInDisplay(Point point) {
        return 0 <= point.x() && point.x() <= width
            && 0 <= point.y() && point.y() <= height;
    }

}
