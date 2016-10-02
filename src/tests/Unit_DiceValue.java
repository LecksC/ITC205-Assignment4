package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import crown_and_anchor.DiceValue;

public class Unit_DiceValue {

	@Test
	public void testGetRandom() {
		
		int hearts = 0,clubs = 0,spades = 0,diamonds = 0,crowns = 0,anchors = 0;
		int testRolls = 5000000;
		
		for(int i = 0; i < testRolls; i++)
		{
			DiceValue roll = DiceValue.getRandom();
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

}
