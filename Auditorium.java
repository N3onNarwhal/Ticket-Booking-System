// Avery Lanier, ATL230000

import java.io.PrintStream;
import java.util.Scanner;

public class Auditorium <AnyType> {
    // Member
    private Node <AnyType> head;

    // Constructors
    public Auditorium()
    {
        head = null;
    }
    public Auditorium(Node<AnyType> newHead)
    {
        head = newHead;
    }
    public Auditorium(Scanner inFS)
    {
        head = null;
        setAuditorium(inFS);
    }

    // Mutator
    public void setHead(Node<AnyType> curr)
    {
        head = curr;
    }

    // Accessor
    public Node<AnyType> getHead()
    {
        return head;
    }

    // Functions to get auditorium dimensions
    public int getNumRows(){
        Node<AnyType> curr = head;
        int numRows = 0;
        while (curr != null){
            curr = curr.getDown();
            numRows++;
        }
        return numRows;
    }
    public int getSeatsPerRow(){
        Node<AnyType> curr = head;
        int seatsPerRow = 0;
        while (curr != null){
            curr = curr.getNext();
            seatsPerRow++;
        }
        return seatsPerRow;
    }

    // additional methods
    public void setAuditorium(Scanner inFS){
        // uses the file scanner to create the auditorium with values from the file
        Node<AnyType> curr = new Node<AnyType>();
        Node<AnyType> rowHead = null;
        int currRow = 1;
        while (inFS.hasNextLine())
        {
            String currLine = inFS.nextLine();
            char[] seats = new char[currLine.length()];
            for (int i = 0; i < currLine.length(); i++)
            {
                seats[i] = currLine.charAt(i);
            }

            for (int i = 0; i < seats.length; i++)
            {
                if (i == 0){    // for the head of the row
                    if (currRow == 1){
                        rowHead = new Node<AnyType>();
                    }

                    // set payload
                    Object newSeat = new Seat(currRow, 'A',seats[0]);
                    rowHead.setPayload(newSeat);
                    rowHead.setPrev(null);

                    // set down
                    if (inFS.hasNextLine()){
                        rowHead.setDown(new Node<AnyType>());
                    }
                    else {
                        rowHead.setDown(null);
                    }

                    // set next
                    if (seats.length == 1){
                        rowHead.setNext(null);
                    }
                    else {
                        rowHead.setNext(new Node<AnyType>());
                    }

                    // if on the very first Node, assign head member to equal rowHead
                    if (currRow == 1){
                        head = rowHead;
                    }
                }
                else {  // for every element after the head of the row
                    Node<AnyType> prev = curr;
                    if (i == 1){
                        prev = rowHead;
                        curr = rowHead.getNext();
                    }
                    else {
                        prev = curr;
                        curr = curr.getNext();
                    }
                    
                    curr.setPrev(prev); // set prev

                    // set next
                    if (i == seats.length-1){
                        curr.setNext(null);
                    }
                    else {
                        curr.setNext(new Node<AnyType>());
                    }

                    // set payload
                    char currSeat = 'A';
                    currSeat += i;
                    Object newSeat = new Seat(currRow, currSeat, seats[i]);
                    curr.setPayload(newSeat);
                }
            }
            rowHead = rowHead.getDown();
            currRow++;
        }
    }   // end of setAuditorium

    public void printAuditorium() {
        // prints the auditorium to the terminal (doesn't show user ticket types, only if the seats are taken or not)
        try {
            if (head == null){  // just in case
                System.out.println("Auditorium object empty");
                return;
            }
        } catch (NullPointerException ignore){}

        // variables for traversal
        Node<AnyType> curr = head;
        Node<AnyType> rowHead = head;
        int currRow = 1;
        int seatCount = 0;
        curr = rowHead;

        // print letters at top
        System.out.print("   ");
        while (curr != null)
        {
            char currSeat = 'A';
            currSeat += seatCount;
            System.out.print(currSeat);
            seatCount++;
            curr = curr.getNext();
        }
        System.out.print("\n");

        // print seats to terminal
        while (rowHead != null)
        {
            System.out.print(currRow);
            System.out.print("  ");
            curr = rowHead;
            for (int i = 0; i < seatCount; i++)
            {
                Object currSeat = curr.getPayload();
                char seatType = ((Seat) currSeat).getTicketType();
                
                if (seatType == 'A' || seatType == 'C' || seatType == 'S'){
                    System.out.print("#");
                }
                else {
                    System.out.print('.');
                }

                curr = curr.getNext();
            }
            rowHead = rowHead.getDown();
            currRow++;
            System.out.println("");
        }
    }   // end of printAuditorium

    public PrintStream getFile(PrintStream outFS) {
        // returns a PrintStream object with the seat types
        // this PrintStream object is handled in Main to print the auditorium
        try {
            if (head == null){
                System.out.println("Auditorium object empty");
                return outFS;
            }
        } catch (NullPointerException ignore){}

        Node<AnyType> curr = head;
        Node<AnyType> rowHead = head;
        curr = rowHead;
        // print seats to terminal
        
        int numRows = getNumRows();
        int seatsPerRow = getSeatsPerRow();
        for (int i = 0; i < numRows; i++)
        {
            curr = rowHead;
            
            for (int j = 0; j < seatsPerRow; j++)
            {
                char seatType = ((Seat)curr.getPayload()).getTicketType();
                outFS.print(seatType);
                curr = curr.getNext();
            }
            rowHead = rowHead.getDown();
            outFS.println("");
        }

        return outFS;
    }
    
    public void printReport(){
        // prints statistics to the terminal
        int totalSeats = getNumRows() * getSeatsPerRow();
        int totalTickets = 0;
        double totalSales = 0.0;
        int aTickets = 0; int cTickets = 0; int sTickets = 0;
        Node<AnyType> curr = head;
        Node<AnyType> rowHead = head;
        for (int i = 0; i < getNumRows(); i++){
            curr = rowHead;
            for (int j = 0; j < getSeatsPerRow(); j++)
            {
                char currSeat = ((Seat)curr.getPayload()).getTicketType();
                if (currSeat == 'A'){
                    aTickets++;
                }
                else if (currSeat == 'C'){
                    cTickets++;
                }
                else if (currSeat == 'S'){
                    sTickets++;
                }
                curr = curr.getNext();
            }
            rowHead = rowHead.getDown();
        }
        totalTickets = aTickets + cTickets + sTickets;
        totalSales = (aTickets * 10.0) + (cTickets * 5.0) + (sTickets * 7.5);
        System.out.println("Total Seats: " + totalSeats);
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Adult Tickets :" + aTickets);
        System.out.println("Child Tickets :" + cTickets);
        System.out.println("Senior Tickets :" + sTickets);
        System.out.printf("Total Sales: $%.2f", totalSales);
        System.out.print("\n");
    }

    public boolean checkAvailable(int row, char startSeat, int totalTickets){
        // checks if a set of seats is available
        if (head == null){
            return false;
        }

        Node<AnyType> curr = head;

        // get to correct row
        for (int i = 1; i < row; i++)
        {
            curr = curr.getDown();
        }
        // get to correct starting seat
        char currSeat = 'A';
        int toSeat = startSeat - currSeat;
        for (int i = 0; i < toSeat; i++)
        {
            curr = curr.getNext();
            currSeat += 1;
            if (curr == null){
                return false;
            }
        }
        
        // checks ticket types of each seat within desired set
        for (int i = 0; i < totalTickets; i++)
        {
            if (curr == null){
                return false;
            }
            char ticketType = ((Seat)curr.getPayload()).getTicketType();
            if (ticketType != '.'){
                return false;
            }
            else {
                curr = curr.getNext();
            }
        }
        return true;
    }   // end of checkAvailable

    public void bookAuditorium(int row, char seat, int adults, int children, int seniors){
        // books the desired seats in the auditorium
        Node<AnyType> curr = head;
        for (int i = 1; i < row; i++)
        {
            curr = curr.getDown();
        }

        Object startSeat = curr.getPayload();
        while (((Seat) startSeat).getSeat() != seat)
        {
            curr = curr.getNext();
            startSeat = curr.getPayload();
        }
        int i = 1;
        while (i <= adults)
        {
            ((Seat) curr.getPayload()).setTicketType('A');
            curr = curr.getNext();
            i++;
        }

        i = 1;
        while (i <= children)
        {
            ((Seat) curr.getPayload()).setTicketType('C');
            curr = curr.getNext();
            i++;
        }

        i = 1;
        while (i <= seniors)
        {
            ((Seat) curr.getPayload()).setTicketType('S');
            curr = curr.getNext();
            i++;
        }
    }   // end of bookAuditorium

    public void bestAvailable(int totalTickets, Scanner scnr, int a, int c, int s){
        // locates the best available seats in the entire auditorium and offers them to the user
        int numRows = getNumRows();
        int seatsPerRow = getSeatsPerRow();

        // find the center of the auditorium
        double centerX = seatsPerRow / 2.0;
        double centerY = (numRows / 2.0) + 0.5;
        
        // initialize finalCoordinates (row, column, starting from (1,1))
        int[] finalCoordinates = new int[2];
        finalCoordinates[0] = 0;
        finalCoordinates[1] = 0;

        // set variable for finding the middle of a set of seats
        double midSet = totalTickets / 2.0;

        // additional variables
        boolean noneAvailable = true;   // keeps track of if there are any sets of seats for the total # of tickets available
        char start = 'A';
        double bestDistance = 1000.0;

        for (int i = 1; i <= numRows; i++)
        {
            for (int j = 1; j <= (seatsPerRow - totalTickets); j++)
            {
                start = 'A'; start += j;
                boolean available = checkAvailable(i, start, totalTickets);
                // check if wanted seats are available
                if (available){
                    // compare the difference between the distance of the middle of the available
                    // set to the center to the distance of the currently stored final set
                    noneAvailable = false;
                    double centerOfSeats = j + midSet;
                    double seatDiff = Math.abs(centerOfSeats - centerX);
                    double rowDiff = Math.abs(i - centerY);
                    double centerDistance = getDistance(rowDiff, seatDiff);
                    if (centerDistance < bestDistance){
                        finalCoordinates[0] = i;
                        finalCoordinates[1] = j;
                        bestDistance = centerDistance;
                    }
                    else if (centerDistance == bestDistance){    // if the distance is the same
                        if (finalCoordinates[0] != i){  // if they are on different rows
                            if (Math.abs(finalCoordinates[0] - centerY) > Math.abs(i - centerY)){
                                // the row closest to center is stored
                                finalCoordinates[0] = i;
                                finalCoordinates[1] = j;
                            }
                        }
                    }
                }
            }
        }   // end of for loop
        if (noneAvailable){
            System.out.println("no seats available.");
            return;
        }

        // variables created for easier printing
        char startLetter = 'A';
        startLetter += finalCoordinates[1];
        char endLetter = startLetter;
        endLetter += (totalTickets - 1);
        String row = Integer.toString(finalCoordinates[0]);

        // ask if user would like to book the best available seats and do so if they answer Y
        char input = '\n';
        while (input == '\n')
        {
            System.out.println("Would you like to reserve these seats? (Y/N)");
            System.out.println(row + startLetter + " - " + row + endLetter);
            input = scnr.next().charAt(0);
            if (input != 'Y' && input != 'N'){
                System.out.println("Please enter Y or N.");
                input = '\n';
            }
        }
        if (input == 'Y'){
            bookAuditorium(finalCoordinates[0], startLetter, a, c, s);
        }
    }   // end of bestAvailable

    public double getDistance(double yDiff, double xDiff){
        double distance = Math.sqrt((yDiff*yDiff) + (xDiff*xDiff));
        return distance;
    }

}