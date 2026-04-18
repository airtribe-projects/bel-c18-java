package org.airtribe.AsyncApplicationBelC18.service;

import java.time.Duration;
import java.util.List;
import org.airtribe.AsyncApplicationBelC18.entity.Product;
import org.airtribe.AsyncApplicationBelC18.entity.ProductMeasurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class HelloWorldService {

  // make sync calls
  @Autowired
  private RestTemplate _restTemplate;

  // make async and sync
  @Autowired
  private WebClient _webClient;

  public String invokeHelloEndpoint() {
    System.out.println("Thread handling /hello2 request: " + Thread.currentThread().getName());
    String returnedResult = _restTemplate.getForObject("http://localhost:1010/hello", String.class);
    return returnedResult;
  }

  public ProductMeasurement fetchProductsSync() {
    System.out.println("Thread handling fetchProductsSync request: " + Thread.currentThread().getName());
    ProductMeasurement result = _restTemplate.getForObject("https://dummyjson.com/products", ProductMeasurement.class);
    return result;
  }

  public Mono<ProductMeasurement> fetchProductsAsync() {
    System.out.println("Thread handling fetchProductsAsync request: " + Thread.currentThread().getName());
    Mono<ProductMeasurement> result = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductMeasurement.class)
        .doOnSuccess(products -> {
          System.out.println("Received response for async products request: " + products);
          System.out.println("Thread handling the async products request: " + Thread.currentThread().getName());
        }).doOnError(error -> {
          System.out.println("Error occurred while fetching products asynchronously: " + error.getMessage());
        });


    for (int i = 0; i < 1000000; i++) {
      // Simulate some processing
      System.out.println("Thread handling fetchProductsAsync request is doing some processing: " + Thread.currentThread().getName());
      double temp = Math.sqrt(i) * Math.pow(i, 2);
    }

    return result;

  }

  public ProductMeasurement fetchProductsSyncWebClient() {
    System.out.println("Thread handling fetchProductssyncWebClient request: " + Thread.currentThread().getName());
    ProductMeasurement resullt = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductMeasurement.class).block();
    for (int i = 0; i < 1000000; i++) {
      // Simulate some processing
      System.out.println("Thread handling fetchProductsAsync request is doing some processing: " + Thread.currentThread().getName());
      double temp = Math.sqrt(i) * Math.pow(i, 2);
    }

    return resullt;
  }

  public Mono<List<ProductMeasurement>> fetchProductsParallelAll() {
    System.out.println("Thread handling fetchProductsParallelAll request: " + Thread.currentThread().getName());

    Mono<ProductMeasurement> result1 = _webClient.get().uri("https://dummyjson.com/ghkghgkgjkgjrlgjlr").retrieve().bodyToMono(ProductMeasurement.class);
    Mono<ProductMeasurement> result2 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductMeasurement.class);
    Mono<ProductMeasurement> result3 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductMeasurement.class);

    return Mono.zip(result1, result2, result3).map(tuple -> List.of(tuple.getT1(), tuple.getT2(), tuple.getT3())).doOnSuccess(apiResult -> {
      System.out.println("Thread handling the async products parallel request: " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred in parallel products request: " + error.getMessage());
    });

  }

  public Mono<ProductMeasurement> fetchProductsParallelOne() {
    System.out.println("Thread handling fetchProductsParallelOne request: " + Thread.currentThread().getName());

    Mono<ProductMeasurement> result1 = _webClient.get().uri("https://dummyjson.com/products]").retrieve().bodyToMono(ProductMeasurement.class);
    Mono<ProductMeasurement> result2 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductMeasurement.class);
    Mono<ProductMeasurement> result3 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductMeasurement.class);

    return Mono.first(result1, result2, result3).doOnSuccess(apiResult -> {
      System.out.println("Thread handling the async products parallel one request: " + Thread.currentThread().getName());
    }).doOnError(error -> {
      System.out.println("Error occurred in parallel one products request: " + error.getMessage());
    });
  }

  public List<ProductMeasurement> fetchProductsChainedSync() {
    System.out.println("Thread handling fetchProductsChainedSync request: " + Thread.currentThread().getName());

    ProductMeasurement result1 = _restTemplate.getForObject("https://dummyjson.com/products", ProductMeasurement.class);

    ProductMeasurement result2 = _restTemplate.getForObject("https://dummyjson.com/products", ProductMeasurement.class);

    ProductMeasurement result3 = _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductMeasurement.class).block();


    return List.of(result1, result2, result3);
  }

  public Mono<ProductMeasurement> callFirstApi() {
    return _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductMeasurement.class)
        .doOnSuccess(result -> {
          System.out.println("Received response for first async products request: " + result);
          System.out.println("Thread handling the first async products request: " + Thread.currentThread().getName());
        }).doOnError(error -> {
          System.out.println("Error occurred while calling the first API: " + error.getMessage());
        });
  }

  public Mono<List<ProductMeasurement>> fetchProductsChainedAsync() {
    System.out.println("Thread handling fetchProductsChainedAsync request: " + Thread.currentThread().getName());

    return callFirstApi().flatMap(apiResult -> {
          return callFirstApi().flatMap(apiResult2 -> {
                return callFirstApi().map(apiResult3 -> List.of(apiResult, apiResult2, apiResult3));
              });
        });
  }

  public Flux<ProductMeasurement> fetchProductsFluxStream() {
    System.out.println("Thread handling fetchProductsFluxStream request: " + Thread.currentThread().getName());

    return Flux.interval(Duration.ofSeconds(3)).take(20).flatMap(i -> _webClient.get().uri("https://dummyjson.com/products").retrieve().bodyToMono(ProductMeasurement.class)
        .doOnSuccess(result -> {
          System.out.println("Received response for flux stream products request: " + result);
          System.out.println("Thread handling the flux stream products request: " + Thread.currentThread().getName());
        }).doOnError(error -> {
          System.out.println("Error occurred in flux stream products request: " + error.getMessage());
        }));
  }
}
