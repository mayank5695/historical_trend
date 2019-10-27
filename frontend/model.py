""""
Model for mongodb data
"""

import mongoengine as db
from mongoengine import Document, DateTimeField, StringField, ReferenceField, LazyReferenceField, ListField, \
    IntField, FloatField, URLField, Q, DynamicDocument, EmbeddedDocument, EmbeddedDocumentListField, BooleanField
import mongoConnect

dbname = mongoConnect.get_main_database_name()
db.connect(dbname)


class Dow30Asc(Document):
    """
    Model class schema for the collection in mongodb
    """
    date = DateTimeField(primary_key=True)
    difference_close = FloatField()
    difference_open = FloatField()
    web_url = ListField(URLField())
    snippet = ListField(StringField())
    lead_paragraph = ListField(StringField())

    @staticmethod
    def create(date, difference_close, difference_open, web_url, snippet="", lead_paragraph=""):
        date_check = Dow30Asc.objects(date=date)
        if (len(date_check)) < 1:
            dow = Dow30Asc(date=date, difference_close=difference_close, difference_open=difference_open,
                           web_url=[web_url], snippet=[snippet], lead_paragraph=[lead_paragraph])
            dow.save()
        else:
            Dow30Asc.objects(date=date).update_one(push__web_url=web_url, push__snippet=snippet,
                                                   push__lead_paragraph=lead_paragraph)
        # save into the collection


class Dow30Desc(Document):
    """
    Model class schema for the collection in mongodb
    """
    date = DateTimeField(primary_key=True)
    difference_close = FloatField()
    difference_open = FloatField()
    web_url = ListField(URLField())
    snippet = ListField(StringField())
    lead_paragraph = ListField(StringField())

    @staticmethod
    def create(date, difference_close, difference_open, web_url, snippet="", lead_paragraph=""):
        date_check = Dow30Desc.objects(date=date)
        if (len(date_check)) < 1:
            dow = Dow30Desc(date=date, difference_close=difference_close, difference_open=difference_open,
                            web_url=[web_url], snippet=[snippet], lead_paragraph=[lead_paragraph])
            dow.save()
        else:
            Dow30Desc.objects(date=date).update_one(push__web_url=web_url, push__snippet=snippet,
                                                    push__lead_paragraph=lead_paragraph)
        # save into the collection


class NasdaqAsc(Document):
    """
        Model class schema for the collection in mongodb
        """
    date = DateTimeField(primary_key=True)
    difference_close = FloatField()
    difference_open = FloatField()
    web_url = ListField(URLField())
    snippet = ListField(StringField())
    lead_paragraph = ListField(StringField())

    @staticmethod
    def create(date, difference_close, difference_open, web_url, snippet="", lead_paragraph=""):
        date_check = NasdaqAsc.objects(date=date)
        if (len(date_check)) < 1:
            nasdaq = NasdaqAsc(date=date, difference_close=difference_close, difference_open=difference_open,
                               web_url=[web_url], snippet=[snippet], lead_paragraph=[lead_paragraph])
            nasdaq.save()
        else:
            NasdaqAsc.objects(date=date).update_one(push__web_url=web_url, push__snippet=snippet,
                                                    push__lead_paragraph=lead_paragraph)
        # save into the collection


class NasdaqDesc(Document):
    """
        Model class schema for the collection in mongodb
        """
    date = DateTimeField(primary_key=True)
    difference_close = FloatField()
    difference_open = FloatField()
    web_url = ListField(URLField())
    snippet = ListField(StringField())
    lead_paragraph = ListField(StringField())

    @staticmethod
    def create(date, difference_close, difference_open, web_url, snippet="", lead_paragraph=""):
        date_check = NasdaqDesc.objects(date=date)
        if (len(date_check)) < 1:
            nasdaq = NasdaqDesc(date=date, difference_close=difference_close, difference_open=difference_open,
                                web_url=[web_url], snippet=[snippet], lead_paragraph=[lead_paragraph])
            nasdaq.save()
        else:
            NasdaqDesc.objects(date=date).update_one(push__web_url=web_url, push__snippet=snippet,
                                                     push__lead_paragraph=lead_paragraph)
        # save into the collection


class SP500Asc(Document):
    """
        Model class schema for the collection in mongodb
        """
    date = DateTimeField(primary_key=True)
    difference_close = FloatField()
    difference_open = FloatField()
    web_url = ListField(URLField())
    snippet = ListField(StringField())
    lead_paragraph = ListField(StringField())

    @staticmethod
    def create(date, difference_close, difference_open, web_url, snippet="", lead_paragraph=""):
        date_check = SP500Asc.objects(date=date)
        if (len(date_check)) < 1:
            sp = SP500Asc(date=date, difference_close=difference_close, difference_open=difference_open,
                          web_url=[web_url], snippet=[snippet], lead_paragraph=[lead_paragraph])
            sp.save()
        else:
            SP500Asc.objects(date=date).update_one(push__web_url=web_url, push__snippet=snippet,
                                                   push__lead_paragraph=lead_paragraph)
        # save into the collection


class SP500Desc(Document):
    """
        Model class schema for the collection in mongodb
        """
    date = DateTimeField(primary_key=True)
    difference_close = FloatField()
    difference_open = FloatField()
    web_url = ListField(URLField())
    snippet = ListField(StringField())
    lead_paragraph = ListField(StringField())

    @staticmethod
    def create(date, difference_close, difference_open, web_url, snippet="", lead_paragraph=""):
        date_check = SP500Desc.objects(date=date)
        if (len(date_check)) < 1:
            sp = SP500Desc(date=date, difference_close=difference_close, difference_open=difference_open,
                           web_url=[web_url], snippet=[snippet], lead_paragraph=[lead_paragraph])
            sp.save()
        else:
            SP500Desc.objects(date=date).update_one(push__web_url=web_url, push__snippet=snippet,
                                                    push__lead_paragraph=lead_paragraph)
        # save into the collection


def get_data_Dow_fall(specified_value):
    """

    :param specified_value: the number of datapoints to  fetch from the backend
    :param collection: specify which collection to be fetched
    :return: list of dictionaries
    """
    data_points = Dow30Asc.objects.order_by('difference_close')
    data_points = data_points[:specified_value]
    return data_points


def get_data_Dow_rise(specified_value):
    """

    :param specified_value: the number of datapoints to  fetch from the backend
    :param collection: specify which collection to be fetched
    :return: list of dictionaries
    """
    data_points = Dow30Desc.objects.order_by('-difference_close')
    data_points = data_points[:specified_value]
    return data_points


def get_data_nasdaq_fall(specified_value):
    """

    :param specified_value: the number of datapoints to  fetch from the backend
    :param collection: specify which collection to be fetched
    :return: list of dictionaries
    """
    data_points = NasdaqAsc.objects.order_by('difference_close')
    data_points = data_points[:specified_value]
    return data_points


def get_data_nasdaq_rise(specified_value):
    """

    :param specified_value: the number of datapoints to  fetch from the backend
    :param collection: specify which collection to be fetched
    :return: list of dictionaries
    """
    data_points = NasdaqDesc.objects.order_by('-difference_close')
    data_points = data_points[:specified_value]
    return data_points


def get_data_sp_fall(specified_value):
    """

    :param specified_value: the number of datapoints to  fetch from the backend
    :param collection: specify which collection to be fetched
    :return: list of dictionaries
    """
    data_points = SP500Asc.objects.order_by('difference_close')
    data_points = data_points[:specified_value]
    return data_points


def get_data_sp_rise(specified_value):
    """

    :param specified_value: the number of datapoints to  fetch from the backend
    :param collection: specify which collection to be fetched
    :return: list of dictionaries
    """
    data_points = SP500Desc.objects.order_by('-difference_close')
    data_points = data_points[:specified_value]
    return data_points


def get_data(specified_value, term):
    print(term)
    if term == 'dow fall':
        data = get_data_Dow_fall(specified_value)
        return data
    elif term == 'dow rise':
        data = get_data_Dow_rise(specified_value)
        return data
    elif term == 'nasdaq fall':
        data = get_data_nasdaq_fall(specified_value)
        return data
    elif term == 'nasdaq rise':
        data = get_data_nasdaq_rise(specified_value)
        return data
    elif term == 'sp500 fall':
        data = get_data_sp_fall(specified_value)
        return data
    else:
        data = get_data_sp_rise(specified_value)
        return data

