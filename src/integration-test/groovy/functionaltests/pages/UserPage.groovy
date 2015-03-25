package functionaltests.pages

import geb.Page

class UserPage extends Page {

    static url = '/users'

    static at = {
        $('title').text() == 'Twitter Users'
    }
}
