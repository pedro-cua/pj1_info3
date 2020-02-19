import java.io.File;
import java.util.LinkedList;
import java.util.Arrays;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Autograder{

    public static void main(String[] args ) throws Exception{
        String directory = "./tests";
        String solutionDirectory = "./solutions";
        if(args.length > 0){
            System.out.println("Utilizando directorio especificado para tests: '"+args[0]+"'");
            directory = args[0];
        }else{
            System.out.println("No se especifico directorio. Usando ruta por defecto: './tests' ");
        }
        if(args.length > 1){
            System.out.println("Utilizando directorio especificado para soluciones: '"+args[1]+"'");
            solutionDirectory = args[1];
        }else{
            System.out.println("No se especifico directorio. Usando ruta por defecto: './solutions' ");
        }
        File directoryFile = new File(directory);
        String[] files = directoryFile.list();
        System.out.println("El directorio de tests contiene "+files.length+" archivos");

        /*********************************************************************** */
        /****************No toque el codigo arriba de esta linea******************/
        /*********************************************************************** */
        /*La llamada al contructor. Envie aqui parametros adicionales si lo desea*/
        Solver solver = new Solver();
        /*********************************************************************** */
        /****************No toque el codigo abajo de esta linea ******************/
        /*********************************************************************** */

        double score = 0.0;
        for(int i = 0; i < files.length;i++){
            String filename = directory+"/"+files[i];
            String solutionFilename = solutionDirectory+"/solution-"+files[i];
            
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Analizando archivo '"+filename+"'");
            Maze maze = new Maze(filename);
            maze.generateMap();

            /*********************************************************************** */
            /**********************La llamada al metodo solve*************************/
            /*********************************************************************** */
            String[] solutions = solver.solve(maze);
            /*********************************************************************** */
            /*********************************************************************** */


            String[] expectedSolutions = (new String(Files.readAllBytes(Paths.get(solutionFilename)), StandardCharsets.UTF_8)).split("\n");
            System.out.println("Soluciones encontradas: "+solutions.length);
            System.out.println("Soluciones esperadas: "+expectedSolutions.length);
            score+=score(solutions,expectedSolutions);
            Thread.sleep(2000);
            maze.close();
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Puntaje Obtenido: "+score+"/100.00");
    }

    public static double score(String[] obtained,String[] expected){
        Arrays.sort(obtained);
        int pivot = 0;
        double score = 0;
        for(int i = 0; i < obtained.length;i++){
            for(int j = pivot; j <= expected.length;j++){
                if(j < expected.length){
                    if(obtained[i].equals(expected[j].trim())){
                        pivot = i+1;
                        score++;
                        break;
                    }
                }else{
                    System.out.println("Sol: "+obtained[i]+" INCORRECTA");
                }
            }
        }
        score = score/expected.length*10;
        System.out.println("Puntaje Obtenido: "+score+"/10");
        return score;
    }

}