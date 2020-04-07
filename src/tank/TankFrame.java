package tank;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


public class TankFrame extends Frame {

    Tank myTank = new Tank(200,400,Dir.DOWN,Group.GOOD,this);
    List<Bullet> bullets = new ArrayList<>();
    //敌方坦克容器
    List<Tank> tanks = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();

    public static final int GAME_WIDTH = 800,GAME_HEIGHT = 600;
    private TankFrame tf = null;
    int x = 200, y = 200;
    Dir dir = Dir.DOWN;

	public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);

        this.addKeyListener((KeyListener) new MyKeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { // bjmashibing/tank
                System.exit(0);
            }
        });
    }

    //利用双缓冲解决游戏画面闪烁问题
	Image offScreenImage = null;

	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	@Override
    public void paint(Graphics g) {

		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("子弹的数量：" + bullets.size(),10,60);
		g.drawString("敌方坦克的数量" + tanks.size(),10,80);
		g.drawString("爆炸的数量：" + explodes.size(),10,100);
		g.setColor(c);

        myTank.paint(g);

		//使用Iterator迭代器不能删除集合中的元素否则会引发ConcurrentModificationException
		//是用最简单的循环遍历方式就可以删除
		//或者在迭代器代码块中删除也是可以的
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        // for(Iterator<Bullet> it = bullets.iterator(); it.hasNext();) {
        // Bullet b = it.next();
        // if(!b.live) it.remove();
        // }

        // for(Bullet b : bullets) {
        // b.paint(g);
        // }
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }


        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
        //collision detect
        for(int i = 0;i<bullets.size();i++) {
            for(int j = 0;j<tanks.size();j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }
        }

//        e.paint(g);

    }

    class MyKeyListener extends KeyAdapter {

        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch (key) {
                case KeyEvent.VK_LEFT: bL = true;
//                    System.out.println("bL = true");
                    break;
                case KeyEvent.VK_UP: bU = true;
//                    System.out.println("bU = true");
                    break;
                case KeyEvent.VK_RIGHT: bR = true;
//                    System.out.println("bR = true");
                    break;
                case KeyEvent.VK_DOWN: bD = true;
//                    System.out.println("bD = true");
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {

                case KeyEvent.VK_LEFT: bL = false;
                    break;
                case KeyEvent.VK_UP: bU = false;
                    break;
                case KeyEvent.VK_RIGHT: bR = false;
                    break;
                case KeyEvent.VK_DOWN: bD = false;
                    break;
                case KeyEvent.VK_CONTROL: myTank.fire();
                    break;

                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {

            if (!bL && !bU && !bR && !bD) {
                myTank.setMoving(false);
            } else {
                myTank.setMoving(true);

                if (bL) myTank.setDir(Dir.LEFT);
//                System.out.println("Dir.LEFT");
                if (bU) myTank.setDir(Dir.UP);
//                System.out.println("Dir.UP");
                if (bR) myTank.setDir(Dir.RIGHT);
//                System.out.println("Dir.RIGHT");
                if (bD) myTank.setDir(Dir.DOWN);
//                System.out.println("Dir.DOWN");
            }
        }
    }
}
