package unrgo.com;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public interface DAO <T> {
    default Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection result=DriverManager.getConnection("jdbc:sqlite:D:/sqlite/catalogs.db");

        return result;
    }
    T insert(T entity);
    boolean update(T entity);
    ArrayList<T> getAll();
    boolean delete(int id);


//    boolean delete(int id);
//    List<T> find(T entity);
//    default List<T> findById(int id){
//        List<T> result=new ArrayList<>();
//        return result;
//    }
//    List<T> findAll();
}

class DAO_Catalog implements DAO<Catalog>{
    Connection connect;
    @Override
    public Catalog insert(Catalog entity) {
        try {
            if(connect==null){

                connect=getConnection();

            }
            String sql="INSERT INTO Catalogs (`name`, `producer`, `price`,`count`)  VALUES ( ?,  ?,  ?,? )";
            PreparedStatement preparedStatement=connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getProducer());
            preparedStatement.setInt(3,entity.getPrice());
            preparedStatement.setInt(4,entity.getCount());

            preparedStatement.execute();
            ResultSet rs=preparedStatement.getGeneratedKeys();
            rs.next();
            int key=rs.getInt(1);
            entity.setId(key);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return entity;
    }
    @Override
    public boolean delete(int id) {
        //DELETE FROM Users   WHERE id = 'id' ;
        boolean result=false;
        try {
            if(connect==null){

                connect=getConnection();

            }
            String sql="DELETE FROM Catalogs   WHERE id = ?";
            PreparedStatement preparedStatement=connect.prepareStatement(sql);

            preparedStatement.setInt(1,id);

            result=preparedStatement.execute();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean update(Catalog entity) {
        //UPDATE Users   SET  login = 'login', password = 'password', tel = 'tel' WHERE id = 'id';
        boolean result=false;
        try {
            if(connect==null){

                connect=getConnection();

            }
            String sql="UPDATE Catalogs SET  name = ?, producer = ?, price = ?, count=?  WHERE id = ?";
            PreparedStatement preparedStatement=connect.prepareStatement(sql);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getProducer());
            preparedStatement.setInt(3,entity.getPrice());
            preparedStatement.setInt(4,entity.getCount());
            preparedStatement.setInt(5,1);

            result=preparedStatement.execute();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    @Override
    public ArrayList<Catalog> getAll() {
        //SELECT *  FROM Catalogs ;
        ArrayList<Catalog> result=new ArrayList<>();
        try {
            if(connect==null){

                connect=getConnection();

            }
            String sql="SELECT *  FROM Catalogs ";
            Statement statement=connect.createStatement();



            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                int id=resultSet.getInt(1);
                String name=resultSet.getString(2);
                String producer=resultSet.getString(3);
                int price=resultSet.getInt(4);
                int count=resultSet.getInt(5);
                Catalog tempCatalog=new Catalog(name,producer,price,count);
                tempCatalog.setId(id);
                result.add(tempCatalog);
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        Catalog user=new Catalog("Iphone","Apple",2224,3334);
//       user.setId(5);
        DAO_Catalog dao=new DAO_Catalog();
        //user.password="56789";
        //dao.update(user);
        // System.out.println(dao.findAll());
        dao.insert(user);
        user.setCount(34);

        dao.update(user);
        System.out.println("Ready");
        ArrayList<Catalog> cts = dao.getAll();
        System.out.println(cts);

        // user.login="1111111";

        // dao.delete(7);

//        List<User> list=dao.findAll();
//        System.out.println(list);
//        User temp=list.get(0);
//        List<User> temp2=dao.find(temp);
//        temp2.get(0).login="7777777777";
//        dao.update(temp2.get(0));
//        System.out.println(dao.findAll());
    }



}