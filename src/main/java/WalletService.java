import java.sql.SQLException;

public class WalletService {
    private WalletRepository walletRepository = new WalletRepository();

    public WalletService() throws SQLException {
    }

    public Integer createWallet() throws SQLException {
        Wallet wallet = new Wallet(10000);
        return walletRepository.insert(wallet);
    }
    public Integer showBalance(Integer id) throws SQLException{
        return findById(id).getAmount();
    }
    public Wallet findById (Integer id) throws SQLException{
        return walletRepository.findById(id);
    }
    public void updateWallet(Wallet wallet) throws SQLException {
        walletRepository.update(wallet);
    }
}
