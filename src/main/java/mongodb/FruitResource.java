package mongodb;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@Path("/fruits")
public class FruitResource {

    @Inject
    FruitService fruitService;

    @Inject
    KafkaService kafkaService;

    @GET
    public List<Fruit> list(Fruit fruit) {
        List<Fruit> data = fruitService.list(fruit.getId());
        kafkaService.publisMessage("test-quarkustopic-one", new Gson().toJson(data));
        return data;
    }

    @POST
    public List<Fruit> add(Fruit fruit) {
        fruitService.add(fruit);
        return list(fruit);
    }
}