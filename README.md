# NASA Daily

## Présentation

**Projet Android de 3ème année à l'ESIEA.**

Petit projet démontrant l'utilisation du pattern MVC dans une application Android, codé en Java.
<br/>Cette application permet l'affichage d'une liste des 10 dernières photos que la NASA poste tous les jours.
<br/>Vous y trouverez aussi une fonction de recherche par date pour revenir dans le temps.

Pour cela, l'application passe par une API que la NASA a ouvert au public.

## Consignes respectées : 

- Clean Architecture & ***MVC***
- Appels ***REST***
- Écrans : 3 activités
- Affichage d'une liste dans un ***RecyclerView***
- Affichage du détail d'un item de la liste
- ***Gitflow*** propre
<br/>
- **Fonctions supplémentaires** :
	- Recherche par date


## Fonctionnalités: 

### Écran de chargement

- Splash avec une fusée qui décolle 

![Splashscreen](http://victorguegan.fr/NASADaily/img/splash.jpg)

### Écran d'accueil 

- Affiche la liste des pokémons avec une barre de recherche.

![Homescreen](http://victorguegan.fr/NASADaily/img/home.jpg)

### Écran du détail de la photo

- Affichage de l'image en entier
- Possibilité de zoomer sur l'image pour observer plus de détails
- Petit paragraphe explicatif en anglais sur le pourquoi du comment de la photo

![Detailscreen](http://victorguegan.fr/NASADaily/img/detail.jpg)

### Selection par date

Si vous souhaitez pour retourner dans le temps et regarder une photo plus veille que les 10 photos affichées de base, vous pouvez utiliser le sélecteur de date en haut à droite de l'écran principal. 
Vous serez limité à 1996, aucune donnée n'étant disponible pour une date antérieure. 

![Datepicker with detail screen](http://victorguegan.fr/NASADaily/img/date_picker.jpg)
