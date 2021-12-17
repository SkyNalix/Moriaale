all:
	./gradlew run

runBuild:
	./gradlew -q build
	java -jar build/libs/Moriaale.jar

clean:
	rm -f -r build