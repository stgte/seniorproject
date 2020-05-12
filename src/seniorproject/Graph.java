package seniorproject;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;


public class Graph {
    //    public List<Node> nodesList = new LinkedList<Node>();
    int numNodes;
    public Map<Node, List<Node>> adjList = new HashMap<Node, List<Node>>();
    public Map<Integer, Node> labelList = new HashMap<Integer, Node>();

    /**
     * constructor
     */
    Graph() {
        numNodes = 0;
    }

    Graph(Integer n){
        numNodes = 0;
        for (int i = 0; i < n; i++) {
            addNode(i);
        }
    }

    /**
     * @return numNodes
     */
    public int getNumNodes() {
        return numNodes;
    }

    /**
     * @param label adds node to the graph
     */
    public void addNode(Integer label) {
        if (!labelList.containsKey(label)) {
            Node newNode = new Node(label);
            adjList.putIfAbsent(newNode, new ArrayList<>());
            labelList.putIfAbsent(label, newNode);
            numNodes++;
        }
    }

    public boolean isEdge(Integer label1, Integer label2){
        if(labelList.containsKey(label1)){
            return adjList.get(labelList.get(label1)).contains(labelList.get(label2));
        }
        return false;
    }

    public Node getNode(Integer label) {
        if (labelList.containsKey(label)){
            return labelList.get(label);
        }
        return null;
    }

    public List<Node> getEdges(Integer label){
        if (labelList.containsKey(label)){
            return adjList.get(labelList.get(label));
        }
        return null;
    }
    /**
     * @param label deletes node from the graph
     */
    public void deleteNode(Integer label) {
        if (labelList.containsKey(label)) {
            Node node = labelList.get(label);
            adjList.values().forEach(e -> e.remove(node));
            adjList.remove(node);
            labelList.remove(label);
            numNodes--;
        }
    }

    /**
     * @param label1,label2 adds edge to the graph using labels of the nodes
     */
    public void addEdge(Integer label1, Integer label2) {
        if (labelList.containsKey(label1) && labelList.containsKey(label2) && !label1.equals(label2)) {
            if (!adjList.get(labelList.get(label1)).contains(labelList.get(label2))) {
                Node node1 = labelList.get(label1);
                Node node2 = labelList.get(label2);
                adjList.get(node1).add(node2);
                adjList.get(node2).add(node1);
            }
        }
    }

    /**
     * @param label1,label2 deletes edge from the graph using labels of the nodes
     */
    public void deleteEdge(int label1, int label2) {
        Node node1 = labelList.get(label1);
        Node node2 = labelList.get(label2);

        List<Node> node1list = adjList.get(node1);
        List<Node> node2list = adjList.get(node2);
        if (node1list != null) {
            node1list.remove(node2);
        }
        if (node2list != null) {
            node2list.remove(node1);
        }
    }

    /**
     * @param label
     * @return node
     * find node using label from labelList hashMap
     */
    public Node findNode(int label) {
        return labelList.get(label);
    }

    public boolean isNode(int label){
        return labelList.containsKey(label);
    }

    public Integer getRandomNode(List<Integer> arr) {
        Random rand = new Random();
        List<Integer> labels = new ArrayList<Integer>(labelList.keySet());
        labels.removeAll(arr);
        return labels.get(rand.nextInt(labels.size()));
    }

//    /**
//     * find the node with
//     * @return
//     */
//    public Node maxNode(){
//        int maxDegree = 0;
//        Node maxNode = null;
//        List<Node> nodes = new ArrayList<Node>(adjList.keySet());
//        for (Node n:nodes ){
//            if (maxDegree<adjList.get(n).size()){
//                maxDegree = adjList.get(n).size();
//                maxNode = n;
//            }
//        }
//        return maxNode;
//    }

    /**
     * Breadth-First Search on the graph
     *
     * @param root
     * @return set of nodes
     */
    public Set<Node> BFS(Node root) {
        Set<Node> visited = new HashSet<>();
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        visited.add(root);
        while (!q.isEmpty()) {
            Node node = q.poll();
            for (Node n : adjList.get(node)) {
                if (!visited.contains(n)) {
                    q.add(n);
                    visited.add(n);
                }
            }
        }
        return visited;
    }

    /**
     * Breadth-First Search on the graph for articulation point where it check whether the node is articulation point or not by marking it visited
     *
     * @param root
     * @return set of nodes
     */
    public Set<Node> BFS2(Node root, Node visNode) {
        Set<Node> visited = new HashSet<>();
        visited.add(visNode);
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        visited.add(root);
        while (!q.isEmpty()) {
            Node node = q.poll();
            for (Node n : adjList.get(node)) {
                if (!visited.contains(n)) {
                    q.add(n);
                    visited.add(n);
                }
            }
        }
        return visited;
    }

    /**
     * check whether graph is connected or not
     *
     * @return boolean
     */
    public boolean isConnected() {
        if (adjList.size() > 0) {
            List<Node> nodes = new ArrayList<Node>(adjList.keySet());
            int visitedNodes = BFS(nodes.get(0)).size();
            return numNodes <= visitedNodes;
        }
        return false;
    }

    /**
     * prints existent nodes in the graph
     */
    public void printNode() {
        List<Integer> label = new ArrayList<>(labelList.keySet());
        System.out.println(label);
    }

    /**
     * Breadth-First Search on the graph for articulation point where it check whether the node is articulation point or not by marking it visite
     * @param root
     * @return set of nodes
     */
    public Set<Node> BFS3(Node root, Node visNode, List<Node> frag) {
        Set<Node> visited = new HashSet<>();
        visited.add(visNode);
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        visited.add(root);
        while (!q.isEmpty()) {
            Node node = q.poll();
            for (Node n : frag) {
                if (!visited.contains(n)) {
                    q.add(n);
                    visited.add(n);
                }
            }
        }
        return visited;
    }


    /**
     * find the articulation points of the graph
     *
     * @return list of nodes
     */
    public List<Node> findArticulationPoint() {
        boolean first = true;
        List<Node> APList = new ArrayList<>();
//        Node articulationPoint = null;

        List<Node> nodes = new ArrayList<Node>(adjList.keySet());
        int visitedNodes;
        List<Node> bigFrag =  findBigFragSet(findFragments());
        for (Node n : nodes) {
            if (first) {
                visitedNodes = BFS3(nodes.get(1), n, bigFrag).size();
                first = false;
            } else {
                visitedNodes = BFS3(nodes.get(0), n, bigFrag).size();
            }
            if (numNodes > visitedNodes) {
                APList.add(n);
            }
        }
        return APList;
    }

    /**
     * finds articulation point
     * @return node label
     */
    public Integer findrandomAP(){
        List<Node> arr = findArticulationPoint();
        if (arr.isEmpty()){
            return -1;
        }
        return  arr.get(0).getLabel();
    }

    /**
     * find all the fragments in the graph
     *
     * @return list of list of nodes that represent fragments
     */
    public List<List<Node>> findFragments() {
        List<List<Node>> subgraphs = new ArrayList<>();
        List<Node> nodes = new ArrayList<Node>(adjList.keySet());
        while (!nodes.isEmpty()) {
            List<Node> visitedNodes = new ArrayList<Node>();
            visitedNodes.addAll(BFS(nodes.get(0)));
//            (List<Node>) BFS(nodes.get(0));
            subgraphs.add(visitedNodes);
            nodes.removeAll(visitedNodes);
        }
        return subgraphs;
    }

    /**
     * find the biggest fragment set
     * @param fragments
     * @return list of biggest fragment nodes
     */
    public List<Node> findBigFragSet(List<List<Node>> fragments){
        if (numNodes == 0) {
            return null;
        }
        int size = 0;
        int indx = 0;
        int count = 0;
//        List<List<Node>> subgraphs = findFragments();
        for (List<Node> n : fragments) {
            count++;
            if (n.size() > size) {
                size = n.size();
                indx = count;
            }
        }
        return fragments.get(indx);
    }

    /**
     * find the highest degree node
     *
     * @return node
     */
    public Node highestDegreeNode() {
        Integer highest = 0;
        Node highNode = null;
        for (Node n : labelList.values()) {
            if (adjList.get(n).size() > highest) {
                highest = adjList.get(n).size();
                highNode = n;
            }
        }
        return highNode;
    }

    /**
     * gets the size of the highest degree node
     * @return
     */
    public Integer sizeHDN(){
        Node n =highestDegreeNode();
        return adjList.get(n).size();
    }

    /**
     * deletes highest degree node
     */
    public void deleteHDN() {
        Node n = highestDegreeNode();
        deleteNode(n.getLabel());
    }


    /**
     * find the average size of the fragments
     *
     * @return size
     */
    public float aveFragments(List<List<Node>> fragments) {
        if (numNodes == 0) {
            return 0;
        }
//        List<List<Node>> subgraphs = findFragments();
        return numNodes /fragments.size();
    }

    /**
     * find the average size of the fragments - the biggest fragment size
     *
     * @return size
     */
    public int aveFrag2(List<List<Node>> fragments) {
        if (numNodes == 0) {
            return 0;
        }
//        List<List<Node>> subgraphs = findFragments();
        if (fragments.size() > 1) {
            int size = (numNodes - biggestFrag(fragments)) / (fragments.size() - 1);
            return size;
        }
        return 0;
    }

    /**
     * Find the size of the biggest fragment in the graph
     *
     * @return size
     */
    public int biggestFrag(List<List<Node>> fragments) {
        if (numNodes == 0) {
            return 0;
        }
        int size = 0;
//        List<List<Node>> subgraphs = findFragments();
        for (List<Node> n : fragments) {
            if (n.size() > size) {
                size = n.size();
            }
        }
        return size;
    }

    /**
     * counts unique pairs
     *
     * @param arr
     * @param n
     * @return
     */
    static Set<Point> countUnique(List<Integer> arr, int n) {
        // Set to store unique pairs
        Set<Point> s = new HashSet<>();
        // Make all possible pairs
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (arr.get(i) != arr.get(j)) {
                    Point opp = new Point(arr.get(j), arr.get(i));
                    if (!s.contains(opp)) {
                        s.add(new Point(arr.get(i), arr.get(j)));
                    }
                }
        // Return the size of the set
        return s;
    }

    /**
     * gets list of all possible edges without repetition
     *
     * @return edgeList of Points
     */
    public List<Point> getAPE() {
        List<Integer> nodeLabels = new ArrayList<Integer>(labelList.keySet());
        List<Point> edgeList = new ArrayList<Point>();
        edgeList.addAll(countUnique(nodeLabels, numNodes));
        return edgeList;
    }

    /**
     *node iterator
     */
    public Iterator<Map.Entry<Integer, Node>> iterator() {
        Iterator<Map.Entry<Integer, Node>> itr = labelList.entrySet().iterator();
        return itr;
    }

    /**
     * edge iterator
     * @param n
     * @return
     */
    public Iterator<Node> edgeIterator(Node n) {
        Iterator<Node> itr = adjList.get(n).iterator();
        return itr;
    }
}

