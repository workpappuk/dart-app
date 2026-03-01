psql -U pappukumar -d postgres -c "CREATE DATABASE dart_db;"
psql -U pappukumar -d postgres -c "CREATE ROLE dart_user WITH LOGIN PASSWORD 'dart_password';"


http://localhost:8080/swagger-ui/index.html

sudo service postgresql start


cd /root/dart-app
git pull && docker-compose down --rmi all --volumes --remove-orphans && docker-compose build --no-cache && docker-compose up -d
L9/Ah.ZEw3zrnWmFj9bq