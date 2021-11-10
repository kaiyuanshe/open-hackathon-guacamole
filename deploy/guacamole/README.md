# About
This doc describes how to deploy open hackathon guacamole extension with docker. Two docker images are involved:
- [Guacd](https://guacamole.apache.org/doc/gug/guacamole-docker.html#guacd-docker-image) as deployed at [guacd on docker hub](https://hub.docker.com/r/guacamole/guacd)
- [Guacamole](https://guacamole.apache.org/doc/gug/guacamole-docker.html#guacamole-docker-image) which is the tomcat-hosted website. Open hackathon builds a custom image using [Dockerfile](https://github.com/kaiyuanshe/open-hackathon-guacamole/blob/main/deploy/guacamole/Dockerfile) based on the [official guacamole on docker hub](https://hub.docker.com/r/guacamole/guacamole).

Following below guides to understand how to run the two images.

## Required environment variables

- `OPEN_HACKATHON_HOSTNAME`: host of open hackathon api. it's `https://localhost:44385` by default for local dev purpose.
- `OPEN_HACKATHON_APP_ID`: required client app id to call open hackathon api. The default value `Development` is for local dev purpose only. Contact to Open Hackathon Admin for a authentic trusted App Id.

## Run containers locally.

#### Guacd
To run guacd locally:
```
docker run --name some-guacd -d guacamole/guacd
```

#### Guacamole
To run guacamole with custom extension locally:
```
mvn verify
cp target/openhackathon-gucamole-authentication-2.0.1.jar deploy/guacamole/extensions/
cd deploy/guacamole
docker build -t oph-guacamole .
docker run --name some-guacamole --link some-guacd:guacd -d -p 8080:8080 oph-guacamole
```

alternatively if you need to call another open hackathon api server instead of the localhost one:
```
docker run --name some-guacamole --link some-guacd:guacd -e OPEN_HACKATHON_HOSTNAME=https://hackathon-api.kaiyuanshe.cn/ -d -p 8080:8080 oph-test
```
Also you can specify `GUACAMOLE_HOME` by add  `-e GUACAMOLE_HOME=/opt/openhackathon/guacamole`.

## Run containers locally with Docker-Compose
To run with docker-compose:
```
mvn verify
cp target/openhackathon-gucamole-authentication-2.0.1.jar deploy/guacamole/extensions/
cd deploy/guacamole
docker build -t oph-guacamole .
docker-compose up -d
```

## Deploy containers to Azure Web App
TBD. [Multi-container App Tutorial](https://docs.microsoft.com/en-us/azure/app-service/tutorial-multi-container-app)