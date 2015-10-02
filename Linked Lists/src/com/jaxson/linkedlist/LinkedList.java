package com.jaxson.linkedlist;

public class LinkedList<Type>
{
	private Node<Type> root;

	public LinkedList(Type data)
	{
		root = new Node<Type>(data);
	}

	public void insert(Type data)
	{
		root.insert(data);
	}

	public void insertAfter(Type data, Type insert)
	{
		if (insert == null)
		{
			Node<Type> oldRoot;

			oldRoot = root;
			root = new Node<Type>(data);
			root.next = oldRoot;
			oldRoot.prev = root;
			return;
		}

		root.insertAfter(data, insert);
	}

	public void remove(Type data)
	{
		find(data).remove();
	}

	public Node find(Type data)
	{
		return root.find(data);
	}

	public String toString()
	{
		return root.toString();
	}
}
