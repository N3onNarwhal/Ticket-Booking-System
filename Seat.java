// Avery Lanier, ATL230000

public class Seat {
    // Members
    private int row;
    private char seat;
    private char ticketType;

    // Constructors
    public Seat()
    {
        row = -1;
        seat = '\0';
        ticketType = '.';
    }
    public Seat(int newRow, char newSeat, char newType)
    {
        row = newRow;
        seat = newSeat;
        ticketType = newType;
    }

    // Mutators
    public void setRow(int newRow)
    {
        row = newRow;
    }
    public void setSeat(char newSeat)
    {
        seat = newSeat;
    }
    public void setTicketType(char newType)
    {
        ticketType = newType;
    }

    // Accessors
    public int getRow()
    {
        return row;
    }
    public char getSeat()
    {
        return seat;
    }
    public char getTicketType()
    {
        return ticketType;
    }
}