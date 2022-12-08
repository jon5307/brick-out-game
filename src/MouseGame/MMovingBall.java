package MouseGame;

import game.*;

public class MMovingBall extends MovingBall {
	private boolean wasAtPlayer = false;
	public MMovingBall(int x_initial, int y_initial, int r, Box box, MBrickMap bm, Player p) {
		super(x_initial, y_initial, r, box, bm,p);
	}
	public void move (int time_units) {
		if (container.inHorizontalContact(x_pos))
			x_velocity *= -1;
		if (container.inVerticalContact(y_pos))
			y_velocity *= -1;
		x_pos += x_velocity * time_units;
		y_pos += y_velocity * time_units;

		// 바닥에 닿음
		if (player.isContact(x_pos, y_pos, radius) && !wasAtPlayer) {
			stop();
			wasAtPlayer = true;
		}
		// 바닥에서 떨어짐
		if (x_velocity > 0 || y_velocity > 0) {
			wasAtPlayer = false;
		}

		Brick[][] game_map;
		game_map = game.getMap();

		for (int i = 0; i < game_map.length; i++) {
			for (int j = 0; j < game_map[0].length; j++) {
				if (game_map[i][j].getVisible() == 1
						&& game_map[i][j].isContact(x_pos, y_pos, radius)) {
					game_map[i][j].setVisible(0);
					if (x_pos + radius - 1 <= game_map[i][j].getX() ||
							x_pos + 1 >= game_map[i][j].getX() + game_map[i][j].getWidth()){
						x_velocity *= -1;
					}
					else {
						y_velocity *= -1;
					}
					player.plusScore();
				}
			}
		}

	}
}