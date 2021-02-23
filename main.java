/* @author Xiongfei Zhang
 * @version 1.0
 */


//imports all of the util class
import java.util.*;
import java.lang.Math; 

public class main {
	
	


//randomize board method
public static int[][] initBoard()
	{
	int[][] board = new int[8][8];
		for(int i = 0; i < 8; i++)
		{
			for(int x = 0; x < 8; x++)
			{
				board[i][x] = 0; 
			}
		}
		//insert queens into random columns
		for(int i = 0; i < 8; i++)
		{
			int random = (int)(Math.random()*8);
			for(int x = 0; x < 8; x++)
			{
				board[random][i] = 1;
				
			}
		}
		return board;
	}
	
public static void printOutput(int[][] board, int h)
{
	//displays board
	System.out.println();
	System.out.println();
	System.out.println("Current h: " + h);
	System.out.println("Current State");
	for(int i = 0; i < 8; i++)
	{
		for(int x = 0; x < 8; x++)
		{
			System.out.print(board[i][x] + "  ");

		}
		System.out.println();
	}
}

public static int checkConflict(int[][] board)
{
	int h = 0;
	for(int i = 0; i < 8; i++)
	{
		for(int x = 0; x < 8; x++)
		{
			int sumRow = 0;
			int sumDiag1 = 0;
			int sumDiag2 = 0;
			//if column position is a queen 
			if(board[x][i] == 1)
			{
				//check for conflicts in row
				for(int k = i; k < 8;k++)
				{
					sumRow+= board[x][k];
				}

				//adds number of conflicts to h
				if(sumRow > 1)
				{
					h += 1;
				}
				
				

				//check diagonal conflict going bottom right
				int diagCounter;
				int counterA= 7-x;
				int counterB = 7-i;
				if(counterA > counterB)
				{
					diagCounter = counterB;
				}
				else 
				{
					diagCounter = counterA;
				}
				for(int k = 0; k <= diagCounter; k++ )
				{
					sumDiag1 += board[x+k][i+k];
				}
				//if more than 1 queen found on diagonal add 1 to counter
				if(sumDiag1 > 1)
				{
					h += 1;
				}

				//check diagonal conflict going bottom left
				int diagCounter2;
				//board[x][i] x goes up i goes down
				int counterA2= 7-x;
				int counterB2 = i;
				if(counterA2 > counterB2)
				{
					diagCounter2 = counterB2;
				}
				else 
				{
					diagCounter2 = counterA2;
				}

				
				for(int k = 0; k <=diagCounter2; k++ )
				{
					//sumDiag1 += board[x+k][i+k];

					sumDiag2 += board[x+k][i-k];
				}
				//if more than 1 queen found on diagonal add 1 to counter
				if(sumDiag2 > 1)
				{
					h += 1;
					
				}
				
			}
			
		}

	}
	return h;
}
	
public static int[][] checkColumn(int[][] board, int h)
{
	int[][] newBoard = board;
	int tempH = h;
	int lowestH = h;
	int rowPosition = 0;
	int originalSpot=0;
	int lowerNeighbors = 0;
	
	
	
	//loops through columns
	for(int column = 0; column < 8; column++)
	{
		//removes queen in column
		for(int row = 0; row < 8; row++)
		{
			if(newBoard[row][column] == 1)
			{
				originalSpot = row;
			}
			newBoard[row][column] = 0;
		}
		
		//places queen in first row
		newBoard[0][column]= 1;

		//checks h value of first row 
		tempH = checkConflict(newBoard);
		
		if(tempH < lowestH)
		{
			lowestH = tempH;
			rowPosition = 0;
		}
		//loop to place and check h value of queen in each row
		for(int row = 1; row < 8; row++)
		{

			newBoard[row-1][column] = 0;
			newBoard[row][column] = 1;
			tempH = checkConflict(newBoard);
			


			if(tempH < lowestH)
			{
				lowerNeighbors++;
				if(tempH == 0)
				{
					
					return newBoard;
				}
				lowestH = tempH;
				rowPosition = row;
			}

		}
		newBoard[7][column] = 0;
		System.out.println("Neighbors found with lower h: " + lowerNeighbors);

		if(lowerNeighbors == 0)
		{

			System.out.println("RESTART");
			newBoard = initBoard();
			tempH = checkConflict(newBoard);
			newBoard = checkColumn(newBoard, tempH);
			return newBoard;
		}
		else if(tempH ==lowestH)
		{
			System.out.println("Setting new current state");
			newBoard[originalSpot][column] = 1;
		}
		else
		{
			//removes queen from last spot
			//places queen in lowest h value spot
			System.out.println("Setting new current state");
			newBoard[rowPosition][column] = 1;
		}
		
		printOutput(newBoard, lowestH);
		System.out.println("Setting new current state");
		
		lowestH = h;
		rowPosition = 0;
		lowerNeighbors = 0;
	}

	return newBoard;
}
public int addState(int state)
{
	state+= 1;
	return state;
}
public int addRestart(int restart)
{
	restart+= 1;
	return restart;
}


public static void main(String[] args) 
	{
		//creates first board
		int[][] board = new int[8][8];
		board = initBoard();
		int h = checkConflict(board);
		printOutput(board, h);
		
		
		
		int restart = 0;
		while(h != 0)
		{
			
			board = checkColumn(board, h);
			h = checkConflict(board);
			restart++;
		}
		
		printOutput(board, h);
		System.out.println("Solution Found!");

		System.out.println("Restarts: " + restart );
	}

}
