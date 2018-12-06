public class AlphaBeta {
	Game g;
	Heuristic h;
	public int bestX;
	public int bestY;
	private int depthLimit;
	
    public AlphaBeta(Game game) {
    	g = game;
    	h = new Heuristic();
    }
    
    public int DFS(int depthLimit, boolean isX) {
    	boolean[] plays = g.getPlays();
    	byte[][] positions = g.getBoard().getPositions();
    	this.depthLimit = depthLimit;
    	return DFS(depthLimit, isX, plays, positions);
    }
    
    private int DFS(int depthLimit, boolean isX, boolean[] plays, byte[][] positions) {
    	if(depthLimit > 1) {
    		int highScore = (isX) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        	int highX = -1;
        	int highY = -1;
        	
        	for(int i = 0; i < plays.length; ++i) {
        		int cur = 0;
        		if(plays[i]) {
        			int x = i%8;
        			int y = i/8;

        			if(isX) {
        				cur += h.scorePosition(y, x, positions, isX);
        			}
        			else {
        				cur -= h.scorePosition(y, x, positions, isX);
        			}

        			plays[i] = false;
        			boolean setUp = false;
        			boolean setDown = false;
        			boolean setLeft = false;
        			boolean setRight = false;
        	    	
        	    	if(y - 1 >= 0 && plays[i - 8] == false && positions[y-1][x] == 0) {
        	    		plays[i - 8] = true;
        	    		setUp = true;
        	    	}
        	    	if(y + 1 < 8 && plays[i + 8] == false && positions[y+1][x] == 0) {
        	    		plays[i + 8] = true;
        	    		setDown = true;
        	    	}
        	    	if(x - 1 >= 0 && plays[i - 1] == false && positions[y][x-1] == 0) {
        	    		plays[i - 1] = true;
        	    		setLeft = true;
        	    	}
        	    	if(x + 1 < 8 && plays[i + 1] == false && positions[y][x+1] == 0) {
        	    		plays[i + 1] = true;
        	    		setRight = true;
        	    	}
        			
        			positions[y][x] = (byte)((isX) ? 1 : -1);
        			if(isX) {
            			cur -= DFS(depthLimit - 1, !isX, plays, positions);
        			}
        			else {
        				cur += DFS(depthLimit - 1, !isX, plays, positions);
        			}
        			positions[y][x] = 0;
        			
        			plays[i] = true;
        			if(setUp) {
        	    		plays[i - 8] = false;
        	    	}
        	    	if(setDown) {
        	    		plays[i + 8] = false;
        	    	}
        	    	if(setLeft) {
        	    		plays[i - 1] = false;
        	    	}
        	    	if(setRight) {
        	    		plays[i + 1] = false;
        	    	}

        	    	//CHANGED
        			if(isX && cur > highScore && positions[y][x] == 0) {
        				highScore = cur;
        				highX = x;
        				highY = y;
        			}
        			else if(!isX && cur < highScore && positions[y][x] == 0) {
        				highScore = cur;
        				highX = x;
        				highY = y;
        			}
        			
        			this.bestX = highX;
                	this.bestY = highY;
        		}
        		
        		if(depthLimit == this.depthLimit) {
        			System.out.print("[" + cur + "]");
        			if((i + 1) % 8 == 0) {
        				System.out.println();
        			}
        		}
        	}
        	
        	if(depthLimit == this.depthLimit) {
        		System.out.print("Plays: {");
    			for(int n = 0; n < plays.length; ++n) {
    				if(plays[n]) {
    					System.out.print("{x: " + (n%8 + 1) + ", y: " + (n/8 + 1) + "}");
    				}
    			}
    			System.out.println();
        	}
        	
        	return highScore;
    	}
    	else {
    		return 0;
    	}
    }
    
}
