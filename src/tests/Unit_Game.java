package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import crown_and_anchor.Dice;
import crown_and_anchor.DiceValue;
import crown_and_anchor.Game;
import crown_and_anchor.Player;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Unit_Game {
	final int INITIAL_BALANCE = 50;
	Player _player;
	Dice _dice1;
	Dice _dice2;
	Dice _dice3;
	Game _game;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private void givenMockDicesCreated(DiceValue dice1Value, DiceValue dice2Value, DiceValue dice3Value)
	{
		_dice1 = mock(Dice.class);
		when(_dice1.getValue()).thenReturn(dice1Value);
		_dice2 = mock(Dice.class);
		when(_dice2.getValue()).thenReturn(dice2Value);
		_dice3 = mock(Dice.class);
		when(_dice3.getValue()).thenReturn(dice3Value);
	}
	private void given3RealDicesCreated()
	{
		_dice1 = new Dice();
		_dice2 = new Dice();
		_dice3 = new Dice();
	}
	private void given3SpyDicesCreated()
	{
		_dice1 = spy(new Dice());
		_dice2 = spy(new Dice());
		_dice3 = spy(new Dice());
	}
	private void givenGameHasBeenCreated()
	{
		_game = new Game(_dice1, _dice2, _dice3);
	}

	private void givenRealPlayer()
	{
		_player = spy(new Player("Test guy", INITIAL_BALANCE));
	}


	@Test
	public void testConstructor() {
		given3RealDicesCreated();
		
		_game = new Game(_dice1, _dice2, _dice3);
		
		assertNotNull(_game);
	}

	@Test
	public void testConstructor_NullDice() {
		given3RealDicesCreated();
		
		exception.expect(IllegalArgumentException.class);
		_game = new Game(_dice1, null, _dice3);
	}
	
	@Test
	public void testGetDiceValues() {
		given3RealDicesCreated();
		givenGameHasBeenCreated();
		DiceValue[] expectedValues = { _dice1.getValue(), _dice2.getValue(), _dice3.getValue() };
		
		List<DiceValue> actual = _game.getDiceValues();
		
		org.junit.Assert.assertArrayEquals(expectedValues, actual.toArray());
	}
	

	@Test
	public void testPlayRound_AllBalance() {
		given3RealDicesCreated();
		givenGameHasBeenCreated();
		givenRealPlayer();
		
		DiceValue pick = DiceValue.ANCHOR;
		int bet = INITIAL_BALANCE;
		
		_game.playRound(_player, pick, bet);
		
		verify(_player, times(1)).takeBet(bet);

	}
	@Test
	public void testPlayRound_OverLimit() {
		given3SpyDicesCreated();
		givenGameHasBeenCreated();
		givenRealPlayer();
		
		DiceValue pick = DiceValue.ANCHOR;
		int bet = INITIAL_BALANCE + 1;
		exception.expect(IllegalArgumentException.class);
		try {
			_game.playRound(_player, pick, bet);
		} catch (IllegalArgumentException exception) {
			assertEquals(INITIAL_BALANCE, _player.getBalance());
			throw exception;
		}
	}
	
	@Test
	public void testPlayRound_NullPlayer() {
		given3SpyDicesCreated();
		givenGameHasBeenCreated();
		givenRealPlayer();
		
		DiceValue pick = DiceValue.ANCHOR;
		int bet = INITIAL_BALANCE + 1;
		exception.expect(IllegalArgumentException.class);
		_game.playRound(null, pick, bet);

	}
	@Test
	public void testPlayRound_NullPick() {
		given3SpyDicesCreated();
		givenGameHasBeenCreated();
		givenRealPlayer();
		
		DiceValue pick = null;
		int bet = INITIAL_BALANCE + 1;
		exception.expect(IllegalArgumentException.class);
		try {
			_game.playRound(_player, pick, bet);
		} catch (IllegalArgumentException exception) {
			verify(_player, times(0)).takeBet(anyInt());
			verify(_player, times(0)).receiveWinnings(anyInt());
			throw exception;
		}

	}
	@Test
	public void testPlayRound_NegativeBet() {
		given3RealDicesCreated();
		givenGameHasBeenCreated();
		givenRealPlayer();
		
		DiceValue pick = DiceValue.ANCHOR;
		int bet = -1;
		exception.expect(IllegalArgumentException.class);
		try {
			_game.playRound(_player, pick, bet);
		} catch (IllegalArgumentException exception) {
			verify(_player, times(0)).takeBet(anyInt());
			verify(_player, times(0)).receiveWinnings(anyInt());
			throw exception;
		}
	}
	
	@Test
	public void testPlayRound_ZeroBet() {
		given3RealDicesCreated();
		givenGameHasBeenCreated();
		givenRealPlayer();
		
		DiceValue pick = DiceValue.ANCHOR;
		int bet = -1;
		exception.expect(IllegalArgumentException.class);
		try {
			_game.playRound(_player, pick, bet);
		} catch (IllegalArgumentException exception) {
			verify(_player, times(0)).takeBet(anyInt());
			verify(_player, times(0)).receiveWinnings(anyInt());
			throw exception;
		}
	}
	@Test
	public void testPlayRound_1Match() {
		givenMockDicesCreated(DiceValue.ANCHOR, DiceValue.CLUB, DiceValue.CROWN);
		givenGameHasBeenCreated();
		givenRealPlayer();
		DiceValue pick = DiceValue.ANCHOR;
		int bet = 50;
		
		_game.playRound(_player, pick, bet);
		
		verify(_player, times(1)).takeBet(bet);
		verify(_player, times(1)).receiveWinnings(bet*2);
	}
	
	@Test
	public void testPlayRound_2Matchs() {
		givenMockDicesCreated(DiceValue.ANCHOR, DiceValue.ANCHOR, DiceValue.CROWN);
		givenGameHasBeenCreated();
		givenRealPlayer();
		DiceValue pick = DiceValue.ANCHOR;
		int bet = 50;
		
		_game.playRound(_player, pick, bet);
		
		verify(_player, times(1)).takeBet(bet);
		verify(_player, times(1)).receiveWinnings(bet*3);
	}
	@Test
	public void testPlayRound_3Matchs() {
		givenMockDicesCreated(DiceValue.ANCHOR, DiceValue.ANCHOR, DiceValue.ANCHOR);
		givenGameHasBeenCreated();
		givenRealPlayer();
		DiceValue pick = DiceValue.ANCHOR;
		int bet = 50;
		
		_game.playRound(_player, pick, bet);
		
		verify(_player, times(1)).takeBet(bet);
		verify(_player, times(1)).receiveWinnings(bet*4);
	}
	@Test
	public void testPlayRound_NoMatchs() {
		givenMockDicesCreated(DiceValue.CROWN, DiceValue.CROWN, DiceValue.CROWN);
		givenGameHasBeenCreated();
		givenRealPlayer();
		DiceValue pick = DiceValue.ANCHOR;
		int bet = 50;
		
		_game.playRound(_player, pick, bet);
		
		verify(_player, times(1)).takeBet(bet);
		verify(_player, times(0)).receiveWinnings(anyInt());
	}
	
	@Test
	public void testPlayRound_Odds() {
		given3RealDicesCreated();
		givenGameHasBeenCreated();
		_player = new Player("Rich test guy", 100000000);
		int wins = 0;
		int rounds = 5000000;
		for(int i = 0; i < rounds; i++)
		{
			DiceValue pick = DiceValue.ANCHOR;
			int winnings = _game.playRound(_player, pick, 1);
			if(winnings > 0)
				wins++;
		}
		assertEquals(0.42d, (double)wins/(double)rounds, 0.01d);
	}
	
}
