#servidor
from flask import Flask
import os

# Initialize the Flask application
app = Flask(__name__)

# Define a basic route
@app.route('/')
def home():
    return "Welcome to the Flask server!"

# SSL Configuration
# Note: For production, use a valid certificate from Let's Encrypt or another CA
ssl_context = ('path/to/cert.pem', 'path/to/key.pem')  # Update with your certificate paths

# Run the server
if __name__ == '__main__':
    # Use '0.0.0.0' to make the server publicly available
    app.run(host='0.0.0.0', port=443, ssl_context=ssl_context)