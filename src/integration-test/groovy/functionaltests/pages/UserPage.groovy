package functionaltests.pages

import geb.Page

class UserPage extends Page {

    static url = '/person/index'

    static at = {
        $('div#list-person')
    }
}
