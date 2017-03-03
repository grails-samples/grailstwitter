package functionaltests.pages

import geb.Page

class LoginPage extends Page{

    static url = '/login'

    static at = {
        loginButton.value() == 'Login'
    }

    static content = {
        loginButton { $('input', type: 'submit') }
    }
}
