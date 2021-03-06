package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import crown_and_anchor.Dice;
import crown_and_anchor.DiceValue;
import crown_and_anchor.Game;
import crown_and_anchor.Player;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Bug1_PaysOutCorrectAmount {

	@Test
	public void testPlayRound_PlayerGetsCorrectWinnings_1Match() {
		DiceValue pick = DiceValue.ANCHOR;
		Dice die1 = getMockDice(pick);
		Dice die2 = getMockDice(DiceValue.CLUB);
		Dice die3 = getMockDice(DiceValue.CLUB);
		
		
		Game game = new Game(die1, die2, die3);
		Player player = new Player("Pete", 100);
		
		game.playRound(player, pick, 10);
		
		assertEquals(110, player.getBalance());
	}

	@Test
	public void testPlayRound_PlayerGetsCorrectWinnings_2Matches() {
		DiceValue pick = DiceValue.ANCHOR;
		Dice die1 = getMockDice(pick);
		Dice die2 = getMockDice(pick);
		Dice die3 = getMockDice(DiceValue.CLUB);
		Game game = new Game(die1, die2, die3);
		Player player = new Player("Pete", 100);
		
		game.playRound(player, pick, 10);
		
		assertEquals(120, player.getBalance());
	}
	
	@Test
	public void testPlayRound_PlayerGetsCorrectWinnings_3Matches() {
		DiceValue pick = DiceValue.ANCHOR;
		Dice die1 = getMockDice(pick);
		Dice die2 = getMockDice(pick);
		Dice die3 = getMockDice(pick);
		
		Game game = new Game(die1, die2, die3);
		Player player = new Player("Pete", 100);
		
		game.playRound(player, pick, 10);
		
		assertEquals(130, player.getBalance());
	}
	
	@Test
	public void testGetBalance() {
		Player player = new Player("Pete", 100);
		assertEquals(100, player.getBalance());
	}
	
	@Test
	public void testTakeBet() {
		Player player = new Player("Pete", 100);
		player.takeBet(10);
		assertEquals(90, player.getBalance());
	}
	
	@Test
	public void testReceiveWinnings() {
		Player player = new Player("Pete", 100);
		player.receiveWinnings(10);
		assertEquals(110, player.getBalance());
	}
	
	@Test
	public void testPlayRound_PlayerGetsCorrectWinnings_NoMatches() {
		DiceValue pick = DiceValue.ANCHOR;
		Dice die1 = getMockDice(DiceValue.CLUB);
		Dice die2 = getMockDice(DiceValue.CLUB);
		Dice die3 = getMockDice(DiceValue.CLUB);
		Game game = new Game(die1, die2, die3);
		Player player = new Player("Pete", 100);
		
		game.playRound(player, pick, 10);
		
		assertEquals(90, player.getBalance());
	}
	
	private static Dice getMockDice(DiceValue value)
	{
		Dice die = mock(Dice.class);
		Mockito.when(die.getValue()).thenReturn(value);
		return die;
	}

}
