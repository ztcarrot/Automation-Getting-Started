package org.pages.utils;

import org.automation.Browser;
import org.automation.exceptions.CannotClickElementException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

/**
 * Created by shantonu on 6/6/16.
 * this class is responsible for all utilitie for yandex element based pages.
 */
public class ElementUtil extends UtilBase {

    public ElementUtil(WebDriver aDriver) {
        super(aDriver);
    }
    public void scrollDown(TypifiedElement element) {
        try {
            executor.executeScript("arguments[0].scrollIntoView(true);", new Object[]{element});
        } catch (WebDriverException var2) {
            ;
        }

    }

    public void click(TypifiedElement element) throws CannotClickElementException {
        try {
            executor.executeScript("arguments[0].click();", new Object[]{element});
        } catch (WebDriverException var3) {
            String text = element.toString() + " :" + var3.getMessage();

            throw new CannotClickElementException(text);
        }
    }

    public boolean isHorizontalScrollbarPresent(TypifiedElement element) {
        long clientWidth = ((Long) executor.executeScript("return arguments[0].clientWidth", new Object[]{element})).longValue();
        long scrollWidth = ((Long)executor.executeScript("return arguments[0].scrollWidth", new Object[]{element})).longValue();
        return clientWidth < scrollWidth;
    }

    public boolean isElementPresent(HtmlElement element) {
        if(element != null && element.getWrappedElement() != null) {
            return true;
        } else {

            return false;
        }
    }

    public void typeUnchecked(TextInput element, CharSequence... charSequences) {


        try {
            element.clear();
            element.sendKeys(charSequences);
        } catch (Exception var3) {

        }

    }
    public boolean isElementVisible(TypifiedElement typifiedElement) {
        return isElementVisible((TypifiedElement)typifiedElement, 20);
    }
    public boolean isElementVisible(TypifiedElement element, int newTimeOut) {

        return isElementVisible((WebElement)element.getWrappedElement(), newTimeOut);
    }
    public boolean isFilledWithThatTextAlready(TextInput element, String text) {
        return element.getText().equals(text);
    }
    public boolean isElementVisible(WebElement webElement, int newTimeOut) {
        WebDriverWait wait = Browser.setWebDriverWait(newTimeOut);
        try {
            wait.until(ExpectedConditions.visibilityOf(webElement));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void type(TextInput element, CharSequence... charSequences) {
        String textValue = "";
        CharSequence[] arr$ = charSequences;
        int len$ = charSequences.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            CharSequence charValue = arr$[i$];
            textValue = textValue + charValue;
        }
        if(isElementVisible((TypifiedElement)element) && !isFilledWithThatTextAlready(element, textValue)) {
            typeUnchecked(element, charSequences);
        }

    }

}