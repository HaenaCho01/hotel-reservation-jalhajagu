import ui.Menu;

public class HotelReservation {
    public static void main(String[] args) {
        Menu menu = new Menu();
        while(true) {
            try {
                menu.show();
                break;
            } catch(RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
