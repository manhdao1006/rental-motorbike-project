# Stage 1: Build ứng dụng Vue
FROM node:18-alpine AS build

WORKDIR /app

# Copy và install dependencies
COPY package*.json ./
RUN npm install

# Copy toàn bộ source và build
COPY . .
RUN npm run build

# Stage 2: Serve bằng Node.js (serve trên port 3000)
FROM node:18-alpine

WORKDIR /app

# Cài global serve để serve thư mục dist
RUN npm install -g serve

# Copy file đã build từ stage trước
COPY --from=build /app/dist ./dist

# Lắng nghe port 3000
EXPOSE 3000

# Chạy serve trên dist ở port 3000
CMD ["serve", "-s", "dist", "-l", "3000"]
