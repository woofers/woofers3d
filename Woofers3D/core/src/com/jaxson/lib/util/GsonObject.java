package com.jaxson.lib.util;

import com.google.gson.Gson;

public abstract class GsonObject<T>
{
	private transient Class<T> type;

	public GsonObject(Class<T> type)
	{
		this.type = type;
	}

	public T fromJson(String json)
	{
		return fromJson(json, type);
	}

	public T fromJson(String json, Class<T> type)
	{
		return new Gson().fromJson(json, type);
	}

	public String toJson()
	{
		return new Gson().toJson(this);
	}
}
