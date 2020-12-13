package time_lapse;

import org.newdawn.slick.Image;

import jig.Entity;
import jig.ResourceManager;

public class ImageManager {
	
	public void setImage(Entity e, String rsc, int rotation, Boolean scale) {
		if(scale) {
			// Create copy of image so that entities already spawned
			// don't change direction
			Image img = ResourceManager.getImage(rsc).getScaledCopy(32, 32).copy();
			img.setRotation(rotation);
			e.addImageWithBoundingBox(img);
		} else {
			Image img = ResourceManager.getImage(rsc).copy();
			img.setRotation(rotation);
			e.addImageWithBoundingBox(img);
		}
	}

}
