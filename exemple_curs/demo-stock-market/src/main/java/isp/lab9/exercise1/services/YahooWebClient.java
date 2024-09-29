package isp.lab9.exercise1.services;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import isp.lab9.exercise1.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Uses htmlunit to retrieve stocks data from Yahoo Finance.
 *
 * @author Radu Miron
 */
public class YahooWebClient {
    private static final String YAHOO_FINANCE_URL = "https://finance.yahoo.com/?guccounter=1";
    private static final String CRUMB_URL = "https://query1.finance.yahoo.com/v1/test/getcrumb";
    private static final String QUOTES_QUERY1V7_BASE_URL = "https://query1.finance.yahoo.com/v7/finance/quote";
    private static final String CRUMB_ERR_MSG = "Unable to get the crumb";
    private static WebClient webClient;
    private static String crumb;

    private YahooWebClient() {
    }

    public static StockItem get(String symbol) throws IOException {
        return getQuotes(symbol).stream().findFirst().orElse(null);
    }

    public static List<StockItem> get(String[] symbols) throws IOException {
        return getQuotes(Utils.join(symbols, ","));
    }

    private static List<StockItem> getQuotes(String query) throws IOException {
        initializeCrumb();
        Map<String, String> params = new LinkedHashMap();
        params.put("symbols", query);
        params.put("crumb", crumb);
        String url = QUOTES_QUERY1V7_BASE_URL + "?" + Utils.getURLParameters(params);
        String stocksJson = getWebClient().getPage(url).getWebResponse().getContentAsString();
        return Utils.parseStocksJson(stocksJson);
    }

    private static WebClient getWebClient() throws IOException {
        if (webClient == null) {
            webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
        }

        return webClient;
    }

    private static void initializeCrumb() throws IOException {
        if (crumb == null) {
            //go to home page - you get redirected to accept cookies page
            HtmlPage home = getWebClient().getPage(YAHOO_FINANCE_URL);

            //select Agree button and accept the cookies by clicking it
            HtmlButton agreeButton = (HtmlButton) home.getByXPath("//button[@name='agree']").stream()
                    .findFirst().orElseThrow(() -> new RuntimeException(CRUMB_ERR_MSG));
            agreeButton.click();

            //now, you have all the needed cookies in webClient; you can retrieve the crumb

            //get the crumb
            WebRequest crumbRequest = new WebRequest(new URL(CRUMB_URL));
            crumb = ((TextPage) getWebClient().getPage(crumbRequest)).getContent().trim();

            if (crumb.isEmpty()) {
                throw new RuntimeException(CRUMB_ERR_MSG);
            }

            System.out.println("Got crumb: " + crumb);
        }
    }
}
