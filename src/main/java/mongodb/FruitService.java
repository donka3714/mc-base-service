package mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FruitService {

    @Inject MongoClient mongoClient;

    public List<Fruit> list(String id){
        List<Fruit> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Fruit fruit = new Fruit();
                fruit.setName(document.getString("name"));
                fruit.setDescription(document.getString("description"));
                fruit.setId(document.getString("id"));
                list.add(fruit);
            }
        } finally {
            cursor.close();
        }

        List<Fruit> result = list.stream()
                .filter(fruit -> id.equals(fruit.getId()))
                .collect(Collectors.toList());

        return result;
    }

    public void add(Fruit fruit){
        Document document = new Document()
                .append("name", fruit.getName())
                .append("id",fruit.getId())
                .append("description", fruit.getDescription());
        getCollection().insertOne(document);
    }

    private MongoCollection getCollection(){
        return mongoClient.getDatabase("fruit").getCollection("fruit");
    }
}