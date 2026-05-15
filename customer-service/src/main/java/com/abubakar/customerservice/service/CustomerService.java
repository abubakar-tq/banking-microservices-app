package com.abubakar.customerservice.service;

import com.abubakar.customerservice.dto.CustomerPageResponseDTO;
import com.abubakar.customerservice.dto.CustomerRequestDTO;
import com.abubakar.customerservice.dto.CustomerResponseDTO;

public interface CustomerService {

    CustomerResponseDTO getCustomerById(String id);
    CustomerResponseDTO getCustomerByCin(String cin);
    CustomerPageResponseDTO getAllCustomers(int page, int size);
    CustomerPageResponseDTO searchCustomers(String keyword, int page, int size);
    CustomerResponseDTO createCustomer(CustomerRequestDTO dto);
    CustomerResponseDTO updateCustomer(String id, CustomerRequestDTO dto);
    void deleteCustomerById(String id);
}
