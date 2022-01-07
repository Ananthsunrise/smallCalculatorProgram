package calculator;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.lang.NumberFormatException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calculator{

    private final static String userName = "postgres";
    private final static String password = "ananth1703";

    public BigDecimal outputPrinter(BigDecimal result){
        if (result.compareTo(result.setScale(1, RoundingMode.CEILING)) == 0) {
            return result.setScale(0, RoundingMode.DOWN);
        } else {
            return result;
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement ps = null ;
        Statement stmt = null ;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sql_demo",userName,password);
            ps = connection.prepareStatement("insert into calculator_history(date_of_entry,first_number,second_number,operation_name,result_value) values(?,?,?,?,?)");
            stmt=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Date date=new Date();
            SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
            String currentDate=sdf.format(date);
            Scanner input = new Scanner(System.in);
            System.out.println("Welcome to my calculator");

            while (true) {
                System.out.println("\nTo perform addition, Enter 1");
                System.out.println("To perform substraction, Enter 2");
                System.out.println("To perform multiplication, Enter 3");
                System.out.println("To perform division, Enter 4");
                System.out.println("To show calculator history, Enter 5");
                System.out.println("To delete all history, Enter 6");
                System.out.println("To view recent 10 history, Enter 7");
                System.out.println("To delete recent 10 history, Enter 8");
                System.out.println("To exit, Enter 9\n");
                System.out.println("Enter your choice");
                ps.setString(1,currentDate);
                int choice = input.nextInt();

                if (choice == 5) {
                    rs= stmt.executeQuery("select * from calculator_history");
                    System.out.printf("%2s%2s%13s%23s%24s%20s\n","s.no ","dateofentry","1stnumber","2ndnumber","operationname","resultvalue");
                    while (rs.next()) {
                    System.out.printf("%-5s%-15s%-23s%-20s%-25s%-10s\n",rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                    }
                } else if (choice == 6) {
                    stmt.executeUpdate("drop table calculator_history");
                    stmt.executeUpdate("create table calculator_history(serial_number bigserial primary key, date_of_entry  varchar,first_number varchar, second_number varchar, operation_name varchar(40), result_value varchar)");
                    System.out.println("All history will be deleted!");
                } else if (choice == 7) {
                    rs= stmt.executeQuery("select * from calculator_history order by serial_number desc fetch first 10 row only");
                    System.out.printf("%2s%2s%13s%23s%24s%20s\n","s.no","dateofentry","1stnumber","2ndnumber","operationname","resultvalue");
                    while (rs.next()) {
                        System.out.printf("%-5s%-15s%-23s%-20s%-25s%-10s\n",rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                    }
                } else if (choice == 8) {
                    stmt.executeUpdate("DELETE FROM calculator_history WHERE serial_number  IN ( SELECT serial_number FROM calculator_history ORDER BY serial_number desc LIMIT 10)");
                    System.out.println("now recent 10 rows will be deleted!");
                    System.out.println("to view again calculator history, Enter 5");
                } else if (choice == 9) {
                    System.out.println("Now operation is stopped!!");
                    System.exit(0);
                } else if (choice > 9) {
                    System.out.println("Please enter the valid input");
                } else {
                    System.out.println("Enter first number");
                    BigDecimal  firstNumber = new BigDecimal(input.next());
                    ps.setString(2, String.valueOf(firstNumber));
                    System.out.println("Enter second number");
                    BigDecimal  secondNumber = new BigDecimal(input.next());
                    ps.setString(3, String.valueOf(secondNumber));
                    Operations Object = new CalculatorOperations();
                    Calculator calculator = new Calculator();
                    switch (choice) {
                        case 1:
                            ps.setString(4,"addition");
                            String resultValue= String.valueOf((calculator.outputPrinter(Object.adder(firstNumber, secondNumber))));
                            System.out.println(resultValue);
                            ps.setString(5,resultValue);
                            ps.execute();
                            break;
                        case 2:
                            ps.setString(4,"substraction");
                            resultValue= String.valueOf(calculator.outputPrinter(Object.subtraction(firstNumber, secondNumber)));
                            System.out.println(resultValue);
                            ps.setString(5,resultValue);
                            ps.execute();
                            break;
                        case 3:
                            ps.setString(4,"multiplication");
                            resultValue=String.valueOf(calculator.outputPrinter(Object.multiplier(firstNumber, secondNumber)));
                            System.out.println(resultValue);
                            ps.setString(5,resultValue);
                            ps.execute();
                            break;
                        case 4:
                            ps.setString(4,"division");
                            resultValue=String.valueOf(calculator.outputPrinter(Object.division(firstNumber, secondNumber)));
                            System.out.println(resultValue);
                            ps.setString(5,resultValue);
                            ps.execute();
                            break;
                        default:
                            System.out.println("Please enter the valid input");
                    }
                }
            }
        } catch (InputMismatchException ims) {
            System.out.println("Input Mismathed in choice. Program Closed");
        } catch (NumberFormatException nfe) {
            System.out.println("Input formate is wrong. Program Closed");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if(connection != null) {
                    connection.close();
                }
                if(stmt != null) {
                    stmt.close();
                }
                if(ps != null) {
                    ps.close();
                }
                if(rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


