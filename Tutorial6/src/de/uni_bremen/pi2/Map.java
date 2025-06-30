package de.uni_bremen.pi2;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
	* Die Klasse liest die Karte aus Knoten und Kanten ein und
	* repräsentiert diese. Die Daten stammen ursprünglich aus Open
	* Street Map (OSM). Dabei werden für jede eingelesene Kante zwei
	* gerichtete Kanten in die Karte eingetragen. Die Klasse stellt
	* eine Methode zur Verfügung, um die Karte zu zeichnen, sowie
	* eine, die zu einem Punkt den dichtesten Knoten ermittelt.
*/
class Map {

/**
	* Liste der Knoten des Graphen
*/
List<Node> nodes = new ArrayList<>();

/**
	* Konstruktor. Liest die Karte ein.
	*
	* @throws FileNotFoundException Entweder die Datei "nodes.txt" oder die
	*                               Datei "edges.txt" wurden nicht gefunden.
	* @throws IOException           Ein Lesefehler ist aufgetreten.
*/
Map() throws FileNotFoundException, IOException {
List<String> fileEdges = new ArrayList<>();
List<String> fileNodes = new ArrayList<>();

try (BufferedReader stream = new BufferedReader(new InputStreamReader(new FileInputStream("edges.txt")))) {
String line;
while ((line = stream.readLine()) != null) {
fileEdges.add(line);
}
} catch (FileNotFoundException e) {
throw new IllegalArgumentException("'edges.txt' wurde nicht gefunden.");
} catch (IOException e) {
throw new IllegalArgumentException("Ein Lesefehler ist aufgetreten.");
}

try (BufferedReader stream = new BufferedReader(new InputStreamReader(new FileInputStream("nodes.txt")))) {
String line;
while ((line = stream.readLine()) != null) {
fileNodes.add(line);
}
} catch (FileNotFoundException e) {
throw new IllegalArgumentException("'nodes.txt' wurde nicht gefunden.");
} catch (IOException e) {
throw new IllegalArgumentException("Ein Lesefehler ist aufgetreten.");
}

for (String nodeLine : fileNodes) {
String[] nodeParams = nodeLine.split(" ");
int id = Integer.parseInt(nodeParams[0]);
double xNode = Double.parseDouble(nodeParams[1]);
double yNode = Double.parseDouble(nodeParams[2]);
nodes.add(new Node(id, xNode, yNode));
}

for (String edgeString : fileEdges) {
String[] edgesParams = edgeString.split(" ");
int idStart = Integer.parseInt(edgesParams[0]);
int idTarget = Integer.parseInt(edgesParams[1]);

Node startNode = null, endNode = null;

for (Node node : nodes) {
if (node.getId() == idStart) {
startNode = node;
} else if (node.getId() == idTarget) {
endNode = node;
}
}

if (startNode != null && endNode != null) {
startNode.getEdges().add(new Edge(endNode, startNode.distance(endNode)));
endNode.getEdges().add(new Edge(startNode, endNode.distance(startNode)));
}
}
}

/**
	* Zeichnen der Karte.
*/
void draw() {
for (Node node : nodes) {
for (Edge edge : node.getEdges()) {
node.draw(edge.getTarget(), Color.BLACK);
}
}
}

/**
	* Findet den dichtesten Knoten zu einer gegebenen Position.
	*
	* @param x Die x-Koordinate.
	* @param y Die y-Koordinate.
	* @return Der Knoten, der der Position am nächsten ist. null,
	* falls es einen solchen nicht gibt.
*/
Node getClosest(final double x, final double y) {
Node position = new Node(-1, x, y);
double minDistance = Double.POSITIVE_INFINITY;
Node closestNode = null;

for (Node node : nodes) {
double distance = node.distance(position);
if (distance < minDistance) {
minDistance = distance;
closestNode = node;
}
}

return closestNode;
}

/**
	* Löschen aller Vorgängereinträge und Setzen aller Kosten auf unendlich.
*/
void reset() {
// Nutzt Node.reachedFromAtCosts für jeden Knoten.
}

List<Node> getNodes() {
return nodes;
}
}
