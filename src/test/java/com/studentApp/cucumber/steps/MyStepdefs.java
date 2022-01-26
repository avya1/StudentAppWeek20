package com.studentApp.cucumber.steps;

import com.studentApp.studentinfo.StudentSteps;

import com.studentApp.utils.TestUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;


public class MyStepdefs {

    static ValidatableResponse response;

    @Steps
    StudentSteps studentSteps;
    static String firstName = "Anchal" + TestUtils.getRandomValue();
    static String lastName = "Rathod" + TestUtils.getRandomValue();
    static String email = TestUtils.getRandomValue() + "abc@gmail.com";
    static String programme = "SerenityAPITesting";
    static int studentID;


    @When("^User sends a GET request to list endpoint$")
    public void userSendsAGETRequestToListEndpoint() {
        response = studentSteps.getAllStudentsInfo();

    }

    @Then("^User must get back a valid status code 200$")
    public void userMustGetBackAValidStatusCode() {
        response.statusCode(200);
    }


    @When("^User can create new student with all the details$")
    public void userCanCreateNewStudentWithAllTheDetails() {
        List<String> courseList = new ArrayList<>();
        courseList.add("Core Java");
        courseList.add("Advance Java");
        ValidatableResponse response = studentSteps.createNewStudent(firstName, lastName, email, programme, courseList);
        response.log().all().statusCode(201);

    }

    @Then("^User must verify created student is added$")
    public void userMustVerifyCreatedStudentIsAdded() {
        HashMap<String, Object> record = studentSteps.getStudentInfoByFirstName(firstName);
        Assert.assertThat(record, hasValue(firstName));
        studentID = (int) record.get("id");
    }

    @When("^User update student detail by changing name$")
    public void userUpdateStudentDetailByChangingName() {
        firstName = firstName + "Updated Name";
        List<String> courseList = new ArrayList<>();
        courseList.add("Core Java");
        courseList.add("Advance Java");
        ValidatableResponse response = studentSteps.updateStudent(studentID, firstName, lastName, email, programme, courseList);
        response.log().all().statusCode(200);


    }

    @Then("^User must able to see updated student detail in record$")
    public void userMustAbleToSeeUpdatedStudentDetailInRecord() {
        HashMap<String, Object> record= studentSteps.getStudentInfoByFirstName(firstName);
        Assert.assertThat(record, hasValue(firstName));
    }

    @When("^User delete any student by id$")
    public void userDeleteAnyStudentById() {
        studentSteps.deleteStudent(studentID);
    }

    @Then("^User search the same id to verify student is deleted$")
    public void userSearchTheSameIdToVerifyStudentIsDeleted() {
        studentSteps.getStudentById(studentID).statusCode(404);
    }
}
