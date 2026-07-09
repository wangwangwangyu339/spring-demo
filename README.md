# Spring Boot Demo

Minimal Spring Boot 3.3 + Java 17 service.

## Endpoints
- `GET /` — service info (name, version, timestamp)
- `GET /hello?name=foo` — greeting
- `GET /actuator/health/liveness` — k8s liveness probe
- `GET /actuator/health/readiness` — k8s readiness probe

## Local build & run
```bash
docker build -t spring-demo:0.1.0 .
docker run --rm -p 8080:8080 spring-demo:0.1.0
curl http://localhost:8080/hello?name=docker
```

## Deploy to Aliyun FC3 (GitHub Actions)

`push` to `main` builds a Docker image, pushes it to `ghcr.io/wangwangwangyu339/spring-demo`, and calls `aliyun fc update-function` to point the FC3 custom-container function at the new image.

Required repo secrets (Settings → Secrets and variables → Actions):
- `ALIYUN_ACCESS_KEY_ID`
- `ALIYUN_ACCESS_KEY_SECRET`

Required one-time setup (run once locally):
```bash
# Function with placeholder image (workflow will update the image on each push)
aliyun fc create-function \
  --region cn-hangzhou \
  --function-name spring-demo \
  --runtime custom-container \
  --handler "main" \
  --custom-container-config '{"image":"ghcr.io/wangwangwangyu339/spring-demo:latest","port":8080}' \
  --memory-size 1024 \
  --cpu 1.0 \
  --disk-size 10240 \
  --timeout 60 \
  --instance-concurrency 1

# HTTP trigger (anonymous)
aliyun fc create-trigger \
  --region cn-hangzhou \
  --function-name spring-demo \
  --trigger-name http-trigger \
  --trigger-type http \
  --trigger-config '{"authType":"anonymous","methods":["GET","POST"]}'
```

Get the URL after the trigger is created:
```bash
TRIGGER_URL=$(aliyun fc get-trigger \
  --region cn-hangzhou \
  --function-name spring-demo \
  --trigger-name http-trigger \
  --output json | jq -r '.httpTrigger.url')
echo "$TRIGGER_URL"
curl "$TRIGGER_URL/hello?name=fcnext"
```
