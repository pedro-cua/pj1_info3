import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.awt.event.WindowEvent;
import java.io.File;

public class Maze{

    private static int SPACE_SIZE = 70;
    private static int WALL_SIZE = 4;
    
    private int width;
    private int height;
    private JFrame frame;
    private int exitSpace;
    private int startSpace;
    private int maxMoves;
    private Node[] nodes;

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int getExitSpace(){
        return this.exitSpace;
    }


    public int getStartSpace(){
        return this.startSpace;
    }

    public int getMaxMoves(){
        return this.maxMoves;
    }

    public Maze(String filename) throws Exception{
        this.frame = new JFrame("Laberinto "+filename);
        Scanner scan = new Scanner(new File(filename));

        String[] split = scan.nextLine().split(",");
        this.width = Integer.parseInt(split[0]);
        this.height = Integer.parseInt(split[1]);
        this.nodes = new Node[this.width*this.height];

        split = scan.nextLine().split(",");
        this.exitSpace = Integer.parseInt(split[0]);
        this.startSpace = Integer.parseInt(split[1]);

        this.maxMoves = Integer.parseInt(scan.nextLine());

        for(int i = 0; i < this.height;i++){
            String line = scan.nextLine();
            split = line.split("\\|");
            for(int j = 0; j < this.width;j++){
                Node n = new Node(split[j]);
                this.nodes[this.width*i+j] = n;
            }
        }
        scan.close();
    }

    private void createWall(JPanel panel,int x,int y,int width,int height){
        JLabel label = new JLabel();
        label.setBounds(x,y,width,height);
        label.setOpaque(true);
        label.setBackground(Color.black);
        panel.add(label);
    }

    private void createHorizontalPassage(JPanel panel,int x,int y,int width,int height){
        JLabel label = new JLabel();
        label.setBounds(x,y,(int)(width*0.4),height);
        label.setOpaque(true);
        label.setBackground(Color.black);
        panel.add(label);

        label = new JLabel();
        label.setBounds((int)(x+width*0.6),y,(int)(width*0.4),height);
        label.setOpaque(true);
        label.setBackground(Color.black);
        panel.add(label);
    }

    private void createVerticalPassage(JPanel panel,int x,int y,int width,int height){
        JLabel label = new JLabel();
        label.setBounds(x,y,width,(int)(height*0.4));
        label.setOpaque(true);
        label.setBackground(Color.black);
        panel.add(label);

        label = new JLabel();
        label.setBounds(x,(int)(y+height*0.6),width,(int)(height*0.4));
        label.setOpaque(true);
        label.setBackground(Color.black);
        panel.add(label);
    }

    private void colorBlock(JPanel panel,int position, Color color){
        int x = position % this.width;
        int y = position / this.width;
        JLabel label = new JLabel();
        label.setBounds(x*SPACE_SIZE+WALL_SIZE,y*SPACE_SIZE+WALL_SIZE,SPACE_SIZE-2*WALL_SIZE,SPACE_SIZE-2*WALL_SIZE);
        label.setOpaque(true);
        label.setBackground(color);
        panel.add(label);
    }

    private void createExit(JPanel panel){
        this.colorBlock(panel,this.exitSpace,Color.green);
    }

    private void createStart(JPanel panel){
        this.colorBlock(panel,this.startSpace,Color.blue);
    }

    public void close(){
        this.frame.setVisible(false);
        this.frame.dispose();
    }

    private int move(int nodeIndex, boolean movement, int step){
        if(movement){
            return nodeIndex+step;
        }else{
            return nodeIndex;
        }
    }

    public int moveNorth(int nodeIndex){
        return this.move(nodeIndex,this.nodes[nodeIndex].north,-this.width);
    }

    public int moveSouth(int nodeIndex){
        return this.move(nodeIndex,this.nodes[nodeIndex].south,this.width);
    }

    public int moveWest(int nodeIndex){
        return this.move(nodeIndex,this.nodes[nodeIndex].west,-1);
    }

    public int moveEast(int nodeIndex){
        return this.move(nodeIndex,this.nodes[nodeIndex].east,1);
    }

    public void generateMap(){
        JPanel panel = new JPanel();
        panel.setLayout(null);

        int width = (this.width)*SPACE_SIZE;
        int height = (this.height)*SPACE_SIZE;

        for(int i = 0; i < this.height;i++){
            for(int j = 0; j < this.width;j++){
                Node n = this.nodes[this.width*i+j];
                if(! n.north){ 
                    this.createWall(panel,j*SPACE_SIZE,i*SPACE_SIZE,SPACE_SIZE,WALL_SIZE);
                }else{
                    this.createHorizontalPassage(panel,j*SPACE_SIZE,i*SPACE_SIZE,SPACE_SIZE,WALL_SIZE);
                }
                if(! n.south){
                    this.createWall(panel,j*SPACE_SIZE,(i+1)*SPACE_SIZE-WALL_SIZE,SPACE_SIZE,WALL_SIZE);
                }else{
                    this.createHorizontalPassage(panel,j*SPACE_SIZE,(i+1)*SPACE_SIZE-WALL_SIZE,SPACE_SIZE,WALL_SIZE);
                }
                if(! n.west){
                    this.createWall(panel,j*SPACE_SIZE,i*SPACE_SIZE,WALL_SIZE,SPACE_SIZE);
                }else{
                    this.createVerticalPassage(panel,j*SPACE_SIZE,i*SPACE_SIZE,WALL_SIZE,SPACE_SIZE);
                }
                if(! n.east){
                    this.createWall(panel,(j+1)*SPACE_SIZE-WALL_SIZE,i*SPACE_SIZE,WALL_SIZE,SPACE_SIZE);
                }else{
                    this.createVerticalPassage(panel,(j+1)*SPACE_SIZE-WALL_SIZE,i*SPACE_SIZE,WALL_SIZE,SPACE_SIZE);
                }
            }
        }
        this.createExit(panel);
        this.createStart(panel);
        this.frame.add(panel);
        panel.setPreferredSize(new Dimension(width,height));
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception{
        if(args.length > 0){
            Maze maze = new Maze(args[0]);
            maze.generateMap();
        }else{
            System.out.println("Clase Maze. Modo de ejecución: java Maze ruta_archivo_laberinto");
        }
    }

}

class Node{
    boolean north,east,south,west;
    Node(String connections){
        this.north = connections.charAt(0) == 'X';
        this.east = connections.charAt(1) == 'X';
        this.south = connections.charAt(2) == 'X';
        this.west = connections.charAt(3) == 'X';
    }
}