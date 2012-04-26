package com.game.util;

import com.game.roadnetwork.Direction;
import com.game.roadnetwork.GeographicCrossWay;
import com.game.roadnetwork.Road;
import com.game.roadnetwork.Way;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.*;

/**
 * Date: 23.04.12
 * Time: 9:12
 *
 * @author: Alexey
 */
public class GraphFactory {
    public static DirectedGraph<CrossWayCoordinate, WayEdge> createGraphForRoad(GeographicCrossWay... crossWayList) {
        SimpleDirectedGraph<CrossWayCoordinate, WayEdge> directedGraph = new SimpleDirectedGraph<CrossWayCoordinate, WayEdge>(WayEdge.class);
        Map<Way, CrossWayCoordinate> ways = new HashMap<Way, CrossWayCoordinate>();
        for (GeographicCrossWay crossWay : crossWayList) {
            Map<Way, CrossWayCoordinate> res = createGraphForCrossWay(crossWay, directedGraph);
            Set<Way> waySet = res.keySet();
            for (Way way : waySet) {
                if (ways.containsKey(way)) {
                    CrossWayCoordinate c1 = ways.remove(way);
                    CrossWayCoordinate c2 = res.get(way);
                    if ((way.getRoad().getStart().equals(c1.getCrossWay()) && (way.getAllowedDirection() == Direction.FORWARD)) ||
                            (way.getRoad().getStart().equals(c2.getCrossWay()) && (way.getAllowedDirection() == Direction.BACK))) {
                        directedGraph.addEdge(c1, c2, new WayEdge(way));
                    } else {
                        directedGraph.addEdge(c2, c1, new WayEdge(way));
                    }
                } else {
                    ways.put(way, res.get(way));
                }
            }
        }
        if (!ways.isEmpty()) {
            //throw new IllegalArgumentException("There are ways without crossWays");
        }
        return directedGraph;
    }

    public static DirectedGraph<CrossWayCoordinate, WayEdge> createGraphForRoad(List<GeographicCrossWay> crossWayList) {
        return createGraphForRoad(crossWayList.toArray(new GeographicCrossWay[crossWayList.size()]));
    }


    private static Map<Way, CrossWayCoordinate> createGraphForCrossWay(GeographicCrossWay crossWay, DirectedGraph<CrossWayCoordinate, WayEdge> graph) {
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

        double triangleAngle;
        double catet;
        double radius;

        if (crossWay.getPlacesCount() > 1) {
            triangleAngle = 2 * Math.PI / crossWay.getPlacesCount();
            catet = (maxWays / 2) / (Math.sin(triangleAngle / 2));
            radius = (maxWays / 2) / (Math.tan(triangleAngle / 2));
        } else {
            triangleAngle = Math.PI;
            catet = maxWays/2;
            radius = 0;
        }
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
                double startX = (catet * crossWay.getScale() * Math.cos(triangleAngle * i));
                double startY = (catet * crossWay.getScale() * Math.sin(triangleAngle * i));

                double endX = (catet * crossWay.getScale() * Math.cos(triangleAngle * (i + 1)));
                double endY = (catet * crossWay.getScale() * Math.sin(triangleAngle * (i + 1)));
                for (double j = -maxWays / 2, kk = 0; (kk - offs) < road.getWaysNumber(); j++, kk++) {
                    if (kk < offs) {
                        continue;
                    }
                    double dx = ((endX - startX) / (maxWays)) * (0.5f + j);
                    double dy = ((endY - startY) / (maxWays)) * (0.5f + j);

                    double spX = ((radius * crossWay.getScale()) * Math.cos(triangleAngle / 2 + triangleAngle * i)) - dx;
                    double spY = ((radius * crossWay.getScale()) * Math.sin(triangleAngle / 2 + triangleAngle * i)) - dy;
                    double epX = ((mRadius * crossWay.getScale()) * Math.cos(triangleAngle / 2 + triangleAngle * i)) - dx;
                    double epY = ((mRadius * crossWay.getScale()) * Math.sin(triangleAngle / 2 + triangleAngle * i)) - dy;
                    traceLines.get(i).add(new MathLine(
                            new Coordinate(spX, spY).rotateAndMove(crossWay.getAngle() - triangleAngle/2, crossWay.getX(), crossWay.getY()),
                            new Coordinate(epX, epY).rotateAndMove(crossWay.getAngle() - triangleAngle/2, crossWay.getX(), crossWay.getY())));

                }
            }
        }

        for (int i = 0; i < traceLines.size(); i++) {
            ArrayList<MathLine> lines1 = traceLines.get(i);
            if(lines1.isEmpty()){
                continue;
            }
            for (int k = 0; k < lines1.size(); k++) {
                Coordinate startPoint = lines1.get(k).getStart();
                CrossWayCoordinate roadEnter = new CrossWayCoordinate(startPoint, crossWay);
                res.put(crossWay.getRoad(i).getWay(k), roadEnter);
                graph.addVertex(roadEnter);

            }
            for (int j = 0; j < traceLines.size(); j++) {
                if (i != j) {
                    ArrayList<MathLine> lines2 = traceLines.get(j);
                    if(lines2.isEmpty()){
                        continue;
                    }
                    Road curRoad = crossWay.getRoad(i);
                    for (int k = 0; k < lines1.size(); k++) {
                        ArrayList<CrossWayCoordinate> crossPoints = new ArrayList<CrossWayCoordinate>(lines1.size() * lines2.size());
                        for (MathLine line:  lines2) {
                            Coordinate coordinate = lines1.get(k).getLineIntersection(line);
                            if ((coordinate == null) && (lines1.get(k).isParallel(line))) {
                                coordinate = lines1.get(k).getParallelLineIntersection(line);
                                if(coordinate!=null){
                                    CrossWayCoordinate crossWayCoordinate = new CrossWayCoordinate(line.getStart(), crossWay);
                                    graph.addVertex(crossWayCoordinate);
                                    crossPoints.add(crossWayCoordinate);
                                }
                            }
                            if (coordinate != null) {
                                CrossWayCoordinate crossWayCoordinate = new CrossWayCoordinate(coordinate, crossWay);
                                graph.addVertex(crossWayCoordinate);
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
                                    graph.addEdge(crossPoints.get(p), crossPoints.get(p + 1));
                                } else {
                                    graph.addEdge(crossPoints.get(p + 1), crossPoints.get(p));
                                }
                            }
                        }

                    }
                }
            }
        }


        return res;

    }


    private static class PointComparator implements Comparator<CrossWayCoordinate> {
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
