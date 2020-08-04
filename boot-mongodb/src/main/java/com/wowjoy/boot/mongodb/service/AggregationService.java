package com.wowjoy.boot.mongodb.service;

import org.mockito.mock.MockName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ConditionalOperators.IfNull.ifNull;
import static org.springframework.data.mongodb.core.aggregation.ConditionalOperators.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author scq
 */
@Service
public class AggregationService {
    private final MongoTemplate mongoTemplate;
    @Autowired
    public AggregationService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void aggreation1() {
        // 分组，排序和投影（选择）
        TypedAggregation<ZipInfo> aggregation = newAggregation(ZipInfo.class,
                group("state", "city")
                        .sum("population").as("pop"),
                sort(ASC, "pop", "state", "city"),
                group("state")
                        .last("city").as("biggestCity")
                        .last("pop").as("biggestPop")
                        .first("city").as("smallestCity")
                        .first("pop").as("smallestPop"),
                project()
                        .and("state").previousOperation()
                        .and("biggestCity")
                        .nested(bind("name", "biggestCity").and("population", "biggestPop"))
                        .and("smallestCity")
                        .nested(bind("name", "smallestCity").and("population", "smallestPop")),
                sort(ASC, "state")

        );
        AggregationResults<ZipInfoStats> result = mongoTemplate.aggregate(aggregation, ZipInfoStats.class);
        ZipInfoStats firstZipInfoStats = result.getMappedResults().get(0);
    }

    public void aggreation2() {
        // 分组，排序和匹配（过滤）
        TypedAggregation<ZipInfo> agg = newAggregation(ZipInfo.class,
                group("state").sum("population").as("totalPop"),
                sort(ASC, previousOperation(), "totalPop"),
                match(where("totalPop").gte(10 * 1000 * 1000))
        );

        AggregationResults<StateStats> result = mongoTemplate.aggregate(agg, StateStats.class);
        List<StateStats> stateStatsList = result.getMappedResults();
    }

    public void aggreataion3() {
        // 投影操作中使用简单算术运算
        TypedAggregation<Product> agg = newAggregation(Product.class,
                project("name", "netPrice")
                        .and("netPrice").plus(1).as("netPricePlus1")
                        .and("netPrice").minus(1).as("netPriceMinus1")
                        .and("netPrice").multiply(1.19).as("grossPrice")
                        .and("netPrice").divide(2).as("netPriceDiv2")
                        .and("spaceUnits").mod(2).as("spaceUnitsMod2")
        );
        AggregationResults<Product> result = mongoTemplate.aggregate(agg, Product.class);
        List<Product> resultList = result.getMappedResults();

        // SpEL表达式派生的简单算术运算
        agg = newAggregation(Product.class,
                project("name", "netPrice")
                        .andExpression("netPrice + 1").as("netPricePlus1")
                        .andExpression("netPrice - 1").as("netPriceMinus1")
                        .andExpression("netPrice / 2").as("netPriceDiv2")
                        .andExpression("netPrice * 1.19").as("grossPrice")
                        .andExpression("spaceUnits % 2").as("spaceUnitsMod2")
                        .andExpression("(netPrice * 0.8  + 1.2) * 1.19").as("grossPriceIncludingDiscountAndCharge")

        );
        result = mongoTemplate.aggregate(agg, Product.class);
        resultList = result.getMappedResults();

        // SpEL表达式派生的复杂算术运算
        double shippingCosts = 1.2;
        agg = newAggregation(Product.class, project("name", "netPrice").andExpression("(netPrice * (1-discountRate)  + [0]) * (1+taxRate)", shippingCosts).as("salesPrice"));
        result = mongoTemplate.aggregate(agg, Product.class);
        resultList = result.getMappedResults();



    }

    public void aggreataion4() {
        TypedAggregation<Book> agg = Aggregation.newAggregation(Book.class,
                project("title")
                        .and(ConditionalOperators.when(ComparisonOperators.valueOf("author.middle")
                                .equalToValue(""))
                                .then("$$REMOVE")
                                .otherwiseValueOf("author.middle")
                        )
                        .as("author.middle"));
    }



}


class ZipInfo {
    String id;
    String city;
    String state;
    @Field("pop")
    int population;
    @Field("loc")
    double[] location;
}

class City {
    String name;
    int population;
}

class ZipInfoStats {
    String id;
    String state;
    City biggestCity;
    City smallestCity;
}

class StateStats {
    @Id
    String id;
    String state;
    @Field("totalPop")
    int totalPopulation;
}

@Document
class Product {
    String id;
    String name;
    double netPrice;
    int spaceUnits;
}

@Document
class InventoryItem {

    @Id
    int id;
    String item;
    String description;
    int qty;
}

@Document
class InventoryItemProjection {

    @Id
    int id;
    String item;
    String description;
    int qty;
    int discount;
}

@Document
class Book{
    @Id
    int id;
    String title;
    String content;
    String author;

}