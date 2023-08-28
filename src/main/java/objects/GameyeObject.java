package objects;

import com.opencsv.bean.CsvBindByName;

public class GameyeObject {

    @CsvBindByName(column = "Platform")
    private String platform;
    @CsvBindByName(column = "Type")
    private String type;
    @CsvBindByName(column = "ForSale")
    private String forSale;
    @CsvBindByName(column = "Wishlist")
    private String wishlist;
    @CsvBindByName(column = "Title")
    private String title;
    @CsvBindByName(column = "Country")
    private String country;
    @CsvBindByName(column = "Publisher")
    private String publisher;
    @CsvBindByName(column = "Developer")
    private String developer;
    @CsvBindByName(column = "DateAdded")
    private String dateAdded;
    @CsvBindByName(column = "Ownership")
    private String ownership;
    @CsvBindByName(column = "PriceLoose")
    private String priceLoose;
    @CsvBindByName(column = "PriceCIB")
    private String priceCIB;
    @CsvBindByName(column = "PriceNew")
    private String priceNew;
    @CsvBindByName(column = "YourPrice")
    private String yourPrice;
    @CsvBindByName(column = "PricePaid")
    private String pricePaid;
    @CsvBindByName(column = "ItemCondition")
    private String itemCondition;
    @CsvBindByName(column = "BoxCondition")
    private String boxCondition;
    @CsvBindByName(column = "ManualCondition")
    private String manualCondition;
    @CsvBindByName(column = "Beat")
    private String beat;
    @CsvBindByName(column = "PlayedCompletion")
    private String playedCompletion;
    @CsvBindByName(column = "Notes")
    private String notes;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getForSale() {
        return forSale;
    }

    public void setForSale(String forSale) {
        this.forSale = forSale;
    }

    public String getWishlist() {
        return wishlist;
    }

    public void setWishlist(String wishlist) {
        this.wishlist = wishlist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getPriceLoose() {
        return priceLoose;
    }

    public void setPriceLoose(String priceLoose) {
        this.priceLoose = priceLoose;
    }

    public String getPriceCIB() {
        return priceCIB;
    }

    public void setPriceCIB(String priceCIB) {
        this.priceCIB = priceCIB;
    }

    public String getPriceNew() {
        return priceNew;
    }

    public void setPriceNew(String priceNew) {
        this.priceNew = priceNew;
    }

    public String getYourPrice() {
        return yourPrice;
    }

    public void setYourPrice(String yourPrice) {
        this.yourPrice = yourPrice;
    }

    public String getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(String pricePaid) {
        this.pricePaid = pricePaid;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }

    public String getBoxCondition() {
        return boxCondition;
    }

    public void setBoxCondition(String boxCondition) {
        this.boxCondition = boxCondition;
    }

    public String getManualCondition() {
        return manualCondition;
    }

    public void setManualCondition(String manualCondition) {
        this.manualCondition = manualCondition;
    }

    public String getBeat() {
        return beat;
    }

    public void setBeat(String beat) {
        this.beat = beat;
    }

    public String getPlayedCompletion() {
        return playedCompletion;
    }

    public void setPlayedCompletion(String playedCompletion) {
        this.playedCompletion = playedCompletion;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "GameyeObject{" +
                "platform='" + platform + '\'' +
                ", state='" + ownership + '\'' +
                ", title='" + title + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
