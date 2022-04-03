package application.model;

public class Customer {
    private String navn;
    private static int customerId;

    // Constructor
    public Customer(String navn){
        this.navn = navn;
        customerId ++;
    }

    // Metoder
    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getCustomerId(){
        return customerId;
    }

    // To string overrides for at kunne undgå at skrive memory location ud
    @Override
    public String toString(){
        return navn;
    }


}
