package functionaltests.pages

import geb.Page

class HomePage extends Page {

    static url = '/'

    static at = {
        $('title').text() == 'Grails 3 Twitter Sample App'
        $('h1', text: 'Welcome To The Grails 3 Twitter Sample App')
    }
}
