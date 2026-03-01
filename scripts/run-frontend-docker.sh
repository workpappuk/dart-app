
#!/bin/sh
# Script to manage the frontend Docker container using docker-compose

case "$1" in
	start)
		echo "Building and starting frontend container..."
		docker-compose build frontend
		docker-compose up -d frontend
		echo "Frontend is running at http://localhost:80"
		;;
	stop)
		echo "Stopping and removing frontend container..."
		docker-compose stop frontend
		docker-compose rm -f frontend
		echo "Frontend container stopped and removed."
		;;
	status)
		docker-compose ps frontend
		;;
	*)
		echo "Usage: $0 {start|stop|status}"
		exit 1
		;;
esac
