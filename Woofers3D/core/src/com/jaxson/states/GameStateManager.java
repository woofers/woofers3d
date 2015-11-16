package com.jaxson.states;

import com.jaxson.util.MyArrayList;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.PerspectiveCamera;

import java.util.Stack;

public class GameStateManager
{
    private Stack<State3D> states;

    public GameStateManager()
    {
        states = new Stack<State3D>();
    }

    public boolean isEmpty()
    {
        return states.isEmpty();
    }

    public void push(State3D state)
    {
        states.push(state);
    }

    public void pop()
    {
        states.pop().dispose();
    }

    public void popAll()
    {
        while (!isEmpty())
        {
            pop();
        }
    }

    public void set(State3D state)
    {
        pop();
        states.push(state);
    }

    public void update(float dt)
    {
        states.peek().update(dt);
    }

    public void render(ModelBatch modelBatch){
        states.peek().render(modelBatch);
    }
}