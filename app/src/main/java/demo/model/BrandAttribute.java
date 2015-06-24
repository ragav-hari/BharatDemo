package demo.model;

/**
 * Created by ragavendran on 23-06-2015.
 */
public class BrandAttribute
{
    private String weight;
    private String price;
    private int brand_image;

    public int getBrand_image() {
        return brand_image;
    }

    public void setBrand_image(int brand_image) {
        this.brand_image = brand_image;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
