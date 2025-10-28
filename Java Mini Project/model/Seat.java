package model;

public class Seat {
    private int number;
    private boolean available;
    
    public Seat(int number, boolean available) {
        this.number = number;
        this.available = available;
    }
    
    public int getNumber() { return number; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public void toggleAvailability() { this.available = !this.available; }
}