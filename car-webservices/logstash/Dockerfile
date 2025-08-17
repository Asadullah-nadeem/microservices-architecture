FROM ubuntu:latest
FROM docker.elastic.co/logstash/logstash:7.16.2

LABEL authors="asadu"

ENTRYPOINT ["top", "-b"]
COPY logstash.conf /usr/share/logstash/pipeline/
