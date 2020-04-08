package tank;

public class DefaultFireStrategy implements FireStrategy {

    @Override
    public void fire(Tank t) {
        int bX = t.getX() + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = t.getY() + Tank.HEIGHT/2 - Bullet.HEIGHT/2;

        t.getTf().bullets.add(new Bullet(bX,bY,t.getDir(),t.getGroup(),t.getTf()));
        if(t.getGroup() == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav")).start();
    }
}
