# Dockerfile for Expo/React Native Web frontend

FROM node:24-alpine AS builder
WORKDIR /app
COPY . .
ENV CI=true
RUN npm install -g pnpm && pnpm install
# Build static web files for Expo
RUN npx expo export --platform web

FROM nginx:alpine
# Copy static web build output to Nginx html directory
COPY --from=builder /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
