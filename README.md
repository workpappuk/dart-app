psql -U pappukumar -d postgres -c "CREATE DATABASE dart_db;"
psql -U pappukumar -d postgres -c "CREATE ROLE dart_user WITH LOGIN PASSWORD 'dart_password';"


http://localhost:8080/swagger-ui/index.html

sudo service postgresql start


cd /root/dart-app
git pull && ./scripts/run-frontend-docker.sh  start
L9/Ah.ZEw3zrnWmFj9bq