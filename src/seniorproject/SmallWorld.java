package seniorproject;

import java.util.*;

public class SmallWorld {
    Graph g;

    SmallWorld(Graph g){
        this.g = g;
    }
    public void addRegular(int k){
        for (int i = 0; i < g.getNumNodes(); i++) {
            for (int j = 1; j <(k+1)/2+1; j++) {
                if ((i+j)>=g.getNumNodes()-1){
                    g.addEdge(i,(i+j)-g.getNumNodes());
                }
                else {
                    g.addEdge(i, i + j);
                }
                if ((i-j)<0){
                    g.addEdge(i,g.getNumNodes()-(i-j));
                }
                else {
                    g.addEdge(i, i-j);
                }
            }
        }
    }

    private void rewireSWG(int b){
        Iterator<Map.Entry<Integer, Node>> nodeItr = g.iterator();
        while (nodeItr.hasNext()) {
            Node currentNode = nodeItr.next().getValue();
            List<Node> Nodes= g.getEdges(currentNode.getLabel());
            List<Integer> labels = new ArrayList<>();
            for (Node n:Nodes
            ) { labels.add(n.getLabel());
            }
            Collections.sort(labels);
            for (int i = 0; i < labels.size()/2; i++) {
                boolean val = new Random().nextInt(b+1) == 0;
                if(val){
                    g.deleteEdge(currentNode.getLabel(), labels.get(i));
//                    Integer rand = new Random().nextInt(g.getNumNodes());
////                    while (rand == currentNode.getLabel() || labels.contains(rand)){
////                        rand = new Random().nextInt(g.getNumNodes());
////                    }
////                    g.addEdge(currentNode.getLabel(),rand );
                    List<Integer> arr = new ArrayList<>();
                    arr.add(currentNode.getLabel());
                    arr.addAll(labels);
                    g.addEdge(currentNode.getLabel(),g.getRandomNode(arr));
                }
            }
        }
    }

    public Graph getG(){
        return g;
    }

    public void createSWG(){
        addRegular((int) Math.log(g.getNumNodes()));
        rewireSWG(10);
    }
}
