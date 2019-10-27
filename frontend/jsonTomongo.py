import json
import datetime
import urllib.parse

folder = 'diff_article/'
from model import Dow30Asc, Dow30Desc, NasdaqAsc, NasdaqDesc, SP500Desc, SP500Asc


def addDow30Asc():
    """
    Convert the json object in well defined schema for the mongo.
    """
    file = folder + 'dow30_diff_nyt_diff_close_asc.json'
    with open(file) as f:
        for line in f:
            data = json.loads(line)
            data['date'] = datetime.datetime.strptime(data['date'], '%Y-%m-%d')
            lead_paragraph = ""
            snippet = ""
            if "lead_paragraph" in data:
                lead_paragraph = data['lead_paragraph']
            if "snippet" in data:
                snippet = data['snippet']

            Dow30Asc.create(date=data['date'], difference_close=data['difference_close'],
                            difference_open=data['difference_open'], web_url=data['web_url'], snippet=snippet,
                            lead_paragraph=lead_paragraph)


def addDow30Desc():
    """
    Convert the json object in well defined schema for the mongo.
    """
    file = folder + 'dow30_diff_nyt_diff_close_desc.json'
    with open(file) as f:
        for line in f:
            data = json.loads(line)
            data['date'] = datetime.datetime.strptime(data['date'], '%Y-%m-%d')
            lead_paragraph = ""
            snippet = ""
            if "lead_paragraph" in data:
                lead_paragraph = data['lead_paragraph']
            if "snippet" in data:
                snippet = data['snippet']

            Dow30Desc.create(date=data['date'], difference_close=data['difference_close'],
                             difference_open=data['difference_open'], web_url=data['web_url'], snippet=snippet,
                             lead_paragraph=lead_paragraph)


def addNasdaqAsc():
    """
    Convert the json object in well defined schema for the mongo.
    """
    file = folder + 'nasdaq_diff_nyt_diff_close_asc.json'
    with open(file) as f:
        for line in f:
            data = json.loads(line)
            data['date'] = datetime.datetime.strptime(data['date'], '%Y-%m-%d')
            lead_paragraph = ""
            snippet = ""
            if "lead_paragraph" in data:
                lead_paragraph = data['lead_paragraph']
            if "snippet" in data:
                snippet = data['snippet']

            NasdaqAsc.create(date=data['date'], difference_close=data['difference_close'],
                             difference_open=data['difference_open'], web_url=data['web_url'], snippet=snippet,
                             lead_paragraph=lead_paragraph)


def addNasdaqDesc():
    """
    Convert the json object in well defined schema for the mongo.
    """
    file = folder + 'nasdaq_diff_nyt_diff_close_desc.json'
    with open(file) as f:
        for line in f:
            data = json.loads(line)
            data['date'] = datetime.datetime.strptime(data['date'], '%Y-%m-%d')
            lead_paragraph = ""
            snippet = ""
            if "lead_paragraph" in data:
                lead_paragraph = data['lead_paragraph']
            if "snippet" in data:
                snippet = data['snippet']

            NasdaqDesc.create(date=data['date'], difference_close=data['difference_close'],
                              difference_open=data['difference_open'], web_url=data['web_url'], snippet=snippet,
                              lead_paragraph=lead_paragraph)


def addSP500Asc():
    """
    Convert the json object in well defined schema for the mongo.
    """
    file = folder + 'sp500_diff_nyt_diff_close_asc.json'
    with open(file) as f:
        for line in f:
            data = json.loads(line)
            data['date'] = datetime.datetime.strptime(data['date'], '%Y-%m-%d')
            lead_paragraph = ""
            snippet = ""
            if "lead_paragraph" in data:
                lead_paragraph = data['lead_paragraph']
            if "snippet" in data:
                snippet = data['snippet']

            SP500Asc.create(date=data['date'], difference_close=data['difference_close'],
                            difference_open=data['difference_open'], web_url=data['web_url'], snippet=snippet,
                            lead_paragraph=lead_paragraph)


def addSP500Desc():
    """
    Convert the json object in well defined schema for the mongo.
    """
    file = folder + 'sp500_diff_nyt_diff_close_desc.json'
    with open(file) as f:
        for line in f:
            data = json.loads(line)
            data['date'] = datetime.datetime.strptime(data['date'], '%Y-%m-%d')
            lead_paragraph = ""
            snippet = ""
            if "lead_paragraph" in data:
                lead_paragraph = data['lead_paragraph']
            if "snippet" in data:
                snippet = data['snippet']

            SP500Desc.create(date=data['date'], difference_close=data['difference_close'],
                             difference_open=data['difference_open'], web_url=data['web_url'], snippet=snippet,
                             lead_paragraph=lead_paragraph)


if __name__ == '__main__':
    addDow30Asc()
    addDow30Desc()
    addNasdaqAsc()
    addNasdaqDesc()
    addSP500Asc()
    addSP500Desc()