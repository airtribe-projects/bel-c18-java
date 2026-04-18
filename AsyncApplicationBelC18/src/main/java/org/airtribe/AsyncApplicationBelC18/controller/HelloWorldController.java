package org.airtribe.AsyncApplicationBelC18.controller;

import java.awt.*;
import java.util.List;
import org.airtribe.AsyncApplicationBelC18.entity.Product;
import org.airtribe.AsyncApplicationBelC18.entity.ProductMeasurement;
import org.airtribe.AsyncApplicationBelC18.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class HelloWorldController {

  @Autowired
  private HelloWorldService _helloWorldService;

  @GetMapping("/hello")
  public String helloWorld() {
    System.out.println("Thread handling /hello request: " + Thread.currentThread().getName());
    for (int i = 0; i < 10000000L; i++) {
      // Simulate some processing
      double temp = Math.sqrt(i) * Math.pow(i, 2);
    }
    return "Hello, World!";
  }

  // RestTemplate Or WebClient
  @GetMapping("/hello2")
  public String helloWorld2() {
    return _helloWorldService.invokeHelloEndpoint();
  }

  @GetMapping("/products/sync")
  public ProductMeasurement getProductsSync() {
    System.out.println("Thread handling /products/sync request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsSync();
  }

  @GetMapping("/products/async")
  public Mono<ProductMeasurement> getProductsAsync() {
    System.out.println("Thread handling /products/async request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsAsync();
  }

  @GetMapping("/products/sync/webclient")
  public ProductMeasurement getProductsSyncWebClient() {
    System.out.println("Thread handling /products/sync/webclient request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsSyncWebClient();
  }

  @GetMapping("/products/parallelAll")
  public Mono<List<ProductMeasurement>> getAllProductsParallelAsync() {
    System.out.println("Thread handling /products/parallelAll request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsParallelAll();

  }

  @GetMapping("/products/parallelOne")
  public Mono<ProductMeasurement> getAllProductsParallelOne() {
    System.out.println("Thread handling /products/parallelOne request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsParallelOne();
  }

  @GetMapping("/products/chaining/sync")
  public List<ProductMeasurement> getProductsChainedSync() {
    System.out.println("Thread handling /products/chaining/sync request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsChainedSync();
  }

  @GetMapping("/products/chaining/async")
  public Mono<List<ProductMeasurement>> getProductsChainedAsync() {
    System.out.println("Thread handling /products/chaining/async request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsChainedAsync();
  }

  @GetMapping(value = "/products/fluxStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ProductMeasurement> getProductsFluxStream() {
    System.out.println("Thread handling /products/fluxStream request: " + Thread.currentThread().getName());
    return _helloWorldService.fetchProductsFluxStream();
  }

}
