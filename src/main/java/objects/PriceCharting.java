package objects;

public class PriceCharting {
    private String url;
    private String completePrice;
    private String newPrice;

    public PriceCharting(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCompletePrice() {
        return completePrice;
    }

    public float getCompletePriceFloat(){
        try{
            return Float.parseFloat(getCompletePrice());
        }catch (Exception e){
            return 0;
        }
    }

    public void setCompletePrice(String completePrice) {
        this.completePrice = completePrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public float getNewPriceFloat(){
        try{
            return Float.parseFloat(getNewPrice());
        }catch (Exception e){
            return 0;
        }
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }
}
