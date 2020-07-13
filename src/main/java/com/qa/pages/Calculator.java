package com.qa.pages;

import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.qa.basePack.DriverTech;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

public class Calculator extends DriverTech {
	public WindowsDriver<WindowsElement> windowSession;

	@WindowsFindBy(accessibility = "num0Button")
	private WindowsElement number0;

	@WindowsFindBy(accessibility = "num1Button")
	private WindowsElement number1;

	@WindowsFindBy(accessibility = "num2Button")
	private WindowsElement number2;

	@WindowsFindBy(accessibility = "num3Button")
	private WindowsElement number3;

	@WindowsFindBy(accessibility = "num4Button")
	private WindowsElement number4;

	@WindowsFindBy(accessibility = "num5Button")
	private WindowsElement number5;

	@WindowsFindBy(accessibility = "num6Button")
	private WindowsElement number6;

	@WindowsFindBy(accessibility = "num7Button")
	private WindowsElement number7;

	@WindowsFindBy(accessibility = "num8Button")
	private WindowsElement number8;

	@WindowsFindBy(accessibility = "num9Button")
	private WindowsElement number9;

	@WindowsFindBy(accessibility = "squareRootButton")
	private WindowsElement squareRoot;

	@WindowsFindBy(accessibility = "TextContainer")
	private WindowsElement resultPane;

	public Calculator(WindowsDriver<WindowsElement> windowSession) {
		this.windowSession = windowSession;
		PageFactory.initElements(new AppiumFieldDecorator(windowSession), this);
	}

	private Calculator pressNumber(int numb) {
		try {
			if (String.valueOf(numb).length() > 1) {
				System.err.println("Existing due to numb is more than one digit");
				System.exit(0);
			}
			switch (numb) {
			case 0:
				number0.click();
				break;
			case 1:
				number1.click();
				break;
			case 2:
				number2.click();
				break;
			case 3:
				number3.click();
				break;
			case 4:
				number4.click();
				break;
			case 5:
				number5.click();
				break;
			case 6:
				number6.click();
				break;
			case 7:
				number7.click();
				break;
			case 8:
				number8.click();
				break;
			case 9:
				number9.click();
				break;
			default:
				System.err.println("Please enter valid input");
				Assert.fail("Please enter valid input");
				break;
			}
		} catch (Exception e) {
			System.err.println("Unable to perform due to " + e.getMessage());
		}
		return this;
	}

	public Calculator enterNumber(String numb) {
		try {
			String[] numbArray = numb.split("");
			for (int counter = 0; counter < numbArray.length; counter++) {
				pressNumber(Integer.parseInt(numbArray[0]));
			}
			System.out.println(numb + " entered successfully");
		} catch (Exception e) {
			System.err.println("Unable to perform due to " + e.getMessage());
		}
		return this;
	}

	public Calculator clickOnSquareRoot() {
		squareRoot.click();
		return this;
	}

	public Calculator validateResult(String expected) {
		String actual = getValue(resultPane);
		Assert.assertTrue(actual.equals(expected));
		System.out.println("Expected found");
		return this;
	}

	public String getValue(WindowsElement wElement) {
		String value = "";
		try {
			value = wElement.getAttribute("Name");
			System.out.println(value);
		} catch (Exception e) {
			System.err.println("Unable to perform due to " + e.getMessage());
		}
		return value;
	}
}
