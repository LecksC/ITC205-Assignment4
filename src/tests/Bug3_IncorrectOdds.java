package tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import crown_and_anchor.Dice;
import crown_and_anchor.DiceValue;
import crown_and_anchor.Game;
import crown_and_anchor.Player;

public class Bug3_IncorrectOdds {

	
	@Test
	public void test_playRound_Odds() {
		Random random = new Random(999);
		Player player = new Player("Pete", 100000000);
		Game game = new Game(new Dice(), new Dice(), new Dice());
		int wins = 0;
		int rounds = 500000;
		
		for(int i = 0; i < rounds; i++)
		{
			DiceValue pick = DiceValue.values()[random.nextInt(6)];
			int winnings = game.playRound(player, pick, 1);
			if(winnings > 0)
				wins++;
		}
		assertEquals(0.42d, (double)wins/(double)rounds, 0.001d);
	}

}
