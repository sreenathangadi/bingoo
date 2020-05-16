import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BingoTicketGen {

    private static final String localPrefix = "resources/";
    private static final int rangerEndNum = 90;
    private static final int noOfTickets = 2;

    public static void main(String[] args) throws IOException {
        BingoTicketGen bingoGen = new BingoTicketGen();
        File inputFile = new File("bingooTickets" + System.currentTimeMillis() + ".txt");
        FileWriter fileWriter = null;
        Set<String> genTickets = new HashSet<>();
        inputFile.createNewFile();
        fileWriter = new FileWriter(inputFile);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        for (int i = 1; i <= noOfTickets; i++) {
            Random r1 = new Random(System.currentTimeMillis());
            int[][] ticket = new int[3][9];
            Set<Integer> firstRowColFillIndexes = r1.ints(0, 9)
                    .distinct().limit(5).boxed().collect(Collectors.toSet());
            Set<Integer> secondRowColFillIndexes = r1.ints(0, 9)
                    .distinct().limit(5).boxed().collect(Collectors.toSet());
            Set<Integer> thirdRowColFillIndexes = r1.ints(0, 9)
                    .distinct().limit(5).boxed().collect(Collectors.toSet());

            for(int column=0;column<9;column++){
                List<Integer> numbers = r1.ints((((column*10)+1)), ((column+1)*10+1))
                        .distinct().limit(3).boxed().collect(Collectors.toList());
                int numbersIndex = 0;
                //first row
                if(firstRowColFillIndexes.contains(column)){
                    ticket[0] [column] = numbers.get(numbersIndex++);
                }
                //second row
                if(secondRowColFillIndexes.contains(column)){
                    ticket[1] [column] = numbers.get(numbersIndex++);
                }
                //third row
                if(thirdRowColFillIndexes.contains(column)){
                    ticket[2] [column] = numbers.get(numbersIndex++);
                }
            }

            bingoGen.displayTicket(ticket, bw);

        }
        bw.close();
    }

    private void displayTicket(int[][] ticket, BufferedWriter bw) throws IOException{
        System.out.println("------------------------------------------------------------------------");
        bw.newLine();
        bw.write("------------------------------------------------------------------------");
        for(int row =0; row <3;row++){
            System.out.println("");
            bw.newLine();
            for(int column = 0;column<9;column++){
                String displayNum = "  * ";
                if(ticket[row][column] != 0){
                    displayNum = ticket[row][column] < 10? " 0":" ";
                    displayNum = displayNum + ticket[row][column] + " ";
                }
                System.out.print(displayNum);
                bw.write(displayNum);
            }
        }
        System.out.println("");
        bw.newLine();
        System.out.println("------------------------------------------------------------------------");
        bw.write("------------------------------------------------------------------------");
    }
}
