package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import crown_and_anchor.Dice;
import crown_and_anchor.DiceValue;
import crown_and_anchor.Game;
import crown_and_anchor.Player;

public class Bug3_IncorrectOdds {

	
	@Test
	public void test_playRound_Odds() {
		Player player = new Player("Pete", 1000000);
		Game game = new Game(new Dice(), new Dice(), new Dice());
		int wins = 0;
		int rounds = 50000;
		for(int i = 0; i < rounds; i++)
		{
			int winnings = game.playRound(player, DiceValue.getRandom(), 1);
			if(winnings > 0)
				wins++;
		}
		assertEquals(0.42d, (double)wins/(double)rounds, 0.001d);
	}

}
