package net.mp3skater.main.elements;

import net.mp3skater.main.GamePanel;

public abstract class Obj_moving extends Obj {
    public Obj_moving(double x, double y, int sX, int sY, double vX, double vY) {
        super(x, y, sX, sY);
        this.vec = new double[]{vX, vY};
    }

    // Movement Vector of the <Obj>
    protected final double[] vec;

    // Collision booleans
    private boolean xColl = false, yColl = false;

    /*
    Updates the position according to the collisions
     */
    public void update() {
        // Reset Collision booleans
        resetCollisions();
        // Check if there are collisions, if so then the corresponding booleans are turned true
        collisionBool();
        // Move in the directions where there is no collision
        move();
    }

    /*
    Ok well this one is easy, come on
     */
    private void resetCollisions() {
        xColl = false;
        yColl = false;
    }

    /*
    Activate x-/yCollision if there is a collision with another <Obj>
     */
    private void collisionBool() {
        // For all elements you could collide with
        for(Obj o : GamePanel.objs) {
            // Test weather going vertically, horizontally or both would make you collide with something
            // Horizontally
            if(collides((int)(o.getX()-vec[0]), (int)o.getY(), (int)o.getSX(), (int)o.getSY())) {
                xCollision();
            }
            // Vertically
            if(collides((int)o.getX(), (int)(o.getY()-vec[1]), (int)o.getSX(), (int)o.getSY())) {
                yCollision();
            }
            // Both
            if(collides((int)(o.getX()-vec[0]), (int)(o.getY()-vec[1]), (int)o.getSX(), (int)o.getSY())) {
                xCollision();
                yCollision();
            }
        }
    }

    /*
    Activate collision boolean and resets the <vec[]> in that direction
     */
    private void xCollision() {
        xColl = true;
        vec[0] = 0;
    }
    private void yCollision() {
        yColl = true;
        vec[1] = 0;
    }

    /*
    Finally makes the Object move (change the position)
     */
    private void move() {
        pos[0] += xColl? 0 : vec[0];
        pos[1] += yColl? 0 : vec[1];
    }

    /*
    Adds a vector to the current <vec[]>
    If value bigger than <max_speed> replace with the maximal speed in that direction
     */
    protected void addVec(double x, double y) {
        final int[] max_speed = new int[]{8, 15};
        vec[0] = vec[0]+x< max_speed[0] && vec[0]+x>-max_speed[0] ? vec[0]+x : vec[0]+x>0? max_speed[0] : -max_speed[0];
        vec[1] = vec[1]+y< max_speed[1] && vec[1]+y>-max_speed[1] ? vec[1]+y : vec[1]+y>0? max_speed[1] : -max_speed[1];
    }
}