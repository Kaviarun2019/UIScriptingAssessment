package org.uiScriptingPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UiScriptingClassTest {

	// Web driver settings
	WebDriver driver = new ChromeDriver();

	@BeforeTest
	public void accessAmazonWebsite() {

		// Pass amazon url in browser
		driver.get("https://www.amazon.in/");

		// Window maximize
		driver.manage().window().maximize();

		// Store the page title
		String pageTitle = driver.getTitle();

		// Verify and Validate the page title
		if (pageTitle.equalsIgnoreCase("Amazon.in")) {
			System.out.println("Amazon page loaded");
		} else {
			System.out.println("Title: \t" + pageTitle);
		}

	}

	@Test(priority = 0)
	public void searchAnItem() throws InterruptedException {

		// Find search bar and search data
		driver.findElement(By.id("twotabsearchtextbox")).clear();
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("The vision of soul book");
		driver.findElement(By.id("nav-search-submit-button")).click();
		Thread.sleep(2000);
	}

	@Test(priority = 1)
	public void selectAnItemFromSearchResult() throws InterruptedException {

		// Verifying the item whether showing based on the search
		WebElement itemTitle = driver.findElement(By.partialLinkText("Soul"));
		Boolean itemTitleDisplay = itemTitle.isDisplayed();
		System.out.println("Items are Showing:\t" + itemTitleDisplay);

		// Verify the Price is available for Item
		Boolean priceAvailable = driver.findElement(By.className("a-price-whole")).isDisplayed();

		// Selecting the Item which has Search Name & Price value
		if (itemTitleDisplay) {

			if (priceAvailable) {
				itemTitle.click();
			}
		}

		// Tabs handling after selecting the item from search list
		java.util.Set<String> handles = driver.getWindowHandles();
		String winHandle1 = driver.getWindowHandle();
		handles.remove(winHandle1);

		String winHandle = handles.iterator().next();
		String winHandle2 = " ";
		if (winHandle != winHandle1) {
			winHandle2 = winHandle;

			driver.switchTo().window(winHandle2);
			System.out.println(winHandle2);
			Thread.sleep(2000);

		}
	}

	@Test(priority = 2)
	public float itemAddToCart() throws InterruptedException {

		// Verifying the selected item name
		WebElement selectedItem = driver.findElement(By.id("productTitle"));
		String selectedItemName = selectedItem.getText();
		System.out.println("Selected Item Name:\t" + selectedItemName);

		// Store the selected item Price value
		String selectedItemPrice = driver.findElement(By.cssSelector("#soldByThirdParty > span")).getText();
		System.out.println("Selected Item Price:\t" + selectedItemPrice);

		// Add Item to cart
		driver.findElement(By.name("submit.add-to-cart")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("hlb-view-cart-announce")).click();
		Thread.sleep(2000);

		// Verify the selected Item showing in cart
		WebElement itemInCart = driver.findElement(By.className("a-truncate-cut"));
		String nameoftheitemincart = itemInCart.getText();
		System.out.println("Cart Item Name:\t" + nameoftheitemincart);

		// Verify the Item price in cart
		String priceOfTheItemInCart = driver.findElement(By.cssSelector(
				"div.sc-list-item-content > div > div.a-column.a-span2.a-text-right.sc-item-right-col.a-span-last > p > span"))
				.getText();
		String priceOfTheItemInCart1 = priceOfTheItemInCart.replaceAll("\"", "");
		float priceOfTheItemInCart2 = Float.parseFloat(priceOfTheItemInCart1);
		System.out.println("Cart Item Price:\t" + priceOfTheItemInCart2);
		return priceOfTheItemInCart2;

	}

	@Test(priority = 3)
	public float increaseQuantityOfheItemTo10() throws InterruptedException {

		// Increase the quantity of item to 10
		Select quantity = new Select(driver.findElement(By.name("quantity")));
		quantity.selectByIndex(10);
		WebElement quantityValueSet = driver.findElement(By.xpath(
				"//input[@class='a-input-text a-width-small a-spacing-mini sc-quantity-textfield sc-update-quantity-input sc-hidden']"));
		quantityValueSet.sendKeys("10");
		driver.findElement(By.cssSelector(
				"div.sc-list-item-content > div > div.a-column.a-span10 > div > div > div.a-fixed-left-grid-col.a-col-right> div.a-row.sc-action-links > span.sc-action-quantity > span > span > span > span > a"))
				.click();
		Thread.sleep(2000);

		// Get the Price value as string
		WebElement subTotal = driver.findElement(By.cssSelector("#sc-subtotal-amount-activecart > span"));
		String subTotalString = subTotal.getText();
		String subTotalString1 = subTotalString.replaceAll(",", "");
		System.out.println("Sub Total String:\t" + subTotalString1);

		// Type casting the Price value
		float subTotalFloat = 0f;
		try {
			subTotalFloat = Float.parseFloat(subTotalString1);
			System.out.println("10 Items Value:\t" + subTotalFloat);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			System.out.println("Something Wrong");
		}

		return subTotalFloat;

	}

	@Test(priority = 4)
	public float changeQuantityOfTheItemTo9() throws InterruptedException {

		// Decrease the quantity of item to 9
		WebElement quantityValueSet1 = driver.findElement(By.name("quantityBox"));
		quantityValueSet1.click();
		quantityValueSet1.sendKeys(Keys.BACK_SPACE);
		quantityValueSet1.sendKeys(Keys.BACK_SPACE);
		Thread.sleep(2000);
		quantityValueSet1.sendKeys("9");
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(
				"div.sc-list-item-content > div > div.a-column.a-span10 > div > div > div.a-fixed-left-grid-col.a-col-right> div.a-row.sc-action-links > span.sc-action-quantity > span > span > span > span > a"))
				.click();
		Thread.sleep(2000);

		// Get the Price value as string
		WebElement subTotal1 = driver.findElement(By.cssSelector("#sc-subtotal-amount-activecart > span"));
		String subTotalString2 = subTotal1.getText();
		String subTotalString3 = subTotalString2.replaceAll(",", "");
		System.out.println("Sub Total String:\t" + subTotalString3);

		// Type casting the Price value
		float subTotalFloat1 = 0f;
		try {
			subTotalFloat1 = Float.parseFloat(subTotalString3);
			System.out.println("9 Items Value:\t" + subTotalFloat1);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			System.out.println("Something Wrong");
		}
		return subTotalFloat1;
	}

	@Test(priority = 5)
	public void pricecomparision() throws InterruptedException {

		// comparing the Price values after increase & decrease the quantity
		float x = this.itemAddToCart();
		float z = 10 * x;
		float y = this.increaseQuantityOfheItemTo10();
		float a = this.changeQuantityOfTheItemTo9();
		float b = 9 * x;
		boolean c = b == a;
		boolean m = z == y;
		if (m) {
			System.out.println("Sub Total showing the correct Price for 10 Items:\n" + "1 Item Value:\t" + x
					+ "\n10 Items Value:" + z + "\nSubTotal Value for 10 Items:" + y);
		} else {
			System.out.println("Sub Total Value not matching for 10 items:\n" + "1 Item Value:\t" + x
					+ "\n10 Items Value:" + z + "\nSubTotal Value for 10 Items:" + y);
		}

		if (c) {
			System.out.println("Sub Total showing the correct Price for 9 Items:\n" + "1 Item Value:\t" + x
					+ "\n9 Items Value:" + b + "\nSubTotal Value for 9 Items:" + a);
		} else {
			System.out.println("Sub Total value not matching for 9 Items:\n" + "1 Item Value:\t" + x
					+ "\n9 Items Value:" + b + "\nSubTotal Value for 9 Items:" + a);

		}

	}

	@Test(priority = 6)
	public void deleteItemFromCart() {

		// Remove the Item from cart
		driver.findElement(By.className("a-color-link")).click();
		System.out.println("Cart Empty");
	}

	@Test(priority = 7)

	// Close the Browser
	public void closeBrowser() {
		driver.quit();
	}

}
