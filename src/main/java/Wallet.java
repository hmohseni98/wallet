public class Wallet {
    private Integer id;
    private Integer amount;

    public Wallet(Integer amount) {
        this.amount = amount;
    }

    public Wallet(Integer id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
