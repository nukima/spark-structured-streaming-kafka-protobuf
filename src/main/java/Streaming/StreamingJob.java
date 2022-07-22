package Streaming;

import model.Datatracking.DataTracking;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;

import static utils.TimeConverter.*;


public class StreamingJob {
    public static void main(String[] args) throws StreamingQueryException {
        SparkSession spark = SparkSession
                .builder()
                .appName("Spark Kafka Integration using Structured Streaming - manhnk9")
                .getOrCreate();

        Dataset<Row> df = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "172.17.80.26:9092")
                .option("startingOffsets", "earliest")
                .option("subscribe", "data_tracking_manhnk9")
                .load();

        Dataset<String> df1 = df.select("value")
                .as(Encoders.BINARY())
                .map(
                    (MapFunction<byte[], String>) value -> String.format(
                            "%s,%s,%s,%s,%d,%d,%s,%s,%s"
                            , DataTracking.parseFrom(value).getVersion()
                            , DataTracking.parseFrom(value).getName()
                            , convertUnixTimeToDatetime(DataTracking.parseFrom(value).getTimestamp())
                            , DataTracking.parseFrom(value).getPhoneId()
                            , DataTracking.parseFrom(value).getLon()
                            , DataTracking.parseFrom(value).getLat()
                            , convertUnixTimeToYear(DataTracking.parseFrom(value).getTimestamp())
                            , convertUnixTimeToDay(DataTracking.parseFrom(value).getTimestamp())
                            , convertUnixTimeToHour(DataTracking.parseFrom(value).getTimestamp())
                    ), Encoders.STRING()
                );

        Column tempCol = functions.split(df1.col("value"), ",");

        Dataset<Row> df2 = df1
                .withColumn("version", tempCol.getItem(0))
                .withColumn("name", tempCol.getItem(1))
                .withColumn("timestamp", tempCol.getItem(2))
                .withColumn("phone_id", tempCol.getItem(3))
                .withColumn("lon", tempCol.getItem(4))
                .withColumn("lat", tempCol.getItem(5))
                .withColumn("year", tempCol.getItem(6))
                .withColumn("day", tempCol.getItem(7))
                .withColumn("hour", tempCol.getItem(8))
                .drop("value");


        StreamingQuery query = df2.writeStream()
                .outputMode("append")
                .format("parquet")
                .option("compression", "snappy")
                .option("checkpointLocation", "/user/manhnk9/checkpoint")
                .option("path", "/user/manhnk9/data_tracking")
                .partitionBy("year","day","hour")
                .start();
        query.awaitTermination();
        spark.streams().awaitAnyTermination(10000);
    }
}
