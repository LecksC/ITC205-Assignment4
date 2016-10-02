package tests;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import crown_and_anchor.Player;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Unit_Player {
	final String PLAYER_NAME = "Test guy";
	final int INITIAL_BALANCE = 100;
	final int TEST_LIMIT = 100;
	final int TEST_BET = 20;
	
	Player _player;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	void givenPlayerHasBeenCreated(int money)
	{
		_player = new Player(PLAYER_NAME, money);
	}

	void givenPlayerHasBeenCreated()
	{
		_player = new Player(PLAYER_NAME, INITIAL_BALANCE);
	}
	@Test
	public void testConstructor() {
		_player = new Player(PLAYER_NAME, INITIAL_BALANCE);
		
		assertEquals(PLAYER_NAME, _player.getName());
		assertEquals(INITIAL_BALANCE, _player.getBalance());
	}

	@Test
	public void testConstructor_NullName() {
		exception.expect(IllegalArgumentException.class);
		_player = new Player(null, INITIAL_BALANCE);
	}
	@Test
	public void testConstructor_EmptyName() {
		exception.expect(IllegalArgumentException.class);
		_player = new Player("", INITIAL_BALANCE);
	}
	
	@Test
	public void testConstructor_NegativeBalance() {
		exception.expect(IllegalArgumentException.class);
		_player = new Player(PLAYER_NAME, -1);
	}
	@Test
	public void testGetName() {
		givenPlayerHasBeenCreated();
		assertEquals(PLAYER_NAME, _player.getName());
	}

	@Test
	public void testGetBalance() {
		givenPlayerHasBeenCreated();
		assertEquals(INITIAL_BALANCE, _player.getBalance());
	}

	@Test
	public void testGetLimit() {
		givenPlayerHasBeenCreated();
		_player.setLimit(TEST_LIMIT);
		
		int actual = _player.getLimit();
		
		assertEquals(TEST_LIMIT, actual);
	}

	@Test
	public void testSetLimit() {
		givenPlayerHasBeenCreated();
		
		_player.setLimit(TEST_LIMIT);
		
		assertEquals(TEST_LIMIT, _player.getLimit());
	}

	@Test
	public void testSetLimit_NegativeLimit() {
		givenPlayerHasBeenCreated();
		_player.setLimit(TEST_LIMIT);
		
		exception.expect(IllegalArgumentException.class);
		try {
			_player.setLimit(-1);
		} catch (IllegalArgumentException exception) {
			assertEquals(TEST_LIMIT, _player.getLimit());
			throw exception;
		}
	}
	
	@Test
	public void testSetLimit_MoreThanBalance() {
		givenPlayerHasBeenCreated();
		_player.setLimit(TEST_LIMIT);
		
		exception.expect(IllegalArgumentException.class);
		try {
			_player.setLimit(-1);
		} catch (IllegalArgumentException exception) {
			assertEquals(TEST_LIMIT, _player.getLimit());
			throw exception;
		}
	}
	@Test
	public void testBalanceExceedsLimit() {
		givenPlayerHasBeenCreated();
		boolean actual = _player.balanceExceedsLimit();
		
		assertEquals(true, actual);
	}

	@Test
	public void testBalanceExceedsLimit_AtLimit() {
		givenPlayerHasBeenCreated();
		_player.setLimit(INITIAL_BALANCE);
		
		boolean actual = _player.balanceExceedsLimit();
		
		//See notes in journal.
		assertEquals(false, actual);
	}
	
	@Test
	public void testBalanceExceedsLimitBy() {
		givenPlayerHasBeenCreated();
		
		boolean actual = _player.balanceExceedsLimitBy(0);
		
		assertEquals(true, actual);
	}
	
	@Test
	public void testBalanceExceedsLimitBy_AtLimit() {
		givenPlayerHasBeenCreated();
		
		boolean actual = _player.balanceExceedsLimitBy(INITIAL_BALANCE);
		
		//See notes in journal.
		assertEquals(true, actual);
	}
	
	@Test
	public void testTakeBet() {
		givenPlayerHasBeenCreated();
		
		_player.takeBet(TEST_BET);
		
		assertEquals(INITIAL_BALANCE-TEST_BET, _player.getBalance());
	}
	
	@Test
	public void testTakeBet_NegativeBet() {
		givenPlayerHasBeenCreated();
		
		exception.expect(IllegalArgumentException.class);
		try {
			_player.takeBet(-TEST_BET);
		} catch (IllegalArgumentException exception) {
			assertEquals(INITIAL_BALANCE, _player.getBalance());
			throw exception;
		}
	}

	@Test
	public void testTakeBet_OverLimit() {
		givenPlayerHasBeenCreated();
		
		exception.expect(IllegalArgumentException.class);
		try {
			_player.takeBet(INITIAL_BALANCE+1);
		} catch (IllegalArgumentException exception) {
			assertEquals(INITIAL_BALANCE, _player.getBalance());
			throw exception;
		}
	}
	
	@Test
	public void testTakeBet_OnLimit() {
		givenPlayerHasBeenCreated();
		
		_player.takeBet(INITIAL_BALANCE);

		assertEquals(0, _player.getBalance());
	}
	
	@Test
	public void testReceiveWinnings() {
		givenPlayerHasBeenCreated();
		
		_player.receiveWinnings(TEST_BET);

		assertEquals(INITIAL_BALANCE+TEST_BET, _player.getBalance());
	}
	
	@Test
	public void testReceiveWinnings_NegativeWinnings() {
		givenPlayerHasBeenCreated();
		
		exception.expect(IllegalArgumentException.class);
		try {
			_player.receiveWinnings(-TEST_BET);
		} catch (IllegalArgumentException exception) {
			assertEquals(INITIAL_BALANCE, _player.getBalance());
			throw exception;
		}
	}
}
