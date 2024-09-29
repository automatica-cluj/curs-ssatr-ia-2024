package utcluj.isp.curs3.oop.abstract2;

abstract class Shape {
    private String color;

    public Shape(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract double getArea();
}

class Circle extends Shape {
    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getArea() {
        return width * height;
    }
}

class Drawing {
    private Shape[] shapes;

    public Drawing(Shape[] shapes) {
        this.shapes = shapes;
    }

    public double calculateTotalArea() {
        double totalArea = 0.0;
        for (Shape shape : shapes) {
            totalArea += shape.getArea();
        }
        return totalArea;
    }

    public void printShapes() {
        for (Shape shape : shapes) {
            System.out.println(shape.getClass().getSimpleName() + " - " + shape.getColor());
        }
    }
}

class AbstractClassExample {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[2];
        shapes[0] = new Circle("Red", 5);
        shapes[1] = new Rectangle("Blue", 10, 20);

        Drawing drawing = new Drawing(shapes);
        drawing.printShapes();
        System.out.println("Total area: " + drawing.calculateTotalArea());
    }
}

