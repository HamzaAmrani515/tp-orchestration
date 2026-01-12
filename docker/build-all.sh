#!/usr/bin/env bash
set -e

echo "Building JARs..."
( cd ../membership && ./mvnw clean package -DskipTests )
( cd ../ms-product && ./mvnw clean package -DskipTests )
( cd ../ms-order && ./mvnw clean package -DskipTests )

echo "Building Docker images..."
docker build -t ecommerce-membership:1.0 ../membership
docker build -t ecommerce-product:1.0 ../ms-product
docker build -t ecommerce-order:1.0 ../ms-order

echo "Build completed successfully."
