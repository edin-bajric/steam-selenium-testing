package com.project.steam;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class Testing {

    private static WebDriver d;
    private static String baseUrl;
    private static String username;
    private static String password;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Edin\\Downloads\\chromedriver_win32\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // options.addArguments("--user-data-dir=C:\\Users\\Edin\\Downloads");
        d = new ChromeDriver(options);
        d.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        baseUrl = "https://store.steampowered.com/";
        username = "steamSeleniumTesting";
        password = "svvt-selenium-testing";
    }

    @AfterAll
    static void afterAll() {
        d.quit();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        // login
        d.get(baseUrl);
        d.findElement(By.linkText("login")).click();
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div/div[1]/div/div/div/div[2]/div/form/div[1]/input")).sendKeys(username);
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div/div[1]/div/div/div/div[2]/div/form/div[2]/input")).sendKeys(password);
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div/div[1]/div/div/div/div[2]/div/form/div[4]/button")).click();

        Thread.sleep(2000);
    }

    @AfterEach
    void tearDown() {
        //logout
        d.get(baseUrl);
        d.findElement(By.id("account_pulldown")).click();
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[1]/div/div[3]/div/div[3]/div/a[3]")).click();
    }

    //1
    @Test
    void testProfileName() {
        //go to edit profile and change profile name
        d.findElement(By.id("account_pulldown")).click();
        d.findElement(By.linkText("View profile")).click();
        d.findElement(By.linkText("Edit Profile")).click();
        d.findElement(By.name("personaName")).clear();
        d.findElement(By.name("personaName")).sendKeys("Edo");

        //save
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[3]/div/div[2]/div/div/div[3]/div[3]/div[2]/form/div[7]/button[1]")).click();

        //go to profile and get and test profile name
        d.findElement(By.linkText("Edo")).click();
        String name = d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[1]/div[1]/div/div/div/div[1]/div[1]/span[1]")).getText();

        assertEquals("Edo", name);
    }

    //2
    @Test
    void testCountry() {
        //go to edit profile and select country
        d.findElement(By.id("account_pulldown")).click();
        d.findElement(By.linkText("View profile")).click();
        d.findElement(By.linkText("Edit Profile")).click();
        d.findElement(By.cssSelector(".DialogDropDown_CurrentDisplay")).click();
        d.findElement(By.cssSelector(".dropdown_DialogDropDownMenu_Item_1R-DV:nth-child(231)")).click();

        //save
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[3]/div/div[2]/div/div/div[3]/div[3]/div[2]/form/div[7]/button[1]")).click();

        //go to account details and get and test country
        d.findElement(By.id("account_pulldown")).click();
        d.findElement(By.linkText("Account details")).click();
        String country = d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[2]/div[1]/div[2]/div[2]/div[3]/div/p/span")).getText();

        assertEquals("Bosnia and Herzegovina", country);
    }

    //3
    @Test
    void testCart() {
        //find first game
        d.findElement(By.name("term")).sendKeys("Skyrim");
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[2]/div/div[9]/div[2]/div/div/a[1]/div[1]")).click();

        //date of birth
        Select day = new Select(d.findElement(By.name("ageDay")));
        day.selectByValue("4");
        Select month = new Select(d.findElement(By.name("ageMonth")));
        month.selectByValue("March");
        Select year = new Select(d.findElement(By.name("ageYear")));
        year.selectByValue("2001");

        d.findElement(By.id("view_product_page_btn")).click();

        //first game add to cart and test first cart total
        d.findElement(By.id("btn_add_to_cart_110687")).click();

        String firstTotal = d.findElement(By.id("cart_estimated_total")).getText();

        assertEquals("39,99€", firstTotal);

        d.findElement(By.linkText("Continue Shopping")).click();

        //find second game
        d.findElement(By.name("term")).sendKeys("Fallout");
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[2]/div/div[9]/div[2]/div/div/a[2]/div[1]")).click();

        //second game add to cart and test second cart total
        d.findElement(By.id("btn_add_to_cart_103387")).click();

        String secondTotal = d.findElement(By.id("cart_estimated_total")).getText();

        assertEquals("59,98€", secondTotal);
    }

    //4
    @Test
    void testWishlistPrice() throws InterruptedException {
        //find first game and add to wishlist
        d.findElement(By.name("term")).sendKeys("Half");
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[2]/div/div[9]/div[2]/div/div/a[3]/div[1]")).click();
        d.findElement(By.linkText("Add to your wishlist")).click();

        //go back
        d.navigate().back();

        //find second game and add to wishlist
        d.findElement(By.name("term")).sendKeys("Dark");
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[2]/div/div[9]/div[2]/div/div/a[3]/div[1]")).click();
        d.findElement(By.linkText("Add to your wishlist")).click();

        //go to wishlist
        d.findElement(By.id("wishlist_link")).click();

        //wait for everything to load
        Thread.sleep(2000);

        //select price under 10€
        d.findElement(By.id("tab_filters")).click();
        d.findElement(By.name("price_2")).click();
        d.findElement(By.id("tab_filters")).click();

        //test that the only game left is the one under 10€
        String price = d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div/div[2]/div/div[6]/div/div[2]/div[1]/div[2]/div/div/div/div")).getText();

        assertEquals("8,19€", price);
    }

    //5
    @Test
    void testWishlistType() throws InterruptedException {
        //find software and add to wishlist
        d.findElement(By.name("term")).sendKeys("Wallpaper");
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[2]/div/div[9]/div[2]/div/div/a[1]/div[1]")).click();
        d.findElement(By.linkText("Add to your wishlist")).click();

        //go back
        d.navigate().back();

        //find game and add to wishlist
        d.findElement(By.name("term")).sendKeys("Dark");
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[2]/div/div[9]/div[2]/div/div/a[3]/div[1]")).click();
        d.findElement(By.linkText("Add to your wishlist")).click();

        //go to wishlist
        d.findElement(By.id("wishlist_link")).click();

        //wait for everything to load
        Thread.sleep(2000);

        //select type software
        d.findElement(By.id("tab_filters")).click();
        d.findElement(By.id("type_Application")).click();
        d.findElement(By.id("tab_filters")).click();

        //test whether only software is left
        String tag = d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div/div[2]/div/div[6]/div/div[2]/div[2]/div[2]/div[1]/div[3]")).getText();

        assertEquals("Software", tag);
    }

    //6
    @Test
    void testLibrary() {
        //find game and add it to library
        d.findElement(By.name("term")).sendKeys("Counter");
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[2]/div/div[9]/div[2]/div/div/a[1]/div[1]")).click();
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[1]/div[3]/div[1]/div[5]/div[2]/div[1]/div[1]/div[2]/div/div[3]/span/span")).click();
        d.findElement(By.xpath("/html/body/div[4]/div[3]/div/div[2]/div/span")).click();

        //go to user's games
        d.findElement(By.id("account_pulldown")).click();
        d.findElement(By.linkText("View profile")).click();
        d.findElement(By.linkText("Games")).click();

        //test whether the game was added to the library
        String title = d.findElement(By.xpath("/html/body/div[1]/div[7]/div[2]/div[1]/div[2]/div/div[5]/div[2]/div/div/div[2]/div[1]/div[1]/div")).getText();

        assertEquals("Counter-Strike: Global Offensive", title);
    }

    //7
    @Test
    void testBadge() {
        //go to edit profile and set real name
        d.findElement(By.id("account_pulldown")).click();
        d.findElement(By.linkText("View profile")).click();
        d.findElement(By.linkText("Edit Profile")).click();
        d.findElement(By.name("real_name")).clear();
        d.findElement(By.name("real_name")).sendKeys("Edin");

        //save
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[3]/div/div[2]/div/div/div[3]/div[3]/div[2]/form/div[7]/button[1]")).click();

        //go back to profile and go to badges
        d.findElement(By.linkText("Edo")).click();
        d.findElement(By.linkText("Badges")).click();

        //go to Pillar of Community badge and test if a new task is completed
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[2]/div/div[2]/div[4]/div[4]/a")).click();

        String description = d.findElement(By.xpath("/html/body/div[1]/div[7]/div[2]/div/div[2]/div/div/div[3]/div[3]")).getText();

        assertEquals("6 of 28 tasks completed. Complete 7 more Steam Community tasks to earn the Level 1 badge.", description);
    }

    //8
    @Test
    void testChatFiltering() throws InterruptedException {
        //go to chat
        d.findElement(By.linkText("CHAT")).click();

        //wait for chat to load
        Thread.sleep(3000);

        //only double click opens a chat with a person
        Actions actions = new Actions(d);
        WebElement chat = d.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[2]/div/div[2]/div[3]/div/div[1]/div[2]/div/div"));
        actions.doubleClick(chat).perform();

        //test to see if message is visible
        String message = d.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[4]/div[2]/div/div[4]/div/div/div/div/div/div/div[2]/div[1]/div[1]/div[2]/div[3]/div/div[3]/div[2]/div[1]/span")).getText();

        assertEquals("testing", message);

        //go back
        d.navigate().back();

        //go to account details and add "testing" to "Always filter these words"
        d.findElement(By.id("account_pulldown")).click();
        d.findElement(By.linkText("Account details")).click();
        d.findElement(By.linkText("Preferences")).click();
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[3]/div[1]/div[2]/div[6]/div[2]/blockquote/div[3]/div[1]/div/form/div/div/input")).sendKeys("testing");
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[3]/div[1]/div[2]/div[6]/div[2]/blockquote/div[3]/div[1]/div/form/div/button/span")).click();

        //go to chat again and test to see if the message is now filtered
        d.findElement(By.linkText("CHAT")).click();

        Thread.sleep(4000);

        String chatFiltered = d.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[4]/div[2]/div/div[4]/div/div/div/div/div/div/div[2]/div[1]/div[1]/div[2]/div[3]/div/div[2]/div[2]/div[1]/span")).getText();

        assertNotEquals("testing", chatFiltered);
    }

    //9
    @Test
    void testChatFilteringFriends() throws InterruptedException {
        //go to account details and add "testing" to "Always filter these words"
        d.findElement(By.id("account_pulldown")).click();
        d.findElement(By.linkText("Account details")).click();
        d.findElement(By.linkText("Preferences")).click();
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[3]/div[1]/div[2]/div[6]/div[2]/blockquote/div[3]/div[1]/div/form/div/div/input")).sendKeys("testing");
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[6]/div[3]/div[1]/div[2]/div[6]/div[2]/blockquote/div[3]/div[1]/div/form/div/button/span")).click();

        //select "Do not filter text from my Steam Friends"
        d.findElement(By.name("community_text_filter_ignore_friends")).click();

        //go to chat again and test to see if the message is now filtered
        d.findElement(By.linkText("CHAT")).click();

        Thread.sleep(3000);

        //only double click opens a chat with a person
        Actions actions = new Actions(d);
        WebElement chat = d.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[2]/div/div[2]/div[3]/div/div[1]/div[2]/div/div"));
        actions.doubleClick(chat).perform();

        //test to see if message is visible
        String chatFiltered = d.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[4]/div[2]/div/div[4]/div/div/div/div/div/div/div[2]/div[1]/div[1]/div[2]/div[3]/div/div[2]/div[2]/div[1]/span")).getText();

        assertNotEquals("♥♥♥♥♥♥♥", chatFiltered);
    }

    //10
    @Test
    void testLanguage() {
        //change language to German
        d.findElement(By.id("account_pulldown")).click();
        d.findElement(By.id("account_language_pulldown")).click();
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[1]/div/div[3]/div/div[3]/div/div/div/a[9]")).click();
        d.findElement(By.linkText("Speichern")).click();

        //go back
        d.navigate().back();

        //refresh
        d.navigate().refresh();

        //test to see if language is changed
        String language = d.findElement(By.id("wishlist_link")).getText();

        assertNotEquals("WISHLIST", language);

        //change language back to English
        d.findElement(By.id("account_pulldown")).click();
        d.findElement(By.id("account_language_pulldown")).click();
        d.findElement(By.xpath("/html/body/div[1]/div[7]/div[1]/div/div[3]/div/div[3]/div/div/div/a[10]")).click();
        d.findElement(By.linkText("Save")).click();
    }
}
