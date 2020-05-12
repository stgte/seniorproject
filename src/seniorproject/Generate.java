package seniorproject;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Generate {
    /**
     * adds nodes to graph
     * @param g
     * @param n
     * @return graph
     */
    static void addNodes(Graph g, Integer n) {
        for (int i = 0; i < n; i++) {
            g.addNode(i);
        }
//        return g;
    }

    /**
     * takes graph and add the edges depending on the number of edges you want to add
     * @param g
     * @param n
     * @return graph1
     */
    static void addAPEdges(Graph g, Integer n) {
        List<Point> edges = g.getAPE();
        Random rand = new Random();
        int size = edges.size();
        for (int i = 0; i < n; i++) {
            int index = rand.nextInt(edges.size());
            double label1 = edges.get(index).getX();
            double label2 = edges.get(index).getY();
            g.addEdge((int) label1, (int) label2);
            edges.remove(edges.get(index));
        }
//        return g;
    }

    /**
     * adds random edges to the graph
     */
    static void addEdges(Graph g, Integer n) {
        Random rand = new Random();
        for (int i = 0; i < g.getNumNodes(); i++) {
            int numEdges = rand.nextInt(n+1);
            for (int j = 0; j <numEdges ; j++) {
                Integer label2 = rand.nextInt(g.getNumNodes());
                while (g.isEdge(i,label2) && i==label2){
                    label2 = rand.nextInt(g.getNumNodes());
                }
                g.addEdge(i,label2);
            }
        }
//        return g;

    }

    static Integer removeRandNodes(Graph g){
        Random rand = new Random();
        Integer count = 0;
        List<List<Node>> fragments = g.findFragments();
        while (g.biggestFrag(fragments)>=(g.getNumNodes()/2)){
            g.deleteNode(g.getRandomNode(new ArrayList<>()));
            count++;
            fragments = g.findFragments();
            display(g,count, fragments);
        }
        System.out.println("done");

        return count;
    }

    static Integer removeHDNodes(Graph g){
        Integer count = 0;
        List<List<Node>> fragments = g.findFragments();
        while (g.biggestFrag(fragments)>(g.getNumNodes()/2)){
            g.deleteHDN();
            count++;
            fragments = g.findFragments();
            display(g,count, fragments);
        }
        return count;
    }

    static Integer removeAPNodes(Graph g){
        Integer count = 0;
        List<List<Node>> fragments = g.findFragments();
        while (g.biggestFrag(fragments)>(g.getNumNodes()/3)){
            Integer ind = g.findrandomAP();
            if(ind==-1){
                System.out.println("HDN");
                g.deleteHDN();
            }
            g.deleteNode(ind);
            count++;
            fragments = g.findFragments();
            display(g,count, fragments);
        }
        return count;
    }

    static void addRegular(Graph g, Integer k){
        for (int i = 0; i < g.numNodes; i++) {
            for (int j = 1; j <(k+1)/2+1; j++) {
                if ((i+j)>=g.numNodes-1){
                    g.addEdge(i,(i+j)-g.numNodes);
                }
                else {
                    g.addEdge(i, i + j);
                }
                if ((i-j)<0){
                    g.addEdge(i,g.numNodes-(i-j));
                }
                else {
                    g.addEdge(i, i-j);
                }
            }
        }
    }

    static void rewireSWG(Graph g, int b){
        Iterator<Map.Entry<Integer, Node>> nodeItr = g.iterator();
        while (nodeItr.hasNext()) {
            Node currentNode = nodeItr.next().getValue();
            List<Node> Nodes= g.adjList.get(currentNode);
            List<Integer> labels = new ArrayList<>();
            for (Node n:Nodes
                 ) { labels.add(n.getLabel());
            }
            Collections.sort(labels);
            for (int i = 0; i < labels.size()/2; i++) {
                boolean val = new Random().nextInt(b+1) == 0;
                if(val){
                    g.deleteEdge(currentNode.getLabel(), labels.get(i));
//                    Integer rand = new Random().nextInt(g.numNodes);
//                    while (rand == currentNode.getLabel() || labels.contains(rand)){
//                        rand = new Random().nextInt(g.numNodes);
//                    }
                    List<Integer> arr = new ArrayList<>();
                    arr.add(currentNode.getLabel());
                    arr.addAll(labels);
                    g.addEdge(currentNode.getLabel(),g.getRandomNode(arr));
                }
            }
        }
    }

    static void display(Graph g, Integer n, List<List<Node>> frags){
        System.out.print(g.getNumNodes());
        System.out.print("\t" + n);
        System.out.print("\t"+ g.biggestFrag(frags) );
        System.out.print("\t" + g.aveFragments(frags));
        System.out.print("\t"+ g.aveFrag2(frags));
        System.out.print("\t"+ g.sizeHDN()+"\n");
    }

    // Driver code
    public static void main(String[] args)
    {
        System.out.print("Number of Nodes");
        System.out.print("\tRemoved Nodes");
        System.out.print("\tBiggest fragment" );
        System.out.print("\tAverage Fragment size");
        System.out.print("\tAF w/o biggest");
        System.out.print("\tHighest Degree Node\n");
        Graph g = new Graph();
//        GV g1= new GV(g);
//        g1.displayGV("AdjListGraph");
        addNodes(g,10000);
        addEdges(g, 4);
        System.out.println(g.findArticulationPoint());
//        g1.displayGV("AdjListGraph");
//        removeRandNodes(g);
        removeAPNodes(g);
//        removeHDNodes(g);

////        g.printNode();
//        addRegular(g,3);
//        GV g1 = new GV(g);
//        g1.displayGV("AdjListGraph");
//        rewireSWG(g,10);
//        GV g2 = new GV(g);
//        g2.displayGV("AdjListGraph");
////        Graph g4 = new Graph();
//        SmallWorld g3 = new SmallWorld(g);
//        GV g4 = new GV (g3.getG());
//        g4.displayGV("AdjListGraph");
//        g3.addRegular((int) Math.log(g.getNumNodes()));
//        addNodes(g,10000);
//        g3.createSWG();
//        removeRandNodes(g);
//        removeHDNodes(g);

//        GV g4 = new GV (g3.getG());
//        g4.displayGV("AdjListGraph");



//        addEdges(g, 1000);
//        g.printNode();
//        g.iterator();
//
//        System.out.println(removeHDNodes(g));
//        System.out.println(g.aveFragments());
//        System.out.println(g.aveFrag2());


    }

}
