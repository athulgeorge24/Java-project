package model;
import java.util.ArrayList;
import java.util.List;

public class UniSeatModel {
    private List<Seat> seatList;
    private int totalSeats = 156;
    private int availableSeats = 124;
    private int occupiedSeats = 32;
    
    public UniSeatModel() {
        this.seatList = new ArrayList<>();
    }
    
    public void generateSeatLayout(int rows, int columns) {
        seatList.clear();
        for (int i = 1; i <= rows * columns; i++) {
            seatList.add(new Seat(i, true));
        }
        updateStatistics();
    }
    
    public void toggleSeatAvailability(int seatNumber) {
        for (Seat seat : seatList) {
            if (seat.getNumber() == seatNumber) {
                seat.toggleAvailability();
                break;
            }
        }
        updateStatistics();
    }
    
    private void updateStatistics() {
        int available = 0;
        for (Seat seat : seatList) {
            if (seat.isAvailable()) available++;
        }
        this.availableSeats = available;
        this.occupiedSeats = seatList.size() - available;
        this.totalSeats = seatList.size();
    }
    
    public List<Seat> getSeatList() { return new ArrayList<>(seatList); }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public int getOccupiedSeats() { return occupiedSeats; }
    public int simulateCSVProcessing() { return (int) (Math.random() * 100) + 10; }
}