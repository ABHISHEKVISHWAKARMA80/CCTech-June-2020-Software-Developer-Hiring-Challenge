// Program 1 : Check if the given point lies inside or outside a polygon?

import java.util.Scanner;

public class Program1
{ 
	static double INF = 10000; //Define Infinite

	static class Coordinate // class represent coordinate
	{ 
		double x; // x coordinate
		double y; // y coordinate

		public Coordinate(double x, double y) // assign value to coordinates by constructor
		{ 
			this.x = x; 
			this.y = y; 
		} 
	}; 
		
	// Write a function that takes two arguments as a input and return True if 'p' lies inside the polygon else False
	static boolean check_point_inside_or_not(Coordinate polygon[], Coordinate p) // Returns true if the point p lies inside the polygon
	{ 
		int num_vertices = polygon.length; 
		
		// There must be at least 3 vertices in polygon
		if (num_vertices < 3) 
		{ 
			return false; 
		} 

		// Create a point for line segment from p to infinite 
		Coordinate extreme = new Coordinate(INF, p.y); 

	 // Count intersections of the above line with sides of polygon
		int count = 0, i = 0; 
		do
		{ 
			int next = (i + 1) % num_vertices; 

			// Check if the line segment from 'p' to 'extreme' intersects with the line segment from 'polygon[i]' to 'polygon[next]
			if (lines_Intersects_or_not(polygon[i], polygon[next], p, extreme)) 
			{ 
				// If the point 'p' is colinear with line segment 'i-next', then check if it lies on segment. If it lies, return true, otherwise false 
				if (check_orientation(polygon[i], p, polygon[next]) == 0) 
				{ 
					return lies_on_Segment_or_not(polygon[i], p, 
									polygon[next]); 
				} 
				count++; 
			} 
			i = next; 
		} while (i != 0); 

		// Return true if count is odd, false otherwise 
		return (count % 2 == 1);
	} 
	
	 // The function that returns true if line segment 'a1b1' and 'a2b2' intersect. 
		static boolean lines_Intersects_or_not(Coordinate a1, Coordinate b1, Coordinate a2, Coordinate b2) 
		{ 
			// Find the four orientations
			int orientation1 = check_orientation(a1, b1, a2); 
			int orientation2 = check_orientation(a1, b1, b2); 
			int orientation3 = check_orientation(a2, b2, a1); 
			int orientation4 = check_orientation(a2, b2, b1); 

			// General case 
			if ( orientation3 != orientation4 && orientation1 != orientation2) 
				return true; 
			
			// a1, b1 and a2 are colinear and a2 lies on segment a1b1 
			if (lies_on_Segment_or_not(a1, a2, b1) && orientation1 == 0)  
				return true; 
			

			// a1, b1 and b2 are colinear and b2 lies on segment a1b1 
			if (lies_on_Segment_or_not(a1, b2, b1) && orientation2 == 0) 
				return true; 
		
 
			if ( lies_on_Segment_or_not(a2, a1, b2) && orientation3 == 0) 
				return true; 

			if (lies_on_Segment_or_not(a2, b1, b2) && orientation4 == 0) 
				return true; 

			return false; 
		} 

		
		// To find orientation of ordered triplet (a, b, c). 
		static int check_orientation(Coordinate a, Coordinate b, Coordinate c) 
		{ 
			double value = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y); 

			if (value == 0) 
			{ 
				return 0; // 0 --> a, b and c are colinear  
			} 
			return (value > 0) ? 1 : 2; //  1 --> Clockwise, 2 --> Counterclockwise 
		} 


	// Given three colinear points a, b, c, the function checks if point b lies on line segment 'ac' 
	static boolean lies_on_Segment_or_not(Coordinate a, Coordinate b, Coordinate c) 
	{ 
	return b.x >= Math.min(a.x, c.x) && b.x <= Math.max(a.x, c.x) &&  b.y <= Math.max(a.y, c.y) && b.y >= Math.min(a.y, c.y) ? true :false;	
	} 
	
	public static void main(String[] args) 
	{ 
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the number of sides/vertices of polygon");
		int vertices = sc.nextInt(),n=vertices,itr = 0;
		
		Coordinate polygon[] = new Coordinate[vertices];
		
		System.out.println("Enter the coordinates for vertex");
          while(n-->0) {
        	  polygon[itr] = new Coordinate(sc.nextDouble(), sc.nextDouble()); 
        	  itr++;
		        }
          
         System.out.println("Enter the coordinate which need to check");
          Coordinate p = new Coordinate(sc.nextDouble(), sc.nextDouble()); 
          
          System.out.println(check_point_inside_or_not(polygon,p)?"True":"False" ); 
	} 
} 
