/**
 *  Authors: Smith Gakuya & Yaqi Huang
 *  All group members were present and contributing during all work on this project
 *  We have neither given nor received any unauthorized aid in this assignment
 */

import java.util.*;

/**
 * Represents a Node for the n-Puzzle 
 */
public class Node{
    
	private static int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}; //the goal configuration of the board.
	
	//Properties of this Node
	private int size = 3; //3 is the board size for 8-Puzzle.
	int[][] board;
    private Node parent;
    private int depth; //depth of this node

   
    /**
     * Constructor to be used by BFS search.
     * par - The parent node.
     * dep - The depth of this node.
     * brd - The board - a 3x3 array of integers from 0 to 15.
     *              The 0 is the blank spot.     
     */
    public Node(Node par, int dep, int[][] brd) {
    	
    	
    	this.parent = par;
    	this.depth = dep;
        this.board = (int[][]) brd.clone();
        
    }
     
    /*************
     ********** Setter and Getter methods for Node variables **********
     *************/
    public void setparent(Node par)
    {
    	this.parent = par;
    }
    
    public Node getparent()
    {
    	return this.parent;
    }

    public void setdepth(int dep)
    {
    	this.depth = dep;
    }
    
    public int getdepth()
    {
    	return this.depth;
    }

    public void setboard(int[][] brd)
    {
    	this.board = brd;
    }
    
    public int[][] getboard()
    {
    	return this.board;
    }
    
    
    /*************
     ********** End of Setter and Getter methods **********
     *************/
    
    /**
     * Returns true if the state of this node is the goal state of the puzzle.
     */
    public boolean isGoal() {
    
    	for(int i=0; i<size; i++)
    	{
    		for(int j=0; j<size; j++)
    			if(board[i][j] != goal[i][j])
    				return false;
    	}
    		
    	return true;
    }
    
         
    
    /**
     * Returns true if brd is same as this board.
     */
    public boolean isSameBoard(int[][] brd) {
        
    	for(int i=0; i<size; i++)
    	{
    		for(int j=0; j<size; j++)
    			if(this.board[i][j] != brd[i][j])
    				return false;
    	}
    		
    	return true;
    }
    
    /**
     * TO DO
	 * Expands the current board to create the new states. 
	 * The next possible states are based on the current location of the '0' (ie. blank spot)
	 */
	public ArrayList<int[][]> expand()
	{	
		ArrayList<int[][]> nodeslist = new ArrayList<int[][]>();
		
		//If the '0' (blank spot) is at board[0][0], we can either move the blank 
		//down or to the right.
		if(board[0][0] == 0){
			nodeslist.add(moveBlankDown(0, 0));
			nodeslist.add(moveBlankRight(0, 0));
			//System.out.println("case 1");
			
		//If the '0' (blank spot) is at board[0][1], we can either move the blank 
		//down, to the right or to the left.
		}else if(board[0][1] == 0){
			nodeslist.add(moveBlankDown(0, 1));
			nodeslist.add(moveBlankRight(0, 1));
			nodeslist.add(moveBlankLeft(0, 1));
			//System.out.println("case 2");
			
		//If the '0' (blank spot) is at board[0][2], we can either move the blank 
		//down or to the left.
		}else if(board[0][2] == 0){
			nodeslist.add(moveBlankDown(0, 2));
			nodeslist.add(moveBlankLeft(0, 2));
			//System.out.println("case 3");
			
		//If the '0' (blank spot) is at board[1][0], we can either move the blank 
		//down, up or to the right.
		}else if(board[1][0] == 0){
			nodeslist.add(moveBlankDown(1, 0));
			nodeslist.add(moveBlankRight(1, 0));
			nodeslist.add(moveBlankUp(1, 0));
//			System.out.println("case 4");
			
		//If the '0' (blank spot) is at board[1][1], we can either move the blank 
		//down, up, to the right or to the left .
		}else if(board[1][1] == 0){
			nodeslist.add(moveBlankDown(1, 1));
			nodeslist.add(moveBlankRight(1, 1));
			nodeslist.add(moveBlankUp(1, 1));
			nodeslist.add(moveBlankLeft(1, 1));
			//System.out.println("case 5");
			
		//If the '0' (blank spot) is at board[1][2], we can either move the blank 
		//down, up or to the left.
		}else if(board[1][2] == 0){
			nodeslist.add(moveBlankDown(1, 2));
			nodeslist.add(moveBlankLeft(1, 2));
			nodeslist.add(moveBlankUp(1, 2));
			//System.out.println("case 6");

		//If the '0' (blank spot) is at board[2][0], we can either move the blank 
		//up or to the right.
		}else if(board[2][0] == 0){
			nodeslist.add(moveBlankUp(2, 0));
			nodeslist.add(moveBlankRight(2, 0));
			//System.out.println("case 7");
		
		//If the '0' (blank spot) is at board[2][1], we can either move the blank 
		//up, to the right or to the left.
		}else if(board[2][1] == 0){
			nodeslist.add(moveBlankUp(2, 1));
			nodeslist.add(moveBlankLeft(2, 1));
			nodeslist.add(moveBlankRight(2, 1));
			//System.out.println("case 8");
			
		//If the '0' (blank spot) is at board[2][2], we can either move the blank 
		//up or to the left.
		}else if(board[2][2] == 0){
			nodeslist.add(moveBlankUp(2, 2));
			nodeslist.add(moveBlankLeft(2, 2));
			//System.out.println("case 9");
		}
		
		return nodeslist;
	}
	
	
	
	/**
	 * Moves the blank down by swapping the '0' with the entry below it
	 */
	public int[][] moveBlankDown(int row, int col)
	{
		int[][] newboard = new int[size][size];
		newboard = copyBoard();
		
		if(row+1<size)
			swap(newboard, row, col, row+1, col);
		else
			System.out.println("row out of bounds in moveBlankDown: "+row);

		
		return newboard;
	}
	
	
	/**
	 * Moves the blank up by swapping the '0' with the entry above it
	 */
	public int[][] moveBlankUp(int row, int col)
	{
		int[][] newboard = new int[size][size];
		newboard = copyBoard();
		
		if(row-1>=0)
			swap(newboard, row, col, row-1, col);
		else
			System.out.println("row out of bounds in moveBlankUp: "+row);
		
		return newboard;
	}
	
	
	
	/**
	 * Moves the blank right by swapping the '0' with the entry right of it
	 */
	public int[][] moveBlankRight(int row, int col)
	{
		int[][] newboard = new int[size][size];
		newboard = copyBoard();
		
		if(col+1<size)
			swap(newboard, row, col, row, col+1);
		else
			System.out.println("col out of bounds in moveBlankRight: "+col);
		
	
		
		return newboard;
	}
	
	
	/**
	 * Moves the blank left by swapping the '0' with the entry left of it
	 */
	public int[][] moveBlankLeft(int row, int col)
	{
		int[][] newboard = new int[size][size];
		newboard = copyBoard();
		
		if(col-1>=0)
			swap(newboard, row, col, row, col-1);
		else
			System.out.println("row out of bounds in moveBlankLeft: "+col);
		
		
		return newboard;
	}
	
	
	/*
	 * Prints the board configuration of the given Node
	 */
	public void print()
	{
		int[][] brd = this.getboard();
		
		for(int i=0; i<size; i++)
		{
			for(int j=0; j<size; j++){
				if(brd[i][j]==0){
					System.out.print("  ");
					continue;
				}
				if (brd[i][j] <10)
					System.out.print(" ");
				System.out.print(brd[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
    

    /*
     * Method to determine if two states are equal, where a state is a node or a board.
     * Parameter o can either be a Node or an int[][] (i.e. the board)
     */
    public boolean equals(Object o)
    {
    	//if the object to compare is a Node
    	if(o.getClass() == this.getClass()){
    		
    		if(this.toString().equals( ((Node)o).toString()))
    	   		return true;
    	  	else
    	  		return false;
    	}
    	//if the object to compare is an int[][]
    	else if(o.getClass() == this.board.getClass()){
    		if(isSameBoard((int[][]) o))
    			return true;
    		else
    			return false;
    	}
    	//if the object to compare is something weird (code shouldn't come here!)
    	else{
    		System.out.println("something weird");
    		return false;
    	}
    }
	
    /**
     * Returns the String representation of this node's state.
     */
    public String toString() {
        String sb = "";
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
               
                if (board[i][j] == 0) {
                    sb += "  ";
                    continue;
                }
                if (board[i][j] < 10) {
                    sb += " ";
                }
                sb+=board[i][j];
            }
            sb+="\n";
        }
        return sb;
    }
    
	
    /****Private Helper Methods****/
    
	/**
	 * Helper method for the moveBlank methods
	 */
	private int[][] copyBoard()
	{
		int[][] newboard = new int[size][size];
		
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++)
				newboard[i][j] = board[i][j];
		}
		
		return newboard;
	}

	
	
	/**
	 * Helper method for the moveBlank methods
	 */
	private void swap(int[][] node, int x1, int y1, int x2, int y2)
	{
		int tmp = node[x1][y1];
		node[x1][y1] = node[x2][y2];
		node[x2][y2] = tmp;
		
	}
	
	
    //------------------A* Code Begins Here-------------------------//
    
    private double gvalue; //To be used by A*Star search
    private double hvalue; //To be used by A*Star search
    
    
    /**
     * Constructor to be used by A*Star search only.
     * par - The parent node.
     * gval - The g-value for this node.
     * hval - The h-value for this node.
     * brd - The board which should be a 3x3 array of integers from 0 to 8. 
     */
    public Node(Node par, double gval, double hval, int[][] brd) {
    	
    	
    	this.parent = par;
    	this.gvalue = gval;
    	this.hvalue = hval;
        this.board = (int[][]) brd.clone();
        
    }


    
    /*************
     ********** Setter and Getter methods for A* Search variables **********
     *************/
    public void setgvalue(double g)
    {
    	this.gvalue = g;
    }
    
    public double getgvalue()
    {
    	return this.gvalue;
    }

    public void sethvalue(double h)
    {
    	this.hvalue = h;
    }
    
    public double gethvalue()
    {
    	return this.hvalue;
    }
    
    
    /**
     * Used by A* Search only.
     * Returns the heuristic value. The heuristic for the state of this node is the sum of Manhattan
     * distances from each tile's position to that tile's final position.
     */
   public double evaluateHeuristic() {
	   double total = 0;
	   int[] idealPos = new int[2];
	   
	   //loops through all digits of board and checks their position relative to the ideal state
	   //uses the current positions to calculate Manhattan distance
	   for (int i=0; i<size; i++){
		   for (int j=0; j<size; j++){
		 	   int curr = this.board[i][j];
		 	   if (curr ==0)
				   continue;
			   else if (curr < 9) {
				   idealPos = rowCol(curr);
				   total += getManhattanDistance(i, j, idealPos[0], idealPos[1]);	
			   }
		   }
	   }
	   return total;
    }
   
   /**
    * Returns the heuristic value by adding the number of digits that are in the wrong row to those
    * that are in the wrong column
    */
   public double myHeuristic() {
	   double total = 0;
	   
	   //if current node is goal, reduces the hvalue so it recieves a higher priority
	   if (this.isGoal()) 
		   return (total - 1000);
	   
	   //arraylist to contain sections being checked for conflicts
	   ArrayList<Integer> arr = new ArrayList<Integer>();
	   
	   //loops through rows and checks each for collisions
	   for (int r=0; r<size; r++) {
		   String section = "";
		   
		   switch (r) {
		   case 0:
			   section = "r0";
			   break;
		   case 1:
			   section = "r1";
			   break;
		   case 2:
			   section = "r2";
			   break;  
		   }
		   for (int c=0; c<size; c++) 
			   arr.add(this.board[r][c]);
		   total += conflicts(arr, section);
		   arr.clear();
	   }
	   
	   //loops through columns and checks each for collisions
	   for (int c=0; c<size; c++) {
		   String section = "";
		   
		   switch (c) {
		   case 0:
			   section = "c0";
			   break;
		   case 1:
			   section = "c1";
			   break;
		   case 2:
			   section = "c2";
			   break;  
		   }
		   for (int r=0; r<size; r++) 
			   arr.add(this.board[r][c]);
		   total += conflicts(arr, section);
		   arr.clear();
	   }	   
	   
	   //returns collisions plus Manhattan distance
	   return (total+this.evaluateHeuristic());
   }
   
   /**
    * Helper method for myHeuristic that checks for and returns the conflicts in each of
    * the rows and the columns
    */
   public int conflicts(ArrayList<Integer> arr, String section) {
	   int conflicts = 0;
	   
	   switch (section) {
	   //checks for conflict on first row
	   case "r0":
		   if (arr.contains(1)) {
			   if (arr.contains(2) && (arr.indexOf(1) > arr.indexOf(2))) 
				   conflicts++;
			   if (arr.contains(3) && (arr.indexOf(1) > arr.indexOf(3)))
				   conflicts++;
		   }
		   if (arr.contains(2) && arr.contains(3) && arr.indexOf(2) > arr.indexOf(3))
			   conflicts++;
		   break;
		   
		//checks for conflict on second row
	   	case "r1":
		   if (arr.contains(4)) {
			   if (arr.contains(5) && (arr.indexOf(4) > arr.indexOf(5))) 
				   conflicts++;
			   if (arr.contains(6) && (arr.indexOf(4) > arr.indexOf(6)))
				   conflicts++;
		   }
		   if (arr.contains(5) && arr.contains(6) && arr.indexOf(5) > arr.indexOf(6))
			   conflicts++;
		   break;
		   
		//checks for conflict on last row
	   	case "r2":
			   if (arr.contains(7)) {
				   if (arr.contains(8) && (arr.indexOf(7) > arr.indexOf(8))) 
					   conflicts++;
				   if (arr.contains(9) && (arr.indexOf(7) > arr.indexOf(9)))
					   conflicts++;
			   }
			   if (arr.contains(8) && arr.contains(9) && arr.indexOf(8) > arr.indexOf(9))
				   conflicts++;
			   break;

	    //checks for conflict on first column	   
	   	case "c0":
			   if (arr.contains(1)) {
				   if (arr.contains(4) && (arr.indexOf(1) > arr.indexOf(4))) 
					   conflicts++;
				   if (arr.contains(7) && (arr.indexOf(1) > arr.indexOf(7)))
					   conflicts++;
			   }
			   if (arr.contains(4) && arr.contains(7) && arr.indexOf(4) > arr.indexOf(7))
				   conflicts++;
			   break; 
			   
	    //checks for conflict on second column			   
	   	case "c1":
			   if (arr.contains(2)) {
				   if (arr.contains(5) && (arr.indexOf(2) > arr.indexOf(5))) 
					   conflicts++;
				   if (arr.contains(8) && (arr.indexOf(2) > arr.indexOf(8)))
					   conflicts++;
			   }
			   if (arr.contains(5) && arr.contains(8) && arr.indexOf(5) > arr.indexOf(8))
				   conflicts++;
			   break;
			   
	   //checks for conflict on last column			   
	   	case "c2":
			   if (arr.contains(3)) {
				   if (arr.contains(6) && (arr.indexOf(3) > arr.indexOf(6))) 
					   conflicts++;
				   if (arr.contains(9) && (arr.indexOf(3) > arr.indexOf(9)))
					   conflicts++;
			   }
			   if (arr.contains(6) && arr.contains(9) && arr.indexOf(6) > arr.indexOf(9))
				   conflicts++;
			   break;
		   
	   }
		   
	   return conflicts;
   }
    
    /**
     * Helper method used by A* Search only. 
     * Returns the Manhattan distance between the given two cells of the 4x4 board.
     */
    private static int getManhattanDistance(int row1, int col1, int row2, int col2) {
        return Math.abs(row1 - row2) + Math.abs(col1 - col2);
    }
  
    
    /**
     * Helper method for evaluateHeuristic and myHeuristic that returns the goal position of the 
     * current digit
     */
    public int[] rowCol(int curr) {
    	int[] goalPos = new int[2];
    	
    	//returns each digit's ideal position
    	switch (curr) {
    	case 1:
    		goalPos[0] = 0;
    		goalPos[1] = 0;
    		return goalPos;
    	case 2:
    		goalPos[0] = 0;
    		goalPos[1] = 1;
    		return goalPos;
    	case 3:
    		goalPos[0] = 0;
    		goalPos[1] = 2;
    		return goalPos;
    	case 4:
    		goalPos[0] = 1;
    		goalPos[1] = 0;
    		return goalPos;
    	case 5:
    		goalPos[0] = 1;
    		goalPos[1] = 1;
    		return goalPos;
    	case 6:
    		goalPos[0] = 1;
    		goalPos[1] = 2;
    		return goalPos;
    	case 7:
    		goalPos[0] = 2;
    		goalPos[1] = 0;
    		return goalPos;
    	case 8:
    		goalPos[0] = 2;
    		goalPos[1] = 1;
    		return goalPos;
    	}
    	return goalPos;

    }
    
  
}
    

/**
 * Astar comparator that uses f values of nodes
 * @author Smith Gakuya
 *
 */
class AstarComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		// TODO Auto-generated method stub
		double f1 = o1.getgvalue() + o1.gethvalue();
		double f2 = o2.getgvalue() + o2.gethvalue();
		if (f1 > f2)
			return 1;
		else if (f1 < f2)
			return -1;
		return 0;
	}
	
}
