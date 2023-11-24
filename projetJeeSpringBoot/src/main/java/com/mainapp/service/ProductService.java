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
	
	public boolean updateProductImg(Product product, MultipartFile imgFile, String fileName, String savePath) {
		try {
			int productId = product.getId();

	        File imgDir = new File(savePath);
	        File[] files = imgDir.listFiles((dir, name) -> name.startsWith(productId + "_"));
	        
	        //Delete all the old image of the product
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
	
	public List<Product> getProductList() {
		return pr.findAll();
	}
	
	public List<Product> getProductByName(String search) {
		return pr.getProductByName(search);
	}
	
	public List<Product> getSellerProducts(int id) {
		return pr.getSellerProducts(id);
	}
	
	public Product getProduct(int id) {
		return pr.getProduct(id);
	}
	
	public boolean deleteProduct(int id, String savePath) {
		try {
            List<Basket> baskets = bs.getBasketListProduct(id);
            
	        File imgDir = new File(savePath);
	        File[] files = imgDir.listFiles((dir, name) -> name.startsWith(id + "_"));
	        
	        //Delete all the old image of the product
	        if (files != null) {
	            for (File file : files) {
	                file.delete();
	            }
	        }
            //Delete the product and the product's reference in all basket using it.
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
