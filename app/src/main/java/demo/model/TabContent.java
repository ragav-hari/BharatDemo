package demo.model;

import java.util.List;

/**
 * Created by ragavendran on 23-06-2015.
 */
public class TabContent
{
    private Product product;
    private List<Brand> brandList;

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
