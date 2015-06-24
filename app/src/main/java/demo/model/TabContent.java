package demo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ragavendran on 23-06-2015.
 */
public class TabContent
{
    private Type type;
    private ArrayList<Brand> brandList;

    public ArrayList<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(ArrayList<Brand> brandList) {
        this.brandList = brandList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
