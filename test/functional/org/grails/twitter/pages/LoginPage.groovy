package org.grails.twitter.pages

class LoginPage extends geb.Page {

    static url = 'login'

    static content = {
        loginForm { $('form', id: 'loginForm') }
        userName { loginForm.j_username() }
        password { loginForm.j_password() }
        loginButton { $('#login_button') }
    }
    
    static at = { 
        title == 'Login' 
    }
}
