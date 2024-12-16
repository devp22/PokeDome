default: build

rebuild: clean build

.PHONY: clean
clean:
	docker-compose -p pokedome -f docker-compose.yml down
	docker image rm pokedome/frontend
	docker image rm pokedome/backend
	docker image rm postgres:16.2

.PHONY: build
build: 
	docker build -t pokedome/backend -f ./images/Dockerfile.backend ./backend
	docker build -t pokedome/frontend -f ./images/Dockerfile.frontend ./frontend
	docker-compose -p pokedome -f docker-compose.yml up -d
