// Avery Lanier, ATL230000

import java.util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main <AnyType> {
    public static void main(String[] args) throws IOException {
        Scanner scnr = new Scanner(System.in);
        String fileName = "";
        FileInputStream inFS = null; Scanner fileIn = null;
        Auditorium<Object> aud = null;

        // open file and use constructor to read through it
        while (fileName == "")
        {
            try {
                System.out.println("Please enter the file name: ");
                fileName = scnr.next();
                inFS = new FileInputStream(fileName);
                fileIn = new Scanner(inFS);
                aud = new Auditorium<Object>(fileIn);
            }
            catch (FileNotFoundException e){
                System.out.println("Error: file not found.");
                fileName = "";
            }
            finally {   // ensure FileInputStream and respective Scanner are closed
                fileIn.close();
                inFS.close();
            }
        }
        
        int numRows = aud.getNumRows();
        int seatsPerRow = aud.getSeatsPerRow();

        // letter array is created for convenience in seat letter validation
        char[] letters = new char[seatsPerRow];
        for (int i = 0; i < seatsPerRow; i++){
            letters[i] = 'A';
            letters[i] += i;
        }
        
        // print menu and validate menu input
        aud.printAuditorium();
        System.out.println("1. Reserve seats");
        System.out.println("2. Quit");
        int input = -1;
        while (input != 1 && input != 2)
        {
            input = -1;
            try {
                input = scnr.nextInt();
                if (input != 1 && input != 2){
                    System.out.print("Invalid input: Enter a number from the menu above: ");
                }
            }
            catch (InputMismatchException ignore){
                System.out.print("Invalid input: Enter a number from the menu above: ");
                scnr.next();
            }
        }
        
        while (input != 2){ // while the user does not quit
            // validate row input
            int row = -1;
            while (row == -1){
                row = -1;
                try{
                    System.out.print("Enter row number: ");
                    row = scnr.nextInt();
                    if (row < 1 || row > numRows){
                        System.out.println("Row number out of range.");
                        row = -1;
                    }
                }
                catch (InputMismatchException e){
                    System.out.println("Invalid input, please enter an integer.");
                    scnr.next();
                }
            }

            // validate seat letter input
            String seat = "1";
            char seatLetter = '\n';
            while (seat.equals("1")){
                System.out.print("Enter seat letter: ");
                try {
                    seat = "1";
                    seat = scnr.next();
                    seatLetter = seat.charAt(0);
                    if (seat.length() > 1){
                        System.out.println("Invalid input, please enter a single character");
                        seat = "1";  
                    }
                    else if (!Character.isAlphabetic(seatLetter)){
                        System.out.println("Invalid input, please enter an alphabetic character.");
                        seat = "1";
                    }
                    else {
                        boolean isInAud = false;
                        for (int i = 0; i < letters.length; i++){   // checks if the seat letter is in the auditorium
                            if (seatLetter == letters[i]){
                                isInAud = true;
                                break;
                            }
                        }
                        if (!isInAud){  // if the seat letter is not in the auditorium
                            System.out.println("Invalid input, please enter a seat letter from the displayed auditorium.");
                            seat = "1";
                        }
                    }

                } catch (InputMismatchException ignore){
                    System.out.println("Invalid input, please enter a single character.");
                }
            }

            int totalTickets = -1;
            int numAdults = -1;
            int numChildren = -1;
            int numSeniors = -1;
            while (totalTickets == -1){
                // adult tickets
                while (numAdults == -1){
                    numAdults = -1;
                    System.out.print("Number of adult tickets: ");
                    try{
                        numAdults = scnr.nextInt();
                        if (numAdults < 0){
                            System.out.println("Please enter a non-negative integer.");
                            numAdults = -1;
                        }
                    } catch (InputMismatchException ignore) {
                        System.out.println("Please enter a non-negative integer.");
                        scnr.next();
                    }
                }

                // child tickets
                while (numChildren == -1){
                    numChildren = -1;
                    System.out.print("Number of child tickets: ");
                    try{
                        numChildren = scnr.nextInt();
                        if (numChildren < 0){
                            System.out.println("Please enter a non-negative integer.");
                            numChildren = -1;
                        }
                    } catch (InputMismatchException ignore) {
                        System.out.println("Please enter a non-negative integer.");
                        scnr.next();
                    }
                }
                    
                // senior tickets
                numSeniors = -1;
                while (numSeniors == -1){
                    System.out.print("Number of senior tickets: ");
                    try{
                        numSeniors = scnr.nextInt();
                        if (numSeniors < 0){
                            System.out.println("Please enter a non-negative integer.");
                            numSeniors = -1;
                        }
                    } catch (InputMismatchException ignore) {
                        System.out.println("Please enter a non-negative integer.");
                        scnr.next();
                    }
                }

                totalTickets = numAdults + numChildren + numSeniors;
                if (totalTickets > seatsPerRow){    // if the total tickets exceeds the length of the row
                    System.out.println("Total number of tickets exceeds row length.");
                    totalTickets = -1;
                    numAdults = -1; numChildren = -1; numSeniors = -1;
                }
            }
            // end of input validation

            // book user seats or locate and offer best available
            boolean userSeatsAvailable = aud.checkAvailable(row, seatLetter, totalTickets);
            if (userSeatsAvailable){
                aud.bookAuditorium(row, seatLetter, numAdults, numChildren, numSeniors);
                System.out.println("User seats booked");
            }
            else {
                aud.bestAvailable(totalTickets, scnr, numAdults, numChildren, numSeniors);
            }

            // print menu and validate new menu input
            aud.printAuditorium();
            System.out.println("1. Reserve seats");
            System.out.println("2. Quit");
            input = -1;
            while (input != 1 && input != 2)
            {
                input = -1;
                try {
                    input = scnr.nextInt();
                    if (input != 1 && input != 2){
                        System.out.print("Invalid input: Enter a number from the menu above: ");
                    }
                }
                catch (InputMismatchException ignore){
                    System.out.print("Invalid input: Enter a number from the menu above: ");
                    scnr.next();
                }
            }
        }   // end of menu/user selection

        // print report to terminal and print the auditorium into "A1.txt"
        FileOutputStream fileOut = null;
        PrintStream outFS = null;
        try {
            fileOut = new FileOutputStream("A1.txt");
            outFS = new PrintStream(fileOut);
            outFS = aud.getFile(outFS);
            aud.printReport();
        } catch (FileNotFoundException e){}
        finally {   // ensure PrintStream and FileOutputStream are closed
            fileOut.close();
            outFS.close();
        }
        scnr.close();
    }   
}