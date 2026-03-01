# Dockerfile for Expo/React Native Web frontend

# Build stage
FROM node:24-alpine AS builder
WORKDIR /app
COPY . .
ENV CI=true
RUN npm install -g pnpm && pnpm install
RUN npx expo export --platform web

# Production stage
FROM node:24-alpine
WORKDIR /app
RUN npm install -g serve
COPY --from=builder /app/dist /app/dist
EXPOSE 7070
CMD ["serve", "-s", "dist", "-l", "7070"]
