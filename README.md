# Ecommerce-OOP-Project

## Features

### Product Management
- Define products with a **name**, **price**, and **quantity**.
- Some products **expire** (e.g., *Cheese*, *Biscuits*), while others do not (*TV*, *Mobile*).
- Some products **require shipping** (e.g., *Cheese*, *TV*), while others do not (*Mobile scratch cards*).
- Every **shippable item** must provide its **weight**.

### Cart & Checkout
- Customers can **add products to the cart** with a specific quantity, **not exceeding available stock**.
- Customers can **proceed to checkout** with items in their cart.
- Upon checkout, the system prints the following details to the console:
  - **Order subtotal** (sum of all items’ prices).
  - **Shipping fees** (if applicable).
  - **Total paid amount** (*subtotal + shipping fees*).
  - **Customer's updated balance** after payment.

### Error Handling
- Checkout is **not allowed if**:
  - The **cart is empty**.
  - The **customer's balance is insufficient**.
  - One or more **products are out of stock** or **expired**.

---

## Sample Outputs

### ❌ Insufficient Balance  
The customer does not have enough money to complete the purchase.  
![Insufficient Balance](https://github.com/user-attachments/assets/76583477-1c7e-44b6-b149-2b96808e3746)

### ❌ Expired Product & Empty Cart  
Attempting to purchase an expired product or proceeding with an empty cart.  
![Expired Product & Empty Cart](https://github.com/user-attachments/assets/f5ba9509-441c-43a1-a747-552067e59e72)

### ✅ Successful Purchase  
A normal buying process where everything is valid.  
![Successful Purchase](https://github.com/user-attachments/assets/457fc566-b37b-4a65-b144-f519ac8b797d)

### ❌ Insufficient Stock & Empty Cart  
Trying to buy more than the available stock or proceeding with an empty cart.  
![Insufficient Stock & Empty Cart](https://github.com/user-attachments/assets/69467dc6-1445-4514-8d9f-8e13c63154d7)
