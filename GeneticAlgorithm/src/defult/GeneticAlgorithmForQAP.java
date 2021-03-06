package defult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithmForQAP 
{
	static Random randomGenerator;
	static  int[][] arrayOfSolutions;
	static  int[][] children;
	static int n;
	static int childCounter;
	static int g_max;
	static double Pc;
	static double Pm;
	
	static int [][] distance = { 
		{0,1,2,3,1,2,3,4,2,3,4,5},
		{1,0,1,2,2,1,2,3,3,2,3,4},
		{2,1,0,1,3,2,1,2,4,3,2,3},
		{3,2,1,0,4,3,2,1,5,4,3,2},
		{1,2,3,4,0,1,2,3,1,2,3,4},
		{2,1,2,3,1,0,1,2,2,1,2,3},
		{3,2,1,2,2,1,0,1,3,2,1,2},
		{4,3,2,1,3,2,1,0,4,3,2,1},
		{2,3,4,5,1,2,3,4,0,1,2,3},
		{3,2,3,4,2,1,2,3,1,0,1,2},
		{4,3,2,3,3,2,1,2,2,1,0,1},
		{5,4,3,2,4,3,2,1,3,2,1,0},
		};
	
static int [][] flow = { 
		{0,5,2,4,1,0,0,6,2,1,1,1},
		{5,0,3,0,2,2,2,0,4,5,0,0},
		{2,3,0,0,0,0,0,5,5,2,2,2},
		{4,0,0,0,5,2,2,10,0,0,5,5},
		{1,2,0,5,0,10,0,0,0,5,1,1},
		{0,2,0,2,10,0,5,1,1,5,4,0},
		{0,2,0,2,0,5,0,10,5,2,3,3},
		{6,0,5,10,0,1,10,0,0,0,5,0},
		{2,4,5,0,0,1,5,0,0,0,10,10},
		{1,5,2,0,5,5,2,0,0,0,5,0},
		{1,0,2,5,1,4,3,5,10,5,0,2},
		{1,0,2,5,1,0,3,0,10,0,2,0},
		};
	
	public static void main(String[] args)
	{
	    n = 200;
		Pc = 1.0;
		Pm= 0.4;
		g_max = 5000;	
		
	    childCounter=0;
	    
	    arrayOfSolutions = new int[n][12];
	    children = new int[n][12];
	    randomGenerator = new Random();
	    System.out.println("GA Started ..." + "(n,Pc,Pm,g_max) = " + n + "," + Pc + "," + Pm + "," + g_max );
	    
	    for (int c = 1 ; c < 5 ; c++)
	    {
		    GA_Algorithim(); // This calls the GA Algorithm Code
		    
		    // This part summarizes the GA results and prints it
		    double x;
		    double x2 = fParent(0);
		    int loc = 0;
		    
		    for (int k = 0; k < n; k++)
			{
		    	x = (fParent(k));
		    	
		    	if(x < x2 )
		    	{
		    		x2 = x;
		    		loc = k;
		    	}
			}
		    System.out.println("=============================");
		    System.out.println("Run " + c + ", Best is " + x2 );
		    for (int k = 0; k < 12; k++)
		    {
		    System.out.print(arrayOfSolutions[loc][k] + " , ");
		    }
		    System.out.println("");
	    }
	}
	
	public static double fParent(int x) 
	{
		// Represents the objective function f(x), Calculate the coast from that department to all departments
		// for the parent array
		int cost = 0;
		int distanceBetweenLocations = 0;
		int flowBetweenDepartments = 0;

		for(int i = 0; i < 12; i++)
		{
			for(int j = 0; j < 12; j++)
			{
				distanceBetweenLocations = distance[i][j];
				flowBetweenDepartments = flow[arrayOfSolutions[x][i]-1][arrayOfSolutions[x][j]-1];	
				cost = cost + distanceBetweenLocations*flowBetweenDepartments;
			}			
		}	

		return cost;
	}
	
	public static double fChild(int x) 
	{
		// Represents the objective function f(x), Calculate the coast from that department to all departments
		// for the child array
		int cost = 0;
		int distanceBetweenLocations = 0;
		int flowBetweenDepartments = 0;
	
		for(int i = 0; i < 12; i++)
		{
			for(int j = 0; j < 12; j++)
			{
				distanceBetweenLocations = distance[i][j];;
				flowBetweenDepartments = flow[children[x][i]][j];		
				cost = cost + distanceBetweenLocations*flowBetweenDepartments;
			}		
		}	
		return cost;
	}
	
	static public void GenerateRandomSolutions()
	{ 
		 // Generate Initial random numbers and fill the array
		 ArrayList<Integer> shuffeList = new ArrayList<Integer>();
		 for (int i =0;i <n;i++)
		 {
			for (int j =0;j <12;j++)
			{
				shuffeList.add(j+1);
			}
			Collections.shuffle(shuffeList, randomGenerator);
			for (int k =0;k <12;k++)
			{
				arrayOfSolutions[i][k] = shuffeList.get(k);
			}
			shuffeList.clear();
		 }
	}
	
	public static void Print(int[][] array)
	{// Prints the array 
		for (int i =0;i < n;i++)
		{
		System.out.println(i + 
	    ": " + array[i][0] + " " + array[i][1] + " " + array[i][2]+
	    " " + array[i][3] + " " + array[i][4] + " " + array[i][5]+
	    " " + array[i][6] + " " + array[i][7] + " " + array[i][8]+
	    " " + array[i][9] + " " + array[i][10]+ " " + array[i][11]);
		}
	}
	
	public static void CrossOver(int x, int y)
	{ // This function performs Crossover 
		int crossOverLocation = randomGenerator.nextInt(12); 
		ArrayList<Integer> temp1 = new ArrayList<Integer>();
		ArrayList<Integer> temp2 = new ArrayList<Integer>();
		int temp3 = crossOverLocation;
		
		// Copy the left hand part as it is
		for (int i =0;i < crossOverLocation;i++)
		{
			children[childCounter][i] = arrayOfSolutions[x][i];
			temp1.add(arrayOfSolutions[x][i]);
			children[childCounter+1][i] = arrayOfSolutions[y][i];
			temp2.add(arrayOfSolutions[y][i]);
		}
		
		// Copy the rest of the parent 2 in child 1
		for (int i =0;i < 12;i++)
		{
			if(!(temp1.contains(arrayOfSolutions[y][i])))
			{
				children[childCounter][temp3] = arrayOfSolutions[y][i];
				temp3++;
			}
		}
		temp3 = crossOverLocation;
		// Copy the rest of the parent 2 in child 1
		for (int i =0;i < 12;i++)
		{
			if(!(temp2.contains(arrayOfSolutions[x][i])))
			{
				children[childCounter+1][temp3] = arrayOfSolutions[x][i];
				temp3++;
			}
		}	
	}
	
	public static void Mutation (int x)
	{
		// This function does Mutation based on the scramble method
		int L1 = randomGenerator.nextInt(12);
		int L2 = randomGenerator.nextInt(12);
		
		while ( L1 == L2 ) // make sure that l1 and l2 are have different values
		{
			L1 = randomGenerator.nextInt(12);
			L2 = randomGenerator.nextInt(12);
		}
		
		ArrayList<Integer> temp1 = new ArrayList<Integer>();
		
		if(L1 > L2)
		{
			int tmp = L2;
			L2 = L1;
			L1 = tmp;
		}
		
		for (int i =L1;i < L2;i++)
		{
			temp1.add(children[x][i]);
		}
		
		Collections.shuffle(temp1, randomGenerator); // do the scramble
		
		int h=0;
		
		for (int i =L1;i < L2;i++)
		{
			children[x][i] = temp1.get(h);
			h++;
		}
		
	}
	
	public static void Mutation2Opt (int x)
	{
		// Do Mutation based on 2-Opt 
				int L1 = randomGenerator.nextInt(12);
				int L2 = randomGenerator.nextInt(12);
				
				while ( L1 == L2 ) // make sure that l1 and l2 are have different values
				{
					L1 = randomGenerator.nextInt(12);
					L2 = randomGenerator.nextInt(12);
				}
				
				int tmp = children[x][L2];
				children[x][L2] = children[x][L1];
				children[x][L1] = tmp;
		
	}
	public static int Tournament()
	{
		// Select two parents, use Tournament , k = 3 
		
		int tmp1 = randomGenerator.nextInt(n); // java will generate integer numbers between 0 and n including 0 and n
		int tmp2 = randomGenerator.nextInt(n);
		int tmp3 = randomGenerator.nextInt(n);
		
		int best = -1;
		
		if (fParent(tmp1) < fParent(tmp1) )
		{
			if ( fParent(tmp1) < fParent(tmp3)  )
			{
				best = tmp3;
			}
		}
		else 
		{
			{
				if ( fParent(tmp2) < fParent(tmp1) )
				{
					best = tmp2;
				}
			}
		}
		if ( best ==  -1)
		{
			best = tmp3;
		}
		return best;
		
	}
	
	public static void GA_Algorithim()
	{ // This is the GA Algorithom code
		GenerateRandomSolutions();
		
		for (int i =0;i < g_max;i++)
		{
			for (int j = 0; j < n/2; j++)
			{ 
				// Select two parents based on two different Tournaments
				int parent1 = Tournament();
				int parent2 = Tournament();
				
				if (randomGenerator.nextDouble() <= Pc) // check probability of crossover
				{	
					//  Cross over Parent1, Parent2 and produce the children
					CrossOver(parent1, parent2);
					
				} 	
				else 
				{ // No Crossover
					for (int f =0;f < 12;f++)
					{
						children[childCounter][f] = arrayOfSolutions[parent1][f];
						children[childCounter+1][f] = arrayOfSolutions[parent2][f];			
					}	
					
				}
				//Mutate the children			    
			    double mutatePropability =  randomGenerator.nextDouble();
			    	
			    	if ( mutatePropability <= Pm)
			    	{
			    		Mutation2Opt(childCounter);	
			    	}		
			    	
				    mutatePropability =  randomGenerator.nextDouble();
				    
				    if ( mutatePropability <= Pm)
			    	{
				    	Mutation2Opt(childCounter+1);	
			    	}
			    }
			childCounter = childCounter +2;
			childCounter = childCounter % (n-1);

			}	
			// Replace parents by children	
			for (int k = 0; k < n; k++)
			{
				for (int j = 0; j < 12; j++)
				{
					arrayOfSolutions[k][j] = children[k][j];
				}
			}	
		}
	}


