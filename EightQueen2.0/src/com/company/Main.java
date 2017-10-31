package com.company;

public class Main {
    static final int size = 8; //Size of the chess board
    static int[][] board = new int[size][size]; //chess board
    static int[][][] memory = new int[size][size][100000]; //Memory of all the possible chess layouts !!Change the size of the Memory if you want the size of the chess board is greater than 13
    static int tot = 0; //Numbers of possible chess board layouts

    public static void main(String[] args) {
        insQueen(0);  //Recursion function start
        System.out.println("Total solutions: " + tot);

    }

    public static void insQueen(int num){  //Recursion function to establish the possible chess layout
        for(int i=0;i<size;i++){
            if (spaceFree(i, num)==0){     //Check if the queen in this position can be eaten by another queen in the chess board
                board[i][num]=1;
                if (num==(size-1)) {
                    if (!checkEquals()) {  //Check if the layout has already been saved in the memory
                        copy();
                        tot++;
                        stamp();
                    }
                }else{
                    insQueen(num + 1);
                }
                board[i][num]=0;
            }
        }
    }
    private static boolean checkEquals(){  //Check if the current layout has already been saved in the memory
        for (int i=0; i<tot; i++) {
            int[][] boardCopy = board;

            for (int l = 0; l <= 4; l++) { //For every possible rotation
                boardCopy = getRotationCopy(boardCopy); //Get the board rotated to 90 degrees
                for (int p=0; p<2; p++) { //For every possible diagonal copy
                    boardCopy = getDiagonalCopy(boardCopy); //Get the board by the diagonal copy
                    int checks = 0;
                    for (int x = 0; x < size; x++) {
                        for (int y = 0; y < size; y++) {
                            if (boardCopy[x][y] == 1 && memory[x][y][i] == 1)
                                checks++;
                        }
                    }

                    if (checks == size) { //If the layout is equal in every part to another layout in the memory then return true
                        return true;
                    }
                }

            }

        }

        return false; //No equal layout found in the memory
    }
    private static void copy(){ //Put the current layout in the memory
        for(int i=0; i<size; i++){
            for(int f=0; f<size; f++){
                memory[i][f][tot]= board[i][f];
            }
        }
    }


    public static int spaceFree(int x, int y){ //Check if the queen can be placed in a certain position
        int xMin;
        int yMin;
        int xMax;
        int yMax;
        //Check horizontal line
        for (int i=0; i<size; i++){
            if (board[i][y]==1)
                return 1;
        }
        //Check vertical line
        for (int i=0; i<size; i++){
            if (board[x][i]==1)
                return 1;
        }
        //Check Diagonal from up
        //check bigger
        if (x>y) {
            xMin=x-y;
            yMin=0;
        }else{
            yMin=y-x;
            xMin=0;
        }
        //Check Diagonal from bottom
        //check bigger
        xMax=x+y;
        yMax=0;
        if (xMax>=size) {
            xMax = size-1;
            yMax = y-(size-x-1);
        }
        //Check upperLeft diagonal
        while (xMin<size && yMin<size)
        {
            if (board[xMin][yMin]==1)
                return 1;
            xMin++;
            yMin++;
        }
        //Check downLeft diagonal
        while (xMax>=0 && yMax<size){
            if (board[xMax][yMax]==1)
                return 1;
            xMax--;
            yMax++;
        }


        return 0;
    }
    public static void stamp(){ //Stamp the current layout
        System.out.println();
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                System.out.print(board[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int[][] getDiagonalCopy(int[][] real){ //Get a diagonal copy of a layout
        int[][] ret = new int[size][size];
        for (int i=0; i<size; i++){
            for (int f=0; f<size; f++){
                ret[i][f]=real[f][i];
            }
        }
        return ret;
    }

    private static int[][] getRotationCopy(int[][] temporalBoard){ //Get a rotation copy of a layout
        int[][] ret = new int[size][size];

        for (int x = 0; x<size; x++){
            int y2=0;
            for (int y=size-1; y>=0; y--){
                ret[x][y2] = temporalBoard[y][x];
                y2++;
            }
        }
        return ret;
    }

}


//This is all
