package net.skylix.elixor.desktop.element.div;

import net.skylix.elixor.desktop.element.Component;
import net.skylix.elixor.desktop.unit.Size;
import net.skylix.elixor.desktop.unit.UnitAdapter;
import net.skylix.elixor.desktop.window.Window;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 * A simple division element, this is a generic element.
 */
public class Div extends Component {
    /**
     * The size of the element.
     */
    private final Size size;

    /**
     * The background color of the element.
     */
    private Color backgroundColor;

    /**
     * The border color of the element.
     */
    private Color borderColor;

    /**
     * The text color of the element.
     */
    private Color textColor;

    /**
     * The parent window.
     */
    private Window window;

    /**
     * The parent element.
     */
    private Component parent;

    /**
     * Clip edges.
     */
    private boolean clip = true;

    /**
     * The shape.
     */
    private Path2D.Float shape;

    /**
     * Create a new division element.
     */
    public Div() {
        size = new Size(0, 0);

        backgroundColor = new Color(0, 0, 0, 0);
        borderColor = new Color(0, 0, 0);
        textColor = new Color(0, 0, 0);
    }

    /**
     * The render method to calculate and paint the element.
     *
     * @param g2d The graphics output.
     * @param window The window.
     * @param parent The parent element.
     */
    @Override
    public void render(Graphics2D g2d, Window window, Component parent) {
        this.parent = parent;
        this.window = window;

        shape = new Path2D.Float();

        size.addListener(new UnitAdapter() {
            @Override
            public void onChange() {
                refresh();
            }
        });

        shape.moveTo(0, 0);
        shape.lineTo(size.getWidth(), 0);
        shape.lineTo(size.getWidth(), size.getHeight());
        shape.lineTo(0, size.getHeight());
        shape.closePath();

        if (parent != null)
            g2d.setClip(0, 0, getParent().getSize().getWidth(), getParent().getSize().getHeight());

        if (getParent() != null) {
            System.out.println("Parent: " + getParent().getClass().getSimpleName() + " Height: " + getParent().getSize().getHeight());
        }

        g2d.setColor(backgroundColor);
        g2d.fill(shape);
    }

    /**
     * Get the parent container.
     *
     * @return The parent container.
     */
    @Override
    public Component getParent() {
        return parent;
    }

    /**
     * Get the parent window.
     *
     * @return The parent window.
     */
    @Override
    public Window getWindow() {
        return window;
    }

    /**
     * Get the size.
     * 
     * @return The size.
     */
    @Override
    public Size getSize() {
        return size;
    }

    /**
     * Get the minimum size.
     */
    @Override
    public Size getMinimumSize() {
        return new Size(0, 0);
    }

    /**
     * Get the maximum size.
     */
    @Override
    public Size getMaximumSize() {
        return size;
    }

    /**
     * Set the background color.
     * 
     * @param backgroundColor The background color.
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        refresh();
    }

    /**
     * Set the border color.
     * 
     * @param borderColor The border color.
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        refresh();
    }

    /**
     * Set the text color.
     * 
     * @param textColor The text color.
     */
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        refresh();
    }

    /**
     * Get the background color.
     * 
     * @return The background color.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Get the border color.
     * 
     * @return The border color.
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Get the text color.
     * 
     * @return The text color.
     */
    public Color getTextColor() {
        return textColor;
    }

    /**
     * Get the shape for the element.
     * 
     * @return The shape.
     */
    @Override
    public Shape getShape() {
        return shape != null ? shape : new Path2D.Float();
    }
}
