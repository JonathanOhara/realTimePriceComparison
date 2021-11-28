package interfaces;

public interface Filter {
	boolean filter( String productName, String searchedName, String shopName, boolean ignoreMatchWords );
}
