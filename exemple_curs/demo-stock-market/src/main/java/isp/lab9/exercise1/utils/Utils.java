package isp.lab9.exercise1.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import isp.lab9.exercise1.services.StockItem;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Radu Miron
 */
public class Utils {
    public static final BigDecimal HUNDRED = new BigDecimal(100);
    public static final BigDecimal THOUSAND = new BigDecimal(1000);
    public static final BigDecimal MILLION = new BigDecimal(1000000);
    public static final BigDecimal BILLION = new BigDecimal(1000000000);

    public static String getURLParameters(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();

        String key;
        String value;
        for (Iterator var2 = params.entrySet().iterator(); var2.hasNext(); sb.append(String.format("%s=%s", key, value))) {
            Map.Entry<String, String> entry = (Map.Entry) var2.next();
            if (sb.length() > 0) {
                sb.append("&");
            }

            key = (String) entry.getKey();
            value = (String) entry.getValue();

            try {
                key = URLEncoder.encode(key, "UTF-8");
                value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }

        return sb.toString();
    }

    public static String join(String[] data, String d) {
        if (data.length == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();

            int i;
            for (i = 0; i < data.length - 1; ++i) {
                sb.append(data[i]).append(d);
            }

            return sb.append(data[i]).toString();
        }
    }

    public static List<StockItem> parseStocksJson(String stocksJson) throws IOException {
        List<StockItem> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(new StringReader(stocksJson));
        if (node.has("quoteResponse") && node.get("quoteResponse").has("result")) {
            node = node.get("quoteResponse").get("result");

            for (int i = 0; i < node.size(); ++i) {
                result.add(parseJson(node.get(i)));
            }

            return result;
        } else {
            throw new IOException("Invalid response");
        }
    }

    private static StockItem parseJson(JsonNode node) {
        String symbol = node.get("symbol").asText();
        StockItem stock = new StockItem();
        stock.setSymbol(symbol);

        if (node.has("longName")) {
            stock.setName(node.get("longName").asText());
        } else {
            stock.setName(getStringValue(node, "shortName"));
        }

        stock.setCurrency(getStringValue(node, "currency"));
        stock.setExchange(getStringValue(node, "fullExchangeName"));

        BigDecimal price = getBigDecimal(getStringValue(node, "regularMarketPrice"));
        BigDecimal previousClose = getBigDecimal(getStringValue(node, "regularMarketPreviousClose"));
        BigDecimal change = price != null && previousClose != null ? price.subtract(previousClose) : null;

        stock.setPrice(price);
        stock.setChange(getPercent(change, previousClose));

        return stock;
    }

    private static String getStringValue(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }

    private static BigDecimal getBigDecimal(String data) {
        BigDecimal result = null;
        if (!isParseable(data)) {
            return result;
        } else {
            try {
                data = cleanNumberString(data);
                char lastChar = data.charAt(data.length() - 1);
                BigDecimal multiplier = BigDecimal.ONE;
                switch (lastChar) {
                    case 'B':
                        data = data.substring(0, data.length() - 1);
                        multiplier = BILLION;
                        break;
                    case 'K':
                        data = data.substring(0, data.length() - 1);
                        multiplier = THOUSAND;
                        break;
                    case 'M':
                        data = data.substring(0, data.length() - 1);
                        multiplier = MILLION;
                }

                result = (new BigDecimal(data)).multiply(multiplier);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            return result;
        }
    }

    private static String cleanNumberString(String data) {
        return join(data.trim().split(","), "");
    }

    private static boolean isParseable(String data) {
        return data != null && !data.equals("N/A") && !data.equals("-") && !data.equals("") && !data.equals("nan");
    }

    public static BigDecimal getPercent(BigDecimal numerator, BigDecimal denominator) {
        return denominator != null && numerator != null && denominator.compareTo(BigDecimal.ZERO) != 0 ? numerator.divide(denominator, 4, RoundingMode.HALF_EVEN).multiply(HUNDRED).setScale(2, RoundingMode.HALF_EVEN) : BigDecimal.ZERO;
    }
}
