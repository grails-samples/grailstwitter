import org.openqa.selenium.htmlunit.HtmlUnitDriver

driver = {
    def driver = new HtmlUnitDriver()
    driver.javascriptEnabled = true
    driver
}