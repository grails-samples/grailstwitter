package functionaltests.pages

import geb.Page

class StatusPage extends Page {

    static url = '/'

    static at = {
        $('title').text() == 'What Are You Doing?'
    }
}
