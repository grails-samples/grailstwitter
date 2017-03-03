package functionaltests.pages

import geb.Page

class StatusPage extends Page {

    static url = '/'

    static at = {
        $('form#updateStatusForm')
    }
}
