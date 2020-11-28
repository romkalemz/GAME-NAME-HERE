package time_lapse;

import org.newdawn.slick.Image;

import jig.Entity;
import jig.ResourceManager;

public class ImageManager {
	
	public void setImage(Entity e, String rsc) {
		
		Image img = ResourceManager.getImage(rsc).getScaledCopy(40, 40);
		e.addImageWithBoundingBox(img);
	}

}
