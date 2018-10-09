package rushi.cloud.poc.kubernates.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GreetingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);

    private static final Map<Integer, String> map = Collections.unmodifiableMap(new HashMap<Integer, String>() {
        {
            put(0, "zero");
            put(1, "one");
            put(2, "two");
            put(3, "three");
            put(4, "four");
            put(5, "five");
            put(6, "six");
            put(7, "seven");
            put(8, "eight");
            put(9, "nine");
            put(10, "ten");
            put(11, "eleven");
            put(12, "twelve");
        }
    });

    @GetMapping("/")
    Mono<String> test() {
        return Mono.justOrEmpty("Hello");
    }

    @GetMapping("/api/env")
    @ResponseBody
    Mono<Map> env() {

        return Mono.justOrEmpty(System.getenv());
    }

    @GetMapping("/api/sys")
    @ResponseBody
    Mono<Map> sysProps() {
        return Mono.justOrEmpty(System.getProperties());
    }

    @GetMapping("/api/id")
    @ResponseBody
    Mono<String> deploymentId() {
        return Mono.justOrEmpty(System.getenv("DEPLOYMENT_ID"));
    }

    @GetMapping("/api/concat")
    @ResponseBody
    Mono<String> concat() {
        LOGGER.info("In Concat");
        int my = Integer.parseInt(System.getenv("DEPLOYMENT_ID"));
        int end = Integer.parseInt(System.getenv("DEPLOYMENT_END"));
        LOGGER.info("my-" + my + " : " + "end-" + end);

        if (my == end) return Mono.justOrEmpty("!");

        RestTemplate restTemplate = new RestTemplate();
        int next = my + 1;
        String s = map.get(my) + " " + restTemplate.getForObject("http://hello-kube-service-" + next + "/api/concat", String.class);
        System.out.println(s);
        LOGGER.info(s);
        return Mono.justOrEmpty(s);
    }
}
