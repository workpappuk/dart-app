# Dockerfile for Expo/React Native Web frontend

FROM node:24-alpine AS builder
WORKDIR /app
COPY . .
ENV CI=true
RUN npm install -g pnpm && pnpm install
# Build static web files for Expo
RUN npx expo export --platform web
