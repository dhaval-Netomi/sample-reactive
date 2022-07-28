package test;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestRun {

    private ExecutorService executor = Executors.newFixedThreadPool(10);
    private Resource r = new Resource();

    public void run(){
        executor.submit(new AsyncTask(r));
        for(int i = 0 ; i < 100; i++){
            new Thread(new AsyncTask(r)).start();
        }
    }


    class AsyncTask implements Runnable {

        private Resource res;

        public AsyncTask(Resource res) {
            this.res = res;
        }

        @Override
        public void run() {
            Mono<ResponseEntity<String>> res = Resource.getWebClient().get().uri("http://localhost:9096/protected").retrieve().toEntity(String.class);

            res.subscribe(asyncResponse ->{
                System.out.println("Async :"+asyncResponse.toString());
            });
        }
    }

}
