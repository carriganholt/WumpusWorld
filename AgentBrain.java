//AI Wumpus World Agent Brain
//Carrigan Holt

public class AgentBrain {

	private AgentAction nextMove;

	private static int numGamesPlayed = 0;
	private static boolean keyboardPlayOnly = false;
	private static int x;
	private static int y;
	private static boolean hasGold;
	
	private static final int moveLimit = 20;
	private static final int gameLimit = 20;
	
	private static int[][] safe;
	private static int[][] visited;
	private static int[][] breezes;
	private static int[][] stenches;

	private int currentNumMoves;

	public AgentBrain() {
		nextMove = null;
		numGamesPlayed++;
		currentNumMoves = 0;
		hasGold = false;
		safe = new int[6][6];
		visited = new int[6][6];
		breezes = new int[6][6];
		stenches = new int[6][6];
	}

	public void setNextMove(AgentAction m) {
		if(nextMove != null) {
			System.out.println("Trouble adding move, only allowed to add 1 at a time");
		} else {
			nextMove = m;
		}
	}
	
	public AgentAction getNextMove(GameTile[][] visableMap) {
		
		currentNumMoves++;
		
		if(visableMap[4][1].hasBreeze() | visableMap[4][1].hasStench()) {
			if(numGamesPlayed < gameLimit) {
				return AgentAction.declareVictory;
				
			} else {
				return AgentAction.quit;
			}
		}
		
		if(keyboardPlayOnly) {
			if(nextMove == null) {
				return AgentAction.doNothing;
				
			} else {
				AgentAction tmp = nextMove;
				nextMove = null;
				return tmp;
			}

		} else {
			return play(visableMap);
		}
	}
	
	//AI player
	public AgentAction play(GameTile[][] map) {
		
		if(currentNumMoves > moveLimit) {
			hasGold = true;
		}
		
		findPlayer(map);
		visited[y][x] = 1;
		
		if(!hasGold) {
			
			//If on gold
			if(map[y][x].hasGlitter()) {
				hasGold = true;
				return AgentAction.pickupSomething;
			}
			
			//If on breeze
			else if(map[y][x].hasBreeze()) {
				breezes[y][x] = 1;
			}
			
			//If on stench
			else if(map[y][x].hasStench()) {
				stenches[y][x] = 1;
			}
			
			//Empty
			else {
				safe[y][x] = 1;
				safe[y + 1][x] = 1;
				safe[y - 1][x] = 1;
				safe[y][x + 1] = 1;
				safe[y][x - 1] = 1;
			}
			
			if(map[y][x].hasStench() & safe[y][x + 1] == 2) {
				safe[y][x + 1] = 1;
				return AgentAction.shootArrowEast;
			}
			
			if(map[y][x].hasStench() & safe[y][x - 1] == 2) {
				safe[y][x - 1] = 1;
				return AgentAction.shootArrowWest;
			}

			if(map[y][x].hasStench() & safe[y + 1][x] == 2) {
				safe[y + 1][x] = 1;
				return AgentAction.shootArrowSouth;
			}

			if(map[y][x].hasStench() & safe[y - 1][x] == 2) {
				safe[y - 1][x] = 1;
				return AgentAction.shootArrowNorth;
			}
			
			//right
			if(safe[y][x + 1] == 1 & visited[y][x + 1] == 0 & (map[y][x + 1] == null || !map[y][x + 1].isWall())) {
				return AgentAction.moveRight;
			}
			
			//up
			if(safe[y - 1][x] == 1 & visited[y - 1][x] == 0 & (map[y - 1][x] == null || !map[y - 1][x].isWall())) {
				return AgentAction.moveUp;
			}
			
			//left
			if(safe[y][x - 1] == 1 & visited[y][x - 1] == 0 & (map[y][x - 1] == null || !map[y][x - 1].isWall())) {
				return AgentAction.moveLeft;
			}
			
			//down
			if(safe[y + 1][x] == 1 & visited[y + 1][x] == 0 & (map[y + 1][x] == null || !map[y + 1][x].isWall())) {
				return AgentAction.moveDown;
			}
			
			//right visited
			if(safe[y][x + 1] == 1 & (map[y][x + 1] == null || !map[y][x + 1].isWall())) {
				return AgentAction.moveRight;
			}
			
			//up visited
			if(safe[y - 1][x] == 1 & (map[y - 1][x] == null || !map[y - 1][x].isWall())) {
				return AgentAction.moveUp;
			}
			
			//left visited
			if(safe[y][x - 1] == 1 & (map[y][x - 1] == null || !map[y][x - 1].isWall())) {
				return AgentAction.moveLeft;
			}
			
			//down visited
			if(safe[y + 1][x] == 1 & (map[y + 1][x] == null || !map[y + 1][x].isWall())) {
				return AgentAction.moveDown;
			}
			
			return AgentAction.doNothing;
			
		} else {
			
			if(map[y][x].hasGlitter()) {
				hasGold = true;
				return AgentAction.pickupSomething;
			}
			
			if(y < 4 & safe[y + 1][x] == 1 & (map[y + 1][x] == null || !map[y + 1][x].isWall())) {
				return AgentAction.moveDown;
			}
			
			if(x > 1 & safe[y][x - 1] == 1 & (map[y][x - 1] == null || !map[y][x - 1].isWall())) {
				return AgentAction.moveLeft;
			}
			
			if(numGamesPlayed < gameLimit) {
				return AgentAction.declareVictory;
				
			} else {
				return AgentAction.quit;
			}
		}
	}
	
	//Finds player on the map
	public void findPlayer(GameTile[][] map) {
		for(int i = 1; i < 5; i++) {
			for(int j = 1; j < 5; j++) {
				if(map[j][i] != null) {
					if(map[j][i].hasPlayer()) {
						x = i;
						y = j;
					}
				}					
			}
		}
	}
}
