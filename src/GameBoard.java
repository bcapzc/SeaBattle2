import java.util.Scanner;

public class GameBoard {
	static int BOARDROW = 7;
	static int BOARDCOL = 7;
	static int shipNum = 4;
	
	int numSink = 0;

	int[][] gameBoard;
	BattleShip[] battleShips;
	
	// ���캯��
	public GameBoard(){
		gameBoard = new int[BOARDROW][BOARDCOL];
		for (int row = 0; row < BOARDROW; row++){
			for (int col = 0; col < BOARDCOL; col++){
				gameBoard[row][col] = 0;
			}
		}
		battleShips = new BattleShip[shipNum];
		for (int i = 0; i < shipNum; i++){
			if (i < (shipNum / 2)){
				battleShips[i] = new BattleShip(2);
			}
			else{
				battleShips[i] = new BattleShip(3);
			}
		}
		arrangeShips();
	}
	
	// ȷ������λ��
	public void arrangeShips(){
		int counter = 0;	// ������
		boolean available = true;
		for (int i = 0; i < shipNum; i++){
			counter = 0;
			do{
				System.out.println("Ship " + (i + 1) + ": try " + ++counter);
				available = isLocAvailable(i);
			} while(!available);
		}
	}
	
	// �ж�λ���Ƿ����
	public boolean isLocAvailable(int shipNo){
		int tempLoc[][] = battleShips[shipNo].getRandomLoc(BOARDROW, BOARDCOL);
		if (battleShips[shipNo].getSize() == 2){
			if (gameBoard[tempLoc[0][0]][tempLoc[0][1]] == 0
					&& gameBoard[tempLoc[1][0]][tempLoc[1][1]] == 0){
				gameBoard[tempLoc[0][0]][tempLoc[0][1]] = shipNo + 1;
				gameBoard[tempLoc[1][0]][tempLoc[1][1]] = shipNo + 1;
				battleShips[shipNo].setLoc(tempLoc);
				return true;
			}
		}
		if (battleShips[shipNo].getSize() == 3){
			if (gameBoard[tempLoc[0][0]][tempLoc[0][1]] == 0
					&& gameBoard[tempLoc[1][0]][tempLoc[1][1]] == 0
					&& gameBoard[tempLoc[2][0]][tempLoc[2][1]] == 0){
				gameBoard[tempLoc[0][0]][tempLoc[0][1]] = shipNo + 1;
				gameBoard[tempLoc[1][0]][tempLoc[1][1]] = shipNo + 1;
				gameBoard[tempLoc[2][0]][tempLoc[2][1]] = shipNo + 1;
				battleShips[shipNo].setLoc(tempLoc);
				return true;
			}
		}
		return false;
	}

	// �����ڵ�
	public void fireCannon(int[] hitLoc){
		for (int i = 0; i < shipNum; i++){
			if (battleShips[i].isHit(hitLoc)){
				System.out.println("You hit battle ship " + i + "!");
				gameBoard[hitLoc[0]][hitLoc[1]] = -1;
				if (battleShips[i].isSink()){
					System.out.println("Battle ship " + (i + 1) + " is destroyed!");
					numSink++;
				}
			}
		}
	}

	// �ж��Ƿ��ʤ
	public boolean isWin(){
		if (numSink >= shipNum){
			return true;
		}
		return false;
	}
	
	// ��ȡ���λ�ã������ã�
	public int[] getHitLoc(){
		int[] tempHit = new int[2];
		Scanner input = new Scanner(System.in);
		System.out.print("������������: ");
		tempHit[0] = input.nextInt();
		System.out.print("������������: ");
		tempHit[1] = input.nextInt();
		return tempHit;
	}
	
	// ��ӡ��Ϸ�壨�����ã�
	public void printBoard(){
		System.out.println("-------The status of the board now:-------");
		for (int row = 0; row < BOARDROW; row++){
			System.out.print("             ");
			for (int col = 0; col < BOARDCOL; col++){
				System.out.print(gameBoard[row][col] + " ");
			}
			System.out.print("\n");
		}
		System.out.println("------------------------------------------");
	}
	
	public static void main(String[] args) {
		GameBoard gameBoard = new GameBoard();
		for (int i = 0; i < gameBoard.shipNum; i++){
			gameBoard.battleShips[i].printStatus();
		}
		gameBoard.printBoard();
		
		do{
			gameBoard.fireCannon(gameBoard.getHitLoc());
			gameBoard.printBoard();
		} while(!gameBoard.isWin());
		
		System.out.println("You Win!");
		
	}

}
