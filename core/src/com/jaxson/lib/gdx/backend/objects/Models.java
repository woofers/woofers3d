package com.jaxson.lib.gdx.backend.objects;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.util.MyArrayList;

public class Models extends ObjectsBase<Entity>
{
    private MyEnvironment environment;

    public Models()
    {
        this(new MyEnvironment());
    }

    public Models(MyEnvironment environment)
    {
        super();
        this.environment = environment;
    }

    public MyEnvironment environment()
    {
        return environment;
    }

    private MyArrayList<ModelInstance> instances()
    {
        MyArrayList<ModelInstance> instances = new MyArrayList<>();
        for (Entity entity: getObjects())
        {
            instances.add(entity.modelInstance());
        }
        return instances;
    }

    @Override
    public void render(View view)
    {
        if (isEmpty()) return;

        view.modelView().apply();
        environment.render(getObjects(), view.modelView().getCamera());
        view.modelBatch().begin(view.modelView().getCamera());
        for (Entity entity: getObjects())
        {
            if (entity.isVisible(view.modelView().getCamera()))
            {
                view.modelBatch().render(
                        entity.modelInstance(), environment);
            }
        }
        view.modelBatch().end();
    }

    public void setEnvironment(MyEnvironment environment)
    {
        this.environment = environment;
    }
}
