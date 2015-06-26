/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.wallboard;

import java.io.IOException;
import static java.lang.System.in;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium2Example {

    public static void main(String[] args) throws Exception {

        //System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
        //System.setProperty("http.proxyPort", "80");
        // Create a new instance of the Firefox driver
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Pepco
        driver.get("http://stormcenter.pepco.com.s3.amazonaws.com/sc.html");
        // Wait until Element is Clickable - it is Displayed and Enabled
        WebDriverWait pepco_wait = new WebDriverWait(driver, 10);
        WebElement pepco_element = pepco_wait.until(ExpectedConditions.elementToBeClickable(By.id("summary_tab")));
        // Sleep for 1 sec
        Thread.sleep(1000);
        pepco_element.click();

        System.out.println("Page title is: " + driver.getTitle());

        WebElement pepco_num_outages = driver.findElement(By.id("num_outages_text"));
        System.out.println(pepco_num_outages.getText());
        WebElement pepco_num_custs = driver.findElement(By.id("num_custs_text"));
        System.out.println(pepco_num_custs.getText());

        // LGE-KU
        driver.get("http://stormcenter.lge-ku.com/default.html");
        // Wait until Element is Clickable - it is Displayed and Enabled
        WebDriverWait lge_ku_wait = new WebDriverWait(driver, 10);
        WebElement lge_ku_element = lge_ku_wait.until(ExpectedConditions.elementToBeClickable(By.id("summary_tab")));
        // Sleep for 1.5 sec
        Thread.sleep(1500);
        lge_ku_element.click();

        System.out.println("Page title is: " + driver.getTitle());

        WebElement lge_ku_num_outages = driver.findElement(By.id("num_outages_text"));
        System.out.println(lge_ku_num_outages.getText());
        WebElement lge_ku_num_custs = driver.findElement(By.id("num_custs_text"));
        System.out.println(lge_ku_num_custs.getText());

        // DUKE ENERGY Carolinas
        driver.get("http://outagemap.duke-energy.com/ncsc/default.html");
        // Wait until Element is Clickable - it is Displayed and Enabled
        WebDriverWait duke_carolinas_wait = new WebDriverWait(driver, 10);
        WebElement duke_carolinas_element = duke_carolinas_wait.until(ExpectedConditions.elementToBeClickable(By.id("summary_tab")));
        // Sleep for 1 sec
        Thread.sleep(1000);
        duke_carolinas_element.click();

        System.out.println("Page title is: " + driver.getTitle());

        WebElement duke_carolinas_num_outages = driver.findElement(By.id("num_outages_text"));
        System.out.println(duke_carolinas_num_outages.getText());
        WebElement duke_carolinas_num_custs = driver.findElement(By.id("num_custs_text"));
        System.out.println(duke_carolinas_num_custs.getText());

        // DUKE ENERGY Indiana
        driver.get("http://outagemap.duke-energy.com/in/default.html");
        // Wait until Element is Clickable - it is Displayed and Enabled
        WebDriverWait duke_indiana_wait = new WebDriverWait(driver, 10);
        WebElement duke_indiana_element = duke_indiana_wait.until(ExpectedConditions.elementToBeClickable(By.id("summary_tab")));
        // Sleep for 1.5 sec
        Thread.sleep(1500);
        duke_indiana_element.click();

        System.out.println("Page title is: " + driver.getTitle());

        WebElement duke_indiana_num_outages = driver.findElement(By.id("num_outages_text"));
        System.out.println(duke_indiana_num_outages.getText());
        WebElement duke_indiana_num_custs = driver.findElement(By.id("num_custs_text"));
        System.out.println(duke_indiana_num_custs.getText());

        // DUKE ENERGY Ohio-Kentucky
        driver.get("http://outagemap.duke-energy.com/ohky/default.html");
        // Wait until Element is Clickable - it is Displayed and Enabled
        WebDriverWait duke_ohky_wait = new WebDriverWait(driver, 10);
        WebElement duke_ohky_element = duke_ohky_wait.until(ExpectedConditions.elementToBeClickable(By.id("summary_tab")));
        // Sleep for 1 sec
        Thread.sleep(1000);
        duke_ohky_element.click();

        System.out.println("Page title is: " + driver.getTitle());

        WebElement duke_ohky_num_outages = driver.findElement(By.id("num_outages_text"));
        System.out.println(duke_ohky_num_outages.getText());
        WebElement duke_ohky_num_custs = driver.findElement(By.id("num_custs_text"));
        System.out.println(duke_ohky_num_custs.getText());

        // KCPL
        driver.get("http://outagemap.kcpl.com/external/default.html");
        // Wait until Element is Clickable - it is Displayed and Enabled
        WebDriverWait kcpl_wait = new WebDriverWait(driver, 10);
        WebElement kcpl_element = kcpl_wait.until(ExpectedConditions.elementToBeClickable(By.id("summary_tab")));
        // Sleep for 1 sec
        Thread.sleep(1000);
        kcpl_element.click();

        System.out.println("Page title is: " + driver.getTitle());

        WebElement kcpl_num_outages = driver.findElement(By.id("num_outages_text"));
        System.out.println(kcpl_num_outages.getText());
        WebElement kcpl_num_custs = driver.findElement(By.id("num_custs_text"));
        System.out.println(kcpl_num_custs.getText());

        // Xcel
        driver.get("http://www.outagemap-xcelenergy.com/outagemap/");
        // Wait until Element is Clickable - it is Displayed and Enabled
        WebDriverWait xcel_wait = new WebDriverWait(driver, 10);
        WebElement xcel_element = xcel_wait.until(ExpectedConditions.elementToBeClickable(By.id("legendTab")));
        // Sleep for 1 sec
        Thread.sleep(1000);
        xcel_element.click();

        System.out.println("Page title is: " + driver.getTitle());

        WebElement xcel_num_outages = driver.findElement(By.id("statisticsDiv"));
        String pattern = "shows (\\d+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(xcel_num_outages.getText());
        if (m.find()) {
            System.out.println("Active Outages: " + m.group(1));
        }
        pattern = "affecting (\\d+)";
        p = Pattern.compile(pattern);
        m = p.matcher(xcel_num_outages.getText());
        if (m.find()) {
            System.out.println("Affected Customers: " + m.group(1));
        }
        //System.out.println(xcel_num_outages.getText());

        // EnergyUnited
        driver.get("https://outageinfo.energyunited.com/storm_center_outages.aspx");

        // Wait until Element is Clickable - it is Displayed and Enabled
        WebDriverWait eu_wait = new WebDriverWait(driver, 10);
        //WebElement eu_element = eu_wait.until(ExpectedConditions.elementToBeClickable(By.id("legendTab")));
        //eu_element.click();

        System.out.println("Page title is: " + driver.getTitle());

        // UGI Electric
        driver.get("http://www.ugi.com/portal/page/portal/UGI/Outage_Center/Outage_Map");

        // Wait until Element is Clickable - it is Displayed and Enabled
        WebDriverWait ugi_wait = new WebDriverWait(driver, 10);
        //WebElement eu_element = eu_wait.until(ExpectedConditions.elementToBeClickable(By.id("legendTab")));
        //eu_element.click();

        System.out.println("Page title is: " + driver.getTitle());

        // Seattle City Light
        driver.get("http://www.seattle.gov/light/sysstat/");

        // Wait until Element is Clickable - it is Displayed and Enabled
        WebDriverWait scl_wait = new WebDriverWait(driver, 10);
        //WebElement scl_element = scl_wait.until(ExpectedConditions.elementToBeClickable(By.id("legendTab")));
        Boolean scl_element = scl_wait.until(ExpectedConditions.titleContains("Seattle City Light System"));
        // Sleep for 1.5 sec
        Thread.sleep(1500);
        //scl_element.click();

        System.out.println("Page title is: " + driver.getTitle());

        WebElement scl_num_outages = driver.findElement(By.id("essOutage2"));
        System.out.println("Active Outages: " + scl_num_outages.getText());
        WebElement scl_num_custs = driver.findElement(By.id("essEffected2"));
        System.out.println("Affected Customers: " + scl_num_custs.getText());

        // SDGE
        HttpHost proxy = new HttpHost("www-proxy.us.oracle.com", 80);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        CloseableHttpClient httpclient = HttpClients.custom().setRoutePlanner(routePlanner).build();
        Integer sdge_num_outages = 0;
        Integer sdge_num_custs = 0;
        try {
            HttpGet httpGet = new HttpGet("http://www.sdge.com/sites/default/files/outagemap_json/outage.json");
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httpGet, responseHandler);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(responseBody);
            JSONObject jsonObject = (JSONObject) obj;
            String dataDateTime = (String) jsonObject.get("dataDateTime");
            LocalDateTime data = LocalDateTime.parse(dataDateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            //System.out.println("dataDateTime: " + data);
            JSONArray outageItems = (JSONArray) jsonObject.get("outageItems");
            Iterator<JSONObject> iterator = outageItems.iterator();
            while (iterator.hasNext()) {
                JSONObject outageItemsJSON = iterator.next();
                String startTime = outageItemsJSON.get("startTime").toString();
                LocalDateTime start = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                String calltype = outageItemsJSON.get("calltype").toString();
                int val = Integer.parseInt(outageItemsJSON.get("customerOut").toString());
                // Planned Outage
                if (calltype.matches("PLAN")) {
                    // dataDateTime is after startTime
                    if (data.compareTo(start) > 0) {
                        sdge_num_outages = sdge_num_outages + 1;
                        sdge_num_custs = sdge_num_custs + val;
                    }
                }
                // Unplanned Outage
                if (calltype.matches("OUT")) {
                    sdge_num_outages = sdge_num_outages + 1;
                    sdge_num_custs = sdge_num_custs + val;
                }
            }
        } finally {
            httpclient.close();
        }
        System.out.println("Page title is: Outage Map | San Diego Gas & Electric");
        System.out.println("Active Outages: " + sdge_num_outages);
        System.out.println("Affected Customers: " + sdge_num_custs);

        // conEdison
        driver.get("http://apps.coned.com/stormcenter_external/default.html");
        // Wait until Element is Clickable - it is Displayed and Enabled
        WebDriverWait coned_wait = new WebDriverWait(driver, 10);
        WebElement coned_element = coned_wait.until(ExpectedConditions.elementToBeClickable(By.id("summary_tab")));
        // Sleep for 1.5 sec
        Thread.sleep(1500);
        coned_element.click();

        System.out.println("Page title is: " + driver.getTitle());

        WebElement coned_num_outages = driver.findElement(By.id("num_outages_text"));
        System.out.println(coned_num_outages.getText());
        WebElement coned_num_custs = driver.findElement(By.id("num_custs_text"));
        System.out.println(coned_num_custs.getText());

        //Close the browser
        driver.close();
        //driver.quit();
    }
}
