package Graph;

import java.util.HashSet;
import java.util.Set;

public class Islands {

    Set<StringBuilder> uniqueIslands ;
    StringBuilder currIsland ;
    int R , C;
    boolean[][] isVisted;
    public int numDistinctIslands(int[][] grid) {
        uniqueIslands = new HashSet<>();
        R = grid.length; C = grid[0].length;
        isVisted = new boolean[R][C];
        for(int i = 0 ; i < R ;i++){
            for(int j = 0 ; j < C ;j++){
                currIsland = new StringBuilder();
                callRec(i,j,grid,'0');
                if(!uniqueIslands.contains(currIsland)){
                    uniqueIslands.add(currIsland);
                }
            }
        }
        return uniqueIslands.size();
    }

    private void callRec(int r, int c, int[][] grid, char curr) {
        if(r < 0 || r >= R || c < 0 || c >= C || !isVisted[r][c] || grid[r][c] ==0){
            return;
        }
        isVisted[r][c] = true;
        currIsland.append(curr);
        callRec(r+1,c,grid,'D');
        callRec(r-1,c,grid,'U');
        callRec(r,c+1,grid,'R');
        callRec(r,c-1,grid,'L');
        currIsland.append('0');
    }


    private boolean callDFS(char[][] board, int x, int y, String word, int index) {
        if(index == word.length()-1 && word.charAt(index) == board[x][y]){
            return true;
        }
        if(word.charAt(index) != board[x][y]){
            return false;
        }
        char tmp = board[x][y];
        board[x][y] = '#';
        boolean res = false;
        int[] xD = {-1,1,0,0};
        int[] yD = {0,0,-1,1};
        for(int i = 0 ; i < 4 ; i++){
            int x1 = xD[i] +x;
            int y1 = yD[i] +y;
            if(x1 >= board.length || y1 >= board[0].length || x1 < 0 || y1 < 0 || board[x1][y1] == '#'){
                continue;
            }
            res = callDFS(board, x1, y1, word, index+1);
            if(res){
                return true;
            }
        }
        board[x][y] = tmp;
        return res;
    }
    int max = (int) Math.pow(10,6)+7;
    public int knightDialer(int n) {
        if(n == 0){
            return 0;
        }
        int R = 4 , C = 3;
        long count = 0 ;
        long[][][] mem = new long[n+1][R][C];
        for(int i =0 ; i < R ; i++){
            for(int j = 0 ; j < C ;j++){
                count += callRecKnight(n,i,j,mem) % max;
            }
        }
        return (int)count;
    }

    private long callRecKnight(int n, int x, int y, long[][][] mem) {
        if(x >= 4 || y >= 3 || x < 0 || y < 0 || (x ==3 && y != 1)){
            return 0;
        }
        if(n == 1){
            mem[n][x][y]= 1;
            return 1;
        }
        if(mem[n][x][y] > 0){
            return mem[n][x][y];
        }
        long count = 0;
        int[] xDirec = {-2,-2,-1,-1,2,2,1,1};
        int[] yDirec = {1,-1,2,-2,-1,1,-2,2};
        for(int i = 0 ; i < 8 ;i++){
            int newX = x + xDirec[i];
            int newY = y + yDirec[i];
            count += callRecKnight(n-1,newX,newY,mem) % max;
        }
        mem[n][x][y] = count;
        return count;
    }

    public int numIslands(char[][] grid) {
        int R = grid.length , C = grid[0].length ,n =0;
        final char ONE = '1';
        for(int r = 0 ; r < R ; r++){
            for(int c = 0; c < C ;c++){
                if(grid[r][c] == '1') {
                    n++;
                    callRec(grid, r, c, ONE);
                }
            }
        }
        return n;
    }

    private void callRec(char[][] grid, int r, int c, char one) {
        grid[r][c] = '0';
        int[] x = {-1,1,0,0};
        int[] y = {0,0,-1,1};
        for(int i =0 ; i<4;i++){
            int r1 = r + x[i];
            int c1 = c + y[i];
            if(r1 >=0 && r1 < grid.length && c1 >=0 && c1 < grid[0].length && grid[r1][c1] == one){
                callRec(grid,r1,c1,one);
            }
        }
    }
}
