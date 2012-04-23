package com.game;

import com.game.graphic.util.VisualGraph;
import com.game.roadnetwork.Direction;
import com.game.roadnetwork.GeographicCrossWay;
import com.game.roadnetwork.Road;
import com.game.roadnetwork.Way;
import com.game.util.RoadGraph;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class App extends BaseExample {
    private final static Log LOG = LogFactory.getLog(App.class);
    private static final int CAMERA_WIDTH = (int) (720 * 1);
    private static final int CAMERA_HEIGHT = (int) (480 * 1);
    private Camera mCamera;

    @Override
    public Engine onLoadEngine() {
        this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
    }

    @Override
    public void onLoadResources() {

    }

    @Override
    public Scene onLoadScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());
        final Scene scene = new Scene();
        scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));


      /*
        GeographicCrossWay crossWay = new GeographicCrossWay(4, 0, 0, 0, 50);
        crossWay.attachRoad(0, new Road(new Way(Direction.FORWARD), new Way(Direction.FORWARD)), false);
        crossWay.attachRoad(1, new Road(new Way(Direction.FORWARD), new Way(Direction.FORWARD)), true);
        crossWay.attachRoad(2, new Road(new Way(Direction.FORWARD), new Way(Direction.FORWARD)), true);

        VisualGraph visualGraph = new VisualGraph(new RoadGraph(crossWay));
        */
        /*
  Road r1 = new Road(new Way(Direction.FORWARD));
  Road r2 = new Road(new Way(Direction.FORWARD));
  Road r3 = new Road(new Way(Direction.FORWARD));
  Road r4 = new Road(new Way(Direction.FORWARD));

  GeographicCrossWay c1 = new GeographicCrossWay(2, 0, 100, 0, 50);
  c1.attachRoad(0, r1, true);

  GeographicCrossWay c2 = new GeographicCrossWay(2, 100, 0, 0, 50);
  c2.attachRoad(0, r2, false);

  GeographicCrossWay c3 = new GeographicCrossWay(2, 0, -100, 0, 50);
  c3.attachRoad(0, r3, false);

  GeographicCrossWay c4 = new GeographicCrossWay(2, -100, 0, 0, 50);
  c4.attachRoad(0, r4, false);


  GeographicCrossWay crossWay = new GeographicCrossWay(4, 0, 0, 0, 50);
  crossWay.attachRoad(3,r1,false);
  crossWay.attachRoad(0,r2,true);
  crossWay.attachRoad(1,r3,true);
  crossWay.attachRoad(2,r4,true);


  VisualGraph visualGraph = new VisualGraph(new RoadGraph(crossWay,c1,c2,c3,c4)); */

        Road r = new Road(new Way(Direction.FORWARD));

        GeographicCrossWay c1 = new GeographicCrossWay(4, -100,0, 0, 50);
        c1.attachRoad(0, r, true);

        GeographicCrossWay c2 = new GeographicCrossWay(4, 100, 0, 0, 50);
        c2.attachRoad(0, r, false);


        VisualGraph visualGraph = new VisualGraph(new RoadGraph(c1,c2));
        visualGraph.setPosition(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2);
        scene.attachChild(visualGraph);

        return scene;
    }

    @Override
    public void onLoadComplete() {

    }

}