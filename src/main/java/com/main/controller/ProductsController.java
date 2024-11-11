package com.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.entity.Products;
import com.main.services.ProductsServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api")
@Tag(name = "Products Information", description = "This is used for update, edit, delete and add products.")
public class ProductsController {
	
	@Autowired
	ProductsServices proudServices;
	
//	@GetMapping("/demo")
//	public void DemoFun() {
//		System.out.println("This is For Demo Controller");
//	}
	
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = Products.class)) }),
		@ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
    @Operation(summary = "Add Product",description = "Add Product Information")
	@PostMapping("/products")
	public ResponseEntity<List<Products>> addProducts(@RequestBody Products products) {
		List<Products> addedproducts = null;
		try {
			addedproducts = proudServices.addProducts(products);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return ResponseEntity.status(200).body(addedproducts);
//		return null;
	}
	
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = Products.class)) }),
		@ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
    @Operation(summary = "Update Product",description = "Update Product Information")
	@PutMapping("/product/update")
	public ResponseEntity<List<Products>> updateProducts(@RequestBody Products products) {
		List<Products> updatedproducts = proudServices.updatedProducts(products);
		return ResponseEntity.status(200).body(updatedproducts);
	}
	
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = Products.class)) }),
		@ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
    @Operation(summary = "All Product",description = "Show All Product Information")
	@GetMapping("/products")
	public ResponseEntity<List<Products>> getAllProducts() {
		List<Products> allproducts = proudServices.getAllProducts();
		return ResponseEntity.status(200).body(allproducts);
	}
	
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = Products.class)) }),
		@ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
    @Operation(summary = "Product Find Using ID",description = "Show Product Information")
	@GetMapping("/products/{product_id}")
	public ResponseEntity<Products> getProductFindById(@PathVariable int product_id){
		Products productsInfo = proudServices.getProductFindById(product_id);
		return ResponseEntity.status(200).body(productsInfo);
	}
	
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = Products.class)) }),
		@ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })
    @Operation(summary = "Remove Product",description = "Remove Product Information")
	@DeleteMapping("/products/{product_id}")
	public String removeProduct(@PathVariable int product_id) {
		return proudServices.removeProduct(product_id);
	}
}
