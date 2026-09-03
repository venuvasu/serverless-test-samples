package com.amazon.aws.sample.dao;

import com.amazon.aws.sample.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CustomerDao {

    private DynamoDbTable<Customer> customerTable;

    @Autowired
    public CustomerDao(DynamoDbTable<Customer> customerTable) {
        this.customerTable = customerTable;
    }

    /**
     * @param id
     * @return customer details based on customer-id
     */
    public Customer getCustomer(String id) {
        return customerTable.getItem(Key.builder().partitionValue(id).build());
    }

    /**
     * @param id
     * delete customer details based on customer-id
     */
    public void deleteCustomer(String id) {
        customerTable.deleteItem(Key.builder().partitionValue(id).build());
    }

    /**
     * @return details of all customers
     */
    public List<Customer> getAllCustomers() {
        return customerTable.scan().items().stream().collect(Collectors.toList());
    }

    /**
     * @param customer
     * @return save customer details
     */
    public Customer save(Customer customer) {
        if (customer.getCustomerId() == null) {
            customer.setCustomerId(UUID.randomUUID().toString());
        }
        customerTable.putItem(customer);
        return customer;
    }
}
