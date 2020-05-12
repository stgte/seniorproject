package seniorproject;

import java.util.Iterator;
import java.util.Map;

public class GV {
	Graph g;

	public GV(Graph g) {
		this.g = g;
	}

	public void displayGV( String log) {
		System.out.println("graph d {");
		System.out.println("\tnode[shape=box];");
		System.out.println("\tnode[style=filled];");
		System.out.println("\tnode[color=gray];");
		System.out.println("\tgraph[overlap=scalexy];");
		System.out.println("\tgraph[concentrate=true];");
		if (log != null)
			System.out.println("\tgraph[label=\"" + log + "\"];");

		Iterator<Map.Entry<Integer, Node>> nodeItr = g.iterator();
		while (nodeItr.hasNext()) {
			Node currentNode = nodeItr.next().getValue();
			System.out.println("\t" + currentNode.getLabel() + ";");
			Iterator<Node> edgeItr = g.edgeIterator(currentNode);
			while (edgeItr.hasNext()) {
				Node neighbour = edgeItr.next();
				System.out.println("\t" + //
						currentNode.getLabel() + " -- " + //
						neighbour.getLabel() + ";");
			}
		}
		System.out.println("}");
	}
}
