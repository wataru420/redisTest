#!/bin/bash

for i in `seq 1 10`
do
	mysql -u root sorttest < HASH.sql
	/usr/local/java/bin/java -jar redisTest-assembly-1.0.jar MysqlWrite
done

echo "-----------------------"

for i in `seq 1 10`
do
	/usr/local/java/bin/java -jar redisTest-assembly-1.0.jar MysqlRead
done

echo "-----------------------"

for i in `seq 1 10`
do
	/usr/local/redis/src/redis-cli FLUSHALL
	/usr/local/java/bin/java -jar redisTest-assembly-1.0.jar RedisWrite
done

echo "-----------------------"

for i in `seq 1 10`
do
	/usr/local/java/bin/java -jar redisTest-assembly-1.0.jar RedisRead
done
