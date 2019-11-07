# MyNews

![banner](https://i.goopics.net/9Oaky.png)

## I. Introduction
### I.1 Objet
Ce document décrit les spécifications techniques et fonctionnelles pour le développement d’une application regroupant les articles du New York Times par le biais de son API. Cette application se nomme « MyNews ».

### I.2 Contexte
Le développement de cette application ne répond pas à une demande mais à un besoin personnel. Cependant, cette application pourra être utilisée par n’importe quel utilisateur.

Le développement de l’application « MyNews » va permettre d’accéder à l’ensemble des articles publiés par le New York Times grâce à son API.

L’utilisateur aura la possibilité, grâce à trois onglets, de suivre les articles en tendance (Top Stories), les articles les plus partagés (Most Popular) ainsi que des articles sur le sujet choisi au développement de l’application (dans le cas présent Arts).

## II. Fonctionnalités
### II.1 La barre d’outil
La barre d’outil (Toolbar) est située en haut de l’application et permet d’accéder à divers menus.

Dans le cas présent, la barre d’outil affiche un bouton d’accès au menu de navigation (Navigation Drawer), le titre de l’application « MyNews », le bouton de recherche ainsi que le menu de paramètres.

![toolbar](https://i.goopics.net/2Op1N.png)

### II.2 Le menu de navigation
Le Navigation Drawer est un menu qui permet à l’utilisateur d’accéder aux différentes catégories de l’application.

D’ici, l’utilisateur va pouvoir accéder aux trois listes d’articles de l’application ainsi qu’à la fonction de recherche proposée par l’application mais aussi au menu de paramétrage des notifications

Les boutons « Help » et « About », quant à eux, ouvrent une fenêtre expliquant en quelques mots l’intérêt de l’application et le cadre dans lequel elle a été réalisée.

![navigationdrawer](https://i.goopics.net/mbkrO.png)

### II.3 Choix de la liste des articles
L’utilisateur a le choix entre trois onglets d’articles (TabLayout). Le choix se fait en cliquant directement sur les onglets. Ou à l’aide d’un ViewPager en faisant glisser le doigt sur l’écran : de droite à gauche pour passer à l’onglet suivant et de gauche à droite pour revenir à l’onglet précédant.

À l’intérieur de ces onglets se trouve la liste des articles lui correspondant.

### II.4 Le choix d’un article
Le choix d’un article se fait par une pression sur la ligne d’un article. Une nouvelle activité sera créée où une WebView affichera l’intégralité de l’article du New York Times.

![tablayout](https://i.goopics.net/VG0XA.png)

### II.5 L’écran de recherche
Le menu de recherche est disponible par le biais du bouton « Loupe ». Le menu se présente sous la forme d’une nouvelle activité où l’utilisateur peut choisir les termes de sa recherche.

Un EditText est présent pour rechercher un terme personnalisé. Des CheckBoxes permettent quant à elles de cibler les recherches.

Le choix du filtre par date se fait à l’aide d’un Date Picker). Ce choix est facultatif pour l’utilisateur, il peut décider de laisser ces champs comme ils le sont. Le champ « Begin Date » sera toujours réglé sur la date du jour, mais n’a pas d’incidence si la date de fin « End Date » n’est pas choisie.

![search](https://i.goopics.net/9Oaxy.png)

### II.6 Le menu de la barre d’outils
Un menu est disponible sur la barre d’outils. L’accès se fait grâce au bouton représenté par les trois points.

Ce menu permet d’accéder aux paramètres de notifications, à la fiche Help ainsi qu’à la fiche About.

![toolbarmenu](https://i.goopics.net/N29kR.png)

### II.7 Le menu de notifications
Le menu de notifications permet à l’utilisateur de pouvoir paramétrer les notifications reçues.

Il pourra choisir un terme à entrer dans l’EditText ainsi que des sujets dans lesquels rechercher ce terme.

Le résultat de la recherche d’articles par notification sera disponible chaque jour (24 heures), à l’heure où la notification a été paramétrée. Elle se présentera sous forme d’un bandeau contenant le nombre d’articles correspondants aux critères choisis.

![notifications](https://i.goopics.net/obajo.png)

### II.8 Informations supplémentaires
Les menus de la barre d’outils « Help » et « About » donnent tous les deux des informations concernant l’application. Le menu « Help » donne une information sur l’utilité de l’application. Le menu « About » donne quant à lui le cadre dans lequel l’application est développée.

![informations](https://i.goopics.net/xXNaR.png)
