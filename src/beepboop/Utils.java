/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beepboop;

/**
 * Some useful methods that will be used through the whole program.
 * @author ahkdel
 */
public class Utils {
    
    /**
     * Calculates the distance of two points.
     * @param x1 X coordinate of point 1.
     * @param y1 Y coordinate of point 1.
     * @param x2 X coordinate of point 2.
     * @param y2 Y coordinate of point 2.
     * @return The distance in double of two points.
     */
    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        double a = Math.pow(y1 - y2, 2);
        double b = Math.pow(x1 - x2, 2);
        return Math.sqrt(a + b);
    }
}
