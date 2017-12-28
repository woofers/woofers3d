package com.jaxson.lib.gdx.input;

import java.util.Iterator;
import com.badlogic.gdx.Input;
import com.jaxson.lib.util.MyArrayList;

public class Keys extends Input.Keys implements Key, Iterable<Key>
{
    private static final String ANY_KEY_NAME = "Any Key";
    public static final int MIN = ANY_KEY;
    public static final int MAX = 256;

    public static String toString(int keycode)
    {
        if (keycode == ANY_KEY) return ANY_KEY_NAME;
        String name = Input.Keys.toString(keycode);
        if (name == null) return new Integer(keycode).toString();
        return name;
    }

    private MyArrayList<Key> keys;

    public Keys()
    {
        this((Key) null);
    }

    public Keys(Key... keys)
    {
        this.keys = new MyArrayList<>(keys);
    }

    public void add(Key... keys)
    {
        keys().addAll(keys);
    }

    public void add(Keys... keys)
    {
        for (Keys keyList: keys)
        {
            keys().addAll(keyList.keys());
        }
    }

    @Override
    public boolean isDown()
    {
        for (Key key: keys())
        {
            if (key.isDown()) return true;
        }
        return false;
    }

    @Override
    public boolean isPressed()
    {
        for (Key key: keys())
        {
            if (key.isPressed()) return true;
        }
        return false;
    }

    @Override
    public boolean isReleased()
    {
        for (Key key: keys())
        {
            if (key.isReleased()) return true;
        }
        return false;
    }

    @Override
    public Iterator<Key> iterator()
    {
        return keys().iterator();
    }

    private MyArrayList<Key> keys()
    {
        return keys;
    }
}
