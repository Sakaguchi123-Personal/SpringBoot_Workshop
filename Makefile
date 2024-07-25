
lint:
	cd frontend && npm run lint
	cd frontend && npm run prettier

tests:
	cd frontend && npm run test-once
	cd server && ./gradlew clean test


pre-commit:
	make lint tests