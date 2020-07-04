package com.qa.test.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

/*
 * This Class represents the page Object of MyStorePage
 */
public class MyStorePage extends CommonMethods {
	/*
	 * WebElements
	 */
	@FindBy(xpath = ".//*[contains(text(),'Popular')]")
	@CacheLookup
	private WebElement popularItemSection;

	@FindBy(xpath = ".//*[@class='right-block' ]//descendant::span[@itemprop='price']")
	@CacheLookup
	private List<WebElement> activePrices;

	@FindBy(xpath = ".//*[@class='right-block' ]//descendant::a[@title='Add to cart']")
	@CacheLookup
	private List<WebElement> addCartButtons;

	/*
	 * Discount
	 */

	@FindBy(xpath = ".//*[@class='right-block' ]//descendant::span[@class='old-price product-price']")
	@CacheLookup
	private List<WebElement> discountedPrices;

	@FindBy(xpath = ".//*[@class='right-block' ]//descendant::span[@class='old-price product-price']//parent::div/child::span[@itemprop='price']")
	@CacheLookup
	private List<WebElement> priceOfDiscountedProducts;

	@FindBy(xpath = ".//*[@class='right-block' ]//descendant::span[@class='old-price product-price']//parent::div//following-sibling::div[@class='button-container']/child::a[@title='Add to cart']")
	@CacheLookup
	private List<WebElement> addToCartButtonsOfDiscountedProducts;

	/*
	 * Product Spec
	 */
	@FindBy(xpath = ".//*[@class='left-block' ]//descendant::img")
	@CacheLookup
	private List<WebElement> productsCatalogue;

	@FindBy(xpath = ".//*[@class='right-block' ]//descendant::a[@class='product-name']")
	@CacheLookup
	private List<WebElement> productsDesc;

	/*
	 * Cart
	 */
	@FindBy(id = ".//*[@id='layer_cart' ]/child::div[1]")
	@CacheLookup
	private WebElement cartFrame;

	@FindBy(xpath = ".//*[@class='layer_cart_img img-responsive']")
	@CacheLookup
	private WebElement productInCart;

	@FindBy(id = "layer_cart_product_title")
	@CacheLookup
	private WebElement titleOfAddedProduct;

	@FindBy(id = "layer_cart_product_attributes")
	@CacheLookup
	private WebElement atrributeOfAddedProduct;

	@FindBy(id = "layer_cart_product_price")
	@CacheLookup
	private WebElement priceOfAddedProduct;

	@FindBy(id = "layer_cart_product_quantity")
	@CacheLookup
	private WebElement qauantityOfAddedProduct;

	SoftAssert Verify;
	Actions action;

	public MyStorePage(WebDriver driver) {
		super(driver);
	}

	public enum FindThe {
		LOWEST, HIGHEST;
	}
	/*
	 * Performing click on Popular Section
	 */

	public MyStorePage clickOnpopularItemSection() {
		clickOnElement(popularItemSection, "POPULAR ItemSection");
		return this;
	}

	/*
	 * Performing click on Add To Cart Using Actions Class
	 */
	private MyStorePage clickOnMouseHoverItems(WebElement target, WebElement clickableElement) {
		checkPageIsReady();
		try {
			action = new Actions(this.driver);
			action.moveToElement(target).moveToElement(clickableElement).clickAndHold(clickableElement)
					.click(clickableElement).build().perform();
			Thread.sleep(5000);
			Reporter.log("Successfully clicked on " + clickableElement.getText());
		} catch (Exception e) {
			Reporter.log("Unable to click due to " + e.getMessage());
		}
		return this;
	}
	/*
	 * Creating UserDefined SortingFunction Without Using Java Existing function
	 */

	private float findingThe(FindThe findThe, List<Float> inputList) {
		float getValue = 0;
		Float[] conArr = new Float[inputList.size()];
		for (int i = 0; i < inputList.size(); i++) {
			conArr[i] = inputList.get(i);
		}
		for (int i = 0; i < conArr.length; i++) {
			for (int j = 0; j < conArr.length - 1 - i; j++) {
				if (conArr[j] > conArr[j + 1]) {
					Float temp = conArr[j];
					conArr[j] = conArr[j + 1];
					conArr[j + 1] = temp;
				}
			}
		}
		switch (findThe) {
		case LOWEST:
			getValue = conArr[0];
			break;
		case HIGHEST:
			getValue = conArr[conArr.length - 1];
			break;
		default:
			break;
		}
		return getValue;
	}

	/*
	 * Getting the List of Price
	 */
	private List<Float> getPriceList(List<WebElement> pricelistElements) {
		List<Float> priceList = new ArrayList<>();
		for (int counter = 0; counter < pricelistElements.size() / 2; counter++) {
			priceList.add(Float.valueOf(activePrices.get(counter).getText().trim().replace("$", "")));
		}
		return priceList;
	}

	/*
	 * Verifying The Right Product Added Successfully
	 */

	private MyStorePage verifyRightProductAddedToTheCart(int i) {
		Verify = new SoftAssert();
		String featuredProductDesc = getText(productsDesc.get(i));
		String featuredProductPrice = getText(activePrices.get(i));
		clickOnMouseHoverItems(productsCatalogue.get(i), addCartButtons.get(i));
		String cartProductDesc = getText(titleOfAddedProduct);
		String cartProductPrice = getText(priceOfAddedProduct);
		String qauantityOfProduct = getText(qauantityOfAddedProduct);
		String atrributeOfProduct = getText(atrributeOfAddedProduct);
		takeScreenShotFor(featuredProductDesc);
		Verify.assertTrue(featuredProductDesc.equals(cartProductDesc), "Product description failed to match");
		Verify.assertTrue(featuredProductPrice.equals(cartProductPrice), "Product Price failed to match");
		Verify.assertTrue(qauantityOfProduct.equals("1"), "Product quantity failed to match");
		Verify.assertFalse(atrributeOfProduct.isEmpty(), "Product attribute failed to match");
		Verify.assertAll();
		Reporter.log("Right Product Has been added successfully", true);
		return this;
	}

	/*
	 * Getting the LowestPrice Using UserDefined Function
	 */
	private float getTheLowestPrice(int i, List<WebElement> elements) {
		return Float.valueOf(elements.get(i).getText().trim().replace("$", ""));
	}
	/*
	 * Verify that product is Successfully added.
	 */

	public MyStorePage verifyLowestValueProductSuccessFullyAdded() {
		checkPageIsReady();
		int iterations = activePrices.size() / 2;
		float lowPrice = findingThe(FindThe.LOWEST, getPriceList(activePrices));
		for (int counter = 0; counter < iterations; counter++) {
			if (getTheLowestPrice(counter, activePrices) == lowPrice)
				verifyRightProductAddedToTheCart(counter);
		}
		Reporter.log("Lowest Value Product SuccessFully Added", true);
		return this;
	}

	private MyStorePage verifyRightProductAddedToTheCartWithDiscount(int i) {
		Verify = new SoftAssert();
		String featuredProductPrice = getText(priceOfDiscountedProducts.get(i));
		clickOnMouseHoverItems(priceOfDiscountedProducts.get(i), addToCartButtonsOfDiscountedProducts.get(i));
		String cartProductPrice = getText(priceOfAddedProduct);
		String qauantityOfProduct = getText(qauantityOfAddedProduct);
		String atrributeOfProduct = getText(atrributeOfAddedProduct);
		Verify.assertTrue(featuredProductPrice.equals(cartProductPrice), "Product Price failed to match");
		Verify.assertTrue(qauantityOfProduct.equals("1"), "Product quantity failed to match");
		Verify.assertFalse(atrributeOfProduct.isEmpty(), "Product attribute failed to match");
		Verify.assertAll();
		Reporter.log("Right Product Has been added successfully", true);
		return this;
	}

	public MyStorePage verifyTheNumberOfItemsHavingDiscounts() {
		checkPageIsReady();
		if (discountedPrices.size() != 0) {
			int iterations = discountedPrices.size() / 2;
			for (int counter = 0; counter < iterations; counter++) {
				verifyRightProductAddedToTheCartWithDiscount(counter);
			}
		} else {
			Reporter.log("No Discount Found", true);
		}
		return this;
	}
}