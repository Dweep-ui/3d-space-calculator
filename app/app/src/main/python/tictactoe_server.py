from flask import Flask, request
from flask_sockets import Sockets
import random

app = Flask(__name__)
sockets = Sockets(app)
clients = []

@sockets.route('/connect')
def connect(ws):
    clients.append(ws)
    player_code = random.randint(999, 1000000)
    ws.send(str(player_code))

    while not ws.closed:
        message = ws.receive()
        if message:
            # Handle message (e.g., move updates, game state)
            print(f"Received message: {message}")
            broadcast(message)  # Example: Broadcast message to all clients
    return ''

@sockets.route('/disconnect')
def disconnect(ws):
    clients.remove(ws)

def broadcast(message):
    for client in clients:
        client.send(message)

if __name__ == '__main__':
    from gevent import pywsgi
    from geventwebsocket.handler import WebSocketHandler
    server = pywsgi.WSGIServer(('0.0.0.0', 5000), app, handler_class=WebSocketHandler)
    print('Server running at http://localhost:5000')
    server.serve_forever()
