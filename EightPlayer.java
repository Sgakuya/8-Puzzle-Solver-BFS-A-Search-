/**
 *  Authors: Smith Gakuya & Yaqi Huang
 *  All group members were present and contributing during all work on this project
 *  We have neither given nor received any unauthorized aid in this assignment
 */

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/*
 * Solves the 8-Puzzle Game (can be generalized to n-Puzzle)
 */

public class EightPlayer {

	static Scanner scan = new Scanner(System.in);
	static int size=3; //size=3 for 8-Puzzle. 
	static int numnodes; //number of nodes generated
	static int nummoves; //number of moves required to reach goal
	
	
	public static void main(String[] args)
	{	
		int numsolutions = 0;
		
		int boardchoice = getBoardChoice();
		int algchoice = getAlgChoice();
			
		//determine numiterations based on user's choices
		int numiterations=0;
		
		if(boardchoice==0)
			numiterations = 1;
		else {
		
			switch (algchoice){
			case 0: 
				numiterations = 100;//BFS
				break;
			case 1: 
				numiterations = 1000;//A* with Manhattan Distance heuristic
				break;
			case 2: 
				numiterations = 1000;//A* with your new heuristic
				break;
			}
		}
		
	
		
		Node initNode;
		
		for(int i=0; i<numiterations; i++){
		
			if(boardchoice==0)
				initNode = getUserBoard();
			else
				initNode = generateInitialState();//create the random board for a new puzzle
			
			boolean result=false; //whether the algorithm returns a solution
			
			switch (algchoice){
				case 0: 
					result = runBFS(initNode); //BFS
					break;
				case 1: 
					result = runAStar(initNode, 0); //A* with Manhattan Distance heuristic
					break;
				case 2: 
					result = runAStar(initNode, 1); //A* with your new heuristic
					break;
			}
			
			
			//if the search returns a solution
			if(result){
				
				numsolutions++;
				
				
				System.out.println("Number of nodes generated to solve: " + numnodes);
				System.out.println("Number of moves to solve: " + nummoves);			
				System.out.println("Number of solutions so far: " + numsolutions);
				System.out.println("_______");		
				
			}
			else
				System.out.print(".");
			
		}//for

		
		
		System.out.println();
		System.out.println("Number of iterations: " +numiterations);
		
		if(numsolutions > 0){
			System.out.println("Average number of moves for "+numsolutions+" solutions: "+nummoves/numsolutions);
			System.out.println("Average number of nodes generated for "+numsolutions+" solutions: "+numnodes/numsolutions);
		}
		else
			System.out.println("No solutions in "+numiterations+"iterations.");
		
	}
	
	
	public static int getBoardChoice()
	{
		
		System.out.println("single(0) or multiple boards(1)");
		int choice = Integer.parseInt(scan.nextLine());
		
		return choice;
	}
	
	public static int getAlgChoice()
	{
		
		System.out.println("BFS(0) or A* Manhattan Distance(1) or A* <Your New Heuristic>(2)");
		int choice = Integer.parseInt(scan.nextLine());
		
		return choice;
	}

	
	public static Node getUserBoard()
	{
		
		System.out.println("Enter board: ex. 012345678");
		String stbd = scan.nextLine();
		
		int[][] board = new int[size][size];
		
		int k=0;
		
		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[0].length; j++){
				System.out.println(stbd.charAt(k));
				board[i][j]= Integer.parseInt(stbd.substring(k, k+1));
				k++;
			}
		}
		
		
		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[0].length; j++){
				System.out.println(board[i][j]);
			}
			System.out.println();
		}
		
		
		Node newNode = new Node(null,0, board);

		return newNode;
		
		
	}

    
	
	
	/**
	 * Generates a new Node with the initial board
	 */
	public static Node generateInitialState()
	{
		int[][] board = getNewBoard();
		
		Node newNode = new Node(null,0, board);

		return newNode;
	}
	
	
	/**
	 * Creates a randomly filled board with numbers from 0 to 8. 
	 * The '0' represents the empty tile.
	 */
	public static int[][] getNewBoard()
	{
		
		int[][] brd = new int[size][size];
		Random gen = new Random();
		int[] generated = new int[size*size];
		for(int i=0; i<generated.length; i++)
			generated[i] = -1;
		
		int count = 0;
		
		for(int i=0; i<size; i++)
		{
			for(int j=0; j<size; j++)
			{
				int num = gen.nextInt(size*size);
				
				while(contains(generated, num)){
					num = gen.nextInt(size*size);
				}
				
				generated[count] = num;
				count++;
				brd[i][j] = num;
			}
		}
		
		/*
		//Case 1: 12 moves
		brd[0][0] = 1;
		brd[0][1] = 3;
		brd[0][2] = 8;
		
		brd[1][0] = 7;
		brd[1][1] = 4;
		brd[1][2] = 2;
		
		brd[2][0] = 0;
		brd[2][1] = 6;
		brd[2][2] = 5;
		*/
		
		return brd;
		
	}
	
	/**
	 * Helper method for getNewBoard()
	 */
	public static boolean contains(int[] array, int x)
	{ 
		int i=0;
		while(i < array.length){
			if(array[i]==x)
				return true;
			i++;
		}
		return false;
	}
	
	
	/**
	 * TO DO:
     * Prints out all the steps of the puzzle solution and sets the number of moves used to solve this board.
     */
    public static void printSolution(Node node) {
    	//recursively loops through the nodes printing the path with the root state first
    	if (node.getparent() == null) {
    		node.print();
    		return;
    	}else {
    		nummoves++;
    		printSolution(node.getparent());
    	}
    	//prints the non-root nodes
    	node.print(); 
    	    
    }
	
	/**
	 * TO DO:
	 * Runs Breadth First Search to find the goal state.
	 * Return true if a solution is found; otherwise returns false.
	 */
	public static boolean runBFS(Node initNode)
	{
		Queue<Node> Frontier = new LinkedList<Node>();
		ArrayList<Node> Explored = new ArrayList<Node>();
		
		Frontier.add(initNode); 
		int maxDepth = 13;
		
		//if current node is the goal calls printSolution and returns true
		if (initNode.isGoal()) {
			printSolution(initNode);
			return true;
		}
				
		//loops through all the elements of the frontier, returns true if node is the goal 
		//otherwise expands the node
		while (!Frontier.isEmpty()) {
			Node cur_state = Frontier.remove();
			Explored.add(cur_state);
			
			//if algorithm reaches depth 13 terminates it, assumes no solution
			if (cur_state.getdepth() >= maxDepth)
				return false;
			
			if (cur_state.isGoal()) {
				System.out.println();
				printSolution(cur_state);
				return true;
			}
			else {
				ArrayList<int[][]> successors = cur_state.expand();
				for (int[][] state:successors) {
					Node n = new Node(cur_state, cur_state.getdepth()+1, state);
					if (!Explored.contains(n) && !Frontier.contains(n)){
						Frontier.add(n);
						//if node is added to the frontier, increments number of nodes
						numnodes++;
					}
				}
			}
		}
		return false;
		
	}//BFS
	
	/***************************A* Code Starts Here ***************************/
	
	/**
	 * TO DO:
	 * Runs A* Search to find the goal state.
	 * Return true if a solution is found; otherwise returns false.
	 * heuristic = 0 for Manhattan Distance, heuristic = 1 for your new heuristic
	 */
	public static boolean runAStar(Node initNode, int heuristic)
	{
		//PriorityQueue Frontier uses the fvalue to compare the nodes
		//Compare method derived in Node class
		PriorityQueue<Node> Frontier = new PriorityQueue<Node>(new AstarComparator());
		ArrayList<Node> Explored = new ArrayList<Node>();
		initNode.setgvalue(0);
		
		//if heuristic is 0, uses Manhattan distance 
		//if heuristic is 1, uses collisions as heuristic
		if (heuristic == 0)
			initNode.sethvalue(initNode.evaluateHeuristic());
		else if (heuristic == 1)
			initNode.sethvalue(initNode.myHeuristic());
		
		Frontier.add(initNode); 
		int maxDepth = 13;
		
		//if current node is the goal returns true
		if (initNode.isGoal()) {
			printSolution(initNode);
			return true;
		}
					
		//loops through the elements of the frontier, returns true if node is the goal 
		//otherwise expands the node
		while (!Frontier.isEmpty()) {
			Node X = Frontier.remove();
			Explored.add(X);
			
			//if algorithm reaches depth 13 terminates it, assumes no solution
			if (X.getgvalue() >= maxDepth)
				return false;
			
			if (X.isGoal()) {
				System.out.println();
				printSolution(X);
				return true;
			}
			else {
				ArrayList<int[][]> successors = X.expand();
				for (int[][] state:successors) {
					Node C = new Node(X, X.getgvalue()+1, 0, state);
					
					//if heuristic is 0, uses manhattan distance as hvalue
					if (heuristic == 0) 
						C.sethvalue(C.evaluateHeuristic());
					
					//if heuristic is 1, uses our heuristic's distance as hvalue
					else if (heuristic == 1) 
						C.sethvalue(C.myHeuristic());						
							
					if (!Explored.contains(C)){
						//copy of frontier to loop through i.e. prevent concurrent modification error
						PriorityQueue<Node> copyFrontier = new PriorityQueue<Node>(new AstarComparator());
						
						//loops through frontier checking if there is another node like C with a higher f value 
						//and replaces it with C which has a lower fvalue
						for (Node n : copyFrontier){
							if(n.equals(C)) {
								double f1 = C.getgvalue() + C.gethvalue();
								double f2 = n.getgvalue() + n.gethvalue();
								if(f1 < f2) {
									Frontier.remove(n);
									Frontier.add(C);
									continue;
								}
							}
						}
						//if node is not in frontier, adds it and increments numnodes
						Frontier.add(C);
						numnodes++;
						
					}
				}
			}
		}
		
		return false;
	}
	
}