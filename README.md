DockerService
=============

Prerequisites:
Please make sure JDK 1.7 is available and configured correctly for this project.

Steps to run:
* mvn package
* java -jar target/docker-service-1.0-SNAPSHOT.jar server example.yml
* check RESTful api <br>
1.**Query SVN:  /branch (GET)<br>**
e.g. http://localhost:8000/branch?query=jack<br>
JSON output:
```
{
    "query": "jack",
    "matchBranches": [
        "jack-i54-yjp-quicklink",
        "jack-i54-yjp-quicklink-rpt",
    ]
}
```

2.**Submit a job: /comp (POST)<br>**
e.g. http://localhost:8000/comp<br>
JSON input:
```
{
    "branchName": "jack-i58",
    "workspace": "mylocalws"
}
```
JSON output:<br>
```
 {
 "link": "http://test.com",
 "status": "submitted",
 "instanceId": "instance1"
 }
```
3.**Query a instance status: /comp/status (GET)<br>**
e.g. http://localhost:8000/comp/status?instance=11 <br>
JSON output:
```
{
    "status": "Success"
}
```

4.**Query a component status: /comp/{instance}/{component}/status (GET) <br>**
e.g. http://localhost:8000/comp/instance1/jb/status<br>
Output:
```
{
    "status": "Success"
}
```

Note: query instance & component is a bit difference as something weird issue for path mapping, this is a workaround.

5.**Perform action to certain component: /comp/{instance}/{component} (POST)<br>**
JSON input:<br>
```
{
    "operation": "restart"
}
```

JSON output:<br>
```
{
    "operation": "restart",
    "status": "Submitted"
}
```


