ImagesService
=============

Prerequisites:
Please make sure JDK 1.7 is available and configured correctly for this project.

Steps to run:
* mvn package
* java -jar target/docker-service-1.0-SNAPSHOT.jar server example.yml


1. Start nginx server with reversed proxy setting.
2. Start the restful service

TODO:
* Use Swagger to define the RESTful APIs.
* Generate the image preview on the fly when uploading (or use a cron job, MQ to async process it)
* Make sure images store into sub-folders by timestamp.
* Add memcached or Redis support
* Register functionality
* ~~Login functionality~~
* Integrate with 3rd API like QQ, Wechat, Weibo
* More to add...
