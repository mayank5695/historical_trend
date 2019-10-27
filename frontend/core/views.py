from flask import render_template
from flask.views import View
from model import get_data
class ServerHomeView(View):


    def dispatch_request(self):
        #do rendering template
        return render_template('index.html')

class ServerStockReader(View):

    def dispatch_request(self,searchValue,term):
        # do rendering before some model coding
        """
        Terms to search  : 1. Dow fall
                            2. Dow rise
                            3. nasdaq fall
                            4. nasdaq rise
                            5. sp500 fall
                            6. sp500 rise
        """

        term_copy=term.lower()

        data_points=get_data(int(searchValue),term_copy)

        return render_template('reader.html',searchValue=searchValue,term=term,data_points=data_points)
