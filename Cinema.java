package cinema;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
     static int rows;
     static int seats;
     static int totalSeats;
     static char[][] layout;
     static int chosenRow;
    static int chosenSeat;
    static final int smallRoomSeats = 60;
    static final int lowerPrice = 8;
    static final int higherPrice = 10;
    static final char emptySeat = 'S';
    static final char bookedSeat = 'B';
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {

        setCinemaLayout();
        calculateTotalSeats();
        startMenu();

    }
    public static void startMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("""

                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit""");

        int menuAction = scanner.nextInt();

        switch (menuAction) {
            case 0: break;
            case 1: {
                printCinemaLayout();
                startMenu();

            }
            case 2: {
                reserveSeat();
                calculateSeatPrice();
                startMenu();

            }
            case 3: {
                displayStats();
                startMenu();

            }

        }

    }
    public static void setCinemaLayout(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();

        layout = new char[rows][seats];
        for (int index = 0; index < rows; index++) {
            Arrays.fill(layout[index], emptySeat);
        }
    }
    public static void printCinemaLayout() {
        System.out.println("Cinema:");

        for (int i = 0; i <= rows; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j <= seats; j++) {
                if (i == 0 && j == 0) {
                    row.append("  ");
                } else if (i == 0) {
                    row.append(String.format("%d ", j)); // Seat numeration
                } else if (j == 0) {
                    row.append(String.format("%d ", i)); //row numeration
                } else {
                    row.append(String.format("%c ", layout[i - 1][j - 1]));
                }
            }

            System.out.println(row);
        }

    }
    public static void calculateTotalSeats(){
        totalSeats = rows*seats;
    }

    public static int calculateProfit(){
        int profit;
        if (totalSeats < smallRoomSeats){
            profit = totalSeats * higherPrice;
        }
        else {
            profit = ((rows / 2) * seats * higherPrice) + ((rows - (rows / 2)) * seats * lowerPrice);
        }
        return profit;
    }
    public static int calculateSoldTickets(){
        int bookedSeats = 0;
        for(int i = 0; i < rows; i++){
            for (int j = 0; j < seats;j++){
                if (layout[i][j] == 'B'){
                    bookedSeats++;
                }
            }
        }

        return bookedSeats;
    }
    public static double percentageOfSeatsBooked(){
        return ((double) calculateSoldTickets() / totalSeats)*100;
    }
    public static int calculateCurrentProfit(){
        int currentProfit = 0;
        for(int i = 0; i < rows; i++){
            for (int j = 0; j < seats;j++){
                if (layout[i][j] == 'B'){
                    if (totalSeats > smallRoomSeats) {
                        int frontHalf = rows / 2;
                        if (i >= frontHalf) {
                            currentProfit += lowerPrice;
                        }
                        else {
                            currentProfit += higherPrice;
                        }
                    }
                    else {
                        currentProfit += higherPrice;
                    }
                }
            }
        }

        return currentProfit;
    }
    public static void displayStats(){
        System.out.println("Number of purchased tickets: "+calculateSoldTickets());
        System.out.println("Percentage: "+df.format(percentageOfSeatsBooked())+"%");
        System.out.println("Current income: $"+calculateCurrentProfit());
        System.out.println("Total income: $"+calculateProfit());
    }
    public static void calculateSeatPrice() {

        if (totalSeats > smallRoomSeats) {
            int frontHalf = rows / 2;
            if (chosenRow > frontHalf) {
                System.out.printf("Ticket price: $%d", lowerPrice);
                return;
            }
        }
            System.out.printf("Ticket price: $%d", higherPrice);

    }
    public static void reserveSeat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a row number:");
        chosenRow = scanner.nextInt();

        System.out.println("Enter a seat number in that row:");
        chosenSeat = scanner.nextInt();

        int rowAdjustedForArrayIndex = chosenRow - 1;
        int seatAdjustedForArrayIndex = chosenSeat - 1;

        if (chosenSeat > totalSeats || chosenRow > rows){
            System.out.println("Wrong input!"); //seat doesn't exist
        }
        else if (layout[rowAdjustedForArrayIndex][seatAdjustedForArrayIndex] == 'B'){
            System.out.println("That ticket has already been purchased!"); // seat is already booked
            reserveSeat();
        }
        else {
        layout[rowAdjustedForArrayIndex][seatAdjustedForArrayIndex] = bookedSeat; // successful purchase
        }
    }
}

