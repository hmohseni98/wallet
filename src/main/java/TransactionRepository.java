import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepository {
    Connection connection = MyConnection.connection;
    Map<String,List<Transaction> >cache = new HashMap<>();

    public TransactionRepository() throws SQLException {
        String createTable = "CREATE TABLE IF NOT EXISTS transaction ( " +
                "    id serial primary key," +
                "    wallet_id int," +
                "    amount int," +
                "    status transaction_status," +
                "    type transaction_type," +
                "    constraint fk_walled foreign key (wallet_id) references wallet(id)" +
                ");";
        PreparedStatement preparedStatement = connection.prepareStatement(createTable);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void insert(Transaction transaction) throws SQLException {
        String insert = "INSERT INTO transaction (wallet_id,amount,status,type) values (?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setInt(1, transaction.getWallet().getId());
        preparedStatement.setInt(2, transaction.getAmount());
        preparedStatement.setString(3, transaction.getStatus().name());
        preparedStatement.setString(4, transaction.getType().name());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void update(Transaction transaction) throws SQLException {
        String update = "UPDATE transaction " +
                "SET wallet_id = ?,amount = ?,status = ?,type = ? " +
                "WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setInt(1, transaction.getWallet().getId());
        preparedStatement.setInt(2, transaction.getAmount());
        preparedStatement.setString(3, transaction.getStatus().name());
        preparedStatement.setString(4, transaction.getType().name());
        preparedStatement.setInt(5, transaction.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void delete(Transaction transaction) throws SQLException {
        String delete = "DELETE FROM transaction " +
                "WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setInt(1, transaction.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public List<Transaction> findAll() throws SQLException {
        String findAll = "SELECT * FROM transaction";
        if (cache.containsKey(findAll))
            return cache.get(findAll);
        List<Transaction> transactionList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(findAll);
        ResultSet resultSet = preparedStatement.executeQuery();
        WalletRepository walletRepository = new WalletRepository();
        while (resultSet.next()) {
            transactionList.add(new Transaction(resultSet.getInt("id"),
                    walletRepository.findById(resultSet.getInt("wallet_id")), resultSet.getInt("amount"),
                    Status.valueOf(resultSet.getString("status")), Type.valueOf(resultSet.getString("type"))));
        }
        cache.put(findAll,transactionList);
        return transactionList;
    }

    public Transaction findById(Integer id) throws SQLException {
        List<Transaction> transactionList = new ArrayList<>(1);
        String findById = "SELECT * FROM transaction " +
                "WHERE id = ?";
        String cacheKey = findById.replace("?",String.valueOf(id));
        if (cache.containsKey(cacheKey))
            return cache.get(cacheKey).get(0);
        PreparedStatement preparedStatement = connection.prepareStatement(findById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Transaction transaction = null;
        WalletRepository walletRepository = new WalletRepository();
        if (resultSet.next()) {
            transaction = new Transaction(resultSet.getInt("id"),
                    walletRepository.findById(resultSet.getInt("wallet_id")), resultSet.getInt("amount"),
                    Status.valueOf(resultSet.getString("status")), Type.valueOf(resultSet.getString("type")));
        }
        transactionList.add(transaction);
        cache.put(cacheKey,transactionList);
        return transaction;
    }
}
