Projet de CPOO 2021-2022 de TAZOUEV Arbi et GUINET Virgile

Compilation du projet :
Grace a Gradle vous pouvez directement exécuter le programme avec './gradlew run'
En plus de ca, nous avons fait en sorte que vous puissiez compiler tout le projet en un unique fichier .jar.
Pour cela faites "./gradlew build" et ça va créer Moriaale.jar dans build/libs/

Exécution du projet:
Pour lancer la version du programme avec l'interface graphique il suffit de lancer normalement le programme,
du coup avec ( à la racine du projet ) './gradlew run' ou pour le jar "java -jar build/libs/Moriaale.jar"

Pour lancer la version en ligne de commande, il faut juste ajouter quelques paramètres au lancement
Utilisez le paramètre "-h" ou "--help" pour avoir la liste des paramètres et des exemples.
Avec Gradle il faut mettre les arguments comme ceci: ./gradlew run --args="--help"
Et avec le jar vous pouvez faire directement "java -jar build/libs/Moriaale.jar --help"
Chaque option permet de modifier une valeur, si vous souhaitez ne rien modifier vous pouvez juste utiliser
le parametre"--draw"

Nous avons utilisé JavaFX au lieu de Swing car c'est celui avec lequel on est le plus habitué à utiliser.
Nous avons également implémenté Gradle dans notre projet, qui nous a permis d'ajouter les librairies Javafx assez facilement
et nous a permis de structurer notre projet en séparant le code et les ressources, ainsi que permettre une exécution assez facile

On a implémenté les threads dans notre projet le calcul les fractales rapidement.
Nous avons séparé l'image en 4 parties, qui a chacun un thread séparé qui est responsable de calculer
les divergences de tous les pixels dans cette partie.n un thread séparé qui est résponsable de calculer
les divergences de tous les pixels dans cette partie.

Nous avons utilisé ce lien pour implementer la fractale de Mandelbrot
https://openclassrooms.com/forum/sujet/fractale-mandelbrot-java-46496