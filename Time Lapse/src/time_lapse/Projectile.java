package time_lapse;

import jig.Entity;
import jig.Vector;

public class Projectile extends Entity {

	private float speed;
	private int damage;
	private float force;
	private Vector velocity;
	
	public float getSpeed() 			{ return speed; }
	public void setSpeed(float f) 		{ speed = f; }
	public int getDamage() 				{ return damage; }
	public void setDamage(int d) 		{ damage = d; }
	public void setForce(float f) 		{ force = f; }
	public float getForce() 			{ return force; }
	public Vector getVelocity() 		{ return velocity; }
	public void setVelocity(Vector v) 	{ velocity = v; }
	
	public Projectile(final float x, final float y) {
		super(x, y);
		velocity = new Vector(0, 0);
		speed = 0.22f;
		damage = 1;
		force = 1;
		
	}
	public void setDirection(Entity e, Vector v) {
		setPosition(e.getPosition());
		setVelocity(v);
//		image.setRotation((int) v.getRotation() + 90);
	}
	
	public void update(final int delta) {
		translate(velocity.scale(delta*speed));
	}

}
