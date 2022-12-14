package MouseGame;

import game.*;

public class MMovingBall extends MovingBall {
	private boolean wasAtPlayer = false;
	private MBrickMap mBrickMap;
	public MMovingBall(int x_initial, int y_initial, int r, Box box, MBrickMap bm, Player p) {
		super(x_initial, y_initial, r, box, bm,p);
		mBrickMap = bm;
	}
	public void move (int time_units) {
		// 충돌시 방향 변경
		boxCrash();

		// 공 이동
		x_pos += x_velocity * time_units;
		y_pos += y_velocity * time_units;

		// 공이 수평으로만 움직이지 않도록 함
		if (y_velocity != 0 && Math.abs(y_velocity) <= 0.5) {
			if (y_velocity > 0) {
				y_velocity = 1;
			} else {
				y_velocity = -1;
			}
			x_velocity -= 1;
		}

		System.out.println("xv =" + x_velocity + ", yv = " + y_velocity);
		// 바닥에서 떨어짐
		if (x_velocity > 0 || y_velocity > 0) {
			wasAtPlayer = false;
		}
		// 바닥에 닿음
		if (player.isContact((int) x_pos, (int) y_pos, radius) && !wasAtPlayer) {
			stop();
			y_pos = player.getY() - radius;
			player.plusScore();
			if (player.getScore() >= 5)
				if (mBrickMap.newSetBrick())
					gameOver();
			wasAtPlayer = true;
			((MBrickMap)game).brickDown();
		}

		Brick[][] game_map;
		game_map = game.getMap();
		int x = (int) x_pos;
		int y = (int) y_pos;

		for (int i = 0; i < game_map.length; i++) {
			for (int j = 0; j < game_map[0].length; j++) {
				if (game_map[i][j].getVisible() == 1 && game_map[i][j].isContact((int) x_pos, (int) y_pos, radius)) {
					//game_map[i][j].setVisible(0);
					Brick b = game_map[i][j];
					boolean dummy = b.getBrickHp() <= 1 ? b.setVisible(0) : b.setBrickHp(); // dummy값은 ? : 연산을 위해 생성.
					// 공이 닿았을 때 브릭의 체력이 0이면(== 닿기 전까지 체력이 1 이하) visible을 0으로, 그렇지 않으면 brick_hp -= 1
					if (dummy) {
					}
					brickCrash(b,x,y);
				}
			}
		}

	}
}
