package com.jaxson.lib.util;

public class Printer
{
	public static class Label
	{
		private static final String SEPERATOR = ": %s";
		private static final String EMPTY = "Empty";

		private String text;
		private Object value;

		public Label()
		{
			this(null);
		}

		public Label(Object value)
		{
			this(null, value);
		}

		public Label(String text, Object value)
		{
			this.text = text;
			this.value = value;
		}

		@Override
		public String toString()
		{
			Object newValue = value;
			if (newValue == null) newValue = EMPTY;
			if (text == null || text.isEmpty()) return newValue.toString();
			return text + String.format(SEPERATOR, newValue);
		}
	}

	private static final String SEPERATOR = ", ";
	private static final String NESTER = "[%s]";

	private Class<?> type;
	private Label[] labels;

	public Printer(Class<?> type)
	{
		this(type, (Label)null);
	}

	public Printer(Class<?> type, Label... labels)
	{
		this.type = type;
		this.labels = labels;
	}

	public String objectName()
	{
		return type.getSimpleName();
	}

	@Override
	public String toString()
	{
		String nester = objectName() + NESTER;
		String inner = "";
		for (Label pair: labels)
			inner += pair.toString() + SEPERATOR;
		inner = inner.substring(0, inner.length() - 2);
		return String.format(nester, inner);
	}
}
