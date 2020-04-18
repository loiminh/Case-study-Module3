package service.BillDetails;

import model.BillDetails;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDetailsImpl implements IBillDetails{
    private static final String  jdbcURL = "jdbc:mysql://localhost:3306/clothing_manager";
    private static final String jdbcUser = "root";
    private static final String jdbcPass = "loi123456";
    String selectBillDetails = "select * from bill_details";
    String insertBillDetails = "insert into bill_detail (number_of_export, export_price) value (?,?)";
    String updateBillDetails = "update bill_detail set number_of_export = ?, export_price = ? where id = ?";
    String deleteBillDetails = "delete from bill_detail where id = ?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
        } catch (SQLException e) {
            System.out.println("Khong ket noi duoc");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    @Override
    public List<BillDetails> findAll() {
        List<BillDetails> billDetails = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectBillDetails)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int number_import = Integer.parseInt(resultSet.getString("number_of_export"));
                float import_price = Float.parseFloat(resultSet.getString("export_price"));

                billDetails.add(new BillDetails(number_import,import_price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billDetails;
    }

    @Override
    public void insert(BillDetails billDetails) throws SQLException {

    }

    @Override
    public boolean update(BillDetails billDetails) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(int id) throws SQLException {
        return false;
    }
}
