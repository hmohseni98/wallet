import java.sql.SQLException;

public class TransactionService {
    private TransactionRepository transactionRepository = new TransactionRepository();
    private WalletService walletService = new WalletService();

    public TransactionService() throws SQLException {
    }
    public void withdraw(Integer amount,Integer wallet_id) throws SQLException {
        Wallet wallet;
        wallet = walletService.findById(wallet_id);
        if (wallet == null){
            System.out.println("wallet does not exist");
            return;
        }
        if(wallet.getAmount() < amount){
            System.out.println("not enough money!");
            return;
        }
        wallet.setAmount(wallet.getAmount() - amount);
        walletService.updateWallet(wallet);
        Transaction transaction = new Transaction(null,wallet,amount,Status.ACCEPTED,Type.WITHDRAW);
        transactionRepository.insert(transaction);
    }
    public void deposit(Integer wallet_id,Integer amount) throws SQLException{
        Wallet wallet = walletService.findById(wallet_id);
        if (wallet == null){
            System.out.println("wallet does not exist");
            return;
        }
        wallet.setAmount(wallet.getAmount() + amount);
        walletService.updateWallet(wallet);
        Transaction transaction = new Transaction(null,wallet,amount,Status.ACCEPTED,Type.DEPOSIT);
        transactionRepository.insert(transaction);
    }
}
