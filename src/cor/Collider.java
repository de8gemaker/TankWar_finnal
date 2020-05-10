package cor;

import tank.GameObject;

public interface Collider {
	boolean collide(GameObject o1, GameObject o2);
}
