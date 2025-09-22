package com.example.khatabackend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000") // allow React
public class ProductController {

    @Autowired
    private ProductService productService;

    // Base URL for serving images
    private static final String BASE_IMAGE_URL = "http://localhost:8084/uploads/";

    @GetMapping
    public List<Product> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        // ✅ Build full image URL only if DB has filename
        products.forEach(p -> {
            if (p.getImage() != null && !p.getImage().startsWith("http")) {
                p.setImage(BASE_IMAGE_URL + p.getImage());
            }
        });

        return products;
    }

    // ✅ Save product with image
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Product addProduct(
            @RequestPart("product") Product product,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {

        if (imageFile != null && !imageFile.isEmpty()) {
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = imageFile.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, imageFile.getBytes());

            // ✅ Save only filename in DB
            product.setImage(fileName);
        }

        return productService.addProduct(product);
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Product updateProduct(
            @PathVariable Long id,
            @RequestPart("product") Product updated,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {

        if (imageFile != null && !imageFile.isEmpty()) {
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = imageFile.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, imageFile.getBytes());

            // ✅ Save only filename in DB
            updated.setImage(fileName);
        }

        Product saved = productService.updateProduct(id, updated);

        // ✅ Ensure response has full URL
        if (saved.getImage() != null && !saved.getImage().startsWith("http")) {
            saved.setImage(BASE_IMAGE_URL + saved.getImage());
        }

        return saved;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
