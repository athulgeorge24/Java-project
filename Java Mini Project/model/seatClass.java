package model;

public class Seat {
   private int number;
   private boolean available;

   public Seat(int var1, boolean var2) {
      this.number = var1;
      this.available = var2;
   }

   public int getNumber() {
      return this.number;
   }

   public boolean isAvailable() {
      return this.available;
   }

   public void setAvailable(boolean var1) {
      this.available = var1;
   }

   public void toggleAvailability() {
      this.available = !this.available;
   }
}
