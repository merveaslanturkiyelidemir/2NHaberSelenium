package com.twonnews.testautomation.helper;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {
    private JSONObject locators;

    public JsonReader() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("locator/locators.json")) {
            if (inputStream == null) {
                throw new IOException("Locator file not found in resources");
            }
            String content = new String(inputStream.readAllBytes());
            locators = new JSONObject(content);
        } catch (IOException e) {
            System.err.println("Error reading locators file: " + e.getMessage());
            throw new RuntimeException("Cannot read locators file", e);
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            throw new RuntimeException("Invalid JSON format in locators file", e);
        }
    }

    /**
     * Retrieves the locator string for the specified element.
     * @param elementName the name of the element
     * @return the locator string
     */
    public String getLocator(String elementName) {
        return locators.getString(elementName);
    }

    /**
     * Retrieves the locator value for the specified element.
     * @param elementName the name of the element
     * @return the locator value
     */
    public String getLocatorValue(String elementName) {
        JSONObject elementObj = locators.getJSONObject(elementName);
        return elementObj.getString("value");
    }

    /**
     * Retrieves the locator type for the specified element.
     * @param elementName the name of the element
     * @return the locator type
     */
    public String getLocatorType(String elementName) {
        JSONObject elementObj = locators.getJSONObject(elementName);
        return elementObj.getString("type");
    }
}