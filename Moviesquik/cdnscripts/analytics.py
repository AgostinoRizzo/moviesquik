#!/usr/bin/python3

from threading import RLock, Thread, Condition
from time import sleep
from collections import deque
import socket
import json

SERVER_USAGE_SAMPLES_WINDOW = 50
SERVER_USAGE_SAMPLES_INTERVAL = 1.0

class ServerUsageSamples:
    
    def __init__(self):
        self.samples = deque([0 for _ in range(SERVER_USAGE_SAMPLES_WINDOW)])
    
    def addLastSample(self, sample:int):
        self.samples.popleft()
        self.samples.append(sample)
    
    def clone(self):
        clone = ServerUsageSamples()
        clone.samples = self.samples.copy()
        return clone

class ServerUsageSamplesCommunicator(Thread):
    
    def __init__(self, targetAddress, serverKey):
        super().__init__()
        self.currentUsageSamples = None
        self.targetAddress = targetAddress
        self.serverKey = serverKey
        self.lock = RLock()
        self.cond = Condition(self.lock)
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    
    def putNewUsageSamples(self, usageSamples:ServerUsageSamples):
        with self.lock:
            self.currentUsageSamples = usageSamples
            self.cond.notify()
    
    def run(self):
        while True:
            
            self.lock.acquire()
            
            while self.currentUsageSamples is None:
                self.cond.wait()
            usageSample = self.currentUsageSamples
            self.currentUsageSamples = None
            self.lock.release()
            
            self.sendCurrentUsageSample(usageSample)
    
    def sendCurrentUsageSample(self, usageSamples:ServerUsageSamples):
        usageSamplesObject = { "key" : self.serverKey, "samples" : list(usageSamples.samples) }
        usageSamplesJson = json.dumps(usageSamplesObject)
        self.sock.sendto(usageSamplesJson.encode(), self.targetAddress)
            

class ServerUsageSamplesUpdater(Thread):
    
    def __init__(self, monitor, targetAddress, serverKey):
        super().__init__()
        self.monitor = monitor
        self.usageSamples = ServerUsageSamples()
        self.usageSamplesComm = ServerUsageSamplesCommunicator(targetAddress, serverKey)
        self.usageSamplesComm.start()
    
    def run(self):
        while True:
            sleep(SERVER_USAGE_SAMPLES_INTERVAL)
            requestsCount = self.monitor.reset()
            self.usageSamples.addLastSample(requestsCount)
            self.usageSamplesComm.putNewUsageSamples(self.usageSamples.clone())
            

class ServerUsageMonitor:
    
    def __init__(self, targetAddress, serverKey):
        self.requestsCount = 0
        self.lock = RLock()
        self.usageSamplesUpdater = ServerUsageSamplesUpdater(self, targetAddress, serverKey)
        self.usageSamplesUpdater.start()
    
    def onNewRequest(self):
        with self.lock:
            self.requestsCount += 1
    
    def reset(self) -> int:
        with self.lock:
            rcount = self.requestsCount
            self.requestsCount = 0
            return rcount
