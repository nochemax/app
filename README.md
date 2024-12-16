# app

## Setting Up the Application on an Nginx Server

This project includes two important files for setting up the application on an Nginx server:

1. **servidor.py**: This is the main Python script that sets up and runs a basic web application using Flask, which will be served by an Nginx server. It includes functions to configure Nginx and run the Flask application. Ensure this file is located outside the `app` directory and is executable.

2. **instrucción.txt**: This file provides detailed instructions for installing and configuring the necessary dependencies to run the Session application on an Nginx server on Debian 12. It guides you through updating the system, installing Python, Flask, and Nginx, and configuring Nginx to serve the Flask application.

### How to Use

- Follow the steps in `instrucción.txt` to install dependencies and configure Nginx.
- Ensure `servidor.py` is executable and run it to start the application.
- Access `http://localhost` in your browser to verify the application is running correctly.

These steps will help you set up and run the application on an Nginx server efficiently.