package restaurantManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


enum MenuItemType {
    BEVERAGE,
    MAIN_COURSE
}
interface Reservable {
    boolean isReserved();
    void setReserved(boolean reserved);
}

abstract class MenuItemCategory {
    private int itemId;
    private String itemName;
    private String itemDescription;
    private double itemPrice;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
}

class Table1 implements Reservable {
    private int tableId;
    private String tableName;
    private int capacity;
    private boolean isReserved;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean isReserved() {
        return isReserved;
    }
    @Override
    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}



class Beverage extends MenuItemCategory {}

class MainCourse extends MenuItemCategory {}

public class RestaurantManagementApp {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/rest";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234567";

    private List<Table> tables;
    private List<MenuItem> menuItems;

    public RestaurantManagementApp() {
        tables = new ArrayList<>();
        menuItems = new ArrayList<>();
        fetchTablesFromDatabase();
        fetchMenuItemsFromDatabase();
    }

    public static void main(String[] args) {
        RestaurantManagementApp app = new RestaurantManagementApp();
        app.signupOrSignin();
    }

    public void signupOrSignin() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to the Restaurant Management System");
        System.out.println("1. Sign Up");
        System.out.println("2. Sign In");
        System.out.print("Enter your choice: ");
        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                signup(scanner);
                break;
            case 2:
                if (signin(scanner)) {
                    run();
                } else {
                    System.out.println("Signin failed. Exiting.");
                }
                break;
            default:
                System.out.println("Invalid choice. Exiting.");
                break;
        }
    }

    private void signup(Scanner scanner) {
    	  System.out.print("Enter a new username: ");
          String newUsername = scanner.next();

          System.out.print("Enter a new password: ");
          String newPassword = scanner.next();

          Connection connection = null;
          PreparedStatement preparedStatement = null;

          try {
              connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
              String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
              preparedStatement = connection.prepareStatement(sql);
              preparedStatement.setString(1, newUsername);
              preparedStatement.setString(2, newPassword);
              int rowsAffected = preparedStatement.executeUpdate();

              if (rowsAffected > 0) {
                  System.out.println("Signup successful. You can now sign in.");
              } else {
                  System.out.println("Signup failed. Please try again.");
              }
          } catch (SQLException e) {
              e.printStackTrace();
              System.out.println("An error occurred. Signup failed.");
          } finally {
              closeResources(null, preparedStatement, connection);
          }
    }

    private boolean signin(Scanner scanner) {
    	 System.out.print("Enter your username: ");
         String username = scanner.next();

         System.out.print("Enter your password: ");
         String password = scanner.next();

         Connection connection = null;
         PreparedStatement preparedStatement = null;
         ResultSet resultSet = null;

         try {
             connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
             preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setString(1, username);
             preparedStatement.setString(2, password);
             resultSet = preparedStatement.executeQuery();

             if (resultSet.next()) {
                 System.out.println("Signin successful. Welcome, " + username + "!");
                 return true;
             } else {
                 System.out.println("Invalid username or password. Signin failed.");
             }
         } catch (SQLException e) {
             e.printStackTrace();
             System.out.println("An error occurred. Signin failed.");
         } finally {
             closeResources(resultSet, preparedStatement, connection);
         }

         return false;
        // ... (existing code)
    }

    public void run() {
    	Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Welcome to the Restaurant Management System");
            System.out.println("1. View Tables");
            System.out.println("2. Reserve a Table");
            System.out.println("3. View Menu");
            System.out.println("4. Place an Order");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewTables();
                    break;
                case 2:
                    reserveTable(scanner);
                    break;
                case 3:
                    viewMenu();
                    break;
                case 4:
                    placeOrder(scanner);
                    break;
                case 5:
                    System.out.println("Thank you for using the Restaurant Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);
        // ... (existing code)
    }

    private void fetchTablesFromDatabase() {
    	 Connection connection = null;
         Statement statement = null;
         ResultSet resultSet = null;

         try {
             connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             statement = connection.createStatement();
             resultSet = statement.executeQuery("SELECT * FROM tables");

             while (resultSet.next()) {
                 Table table = new Table();
                 table.setTableId(resultSet.getInt("table_id"));
                 table.setTableName(resultSet.getString("table_name"));
                 table.setCapacity(resultSet.getInt("capacity"));
                 table.setReserved(resultSet.getBoolean("is_reserved"));
                 tables.add(table);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             closeResources(resultSet, statement, connection);
         }
        // ... (existing code)
    }

    private void fetchMenuItemsFromDatabase() {
    	 Connection connection = null;
         Statement statement = null;
         ResultSet resultSet = null;

         try {
             connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             statement = connection.createStatement();
             resultSet = statement.executeQuery("SELECT * FROM menu_items");

             while (resultSet.next()) {
                 MenuItem menuItem = new MenuItem();
                 menuItem.setItemId(resultSet.getInt("item_id"));
                 menuItem.setItemName(resultSet.getString("item_name"));
                 menuItem.setItemDescription(resultSet.getString("item_description"));
                 menuItem.setItemPrice(resultSet.getDouble("item_price"));
                 menuItems.add(menuItem);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             closeResources(resultSet, statement, connection);
         }
        // ... (existing code)
    }

    private void viewTables() {
    	   System.out.println("Tables:");
           for (Table table : tables) {
               System.out.println(table.getTableId() + "\t" + table.getTableName() + "\tCapacity: " + table.getCapacity() + "\tReserved: " + (table.isReserved() ? "Yes" : "No"));
           }
        // ... (existing code)
    }

    private void reserveTable(Scanner scanner) {
        System.out.println("Tables available for reservation:");
        for (Table table : tables) {
            if (!table.isReserved()) {
                System.out.println(table.getTableId() + "\t" + table.getTableName() + "\tCapacity: " + table.getCapacity());
            }
        }

        System.out.print("Enter the Table ID to reserve: ");
        int tableId = scanner.nextInt();

        Table selectedTable = null;
        for (Table table : tables) {
            if (table.getTableId() == tableId) {
                selectedTable = table;
                break;
            }
        }

        if (selectedTable == null) {
            System.out.println("Invalid Table ID. Reservation failed.");
            return;
        }

        else 
        	if (selectedTable.isReserved()) {
            System.out.println("The selected table is already reserved. Reservation failed.");
            return;
        }

        System.out.print("Enter the reservation date and time (yyyy-MM-dd HH:mm:ss): ");
        scanner.nextLine(); // Consume the newline character from the previous nextInt()
        String dateTimeString = scanner.nextLine();

        System.out.print("Enter the party size: ");
        int partySize = scanner.nextInt();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO reservations (table_id, reservation_time, party_size) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tableId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateTimeString));
            preparedStatement.setInt(3, partySize);
            preparedStatement.executeUpdate();

            selectedTable.setReserved(true);
            System.out.println("Table reservation successful.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred. Reservation failed.");
        } finally {
            closeResources(null, preparedStatement, connection);
        }
    }




    private void viewMenu() {
    	 System.out.println("Menu Items:");
         for (MenuItem menuItem : menuItems) {
             System.out.println(menuItem.getItemId() + "\t" + menuItem.getItemName() + "\t" + menuItem.getItemDescription() + "\t$" + menuItem.getItemPrice());
         }
        // ... (existing code)
    }

    private void placeOrder(Scanner scanner) {
        System.out.println("Tables with active reservations:");
        for (Table table : tables) {
            if (table.isReserved()) {
                System.out.println(table.getTableId() + "\t" + table.getTableName());
            }
        }

        System.out.print("Enter the Table ID for the order: ");
        int tableId = scanner.nextInt();

        Table selectedTable = null;
        for (Table table : tables) {
            if (table.getTableId() == tableId) {
                selectedTable = table;
                break;
            }
        }

        if (selectedTable == null || !selectedTable.isReserved()) {
            System.out.println("Invalid Table ID or the table is not reserved. Order placement failed.");
            return;
        }

        List<MenuItem> orderedItems = new ArrayList<>();
        double totalPrice = 0;

        while (true) {
            System.out.println("Menu Items:");
            for (MenuItem menuItem : menuItems) {
                System.out.println(menuItem.getItemId() + "\t" + menuItem.getItemName() + "\t$" + menuItem.getItemPrice());
            }

            System.out.print("Enter the Menu Item ID to add to the order (or -1 to finish): ");
            int itemId = scanner.nextInt();

            if (itemId == -1) {
                break;
            }

            MenuItem selectedMenuItem = null;
            for (MenuItem menuItem : menuItems) {
                if (menuItem.getItemId() == itemId) {
                    selectedMenuItem = menuItem;
                    break;
                }
            }

            if (selectedMenuItem == null) {
                System.out.println("Invalid Menu Item ID. Please try again.");
                continue;
            }

            orderedItems.add(selectedMenuItem);
            totalPrice += selectedMenuItem.getItemPrice();
        }

        if (orderedItems.isEmpty()) {
            System.out.println("Order cannot be empty. Order placement failed.");
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO orders (table_id, order_time, total_price) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, tableId);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setDouble(3, totalPrice);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                sql = "INSERT INTO order_items (order_id, item_id) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(sql);

                for (MenuItem orderedItem : orderedItems) {
                    preparedStatement.setInt(1, orderId);
                    preparedStatement.setInt(2, orderedItem.getItemId());
                    preparedStatement.addBatch();
                }

                preparedStatement.executeBatch();

                System.out.println("Order placed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred. Order placement failed.");
        } finally {
            closeResources(null, preparedStatement, connection);
        }

        // ... (existing code)
    }

    private void closeResources(ResultSet resultSet, Statement statement, Connection connection) {
    	  try {
              if (resultSet != null) {
                  resultSet.close();
              }
              if (statement != null) {
                  statement.close();
              }
              if (connection != null) {
                 connection.close();
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }
    }

