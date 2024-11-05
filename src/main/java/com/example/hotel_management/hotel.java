package com.example.hotel_management;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class hotel {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String user = "root";
    private static final String password = "Shitlamaa@8989";



    public static void main(String[] args) {
       try{
           Class.forName("com.mysql.jdbc.Driver");

       }catch(ClassNotFoundException e){
           System.out.println(e.getMessage());
       }
       try{
           Connection connection= DriverManager.getConnection(url,user,password);
           while(true){
               System.out.println();
               System.out.println("HOTEL MANAGEMENT SYSTEM");
               Scanner scanner = new Scanner(System.in);
               System.out.println("1. Reserve a room");
               System.out.println("2. View Reservation");
               System.out.println("3. Get Room Number");
               System.out.println("4. Update Reservation ");
               System.out.println("5. Remove Reservation");
               System.out.println("0. Exit");
               System.out.println("Choose an option:");
               int choice=scanner.nextInt();
               switch (choice){
                   case 1:
                       reserveRoom(connection,scanner);
                       break;
                   case 2:
                       viewReservation(connection);
                       break;
                   case 3:
                       getRoomNumber(connection,scanner);
                       break;
                   case 4:
                       updateReservation(connection,scanner);
                       break;
                   case 5:
                       deleteReservation(connection,scanner);
                       break;
                   case 0:
                       exit();
                       scanner.close();
                       return;
                   default:
                       System.out.println("Invalid choice. Try Again.");
               }
           }

            }catch(SQLException e){
           System.out.println(e.getMessage());
           System.out.println("Connection failed...");
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
    }



    private static void reserveRoom(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter guest name:");
            String guestName = scanner.next();
            scanner.nextLine();
            System.out.print("Enter room number:");
            int roomNumber = scanner.nextInt();
            System.out.println("Enter contact number:");
            String contactNumber = scanner.next();

            String sql = "insert into reservation(guest_name,room_no,contact_no)" + "values('" + guestName + "'," + roomNumber + ",'" + contactNumber + "')";
            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation successful!");
                } else {
                    System.out.println("Reservation failed.");
                }
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



        private static void viewReservation(Connection connection) throws   SQLException{
        String sql="select reservation_id,guest_name, room_no,contact_no,reservation_date from reservation;";
        try(Statement statement= connection.createStatement();
            ResultSet resultSet= statement.executeQuery(sql)){
            System.out.println("+-----------------+----------------+------------+--------------+-------------------+");
            System.out.println("| Reservation ID  | Guest_name     | Room_no    | Contact_no   | Reservation_date  |");
            System.out.println("+-----------------+----------------+------------+--------------+-------------------+");
            while (resultSet.next()){
                int reservationId=resultSet.getInt("reservation_id");
                String guest_name=resultSet.getString("guest_name");
                int roomNumber=resultSet.getInt("room_no");
                String contactNumber=resultSet.getString("contact_no");
                String reservationDate= resultSet.getString("reservation_date");

                System.out.println("       " +reservationId+"         "+ guest_name+"          "+ roomNumber+"       "+ contactNumber+"         "+reservationDate);
            }
            System.out.println("+-----------------+----------------+------------+--------------+-------------------+");

        }
        }



        private static void getRoomNumber(Connection connection, Scanner scanner){
        try{
            System.out.println("Enter Reservation Id:");
            int reservationId=scanner.nextInt();
            System.out.println("Enter Guest Name:");
            String guestName=scanner.next();


            String sql="select room_no from reservation "+"where reservation_id="+reservationId+"and guest_name="+guestName+"'";

            try(Statement statement= connection.createStatement();
            ResultSet resultSet= statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_no");
                    System.out.println("Room number for Reservation ID " + reservationId + " and Guest " + guestName + " is: " + roomNumber);
                } else {
                    System.out.println("Reservation not found for the given ID and guest name.");
                }
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



        private static void updateReservation(Connection connection,Scanner scanner){
            System.out.println("Enter reservation ID to update:");
            int reservationId=scanner.nextInt();
            scanner.nextLine();

            if(!reservationExists(connection,reservationId)){
                System.out.println("Reservation not found for this given ID.");
                return;
            }
            System.out.print("Enter new guest name:");
            String newGuestName=scanner.next();
            System.out.print("Enter new room Number:");
            int newRoomNumber=scanner.nextInt();
            System.out.print("Enter new Contact number:");
            String newContactNumber=scanner.next();

            String sql="update reservation set guest_name='"+ newGuestName +"',"+ "room_no="+newRoomNumber+","
                    +"contact_no="+ newContactNumber
                    +"where reservation_id="+reservationId;
            try (Statement statement=connection.createStatement()){
                int affectedRows=statement.executeUpdate(sql);

                if(affectedRows>0){
                    System.out.println("Reservation updated successfully!");
                }
                else{
                    System.out.println("Reservation update failed.");
                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }



        private static void deleteReservation(Connection connection,Scanner scanner){
        try{
            System.out.println("Enter reservation ID to delete:");
            int reservationId=scanner.nextInt();

            if(!reservationExists(connection,reservationId)){
                System.out.println("Reservation not found for tthe given ID.");
                return;
            }
            String sql="delete from reservation where reservation_id="+reservationId;
            try(Statement statement= connection.createStatement()){
                int affectedRows=statement.executeUpdate(sql);

                if(affectedRows>0){
                    System.out.println("Reservation deleted successfully!");
                }
                else{
                  System.out.println("Reservation deletion failed.");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        }



        private static boolean reservationExists(Connection connection, int reservationId){
        try{
            String sql="select reservation_id from reservation where reservation_id="+reservationId;
            try(Statement statement=connection.createStatement();
                ResultSet resultSet= statement.executeQuery(sql)){
                return resultSet.next();
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        }



        public static void exit() throws InterruptedException{
        System.out.print("Exiting System.");
        int i=5;
        while (i!=0){
            System.out.println(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank you for using Hotel Reservation System!!!");
        }
        }


