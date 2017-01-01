# TP1
Utilisation de mécanismes de synchronisation classiques pour gérer une simulation d'Usine avec des Ateliers et des Stocks

###Comment exécuter le TP:
Dans la méthode Usine#main, passer en paramètre du constructeur Constantes.VERSION1, Constantes.VERSION2, Constantes.VERSION3 ou Constantes.VERSION4.

####VERSION1:
2 Ateliers travaillent sur un stock de départ et un stock d'arrivée. 

####VERSION2:
2 Ateliers travaillent sur un stock de départ, un stock intermédiaire et un stock d'arrivée.

####VERSION3:
3 Ateliers travaillent sur un stock de départ, un stock intermédiaire et un stock d'arrivée.
Les deux ateliers travaillant sur les stocks intermédiaire et de fin se partagent la charge de travail.

####VERSION4: 
2 Ateliers travaillent sur un stock de départ et un stock intermédiaire, 2 autres à ateliers travaillent sur le stock intermédiaire et un stock de fin.
Les Ateliers travaillant sur les mêmes stocks se partagent la charge de travail.
Le stock intermédaire a une capacité limitée à 1.
