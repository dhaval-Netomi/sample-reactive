package test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Controller {

    private final CustomerRepository customerRepository;

    //private ExecutorService executor = Executors.newFixedThreadPool(100);

    @GetMapping("/hello")
    private Mono<ResponseEntity<String>> greet() throws InterruptedException {
        return Resource.callWebClient();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("/")
    public Flux<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{customerId}")
    public Mono<Customer> getCustomer(@PathVariable("customerId") Long id) {
        return customerRepository.findById(id);
    }

}
