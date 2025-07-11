package com.example.crud_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud_app.model.User;
import com.example.crud_app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/siebel")
public class SiebelController {

    @Autowired
    private UserService userService;

    // @PostMapping(value = "", consumes = "text/xml", produces = "text/xml")
    // public ResponseEntity<String> handlePostRequest(@RequestBody(required =
    // false) String requestBody) {
    // System.out.println("Received body: " + requestBody);
    // System.out.println(">>> Incoming request to /siebel <<<");
    // if (requestBody != null && requestBody.contains("<siebel-xmlext-fields-req"))
    // {
    // System.out.println("Received Request:\n" + requestBody);
    // return ResponseEntity.ok(buildInitResponse());
    // } else if (requestBody != null &&
    // requestBody.contains("<siebel-xmlext-query-req")) {
    // List<User> users = userService.getAllUsers();
    // System.out.println("Received Query Request:\n" + requestBody);
    // return ResponseEntity.ok(buildQueryResponse(users));
    // } else {
    // System.out.println("Invalid Request:\n" + requestBody);
    // return ResponseEntity.badRequest().body("Invalid request");
    // }
    // }
    @PostMapping(consumes = "text/xml", produces = "text/xml")
    public ResponseEntity<String> handlePostRequest(
            @RequestBody(required = false) String requestBody,
            HttpServletRequest request) {

        System.out.println(">>> Incoming request to /siebel <<<");

        request.getHeaderNames().asIterator()
                .forEachRemaining(header -> System.out.println(header + "-------HEADER-------: " + request.getHeader(header)));

        if (requestBody == null || requestBody.trim().isEmpty()) {
            System.out.println("No body received!");
            return ResponseEntity.badRequest().body("No XML body received");
        }

        System.out.println("Received body: " + requestBody);

        if (requestBody.contains("<siebel-xmlext-fields-req")) {
            System.out.println("Detected INIT Request");
            return ResponseEntity.ok(buildInitResponse());
        } else if (requestBody.contains("<siebel-xmlext-query-req")) {
            System.out.println("Detected QUERY Request");
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(buildQueryResponse(users));
        } else {
            System.out.println("Unrecognized Request Type");
            return ResponseEntity.badRequest().body("Invalid request");
        }
    }

    // Build and return the <init> response XML
    private String buildInitResponse() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<siebel-xmlext-fields-ret>\n" +
                "    <support field=\"id\" />\n" +
                "    <support field=\"name\" />\n" +
                "    <support field=\"email\" />\n" +
                "</siebel-xmlext-fields-ret>";
    }

    // Build and return the <query> response XML
    private String buildQueryResponse(List<User> users) {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<siebel-xmlext-query-ret>\n");

        if (users == null || users.isEmpty()) {
            xml.append("  <row></row>\n");
        } else {
            for (User user : users) {
                xml.append("  <row>\n");
                xml.append("    <value field=\"id\">").append(user.getId()).append("</value>\n");
                xml.append("    <value field=\"name\">").append(user.getName()).append("</value>\n");
                xml.append("    <value field=\"email\">").append(user.getEmail()).append("</value>\n");
                xml.append("  </row>\n");
            }
        }
        xml.append("</siebel-xmlext-query-ret>");
        return xml.toString();
    }

}
