import javax.xml.transform.Result;
import java.sql.*;
import java.util.*;

public class WalletRepository {
    private Connection connection = MyConnection.connection;


    Map<String, List<Wallet>> cache = new HashMap<>();

    public WalletRepository() throws SQLException {
        String createTable = "CREATE TABLE IF NOT EXISTS wallet(" +
                "    id serial primary key," +
                "    amount int" +
                ")";
        PreparedStatement preparedStatement = connection.prepareStatement(createTable);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public Integer insert(Wallet wallet) throws SQLException {
        String insert = "INSERT INTO wallet (amount) values (?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, wallet.getAmount());
        preparedStatement.executeUpdate();
        ResultSet generatedKey = preparedStatement.getGeneratedKeys();
        Integer id = null;
        if (generatedKey.next()) {
            id = generatedKey.getInt(1);
        }
        preparedStatement.close();
        return id;
    }

    public void update(Wallet wallet) throws SQLException {
        String update = "UPDATE wallet " +
                "SET amount = ? " +
                "WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setInt(1, wallet.getAmount());
        preparedStatement.setInt(2, wallet.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void delete(Wallet wallet) throws SQLException {
        String delete = "DELETE FROM wallet " +
                "WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setInt(1, wallet.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public List<Wallet> findAll() throws SQLException {
        String findAll = "SELECT * FROM wallet";
        if (cache.containsKey(findAll))
            return cache.get(findAll);
        PreparedStatement preparedStatement = connection.prepareStatement(findAll);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Wallet> walletList = new ArrayList<>();
        while (resultSet.next()) {
            walletList.add(new Wallet(resultSet.getInt("id"),
                    resultSet.getInt("amount")));
        }
        cache.put(findAll, walletList);
        return walletList;
    }

    public Wallet findById(Integer id) throws SQLException {
        String findById = "SELECT * FROM wallet " +
                "WHERE id = ?";
        String cacheKey=findById.replace("?",String.valueOf(id));
        if (cache.containsKey(cacheKey))
            return cache.get(cacheKey).get(0);
        PreparedStatement preparedStatement = connection.prepareStatement(findById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Wallet wallet = null;
        if (resultSet.next()) {
            wallet = new Wallet(resultSet.getInt("id"),
                    resultSet.getInt("amount"));
        }
        List <Wallet> walletList = new ArrayList<>(1);
        walletList.add(wallet);
        cache.put(cacheKey,walletList);
        return wallet;
    }
}
