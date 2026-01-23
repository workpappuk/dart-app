psql -U pappukumar -d postgres -c "CREATE DATABASE dart_db;"
psql -U pappukumar -d postgres -c "CREATE ROLE dart_user WITH LOGIN PASSWORD 'dart_password';"


http://localhost:8080/swagger-ui/index.html