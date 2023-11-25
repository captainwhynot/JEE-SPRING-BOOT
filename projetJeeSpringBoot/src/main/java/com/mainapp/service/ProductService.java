package com.mainapp.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mainapp.entity.Product;
import com.mainapp.entity.Basket;
import com.mainapp.repository.ProductRepository;

/**
 * Service class for managing Product entities.
 * Handles interactions between the application and the ProductRepository.
 */
@Service
public class ProductService {

	private ProductRepository pr;
	private BasketService bs;

    /**
     * Sets the ProductRepository and BasketService dependencies for the service.
     *
     * @param pr The ProductRepository to be injected.
     * @param bs The BasketService to be injected.
     */
	@Autowired
	public void setDependencies(ProductRepository pr, BasketService bs) {
		this.pr = pr;
		this.bs = bs;
	}

    /**
     * Retrieves the ProductRepository associated with this service.
     *
     * @return The ProductRepository associated with this service.
     */
	public ProductRepository getPr() {
		return pr;
	}

    /**
     * Adds a new Product to the system.
     *
     * @param product The Product object to be added.
     * @return True if the addition is successful, false otherwise.
     */
	public boolean addProduct(Product product) {
		try {
			pr.save(product);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

    /**
     * Modifies the details of an existing Product.
     *
     * @param product The Product object to be modified.
     * @param name    The new name for the product.
     * @param price   The new price for the product.
     * @param stock   The new stock quantity for the product.
     * @return True if the modification is successful, false otherwise.
     */
	public boolean modifyProduct(Product product, String name, double price, int stock) {
		if (stock < 0) return false;
		try {
			int update = pr.modifyProduct(product, name, price, stock);
			return (update > 0);
		} catch (Exception e) {
			return false;
		}
	}

    /**
     * Updates the image of a Product.
     *
     * @param product   The Product object for which the image is to be updated.
     * @param imgFile   The MultipartFile representing the new image file.
     * @param fileName  The desired file name for the image.
     * @param savePath  The path to save the image file.
     * @return True if the update is successful, false otherwise.
     */
	public boolean updateProductImg(Product product, MultipartFile imgFile, String fileName, String savePath) {
		try {
			int productId = product.getId();

	        File imgDir = new File(savePath);
	        File[] files = imgDir.listFiles((dir, name) -> name.startsWith(productId + "_"));
	        
	        // Delete all the old image of the product
	        if (files != null) {
	            for (File file : files) {
	                file.delete();
	            }
	        }
	        
			// Create product folder if it does not exist
			File saveDir = new File(savePath);
	        if (!saveDir.exists()) {
	            saveDir.mkdirs();
	        }

	        // Save the image in the folder
	        if (fileName.equals("")) fileName = product.getId() + "_" + imgFile.getOriginalFilename();
	        else fileName = product.getId() + "_" + fileName;
			Path imgFilePath = Paths.get(savePath).resolve(fileName);
			imgFile.transferTo(imgFilePath.toFile());
	        
			fileName = "img/Product/" + fileName;
			int update = pr.updateProductImg(productId, fileName);
			return (update > 0);	
		} catch (Exception e) {
	        return false;
		} 
	}

    /**
     * Retrieves a list of all Product entities.
     *
     * @return A list of all Product entities.
     */
	public List<Product> getProductList() {
		return pr.findAll();
	}

    /**
     * Retrieves a list of Product entities based on a search string.
     *
     * @param search The search string for finding products.
     * @return A list of Product entities matching the search criteria.
     */
	public List<Product> getProductByName(String search) {
		return pr.getProductByName(search);
	}

    /**
     * Retrieves a list of Product entities associated with a seller (User ID).
     *
     * @param id The ID of the seller (User ID).
     * @return A list of Product entities associated with the seller.
     */
	public List<Product> getSellerProducts(int id) {
		return pr.getSellerProducts(id);
	}

    /**
     * Retrieves a Product entity by its ID.
     *
     * @param id The ID of the Product.
     * @return The Product object associated with the given ID.
     */
	public Product getProduct(int id) {
		return pr.getProduct(id);
	}

    /**
     * Deletes a Product along with its associated Baskets and image files.
     *
     * @param id       The ID of the Product to be deleted.
     * @param savePath The path to save associated files during the deletion.
     * @return True if the deletion is successful, false otherwise.
     */
	public boolean deleteProduct(int id, String savePath) {
		try {
            List<Basket> baskets = bs.getBasketListProduct(id);
            
	        File imgDir = new File(savePath);
	        File[] files = imgDir.listFiles((dir, name) -> name.startsWith(id + "_"));
	        
	        // Delete all the old image of the product
	        if (files != null) {
	            for (File file : files) {
	                file.delete();
	            }
	        }
            // Delete the product and the product's reference in all basket using it.
	        for (Basket basket : baskets) {
	            bs.deleteOrder(basket.getId());
	        }
	        pr.deleteProduct(id);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
}
