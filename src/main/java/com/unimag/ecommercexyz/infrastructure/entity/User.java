package com.unimag.ecommercexyz.infrastructure.entity;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDbBean
public class User {
    private String pk;
    private String name;
    private String email;
    private List<String> paymentMethods;
    private List<String> shippingAddresses;

    @DynamoDbPartitionKey
    public String getPk() {
        return pk;
    }
    public void setPk(String pk) {
        this.pk = pk;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<String> getPaymentMethods() {
        return paymentMethods;
    }
    public void setPaymentMethods(List<String> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
    public List<String> getShippingAddresses() {
        return shippingAddresses;
    }
    public void setShippingAddresses(List<String> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    
}
