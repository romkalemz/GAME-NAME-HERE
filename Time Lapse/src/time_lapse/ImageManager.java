package time_lapse;

import org.newdawn.slick.Image;

import jig.Entity;
import jig.ResourceManager;

public class ImageManager {
	
	public void setImage(Entity e, String rsc, int rotation, Boolean scale) {
		if(scale) {
			// Create copy of image so that entities already spawned
			// don't change direction
			Image img = ResourceManager.getImage(rsc).getScaledCopy(40, 40).copy();
			img.setRotation(rotation);
			e.addImageWithBoundingBox(img);
		} else {
			Image img = ResourceManager.getImage(rsc).copy();
			img.setRotation(rotation);
			e.addImageWithBoundingBox(img);
		}
	}
	public void RemoveIMG(Entity e, String rsc) {
			Image img = ResourceManager.getImage(rsc);
			e.removeImage(img);
	}
}
