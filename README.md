ImagesService
=============

Prerequisites:
* Please make sure JDK 1.7 is available and configured correctly for this project.
* Start nginx server with reversed proxy setting.

Steps to run:
* mvn package
* java -jar target/docker-service-1.0-SNAPSHOT.jar server example.yml

TODO:
* ~~Generate the image preview on the fly when uploading (or use a cron job, MQ to async process it)~~
* ~~Make sure images store into sub-folders by timestamp.~~
* ~~Register functionality~~
* ~~Login functionality~~
* Use Swagger to define the RESTful APIs.
* Integrate with 3rd API like QQ, Wechat, Weibo
* NRT solr update & scheduled DIH
* MQ (kafka or rabbit etc.) to decouple components
* Add memcached or Redis support
* Review
* Follower
* More to add...
