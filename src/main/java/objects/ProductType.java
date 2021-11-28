package objects;

public enum ProductType {
    DEFAULT,
    NDS("ds"),
    N3DS("3ds"),
    PSP("psp"),
    PSVITA("vita"),
    SWITCH("switch");

    private String stringPattern = "";

    ProductType() {}
    ProductType(String stringPattern) {
        this.stringPattern = stringPattern;
    }

    public String getStringPattern() {
        return stringPattern;
    }
}
