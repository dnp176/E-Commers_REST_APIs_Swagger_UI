package com.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.entity.Payments;
import com.main.services.PaymentsServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Payment Information", description = "This is used for check and update payments details.")
public class PaymentsController {
	
	@Autowired
	PaymentsServices paymentsServices;
	
	@Operation(summary = "Payment Process", description = "Enter Order Id and Select Status")
    @PostMapping("/payment")
	public ResponseEntity<String> checkoutOrder(
			@RequestParam(name = "orderId", required = true) @Schema(description = "Order ID", example = "10101") int orderId,
			@RequestParam(name = "paymentType", required = true) @Schema(allowableValues = { "ApplePay", "GPay",
					"DebitCard" }, description = "Select payment type") String paymentType,
			@RequestParam(name = "paymentStatus", required = true) @Schema(allowableValues = { "Completed", "Failed",
					"Cancelled" }, description = "Select payment status") String paymentStatus) {

		String paymentInfo = paymentsServices.updatePaymentStatus(paymentType, orderId, paymentStatus);
		return ResponseEntity.status(200).body(paymentInfo);
        
    }
	
	@Operation(summary = "All Payments", description = "Show all payments informations.")
	@GetMapping("/allpayments")
	public ResponseEntity<List<Payments>> getAllPaymentInformation(){
		List<Payments> allPayment =  paymentsServices.getAllPaymentInformation();
		return ResponseEntity.status(200).body(allPayment);
	}
}
