package functionaltests.pages

import geb.Page

class LoginPage extends Page{

    static url = '/login'

    static at = {
        $('title').text() == 'Twitter Login'
    }

    static content = {
        logginButton { $('input', type: 'submit') }
    }
}
