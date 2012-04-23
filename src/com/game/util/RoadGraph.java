package com.game.util;

import com.game.roadnetwork.Direction;
import com.game.roadnetwork.GeographicCrossWay;
import com.game.roadnetwork.Road;
import com.game.roadnetwork.Way;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.*;

/**
 * Date: 19.04.12
 * Time: 14:51
 *
 * @author: Alexey
 */
public class RoadGraph extends SimpleDirectedGraph<CrossWayCoordinate, WayEdge> {

    public RoadGraph() {
        super(WayEdge.class);
    }

    public RoadGraph(GeographicCrossWay... crossWayList) {
        super(WayEdge.class);
        Map<Way, CrossWayCoordinate> ways = new HashMap<Way, CrossWayCoordinate>();
        for (GeographicCrossWay crossWay : crossWayList) {
            Map<Way, CrossWayCoordinate> res = createGraphForCrossWay(crossWay);
            Set<Way> waySet = res.keySet();
            for (Way way : waySet) {
                if (ways.containsKey(way)) {
                    CrossWayCoordinate c1 = ways.remove(way);
                    CrossWayCoordinate c2 = res.get(way);
                    if ((way.getRoad().getStart().equals(c1.getCrossWay()) && (way.getAllowedDirection() == Direction.FORWARD)) &&
                            (way.getRoad().getStart().equals(c2.getCrossWay()) && (way.getAllowedDirection() == Direction.BACK))) {
                        addEdge(c1, c2, new WayEdge(way));
                    } else {
                        addEdge(c1, c2, new WayEdge(way));
                    }
                } else {
                    ways.put(way, res.get(way));
                }
            }
        }
        if (!ways.isEmpty()) {
            //throw new IllegalArgumentException("There are ways without crossWays");
        }
    }


    public RoadGraph(List<GeographicCrossWay> crossWayList) {
        this(crossWayList.toArray(new GeographicCrossWay[crossWayList.size()]));
    }


    private Map<Way, CrossWayCoordinate> createGraphForCrossWay(GeographicCrossWay crossWay) {
        List<Road> roadList = crossWay.getRoads();
        HashMap<Way, CrossWayCoordinate> res = new HashMap<Way, CrossWayCoordinate>();
        ArrayList<ArrayList<MathLine>> traceLines = new ArrayList<ArrayList<MathLine>>(crossWay.getPlacesCount());

        if (roadList.size() == 0) {
            throw new IllegalArgumentException("Can't build graph for cross way. It don't  contains  roads.");
        }
        double maxWays = 0;
        for (Road road : roadList) {
            if (road.getWaysNumber() > maxWays) {
                maxWays = road.getWaysNumber();
            }
        }
        if (maxWays == 0) {
            throw new IllegalArgumentException("Can't build graph for cross. It contains only empty roads.");
        }

        double triangleAngle = 2 * Math.PI / crossWay.getPlacesCount();
        double catet = maxWays / (Math.sin(triangleAngle / 2));
        double radius = maxWays / (Math.tan(triangleAngle / 2));
        double mRadius;

        if ((crossWay.getPlacesCount() % 2) == 0) {
            mRadius = -radius;
        } else {
            mRadius = -catet;
        }
        for (int i = 0; i < crossWay.getPlacesCount(); i++) {
            Road road = crossWay.getRoad(i);
            traceLines.add(new ArrayList<MathLine>());
            if ((road != null) && (road.getWaysNumber() != 0)) {
                int offs = (int) ((maxWays - road.getWaysNumber()) / 2);
                double startX =  (catet * crossWay.getScale() * Math.cos(triangleAngle * i));
                double startY =  (catet * crossWay.getScale() * Math.sin(triangleAngle * i));

                double endX =  (catet * crossWay.getScale() * Math.cos(triangleAngle * (i + 1)));
                double endY = (catet * crossWay.getScale() * Math.sin(triangleAngle * (i + 1)));
                for (double j = -maxWays / 2, kk = 0; j < maxWays / 2; j++, kk++) {
                    if (kk < offs) {
                        continue;
                    } else if ((kk - offs) >= road.getWaysNumber()) {
                        break;
                    }
                    double dx = ((endX - startX) / (maxWays)) * (0.5f + j);
                    double dy = ((endY - startY) / (maxWays)) * (0.5f + j);

                    double spX =  ((radius * crossWay.getScale()) * Math.cos(triangleAngle / 2 + triangleAngle * i)) - dx;
                    double spY =  ((radius * crossWay.getScale()) * Math.sin(triangleAngle / 2 + triangleAngle * i)) - dy;
                    double epX =  ((mRadius * crossWay.getScale()) * Math.cos(triangleAngle / 2 + triangleAngle * i)) - dx;
                    double epY = ((mRadius * crossWay.getScale()) * Math.sin(triangleAngle / 2 + triangleAngle * i)) - dy;
                    /*
                    traceLines.get(i).add(new MathLine(
                            new Coordinate(spX, spY).rotateAndMove(crossWay.getAngle(), crossWay.getX(), crossWay.getY()),
                            new Coordinate(epX, epY).rotateAndMove(crossWay.getAngle(), crossWay.getX(), crossWay.getY())));
                    */
                    traceLines.get(i).add(new MathLine(
                            new Coordinate(spX, spY).rotate(crossWay.getAngle()),
                            new Coordinate(epX, epY).rotate(crossWay.getAngle())));

                }
            }
        }

        for (int i = 0; i < traceLines.size(); i++) {
            ArrayList<MathLine> lines1 = traceLines.get(i);
            for (int k = 0; k < lines1.size(); k++) {
                Coordinate startPoint = lines1.get(k).getStart();
                CrossWayCoordinate roadEnter = new CrossWayCoordinate(startPoint, crossWay);
                res.put(crossWay.getRoad(i).getWay(k), roadEnter);
                addVertex(roadEnter);

            }
            for (int j = 0; j < traceLines.size(); j++) {
                if (i != j) {
                    ArrayList<MathLine> lines2 = traceLines.get(j);
                    Road curRoad = crossWay.getRoad(i);
                    for (int k = 0; k < lines1.size(); k++) {
                        ArrayList<CrossWayCoordinate> crossPoints = new ArrayList<CrossWayCoordinate>(lines1.size() * lines2.size());
                        for (int p = 0; p < lines2.size(); p++) {
                            Coordinate coordinate = lines1.get(k).getLineIntersection(lines2.get(p));
                            if ((coordinate == null) && (lines1.get(k).isParallel(lines2.get(p)))) {
                                coordinate = lines1.get(k).getParallelLineIntersection(lines2.get(p));
                            }
                            if (coordinate != null) {
                                CrossWayCoordinate crossWayCoordinate = new CrossWayCoordinate(coordinate, crossWay);
                                addVertex(crossWayCoordinate);
                                crossPoints.add(crossWayCoordinate);
                            }
                        }
                        Direction direction = Direction.BACK;
                        Way currWay = curRoad.getWay(k);
                        if (crossWay.beginOfRoadIn(i) && (currWay.getAllowedDirection() == Direction.FORWARD)) {
                            direction = Direction.FORWARD;
                        } else if ((!crossWay.beginOfRoadIn(i)) && (currWay.getAllowedDirection() == Direction.BACK)) {
                            direction = Direction.FORWARD;
                        }
                        Coordinate startPoint = lines1.get(k).getStart();
                        CrossWayCoordinate roadEnter = new CrossWayCoordinate(startPoint, crossWay);
                        crossPoints.add(roadEnter);
                        Collections.sort(crossPoints, new PointComparator(startPoint));
                        //res.put(crossWay.getRoad(i).getWay(k), roadEnter);
                        for (int p = 0; p < crossPoints.size() - 1; p++) {
                            if (!crossPoints.get(p).equals(crossPoints.get(p + 1))) {
                                if (direction == Direction.FORWARD) {
                                    addEdge(crossPoints.get(p), crossPoints.get(p + 1));
                                } else {
                                    addEdge(crossPoints.get(p + 1), crossPoints.get(p));
                                }
                            }
                        }

                    }
                }
            }
        }


        return res;

    }


    private class PointComparator implements Comparator<CrossWayCoordinate> {
        private Coordinate start;


        private PointComparator(Coordinate start) {
            this.start = start;
        }

        @Override
        public int compare(CrossWayCoordinate first, CrossWayCoordinate second) {
            double dxF = first.getX() - start.getX();
            double dyF = first.getY() - start.getY();
            double dxS = second.getX() - start.getX();
            double dyS = second.getY() - start.getY();
            double lenF = Math.sqrt(dxF * dxF + dyF * dyF);
            double lenS = Math.sqrt(dxS * dxS + dyS * dyS);
            if (lenF > lenS) {
                return -1;
            } else if (lenF == lenS) {
                return 0;
            } else {
                return 1;
            }

        }
    }
}
