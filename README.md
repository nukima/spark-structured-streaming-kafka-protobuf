# spark-structured-streaming-kafka-protobuf
### Đẩy dữ liệu random vào topic "data_tracking_manhnk9" kafka trên server 172.17.80.26 (ProtobufSerializer)
* Chạy file "src/main/java/kafka/Producer.java"  
Dữ liệu từ file "data/messages.csv" (1 triệu records)
### Job Streaming consume dữ liệu từ topic trên và ghi xuống HDFS
* Đăng nhập vào user hadoop trên server 172.17.80.27:
```
ssh hadoop@172.17.80.27
Password:1
```
* Submit lên YARN
```
spark-submit --deploy-mode cluster --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.0,com.google.protobuf:protobuf-java:3.12.2 --class Streaming.StreamingJob ~/manhnk9/structured-streaming/target/structured-streaming-1.0-SNAPSHOT.jar
```
* Dữ liệu được lưu xuống HDFS trong folder "/user/manhnk9/data_tracking/year=?/day=?/hour=?" với year, day, hour parsing từ field timestamp  
http://172.17.80.27:9870/explorer.html#/user/manhnk9/data_tracking
![alt text](https://i.imgur.com/1AY7TzL.png)
* Kill job
```
yarn application --kill application_name
```
### Tạo bảng Hive từ folder dữ liệu 
```
hive
```
* Chọn database manhnk9
```
USE manhnk9;
```
* Tạo bảng manhnk9.data_tracking_2001_01_01 từ folder "/user/manhnk9/data_tracking/year=2001/day=01/hour=01"
```
CREATE TABLE IF NOT EXISTS manhnk9.data_tracking_2001_01_01 (`version` STRING, `name` STRING, `timestamp` STRING, `phone_id` STRING, `lon` STRING, `lat` STRING) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS PARQUET LOCATION "/user/manhnk9/data_tracking/year=2001/day=01/hour=01";
```
* Show bảng vừa tạo
```
SELECT * FROM manhnk9.data_tracking_2001_01_01;
```
![alt text](https://i.imgur.com/5gyzGkx.png)
### Gen Java code từ file .protto
```
mvn complie
```
### Build file jar
```
mvn package
```
