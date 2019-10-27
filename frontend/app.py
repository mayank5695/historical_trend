"""
This simple flask app provides restAPIs to connect with backend in mongodb and use View function of flask to render templates.
"""
from flask import Flask, request
from flask.helpers import send_from_directory

from core.views import ServerStockReader,ServerHomeView
from werkzeug.serving import run_simple
# create additional log files for werkzeug, and for Flask

HOST = '0.0.0.0'
PORT = 8000

app = Flask(__name__,template_folder='ui/templates', static_folder='ui/static')

app.config['CSS_FOLDER'] = 'ui/static/css'
app.config['JS_FOLDER'] = 'ui/static/js'

urls = {'home': '/',
        'css': '/css/<path:filename>',
        'js': '/js/<path:filename>',
        'stockreader': '/<searchValue>/<term>.html'
        }

@app.route(urls['css'])
def serve_css(filename):
    return send_from_directory(app.config['CSS_FOLDER'], filename)


@app.route(urls['js'])
def serve_js(filename):
    return send_from_directory(app.config['JS_FOLDER'], filename)


app.add_url_rule(urls['stockreader'], view_func=ServerStockReader.as_view('serve_translation_view'))
app.add_url_rule(urls['home'], view_func=ServerHomeView.as_view('home_view'))


if __name__ == '__main__':
    run_simple(HOST, PORT, app, use_debugger=True, threaded=True, use_reloader=False,
               use_evalex=True)
