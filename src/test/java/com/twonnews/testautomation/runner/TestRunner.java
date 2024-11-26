package com.twonnews.testautomation.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "twonnewstestautomation/src/test/resources/features",
    glue = "com.twonnews.testautomation.steps",
    plugin = {
        "pretty",
        "json:target/testreport/cucumber-json/cucumber.json",
        "html:target/testreport/cucumber-reports/Cucumber-Report.html",
        "junit:target/testreport/cucumber-reports/Cucumber-junit.xml"
    },
    monochrome = true,
    publish = true
)
public class TestRunner {
    static {
        System.setProperty("cucumber.publish.quiet", "true");
    }
}