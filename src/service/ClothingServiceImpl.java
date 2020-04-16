package service;

import model.Clothing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClothingServiceImpl implements IClothingService {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/clothing_manager";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "loi123456";


    String selectClothing = "select * from clothing_manager.clothing";

    String updateClothing = "update clothing set name = ?, description = ?, picture = ?, price = ?, origin = ? where id = ?";
    String deleteClothing = "delete from clothing where id = ?";

    public ClothingServiceImpl(){
    }
    protected Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
        } catch (SQLException e) {
            System.out.println("Khong ket noi duoc");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    @Override
    public List<Clothing> findAll() {
        List<Clothing> clothing = new ArrayList<>();

        String selectAllClothing = "SELECT c.name, c.description, c.picture, c.price, c.origin FROM clothing c";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(selectAllClothing)
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String picture = resultSet.getString("picture");
                int price = resultSet.getInt("price");
                String origin = resultSet.getString("origin");

                clothing.add(new Clothing(name, description, picture, price, origin));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clothing;
    }
    @Override//TODO Hiển thị danh sách thông ton của cả 2 bảng
    public List<Clothing> findAllClothingCategory() {
        List<Clothing> clothingCategory = new ArrayList<>();
        String selectAll = "SELECT cl.id, ca.category_name, ca.status, cl.name, cl.description, cl.picture, cl.price, cl.origin\n" +
                "FROM category as ca INNER JOIN clothing cl on cl.category_id = ca.category_id;";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String picture = resultSet.getString("picture");
                int price = resultSet.getInt("price");
                String origin = resultSet.getString("origin");
                String category = resultSet.getString("category_name");
                String status = resultSet.getString("status");

                clothingCategory.add(new Clothing(name, description, picture, price, origin,category,status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clothingCategory;
    }

    @Override
    public void insert(Clothing clothing) throws SQLException {
        String insertClothing = "insert into clothing_manager.clothing (name, description, picture, price, origin) value (?,?,?,?,?)";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(insertClothing)
        ) {

            statement.setString(1, clothing.getName());
            statement.setString(2, clothing.getDescription());
            statement.setString(3, clothing.getPicture());
            statement.setInt(4, clothing.getPrice());
            statement.setString(5, clothing.getOrigin());
            System.out.println(statement);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public List<Clothing> findByCategoryID(int id) {
//        List<Clothing> clothingList = new ArrayList<>();
//
//    }

    @Override
    public boolean update(Clothing clothing) throws SQLException {
        boolean rowUpdated;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(updateClothing)) {
                statement.setString(1, clothing.getName());
                statement.setString(2,clothing.getDescription());
                statement.setString(3,clothing.getPicture());
                statement.setInt(4,clothing.getPrice());
                statement.setString(5,clothing.getOrigin());
                statement.setInt(6,clothing.getId());

                rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public boolean remove(int id) throws SQLException {
        boolean rowDeleted;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteClothing)) {
            statement.setInt(1,id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}