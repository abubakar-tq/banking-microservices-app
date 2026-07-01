package com.abubakar.customerservice.web;

import com.abubakar.customerservice.dto.CustomerExistResponseDTO;
import com.abubakar.customerservice.dto.CustomerResponseDTO;
import com.abubakar.customerservice.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Internal (service-to-service) endpoints. Mapped under /intern, which SecurityConfiguration
 * permits without a JWT so other services (e.g. account-service via Feign) can call it directly.
 * Do NOT expose customer PII here beyond what callers strictly need.
 */
@RestController
@RequestMapping("/intern")
public class InternRestController {

    private final CustomerService customerService;

    public InternRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Confirms a customer exists and returns the minimal data account-service needs
     * (id + email). Throws CustomerNotFoundException (→ 404) if there is no such customer,
     * which the caller treats as "customer does not exist".
     */
    @GetMapping("/verify/{id}")
    public CustomerExistResponseDTO verifyCustomer(@PathVariable String id) {
        CustomerResponseDTO customer = customerService.getCustomerById(id);
        return new CustomerExistResponseDTO(customer.getId(), customer.getEmail());
    }
}
