package com.ImageProjects.ImageDemo01.Controller;

import com.ImageProjects.ImageDemo01.DTO.ProductDTO;
import com.ImageProjects.ImageDemo01.Entity.Product;
import com.ImageProjects.ImageDemo01.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("app/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/newProduct")
    public ResponseEntity<String> addNewProduct(@RequestParam("productName") String productName,
                                                @RequestParam("productCategory") String productCategory,
                                                @RequestParam("productPrice") Integer productPrice,
                                                @RequestParam("productImage") MultipartFile imageFile)
    {
        try{
            Product product = new Product();
            product.setProductName(productName);
            product.setProductCategory(productCategory);
            product.setProductPrice(productPrice);
            product.setData(imageFile.getBytes());
            productRepository.save(product);
            return ResponseEntity.ok("Image uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

//    @GetMapping("/all")
//    public List<ImageDTO> listImages() {
//        return imageRepository.findAll().stream()
//                .map(img -> new ImageDTO(img.getId(), img.getName()))
//                .collect(Collectors.toList());
//    }

    @GetMapping("/allProducts")
    public List<ProductDTO> allProducts(){
        return productRepository.findAll().stream()
                .map(prod -> new ProductDTO(prod.getProductId(), prod.getProductName(),prod.getProductCategory(),prod.getProductPrice()))
                .collect(Collectors.toList());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
//        return imageRepository.findById(id)
//                .map(img -> ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + img.getName() + "\"")
//                        .contentType(MediaType.IMAGE_JPEG)
//                        .body(img.getData()))
//                .orElse(ResponseEntity.notFound().build());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id){
        return productRepository.findById(id)
                .map(img ->ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + img.getProductName() + "\"")
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(img.getData()))
                        .orElse(ResponseEntity.notFound().build());
    }
}
