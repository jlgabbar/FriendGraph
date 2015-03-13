/* 
     Name: Joseph Gabbard 
     Email: jlgabbar@go.olemiss.edu 
     Program Source File Name: Graph.java 
     Current Date: 3-7-14
     Course Information: CSCI 433 Section1
     Instructor: Dr. Wilkins
     Program Description: Builds a graph with a given input 
     
     Honor Code Statement: In keeping with the honor code policies of the University of Mississippi, the School of Engineering,      and the Department of Computer and Information Science, I affirm that I have neither given nor received assistance on this      programming assignment. This assignment represents my individual, original effort. 
                    ... My Signature is on File. 
*/ 

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.io.FileNotFoundException;

public class Graph {
	int numgraphs;
	int n, m, v1, v2, followers, following;
	boolean weighted;
	boolean directed;
	boolean adjmatrix[][];
	double adjmatrix2[][];
	boolean visited[];
	int count;
	double weight;
	double popularity;
	DecimalFormat oneDigit = new DecimalFormat("#.#");
	int friends;
	int FoFs;
	int oldestfriend;
	double days;
	ArrayList<Integer> list;

	public Graph(String file) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(file));
		numgraphs = scan.nextInt();
		System.out.println("Processing " + numgraphs + " graph(s)");
		for (int i = 0; i < numgraphs; i++) {

			n = scan.nextInt();
			m = scan.nextInt();
			if (scan.nextInt() == 0) {
				directed = false;
			} else {
				directed = true;
			}
			if (scan.nextInt() == 0) {
				weighted = false;
			} else {
				weighted = true;
			}
			if (directed == true) {
				System.out.println("Processing directed graph " + count
						+ " with " + n + " nodes and " + m + " edges.");
				count++;
				adjmatrix = new boolean[n][n];
				for (int j = 0; j < m; j++) {
					scan.nextLine();
					v1 = scan.nextInt();
					v2 = scan.nextInt();
					adjmatrix[v1][v2] = true;
				}
				System.out.print("DFS traversal: ");
				DFS();
				System.out.println();
				Popularity();
			} else {
				System.out.println();
				System.out.println("Processing undirected graph " + count
						+ " with " + n + " nodes and " + m + " edges.");
				count++;
				adjmatrix2 = new double[n][n];
				for (int j = 0; j < m; j++) {
					scan.nextLine();
					v1 = scan.nextInt();
					v2 = scan.nextInt();
					weight = scan.nextDouble();
					adjmatrix2[v1][v2] = weight;
					adjmatrix2[v2][v1] = weight;
				}
				System.out.print("BFS traversal: ");
				BFS();
				for (int k : list) {
					System.out.print(" " + k);
				}
				System.out.println();
				Friends();
			}
		}
	}

	public void DFS() {
		visited = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				DFS(i, visited);
			}

		}

	}

	public void DFS(int here, boolean[] visited) {
		visited[here] = true;
		System.out.print(here + " ");
		for (int j = 0; j < n; j++)
			if (adjmatrix[here][j] && !visited[j]) {
				DFS(j, visited);
			}
	}

	public void BFS() {
		visited = new boolean[n];
		list = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				list.add(i);
				visited[i] = true;
				BFS(list.size() - 1);
			}
		}
	}

	public void BFS(int start) {
		for (int j = 0; j < n; j++) {
			if (adjmatrix2[list.get(start)][j] != 0 && !visited[j]) {
				list.add(j);
				visited[j] = true;
			}
		}
		for (int k = start + 1; k < list.size(); k++) {
			BFS(k);
		}
	}

	public void Popularity() {
		for (int i = 0; i < n; i++) {
			following = 0;
			followers = 0;
			for (int j = 0; j < n; j++) {
				if (adjmatrix[i][j] == true) {
					following++;
				}
				if (adjmatrix[j][i] == true) {
					followers++;
				}
			}
			popularity = 0;
			if (following == 0) {
				if (followers < 3) {
					System.out.println("Person " + i
							+ " IS NOT popular. Followed by: " + followers
							+ " Follows: " + following);
				} else
					System.out.println("Person " + i
							+ " IS popular. Followed by: " + followers
							+ " Follows: " + following);
			}
			if (following != 0) {
				popularity = (double) followers / following;
				if (popularity >= 2) {
					System.out.println("Person " + i
							+ " IS popular. Popularity Score: " + popularity
							+ " Followed by: " + followers + " Follows: "
							+ following);
				} else
					System.out.println("Person " + i
							+ " IS NOT popular. Popularity Score: "
							+ popularity + " Followed by: " + followers
							+ " Follows: " + following);
			}
		}

	}
	public void friendOfFriend(){
		visited = new boolean[n];
		FoFs= 0;
		for(int i = 0; i<n;i++){
			for(int j = 0; j < n; j++){
				if(adjmatrix2[i][j]!=0){
					visited[i] = true;
					if(adjmatrix2[j][i]!=0 && !visited[j]){
						FoFs++;
					}//use an array list and add the friends to the list- then goes through the array list and sees who those friends are
				}
			}
		}
		
	}
	public void Friends() {
		for (int i = 0; i < n; i++) {
			friends = 0;
			oldestfriend = 0;
			days = 0;
			for (int j = 0; j < n; j++) {
				if (adjmatrix2[i][j] != 0) {
					friends++;
				}
				if (adjmatrix2[i][j] > days) {
					days = adjmatrix2[i][j];
					oldestfriend = j;
				}
			}
			friendOfFriend();
			System.out.println("Person " + i + " has " + friends
					+ " friend(s) and " + FoFs + " FoFs oldest friend is "
					+ oldestfriend + " (" + (int) days + ") ");
		}
	}
}
