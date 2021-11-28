package objects;

public class Game {
    private String name;
    private ProductType productType;
    private PriceCharting priceCharting;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public PriceCharting getPriceCharting() {
        return priceCharting;
    }

    public void setPriceCharting(PriceCharting priceCharting) {
        this.priceCharting = priceCharting;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
