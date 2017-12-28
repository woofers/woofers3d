package com.jaxson.lib.gdx.input;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import com.badlogic.gdx.Input;

public class Keyboard extends Peripheral implements Iterable<KeyboardKey>
{
    private HashMap<Integer, KeyboardKey> keycodeKeys;
    private HashMap<String, KeyboardKey> stringKeys;
    private TouchKeyboard touchKeyboard;

    Keyboard(Input input)
    {
        super(input);
        this.touchKeyboard = new TouchKeyboard(input());
        this.keycodeKeys = new HashMap<>();
        this.stringKeys = new HashMap<>();
        for (int keycode = Keys.MIN; keycode < Keys.MAX; keycode ++)
        {
            KeyboardKey key = new KeyboardKey(keycode);
            keycodeKeys.put(key.keycode(), key);
            stringKeys.put(key.name().toLowerCase(), key);
        }
    }

    @Override
    public boolean exists()
    {
        return input().isPeripheralAvailable(
                Input.Peripheral.HardwareKeyboard);
    }

    @Override
    public Iterator<KeyboardKey> iterator()
    {
        return keys().iterator();
    }

    public KeyboardKey key(int keycode)
    {
        KeyboardKey key = keycodeKeys.get(keycode);
        if (key == null) throw new InvalidKeyException(keycode);
        return key;
    }

    public KeyboardKey key(String name)
    {
        KeyboardKey key = stringKeys.get(name.toLowerCase().trim());
        if (key == null) throw new InvalidKeyException(name);
        return key;
    }

    private Collection<KeyboardKey> keys()
    {
        return keycodeKeys.values();
    }

    void reset()
    {
        for (KeyboardKey key: keys())
        {
            key.setDown(false);
            key.setWasDown(false);
        }
    }

    public TouchKeyboard touchKeyboard()
    {
        return touchKeyboard;
    }

    void transfer()
    {
        for (KeyboardKey key: keys())
        {
            key.transfer();
        }
    }
}
