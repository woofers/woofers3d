package com.jaxson.lib.gdx.input;

public class InvalidKeyException extends IllegalArgumentException
{
    private static final long serialVersionUID = -7919938276213778602L;
    private static final String INVALID_KEY = "Keycode %s is invalid";
    private static final String INVALID_NAME = "Key %s is invalid";

    public InvalidKeyException()
    {
        super();
    }

    public InvalidKeyException(int keycode)
    {
        super(String.format(INVALID_KEY, keycode));
    }

    public InvalidKeyException(String name)
    {
        super(String.format(INVALID_NAME, name));
    }
}
