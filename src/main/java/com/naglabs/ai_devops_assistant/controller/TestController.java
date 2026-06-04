package com.naglabs.ai_devops_assistant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;

@RestController
public class TestController {
    @GetMapping("/whoami")
    public String whoAmI() {
        System.out.println("hey hello....");
        try (StsClient sts = StsClient.builder()
                .region(Region.EU_WEST_2)
                .build()) {

            return sts.getCallerIdentity().arn();
        }
    }
}
