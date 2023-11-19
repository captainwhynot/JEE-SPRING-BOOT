package com.mainapp.service;
import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainapp.entity.Product;
import com.mainapp.entity.Basket;
import com.mainapp.repository.ProductRepository;

import jakarta.servlet.http.Part;

@Service
public class ProductService {

	private ProductRepository pr;
	private BasketService bs;

	@Autowired
	public void setDependencies(ProductRepository pr, BasketService bs) {
		this.pr = pr;
		this.bs = bs;
	}

	public ProductRepository getPr() {
		return pr;
	}

	public boolean addProduct(Product product) {
		try {
			pr.save(product);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean modifyProduct(Product product, String name, double price, int stock) {
		if (stock < 0) return false;
		try {
			int update = pr.modifyProduct(product, name, price, stock);
			return (update > 0);
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean updateProductImg(Product product, Part filePart, String fileName, String savePath) {
		try {
			int productId = product.getId();

	        File imgDir = new File(savePath);
	        File[] files = imgDir.listFiles((dir, name) -> name.startsWith(productId + "_"));
	        
	        //Delete all the old profile picture of the user
	        if (files != null) {
	            for (File file : files) {
	                file.delete();
	            }
	        }
			//Create product folder if it does not exist
			File saveDir = new File(savePath);
	        if (!saveDir.exists()) {
	            saveDir.mkdirs();
	        }

	        //Save the image in the folder
	        fileName = product.getId() + "_"+ fileName;
			String filePath = savePath + File.separator + fileName;
			filePart.write(filePath);
	        
			fileName = "img/Product/" + fileName;
			int update = pr.updateProductImg(productId, fileName);
			return (update > 0);	
		} catch (Exception e) {
	        return false;
		} 
	}
	
	public List<Product> getProductList() {
		return pr.findAll();
	}
	
	public Product getProduct(int id) {
		return pr.getProduct(id);
	}
	
	public boolean deleteProduct(int id) {
		try {
			Product product = this.getProduct(id);
            List<Basket> baskets = product.getBaskets();
            
            //Delete the product and the product's reference in all basket using it.
	        pr.delete(product);
	        for (Basket basket : baskets) {
	            bs.getBr().delete(basket);
	        }
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
}
