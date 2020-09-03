#!/usr/bin/python3

import http.server
import socketserver
import sys, getopt
import threading
import socket
import time
from analytics import ServerUsageMonitor
import json


TARGET_SYNC_ADDRESS              = "localhost"
TARGET_SYNC_PORT                 = 3000
TARGET_USAGE_DATA_EXCANGE_PORT   = 3001
MEDIA_PORT                       = 8000

TARGET_SYNC_ACK_TIMEOUT     = 3
TARGET_SYNC_ACK_ATTEMPTS    = 5
TARGET_SYNC_UPDATE_INTERVAL = 60

SERVER_KEY = ''


def print_usage():
	print("%s -k <server_key> -t <target_address> [-p <streaming_port>]" % sys.argv[0])
	

class CORSHttpRequestHandler(http.server.SimpleHTTPRequestHandler):
	def end_headers(self):
		global serverUsageMonitor
		serverUsageMonitor.onNewRequest()
		self.send_header('Access-Control-Allow-Origin', '*')
		super().end_headers()

def target_sync_routine():
	while True:
		sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
		
		sock.settimeout(TARGET_SYNC_ACK_TIMEOUT)
		sock.bind(('', TARGET_SYNC_PORT))
		
		ack = False
		attempts = 0
		while not ack and attempts < TARGET_SYNC_ACK_ATTEMPTS:
			serverDataObject = { "key" : SERVER_KEY, "streaming_port" : MEDIA_PORT }
			serverDataJson = json.dumps(serverDataObject)
			sock.sendto(serverDataJson.encode(), (TARGET_SYNC_ADDRESS, TARGET_SYNC_PORT))
			
			try:
				data, addr = sock.recvfrom(1024)
				print("Successfully synchronized to target server at %s." % TARGET_SYNC_ADDRESS)
				ack = True
			except socket.timeout:
				attempts += 1
				print("Synchronization timeout exception.", TARGET_SYNC_ACK_ATTEMPTS - attempts, "remaining attempts.")
				pass

		sock.close()
		time.sleep(TARGET_SYNC_UPDATE_INTERVAL)

try:
	optlist, args = getopt.getopt(sys.argv[1:], 'k:t:p:')
except getopt.GetoptError:
	print_usage()
	sys.exit(2)

for o, a in optlist:
	if o == '-t':
		TARGET_SYNC_ADDRESS = a
	elif o == '-k':
		SERVER_KEY = a
	elif o == '-p':
		MEDIA_PORT = int(a)

sync_thread = threading.Thread(target=target_sync_routine, daemon=True)
sync_thread.start()

serverUsageMonitor = ServerUsageMonitor((TARGET_SYNC_ADDRESS, TARGET_USAGE_DATA_EXCANGE_PORT), SERVER_KEY)

try:
	with socketserver.TCPServer(("", MEDIA_PORT), CORSHttpRequestHandler) as httpd:
		print("Serving HTTP on 0.0.0.0 port %d (http://0.0.0.0:%d) ..." % (MEDIA_PORT, MEDIA_PORT))
		httpd.serve_forever()
except KeyboardInterrupt:
	sys.exit(0)

