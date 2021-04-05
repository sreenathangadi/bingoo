import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BingoTicketGen {

    private static final int noOfTickets = 25;

    private static final String HTML_TEMPLATE = "<html>\n" +
            "    <head>\n" +
            "        <style>\n" +
            "            .emptyBg {\n" +
            "                background-color: #fff;min-width: 25px;\n" +
            "            }\n" +
            "\n" +
            "            .nonEmptyBg{\n" +
            "               border: 1px solid white;\n" +
            "               color: white;\n" +
            "               text-align: center;font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande',\n" +
            "                'Lucida Sans Unicode', Geneva, Verdana, sans-serif;\n" +
            "                font-weight: bolder;\n" +
            "                font-size: 20px;\n" +
            "            }\n" +
            "\n" +
            "            .magicBg{\n" +
            "                border: 4px solid black;\n" +
            "                color: white;\n" +
            "                text-align: center;font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande',\n" +
            "                'Lucida Sans Unicode', Geneva, Verdana, sans-serif;\n" +
            "                font-weight: bolder;\n" +
            "                font-size: 20px;\n" +
            "                background-color: orange;\n" +
            "            }\n" +
            "        </style>\n" +
            "    </head>\n" +
            "    <body>REPLACE_STRING\n" +
             "    </body>\n" +
            "</html>\n";

    public static void main(String[] args) throws IOException {
        BingoTicketGen bingoGen = new BingoTicketGen();
        DateFormat dateFormat = new SimpleDateFormat("_yyyy_MM_dd_hh_mm_");
        File inputFile = new File("bingooTickets"+ dateFormat.format(new Date()) + System.currentTimeMillis() + ".txt");
        File htmlInputFile = new File("bingooTickets"+ dateFormat.format(new Date()) + System.currentTimeMillis() + ".html");
        inputFile.createNewFile();
        htmlInputFile.createNewFile();
        FileWriter fileWriter = new FileWriter(inputFile);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        List<String> htmlTickets = new ArrayList<>();
        for (int i = 1; i <= noOfTickets; i++) {
            Random r1 = new Random(System.currentTimeMillis() + i);
            int[][] ticket = new int[3][9];
            Set<Integer> firstRowColFillIndexes = r1.ints(0, 9)
                    .distinct().limit(5).boxed().collect(Collectors.toSet());
            Set<Integer> secondRowColFillIndexes = r1.ints(0, 9)
                    .distinct().limit(5).boxed().collect(Collectors.toSet());
            Set<Integer> thirdRowColFillIndexes = r1.ints(0, 9)
                    .distinct().limit(5).boxed().collect(Collectors.toSet());

            for (int column = 0; column < 9; column++) {
                List<Integer> numbers = r1.ints((((column * 10) + 1)), ((column + 1) * 10 + 1))
                        .distinct().limit(3).boxed().collect(Collectors.toList());
                int numbersIndex = 0;
                //first row
                if (firstRowColFillIndexes.contains(column)) {
                    ticket[0][column] = numbers.get(numbersIndex++);
                }
                //second row
                if (secondRowColFillIndexes.contains(column)) {
                    ticket[1][column] = numbers.get(numbersIndex++);
                }
                //third row
                if (thirdRowColFillIndexes.contains(column)) {
                    ticket[2][column] = numbers.get(numbersIndex++);
                }
            }

            bingoGen.displayTicket(ticket, bw, i);
            bingoGen.displayTicketHTML(ticket, htmlTickets, i);

        }
        FileWriter htmlFileWriter = new FileWriter(htmlInputFile);
        BufferedWriter htmlBW = new BufferedWriter(htmlFileWriter);
        htmlBW.write(HTML_TEMPLATE.replace("REPLACE_STRING", StringUtils.join(htmlTickets, "")));
        htmlBW.close();
        bw.close();
    }

    private void displayTicket(int[][] ticket, BufferedWriter bw, int i) throws IOException {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("---------B------I-----N------G----0-----"+i+"----------------------------");
        bw.write("---------------------------------------------------------");
        bw.newLine();
        bw.write("-------B------I-----N------G-----0-------"+i+"------------");
        for (int row = 0; row < 3; row++) {
            System.out.println("");
            bw.newLine();
            for (int column = 0; column < 9; column++) {
                String displayNum = "  * ";
                if (ticket[row][column] != 0) {
                    displayNum = ticket[row][column] < 10 ? " 0" : " ";
                    displayNum = displayNum + ticket[row][column] + " ";
                }
                System.out.print(displayNum);
                bw.write(displayNum);
            }
        }
        System.out.println("");
        bw.newLine();
        System.out.println("------------------------------------------------------------------------");
        bw.write("---------------------------------------------------------");
        bw.newLine();
    }


    private void displayTicketHTML(int[][] ticket, List<String> htmlTickets, int i) throws IOException {
       StringBuffer ticketStr = new StringBuffer("<div  a style=\"margin-bottom:10px;background: rgb(64, 160, 197); max-width:350px;border-radius: 10px;padding: 10px;\">" +
               "<div style=\"width: 100%;color: white;text-align: center;font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;font-weight: bolder;font-size: 20px;\">\n" +
               "                   BINGO \n" + i +
               "            </div>\n" +
               "            <table style=\"width: 100%;\">");
        List<Integer> numbersInTicket = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                if (ticket[row][column] != 0) {
                    numbersInTicket.add(ticket[row][column]);
                }
            }
        }
        Collections.shuffle(numbersInTicket);

        List<Integer> magic5Numbers = numbersInTicket.subList(0,5);
        System.out.println("magic5Numbers: " + magic5Numbers);
        if(magic5Numbers.size() != 5){
            throw new RuntimeException("magic5Numbers not working");
        }

        for (int row = 0; row < 3; row++) {
            ticketStr.append("<tr>");
            for (int column = 0; column < 9; column++) {
                if (ticket[row][column] != 0) {
                    if(magic5Numbers.contains(ticket[row][column])) {
                        ticketStr.append("<td class=\"magicBg\">");
                    }else{
                        ticketStr.append("<td class=\"nonEmptyBg\">");
                    }
                    ticketStr.append(ticket[row][column]);
                }else {
                    ticketStr.append("<td class=\"emptyBg\">");
                    ticketStr.append("&nbsp;");
                }
                ticketStr.append("</td>");
            }
            ticketStr.append("</tr>");
        }
        ticketStr.append("</table></div></div>");
        htmlTickets.add(ticketStr.toString());
    }
}
