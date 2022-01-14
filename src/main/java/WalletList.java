
public class WalletList {
    private Wallet[] list;
    private int emptyHomeIndex;

    public WalletList() {
        list = new Wallet[1000];
        emptyHomeIndex = 0;
    }

    public void add(Wallet value) {
        list[emptyHomeIndex] = value;
        emptyHomeIndex++;
    }

    public Wallet get(int index) {
        return list[index];
    }


    public Boolean isEmpty() {
        return emptyHomeIndex == 0;
    }

    public int size() {
        return emptyHomeIndex;
    }

    public void add(int index, Wallet value) {
        // Check: Index invalid
        for (int i = emptyHomeIndex; i > index ; i--) {
            list[i] = list[i - 1];
        }
        list[index] = value;
        emptyHomeIndex++;
    }

    public void addAll(Wallet[] values) {
        for (Wallet v: values) {
            add(v);
        }
    }

    public void showList() {
        for (int i = 0; i < emptyHomeIndex; i++) {
            if (list[i] != null){
                System.out.print(list[i]);
                System.out.print(", ");
            }
            else {
                System.out.print("null");
                System.out.print(", ");
            }
        }
    }
    public void remove(int index){
        list[index] = null;
    }
    public void clear(){
        for (int i = 0; i < emptyHomeIndex; i++) {
            remove(i);
        }
    }
    public Boolean contains(Wallet number){
        boolean isContains = false;
        for (int i = 0; i < emptyHomeIndex; i++) {
            if (list[i] != null){
                if (list[i] == number) {
                    isContains = true;
                    break;
                }
            }
            else
                continue;
        }
        return isContains;
    }
}
