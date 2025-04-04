import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class Product{
    private final String name;
    private final int price;
    private int quantity;

    public Product(String name, int price, int quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public int getPrice(){
        return this.price;
    }

    public void ModifyQty(int quantity){
        this.quantity -= quantity;
    }

    public String getName(){
        return this.name;
    }

    public boolean isExpired(){
        return false;
    }

    public double getWeight(){
        return 0;
    }
}

class Shipable extends Product{
    private final int weight;

    public Shipable(String name, int price, int quantity, int weight){
        super(name, price, quantity);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }
}

class Expirable extends Product{
    private final Date date;

    public Expirable(String name, int price, int quantity, Date date) {
        super(name, price, quantity);
        this.date = date;
    }

    @Override
    public boolean isExpired(){
        Date now = new Date(2025, Calendar.APRIL, 4);
        return now.after(date);
    }
}

class ShipableExpirable extends Expirable{
    private final int weight;

    public ShipableExpirable(String name, int price, int quantity, int weight, Date date) {
        super(name, price, quantity, date);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }


}

class CartItem{
    Product product;
    int quantity;

    public CartItem(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }
}

class Cart {
    List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product, int quantity){
        if(product.isExpired()){
            System.out.println("This product is expired");
        }
        else if(product.getQuantity() < quantity){
            System.out.println("The product's quantity is insufficient");
        }
        else if(!product.isExpired() && product.getQuantity() >= quantity){
            boolean found = false;
            for(CartItem p : items){
                if(p.product.getName().equals(product.getName())){
                    p.quantity += quantity;
                    found = true;
                    break;
                }
            }
            if(!found){
                CartItem NewItem = new CartItem(product, quantity);
                this.items.add(NewItem);
            }
            product.ModifyQty(quantity);
        }
    }
}

class Customer {
    double balance;
    Cart cart;

    public Customer(int balance) {
        this.balance = balance;
        this.cart = new Cart();
    }

    public void buy(Product product, int quantity){
        this.cart.addProduct(product, quantity);
    }

    public void CheckOut(){
        if(cart.items.isEmpty()){
            System.out.println("The cart is empty!");
            return;
        }

        List<CartItem> Shipments = new ArrayList<>();
        double Subtotal = 0;
        for(CartItem p : this.cart.items){
            if(p.product instanceof Shipable || p.product instanceof ShipableExpirable){
                Shipments.add(p);
            }
        }
        double totalShipment = ShippingService.CalculateShipment(Shipments);
        System.out.println("** Checkout Receipt **");
        for(CartItem p : this.cart.items){
            System.out.println(p.quantity + "x " + p.product.getName() + " : " + p.product.getPrice() * p.quantity + '$');
            Subtotal += p.quantity * p.product.getPrice();
        }
        double totalAmount = Subtotal + totalShipment;
        System.out.println();
        System.out.println("Subtotal: " + Subtotal + "$\n" + "Shipping: " + totalShipment + "$\n" + "Amount: " + totalAmount + '$');


        if(balance < totalAmount){
            System.out.println("Customer's balance is insufficient\n");
            for(CartItem p : this.cart.items){
                p.product.ModifyQty(-p.quantity);
            }
            this.cart = new Cart();
        } else {
            this.balance -= totalAmount;
            System.out.println("Customer's current balance: " + (this.balance) + "$\n");
            this.cart = new Cart();
        }
    }
}

class ShippingService {
    public static double CalculateShipment(List<CartItem> products){
        double totalShipment = 0;
        double totalWeight = 0;
        System.out.println("\n** Shipment Notice **");
        for(CartItem p : products){
            System.out.println(p.quantity + "x " + p.product.getName() + " : " + p.product.getWeight() + 'g');
            totalShipment += p.quantity * p.product.getWeight() * 0.1;
            totalWeight += p.quantity * p.product.getWeight();
        }
        System.out.println("Total package weight " + totalWeight/1000 + "kg\n");
        return totalShipment;
    }
}

public class Main {
    public static void main(String[] args) {
        Product Cheese = new ShipableExpirable("Cheese", 4, 10, 300, new Date(2025, Calendar.APRIL, 1));
        Product Biscuits = new ShipableExpirable("Biscuits", 4, 10, 200, new Date(2025, Calendar.APRIL, 10));
        Product TV = new Shipable("TV", 10, 4, 2000);
        Product ScratchCard = new Product("Scratch Card", 2, 150);

        // No Money
        Customer mazen = new Customer(1);
        mazen.buy(Biscuits, 1);
        mazen.CheckOut();
        System.out.println("-----------------------------------");

        // Expired product and empty
        mazen.buy(Cheese, 1);
        mazen.CheckOut();
        System.out.println("-----------------------------------");

        // Normal buy
        Customer nada = new Customer(1000);
        nada.buy(Biscuits, 3);
        nada.buy(Biscuits, 5);
        nada.CheckOut();
        System.out.println("-----------------------------------");

        // No stock and empty cart
        nada.buy(Biscuits, 1000);
        nada.CheckOut();
    }
}