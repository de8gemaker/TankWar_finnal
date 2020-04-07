package tank;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		TankFrame tf = new TankFrame();

		int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));
		//初始化敌方坦克
		for(int i = 0;i<initTankCount;i++) {
			tf.tanks.add(new Tank(50 + i*80,200,Dir.DOWN,Group.BAD, tf));
		}
//		如何调整声音大小？
//		new Thread(()->new Audio("audio/war1.wav").loop()).start();


		new Thread(()-> {
			while(true) {
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tf.repaint();
			}
		}).start();

	}

}
