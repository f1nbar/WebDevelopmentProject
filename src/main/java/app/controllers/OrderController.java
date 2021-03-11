package app.controllers;

import app.entities.Order;
import app.entities.Product;
import app.entities.ProductOrder;
import app.entities.User;
import app.keys.ProductOrderKey;
import app.repositories.OrderRepository;
import app.repositories.ProductOrderRepository;
import app.repositories.ProductRepository;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductOrderRepository productOrderRepository;

    @GetMapping("/orders")
    public @ResponseBody List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/orders/setShipped")
    public @ResponseBody Order setShipped(@RequestParam int orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setState("Shipped");
        orderRepository.save(order);
        return order;
    }

    @GetMapping("/orders/setCancelled")
    public @ResponseBody Order setCancelled(@RequestParam int orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setState("Cancelled");
        orderRepository.save(order);
        return order;
    }

    @GetMapping("/orderHistory")
    public @ResponseBody List<Order> getOrderHistory(@RequestParam Long customerId) {
        List<Order> orderHistory = new ArrayList<Order>();
        for(Order order:orderRepository.findAll()) {
            if (order.getCustomer().getId().equals(customerId)) {
                orderHistory.add(order);
            }
        }
        return orderHistory;
    }

    @PostMapping("/cart/add/")
    public @ResponseBody Order addToCart(@RequestParam Long customerId, @RequestParam String productId) {
        // Insert check for if user is logged in...
        User customer = userRepository.findById(customerId).get();
        Order cart = orderRepository.findById(customer.getCart().getOrderId()).get();
        Product product = productRepository.findById(productId).get();
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrder(cart);
        productOrder.setProduct(product);
        productOrderRepository.save(productOrder);
        return cart;
    }

    @PostMapping("/cart/remove/")
    public @ResponseBody String removeFromCart(@RequestParam Long customerId, @RequestParam String productId) {
    if (userRepository.findById(customerId).isPresent()) {
        // Insert check for if user is logged in...
        User customer = userRepository.findById(customerId).get();
            if (orderRepository.findById(customer.getCart().getOrderId()).isPresent()) {
                Order cart = orderRepository.findById(customer.getCart().getOrderId()).get();
                Product product = productRepository.findById(productId).get();
                productOrderRepository.deleteById(new ProductOrderKey(product.getProductId(), cart.getOrderId()));
            }
        }
        return productId;
    }

    @GetMapping("/checkout")
    public String getCheckout() {
        return "checkout.html";
    }

    @PostMapping("/checkout")
    public @ResponseBody Order postCheckout(@RequestParam int orderId, @RequestParam String address) {
        Order order = orderRepository.findById(orderId).get();
        User customer = order.getCustomer();
        // Move this order to the customers order history and create a new, empty cart
        if (customer != null) {
            Order newOrder = new Order();
            newOrder.setState("Cart");
            newOrder = orderRepository.save(newOrder);
            customer.setCart(newOrder);
        }
        // Set the date ordered, address, and state of the order
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        order.setDateOrdered(dtf.format(now));
        order.setAddress(address);
        order.setState("Confirmed");
        return orderRepository.save(order);
    }
}
