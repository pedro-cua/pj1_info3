/*
    Esta es su clase principal. El unico metodo que debe implementar es
    public String[] solve(Maze maze)
    pero es libre de crear otros metodos y clases en este u otro archivo que desee.
*/
public class Solver{
    int Wid, Hei, Exit, Start, MaxM, N, E, S, W;
    //String movies;

    public Solver(int Wid, int Hei, int Exit, int Start, int MaxM, int N, int E, int S, int W){//*/ ){
        //*/Sientase libre de implementar el contructor de la forma que usted lo desee
        this.Wid = maze.getWidth(); 
        this.Hei = Maze.getHeigth();
        this.Exit = Maze.getExitSpace();
        this.Start = Maze.getStartSpace();
        this.MaxM = Maze.getMaxSpace();//*/
    
    }

    public String[] solve(Maze maze){
        ArrayList<String> str = new ArrayList<String>();
        //Implemente su metodo aqui. Sientase libre de implementar métodos adicionales
        if(maze.getStartSpace() == maze.getExitSpace()){
            System.out.println("[" + maze.getExitSpace() + "]");
        }else {
            for(int i = 0; i < Maze.getHeigth(); i++){
                for(int j = 0; j< Maze.getWidth();j++){
                    if(maze[i][j] != Maze.getExitSpace()){
                        while(Maze.getMaxMovies() != 0){
                            int N = moveNorth(Maze.getStartSpace());
                            int S = moveSouth(Maze.getStartSpace());
                            int E = moveEast(Maze.getStartSpace());
                            int W = moveWest(Maze.getStartSpace());
                            int posE = Maze.getExitSpace();
                            if(N != posE || S!= posE ){
                                n = Integer.toString(N);
                                s = Integer.tiString(S);
                                str.add(n);
                                str.add(s);
                            }else if(E != posE || W != posE){
                                e = Integer.toStrig(E);
                                w = Integer.toString(W);
                                str.add(e);
                                str.add(w);
                            }
                            Maze.getExitSpace -= 1;
                        }
                    }return str;
                }
            }
        }
    solve(maze, maze.getStartSpace());


        //String[] str = new String[1];
        //str[0] = "[-1]";
        //return str;
    }

    /*public mapa(int Maze.getHeigth(), int Maze.getWidth()){
        int[][] matriz = new int[Maze.getHeigth()][Maze.getWidth()];
        for(int i = 0; i<=Maze.getWidth().length; i++){
            for(int j = 0; j<=Maze.getHeigth().length; j++){
               int sizemap = matriz[i][j];
            }
        }
        return sizemap;
    }*/

}