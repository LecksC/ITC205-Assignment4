package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import crown_and_anchor.Player;

public class Bug2_CannotReachBettingLimit {
	
	@Test
	public void testBalanceExceedsLimitBy_SomeOfBalance() {
		Player player = new Player("Pete", 5);
		boolean result = player.balanceExceedsLimitBy(4);
		assertEquals(true, result);
	}
	
	@Test
	public void testBalanceExceedsLimitBy_AllOfBalance() {
		Player player = new Player("Pete", 5);
		boolean result = player.balanceExceedsLimitBy(5);
		assertEquals(true, result);
	}
	
	@Test
	public void testBalanceExceedsLimitBy_MoreThanBalance() {
		Player player = new Player("Pete", 5);
		boolean result = player.balanceExceedsLimitBy(6);
		assertEquals(false, result);
	}
}
