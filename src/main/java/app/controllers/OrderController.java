package app.controllers;

import app.entities.Order;
import app.entities.Product;
import app.entities.ProductOrder;
import app.entities.User;
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

    @GetMapping("/orders/setConfirmed")
    public @ResponseBody Order setConfirmed(@RequestParam int orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setState("Confirmed");
        orderRepository.save(order);
        return order;
    }

    @GetMapping("/orders/setDelivered")
    public @ResponseBody Order setDelivered(@RequestParam int orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setState("Delivered");
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
        for (Order order : orderRepository.findAll()) {
            if (order.getCustomer().getId().equals(customerId)) {
                orderHistory.add(order);
            }
        }
        return orderHistory;
    }

    @GetMapping("/checkout")
    public String getCheckout() {
        return "checkout.html";
    }

    @PostMapping("/checkout")
    public @ResponseBody String postCheckout(@RequestBody List<String> orderInfo, @RequestParam Long customerId) {
        String address = orderInfo.get(0);
        int orderTotal = 0;
        List<Product> products = new ArrayList<Product>();
        List<Integer> quantities = new ArrayList<Integer>();
        for (String str : orderInfo) {
            String productId = str.split("_")[0];
            if (productRepository.findById(productId).isPresent()) {
                Product product = productRepository.findById(productId).get();
                quantities.add(Integer.parseInt(str.split("_")[1]));
                products.add(product);
                orderTotal += product.getPrice();
            }
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String dateOrdered = dtf.format(now);
        User customer = userRepository.findById(customerId).get();
        Order order = new Order(customer, address, "New", dateOrdered, orderTotal);
        orderRepository.save(order);
        for (int i = 0; i < products.size(); i++) {
            ProductOrder productOrder = new ProductOrder(products.get(i), order, quantities.get(i));
            productOrderRepository.save(productOrder);
        }

        return "Success";
    }
}
