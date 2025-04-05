# Payment Service - Node.js

Ce service de paiement est conçu pour gérer les paiements en ligne via Stripe. Il permet la création de sessions de paiement pour les utilisateurs et gère les redirections vers des pages de succès et d'annulation après le paiement.

## Prérequis

Avant de commencer, assurez-vous d'avoir les éléments suivants installés sur votre machine :

- [Node.js](https://nodejs.org/) version 14 ou supérieure.
- [Stripe Account](https://stripe.com/) avec une clé secrète d'API (vous pouvez obtenir cette clé dans le dashboard Stripe).

## Installation

1. Clonez ce dépôt sur votre machine locale :

    ```bash
    git clone https://github.com/your-username/payment-service.git
    cd payment-service
    ```

2. Installez les dépendances :

    ```bash
    npm install
    ```

3. Créez un fichier `.env` à la racine du projet pour stocker vos variables d'environnement :

    ```bash
    touch .env
    ```

4. Dans le fichier `.env`, ajoutez vos clés et URLs :

    ```env
    STRIPE_SECRET_KEY=your_stripe_secret_key
    FRONTEND_URL=http://localhost:4200   # URL de votre frontend
    PORT=5000  # Port sur lequel le service Node.js sera lancé
    ```

## Structure du Projet

- **`/controllers`** : Contient la logique métier, notamment la création des sessions de paiement.
- **`/routes`** : Définit les routes pour interagir avec le service de paiement.
- **`/services`** : Contient des services utiles (si nécessaire).
- **`server.js`** : Point d'entrée principal de l'application, où le serveur est initialisé.
- **`.env`** : Fichier de configuration des variables d'environnement.

## Utilisation

### Lancer le service

Pour démarrer le serveur, exécutez la commande suivante :

```bash
npm start

##### .env ####
STRIPE_SECRET_KEY=sk_test_51PEXuy036eeIRjfNNs91eTdEY6sbyDG7NDKoNW1za3Tmcpdd0SR5ShlI8VFBB8ouM5YoBcIwgvS0VoPzz4gjE6TZ00pQLxaP8x
FRONTEND_URL=http://localhost:4200
###########