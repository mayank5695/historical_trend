import os

from mongoengine import connect
from pymongo import MongoClient

__main_db_name = "StockData"

if 'MONGO_DB_HOST' in os.environ:
    __host = os.environ['MONGO_DB_HOST']  # This environment variable is set in the docker-compose file
else:
    __host = "127.0.0.1"

if 'MONGO_DB_PORT' in os.environ:
    __port = os.environ['MONGO_DB_PORT']
else:
    __port = 27017

# pymongo
try:
    connectP = MongoClient(__host, __port)
except:
    print('cannot connect to database.')

# mongoengine
try:
    connect(__main_db_name, host=__host, port=__port)
except:
    print('Cannot connect to mongoengine instance of database.')


def get_host():
    return __host


def get_port():
    return __port


def get_main_database_name():
    return __main_db_name
