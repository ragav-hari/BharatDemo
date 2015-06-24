package demo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ragavendran on 23-06-2015.
 */
public class Brand
{
    private int brand_id;
    private String brand_name;
    private ArrayList<BrandAttribute> brandAttributeList;

    public ArrayList<BrandAttribute> getBrandAttributeList() {
        return brandAttributeList;
    }

    public void setBrandAttributeList(ArrayList<BrandAttribute> brandAttributeList) {
        this.brandAttributeList = brandAttributeList;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }
}
