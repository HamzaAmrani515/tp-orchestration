#!/usr/bin/env bash
set -e

USERNAME="hamzaamrani"

docker tag ecommerce-membership:1.0 $USERNAME/ecommerce-membership:1.0
docker tag ecommerce-product:1.0 $USERNAME/ecommerce-product:1.0
docker tag ecommerce-order:1.0 $USERNAME/ecommerce-order:1.0

docker push $USERNAME/ecommerce-membership:1.0
docker push $USERNAME/ecommerce-product:1.0
docker push $USERNAME/ecommerce-order:1.0

echo "Images published to Docker Hub."
