# 8-Puzzle Solver (BFS & A* Search)

Intelligent solver for 8-Puzzle using Searches i.e. Breadth First Search and A* Search 

## Authors: 
Smith Gakuya & Yaqi Huang

## Tests
__Case 2__      
//string format: 123456078   
brd[0][0] = 1; brd[0][1] = 2; brd[0][2] = 3; brd[1][0] = 4; brd[1][1] = 5; brd[1][2] = 6; brd[2][0] = 0; brd[2][1] = 7; brd[2][2] = 8; 		

__Case 3__    
//string format: 123460758  	    	   
brd[1][2] = 0;  brd[0][0] = 1; brd[0][1] = 2; brd[0][2] = 3; brd[1][0] = 4; brd[2][1] = 5;  brd[1][1] = 6;  brd[2][0] = 7;  brd[2][2] = 8;		

__Case 4__		
//string format: 412053786			
brd[0][0] = 4;	brd[0][1] = 1;	brd[0][2] = 2;	brd[1][0] = 0;	brd[1][1] = 5;	brd[1][2] = 3;	brd[2][0] = 7;	brd[2][1] = 8;	brd[2][2] = 6;		

__Case 5__		
//string format: 412763058			
brd[0][0] = 4;	brd[0][1] = 1;	brd[0][2] = 2;	brd[1][0] = 7;	brd[1][1] = 6;	brd[1][2] = 3;	brd[2][0] = 0;	brd[2][1] = 5;	brd[2][2] = 8;		

__Case 6__		
//string format: 412763580			
brd[0][0] = 4;	brd[0][1] = 1;	brd[0][2] = 2;	brd[1][0] = 7;	brd[1][1] = 6;	brd[1][2] = 3;  brd[2][0] = 5;  brd[2][1] = 8;	brd[2][2] = 0;		

__Case 7__			
//string format: 134805726		
brd[0][0] = 1;	brd[0][1] = 3;  brd[0][2] = 4;	brd[1][0] = 8;	brd[1][1] = 0;	brd[1][2] = 5;	brd[2][0] = 7;	brd[2][1] = 2; brd[2][2] = 6;			

__Case__	__Number of moves__	    __Number of nodes generated__
			                    _BFS_    _A* (Manhattan)_      _A* (Other)_ 
                                                                 
 1.	            12		            2038	    41			39
 2.	             2		             14	     	     4			4
 3.	             3		             24	     	     8			8
 4.	             5		             99	     	     9			9
 5.	             8		            342		    15			15	
 6.	            10		            1098	    23			20
 7.	        unsolvable			
Average for all iterations	          
(should be the same)	158429		23683			40948


## EightPlayer.java         
_______________________________________		
> _void printSolution(Node node)_	
			
Recursively loops through the parent nodes until it reaches the root(i.e. the initial state) then
prints the root node. Prints nodes on the way as it traces back. 
Used regular recursion since we only go up to a maximum of 13 levels hence doesn't require too much 
space

_______________________________________		
> _boolean runBFS(Node initNode)_   	
	
Uses a queue to store nodes in Frontier and an ArrayList for Explored nodes.		
If the initial node is the solution then prints it out and returns true.		

While there are nodes in the Frontier, it removes the first one and adds it to Explored List, we then
check if the node we have explored has a depth of 13, if so we return false and assume no solution is 
present.		
If it is above the 13th level, we check if it is the goal state(if so print it out and return true).		
If not, we expand the node and add the successors to the back of the Frontier queue if they are neither in the Frontier nor the Explored List. 		
We then increase the number of nodes if we add a new node to the Frontier.

________________________________________________________
> _boolean runAStar(Node initNode, int heuristic)_	
	
We used a priority queue for the Frontier and made the ordering to be based on the fvalues (gval+hval). 		
We had to override the compare method so as to allow this ordering instead of natural ordering		
We set the initNode's gvalue to 0.		
If the heuristic chosen is:		
  0) We calculate the Manhattan distance for each digit on the board and use it as the hvalue		
  1) We calculate the number of collisions and add that to Manhattan distance and use that as the hvalue		
We then check if the initNode is the goal		

If not and if the Frontier still isn't empty, we remove the first element of the Priority Queue and add it to Explored		
If the gvalue is greater than 13 we return false		
If the current node is the goal we return true		
If the above conditions aren't met, we expand the node		

Depending on the heuristic choice, we do as stipulated above.		
If the current node is in the Explored list, we make a copy of the Frontier to loop through(we noticed we got an error when we looped through and altered contents of the Frontier hence copied it)		
If we find that the node in the Frontier has a higher fval than our current node, we replace it with the node which has the updated details		

If node is not in frontier, we add it and increment numnodes		


## Node.java
_____________________________________		
> _ArrayList<int[][]> expand()_	
	
if the blank tile is at 0,1 we can move it down, right or left		
if the blank tile is at 0,2 we can move it down or left		
if the blank tile is at 1,0 we can move it down, right or up		
if the blank tile is at 1,1 we can move it up, down, right or left		
if the blank tile is at 1,2 we can move it up, down or left		
if the blank tile is at 2,0 we can move it up or right 		
if the blank tile is at 2,1 we can move it up, right or left		
if the blank tile is at 2,2 we can move it up or left		

____________________________________		
> _double evaluateHeuristic()_		

We loop through each position of the current board state checking for the digits' ideal positions if the current state isn't the goal state.		
For each digit we get the manhattan distance to the goal position and add it to the total which we retun in the end		

______________________________
> _double myHeuristic()_		

If the current state is the goal state, we set the hval to be -1000 so that it receives a higher priority and is evaluated 
before the other members of the Frontier if it did not already have the lowest fval		

We then check for collisions row by row and column by column using the helper method conflicts(ArrayList<Integer>, String)		
When all the collisions are recorded, we add them to the Manhattan distance and use this as the hval instead		

_______________________________________________________________
> _int conflicts(ArrayList<Integer> arr, String section)_		

For each row and each column we check if the digits on the specified row or column are supposed to be in the row/column		
We then check if in each of them, these digits that are supposed to be there are in the wrong order i.e. in conflict with each
other		
We then return the total number of conflicts		

______________________________
> _int[] rowCol(int curr)_		

Returns the goal position of the specified current digit curr		


### _class AstarComparator implements Comparator<Node>_		
_______________________________________
@Override		
> _int compare(Node o1, Node o2)_		

We override how the nodes are compared when being stored in the PriorityQueue for Astar, this way they are ordered according to 
their f values with priority being given to the node with a lower fvalue		























