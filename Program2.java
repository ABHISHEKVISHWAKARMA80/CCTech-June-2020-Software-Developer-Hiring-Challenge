import java.util.Scanner;

public class Program2 {
	
	static float positive_inf = Float.POSITIVE_INFINITY; //defined positive Infinity 
	static float negative_inf = Float.NEGATIVE_INFINITY; ////defined negative Infinity 
	
	static class Coordinate // class represent coordinate
	{ 
		float x; // x coordinate
		float y; // y coordinate

		public Coordinate(float x, float y) // assign value to coordinates by constructor
		{ 
			this.x = x; 
			this.y = y; 
		} 
	};
	
	
	//this is the function which finds the location of light source, means whether it is lies on left,right or middle of the buildings
	static String location(Coordinate buildings[][], Coordinate p) {
		int num_building =  buildings.length;
		int count=0;	
		
		if(p.x>negative_inf &&  p.x<buildings[0][0].x) {// light source is towards the left side of the buildings
			return "0"; 
		}
		
        for(int i=0;i<num_building;i++) {
        	if(p.x>buildings[i][0].x &&  p.x<buildings[i][3].x) {
        		count++;
        		return "a"+count;		// light source is above the building no. count
        	}
		}
        
        count=0;
        for(int i=0;i<num_building-1;i++) {
        	if(p.x>buildings[i][3].x &&  p.x<buildings[i+1][0].x) {
        		count++;
        		return "m"+count;		// light source is b/w the buildings count and count+1
        	}
		}
        
     	return "-1"; // means  light source is towards the left side of the buildings
	}
	
	  static Coordinate pointOfIntersection(Coordinate A, Coordinate B, Coordinate C, Coordinate D) 
	    { 
	        // Line AB represented as a1x + b1y = c1 
	        float a1 = B.y - A.y; 
	        float b1 = A.x - B.x; 
	        float c1 = a1*(A.x) + b1*(A.y); 
	       
	        // Line CD represented as a2x + b2y = c2 
	        float a2 = D.y - C.y; 
	        float b2 = C.x - D.x; 
	        float c2 = a2*(C.x)+ b2*(C.y); 
	       
	        float determinant = a1*b2 - a2*b1; 
	       
	        if (determinant == 0) 
	        { 
	            // The lines are parallel. This is simplified 
	            // by returning a pair of FLT_MAX 
	            return new Coordinate(Float.MAX_VALUE, Float.MAX_VALUE); 
	        } 
	        else
	        { 
	        	float x = (b2*c1 - b1*c2)/determinant; 
	        	float y = (a1*c2 - a2*c1)/determinant; 
	            return new Coordinate(x, y); 
	        } 
	    } 
	  
	  static float distance(float x1, float y1, float x2, float y2) 
	  { 
	      // Calculating distance 
	      return (float)Math.sqrt(Math.pow(x2 - x1, 2) +  Math.pow(y2 - y1, 2) * 1.0); 
	  } 
	
	static float left(Coordinate buildings[][], Coordinate p) {
		
		int num_building =  buildings.length,i=1,j=0; // i is iterator for each building, j is the iterator for building of maximum height 
		float verticle_heights=Math.abs(Math.abs(buildings[0][0].y) - Math.abs(buildings[0][1].y)),horizontal_heights=Math.abs(Math.abs(buildings[0][0].x) - Math.abs(buildings[0][3].x));
		
		float curr_max_height =Math.abs(Math.abs(buildings[0][0].y) - Math.abs(buildings[0][1].y));;
		
		Coordinate point;
		
		if(p.y>buildings[0][0].y) { // if light source is above the first building
			
			while(i!=num_building) { // check for every building
			
				if (curr_max_height <= Math.abs(Math.abs(buildings[i][0].y) - Math.abs(buildings[i][1].y))) // if current height of the building is less or equal to the next building
	            { 
					horizontal_heights = horizontal_heights + Math.abs(Math.abs(buildings[i][0].x) - Math.abs(buildings[i][3].x));	// store sum of all horizontal height of each building	
					
				    point = pointOfIntersection(p,buildings[j][3],buildings[i][0],buildings[i][1]); // point of intersection for the surface which is exposed to sunlight
					
				    if(point.y <= buildings[i][1].y) // if line intersects within the range of next building
					verticle_heights = verticle_heights + distance(point.x,point.y,buildings[i][0].x,buildings[i][0].y); // store sum of all vertical height of each building	
				    else 
				    	verticle_heights = verticle_heights + distance(buildings[i][1].x,buildings[i][1].y,buildings[i][0].x,buildings[i][0].y);
				    
				    curr_max_height  =  Math.abs(Math.abs(buildings[i][0].y) - Math.abs(buildings[i][1].y)); // re-initialize the max height 
				    j++;
					
	            }   
				
				else if(curr_max_height > Math.abs(Math.abs(buildings[i][0].y) - Math.abs(buildings[i][1].y))) //if current height of the building is greater than the next building
				{		
					
					point = pointOfIntersection(p,buildings[j][3],buildings[i][0],buildings[i][3]);
					
					if(point.x>=buildings[i][0].x  && point.x <= buildings[i][3].x)
					horizontal_heights = horizontal_heights +  distance(point.x,point.y,buildings[i][0].x,buildings[i][0].y);
					
					point = pointOfIntersection(p,buildings[j][3],buildings[i][0],buildings[i][1]);
					
					if(point.y>=buildings[i][0].y  && point.y <= buildings[i][1].y)
						verticle_heights = verticle_heights +  distance(point.x,point.y,buildings[i][0].x,buildings[i][0].y);
						
				}
				i++;	
			}
		}
		
		return verticle_heights + horizontal_heights; // sum of all vertical and horizontal height is the final result
	}
	

	static float above(Coordinate buildings[][], Coordinate p,int index) {
		// code
		// if light source is above any building
		return 0;
	}
	
  static float middle(Coordinate buildings[][], Coordinate p,int index) {
	         // code
			// if light source is b/w the building	
		return 0;
	}
	
	static float length_exposed_to_sunlight(Coordinate buildings[][], Coordinate p) {
		String loc = location(buildings,p);
		
		if(loc=="0" || loc=="-1" ) // if light source is lies towards left or right of all buildings
		return left(buildings,p);	
		
		if(loc.charAt(0)=='a') // if light source is lies above a particular building
			return above(buildings,p,Integer.parseInt(loc.substring(1)));	
			
	    if(loc.charAt(0)=='m')  // // if light source is lies b/w the building
	    	return above(buildings,p,Integer.parseInt(loc.substring(1)));	
							
		return 0;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the number of buildings");
		int num_building = sc.nextInt(),n=num_building;
		Coordinate[][] buildings = new Coordinate[num_building][4];
		
		System.out.println("Enter the coordinate for each building");
		for(int i=0;i<num_building;i++) {
			for(int j=0;j<4;j++) {
				buildings[i][j] = new Coordinate(sc.nextFloat(),sc.nextFloat());
			}
		}
		
		System.out.println("Enter the coordinate of light source"); 
		Coordinate p = new Coordinate(sc.nextFloat(), sc.nextFloat());
		
		System.out.println(length_exposed_to_sunlight(buildings,p));
	}
}