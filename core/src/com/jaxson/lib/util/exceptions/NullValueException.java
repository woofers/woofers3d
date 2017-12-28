package com.jaxson.lib.util.exceptions;

public class NullValueException extends NullPointerException
{
    private static final long serialVersionUID = -4488378721426453814L;
    private static final String CANNOT_BE_NULL = " cannot be null";

    public NullValueException()
    {
        super();
    }

    public NullValueException(String var)
    {
        super(var + CANNOT_BE_NULL);
    }
}
