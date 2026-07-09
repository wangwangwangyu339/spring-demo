# Spring Boot Demo

Minimal Spring Boot 3.3 + Java 17 service.

## Endpoints
- `GET /` — service info (name, version, timestamp)
- `GET /hello?name=foo` — greeting
- `GET /actuator/health/liveness` — k8s liveness probe
- `GET /actuator/health/readiness` — k8s readiness probe

## Build image
```bash
docker build -t spring-demo:0.1.0 .
docker run --rm -p 8080:8080 spring-demo:0.1.0
curl http://localhost:8080/hello?name=ECI
```

## Push to ACR
```bash
REG=crpi-1enwvc0cxewylit7.cn-hangzhou.personal.cr.aliyuncs.com
docker tag  spring-demo:0.1.0 $REG/library/spring-demo:0.1.0
docker push $REG/library/spring-demo:0.1.0
```