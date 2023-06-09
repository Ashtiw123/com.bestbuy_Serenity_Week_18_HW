package com.bestbuy.swagger.crudtests;

import com.bestbuy.steps.CategoriesSteps;
import com.bestbuy.swagger.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CategoriesCRUDTest extends TestBase {
    static String name = "Cars" + TestUtils.getRandomValue();
    static String id = "Jason's" + TestUtils.getRandomValue();
    static String categoryID;

    @Steps
    CategoriesSteps categoriesSteps;

    @Title("This will create a New Category")
    @Test
    public void test001() {
        ValidatableResponse response = categoriesSteps.createCategory(name, id);
        response.log().all().statusCode(201);
        categoryID = response.log().all().extract().path("id");
        System.out.println(categoryID);
    }

    @Title("This test will Verify if the Category was added to the application")
    @Test
    public void test002() {
        HashMap<String, ?> categoryMap = categoriesSteps.getCategoryInfoByName(categoryID);
        Assert.assertThat(categoryMap, hasValue(name));
        System.out.println(categoryMap);
    }

    @Title("This test will Update the Category information")
    @Test
    public void test003() {
        name = name + "_updated";
        categoriesSteps.updatingCategory(categoryID, name, id).statusCode(200);
        HashMap<String, ?> productList = categoriesSteps.getCategoryInfoByName(categoryID);
        Assert.assertThat(productList, hasValue(name));
        System.out.println(productList);
    }

    @Title("This test will Delete the Category")
    @Test
    public void test004() {
        categoriesSteps.deleteCategory(categoryID).statusCode(200);
        categoriesSteps.getCategoryByID(categoryID).statusCode(404);
    }
}