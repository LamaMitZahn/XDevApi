package de.ruben.xdevapi.storage;

import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.InsertManyOptions;
import de.ruben.xdevapi.XDevApi;
import io.netty.util.concurrent.CompleteFuture;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MongoDBStorage {


    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoClientOptions mongoClientOptions;
    private XDevApi plugin;
    private String host;
    private String database;
    private int port;
    private String user;
    private String password;

    private ExecutorService executorService;

    public MongoDBStorage(XDevApi plugin, int poolsize, String host, String database, int port, String user, String password, MongoClientOptions mongoClientOptions) {
        this.plugin = plugin;
        this.host = host;
        this.database = database;
        this.port = port;
        this.user = user;
        this.password = password;
        this.mongoClientOptions = mongoClientOptions;
        //this.codecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),CodecRegistries.fromProviders(new UuidCodecProvider(UuidRepresentation.JAVA_LEGACY)));
        this.executorService = Executors.newFixedThreadPool(poolsize, new DefaultThreadFactory(database+" - "));

        plugin.consoleMessage("&eMongoDB Global Storage started", true);
    }

    public MongoDBStorage(XDevApi plugin, String host, String database, int port, String user, String password, MongoClientOptions mongoClientOptions) {
        this(plugin, 2, host, database, port, user, password, mongoClientOptions);
    }

    public MongoDBStorage(XDevApi plugin, String host, String database, int port, MongoClientOptions mongoClientOptions) {
        this(plugin, 2, host, database, port, "", "", mongoClientOptions);
    }

    public CompletableFuture<Document> insertOneDocument(String collection, Document document){
        MongoCollection<Document> mongoCollection = getCollection(collection);

        return CompletableFuture.supplyAsync(() -> {
            mongoCollection.insertOne(document);
            return document;
        }, executorService);
    }

    public CompletableFuture<List<Document>> insertDocument(String collection, List<Document> document){
        MongoCollection<Document> mongoCollection = getCollection(collection);

        return CompletableFuture.supplyAsync(() -> {
            mongoCollection.insertMany(document);
            return document;
        }, executorService);
    }

    public CompletableFuture<List<Document>> insertDocument(String collection, Document... documents){
        MongoCollection<Document> mongoCollection = getCollection(collection);

        return CompletableFuture.supplyAsync(() -> {
            mongoCollection.insertMany(Arrays.asList(documents));
            return Arrays.asList(documents);
        }, executorService);
    }

    public CompletableFuture<Document> getDocumentByDocument(String collection, Document document){
        return getDocumentByBson(collection, document);
    }

    public CompletableFuture<Document> getDocumentByBson(String collection, Bson bson){
        MongoCollection<Document> mongoCollection = getCollection(collection);
        return CompletableFuture.supplyAsync(() -> mongoCollection.find(bson).first(), executorService);
    }

    public CompletableFuture<Document> updateDocument(String collection, Bson document, Bson updatedDocument){
        MongoCollection<Document> mongoCollection = getCollection(collection);
        return CompletableFuture.supplyAsync(() -> mongoCollection.findOneAndUpdate(document, updatedDocument));
    }

    public CompletableFuture<Document> replaceDocument(String collection, Document document, Document replacement){
        return CompletableFuture.supplyAsync(() -> getCollection(collection).findOneAndUpdate(document, replacement));
    }

    public void createAscendingIndex(String collection, String... fields){
        createIndex(collection, Indexes.ascending(fields));
    }

    public void createDescendingIndex(String collection, String... fields){
        createIndex(collection, Indexes.descending(fields));
    }

    public void createIndex(String collection, Bson bson){
        MongoCollection<Document> mongoCollection = getCollection(collection);
        executorService.execute(() -> mongoCollection.createIndex(bson));
    }


    private final MongoCollection<Document> getCollection(String name) {
        try {
            com.mongodb.client.MongoCollection<Document> collection = getMongoDatabase().getCollection(name);
            return getMongoDatabase().getCollection(name);
        }
        // Collection does not exist
        catch (IllegalArgumentException e) {
            getMongoDatabase().createCollection(name);
            return getCollection(name);
        }

    }

    public void connect() {
        plugin.consoleMessage("&6Trying to connect to MongoDB&7: &b" + host + "&7:&b" + port, false);
        if (user.isEmpty() && password.isEmpty())
            this.mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
        else
            this.mongoClient = new MongoClient(new ServerAddress(host, port), List.of(MongoCredential.createCredential(user, database, password.toCharArray())), mongoClientOptions);
        this.mongoDatabase = mongoClient.getDatabase(database);
    }

    public void disconnect() {
        this.mongoClient.close();
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    private CodecRegistry codecRegistry(){
        return CodecRegistries.fromRegistries(
                CodecRegistries.fromProviders(new UuidCodecProvider(UuidRepresentation.STANDARD)),
                MongoClientSettings.getDefaultCodecRegistry()
        );
    }


    public Binary toStandardBinaryUUID(java.util.UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();

        byte[] uuidBytes = new byte[16];

        for (int i = 15; i >= 8; i--) {
            uuidBytes[i] = (byte) (lsb & 0xFFL);
            lsb >>= 8;
        }

        for (int i = 7; i >= 0; i--) {
            uuidBytes[i] = (byte) (msb & 0xFFL);
            msb >>= 8;
        }

        return new Binary((byte) 0x03, uuidBytes);
    }
}
