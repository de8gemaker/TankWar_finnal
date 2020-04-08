package tank;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Tank {
    private static final int SPEED = 2;
	public static final int WIDTH = ResourceMgr.goodtankD.getWidth();
	public static final int HEIGHT = ResourceMgr.goodtankD.getHeight();
	private int x,y;

    private boolean moving = true;

    private TankFrame tf = null;
    private Dir dir = Dir.DOWN;
	private boolean living = true;
	private Random random = new Random();
	Rectangle rect = new Rectangle();

	private Group group = Group.BAD;
    FireStrategy fs;

	//------------------------------------------------------------------------------------
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public boolean isMoving() {
        return moving;
    }
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isLiving() {
        return living;
    }
    public void setLiving(boolean living) {
        this.living = living;
    }

    public Dir getDir() {
        return dir;
    }
    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public TankFrame getTf() { return tf; }

    public Tank(int x, int y, Dir dir, Group group , TankFrame tf) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        //将FireStrategyGood的load到内存
        if(group == Group.GOOD) {
            String goodFsName = (String) PropertyMgr.get("goodFs");
            try {
                fs = (FireStrategy) Class.forName(goodFsName).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            fs = new DefaultFireStrategy();
        }
    }
    //----------------------------------------------------------------------------------

    public void paint(Graphics g) {
        if(!living) tf.tanks.remove(this);

        switch (dir) {
            case LEFT: g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodtankL : ResourceMgr.badtankL,x,y,null);
                break;
            case UP: g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodtankU : ResourceMgr.badtankU,x,y,null);
                break;
            case RIGHT: g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodtankR : ResourceMgr.badtankR,x,y,null);
                break;
            case DOWN: g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodtankD : ResourceMgr.badtankD,x,y,null);
                break;
        }

        move();
    }

    private void move() {
        if (!moving) return;
        else
        {
            switch (dir) {
                case LEFT:
                    x -= SPEED;
                    break;
                case UP:
                    y -= SPEED;
                    break;
                case RIGHT:
                    x += SPEED;
                    break;
                case DOWN:
                    y += SPEED;
                    break;
            }
            if(this.group == Group.BAD && random.nextInt(100) > 95)
                this.fire();

            if(this.group == Group.BAD && random.nextInt(100) > 95)
                randomDir();

            boundsCheck();
            //update rect
            rect.x = this.x;
            rect.y = this.y;
        }

    }

    private void boundsCheck() {
        //不要让坦克太靠边留出一个像素的余地
        if(this.x < 1) x= 1;
        if(this.y < 29) y = 29;
        if(this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 1) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 1;
        if(this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 1) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT -1;
    }

    private void randomDir() {

        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        fs.fire(this);
//        int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
//        int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
//
//        tf.bullets.add( new Bullet(bX,bY,this.dir,this.group,this.tf));
//        if(this.group == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav")).start();
    }

    public void die() {
        this.living = false;
    }
}
