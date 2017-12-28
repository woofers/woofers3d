package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.jaxson.lib.util.Printer;

public class ScrollWheel extends MouseButton
{
    private int amount;

    ScrollWheel(Input input)
    {
        super(MIDDLE, input);
    }

    public int amountScrolled()
    {
        return amount;
    }

    public boolean isScrolledDown()
    {
        return amount < 0;
    }

    public boolean isScrolledUp()
    {
        return amount > 0;
    }

    public boolean isStill()
    {
        return isScrolledUp() && isScrolledDown();
    }

    void setScrollAmount(int amount)
    {
        this.amount = amount;
    }

    @Override
    public String toString()
    {
        return new Printer(getClass(),
                new Printer.Label("Name", name()),
                new Printer.Label("Button", button())).toString();
    }

    public void update(float dt)
    {
        if (isScrolledUp()) amount --;
        if (isScrolledDown()) amount ++;
    }
}
