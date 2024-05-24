package net.mp3skater.main.obj;

import net.mp3skater.main.GamePanel;

public abstract class Obj_moving extends Obj {
    /*
    Superclass for all moving objects, like the player
     */

    public Obj_moving(double x, double y, int sX, int sY, double vX, double vY, double mX, double mY) {
        super(x, y, sX, sY, false);
        this.max_speed = new double[]{mX, mY};
        this.vec = new double[]{vX, vY};
    }

    // The max. value of <vec> to ensure more "realistic movement"
    final double[] max_speed;

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
    protected void collisionBool() {
        // For all elements you could collide with
        for(Obj o : GamePanel.walls) {
            // Test weather going vertically, horizontally or both would make you collide with something
            // Horizontally
            if(collides((int)(o.getX()-vec[0]), (int)o.getY(), (int)o.getSX(), (int)o.getSY())) {
                xCollision((int)o.getX(), vec[0]<0/*going up*/? o.size[0] : 0);
            }
            // Vertically
            if(collides((int)o.getX(), (int)(o.getY()-vec[1]), (int)o.getSX(), (int)o.getSY())) {
                yCollision((int)o.getY(), vec[1]<0/*going left*/? o.size[1] : 0);
            }
            // Both
            if(collides((int)(o.getX()-vec[0]), (int)(o.getY()-vec[1]), (int)o.getSX(), (int)o.getSY())) {
                xCollision((int)o.getX(), vec[0]<0/*going up*/? o.size[0] : 0);
                yCollision((int)o.getY(), vec[1]<0/*going left*/? o.size[1] : 0);
            }
        }
    }

    /*
    Activate collision boolean and reset the <vec[]> in that direction
    If <colSize> is 0, the player is colliding from up or left respectively
     */
    protected void xCollision(double collisionX, int colSize) {
        xColl = true;
        vec[0] = 0;
        pos[0] = colSize==0? collisionX-size[0] : collisionX+colSize;
    }
    protected void yCollision(double collisionY, int colSize) {
        yColl = true;
        vec[1] = 0;
        pos[1] = colSize==0? collisionY-size[1] : collisionY+colSize;
    }

    /*
    Finally makes the Object move (change the position)
     */
    protected void move() {
        pos[0] += xColl? 0 : vec[0];
        pos[1] += yColl? 0 : vec[1];
    }

    /*
    Adds a vector to the current <vec[]>
    If value bigger than <max_speed> replace with the maximal speed in that direction
     */
    protected void addVec(double x, double y) {
        vec[0] = vec[0]+x< max_speed[0] && vec[0]+x>-max_speed[0] ? vec[0]+x : vec[0]+x>0? max_speed[0] : -max_speed[0];
        vec[1] = vec[1]+y< max_speed[1] && vec[1]+y>-max_speed[1] ? vec[1]+y : vec[1]+y>0? max_speed[1] : -max_speed[1];
    }
}