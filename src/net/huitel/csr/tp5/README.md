# TP5
## Partie A - Synchronisation
Utilisation d'outils de synchronisation classiques pour simuler un supermarché.
<br/>
Un objet StructureSupermarche permet de garder une référence sur tous les objets partagés. <br/>
La classe Supermarche contient le main() pour exécuter l'application.

## Partie B - API REST
La classe SupermarcheRestApp possède un attribut StructureSupermarche (référence vers les ressources partagées de la simulation).
Elle initialise le routeur de l’API REST, en redirigeant les requêtes de la manière suivante:
* Les requêtes sur les uri “/supermarche/clients” et “/supermarche/clients/” sont
redirigées vers la ressource de serveur ResourceClients.
* Les requêtes sur les uri “/supermarche/clients/{id]” et “/supermarche/clients/{id}/” sont
redirigées vers la ressource de serveur ResourceClient.
* Les requêtes sur les uri “/supermarche/clients/stock” et “/supermarche/clients/stock/”
sont redirigées vers la ressource de serveur ResourceStocks.

L’application gère la présence ou non du caractère ‘/’ à la fin de l’uri des requêtes. Elle
s’occupe de l’ajouter si besoin quand elle renvoie un objet JSON avec un uri dedans.

## Utilisation:
1. Exécuter la classe Supermarche comme une application Java.
2. Exécuter les scripts disponibles dans le dossier scripts_tp5, ou effectuer des requêtes POST ou GET sur localhost, port 5000, aux URIs précisées plus haut, pour interagir avec l'application.
