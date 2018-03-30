package com.jaxson.lib.gdx.box2d;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.jaxson.lib.gdx.box2d.simulation.Box2DWorld;
import com.jaxson.lib.util.MyArrayList;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Box2DTiledMap
{
    private TiledMap map;
    private Box2DWorld world;
    private MyArrayList<Body> bodies;

    public Box2DTiledMap(TiledMap map, Box2DWorld world)
    {
        this(map, world, "Collision");
    }

    public Box2DTiledMap(TiledMap map, Box2DWorld world, String collisionLayer)
    {
        this.map = map;
        this.world = world;
        this.bodies = new MyArrayList<Body>();
        buildShapes(collisionLayer);
    }

    public TiledMap map()
    {
        return map;
    }

    public Box2DWorld world()
    {
        return world;
    }

    public MyArrayList<Body> bodies()
    {
        return bodies;
    }

    /**
     * Adapted from daemonaka's and David Saltares' Solution
     * @see <a href="https://gamedev.stackexchange.com/questions/66924/how-can-i-convert-a-tilemap-to-a-box2d-world"></a>
     * @see <a href="https://github.com/saltares/sioncore"></a>
     */
    private void buildShapes(String collisionLayer)
    {
        MapObjects objects = map.getLayers().get(collisionLayer).getObjects();

        for (MapObject object: objects)
        {
            if (object instanceof TextureMapObject)
            {
                continue;
            }

            Shape shape;

            if (object instanceof RectangleMapObject)
            {
                shape = getRectangle((RectangleMapObject)object);
            }
            else if (object instanceof PolygonMapObject)
            {
                shape = getPolygon((PolygonMapObject)object);
            }
            else if (object instanceof PolylineMapObject)
            {
                shape = getPolyline((PolylineMapObject)object);
            }
            else if (object instanceof CircleMapObject)
            {
                shape = getCircle((CircleMapObject)object);
            }
            else
            {
                continue;
            }

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.StaticBody;
            Body body = world.createBody(bodyDef);
            body.createFixture(shape, 1);
            bodies.add(body);
            shape.dispose();
        }
    }


    private PolygonShape getRectangle(RectangleMapObject rectangleObject)
    {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / world.metersToPixels(),
                                   (rectangle.y + rectangle.height * 0.5f ) / world.metersToPixels());
        polygon.setAsBox(rectangle.width * 0.5f / world.metersToPixels(),
                         rectangle.height * 0.5f / world.metersToPixels(),
                         size,
                         0.0f);
        return polygon;
    }

    private CircleShape getCircle(CircleMapObject circleObject)
    {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / world.metersToPixels());
        circleShape.setPosition(new Vector2(circle.x / world.metersToPixels(), circle.y / world.metersToPixels()));
        return circleShape;
    }

    private PolygonShape getPolygon(PolygonMapObject polygonObject)
    {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; i ++)
        {
            System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / world.metersToPixels();
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private ChainShape getPolyline(PolylineMapObject polylineObject)
    {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i ++)
        {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / world.metersToPixels();
            worldVertices[i].y = vertices[i * 2 + 1] / world.metersToPixels();
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
}
