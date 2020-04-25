import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class BingoGen {

    private static final String localPrefix = "resources/";
    private static final int rangerStartNum = 1;
    private static final int rangerEndNum = 90;

    public static void main(String[] args) {
        BingoGen bingoGen = new BingoGen();
        File inputFile = new File("bingooGame_" + System.currentTimeMillis() + ".txt");
        FileWriter fileWriter = null;
        Calendar now = Calendar.getInstance();
        Set<Integer> readNums = new HashSet<>();
        try {
            inputFile.createNewFile();
            fileWriter = new FileWriter(inputFile);

            BufferedWriter bw = new BufferedWriter(fileWriter);
            Random r1 = new Random(System.currentTimeMillis());
            Random r2 = new Random(System.currentTimeMillis() + now.get(Calendar.SECOND));
            //randomize actual numbers
            List<Integer> randomNumbers = r1.ints(1, rangerEndNum+1)
                    .distinct().limit(rangerEndNum).boxed().collect(Collectors.toList());

            //randomize index also, to make interesting
            List<Integer> randomNumbersIndex = r2.ints(0, rangerEndNum)
                    .distinct().limit(rangerEndNum).boxed().collect(Collectors.toList());

            for (Integer ranNum: randomNumbersIndex){
                int nextNum = randomNumbers.get(ranNum);
                readNums.add(nextNum);
                System.out.println("NEXT NUMBER: [" +readNums.size() +
                        " of " + rangerEndNum  + "] => "  + nextNum);
                bw.write(String.valueOf(nextNum));
                bw.write(System.lineSeparator());
                bw.flush();
                bingoGen.displayMatrix(readNums, nextNum);
                if (readNums.size() == rangerEndNum) {
                    break;
                }
                //pause
                System.in.read();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayMatrix(Set<Integer> readNums, int nextNum){
        System.out.println("------------------------------------------------------------------------");
        int pre = 0;
        for(int i=1;i<=rangerEndNum;i++){
            String displayNum = "  * ";
            if(readNums.contains(i)){
                displayNum = (i == nextNum)? "[" : " ";
                displayNum = displayNum + (i < 10? "0": "") + i;
                displayNum = displayNum + ((i == nextNum)? "]" : " ");
            }

            if(pre == i/10) {
                System.out.print(displayNum);
            }else{
                System.out.println(displayNum + "\t");
            }
            pre = i/10;
        }
        System.out.println("------------------------------------------------------------------------");
    }
}
