public class Transaction {
    private Integer id;
    private Wallet wallet;
    private Integer amount;
    private Status status;
    private Type type;

    public Transaction(Integer id, Wallet wallet, Integer amount, Status status, Type type) {
        this.id = id;
        this.wallet = wallet;
        this.amount = amount;
        this.status = status;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", wallet=" + wallet +
                ", amount=" + amount +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
