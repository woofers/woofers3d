package com.jaxson.linkedlist;

import java.util.Random;

public class LinkedListTester
{
	private static final AMOUNT_MONTHS = 12;

	private LinkedList<String> calender;

	public LinkedListTester()
	{
		String[] months =
		{
			"January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December"
		};

		calender = new LinkedList<String>(months[0]);
		for (int i = 1; i < AMOUNT_MONTHS; i ++)
		{
			calender.insert(months[i]);
		}
		System.out.println(calender.toString());

		calender.remove("July");
		calender.insertAfter("Movember", months[AMOUNT_MONTHS - 2]);
		calender.insertAfter("New Year's Eve", months[AMOUNT_MONTHS - 1]);
		calender.insertAfter("New Year's Day", null);
		System.out.println(calender.toString());
	}

	private int rand(int min, int max)
	{
    	Random random = new Random();
	    return random.nextInt((max - min) + 1) + min;
	}
}
