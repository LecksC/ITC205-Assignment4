package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import crown_and_anchor.Dice;
import crown_and_anchor.DiceValue;

public class Unit_Dice {
	Dice dice;
	@Before
	public void setUp() throws Exception {
		dice = new Dice();
	}

	@Test
	public void testDice() {
		assertNotNull(dice);
		assertDiceValueIsValid(dice.getValue());
	}

	@Test
	public void testGetValue() {
		DiceValue expected = dice.roll();
		DiceValue actual = dice.getValue();
		
		assertDiceValueIsValid(actual);
		assertEquals(expected,actual);
	}

	@Test
	public void testRoll() {
		
		int hearts = 0,clubs = 0,spades = 0,diamonds = 0,crowns = 0,anchors = 0;
		int testRolls = 5000000;
		
		for(int i = 0; i < testRolls; i++)
		{
			DiceValue roll = dice.roll();
			switch(roll)
			{
			case ANCHOR:
				anchors++;
				break;
			case CLUB:
				clubs++;
				break;
			case CROWN:
				crowns++;
				break;
			case DIAMOND:
				diamonds++;
				break;
			case HEART:
				hearts++;
				break;
			case SPADE:
				spades++;
				break;
			default:
				break;
			}
		}
		double expected = 1d/6d;
		assertEquals(expected, anchors/(double)testRolls, 0.01d);
		assertEquals(expected, clubs/(double)testRolls, 0.01d);
		assertEquals(expected, crowns/(double)testRolls, 0.01d);
		assertEquals(expected, diamonds/(double)testRolls, 0.01d);
		assertEquals(expected, hearts/(double)testRolls, 0.01d);
		assertEquals(expected, spades/(double)testRolls, 0.01d);
	}

	@Test
	public void testToString() {
		String expected = dice.roll().toString();
		String actual = dice.toString();

		assertEquals(expected,actual);
	}
	
	static void assertDiceValueIsValid(DiceValue value)
	{
		assertNotNull(value);
		assertTrue(value == DiceValue.ANCHOR 
				|| value == DiceValue.CLUB 
				|| value == DiceValue.CROWN 
				|| value == DiceValue.HEART 
				|| value == DiceValue.SPADE
				|| value == DiceValue.DIAMOND);
	}

}
