package com.gr25.thinkpro.controller.Admin;

import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.Image;
import com.gr25.thinkpro.domain.entity.Product;
import com.gr25.thinkpro.service.CategoryService;
import com.gr25.thinkpro.service.ImageService;
import com.gr25.thinkpro.service.ProductService;
import com.gr25.thinkpro.service.UploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductPageController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final UploadService uploadService;
    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.getProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/{Id}")
    public String detailProductPage(@PathVariable("Id") long Id, Model model) {
        Product product = this.productService.getProductById(Id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/create")
    public String getcreateProductPage(Model model) {
        Product product = new Product();

        List<Image> images = new ArrayList<>();

        List<Category> categories = this.categoryService.getCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("newProduct", product);
        model.addAttribute("images", images);
        return "admin/product/create";
    }


    @PostMapping("/admin/product/create")
    public String createProduct(Model model,
                                @ModelAttribute("newProduct") Product product,
                                @RequestParam("imageFiles") List<MultipartFile> imageFiles,
                                RedirectAttributes redirectAttributes) {
        log.info("Received product: {}", product);
        log.info("Received images: {}", imageFiles);

        Category categoryName = categoryService.getCategoryByName(product.getCategory().getName());
        List<Category> categories = this.categoryService.getCategories();
        model.addAttribute("categories", categories);

        boolean hasError = false; // Biến kiểm tra xem có lỗi hay không

        // Kiểm tra các lỗi và thêm vào RedirectAttributes
        if (product.getName() == null || product.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorName", "Tên sản phẩm không được để trống");
            hasError = true;
        }
        if (product.getDescription() == null || product.getDescription().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorDescription", "Mô tả không được để trống");
            hasError = true;
        }
        if (product.getPrice() <= 0) {
            redirectAttributes.addFlashAttribute("errorPrice", "Giá sản phẩm phải lớn hơn 0");
            hasError = true;
        }
        if (product.getQuantity() <= 0) {
            redirectAttributes.addFlashAttribute("errorQuantity", "Số lượng sản phẩm phải lớn hơn 0");
            hasError = true;
        }
        if (product.getDiscount() < 0 || product.getDiscount() >= 1) {
            redirectAttributes.addFlashAttribute("errorDiscount", "Giảm giá không hợp lệ");
            hasError = true;
        }

        // Kiểm tra các lỗi từ service, nếu có lỗi, thêm vào RedirectAttributes
        this.productService.rqProduct(product, redirectAttributes);

        // Kiểm tra số lượng ảnh, nếu không đủ 3 ảnh, thêm thông báo lỗi
        if (imageFiles.size() != 3) {
            redirectAttributes.addFlashAttribute("errorImage", "Vui lòng chọn đủ 3 ảnh cho sản phẩm");
            hasError = true;
        }

        // Nếu có lỗi, trả về trang tạo sản phẩm với thông báo lỗi
        if (hasError || redirectAttributes.containsAttribute("errorName") ||
                redirectAttributes.containsAttribute("errorDescription") ||
                redirectAttributes.containsAttribute("errorPrice") ||
                redirectAttributes.containsAttribute("errorQuantity") ||
                redirectAttributes.containsAttribute("errorDiscount") ||
                redirectAttributes.containsAttribute("errorImage")) {
            return "redirect:/admin/product/create"; // Trả về lại trang tạo sản phẩm với các thông báo lỗi
        }

        // Nếu không có lỗi, tiếp tục lưu sản phẩm
        product.setCreatedDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        product.setLastModifiedDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        product.setCategory(categoryName);

        // Lưu sản phẩm vào cơ sở dữ liệu
        productService.saveProduct(product);

        // Lưu hình ảnh (nếu có)
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                Image image = new Image();
                String url = this.uploadService.handleSaveUploadFile(file, "products");
                image.setUrl(url);
                image.setProduct(product);
                imageService.saveImage(image);
                images.add(image);
            }
        }

        // Cập nhật giá cuối cùng của sản phẩm
        product.setFinalPrice();
        product.setImages(images);
        productService.saveProduct(product); // Lưu lại sản phẩm với hình ảnh

        return "redirect:/admin/product"; // Redirect đến trang danh sách sản phẩm
    }


    @GetMapping("/admin/product/delete/{id}")
    public String getdeleteProductPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @Transactional
    @PostMapping("/admin/product/delete")
    public String postdeleteProductPage(Model model,@ModelAttribute("newProduct") Product product) {
        this.productService.deleteProduct(product.getProductId());
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Product currentProduct = this.productService.findProductById(id);
        model.addAttribute("newProduct",currentProduct);
        model.addAttribute("id", currentProduct.getProductId());
        List<Category> categories = this.categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "admin/product/update";
    }
    @PostMapping("/admin/product/update/{id}")
    public String handleUpdateProduct(Model model, @ModelAttribute("newProduct") @Valid Product pr, @PathVariable("id") long id,
                                      BindingResult newProductBindingResult,
                                      @RequestParam("imageFiles") List<MultipartFile> imageFiles) {

        Category categoryName = categoryService.getCategoryByName(pr.getCategory().getName());

        List<Category> categories = this.categoryService.getCategories();
        model.addAttribute("categories", categories);
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        List<Image> images=new ArrayList<>();

        Product currentProduct = this.productService.findProductById(id);
        if (currentProduct != null) {
            // update new image

            currentProduct.setName(pr.getName());
            currentProduct.setPrice(pr.getPrice());
            currentProduct.setQuantity(pr.getQuantity());
            currentProduct.setDiscount(pr.getDiscount());
            currentProduct.setDescription(pr.getDescription());
            currentProduct.setCategory(categoryName);
            for(Image tmp : currentProduct.getImages()) {
                imageService.deleteImage(tmp.getProduct().getProductId());
            }
            for (MultipartFile file : imageFiles) {
                if (!file.isEmpty()) {
                    Image image = new Image();
                    images.add(image);
                    String url = this.uploadService.handleSaveUploadFile(file, "products");
                    image.setUrl(url);
                    image.setProduct(currentProduct);
                    imageService.saveImage(image);
                }
            }
            currentProduct.setImages(images);
            this.productService.saveProduct(currentProduct);
        }

        return "redirect:/admin/product";
    }

}
