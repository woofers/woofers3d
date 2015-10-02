package com.jaxson.linkedlist;

public class Node<Type>
{
	public Type data;
	protected Node prev;
	protected Node next;

	public Node(Type data)
	{
		this.data = data;
	}

	protected void insert(Type data)
	{
		if (isLast())
		{
			next = new Node<Type>(data);
			next.prev = this;
			return;
		}
		next.insert(data);
	}

	protected void insertAfter(Type data, Type insert)
	{
		Node<Type> left, middle, right;

		middle = new Node<Type>(data);
		left   = find(insert);

		right       = left.next;
		left.next   = middle;
		middle.prev = left;
		middle.next = right;
		if (right != null)
		{
			right.prev  = middle;
		}
	}

	protected void remove()
	{
		if (!isRoot())
		{
			prev.next = next;
		}
		if (!isLast())
		{
			next.prev = prev;
		}
		prev = null;
		next = null;
	}

	protected Node find(Type data)
	{
		if (this.data.equals(data))
		{
			return this;
		}
		if (isLast())
		{
			return null;
		}
		return next.find(data);
	}

	public String toString()
	{
		String text = "";
		text = text + data;
		if (!isLast())
		{
			text = text + ", " + next.toString();
		}
		return text;
	}

	private boolean isLast()
	{
		return next == null;
	}

	private boolean isRoot()
	{
		return prev == null;
	}
}
