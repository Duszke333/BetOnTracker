FROM node:24-alpine AS builder
WORKDIR /app
# COPY package.json package-lock.json ./
# RUN npm ci
COPY . .
RUN rm -rf node_modules package-lock.json
RUN NODE_ENV=development npm i @types/node --save-dev
RUN npm run build

FROM busybox:1.37 AS runner
WORKDIR /app
COPY --from=builder /app/dist .
CMD ["busybox", "httpd", "-f", "-v", "-p", "8080"]
